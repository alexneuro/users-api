package ru.aneuro.usersapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aneuro.usersapi.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
