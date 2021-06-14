package se.nithya.trustlymontyhall.cucumber.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.repository.cache.CacheRepository;

import java.util.Optional;

public class MontyHallSteps {

    @Autowired
    CacheRepository cacheRepository;

    @Autowired
    CacheManager cacheManager;

    @Given("the client calls /v1/game/start")
    public void startGame(){
        String newGameId = Long.toString(RandomUtils.nextLong(1, 1000000), 4);
        cacheRepository.startGame(newGameId);
    }

    @Then("the new game (\\d+) stored in cache")
    public void aNewGameShouldBeStoredInCache(String gameId) {
        Assert.assertTrue(Optional.ofNullable(cacheManager.getCache("games")).
                map(c -> c.get(gameId, Game.class)).isPresent());
    }

}
