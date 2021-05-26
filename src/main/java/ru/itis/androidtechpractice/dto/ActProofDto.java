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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActProofDto {
    private Integer id;
    private Long sendDate;
    private String photoLink;
    private String text;
    private State state;
    private Integer moderatorId;
    private Integer actId;
    private ProofType type;
    private Double longitude;
    private Double latitude;

    public static ActProofDto fromUserAct(UserActProof userActProof) {
        return ActProofDto.builder()
                .sendDate(userActProof.getSendDate())
                .photoLink(userActProof.getPhotoLink())
                .text(userActProof.getText())
                .state(userActProof.getState())
                .id(userActProof.getId())
                .moderatorId(userActProof.getModeratorId())
                .actId(userActProof.getId())
                .latitude(userActProof.getLatitude())
                .longitude(userActProof.getLongitude())
                .type(ProofType.USER)
                .build();
    }

    public static ActProofDto fromGroupAct(GroupActProof groupActProof) {
        return ActProofDto.builder()
                .sendDate(groupActProof.getSendDate())
                .photoLink(groupActProof.getPhotoLink())
                .text(groupActProof.getText())
                .id(groupActProof.getId())
                .state(groupActProof.getState())
                .moderatorId(groupActProof.getModeratorId())
                .actId(groupActProof.getId())
                .latitude(groupActProof.getLatitude())
                .longitude(groupActProof.getLongitude())
                .type(ProofType.GROUP)
                .build();
    }

    public static List<ActProofDto> fromUserAct(List<UserActProof> userActProofs) {
        return userActProofs.stream()
                .map(ActProofDto::fromUserAct)
                .collect(Collectors.toList());
    }

    public static List<ActProofDto> fromGroupAct(List<GroupActProof> groupActProofs) {
        return groupActProofs.stream()
                .map(ActProofDto::fromGroupAct)
                .collect(Collectors.toList());
    }

    public enum State {
        CONSIDERATION, APPROVED, DECLINED
    }

    public enum ProofType {
        USER, GROUP
    }
}
