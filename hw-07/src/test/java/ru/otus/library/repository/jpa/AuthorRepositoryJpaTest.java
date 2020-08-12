package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для авторов")
@DataJpaTest
class AuthorRepositoryJpaTest {
    private static final String ACTUAL_NAME = "Маяковский";
    private static final long ACTUAL_ID = 1L;
    private static final String SAVED_AUTHOR_NAME = "Пушкин";
    private static final long SAVED_AUTHOR_ID = 3L;



    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен сохранять автора")
    public void shouldAddAuthor() {
        Author author = new Author(SAVED_AUTHOR_NAME);
        authorRepository.save(author);
        Author actualAuthor = em.find(Author.class, SAVED_AUTHOR_ID);
        assertThat(actualAuthor.getId()).isNotNull().isEqualTo(SAVED_AUTHOR_ID);
        System.out.println(actualAuthor.toString());
    }

    @Test
    @DisplayName("должен удальть автора по id")
    public void shouldRemoveAuthorById() {
        authorRepository.deleteById(ACTUAL_ID);
        Author actualAuthor = em.find(Author.class, ACTUAL_ID);
        assertThat(actualAuthor).isNull();
    }

    @Test
    @DisplayName("должен искать по id")
    public void shouldFindById() {
        Author expectedAuthor = new Author(ACTUAL_ID, ACTUAL_NAME);
        Author actualAuthor = authorRepository.findById(ACTUAL_ID).get();
        assertThat(actualAuthor).isNotNull().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен искать по имени")
    public void shouldFindByName() {
        Author expectedAuthor = new Author(ACTUAL_ID, ACTUAL_NAME);
        Author actualAuthor = authorRepository.findByName(ACTUAL_NAME).get();
        assertThat(actualAuthor).isNotNull().isEqualTo(expectedAuthor);
    }

}