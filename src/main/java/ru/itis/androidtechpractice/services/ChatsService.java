package ru.itis.androidtechpractice.services;

import ru.itis.androidtechpractice.dto.ChatDto;
import ru.itis.androidtechpractice.dto.CreateChatBetweenTwoUsersDto;
import ru.itis.androidtechpractice.dto.ForDialogDto;
import ru.itis.androidtechpractice.dto.MessageDto;
import ru.itis.androidtechpractice.models.Group;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public interface ChatsService {

    ChatDto createChat(Group group);

    ChatDto createChat(CreateChatBetweenTwoUsersDto createChatBetweenTwoUsersDto);

    MessageDto sendMessage(MessageDto messageDto) throws IOException;

    ChatDto getChatById(Integer id);

    Set<Integer> getAllUserChatsId(Integer userId);

    Set<Integer> findAllUsersIdsByChatId(Integer chatId);

    List<ChatDto> findUserChats(Integer userId);

    ForDialogDto findDialogTitle(Integer chatId, Integer myId);
}
