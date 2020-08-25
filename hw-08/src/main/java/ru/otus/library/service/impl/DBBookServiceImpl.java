package ru.otus.library.service.impl;

import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.DBBookService;
import ru.otus.library.service.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBBookServiceImpl implements DBBookService {
    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getBookById(String id) {
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
    public List<Book> getBooksByAuthorId(String id) {
        return bookRepository.findAllByAuthorId(id);
    }

    @Override
    public void addNewCommentToBook(String id, Comment comment) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.addComment(comment);
        bookRepository.save(book);
    }

    @Override
    public List<Comment> getCommentsByBookId(String id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new).getComments();
    }

    @Override
    public void updateBookTitle(String id, String title) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.setTitle(title);
        bookRepository.save(book);
    }
}
