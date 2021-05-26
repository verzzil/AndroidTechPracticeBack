package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.androidtechpractice.models.UserAct;

import java.util.List;

public interface UserActsRepository extends JpaRepository<UserAct, Integer> {

    @Query(
            nativeQuery = true,
            value = "select * from user_act where user_id = :userId and act_status = 'END'"
    )
    List<UserAct> findEndActsByUserId(Integer userId);

    @Query(
            nativeQuery = true,
            value = "select * from user_act where user_id = :userId"
    )
    List<UserAct> findContinueActsByUserId(Integer userId);

}
