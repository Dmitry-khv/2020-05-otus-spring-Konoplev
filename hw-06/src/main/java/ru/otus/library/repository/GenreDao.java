package ru.otus.library.repository;

import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    long addGenre(Genre genre);
    Optional<Genre> getGenreById(long id);
    Optional<Genre> getGenreByName(String name);
    List<Genre> getAllGenres();
}
