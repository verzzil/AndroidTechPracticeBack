package ru.itis.androidtechpractice.handlers;

import javafx.util.Pair;
import org.springframework.stereotype.Component;
import ru.itis.androidtechpractice.config.SpringContext;
import ru.itis.androidtechpractice.dto.ChatDto;
import ru.itis.androidtechpractice.dto.coders.ChatDecoder;
import ru.itis.androidtechpractice.dto.coders.ChatEncoder;
import ru.itis.androidtechpractice.services.ChatsService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(
        value = "/wschat/{userId}",
        decoders = {ChatDecoder.class},
        encoders = {ChatEncoder.class}
)
@Component
public class ChatEndpoint {

    private ChatsService chatsService;

    private static final Map<Integer, Pair<Session, Set<Integer>>> chatEndpoints = new HashMap<>(); // Map<UserId, Session>
    private Integer userId = null;

    public ChatEndpoint() {
        this.chatsService = SpringContext.getApplicationContext().getBean(ChatsService.class);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String strUserId) {
        try {
            userId = Integer.parseInt(strUserId);
            Set<Integer> userChatsIds = chatsService.getAllUserChatsId(userId);

            if (!chatEndpoints.containsKey(userId)) {
                chatEndpoints.put(userId, new Pair<>(session, userChatsIds));
            }
        } catch (Exception ignored) {

        }


    }

    @OnClose
    public void onClose(Session session) {
        if (chatEndpoints.get(userId).getKey().equals(session)) {
            chatEndpoints.remove(userId);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, ChatDto chatDto) throws IOException, EncodeException {

        System.out.println(chatDto);

        chatEndpoints.forEach((userId, sessionSetPair) -> {
            if (sessionSetPair.getValue().contains(chatDto.getId())) {
                chatDto.getLastMessage().setSendDate(System.currentTimeMillis());
                sessionSetPair.getKey().getAsyncRemote().sendObject(chatDto);
            }
        });

    }
}
