package se.nithya.trustlymontyhall.businessbridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.exception.MontyHallException;
import se.nithya.trustlymontyhall.repository.cache.CacheRepository;
import se.nithya.trustlymontyhall.repository.db.GameStatRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Slf4j
class CacheMontyHallBusinessBridgeTest {

    @Mock
    CacheRepository cacheRepository;
    @Mock
    GameStatRepository gameStatRepository;

    @InjectMocks
    CacheMontyHallBusinessBridgeImpl cacheMontyHallBusinessBridge;

    @Test
    void should_startNewGame(){
        when(cacheRepository.startGame(any())).thenReturn(Optional.of(buildGame()));
        Game game = cacheMontyHallBusinessBridge.startGame();
        assertEquals("670456840", game.getId());
    }

    @Test
    void should_getGame(){
        when(cacheRepository.getGame(any())).thenReturn(Optional.of(buildGame()));
        Game game = cacheMontyHallBusinessBridge.getGame("670456840");
        assertEquals("670456840", game.getId());
    }

    @Test
    void should_game_notFound(){
        when(cacheRepository.getGame(any())).thenReturn(Optional.empty());
        MontyHallException exception = assertThrows(MontyHallException.class, () -> {
            cacheMontyHallBusinessBridge.getGame("670456840");
        });
        assertTrue(exception.getMessage().contains("Game finished/No Game exists for the id 670456840"));
    }

    @Test
    void should_setPickBox(){
        Game gameRes = buildGame();
        when(cacheRepository.getGame(any())).thenReturn(Optional.of(gameRes));
        cacheMontyHallBusinessBridge.pickBox("670456840",1);
        verify(cacheRepository).pickBox(gameRes,1);
    }

    @Test
    void should_setReveal(){
        Game gameRes = buildGame();
        gameRes.setPickBox(0);
        gameRes.setRevealBox(2);
        when(cacheRepository.getGame(any())).thenReturn(Optional.of(gameRes));
        cacheMontyHallBusinessBridge.reveal("670456840");
        verify(cacheRepository).reveal(gameRes);
    }

    @Test
    void should_switchBox(){
        Game gameRes = buildGame();
        gameRes.setPickBox(0);
        gameRes.setRevealBox(2);
        when(cacheRepository.getGame(any())).thenReturn(Optional.of(gameRes));
        when(cacheRepository.switchBox(any())).thenReturn("WIN");

        String result = cacheMontyHallBusinessBridge.switchBox("670456840");
        verify(cacheRepository).switchBox(gameRes);
        assertEquals("WIN", result);
    }

    @Test
    void should_stayBox(){
        Game gameRes = buildGame();
        gameRes.setPickBox(0);
        gameRes.setRevealBox(2);
        when(cacheRepository.getGame(any())).thenReturn(Optional.of(gameRes));
        when(cacheRepository.stayBox(any())).thenReturn("LOSS");

        String result = cacheMontyHallBusinessBridge.stayBox("670456840");
        verify(cacheRepository).stayBox(gameRes);
        assertEquals("LOSS", result);
    }

    /**
     * Test data
     *
     */

    private Game buildGame(){
        Game game = new Game();
        game.setId("670456840");
        game.setPrizeBox(1);
        return game;
    }

}