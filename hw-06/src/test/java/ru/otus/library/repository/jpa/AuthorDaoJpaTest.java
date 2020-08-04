package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для авторов")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {
    private static final String ACTUAL_NAME = "Маяковский";
    private static final String EXPECTED_NAME = "Лем";
    private static final long ACTUAL_ID = 1L;
    private static final long EXPECTED_ID = 3L;
    private static final int AUTHORS_COUNT = 2;

    @Autowired
    private AuthorDaoJpa authorDaoJpa;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Должен добавлять нового автора и возвращать сгенерированный id")
    public void shouldAddNewAuthor() {
        Author expectedAuthor = new Author(EXPECTED_NAME);
        long id = authorDaoJpa.addAuthor(expectedAuthor);
        assertThat(id).isEqualTo(EXPECTED_ID);
    }

    @Test
    @DisplayName("должет обновлять данные автора если id установлен")
    public void shouldUpdateAuthorIfIdKnown() {
        Author expectedAuthor = new Author(ACTUAL_ID, EXPECTED_NAME);
        long id = authorDaoJpa.addAuthor(expectedAuthor);
        assertThat(id).isEqualTo(ACTUAL_ID);
    }

    @Test
    @DisplayName("должен удальть автора по id")
    public void shouldRemoveAuthorById() {
        authorDaoJpa.removeAuthor(ACTUAL_ID);
        Author actualAuthor = em.find(Author.class, ACTUAL_ID);
        assertThat(actualAuthor).isNull();
    }

    @Test
    @DisplayName("должен искать по id")
    public void shouldFindById() {
        Author expectedAuthor = new Author(ACTUAL_ID, ACTUAL_NAME);
        Author actualAuthor = authorDaoJpa.getAuthorById(ACTUAL_ID).get();
        assertThat(actualAuthor).isNotNull().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен искать по имени")
    public void shouldFindByName() {
        Author expectedAuthor = new Author(ACTUAL_ID, ACTUAL_NAME);
        Author actualAuthor = authorDaoJpa.getAuthorByName(ACTUAL_NAME).get();
        assertThat(actualAuthor).isNotNull().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен возвращать список авторов")
    public void shouldReturnListOfAuthors() {
        Author expectedAuthor = new Author(ACTUAL_ID, ACTUAL_NAME);
        List<Author> authors = authorDaoJpa.getAllAuthors();
        assertThat(authors).isNotEmpty().hasSize(AUTHORS_COUNT)
                .allMatch(a -> !a.getName().equals(""))
                .anyMatch(a -> a.equals(expectedAuthor));
    }
}