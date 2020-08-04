package ru.otus.library.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.jpa.BookDaoJpa;

import static org.assertj.core.api.Assertions.assertThat;

@Import(BookDaoJpa.class)
@DisplayName("Service для книги")
class LibraryServiceImplTest {
    @Autowired
    private LibraryServiceImpl dbServiceBook;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен корректно добавлять новую книгу")
    public void shouldAddBookWithoutId() {
        String title = "newTitle";
        Author author = new Author("newAuthor");
        Genre genre = new Genre("newGenre");
        Book expectedBook = new Book(title, author, genre);
        long id = dbServiceBook.saveBook(expectedBook);
        Book actualBook = em.find(Book.class, id);
        assertThat(actualBook).isNotNull()
                .matches(b -> b.getId() == id)
                .matches(b -> b.getTitle().equals(title))
                .matches(b -> b.getAuthor().getName().equals(author.getName()))
                .matches(b -> b.getGenre().getGenre().equals(genre.getGenre()));
    }
}