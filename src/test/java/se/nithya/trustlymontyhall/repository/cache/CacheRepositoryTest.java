package se.nithya.trustlymontyhall.repository.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.nithya.trustlymontyhall.TrustlyMontyHallApplication;
import se.nithya.trustlymontyhall.dto.Game;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TrustlyMontyHallApplication.class)
@ContextConfiguration
@Slf4j
public class CacheRepositoryTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private CacheRepository cacheRepository;

    private Optional<Game> getGame(String gameId) {
        return Optional.ofNullable(cacheManager.getCache("games")).map(c -> c.get(gameId, Game.class));
    }

    @Test
    void should_getGame() {
        cacheRepository.startGame("123");
        assertTrue(getGame("123").isPresent());
    }

    @Test
    void should_updateGameWithPickBox() {
        cacheRepository.startGame("123");
        assertTrue(getGame("123").isPresent());
        cacheRepository.pickBox(getGame("123").get(), 1);
        assertTrue(getGame("123").get().getPickBox().equals(1));

    }

    @Test
    void should_removeGame() {
        cacheRepository.startGame("123");
        assertTrue(getGame("123").isPresent());
        cacheRepository.switchBox(getGame("123").get());
        assertTrue(getGame("123").isEmpty());

    }
}
