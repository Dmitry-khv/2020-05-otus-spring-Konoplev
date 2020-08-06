package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.util.List;

public interface LibraryService {
    long saveBook(Book book);
    long updateBookTitle(long id, String title);
    void deleteBookById(long id);
    Book getBookById(long id);
    List<Book> getBookByTitle(String title);
    List<Book> getListBooks();
    List<Book> getBooksByAuthorName(String name);
    void addNewCommentToBookById(long id, String commentText);
}
