package ru.otus.library.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BookDaoJdbc.class)
@DisplayName("Dao для работы с книгами")
class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;


    @Test
    @DisplayName("должен добавлять книги в базу")
    void shouldAddBook() {
        Genre genre = new Genre(1L, "Проза");
        Author author = new Author(1L, "Толстой");
        Book book = new Book("Анна Каренина", author, genre);
        long id = bookDaoJdbc.addBook(book);
        assertThat(id).isEqualTo(3);
    }

    @Test
    @DisplayName("должен извлекать книгу по id")
    void shouldGetBookById() {
        long expectedId = 1L;
        String expectedTitle = "Властелин колец";
        String expectedAuthor = "Толкиен";
        String expectedGenre = "Фэнтэзи";
        Book actualBook = bookDaoJdbc.getBookById(expectedId).get();

        assertThat(actualBook.getTitle()).isNotNull().isEqualTo(expectedTitle);
        assertThat(actualBook.getAuthor().getName()).isNotNull().isEqualTo(expectedAuthor);
        assertThat(actualBook.getGenre().getGenre()).isNotNull().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен извлекать книгу по title")
    void shouldGetBookByTitle() {
        long expectedId = 1L;
        String expectedTitle = "Властелин колец";
        String expectedAuthor = "Толкиен";
        String expectedGenre = "Фэнтэзи";
        Book actualBook = bookDaoJdbc.getBookByTitle(expectedTitle).get();

        assertThat(actualBook.getTitle()).isNotNull().isEqualTo(expectedTitle);
        assertThat(actualBook.getAuthor().getName()).isNotNull().isEqualTo(expectedAuthor);
        assertThat(actualBook.getGenre().getGenre()).isNotNull().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен возвращать пустой Optional при неверном id")
    void shouldGetEmptyOptionalByWrongId() {
        Optional<Book> book = bookDaoJdbc.getBookById(3L);
        assertThat(book).isEmpty();
    }

    @Test
    @DisplayName("должен удалять книгу по id")
    void shouldDeleteBookById() {
        bookDaoJdbc.deleteBookById(1L);
        Optional<Book> actualBook = bookDaoJdbc.getBookById(1L);
        assertThat(actualBook).isEqualTo(Optional.empty());
    }


    @Test
    @DisplayName("должен извлекать список всех книг")
    void shouldGetAllBooks() {
        List<Book> allBooks = bookDaoJdbc.getAllBook();
        assertThat(allBooks).isNotEmpty().contains(
                new Book(1L, "Властелин колец", new Author(3L,"Толкиен"), new Genre(3L,"Фэнтэзи")),
                new Book(2L, "Хоббит", new Author(3L,"Толкиен"), new Genre(3L,"Фэнтэзи")));
    }

    @Test
    @DisplayName("должен вносить изменения в книгу по id")
    void shouldMakeChangeInBook() {
        Book expectedBook = new Book(1L, "Властелин колец. Братство кольца",
                new Author(3L,"Толкиен"), new Genre(3L,"Фэнтэзи"));
        bookDaoJdbc.updateBookById(1L, expectedBook);
        Book actualBook = bookDaoJdbc.getBookById(1L).get();
        assertThat(actualBook).isNotNull().isEqualTo(expectedBook);

    }
}