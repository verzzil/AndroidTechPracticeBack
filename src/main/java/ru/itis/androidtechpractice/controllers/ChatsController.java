package ru.itis.androidtechpractice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.androidtechpractice.dto.ChatDto;
import ru.itis.androidtechpractice.dto.CreateChatBetweenTwoUsersDto;
import ru.itis.androidtechpractice.dto.ForDialogDto;
import ru.itis.androidtechpractice.dto.MessageDto;
import ru.itis.androidtechpractice.services.ChatsService;
import ru.itis.androidtechpractice.services.MessagesService;

import java.util.List;

@RestController
public class ChatsController {

    @Autowired
    private ChatsService chatsService;

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/user/{userId}/chats")
    public ResponseEntity<List<ChatDto>> getAllChats(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(
                chatsService.findUserChats(userId)
        );
    }

    @PostMapping("/chat/create")
    public ResponseEntity<ChatDto> createChatTwoUsers(@RequestBody CreateChatBetweenTwoUsersDto createChatBetweenTwoUsersDto) {
        return ResponseEntity.ok(chatsService.createChat(createChatBetweenTwoUsersDto));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDto>> getChatById(@PathVariable(name = "chatId") Integer chatId) {
        return ResponseEntity.ok(
                messagesService.getMessagesFromChat(chatId)
        );
    }

    @PostMapping("/chat/send-message")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(chatsService.sendMessage(messageDto));
    }

    @GetMapping("/chat/find-user/{chatId}/{userId}")
    public ResponseEntity<ForDialogDto> chatTitle(@PathVariable("chatId") Integer chatId, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(
                chatsService.findDialogTitle(chatId, userId)
        );
    }

}
