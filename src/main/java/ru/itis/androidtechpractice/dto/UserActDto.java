package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.UserAct;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActDto {
    private Integer id;
    private String title;
    private Integer userId;
    private Integer reward;
    private Long dateOfCreation;
    private Long dateOfEnding;
    private ActDto.State actStatus;
    private List<UserActProofDto> proofs;

    public static UserActDto from(UserAct act) {
        return UserActDto.builder()
                .id(act.getId())
                .title(act.getTitle())
                .userId(act.getUser().getId())
                .reward(act.getReward())
                .dateOfCreation(act.getDateOfCreation())
                .dateOfEnding(act.getDateOfEnding())
                .actStatus(act.getActStatus())
                .build();
    }

    public static List<UserActDto> from(List<UserAct> acts) {
        return acts.stream()
                .map(UserActDto::from)
                .collect(Collectors.toList());
    }
}
