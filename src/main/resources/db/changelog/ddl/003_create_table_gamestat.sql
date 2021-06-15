CREATE TABLE IF NOT EXISTS GAME_STAT
                              (	"ID" VARCHAR2(30 BYTE) NOT NULL,
                           	"WIN_COUNT" NUMBER ,
                           	"LOSS_COUNT" NUMBER ,
                           	"STATUS" VARCHAR2(10 BYTE) ,
                           	"CREATED_DATE" DATE,
                           	"STATUS_DATE" DATE
                              );