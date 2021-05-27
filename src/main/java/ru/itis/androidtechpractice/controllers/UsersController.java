package ru.itis.androidtechpractice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.androidtechpractice.dto.*;
import ru.itis.androidtechpractice.services.UsersService;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestHeader("X-TOKEN") String token) {
        return ResponseEntity.ok(
                usersService.getAllUsers()
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Integer userId) {
        return ResponseEntity.ok(
                usersService.getUserById(userId)
        );
    }

    @GetMapping("/users/top")
    public ResponseEntity<List<UserDto>> getTopUsers() {
        return ResponseEntity.ok(
                usersService.getTopUsers()
        );
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok(
                usersService.signIn(form)
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(
                usersService.signUp(form)
        );
    }

    @PostMapping("/out")
    public ResponseEntity<String> logout(@RequestHeader("X-TOKEN") String tokenValue) {
        usersService.logout(tokenValue);
        return ResponseEntity.ok("Alba");
    }

    @PostMapping("/user/act/create")
    public ResponseEntity<String> createAct(@RequestBody UserActDto userActDto) {
        usersService.createAct(userActDto);
        return ResponseEntity.ok("str");
    }

    @PostMapping("/user/act/proof/create")
    public ResponseEntity<String> createProof(@RequestBody UserActProofDto userActProofDto) {
        System.out.println(userActProofDto);
        usersService.createActProof(userActProofDto);
        return ResponseEntity.ok("str");
    }

    @PostMapping("/user/act/proof/moderator-decision")
    public ResponseEntity<String> decision(@RequestBody ModeratorDecisionDto moderatorDecisionDto) {
        try {
            usersService.proofDecision(moderatorDecisionDto);
            return ResponseEntity.ok("str");
        } catch (IllegalAccessException e) {
            return ResponseEntity.ok("Ну удалось(");
        }
    }

    @GetMapping("/user/act/continue/{userId}")
    public ResponseEntity<List<ActDto>> getUserContinueActs(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(
                usersService.findContinueUserActs(userId)
        );
    }

    @GetMapping("/user/act/end/{userId}")
    public ResponseEntity<List<ActDto>> getUserEndActs(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(
                usersService.findEndUserActs(userId)
        );
    }

    @GetMapping("/user/act/{userId}")
    public ResponseEntity<List<ActDto>> getUserAllActs(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(
                usersService.findActsByUserId(userId)
        );
    }

    @GetMapping("/user/get-by-email")
    public ResponseEntity<List<UserDto>> getUserByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(
                usersService.getUserByLikeEmail(email)
        );
    }

    @GetMapping("/users/email")
    public ResponseEntity<List<UserDto>> getByEmailLike(@RequestParam(name = "q") String query) {
        return ResponseEntity.ok(
                usersService.findAllUsersByEmailStartsWith(query)
        );
    }

    @PostMapping("/user/change-settings/{userId}")
    public ResponseEntity<String> changeUser(@RequestBody ChangeUserSettingsDto settingsDto, @PathVariable Integer userId) {
        settingsDto.setId(userId);
        usersService.changeUserSettings(settingsDto);
        return ResponseEntity.ok("str");
    }

    @GetMapping("/moderator/get-acts/{moderatorId}")
    public ResponseEntity<List<ActProofDto>> getModeratorActs(@PathVariable("moderatorId") Integer moderatorId) {
        return ResponseEntity.ok(
                usersService.getModeratorActs(moderatorId)
        );
    }

    @GetMapping("/user/proofs/approved/{userId}")
    public ResponseEntity<List<ActProofDto>> getUserApprovedProofs(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(
                usersService.getUserApprovedProofs(userId)
        );
    }

    @PostMapping("/user/{userId}/{firebaseToken}")
    public ResponseEntity<Boolean> saveFirebaseToken(@PathVariable("userId") Integer userId, @PathVariable("firebaseToken") String firebaseToken) {
        usersService.saveFirebaseToken(userId, firebaseToken);
        return ResponseEntity.ok(true);
    }

}
