package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ModeratorDecisionDto {
    private Integer moderatorId;
    private Integer proofId;
    private String decision;
    private Integer reward;
}
