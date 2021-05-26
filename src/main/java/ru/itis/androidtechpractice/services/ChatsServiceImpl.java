package ru.itis.androidtechpractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.androidtechpractice.dto.*;
import ru.itis.androidtechpractice.models.Chat;
import ru.itis.androidtechpractice.models.Group;
import ru.itis.androidtechpractice.models.Message;
import ru.itis.androidtechpractice.models.User;
import ru.itis.androidtechpractice.repositories.ChatsRepository;
import ru.itis.androidtechpractice.repositories.MessagesRepository;
import ru.itis.androidtechpractice.repositories.UsersRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChatsServiceImpl implements ChatsService {

    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public ChatDto createChat(Group group) {
        Chat newChat = chatsRepository.save(Chat.builder()
                .users(group.getUsers())
                .chatType(Chat.ChatType.GROUP)
                .title(group.getTitle())
                .messages(new ArrayList<>())
                .build());

        messagesRepository.save(
                Message.builder()
                        .text("Hello, my Friends!")
                        .chat(newChat)
                        .sendDate(System.currentTimeMillis())
                        .user(group.getMainUser())
                        .build()
        );

        return ChatDto.from(newChat);
    }

    @Override
    public ChatDto createChat(CreateChatBetweenTwoUsersDto createChatBetweenTwoUsersDto) {

        Optional<Chat> chat = chatsRepository.getChatBetweenTwoUsers(createChatBetweenTwoUsersDto.getFirstUserId(), createChatBetweenTwoUsersDto.getSecondUserId());

        if (chat.isPresent()) {
            return ChatDto.from(chat.get());
        } else {
            User firstUser = usersRepository.findById(createChatBetweenTwoUsersDto.getFirstUserId()).orElseThrow(IllegalArgumentException::new);
            User secondUser = usersRepository.findById(createChatBetweenTwoUsersDto.getSecondUserId()).orElseThrow(IllegalArgumentException::new);

            Chat newChat = Chat.builder()
                    .users(Arrays.asList(firstUser, secondUser))
                    .messages(new ArrayList<>())
                    .chatType(Chat.ChatType.TWO)
                    .title("Двойной диалог")
                    .build();

            return ChatDto.from(chatsRepository.save(newChat));
        }
    }

    @Override
    public MessageDto sendMessage(MessageDto messageDto) {
        Chat currentChat = chatsRepository.findById(messageDto.getChatId()).orElseThrow(IllegalArgumentException::new);
        User currentUser = usersRepository.findById(messageDto.getUserId()).orElseThrow(IllegalArgumentException::new);

        Message newMessage = Message.builder()
                .chat(currentChat)
                .user(currentUser)
                .text(messageDto.getText())
                .sendDate(System.currentTimeMillis())
                .build();

        return MessageDto.from(messagesRepository.save(newMessage));
    }

    @Override
    public ChatDto getChatById(Integer id) {
        return ChatDto.from(chatsRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public Set<Integer> getAllUserChatsId(Integer userId) {
        return chatsRepository.findAllChatsIdByUserId(userId);
    }

    @Override
    public Set<Integer> findAllUsersIdsByChatId(Integer chatId) {
        return chatsRepository.findAllUsersIdsByChatId(chatId);
    }

    @Override
    public List<ChatDto> findUserChats(Integer userId) {
        return ChatDto.from(chatsRepository.findAllUserChats(userId));
    }

    @Override
    public ForDialogDto findDialogTitle(Integer chatId, Integer myId) {
        Integer anotherUserId = chatsRepository.findBetweenUserId(chatId, myId);
        User user = usersRepository.findById(anotherUserId).orElseThrow(IllegalArgumentException::new);
        return ForDialogDto.builder()
                .dialogTitle(user.getFullName())
                .link(user.getPhotoLink())
                .build();
    }
}
