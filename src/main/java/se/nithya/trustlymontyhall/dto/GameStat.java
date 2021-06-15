package se.nithya.trustlymontyhall.dto;

import lombok.Builder;

@Builder(builderClassName = "Builder")
public class GameStat {

    private String id;

    private String winCount;

    private String lossCount;

    private String status;

    private String result;
}
