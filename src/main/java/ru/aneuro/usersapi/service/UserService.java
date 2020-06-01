package ru.aneuro.usersapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aneuro.usersapi.entity.User;
import ru.aneuro.usersapi.repository.RoleRepository;
import ru.aneuro.usersapi.repository.UserRepository;
import ru.aneuro.usersapi.security.JwtUserFactory;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User add(User user, String... roleNames) {
        if (user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findAllByNameIn(roleNames));
        return userRepository.save(user);
    }

    public User add(User user) {
        return add(user, "ROLE_USER");
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByLogin(s);

        if (user == null)
            throw new UsernameNotFoundException(String.format("User with login '%s' not found", s));

        return JwtUserFactory.create(user);
    }
}
