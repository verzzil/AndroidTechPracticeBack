package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.Group;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {
    private Integer id;
    private String title;
    private Date dateOfCreation;
    private List<Integer> usersIds;
    private List<GroupActDto> groupAct;
    private Integer idMainUser;

    public static GroupDto from(Group group) {
        return GroupDto.builder()
                .id(group.getId())
                .title(group.getTitle())
                .dateOfCreation(group.getDateOfCreation())
                .usersIds(
                        UserDto.from(group.getUsers()).stream()
                                .map(UserDto::getId)
                                .collect(Collectors.toList())
                )
                .groupAct(group.getGroupAct() != null ? GroupActDto.from(group.getGroupAct()) : null)
                .idMainUser(group.getMainUser().getId())
                .build();
    }
}
