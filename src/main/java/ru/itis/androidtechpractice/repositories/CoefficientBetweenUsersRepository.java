package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.androidtechpractice.models.CoefficientBetweenUsers;
import ru.itis.androidtechpractice.models.User;

import java.util.List;
import java.util.Optional;


public interface CoefficientBetweenUsersRepository extends JpaRepository<CoefficientBetweenUsers, Integer> {

    @Query(
            nativeQuery = true,
            value = "select * from coefficient_between_users where (first_user = :firstUser and second_user = :secondUser) or (second_user = :firstUser and first_user = :secondUser)"
    )
    Optional<CoefficientBetweenUsers> getCoeffsBetweenUsers(Integer firstUser, Integer secondUser);

    @Query(
            nativeQuery = true,
            value = "select coefficient from coefficient_between_users where (first_user = :firstUser and second_user = :secondUser) or (second_user = :firstUser and first_user = :secondUser)"
    )
    Float getCoefficientBetweenUsersByIds(Integer firstUser, Integer secondUser);

}
