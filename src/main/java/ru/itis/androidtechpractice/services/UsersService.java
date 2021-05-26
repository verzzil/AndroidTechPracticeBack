package ru.itis.androidtechpractice.services;

import ru.itis.androidtechpractice.dto.*;
import ru.itis.androidtechpractice.models.UserActProof;

import java.util.List;

public interface UsersService {
    TokenDto signIn(SignInForm form);
    UserDto signUp(SignUpForm form);
    void logout(String token);

    UserDto getUserById(Integer id);
    UserDto getUserByEmail(String email) throws Throwable;
    List<UserDto> getAllUsers();
    List<UserDto> getTopUsers();
    List<ActDto> findContinueUserActs(Integer userId);
    List<ActDto> findEndUserActs(Integer userId);
    List<ActDto> findActsByUserId(Integer userId);
    List<UserDto> getUserByLikeEmail(String email);

    void accrueReward(Integer userId, Float coef, Integer reward);
    void accrueReward(List<Integer> userIds, Float coef, Integer reward);
    void increaseSelfCoefficient(Integer userId, Float incr);

    void createAct(ru.itis.androidtechpractice.dto.UserActDto userActDto);
    void createActProof(UserActProofDto userActProofDto);
    void proofDecision(ModeratorDecisionDto moderatorDecisionDto) throws IllegalAccessException;

    void changeUserSettings(ChangeUserSettingsDto changeUserSettingsDto);

    List<ActProofDto> getModeratorActs(Integer moderatorId);

    List<UserDto> findAllUsersByEmailStartsWith(String query);

    List<ActProofDto> getUserApprovedProofs(Integer userId);
}
