package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.androidtechpractice.models.Group;

public interface GroupsRepository extends JpaRepository<Group, Integer> {

}
