package se.nithya.trustlymontyhall.businessbridge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.Game;

import java.util.Optional;
import java.util.Random;

public interface MontyHallBusinessBridge {

    Game startGame();
    Game getGame(String gameId);
    Game pickBox(String gameId, Integer pickBox);
    Game reveal(String gameId);
    String switchBox(String gameId);
    String stayBox(String gameId);

}
