package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.Message;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Integer id;
    private String text;
    private Long sendDate;
    private Integer chatId;
    private Integer userId;
    private String userName;

    public static MessageDto from(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .text(message.getText())
                .sendDate(message.getSendDate())
                .chatId(message.getChat().getId())
                .userId(message.getUser().getId())
                .userName(message.getUser().getFullName())
                .build();
    }

    public static List<MessageDto> from(List<Message> messages) {
        return messages.stream()
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }


}
