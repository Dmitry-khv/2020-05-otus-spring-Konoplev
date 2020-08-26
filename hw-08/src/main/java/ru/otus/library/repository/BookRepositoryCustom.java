package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> findAllByGenreId(String name);
    List<Book> findAllByAuthorId(String id);
    void removeAuthorById(String id);
    void removeGenreById(String id);
}
