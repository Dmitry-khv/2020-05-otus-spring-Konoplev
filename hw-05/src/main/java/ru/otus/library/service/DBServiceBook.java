package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.util.List;

public interface DBServiceBook {
    long addBook(Book book);
    void deleteBookById(long id);
    Book getBookById(long id);
    Book getBookByTitle(String title);
    List<Book> getListBooks();
    void updateBookById(long id, Book book);
}
