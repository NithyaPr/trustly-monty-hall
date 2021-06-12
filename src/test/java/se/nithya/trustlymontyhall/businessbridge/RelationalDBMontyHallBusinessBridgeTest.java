package se.nithya.trustlymontyhall.businessbridge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import se.nithya.trustlymontyhall.dto.Game;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@ContextConfiguration
class RelationalDBMontyHallBusinessBridgeTest {

    @InjectMocks
    private CacheMontyHallBusinessBridgeImpl montyHallBusinessBridge;

    //@Test
    public void should_startNewGame() {
        Game game = montyHallBusinessBridge.startGame();
        assertNotNull(game);
        assertEquals("124", game.getId());
        assertNotNull(game.getPrizeBox());
    }
}