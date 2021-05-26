package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.GroupAct;
import ru.itis.androidtechpractice.models.UserAct;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActDto {
    private Integer id;
    private String title;
    private Integer reward;
    private Long dateOfCreation;
    private Long dateOfEnding;
    private State actStatus;
    private Integer foreignId;
    private Type type;

    public static ActDto fromUserAct(UserAct userAct) {
        return ActDto.builder()
                .type(Type.USER)
                .actStatus(userAct.getActStatus())
                .title(userAct.getTitle())
                .id(userAct.getId())
                .foreignId(userAct.getUser().getId())
                .reward(userAct.getReward())
                .dateOfCreation(userAct.getDateOfCreation())
                .dateOfEnding(userAct.getDateOfEnding())
                .build();
    }

    public static ActDto fromGroupAct(GroupAct groupAct) {
        return ActDto.builder()
                .type(Type.GROUP)
                .actStatus(groupAct.getActStatus())
                .title(groupAct.getTitle())
                .id(groupAct.getId())
                .reward(groupAct.getReward())
                .foreignId(groupAct.getGroup().getId())
                .dateOfCreation(groupAct.getDateOfCreation())
                .dateOfEnding(groupAct.getDateOfEnding())
                .build();
    }

    public static List<ActDto> fromUserAct(List<UserAct> userAct) {
        return userAct.stream()
                .map(ActDto::fromUserAct)
                .collect(Collectors.toList());
    }

    public static List<ActDto> fromGroupAct(List<GroupAct> groupAct) {
        return groupAct.stream()
                .map(ActDto::fromGroupAct)
                .collect(Collectors.toList());
    }

    public enum State {
        END, CONTINUE
    }

    public enum Type {
        USER, GROUP
    }
}
