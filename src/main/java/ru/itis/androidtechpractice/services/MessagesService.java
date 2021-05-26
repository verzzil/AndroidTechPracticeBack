package ru.itis.androidtechpractice.services;

import ru.itis.androidtechpractice.dto.MessageDto;

import java.util.List;

public interface MessagesService {
    List<MessageDto> getMessagesFromChat(Integer chatId);
}
