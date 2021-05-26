package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.androidtechpractice.models.Token;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);

    @Transactional
    void deleteByToken(String token);
}
