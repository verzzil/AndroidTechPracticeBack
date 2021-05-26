package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.GroupAct;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupActDto {
    private Integer id;
    private String title;
    private Integer groupId;
    private Integer reward;
    private Long dateOfCreation;
    private Long dateOfEnding;
    private ActDto.State actStatus;
    private List<GroupActProofDto> proofs;

    public static GroupActDto from(GroupAct act) {
        return GroupActDto.builder()
                .id(act.getId())
                .groupId(act.getGroup().getId())
                .title(act.getTitle())
                .reward(act.getReward())
                .dateOfCreation(act.getDateOfCreation())
                .dateOfEnding(act.getDateOfEnding())
                .actStatus(act.getActStatus())
                .proofs(GroupActProofDto.from(act.getProofs()))
                .build();
    }

    public static List<GroupActDto> from(List<GroupAct> acts) {
        return acts.stream()
                .map(GroupActDto::from)
                .collect(Collectors.toList());
    }
}
