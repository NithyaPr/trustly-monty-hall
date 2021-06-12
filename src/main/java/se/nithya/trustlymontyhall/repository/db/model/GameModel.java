package se.nithya.trustlymontyhall.repository.db.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;

@Data
@Entity(name = "GAME")
public class GameModel implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PICK_BOX")
    private Integer pickBox;

    @Column(name = "REVEAL_BOX")
    private Integer revealBox;

    @Column(name = "PRIZE_BOX")
    private Integer prizeBox;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "STATUS_DATE")
    private LocalDateTime statusDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "RESULT")
    private String result;
}
