package ru.itis.androidtechpractice.handlers;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.androidtechpractice.config.SpringContext;
import ru.itis.androidtechpractice.dto.MessageDto;
import ru.itis.androidtechpractice.dto.coders.MessageDecoder;
import ru.itis.androidtechpractice.dto.coders.MessageEncoder;
import ru.itis.androidtechpractice.services.ChatsService;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(
        value = "/wschat/user/{userId}/chat/{chatId}",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class}
)
@Component
public class MessagesEndpoint {
    private final ChatsService chatsService;

    private static final Map<Pair<Integer, Integer>, Session> chatEndpoints = new HashMap<>(); // Map<Pair<UserId, ChatId>, Session>
    Integer chatId = null;
    Integer userId = null;

    public MessagesEndpoint() {
        this.chatsService = SpringContext.getApplicationContext().getBean(ChatsService.class);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") String strChatId, @PathParam("userId") String strUserId) {
        chatId = Integer.parseInt(strChatId);
        userId = Integer.parseInt(strUserId);

        chatEndpoints.put(new Pair(userId, chatId), session);

        System.out.println(chatEndpoints);
    }

    @OnClose
    public void onClose(Session session) {
        chatEndpoints.remove(new Pair<>(userId, chatId));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, MessageDto messageDto) throws IOException {

        System.out.println(messageDto);

        if (messageDto.getChatId().equals(chatId)) {
            chatEndpoints.forEach((userIdChatIdPair, currentSession) -> {
                if (userIdChatIdPair.getValue().equals(messageDto.getChatId())) {
                    try {
                        messageDto.setSendDate(System.currentTimeMillis());
                        currentSession.getBasicRemote().sendObject(messageDto);
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        chatsService.sendMessage(messageDto);

//        if (messageDto.getChatId().equals(chatId)) {
//            chatEndpoints.forEach((userId, currentSession) -> {
//                try {
//                    currentSession.getBasicRemote().sendObject(messageDto);
//                } catch (IOException | EncodeException e) {
//                    e.printStackTrace();
//                }
//            });
//            chatsService.sendMessage(messageDto);
//        }

//        chatsService.findAllUsersIdsByChatId(messageDto.getChatId()).forEach(userId -> {
//            try {
//                if (chatEndpoints.containsKey(userId))
//                    chatEndpoints.get(userId).getBasicRemote().sendObject(messageDto);
//            } catch (IOException | EncodeException e) {
//                e.printStackTrace();
//            }
//        });


    }
}
