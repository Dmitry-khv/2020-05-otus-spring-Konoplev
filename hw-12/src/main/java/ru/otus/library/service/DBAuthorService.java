package ru.otus.library.service;

import ru.otus.library.domain.Author;

import java.util.List;

public interface DBAuthorService {
    List<Author> getAll();

    Author getById(String id);

    Author save(Author author);
}
