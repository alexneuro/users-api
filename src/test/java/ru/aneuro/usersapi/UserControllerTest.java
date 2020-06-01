package ru.aneuro.usersapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.aneuro.usersapi.dto.AuthenticationRequestDto;
import ru.aneuro.usersapi.dto.AuthenticationResponseDto;
import ru.aneuro.usersapi.dto.UserDto;
import ru.aneuro.usersapi.entity.User;
import ru.aneuro.usersapi.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private static ResponseEntity<AuthenticationResponseDto> _authResponse;

    @Test
    public void whenGetUser_notAuth__thenForbidden() {
        User user = userService.findByLogin("user");

        HttpEntity<UserDto> request = new HttpEntity<>(UserDto.fromUser(user));
        ResponseEntity<UserDto> response = restTemplate.exchange("/api/user", HttpMethod.GET, request, UserDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void whenSaveUser_notAuth__thenForbidden() {
        User user = userService.findByLogin("user");

        UserDto userDto = UserDto.fromUser(user);
        userDto.setLogin("TEST_LOGIN");
        userDto.setLastName("TEST_LN");

        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        ResponseEntity<UserDto> response = restTemplate.exchange("/api/user", HttpMethod.POST, request, UserDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void whenAuthentificationUser__thenOk() {
        assertEquals(authResponse().getStatusCode(), HttpStatus.OK);
        assertNotNull(authResponse().getBody());
        assertNotNull(authResponse().getBody().getToken());
    }


    @Test
    public void whenGetUser_withAuth__thenOk() {
        User user = userService.findByLogin("user");
        HttpEntity<UserDto> request = authUserRequest(UserDto.fromUser(user), authResponse().getBody().getToken());

        ResponseEntity<UserDto> response = restTemplate.exchange("/api/user", HttpMethod.GET, request, UserDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody(), UserDto.fromUser(user));
    }

    @Test
    public void whenSaveUser_withAuth__thenForbidden() {
        User user = userService.findByLogin("user");

        UserDto userDto = UserDto.fromUser(user);
        userDto.setLogin("TEST_LOGIN");
        userDto.setLastName("TEST_LN");

        HttpEntity<UserDto> request = authUserRequest(userDto, authResponse().getBody().getToken());
        ResponseEntity<UserDto> response = restTemplate.exchange("/api/user", HttpMethod.POST, request, UserDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getLogin(), "user");
        assertEquals(response.getBody().getLastName(), "TEST_LN");
    }


    private ResponseEntity<AuthenticationResponseDto> authResponse() {
        if (_authResponse == null) {
            AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
            authenticationRequestDto.setLogin("user");
            authenticationRequestDto.setPassword("4321");

            HttpEntity<AuthenticationRequestDto> authRequest = new HttpEntity<>(authenticationRequestDto);
            _authResponse = restTemplate.exchange("/api/auth", HttpMethod.POST,
                    authRequest, AuthenticationResponseDto.class);
        }

        return _authResponse;
    }

    private HttpEntity<UserDto> authUserRequest(UserDto userDto, String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer_" + token);
        return new HttpEntity<>(userDto, httpHeaders);
    }


}
