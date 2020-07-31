package ru.otus.library.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Author {
    private long id;
    private final String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
