package se.nithya.trustlymontyhall.repository.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.Game;

import java.util.Optional;
import java.util.Random;

@Component
@Slf4j
public class CacheRepository {

    @Cacheable(value = "games", key = "#p0")
    public Optional<Game> startGame(String gameId) {
        log.info("New game started with ID: {} ", gameId);
        Random rand = new Random();
        Integer prizeDoor = rand.nextInt(3);
        Game game = new Game();
        game.setId(gameId);
        game.setPrizeBox(prizeDoor);
        return Optional.of(game);
    }

    @Cacheable(value = "games", key = "#p0")
    public Optional<Game> getGame(String gameId) {
        log.info("No game exists in cache for the Id {} ", gameId);
        return Optional.empty();
    }

    @CachePut(value = "games", key = "#game.id")
    public Game pickBox(Game game, Integer pickBox) {
        game.setPickBox(pickBox);
        log.info("Game id {} Pick up box {} , Prize box {} ",
                game.getId(), pickBox, game.getPrizeBox());

        return game;
    }

    @CachePut(value = "games", key = "#game.id")
    public Game reveal(Game game) {
        return game;
    }
    @Caching(evict = {
            @CacheEvict(value = "games", key = "#game.id")})
    public String switchBox(Game game) {
        if (!game.getPrizeBox().equals(game.getPickBox())) {
            return "WIN";
        }
        return "LOSS";
    }

    @Caching(evict = {
            @CacheEvict(value = "games", key = "#game.id")})
    public String stayBox(Game game) {

        if (game.getPrizeBox().equals(game.getPickBox())) {
            return "WIN";
        }
        return "LOSS";
    }

}
