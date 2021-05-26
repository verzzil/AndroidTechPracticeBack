package ru.itis.androidtechpractice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.dto.ActProofDto;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserActProof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long sendDate;
    @Enumerated(value = EnumType.STRING)
    private ActProofDto.State state;
    private String photoLink;
    private String text;
    private Integer moderatorId;
    private Double longitude;
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "user_act_id")
    private UserAct userAct;


}
