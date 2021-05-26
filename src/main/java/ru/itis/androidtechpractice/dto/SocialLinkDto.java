package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.SocialLink;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinkDto {
    private Integer id;
    private Integer userId;
    private String titleOfSocialRecourse;
    private String socialLink;

    public static SocialLinkDto from(SocialLink link) {
        return SocialLinkDto.builder()
                .id(link.getId())
                .userId(link.getUser().getId())
                .titleOfSocialRecourse(link.getTitleOfSocialRecourse())
                .socialLink(link.getSocialLink())
                .build();
    }

    public static List<SocialLinkDto> from(List<SocialLink> links) {
        return links.stream()
                .map(SocialLinkDto::from)
                .collect(Collectors.toList());
    }
}
