package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.GroupActProof;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupActProofDto {
    private Integer id;
    private Long sendDate;
    private Integer groupActId;
    private String photoLink;
    private String text;
    private Integer moderatorId;
    private Double latitude;
    private Double longitude;


    public static GroupActProofDto from(GroupActProof proof) {
        return GroupActProofDto.builder()
                .id(proof.getId())
                .sendDate(proof.getSendDate())
                .groupActId(proof.getGroupAct().getId())
                .moderatorId(proof.getModeratorId())
                .longitude(proof.getLongitude())
                .latitude(proof.getLatitude())
                .build();
    }

    public static List<GroupActProofDto> from(List<GroupActProof> proofs) {
        return proofs.stream()
                .map(GroupActProofDto::from)
                .collect(Collectors.toList());
    }
}
