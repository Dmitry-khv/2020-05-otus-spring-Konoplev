package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookDao;
import ru.otus.library.repository.jpa.AuthorDaoJpa;
import ru.otus.library.repository.jpa.BookDaoJpa;
import ru.otus.library.repository.jpa.GenreDaoJpa;
import ru.otus.library.service.LibraryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryServiceImpl.class);
    private final BookDaoJpa bookDao;
    private final AuthorDaoJpa authorDao;
    private final GenreDaoJpa genreDao;

    @Override
    @Transactional
    public long saveBook(Book book) {
        if (book.getAuthor().getId() == 0) {
            authorDao.getAuthorByName(book.getAuthor().getName()).ifPresent(book::setAuthor);
        }
        if(book.getGenre().getId() == 0) {
            genreDao.getGenreByName(book.getGenre().getGenre()).ifPresent(book::setGenre);
        }
        return bookDao.insertOrUpdateBook(book);
    }

    @Override
    @Transactional
    public long updateBookTitle(long id, String title) {
        Book book = bookDao.getBookById(id).get();
        book.setTitle(title);
        return bookDao.insertOrUpdateBook(book);
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookDao.deleteBookById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Book getBookById(long id) {
        Optional<Book> book = bookDao.getBookById(id);
        return book.orElse(null);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> getBookByTitle(String title) {
        return bookDao.getBookByTitle(title);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> getListBooks()  {
        return bookDao.getAllBook();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> getBooksByAuthorName(String name) {
        return bookDao.getBooksByAuthorName(name);
    }

    @Override
    @Transactional
    public void addNewCommentToBookById(long id, String commentText) {
        bookDao.addNewCommentToBookById(id, commentText);
    }

    @Override
    @Transactional
    public void updateBookById(long id, String title, String authorName, String genreName) {
        Book book = bookDao.getBookById(id).get();
        book.setTitle(title);
        book.setAuthor(new Author(authorName));
        book.setGenre(new Genre(genreName));
        bookDao.insertOrUpdateBook(book);
    }
}