package ru.itis.androidtechpractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.androidtechpractice.dto.MessageDto;
import ru.itis.androidtechpractice.repositories.MessagesRepository;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public List<MessageDto> getMessagesFromChat(Integer chatId) {
        return MessageDto.from(messagesRepository.findAllByChatIdOrderBySendDate(chatId));
    }
}
