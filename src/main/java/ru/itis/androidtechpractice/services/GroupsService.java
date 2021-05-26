package ru.itis.androidtechpractice.services;

import ru.itis.androidtechpractice.dto.GroupActDto;
import ru.itis.androidtechpractice.dto.GroupActProofDto;
import ru.itis.androidtechpractice.dto.GroupDto;
import ru.itis.androidtechpractice.dto.ModeratorDecisionDto;

public interface GroupsService {

    GroupDto findGroupById(Integer groupId);
    void createGroup(GroupDto groupDto);

    void createAct(GroupActDto act);

    void createProof(GroupActProofDto act);

    void proofDecision(ModeratorDecisionDto moderatorDecisionDto) throws IllegalAccessException;

}
