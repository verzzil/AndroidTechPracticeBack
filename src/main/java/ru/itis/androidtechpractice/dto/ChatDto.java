package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.Chat;
import ru.itis.androidtechpractice.models.Message;
import ru.itis.androidtechpractice.models.User;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {
    private Integer id;
    private Date dateOfCreation;
    private String title;
    private List<Integer> usersId;
    private MessageDto lastMessage;
    private Chat.ChatType chatType;

    public static ChatDto from(Chat chat) {

        return ChatDto.builder()
                .id(chat.getId())
                .dateOfCreation(chat.getDateOfCreation())
                .chatType(chat.getChatType())
                .title(chat.getTitle())
                .usersId(chat.getUsers().stream().map(User::getId).collect(Collectors.toList()))
                .lastMessage(
                        chat.getMessages().isEmpty() ? new MessageDto() : MessageDto.from(chat.getMessages().stream().sorted(Comparator.comparing(Message::getSendDate)).collect(Collectors.toList()).get(chat.getMessages().size() - 1))
                        )
                .build();
    }

    public static List<ChatDto> from(List<Chat> chats) {
        return chats.stream()
                .map(ChatDto::from)
                .collect(Collectors.toList());
    }
}
