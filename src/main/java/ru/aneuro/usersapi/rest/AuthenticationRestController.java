package ru.aneuro.usersapi.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.aneuro.usersapi.dto.AuthenticationRequestDto;
import ru.aneuro.usersapi.dto.AuthenticationResponseDto;
import ru.aneuro.usersapi.entity.User;
import ru.aneuro.usersapi.security.JwtTokenProvider;
import ru.aneuro.usersapi.service.UserService;

@RestController
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping(value = "/api/auth")
    public ResponseEntity<AuthenticationResponseDto> auth(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String login = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            User user = userService.findByLogin(login);
            if (user == null)
                throw new UsernameNotFoundException(String.format("User with login %s not found!", login));
            String token = jwtTokenProvider.createToken(user);

            AuthenticationResponseDto responseDto = new AuthenticationResponseDto();
            responseDto.setLogin(login);
            responseDto.setToken(token);
            return ResponseEntity.ok(responseDto);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }
}