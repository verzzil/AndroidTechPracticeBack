package ru.itis.androidtechpractice.models;

import lombok.*;
import ru.itis.androidtechpractice.dto.ActProofDto;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"groupAct"})
@EqualsAndHashCode
public class GroupActProof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long sendDate;
    private String photoLink;
    private String text;
    @Enumerated(value = EnumType.STRING)
    private ActProofDto.State state;
    private Integer moderatorId;
    private Double longitude;
    private Double latitude;

    @ManyToOne
    @JoinColumn(name = "group_act_id")
    private GroupAct groupAct;
}
