package se.nithya.trustlymontyhall.businessbridge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.exception.MontyHallException;
import se.nithya.trustlymontyhall.repository.db.GameRepository;
import se.nithya.trustlymontyhall.repository.db.model.GameModel;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class RelationalDBMontyHallBusinessBridgeTest {
    @Mock
    GameRepository gameRepository;

    @InjectMocks
    RelationalDBMontyHallBusinessBridgeImpl montyHallBusinessBridge;

    @Test
    void should_startNewGame(){
        Game game = montyHallBusinessBridge.startGame();
        verify(gameRepository).save(any());
        assertNotNull(game.getId());
    }

    @Test
    void should_getGame(){
        when(gameRepository.findById(any())).thenReturn(Optional.empty());

        MontyHallException exception = assertThrows(MontyHallException.class,
                () -> montyHallBusinessBridge.getGame("564"));
        assertTrue(exception.getMessage().contains("Game finished/No Game exists for the id 564"));
    }


    @Test
    void should_setPickBox(){
        when(gameRepository.findById(any())).thenReturn(Optional.of(buildGameModel()));
        when(gameRepository.save(any())).thenReturn(buildGameModel());

        montyHallBusinessBridge.pickBox("670456840",1);
        verify(gameRepository).save(any());
    }

    @Test
    void should_setReveal(){
        when(gameRepository.findById(any())).thenReturn(Optional.of(buildGameModel()));
        when(gameRepository.save(any())).thenReturn(buildGameModel());

        montyHallBusinessBridge.reveal("234");
        verify(gameRepository).save(any());
    }

    @Test
    void should_switchBox(){
        when(gameRepository.findById(any())).thenReturn(Optional.of(buildGameModel()));

        montyHallBusinessBridge.switchBox("234");
        verify(gameRepository).save(any());
    }

    @Test
    void should_stayBox(){
        when(gameRepository.findById(any())).thenReturn(Optional.of(buildGameModel()));
        montyHallBusinessBridge.stayBox("234");
        verify(gameRepository).save(any());
    }

    /**
     * Test data
     *
     */

    private GameModel buildGameModel(){
        GameModel game = new GameModel();
        game.setId("670456840");
        game.setPrizeBox(1);
        game.setCreatedDate(LocalDateTime.now());
        game.setRevealBox(2);
        game.setPickBox(0);

        return game;
    }

}