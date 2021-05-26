package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeUserSettingsDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
    private List<SocialLinkDto> socialLinks;
    private String photoLink;
}
