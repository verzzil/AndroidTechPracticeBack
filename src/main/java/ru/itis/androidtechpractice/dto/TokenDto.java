package ru.itis.androidtechpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.androidtechpractice.models.Token;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private Integer id;
    private String token;
    private Date lifetime;
    private Integer userId;

    public static TokenDto from(Token token) {
        if (token == null)
            return null;
        return TokenDto.builder()
                .id(token.getId())
                .token(token.getToken())
                .lifetime(token.getLifetime())
                .userId(token.getUser().getId())
                .build();
    }

    public static List<TokenDto> from(List<Token> tokens) {
        return tokens.stream()
                .map(TokenDto::from)
                .collect(Collectors.toList());
    }
}
