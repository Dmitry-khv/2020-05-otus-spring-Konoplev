package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.jpa.AuthorDaoJpa;
import ru.otus.library.repository.jpa.BookDaoJpa;
import ru.otus.library.repository.jpa.GenreDaoJpa;
import ru.otus.library.service.LibraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        Book transactionalBook = bookDao.getBookById(id).orElse(new Book());
        Book book = Book.builder().id(id)
                .title(transactionalBook.getTitle())
                .author(new Author(transactionalBook.getAuthor().getId(), transactionalBook.getAuthor().getName()))
                .genre(new Genre(transactionalBook.getGenre().getId(), transactionalBook.getGenre().getGenre()))
                .comment(new ArrayList<>(transactionalBook.getComment()))
                .build();
        return book;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> getBookByTitle(String title) {
        return generateBookList(bookDao.getBookByTitle(title));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> getListBooks()  {
        return bookDao.getAllBooks();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Book> getBooksByAuthorName(String name) {
        List<Book> books = generateBookList(bookDao.getAllBooks())
                .stream().filter(b -> b.getAuthor().getName().equals(name))
                .collect(Collectors.toList());
        return books;
    }

    @Override
    @Transactional
    public void addNewCommentToBookById(long id, String commentText) {
        Optional<Book> book = bookDao.getBookById(id);
        book.ifPresent(b -> b.getComment().add(new Comment(commentText)));
    }

    private List<Book> generateBookList(List<Book> resultList) {
        List<Book> books = new ArrayList<>();
        resultList.forEach(b -> books.add(Book.builder()
                .id(b.getId())
                .title(b.getTitle())
                .author(b.getAuthor())
                .genre(b.getGenre())
                .build()));
        return books;
    }
}