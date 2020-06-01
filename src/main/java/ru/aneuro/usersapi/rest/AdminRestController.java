package ru.aneuro.usersapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aneuro.usersapi.dto.UserDto;
import ru.aneuro.usersapi.entity.User;
import ru.aneuro.usersapi.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    private ResponseEntity<List<UserDto>> userList(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> userList = userService.getAll(pageable);
        return new ResponseEntity<>(userList.stream().map(UserDto::fromUser).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("user/{id}")
    private void deleteUser(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user != null)
            userService.delete(user);
    }

    @GetMapping("user/{id}")
    private ResponseEntity<UserDto> getUser(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @PostMapping("user/{id}")
    private ResponseEntity<UserDto> saveUser(@PathVariable(name = "id") Long id, @RequestBody UserDto userDto) {
        User user = userService.findById(id);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        userDto.toUser(user);
        userService.save(user);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

    @PutMapping("user")
    private ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userDto.toUser();
        userService.add(user);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }

}
