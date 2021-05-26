package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.androidtechpractice.models.Chat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatsRepository extends JpaRepository<Chat, Integer> {

    @Query(
            nativeQuery = true,
            value = "select * from chat where chat_type = 'TWO' and chat.id in (select chat_id from users_chats_rel where user_id = :firstUserId) and chat.id in (select chat_id from users_chats_rel where user_id = :secondUserId)"
    )
    Optional<Chat> getChatBetweenTwoUsers(Integer firstUserId, Integer secondUserId);

    @Query(
            nativeQuery = true,
            value = "select chat_id from users_chats_rel where user_id = :userId"
    )
    Set<Integer> findAllChatsIdByUserId(Integer userId);

    @Query(
            nativeQuery = true,
            value = "select user_id from users_chats_rel where chat_id = :chatId"
    )
    Set<Integer> findAllUsersIdsByChatId(Integer chatId);

    @Query(
            nativeQuery = true,
            value = "select * from chat where id in (select chat_id from users_chats_rel where user_id = :userId)"
    )
    List<Chat> findAllUserChats(Integer userId);

    @Query(
            nativeQuery = true,
            value = "select user_id from users_chats_rel where chat_id = :chatId and user_id != :myId"
    )
    Integer findBetweenUserId(Integer chatId, Integer myId);

}
