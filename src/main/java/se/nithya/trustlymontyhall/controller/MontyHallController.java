package se.nithya.trustlymontyhall.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.nithya.trustlymontyhall.businessbridge.MontyHallBusinessBridge;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.exception.MontyHallException;

import java.util.Optional;

@RestController
@Slf4j
public class MontyHallController {

    private final MontyHallBusinessBridge montyHallBusinessBridge;

    public MontyHallController(MontyHallBusinessBridge montyHallBusinessBridge) {
        this.montyHallBusinessBridge = montyHallBusinessBridge;
    }

    @PostMapping("/v1/startGame")
    public ResponseEntity startGame(@RequestHeader HttpHeaders headers) {
        String newGameId = Long.toString(RandomUtils.nextLong(1, 1000000), 4);

        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.startGame(newGameId).get());
    }

    @PutMapping("/v1/pickBox/{gameId}")
    public ResponseEntity pickBox(@RequestHeader HttpHeaders headers, @PathVariable(value = "gameId") String gameId,
                                  @RequestParam(value = "pickBox") Integer pickBox) {
        Optional<Game> game = montyHallBusinessBridge.getGame(gameId);
        if (game.isEmpty()) {
            throw new MontyHallException(HttpStatus.BAD_REQUEST,
                    String.format("Game finished/No Game exists for the id %s ", gameId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.pickBox(game.get(),pickBox));
    }

    @PutMapping("/v1/reveal/{gameId}")
    public ResponseEntity reveal(@RequestHeader HttpHeaders headers, @PathVariable(value = "gameId") String gameId) {
        Optional<Game> game = montyHallBusinessBridge.getGame(gameId);
        if (game.isEmpty()) {
            throw new MontyHallException(HttpStatus.BAD_REQUEST,
                    String.format("Game finished/No Game exists for the id %s ", gameId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.reveal(game.get()));
    }

    @PutMapping("/v1/switch/{gameId}")
    public ResponseEntity switchBox(@RequestHeader HttpHeaders headers, @PathVariable(value = "gameId") String gameId) {
        Optional<Game> game = montyHallBusinessBridge.getGame(gameId);
        if (game.isEmpty()) {
            throw new MontyHallException(HttpStatus.BAD_REQUEST,
                    String.format("Game finished/No Game exists for the id %s ", gameId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.switchBox(game.get()));
    }

    @PutMapping("/v1/stay/{gameId}")
    public ResponseEntity stayBox(@RequestHeader HttpHeaders headers, @PathVariable(value = "gameId") String gameId) {
        Optional<Game> game = montyHallBusinessBridge.getGame(gameId);
        if (game.isEmpty()) {
            throw new MontyHallException(HttpStatus.BAD_REQUEST,
                    String.format("Game finished/No Game exists for the id %s ", gameId));
        }
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.stayBox(game.get()));
    }

}
