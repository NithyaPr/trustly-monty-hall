package se.nithya.trustlymontyhall.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.GamePoll;
import se.nithya.trustlymontyhall.repository.db.GameStatRepository;
import se.nithya.trustlymontyhall.repository.db.model.GameStatModel;

import java.util.Optional;

@Component
@Slf4j
public class EventListener {

    private final GameStatRepository gameStatRepository;

    public EventListener(GameStatRepository gameStatRepository) {
        this.gameStatRepository = gameStatRepository;
    }

    @RabbitListener(queues = "#{rabbitMqListenerConfiguration.getPollStatisticsQueue()}")
    public void getPollStat(@Payload GamePoll gamePoll){
        Optional<GameStatModel> gameStatModelOptional =  gameStatRepository.findById(gamePoll.getGameId());

        if(gameStatModelOptional.isEmpty()) {
            log.error("Error in retrieving game update event for game Id {} : No game Id found "
                    , gamePoll.getGameId());
            return;
        }

        GameStatModel gameStat = gameStatModelOptional.get();

        if(gameStat.getStatus().equals("CLOSED")){
            log.error("Poll closed for the game {} " , gamePoll.getGameId());
            return;
        }
        countPollOpinion(gameStat, gamePoll);
        gameStatRepository.save(gameStat);
    }

    private void countPollOpinion(GameStatModel gameStat, GamePoll gamePoll) {
        if(gamePoll.getOpinion().equals("WIN")){
            gameStat.setWinCount(gameStat.getWinCount() + 1);
        }

        if(gamePoll.getOpinion().equals("LOSS")){
            gameStat.setLossCount(gameStat.getLossCount() + 1);
        }
    }
}
