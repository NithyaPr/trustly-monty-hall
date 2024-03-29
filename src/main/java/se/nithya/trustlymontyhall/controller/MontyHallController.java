package se.nithya.trustlymontyhall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.nithya.trustlymontyhall.businessbridge.EventBusinessBridge;
import se.nithya.trustlymontyhall.businessbridge.MontyHallBusinessBridge;
import se.nithya.trustlymontyhall.dto.Game;
import se.nithya.trustlymontyhall.rabbit.EventPublisher;


@RestController
@Slf4j
public class MontyHallController {

    private final MontyHallBusinessBridge montyHallBusinessBridge;

    private final EventBusinessBridge eventBusinessBridge;

    public MontyHallController(@Qualifier("dbMontyHall") MontyHallBusinessBridge montyHallBusinessBridge,
                               EventBusinessBridge eventBusinessBridge) {
        this.montyHallBusinessBridge = montyHallBusinessBridge;
        this.eventBusinessBridge = eventBusinessBridge;
    }

    @PostMapping("/v1/game/start")
    public ResponseEntity<Game> startGame() {
        Game game = montyHallBusinessBridge.startGame();
        eventBusinessBridge.sendMessage(game.getId(), "OPEN", null);
        return ResponseEntity.status(HttpStatus.OK).body(game);
    }

    @PutMapping("/v1/game/{gameId}/pickup")
    public ResponseEntity<Game> pickBox(@PathVariable(value = "gameId") String gameId,
                                  @RequestParam(value = "pickBox") Integer pickBox) {
        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.pickBox(gameId,pickBox));
    }

    @PutMapping("/v1/game/{gameId}/reveal")
    public ResponseEntity<Game> reveal(@PathVariable(value = "gameId") String gameId) {

        return ResponseEntity.status(HttpStatus.OK).body(montyHallBusinessBridge.reveal(gameId));
    }

    @PutMapping("/v1/game/{gameId}/switch")
    public ResponseEntity<String> switchBox(@PathVariable(value = "gameId") String gameId) {
        String result = montyHallBusinessBridge.switchBox(gameId);
        eventBusinessBridge.sendMessage(gameId, "CLOSED", result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/v1/game/{gameId}/stay")
    public ResponseEntity<String> stayBox(@PathVariable(value = "gameId") String gameId) {
        String result = montyHallBusinessBridge.stayBox(gameId);
        eventBusinessBridge.sendMessage(gameId, "CLOSED", result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
