package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreDaoJpa.class)
@DisplayName("Репозиторий на основе JPA для жанров")
class GenreDaoJpaTest {
    private static final String FIRST_GENRE_NAME = "Научная литература";
    private static final String SECOND_GENRE_NAME = "Фэнтэзи";

    @Autowired
    private GenreDaoJpa genreDaoJpa;
    @Autowired
    private TestEntityManager em;


    @Test
    @DisplayName("должен добавлять новый жанр")
    public void shouldAddNewGenre() {
        String fifthGenreName = "Роман";
        long id = genreDaoJpa.addGenre(new Genre(fifthGenreName));
        Genre actualGenre = em.find(Genre.class, id);
        assertThat(id).isEqualTo(3L);
        assertThat(actualGenre.getGenre()).isNotNull().isEqualTo(fifthGenreName);
    }

    @Test
    @DisplayName("должен доставать жанр по id")
    public void shouldGetGenreById() {
        Genre actualGenre = genreDaoJpa.getGenreById(1L).get();
        assertThat(actualGenre).isNotNull().extracting(Genre::getGenre).isEqualTo(FIRST_GENRE_NAME);
    }

    @Test
    @DisplayName("должен доставать жанр по названию")
    public void shouldGetGenreByName() {
        Genre actualGenre = genreDaoJpa.getGenreByName(FIRST_GENRE_NAME).get();
        assertThat(actualGenre).isNotNull().extracting(Genre::getId).isEqualTo(1L);
    }

    @Test
    @DisplayName("должен доставать список жанров")
    public void shouldGetListOfGenres() {
        List<Genre> genres = genreDaoJpa.getAllGenres();
        assertThat(genres).isNotNull().hasSize(2)
                .anyMatch(genre -> genre.getGenre().equals(FIRST_GENRE_NAME))
                .anyMatch(genre -> genre.getGenre().equals(SECOND_GENRE_NAME));
    }
}