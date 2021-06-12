package se.nithya.trustlymontyhall.businessbridge;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.exception.MontyHallException;
import se.nithya.trustlymontyhall.repository.cache.CacheRepository;

import java.util.Optional;
import java.util.Random;

@Component
@Slf4j
@Qualifier("cacheMontyHall")
public class CacheMontyHallBusinessBridgeImpl implements  MontyHallBusinessBridge{

    private final CacheRepository cacheRepository;
    public CacheMontyHallBusinessBridgeImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public Game startGame() {
        String newGameId = Long.toString(RandomUtils.nextLong(1, 1000000), 4);
        return cacheRepository.startGame(newGameId).get();
    }

    public Game getGame(String gameId) {
        log.info("No game exists in cache for the Id {} ", gameId);
        Optional<Game> game = cacheRepository.getGame(gameId);
        if(game.isEmpty()) {
            throw new MontyHallException(HttpStatus.BAD_REQUEST,
                    String.format("Game finished/No Game exists for the id %s ", gameId));
        }
        return game.get();
    }

    public Game pickBox(String gameId, Integer pickBox) {
        Game game = getGame(gameId);
        game.setPickBox(pickBox);
        log.info("Game id {} Pick up box {} , Prize box {} ",
                game.getId(), pickBox, game.getPrizeBox());
        cacheRepository.pickBox(game, pickBox);
        return game;
    }

    public Game reveal(String gameId) {
        Game game = getGame(gameId);

        Random rand = new Random();

        Integer revealBox;
        do {
            revealBox = rand.nextInt(3);
        } while (revealBox.equals(game.getPrizeBox()) || revealBox.equals(game.getPickBox()));

        game.setRevealBox(revealBox);

        log.info("Game id {} Pick up box {} , Prize box {} , Revealed box {} ",
                game.getId(), game.getPickBox(), game.getPrizeBox(), revealBox);
        cacheRepository.reveal(game);

        return game;
    }

    public String switchBox(String gameId) {
        Game game = getGame(gameId);
        return cacheRepository.switchBox(game);
    }


    public String stayBox(String gameId) {
        Game game = getGame(gameId);

        return cacheRepository.stayBox(game);

    }
}
