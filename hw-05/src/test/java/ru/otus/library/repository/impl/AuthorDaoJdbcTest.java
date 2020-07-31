package ru.otus.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("Dao для работы с авторами")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc dao;

    @Test
    @DisplayName("должен возвращать автора по его id")
    void shouldReturnExpectedAuthorById() {
        String expectedName = "Толстой";
        Author author = dao.getAuthorById(1).get();
        assertThat(author.getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("должен возвращать автора по его имени")
    void shouldReturnAuthorByName() {
        long expectedId = 1L;
        Author actualAuthor = dao.getAuthorByName("Толстой").get();
        assertThat(actualAuthor.getId()).isEqualTo(expectedId);
    }


    @Test
    @DisplayName("должен добавлять автора в базу данных")
    void shouldAddAuthorToDataBase() {
        Author expectedAuthor = new Author(4L, "Лем");
        long id = dao.addAuthor(expectedAuthor);
        Author actualAuthor = dao.getAuthorById(id).get();
        assertThat(actualAuthor).isNotNull().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен удалять автора из базы")
    void shouldRemoveAuthorById() {
        dao.removeAuthor(1L);
        assertThatExceptionOfType(EmptyResultDataAccessException.class).isThrownBy(() -> {
            assertThat(dao.getAuthorById(1L).get());
        }).withMessage("Incorrect result size: expected 1, actual 0");
    }

    @Test
    @DisplayName("должен получать список авторов")
    void shouldReturnListOfAllAuthors() {
        List<Author> realAuthors = dao.getAllAuthors();
        assertThat(dao.getAllAuthors().size()).isEqualTo(3);
        assertThat(realAuthors).isNotNull()
                .contains(new Author(1L, "Толстой"),
                        new Author(2L, "Маяковский"),
                        new Author(3L, "Толкиен"));
    }
}