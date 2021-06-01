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
class MontyHallBusinessBridgeTest {

    @InjectMocks
    private MontyHallBusinessBridge montyHallBusinessBridge;

    @Test
    public void should_startNewGame() {
        Optional<Game> game = montyHallBusinessBridge.startGame("124");
        assertTrue(game.isPresent());
        assertEquals("124", game.get().getId());
        assertNotNull(game.get().getPrizeBox());
    }
}