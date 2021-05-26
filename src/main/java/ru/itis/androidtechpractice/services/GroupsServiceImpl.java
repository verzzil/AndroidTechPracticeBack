package ru.itis.androidtechpractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.androidtechpractice.dto.*;
import ru.itis.androidtechpractice.models.*;
import ru.itis.androidtechpractice.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private GroupActsRepository groupsActsRepository;

    @Autowired
    private GroupActProofsRepository groupsActsProofsRepository;

    @Autowired
    private CoefficientBetweenUsersRepository coefficientBetweenUsersRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ChatsService chatsService;

    @Override
    public GroupDto findGroupById(Integer groupId) {
        return GroupDto.from(groupsRepository.findById(groupId).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void createGroup(GroupDto groupDto) {
        Group group = getGroup(groupDto);

        chatsService.createChat(group);
        Group newGroup = groupsRepository.save(group);
        createAct(GroupActDto.builder()
                .title(group.getTitle())
                .groupId(newGroup.getId())
                .build());

    }

    @Override
    public void createAct(GroupActDto act) {
        GroupAct newAct = getGroupAct(act);

        groupsActsRepository.save(newAct);
    }

    @Override
    public void createProof(GroupActProofDto act) {
        GroupActProof newProof = getGroupActProof(act);

        groupsActsProofsRepository.save(newProof);

        // TODO сделать отправку сообщения в группу о том, что их акт рассматривается
    }

    @Override
    public void proofDecision(ModeratorDecisionDto moderatorDecisionDto) throws IllegalAccessException {
        GroupActProof proof = groupsActsProofsRepository.findById(moderatorDecisionDto.getProofId()).orElseThrow(IllegalArgumentException::new);

        if (proof.getModeratorId().equals(moderatorDecisionDto.getModeratorId())) {
            setProofState(moderatorDecisionDto, proof);
        } else {
            throw new IllegalAccessException("Вы не отвественны за этот акт");
        }

        groupsActsProofsRepository.save(proof);

        /// TODO: Сделать отправку сообщения о решении модератора/админа в чат группы

    }

    private void setProofState(ModeratorDecisionDto moderatorDecisionDto, GroupActProof proof) {
        switch (moderatorDecisionDto.getDecision()) {
            case "APPROVED":
                proof.setState(ActProofDto.State.APPROVED);
                proof.getGroupAct().setReward(moderatorDecisionDto.getReward());
                proof.getGroupAct().setActStatus(ActDto.State.END);
                groupsActsRepository.save(proof.getGroupAct());

                accrueReward(proof.getGroupAct());
                break;
            case "DECLINED":
                proof.setState(ActProofDto.State.DECLINED);
                break;
        }
    }

    private Group getGroup(GroupDto groupDto) {
        List<User> users = findUsersById(groupDto.getUsersIds());

        createUsersCoefficients(users);

        return Group.builder()
                .mainUser(findUserById(groupDto.getIdMainUser()))
                .title(groupDto.getTitle())
                .users(users)
                .build();
    }

    private void createUsersCoefficients(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                Optional<CoefficientBetweenUsers> coef = coefficientBetweenUsersRepository.getCoeffsBetweenUsers(users.get(i).getId(), users.get(j).getId());
                if (coef.isPresent()) {
                    coef.get().setCoefficient(coef.get().getCoefficient() + 0.1f);
                    coefficientBetweenUsersRepository.save(coef.get());
                } else {
                    CoefficientBetweenUsers newCoef = CoefficientBetweenUsers.builder()
                            .firstUser(users.get(i))
                            .secondUser(users.get(j))
                            .relationType(CoefficientBetweenUsers.RelationType.ACQUAINTANCES)
                            .coefficient(1.2f)
                            .build();
                    coefficientBetweenUsersRepository.save(newCoef);
                }
            }
        }
    }

    private GroupAct getGroupAct(GroupActDto act) {
        Group group = groupsRepository.findById(act.getGroupId()).orElseThrow(IllegalArgumentException::new);
        GroupAct newAct = GroupAct.builder()
                .title(act.getTitle())
                .actStatus(ActDto.State.CONTINUE)
                .dateOfCreation(System.currentTimeMillis())
                .group(group)
                .build();
        return newAct;
    }

    private GroupActProof getGroupActProof(GroupActProofDto act) {
        GroupAct groupAct = groupsActsRepository.findById(act.getGroupActId()).orElseThrow(IllegalArgumentException::new);

        GroupActProof newProof = GroupActProof.builder()
                .groupAct(groupAct)
                .photoLink(act.getPhotoLink())
                .text(act.getText())
                .sendDate(System.currentTimeMillis())
                .latitude(act.getLatitude())
                .longitude(act.getLongitude())
                .state(ActProofDto.State.CONSIDERATION)
                .moderatorId(getRandomAdminOrModeratorId())
                .build();
        return newProof;
    }

    private List<User> findUsersById(List<Integer> usersIds) {
        List<User> users = new ArrayList<>();
        for (Integer userId : usersIds) {
            users.add(
                    findUserById(userId)
            );
        }
        return users;
    }

    private User findUserById(Integer userId) {
        return usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    private Float getGroupCoef(Group group) {
        List<User> users = group.getUsers();
        List<Float> coeffs = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                coeffs.add(
                        coefficientBetweenUsersRepository.getCoefficientBetweenUsersByIds(users.get(i).getId(), users.get(j).getId())
                );
            }
        }

        Float result = 0f;

        for (Float coef : coeffs) {
            result += coef;
        }

        return result / coeffs.size();
    }

    private Integer getRandomAdminOrModeratorId() {
        List<Integer> ids = usersRepository.findModeratorsAndAdminsIds();
        return ids.get(new Random().nextInt(ids.size()));
    }

    private void accrueReward(GroupAct groupAct) {
        List<Integer> groupUsersIds = groupAct.getGroup().getUsers().stream().map(User::getId).collect(Collectors.toList());
        Float coeff = getGroupCoef(groupAct.getGroup());

        usersService.accrueReward(groupUsersIds, coeff, groupAct.getReward());
    }

}
