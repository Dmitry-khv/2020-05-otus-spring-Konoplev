package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.BookDao;
import ru.otus.library.service.DBServiceBook;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBServiceBookImpl implements DBServiceBook {
    private final BookDao bookDao;

    @Override
    public long addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getBookById(id).orElse(null);
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookDao.getBookByTitle(title).orElse(null);
    }

    @Override
    public List<Book> getListBooks()  {
        List<Book> books = bookDao.getAllBook();
        return books;
    }

    @Override
    public void updateBookById(long id, Book book) {
        bookDao.updateBookById(id, book);
    }
}