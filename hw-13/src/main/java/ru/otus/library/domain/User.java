package ru.otus.library.domain;

import lombok.Data;

@Data
public class User {

    private final String login;
    private final String password;
    private final String role;

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
