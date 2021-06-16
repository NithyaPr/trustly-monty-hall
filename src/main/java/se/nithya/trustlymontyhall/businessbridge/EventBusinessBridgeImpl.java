package se.nithya.trustlymontyhall.businessbridge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.GameStat;
import se.nithya.trustlymontyhall.rabbit.EventPublisher;
import se.nithya.trustlymontyhall.repository.db.GameStatRepository;
import se.nithya.trustlymontyhall.repository.db.model.GameStatModel;

import java.util.Optional;

@Component
@Slf4j
public class EventBusinessBridgeImpl implements EventBusinessBridge{

    private final GameStatRepository gameStatRepository;

    private final EventPublisher eventPublisher;

    public EventBusinessBridgeImpl(GameStatRepository gameStatRepository, EventPublisher eventPublisher) {
        this.gameStatRepository = gameStatRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void sendMessage(String gameId, String status, String result) {
        Optional<GameStatModel> gameStat =  gameStatRepository.findById(gameId);

        if(gameStat.isEmpty()) {
            log.error("Error in sending game update event for game Id {} : No game Id found " , gameId);
            return;
        }
        eventPublisher.sendMessage(buildGameStat(gameStat.get(), status, result));
    }

    private GameStat buildGameStat(GameStatModel statModel, String status, String result) {
        return GameStat.builder().lossCount(String.valueOf(statModel.getLossCount()))
                                .winCount(String.valueOf(statModel.getWinCount()))
                .status(status).result(result).id(statModel.getId()).build();
    }
}

