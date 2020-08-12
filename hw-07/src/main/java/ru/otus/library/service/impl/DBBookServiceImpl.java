package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.service.DBBookService;
import ru.otus.library.service.NotFoundException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DBBookServiceImpl implements DBBookService {
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Book getBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return Book.builder()
                .id(id)
                .title(book.getTitle())
                .author(new Author(book.getAuthor().getName()))
                .genre(new Genre(book.getGenre().getGenreName()))
                .comment(new ArrayList<>(book.getComment()))
                .build();
    }

    @Override
    @Transactional
    public List<Book> getBooksByTitle(String title) {
        return generateBookList(bookRepository.findBooksByTitle(title));
    }

    @Override
    @Transactional
    public List<Book> getBooks() {
        return generateBookList(bookRepository.findAll());
    }

    @Override
    @Transactional
    public List<Book> getBooksByAuthorName(String name) {
        return generateBookList(bookRepository.findBooksByAuthor_Name(name));
    }

    @Override
    @Transactional
    public void addNewCommentByBookId(long id, Comment comment) {
        bookRepository.addCommentToBook(id, comment);
    }

    @Override
    @Transactional
    public void updateBookTitle(long id, String title) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.setTitle(title);
    }

    private List<Book> generateBookList(List<Book> resultList) {
        List<Book> books = new ArrayList<>();
        resultList.forEach(b -> books.add(Book.builder()
                .id(b.getId())
                .title(b.getTitle())
                .author(new Author(b.getAuthor().getId(), b.getAuthor().getName()))
                .genre(new Genre(b.getGenre().getId(), b.getGenre().getGenreName()))
                .build()));
        return books;
    }
}
