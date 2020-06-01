package ru.aneuro.usersapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aneuro.usersapi.entity.Role;
import ru.aneuro.usersapi.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public void save(Role role) {
        roleRepository.save(role);
    }
}
