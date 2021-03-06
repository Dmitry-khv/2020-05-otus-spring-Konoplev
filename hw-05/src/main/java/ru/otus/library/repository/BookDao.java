package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long addBook(Book book);
    void deleteBookById(long id);
    Optional<Book> getBookById(long id);
    Optional<Book> getBookByTitle(String name);
    List<Book> getAllBook();
    void updateBookById(long id, Book book);
}
