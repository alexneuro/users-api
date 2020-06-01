package ru.aneuro.usersapi.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.aneuro.usersapi.entity.Role;
import ru.aneuro.usersapi.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getLastName(),
                user.getFirstName(),
                user.getSecondName(),
                user.getPassword(),
                mapRoles(new ArrayList<>(user.getRoles())),
                true,
                user.getUpdateDate()
        );
    }

    private static List<GrantedAuthority> mapRoles(List<Role> userRoles) {
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
