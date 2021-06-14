package se.nithya.trustlymontyhall.businessbridge;

import se.nithya.trustlymontyhall.dto.Game;

public interface MontyHallBusinessBridge {

    Game startGame();
    Game getGame(String gameId);
    Game pickBox(String gameId, Integer pickBox);
    Game reveal(String gameId);
    String switchBox(String gameId);
    String stayBox(String gameId);

}
