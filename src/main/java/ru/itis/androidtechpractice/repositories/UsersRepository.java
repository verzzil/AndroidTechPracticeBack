package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.androidtechpractice.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    @Query(
            nativeQuery = true,
            value = "select * from account order by cash DESC limit 100"
    )
    List<User> findAllByOrderByCashDesc();

    @Query(nativeQuery = true, value = "select id from account where role = 'MODERATOR' or role = 'ADMIN'")
    List<Integer> findModeratorsAndAdminsIds();

    List<User> findAllByEmailStartsWith(String email);

    @Query(
            nativeQuery = true,
            value = "select * from account where id in (select user_id from users_chats_rel where chat_id = :chatId)"
    )
    List<User> findAllChatUsers(Integer chatId);

}
