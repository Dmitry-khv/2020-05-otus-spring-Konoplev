package ru.otus.library.service;

import ru.otus.library.domain.Genre;

import java.util.List;

public interface DBGenreService {
    List<Genre> getAll();

    Genre getById(String id);

    Genre save(Genre author);

}
