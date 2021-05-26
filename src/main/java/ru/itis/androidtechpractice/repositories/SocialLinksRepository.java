package ru.itis.androidtechpractice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.androidtechpractice.models.SocialLink;

public interface SocialLinksRepository extends JpaRepository<SocialLink, Integer> {

}
