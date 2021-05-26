package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private Long birthday;
    private Long cash;
    private Float selfCoefficient;
    private User.Role role;
    private String photoLink;
    private List<SocialLinkDto> socialLinks;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthday(user.getBirthday())
                .cash(user.getCash())
                .selfCoefficient(user.getSelfCoefficient())
                .role(user.getRole())
                .photoLink(user.getPhotoLink())
                .socialLinks(user.getSocialLinks() == null || user.getSocialLinks().isEmpty() ? new ArrayList() : SocialLinkDto.from(user.getSocialLinks()))
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
