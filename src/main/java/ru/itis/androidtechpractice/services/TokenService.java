package ru.itis.androidtechpractice.services;

import ru.itis.androidtechpractice.dto.TokenDto;
import ru.itis.androidtechpractice.models.Token;

public interface TokenService {
    TokenDto save(Token token);

    void logout(String token);
}
