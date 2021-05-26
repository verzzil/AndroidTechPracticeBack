package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.androidtechpractice.models.UserActProof;

import java.util.List;

public interface UserActProofsRepository extends JpaRepository<UserActProof, Integer> {

    @Query(
            nativeQuery = true,
            value = "select * from user_act_proof where moderator_id = :moderatorId and state = 'CONSIDERATION'"
    )
    List<UserActProof> findAllByModeratorId(Integer moderatorId);

}
