package ru.itis.androidtechpractice.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.androidtechpractice.dto.MessageDto;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private static final Map<Integer, WebSocketSession> sessions = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage jsonMessage) throws Exception {
        System.out.println(session.getUri());
        MessageDto message = objectMapper.readValue(jsonMessage.getPayload(), MessageDto.class);

        if (!sessions.containsKey(message.getChatId())) {
            sessions.put(message.getChatId(), session);
        }

        sessions.get(message.getChatId()).sendMessage(jsonMessage);

    }

}
