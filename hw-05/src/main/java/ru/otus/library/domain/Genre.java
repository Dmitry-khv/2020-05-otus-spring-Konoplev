package ru.otus.library.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {
    private long id;
    private final String genre;

    public Genre(long id, String genre) {
        this.id = id;
        this.genre = genre;
    }
}
