package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.User;
import ru.itis.androidtechpractice.models.UserPhoto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPhotoDto {
    private Integer id;
    private String link;
    private Long dateOfCreation;
    private boolean isAvatar;
    private Integer userId;

    public static UserPhotoDto from(UserPhoto photo) {
        return UserPhotoDto.builder()
                .id(photo.getId())
                .link(photo.getLink())
                .dateOfCreation(photo.getDateOfCreation())
                .isAvatar(photo.isAvatar())
                .userId(photo.getUser().getId())
                .build();
    }

    public static List<UserPhotoDto> from(List<UserPhoto> photos) {
        return photos.stream()
                .map(UserPhotoDto::from)
                .collect(Collectors.toList());
    }
}
