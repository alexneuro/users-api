package ru.aneuro.usersapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.aneuro.usersapi.dto.UserDto;
import ru.aneuro.usersapi.entity.User;
import ru.aneuro.usersapi.security.JwtUser;
import ru.aneuro.usersapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    private Long getAuthUserId() {
        JwtUser authUser = (JwtUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return authUser.getId();
    }


    @GetMapping
    private ResponseEntity<UserDto> getUser() {
        User user = userService.findById(getAuthUserId());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        User user = userService.findById(getAuthUserId());
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        userDto.setLogin(null); //Не меняем логин
        userDto.toUser(user);
        userService.save(user);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }
}
