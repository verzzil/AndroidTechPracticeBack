package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.androidtechpractice.models.GroupActProof;
import ru.itis.androidtechpractice.models.UserActProof;

import java.util.List;

public interface GroupActProofsRepository extends JpaRepository<GroupActProof, Integer> {

    @Query(
            nativeQuery = true,
            value = "select * from group_act_proof where moderator_id = :moderatorId and state = 'CONSIDERATION'"
    )
    List<GroupActProof> findAllByModeratorId(Integer moderatorId);


}
