package ru.itis.androidtechpractice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.dto.ActDto;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GroupAct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer reward;
    private Long dateOfCreation;
    private Long dateOfEnding;
    @Enumerated(value = EnumType.STRING)
    private ActDto.State actStatus;


    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @OneToMany(mappedBy = "groupAct")
    private List<GroupActProof> proofs;


}
