package se.nithya.trustlymontyhall.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {
    private String id;
    private Integer prizeBox;
    private Integer revealBox;
    private Integer pickBox;

}
