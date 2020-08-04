package ru.otus.library.repository;

import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    long addAuthor(Author author);
    void removeAuthor(long id);
    Optional<Author> getAuthorById(long id);
    Optional<Author> getAuthorByName(String name);
    List<Author> getAllAuthors();
}
