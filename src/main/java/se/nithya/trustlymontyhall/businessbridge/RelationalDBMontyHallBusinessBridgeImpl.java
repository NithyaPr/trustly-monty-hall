package se.nithya.trustlymontyhall.businessbridge;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.exception.MontyHallException;
import se.nithya.trustlymontyhall.repository.db.GameRepository;
import se.nithya.trustlymontyhall.repository.db.GameStatRepository;
import se.nithya.trustlymontyhall.repository.db.model.GameModel;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Component
@Slf4j
@Qualifier("dbMontyHall")
public class RelationalDBMontyHallBusinessBridgeImpl extends AbstractMontyHallBusinessBridge
        implements MontyHallBusinessBridge{

    private final GameRepository gameRepository;

    public RelationalDBMontyHallBusinessBridgeImpl(GameRepository gameRepository,
                                                   GameStatRepository gameStatRepository) {
        super(gameStatRepository);
        this.gameRepository = gameRepository;
    }

    @Override
    public Game startGame() {
        String newGameId = Long.toString(RandomUtils.nextLong(1, 1000000), 4);

        GameModel game = new GameModel();
        Random rand = new Random();
        Integer prizeDoor = rand.nextInt(3);
        game.setId(newGameId);
        game.setPrizeBox(prizeDoor);
        game.setCreatedDate(LocalDateTime.now());
        GameModel gameUpdated = gameRepository.save(game);
        log.info("gameUpdated {} " , gameUpdated);
        initializeGameStat(newGameId);

        return getGameDTO(game);
    }

    @Override
    public Game getGame(String gameId) {
        GameModel game = getGameModel(gameId);
        return getGameDTO(game);
    }

    private GameModel getGameModel(String gameId) {
        Optional<GameModel> game = gameRepository.findById(gameId);
        if(game.isEmpty() || "DONE".equals(game.get().getStatus())) {
            throw new MontyHallException(HttpStatus.BAD_REQUEST,
                    String.format("Game finished/No Game exists for the id %s ", gameId));
        }

        return game.get();
    }

    @Override
    public Game pickBox(String gameId, Integer pickBox) {
        GameModel game = getGameModel(gameId);
        game.setPickBox(pickBox);
        log.info("Game id {} Pick up box {} , Prize box {} ",
                game.getId(), pickBox, game.getPrizeBox());
        game = gameRepository.save(game);
        return getGameDTO(game);
    }

    @Override
    public Game reveal(String gameId) {

        GameModel game = getGameModel(gameId);

        Random rand = new Random();

        Integer revealBox;
        do {
            revealBox = rand.nextInt(3);
        } while (revealBox.equals(game.getPrizeBox()) || revealBox.equals(game.getPickBox()));

        game.setRevealBox(revealBox);

        log.info("Game id {} Pick up box {} , Prize box {} , Revealed box {} ",
                game.getId(), game.getPickBox(), game.getPrizeBox(), revealBox);
        game = gameRepository.save(game);
        return getGameDTO(game);
    }

    @Override
    public String switchBox(String gameId) {

        GameModel game = getGameModel(gameId);
        game.setStatus("DONE");
        game.setStatusDate(LocalDateTime.now());
        String result = "LOSS";
        if (!game.getPrizeBox().equals(game.getPickBox())) {
            result =  "WIN";
        }

        game.setResult(result);
        gameRepository.save(game);
        updateGameStat(gameId);

        return result;
    }

    @Override
    public String stayBox(String gameId) {
        GameModel game = getGameModel(gameId);
        game.setStatus("DONE");
        game.setStatusDate(LocalDateTime.now());
        String result = "WIN";

        if (game.getPrizeBox().equals(game.getPickBox())) {
            result =  "WIN";
        }
        game.setResult(result);
        gameRepository.save(game);
        updateGameStat(gameId);

        return result;
    }

    private Game getGameDTO(GameModel gameModel){
        Game gameRes = new Game();
        gameRes.setId(gameModel.getId());
        gameRes.setPrizeBox(gameModel.getPrizeBox());
        gameRes.setPrizeBox(gameModel.getPrizeBox());
        gameRes.setRevealBox(gameModel.getRevealBox());
        return gameRes;
    }
}
