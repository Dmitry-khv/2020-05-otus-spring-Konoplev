package ru.otus.library.service;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface DBBookService {
    Book saveBook(Book book);
    void deleteBookById(String id);
    Book getBookById(String id);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooks();
    List<Book> getBooksByAuthorId(String id);
    void addNewCommentToBook(String id, Comment comment);
    List<Comment> getCommentsByBookId(String id);
    void updateBookTitle(String id, String title);
}
