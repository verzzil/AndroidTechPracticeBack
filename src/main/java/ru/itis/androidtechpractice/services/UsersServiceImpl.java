package ru.itis.androidtechpractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.androidtechpractice.dto.*;
import ru.itis.androidtechpractice.models.*;
import ru.itis.androidtechpractice.repositories.*;
import ru.itis.androidtechpractice.repositories.my.UserActsMyRepository;

import java.util.*;
import java.util.function.Supplier;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserActsRepository userActsRepository;

    @Autowired
    private UserActProofsRepository userActProofsRepository;

    @Autowired
    private GroupActProofsRepository groupActProofsRepository;

    @Autowired
    private SocialLinksRepository socialLinksRepository;

    @Autowired
    private UserActsMyRepository userActsMyRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDto signIn(SignInForm form) {
        Optional<User> user = usersRepository.findUserByEmail(form.getEmail());
        if (user.isPresent())
            if (passwordEncoder.matches(form.getPassword(), user.get().getHashPassword())) {
                String tokenValue = UUID.randomUUID().toString();
                Token token = Token.builder()
                        .token(tokenValue)
                        .user(user.get())
                        .build();
                return tokenService.save(token);
            }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserDto signUp(SignUpForm form) {
        User user = User.builder()
                .birthday(0L)
                .cash(0L)
                .email(form.getEmail())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .dateOfRegistration(System.currentTimeMillis())
                .role(User.Role.USER)
                .selfCoefficient(1f)
                .build();
        return UserDto.from(usersRepository.save(user));
    }

    @Override
    public void logout(String token) {
        tokenService.logout(token);
    }

    @Override
    public UserDto getUserById(Integer id) {
        return UserDto.from(
                usersRepository
                        .findById(id)
                        .orElse(User.builder().build())
        );
    }

    @Override
    public UserDto getUserByEmail(String email) throws Throwable {
        return UserDto.from(
                usersRepository.findUserByEmail(email)
                        .orElseThrow(
                                (Supplier<Throwable>) () -> new UsernameNotFoundException("User not found")
                        )
        );
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public List<UserDto> getTopUsers() {
        return UserDto.from(
                usersRepository.findAllByOrderByCashDesc()
        );
    }

    @Override
    public void accrueReward(Integer userId, Float coef, Integer reward) {
        accrue(coef, reward, userId);
    }

    @Override
    public void accrueReward(List<Integer> usersIds, Float coef, Integer reward) {
        for (Integer userId : usersIds) {
            accrue(coef, reward, userId);
        }
    }

    @Override
    public void increaseSelfCoefficient(Integer userId, Float incr) {
        User user = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setSelfCoefficient(
                user.getSelfCoefficient() + incr
        );
        usersRepository.save(user);
    }

    @Override
    public void createAct(ru.itis.androidtechpractice.dto.UserActDto userActDto) {
        UserAct newAct = getUserAct(userActDto);

        userActsRepository.save(newAct);

    }

    @Override
    public void createActProof(UserActProofDto userActProofDto) {
        UserActProof newProof = getProof(userActProofDto);

        userActProofsRepository.save(newProof);

        // TODO добавить везде даты

    }

    @Override
    public void proofDecision(ModeratorDecisionDto moderatorDecisionDto) throws IllegalAccessException {

        UserActProof proof = userActProofsRepository.findById(moderatorDecisionDto.getProofId()).orElseThrow(IllegalArgumentException::new);

        if (moderatorDecisionDto.getModeratorId().equals(proof.getModeratorId())) {

            switch (moderatorDecisionDto.getDecision()) {
                case "APPROVED":
                    proof.setState(ActProofDto.State.APPROVED);
                    proof.getUserAct().setActStatus(ActDto.State.END);
                    proof.getUserAct().setReward(moderatorDecisionDto.getReward());
                    userActsRepository.save(proof.getUserAct());

                    User currentUser = proof.getUserAct().getUser();

                    accrue(moderatorDecisionDto.getReward(), currentUser.getId());
                    break;
                case "DECLINED":
                    proof.setState(ActProofDto.State.DECLINED);
                    break;
            }
        } else {
            throw new IllegalAccessException("Вы не ответственный за этот акт");
        }

        userActProofsRepository.save(proof);

    }

    @Override
    public void changeUserSettings(ChangeUserSettingsDto settingsDto) {
        User user = usersRepository.findById(settingsDto.getId()).orElseThrow(IllegalArgumentException::new);

        System.out.println(settingsDto);

        if (settingsDto.getOldPassword() != null) {
            if (passwordEncoder.matches(settingsDto.getOldPassword(), user.getHashPassword()))  {
                user.setHashPassword(passwordEncoder.encode(settingsDto.getNewPassword()));
            } else {
                return;
            }
        }
        if (settingsDto.getFirstName() != null) user.setFirstName(settingsDto.getFirstName());
        if (settingsDto.getLastName() != null) user.setLastName(settingsDto.getLastName());
        if (settingsDto.getSocialLinks() != null) setSocialLinks(settingsDto, user);
        if (settingsDto.getPhotoLink() != null) user.setPhotoLink(settingsDto.getPhotoLink());

        usersRepository.save(user);

    }

    @Override
    public List<ActProofDto> getModeratorActs(Integer moderatorId) {
        List<ActProofDto> allActs = ActProofDto.fromUserAct(userActProofsRepository.findAllByModeratorId(moderatorId));
        allActs.addAll(ActProofDto.fromGroupAct(groupActProofsRepository.findAllByModeratorId(moderatorId)));

        return allActs;
    }

    @Override
    public List<UserDto> findAllUsersByEmailStartsWith(String query) {
        return UserDto.from(usersRepository.findAllByEmailStartsWith(query));
    }

    @Override
    public List<ActProofDto> getUserApprovedProofs(Integer userId) {
        return userActsMyRepository.findAllApprovedActs(userId);
    }

    @Override
    public void saveFirebaseToken(Integer userId, String firebaseToken) {
        User user = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setFirebaseToken(firebaseToken);
        usersRepository.save(user);
    }

    @Override
    public List<ActDto> findContinueUserActs(Integer userId) {
        return userActsMyRepository.findAllContinueUserActs(userId);
    }

    @Override
    public List<ActDto> findEndUserActs(Integer userId) {
        return userActsMyRepository.findAllEndUserActs(userId);
    }

    @Override
    public List<ActDto> findActsByUserId(Integer userId) {
        return userActsMyRepository.findAllUserActs(userId);
    }

    @Override
    public List<UserDto> getUserByLikeEmail(String email) {
        return UserDto.from(usersRepository.findAllByEmailStartsWith(email));
    }

    private void setSocialLinks(ChangeUserSettingsDto settingsDto, User user) {
        for (SocialLinkDto socialLinkDto : settingsDto.getSocialLinks()) {
            SocialLink socialLink = SocialLink.builder()
                    .socialLink(socialLinkDto.getSocialLink())
                    .titleOfSocialRecourse(socialLinkDto.getTitleOfSocialRecourse())
                    .user(user)
                    .build();
            user.getSocialLinks().add(socialLink);
            socialLinksRepository.save(socialLink);
        }
    }


    private UserAct getUserAct(ru.itis.androidtechpractice.dto.UserActDto userActDto) {
        User currentUser = usersRepository.findById(userActDto.getUserId()).orElseThrow(IllegalArgumentException::new);
        UserAct newAct = UserAct.builder()
                .title(userActDto.getTitle())
                .user(currentUser)
                .dateOfCreation(System.currentTimeMillis())
                .actStatus(ActDto.State.CONTINUE)
                .build();
        return newAct;
    }

    private UserActProof getProof(UserActProofDto userActProofDto) {
        UserAct currentUserAct = userActsRepository.findById(userActProofDto.getUserActId()).orElseThrow(IllegalArgumentException::new);
        UserActProof newProof = UserActProof.builder()
                .photoLink(userActProofDto.getPhotoLink())
                .text(userActProofDto.getText())
                .state(ActProofDto.State.CONSIDERATION)
                .moderatorId(getRandomAdminOrModeratorId())
                .sendDate(System.currentTimeMillis())
                .latitude(userActProofDto.getLatitude())
                .longitude(userActProofDto.getLongitude())
                .userAct(currentUserAct)
                .build();
        return newProof;
    }

    private void accrue(Float coef, Integer reward, Integer userId) {
        User user = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setCash(
                (long) (user.getCash() + (user.getSelfCoefficient() + coef) * reward)
        );
        user.setSelfCoefficient(
                user.getSelfCoefficient() + coef * .7f
        );
        usersRepository.save(user);
    }

    private void accrue(Integer reward, Integer userId) {
        User user = usersRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setCash(
                (long) (user.getCash() + user.getSelfCoefficient() * reward)
        );
        user.setSelfCoefficient(
                user.getSelfCoefficient() + .9f
        );
        usersRepository.save(user);
    }

    private Integer getRandomAdminOrModeratorId() {
        List<Integer> ids = usersRepository.findModeratorsAndAdminsIds();
        return ids.get(new Random().nextInt(ids.size()));
    }

}
