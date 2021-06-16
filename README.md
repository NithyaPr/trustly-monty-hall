# trustly-monty-hall

1. Clone the project from github
2. Import the project as maven project
3. Set java 11 in build path.
4. mvn clean install
5. Run TrustlyMontyHallApplication
6. After application started, use http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

The game can be started using,

1. v1/game/start endpoint. A unique id is generated and seen in the Id field in response
2. v1/game/{gameId}/pickup. User can select the box which will be picked up
3. v1/game/{gameId}/reveal. Host can be used to reveal the box
4. v1/game/{gameId}/switch and v1/game/{gameId}/stay can be used to switch or stay with the option

Tech stack:

Java 11
Spring boot microservices
H2 database
Liquibase
Rabbit Mq
Spring cache

Rabbit Mq:
Publisher and Listener.

Publisher pusblish message to Routing key. To consume message, a queue should be binded to routingkey rabbitmq.montyhall.publisher.routingkey for exchange x.montyhall

Monty hall problem - For more information https://en.wikipedia.org/wiki/Monty_Hall_problem
