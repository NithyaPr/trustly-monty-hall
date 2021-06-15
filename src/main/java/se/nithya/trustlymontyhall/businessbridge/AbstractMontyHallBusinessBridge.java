package se.nithya.trustlymontyhall.businessbridge;

import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.repository.db.GameStatRepository;
import se.nithya.trustlymontyhall.repository.db.model.GameStatModel;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public abstract class AbstractMontyHallBusinessBridge {

    private final GameStatRepository gameStatRepository;

    protected AbstractMontyHallBusinessBridge(GameStatRepository gameStatRepository) {
        this.gameStatRepository = gameStatRepository;
    }

    protected void initializeGameStat(String newGameId) {
        gameStatRepository.save(buildGameStat(newGameId));
    }


    protected void updateGameStat(String gameId) {
        Optional<GameStatModel> gameStat = gameStatRepository.findById(gameId);

        if(gameStat.isEmpty()) {
            return;
        }
        GameStatModel game = gameStat.get();
        game.setStatus("CLOSED");
        game.setStatusDate(LocalDateTime.now());
        gameStatRepository.save(game);
    }


    private GameStatModel buildGameStat(String newGameId) {
        GameStatModel gameStatModel = new GameStatModel();
        gameStatModel.setId(newGameId);
        gameStatModel.setCreatedDate(LocalDateTime.now());
        gameStatModel.setLossCount(0);
        gameStatModel.setWinCount(0);
        gameStatModel.setStatus("OPEN");
        return gameStatModel;
    }
}
