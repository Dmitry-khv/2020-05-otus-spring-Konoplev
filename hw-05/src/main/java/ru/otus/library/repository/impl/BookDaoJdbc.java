package ru.otus.library.repository.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final static Logger LOG = LoggerFactory.getLogger(BookDaoJdbc.class);
    private final NamedParameterJdbcOperations jdbcOperations;

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getNString("title");

            long authId = rs.getLong("author_id");
            String authName = rs.getNString("name");
            Author author = new Author(authId, authName);

            long genreId = rs.getLong("genre_id");
            String genreName = rs.getNString("genre");
            Genre genre = new Genre(genreId, genreName);

            return new Book(id, title, author, genre);
        }
    }

    @Override
    public long addBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insert = "insert into books (title, author_id, genre_id) values(:title, :author_id, :genre_id)";
        jdbcOperations.update(insert, new MapSqlParameterSource(Map.of(
                "title", book.getTitle(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId())),
                keyHolder);
        LOG.info("Book: {} added to database", book.toString());
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            String select = "select b.id, b.title, b.author_id, a.name, b.genre_id, g.genre from books b " +
                    "inner join authors a on (b.author_id = a.id) " +
                    "inner join genres g on (b.genre_id = g.id) where b.id= :id";
            Book book = jdbcOperations.queryForObject(select, params, new BookMapper());
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            LOG.error(String.format("Book is not found id:%d", id), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        Map<String, Object> params = Collections.singletonMap("title", title);
        try {
            String select = "select b.id, b.title, b.author_id, a.name, b.genre_id, g.genre from books b " +
                    "inner join authors a on (b.author_id = a.id) " +
                    "inner join genres g on (b.genre_id = g.id) where b.title= :title";
            Book book = jdbcOperations.queryForObject(select, params, new BookMapper());
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            LOG.error(String.format("Book is not found title:%s", title), e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAllBook() {
        String select = "select b.id, b.title, b.author_id, a.name, b.genre_id, g.genre from books b " +
                "inner join authors a on (b.author_id = a.id) " +
                "inner join genres g on (b.genre_id = g.id)";
        List<Book> books = jdbcOperations.query(select, new BookMapper());
        return books;
    }

    @Override
    public void updateBookById(long id, Book book) {
        Map<String, Object> params = Map.of("id", id, "title", book.getTitle(),
                "author", book.getAuthor().getId(), "genre", book.getGenre().getId());
        String updateTitle = "update books set title= :title, author_id= :author, genre_id= :genre where id= :id";
        Optional<Book> updatedBook = getBookById(id);
        updatedBook.ifPresent(b -> jdbcOperations.update(updateTitle, params));
        LOG.info("Book was changed to {}", updatedBook.toString());
    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String delete = "delete from books where id= :id";
        jdbcOperations.update(delete, params);
        LOG.info("Book with id:{} was deleted", id);
    }
}
