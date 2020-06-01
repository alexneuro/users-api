package ru.aneuro.usersapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aneuro.usersapi.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    List<Role> findAllByNameIn(String... names);
}
