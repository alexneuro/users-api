package ru.aneuro.usersapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import ru.aneuro.usersapi.entity.Role;
import ru.aneuro.usersapi.entity.User;
import ru.aneuro.usersapi.service.RoleService;
import ru.aneuro.usersapi.service.UserService;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class UsersApiApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(UsersApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        roleService.save(role);

        role = new Role();
        role.setName("ROLE_USER");
        roleService.save(role);

        User user = new User();
        user.setLogin("admin");
        user.setLastName("Иванов");
        user.setFirstName("Иван");
        user.setSecondName("Иванович");
        user.setPassword("1234");
        userService.add(user, "ROLE_ADMIN", "ROLE_USER");

        user = new User();
        user.setLogin("user");
        user.setLastName("Петров");
        user.setFirstName("Петр");
        user.setSecondName("Петрович");
        user.setPassword("4321");
        userService.add(user);

        user = new User();
        user.setLogin("user2");
        user.setLastName("Лаптев");
        user.setFirstName("Тестр");
        user.setSecondName("Тестрович");
        user.setPassword("4321");
        userService.add(user);
    }
}
