package ru.otus.library.domain;

import lombok.Data;

@Data
public class User {

    private final String login;
    private final String password;
    private final int accessLevel;

    public User(String login, String password, int accessLevel) {
        this.login = login;
        this.password = password;
        this.accessLevel = accessLevel;
    }
}
