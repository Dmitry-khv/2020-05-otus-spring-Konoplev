package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий на основе JPA для жанров")
class GenreRepositoryJpaTest {
    private static final String FIRST_GENRE_NAME = "Научная литература";
    private static final String SECOND_GENRE_NAME = "Фэнтэзи";

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("должен добавлять новый жанр")
    public void shouldAddNewGenre() {
        String fifthGenreName = "Роман";
        Genre savedGenre = genreRepository.save(new Genre(fifthGenreName));
        Genre actualGenre = em.find(Genre.class, savedGenre.getId());
        assertThat(actualGenre).isNotNull().isEqualTo(savedGenre);
    }

    @Test
    @DisplayName("должен доставать жанр по id")
    public void shouldGetGenreById() {
        Genre actualGenre = genreRepository.findById(1L).get();
        assertThat(actualGenre).isNotNull().extracting(Genre::getGenreName).isEqualTo(FIRST_GENRE_NAME);
    }

    @Test
    @DisplayName("должен доставать жанр по названию")
    public void shouldGetGenreByName() {
        Genre actualGenre = genreRepository.findGenreByGenreName(FIRST_GENRE_NAME).get();
        assertThat(actualGenre).isNotNull().extracting(Genre::getId).isEqualTo(1L);
    }

    @Test
    @DisplayName("должен доставать список жанров")
    public void shouldGetListOfGenres() {
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres).isNotNull().hasSize(2)
                .anyMatch(genre -> genre.getGenreName().equals(FIRST_GENRE_NAME))
                .anyMatch(genre -> genre.getGenreName().equals(SECOND_GENRE_NAME));
    }
}