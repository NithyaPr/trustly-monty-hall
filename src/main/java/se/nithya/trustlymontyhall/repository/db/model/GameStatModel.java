package se.nithya.trustlymontyhall.repository.db.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "GAME_STAT")
public class GameStatModel {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "WIN_COUNT")
    private Integer winCount;

    @Column(name = "LOSS_COUNT")
    private Integer lossCount;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "STATUS_DATE")
    private LocalDateTime statusDate;

    @Column(name = "STATUS")
    private String status;
}
