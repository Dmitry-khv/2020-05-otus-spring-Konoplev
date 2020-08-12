package ru.otus.library.service;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface DBBookService {
    Book saveBook(Book book);
    void deleteBookById(long id);
    Book getBookById(long id);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooks();
    List<Book> getBooksByAuthorName(String name);
    void addNewCommentByBookId(long id, Comment comment);
    void updateBookTitle(long id, String title);
}
