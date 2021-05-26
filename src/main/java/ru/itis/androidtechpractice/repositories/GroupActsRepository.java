package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.androidtechpractice.models.GroupAct;

public interface GroupActsRepository extends JpaRepository<GroupAct, Integer> {

}
