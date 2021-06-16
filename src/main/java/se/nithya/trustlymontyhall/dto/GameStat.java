package se.nithya.trustlymontyhall.dto;

import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "Builder")
@Data
public class GameStat {

    private String id;

    private String winCount;

    private String lossCount;

    private String status;

    private String result;
}
