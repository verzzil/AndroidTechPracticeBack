package ru.itis.androidtechpractice.repositories.my;

import ru.itis.androidtechpractice.dto.ActDto;
import ru.itis.androidtechpractice.dto.ActProofDto;

import java.util.List;

public interface UserActsMyRepository {
    List<ActDto> findAllUserActs(Integer userId);
    List<ActDto> findAllContinueUserActs(Integer userId);
    List<ActDto> findAllEndUserActs(Integer userId);

    List<ActProofDto> findAllApprovedActs(Integer userId);
}
