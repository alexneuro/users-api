package ru.aneuro.usersapi.dto;

import ru.aneuro.usersapi.entity.User;

import java.util.Date;
import java.util.Objects;

public class UserDto {
    private String firstName;
    private String secondName;
    private String lastName;
    private Date birthDate;
    private String login;
    private String post;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User toUser() {
        User user = new User();
        return toUser(user);
    }

    public User toUser(User user) {
        if (firstName != null)
            user.setFirstName(firstName);
        if (lastName != null)
            user.setLastName(lastName);
        if (secondName != null)
            user.setSecondName(secondName);
        if (login != null)
            user.setLogin(login);
        if (birthDate != null)
            user.setBirthDate(birthDate);
        if (post != null)
            user.setPost(post);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setSecondName(user.getSecondName());
        dto.setLogin(user.getLogin());
        dto.setBirthDate(user.getBirthDate());
        dto.setPost(user.getPost());
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(secondName, userDto.secondName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(birthDate, userDto.birthDate) &&
                Objects.equals(post, userDto.post) &&
                Objects.equals(login, userDto.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, lastName, birthDate, post, login);
    }
}
