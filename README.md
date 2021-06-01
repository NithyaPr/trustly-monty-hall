# trustly-monty-hall

1. Clone the project from github
2. Import the project as maven project
3. Set java 11 in build path.
4. mvn clean install
5. Run TrustlyMontyHallApplication
6. After application started, use http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

The game can be started using,

1. v1/startGame endpoint. A unique id is generated and seen in the Id field in response
2. v1/pickBox/{gameId}. User can select the box which will be picked up
3. v1/reveal/{gameId}. Host can be used to reveal the box
4. v1/switch/{gameId} and v1/stay/{gameId} can be used to switch or stay with the option


Monty hall problem - For more information https://en.wikipedia.org/wiki/Monty_Hall_problem
