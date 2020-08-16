package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.DBBookService;
import ru.otus.library.service.NotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DBBookServiceImpl implements DBBookService {
    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByAuthorName(String name) {
        return bookRepository.findBooksByAuthor_Name(name);
    }

    @Override
    @Transactional
    public void addNewCommentToBook(long id, Comment comment) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.getComment().add(comment);
    }

    @Override
    public List<Comment> getCommentsByBookId(long id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new).getComment();
    }

    @Override
    public void updateBookTitle(long id, String title) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.setTitle(title);
    }
}
