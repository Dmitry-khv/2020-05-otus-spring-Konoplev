package ru.otus.library.repository;

import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    long insertOrUpdateBook(Book book);
    void deleteBookById(long id);
    Optional<Book> getBookById(long id);
    List<Book> getBookByTitle(String name);
    List<Book> getAllBook();
    List<Book> getBooksByAuthorName(String name);
    void addNewCommentToBookById(long id, String commentText);
}
