CREATE TABLE IF NOT EXISTS GAME
                              (	"ID" VARCHAR2(30 BYTE) NOT NULL,
                           	"PRIZE_BOX" NUMBER ,
                           	"REVEAL_BOX" NUMBER ,
                           	"PICK_BOX" NUMBER ,
                           	"STATUS" VARCHAR2(10 BYTE) ,
                           	"CREATED_DATE" DATE,
                           	"STATUS_DATE" DATE
                              );