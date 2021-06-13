package se.nithya.trustlymontyhall.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public MontyHallController( @Qualifier("dbMontyHall")  MontyHallBusinessBridge montyHallBusinessBridge) {
        this.montyHallBusinessBridge = montyHallBusinessBridge;
    }

    @PostMapping("/v1/game/start")
    public ResponseEntity startGame() {
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.startGame());
    }

    @PutMapping("/v1/game/pickup/{gameId}")
    public ResponseEntity pickBox(@PathVariable(value = "gameId") String gameId,
                                  @RequestParam(value = "pickBox") Integer pickBox) {
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.pickBox(gameId,pickBox));
    }

    @PutMapping("/v1/game/reveal/{gameId}")
    public ResponseEntity reveal(@PathVariable(value = "gameId") String gameId) {

        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.reveal(gameId));
    }

    @PutMapping("/v1/game/switch/{gameId}")
    public ResponseEntity switchBox(@PathVariable(value = "gameId") String gameId) {
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.switchBox(gameId));
    }

    @PutMapping("/v1/game/stay/{gameId}")
    public ResponseEntity stayBox(@PathVariable(value = "gameId") String gameId) {

        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.stayBox(gameId));
    }

}
