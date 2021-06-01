package se.nithya.trustlymontyhall.businessbridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.nithya.trustlymontyhall.TrustlyMontyHallApplication;
import se.nithya.trustlymontyhall.dto.Game;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TrustlyMontyHallApplication.class)

@Slf4j
class CacheMontyHallBusinessBridgeTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private MontyHallBusinessBridge montyHallBusinessBridge;

    private Optional<Game> getGame(String gameId) {
        return Optional.ofNullable(cacheManager.getCache("games")).map(c -> c.get(gameId, Game.class));
    }

    @Test
    void should_getGame() {
        montyHallBusinessBridge.startGame("123");
        assertTrue(getGame("123").isPresent());
    }

}