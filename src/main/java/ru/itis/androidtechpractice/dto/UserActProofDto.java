package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.GroupActProof;
import ru.itis.androidtechpractice.models.UserActProof;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserActProofDto {
    private Integer id;
    private Long sendDate;
    private Integer userActId;
    private Integer moderatorId;
    private String photoLink;
    private String text;
    private Double latitude;
    private Double longitude;

    public static UserActProofDto from(UserActProof proof) {
        return UserActProofDto.builder()
                .id(proof.getId())
                .sendDate(proof.getSendDate())
                .userActId(proof.getUserAct().getId())
                .moderatorId(proof.getModeratorId())
                .build();
    }

    public static List<GroupActProofDto> from(List<GroupActProof> proofs) {
        return proofs.stream()
                .map(GroupActProofDto::from)
                .collect(Collectors.toList());
    }
}
