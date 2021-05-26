package ru.itis.androidtechpractice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.androidtechpractice.dto.TokenDto;
import ru.itis.androidtechpractice.models.Token;
import ru.itis.androidtechpractice.repositories.TokenRepository;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public TokenDto save(Token token) {
        return TokenDto.from(
                tokenRepository.save(token)
        );
    }

    @Override
    public void logout(String token) {
        tokenRepository.deleteByToken(token);
    }
}
