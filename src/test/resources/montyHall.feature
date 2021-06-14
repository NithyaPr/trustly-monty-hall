Feature: the game can be retrieved

  Scenario Outline: client makes call to GET /v1/game/start
    When the client calls /v1/game/start
    Then the new game stored in cache
    And the game should return 200
    Examples:
