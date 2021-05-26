package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.androidtechpractice.models.Message;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByChatIdOrderBySendDate(Integer chatId);

}
