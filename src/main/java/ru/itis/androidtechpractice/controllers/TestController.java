package ru.itis.androidtechpractice.controllers;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.androidtechpractice.dto.ChatDto;
import ru.itis.androidtechpractice.dto.CreateChatBetweenTwoUsersDto;
import ru.itis.androidtechpractice.dto.UserActDto;
import ru.itis.androidtechpractice.dto.UserDto;
import ru.itis.androidtechpractice.models.Chat;
import ru.itis.androidtechpractice.models.CoefficientBetweenUsers;
import ru.itis.androidtechpractice.repositories.ChatsRepository;
import ru.itis.androidtechpractice.repositories.CoefficientBetweenUsersRepository;
import ru.itis.androidtechpractice.services.ChatsService;
import ru.itis.androidtechpractice.services.UsersService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
public class TestController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ChatsService chatsService;

    @Autowired
    private CoefficientBetweenUsersRepository coefficientBetweenUsersRepository;

    @Autowired
    private ChatsRepository chatsRepository;

    @GetMapping("")
    public ResponseEntity page() {
        Pair<Integer, Integer> pair1 = new Pair<>(1, 1);
        Pair<Integer, Integer> pair2 = new Pair<>(1, 1);
        return ResponseEntity.ok(pair1.equals(pair2));
    }

    @GetMapping("/testchat/{userId}")
    public String getMainPage(@PathVariable("userId") Integer userId, Model model) {
        UserDto user = usersService.getUserById(userId);
        model.addAttribute("user", user);
        return "main";
    }

    @GetMapping("/testchat/user/{userId}/chat/{chatId}")
    public String getChatPage(@PathVariable("userId") Integer userId, @PathVariable("chatId") Integer chatId, Model model) {
        UserDto user = usersService.getUserById(userId);
        ChatDto chat = chatsService.getChatById(chatId);
        model.addAttribute("user", user);
        model.addAttribute("chat", chat);
        model.addAttribute("uuid", UUID.randomUUID().toString());
        return "message";
    }

    @GetMapping("/tesssets")
    @ResponseBody
    public ResponseEntity<String> getTejst() {
        Optional<CoefficientBetweenUsers> coefficientBetweenUsers = coefficientBetweenUsersRepository.getCoeffsBetweenUsers(1, 2);
        System.out.println(coefficientBetweenUsers + "alva");
        return ResponseEntity.ok("str");
    }


    @GetMapping("/asdf")
    @ResponseBody
    public ResponseEntity<Set<Integer>> getasdf() {
        return ResponseEntity.ok(chatsService.getAllUserChatsId(2));
    }

}
