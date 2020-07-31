package ru.otus.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreDao;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("Dao для работы с жанрами")
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("должен корректо добавлять жанр и извлекать его по id")
    void addAndGetGenre() {
        String expectedGenre = "Фэнтэзи";
        String actualGenre = genreDao.getGenreById(3L).get().getGenre();
        assertThat(actualGenre).isNotNull().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен извлекать жанр по названию")
    void shouldGetGenreByName() {
        long expectedGenreId = 3L;
        Genre actualGenre = genreDao.getGenreByName("Фэнтэзи").get();
        assertThat(actualGenre.getId()).isEqualTo(expectedGenreId);
    }

    @Test
    void getListGenres() {
        List<Genre> actualGenres = genreDao.getAllGenres();
        assertThat(actualGenres.size()).isEqualTo(3);
        assertThat(actualGenres).contains(new Genre(1L, "Проза"),
                new Genre(2L, "Научная литература"),
                new Genre(3L, "Фэнтэзи"));
    }
}