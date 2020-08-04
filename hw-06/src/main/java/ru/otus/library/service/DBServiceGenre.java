package ru.otus.library.service;

import ru.otus.library.domain.Genre;

import java.util.List;

public interface DBServiceGenre {
    long addGenre(Genre genre);
    Genre getGenreById(long id);
    Genre getGenreByName(String name);
    List<Genre> getListGenres();
}
