package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для авторов")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {
    private static final String ACTUAL_NAME = "Маяковский";
    private static final long ACTUAL_ID = 1L;

    @Autowired
    private AuthorDaoJpa authorDaoJpa;
    @Autowired
    private TestEntityManager em;

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

}