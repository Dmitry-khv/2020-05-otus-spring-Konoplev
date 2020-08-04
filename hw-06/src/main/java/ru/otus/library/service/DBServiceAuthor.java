package ru.otus.library.service;

import ru.otus.library.domain.Author;

import java.util.List;

public interface DBServiceAuthor {
    long addAuthor(Author author);
    void removeAuthor(long id);
    Author getAuthorById(long id);
    Author getAuthorByName(String name);
    List<Author> getListAuthors();
}
