package ru.otus.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository должен")
@DataMongoTest
class BookRepositoryTest {
    private static final String ACTUAL_BOOK_TITLE = "Властелин колец";
    private static final String ACTUAL_BOOK_AUTHOR = "Толкиен";
    private static final String ACTUAL_BOOK_GENRE = "Фэнтэзи";
    private static final String ACTUAL_COMMENT_1 = "не плохо";
    private static final String ACTUAL_COMMENT_2 = "бывало и лучше";
    private static final String SAVED_BOOK_ID = "1";
    private static final String SAVED_BOOK_TITLE = "Lord of the Ring";
    private static final String SAVED_BOOK_AUTHOR = "Tolkien";
    private static final String SAVED_BOOK_GENRE = "Fantasy";
    private static final String SAVED_COMMENT_1 = "amazing";
    private static final String SAVED_COMMENT_2 = "great";
    private static final String SAVED_COMMENT_3 = "very nice";

    private Book savedBook;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        savedBook = new Book();
        savedBook.setId(SAVED_BOOK_ID);
        savedBook.setTitle(SAVED_BOOK_TITLE);
        savedBook.addAuthor(new Author(SAVED_BOOK_AUTHOR));
        savedBook.addGenre(new Genre(SAVED_BOOK_GENRE));
        savedBook.getComments().addAll(List.of(new Comment(SAVED_COMMENT_1), new Comment(SAVED_COMMENT_2), new Comment(SAVED_COMMENT_3)));
    }

    @Test
    @DisplayName("должен добавлять новую книгу с новым автором, жанром и комментарием")
    public void shouldAddNewBook() {
        Book book = new Book();
        book.setTitle(ACTUAL_BOOK_TITLE);
        book.addAuthor(new Author(ACTUAL_BOOK_AUTHOR));
        book.addGenre(new Genre(ACTUAL_BOOK_GENRE));
        book.getComments().addAll(List.of(new Comment(ACTUAL_COMMENT_1), new Comment(ACTUAL_COMMENT_2)));
        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).extracting("title", "authors", "genres", "comments")
                .containsExactly(ACTUAL_BOOK_TITLE,
                        Set.of(new Author(ACTUAL_BOOK_AUTHOR)),
                        Set.of(new Genre(ACTUAL_BOOK_GENRE)),
                        List.of(new Comment(ACTUAL_COMMENT_1),new Comment(ACTUAL_COMMENT_2))
                );
    }

    @Test
    @DisplayName("должен доставать книгу по id")
    public void shouldGetBookById() {
        Book book = bookRepository.findById(SAVED_BOOK_ID).get();
        assertThat(book).isNotNull().isEqualTo(savedBook);
    }


    @Test
    @DisplayName("должен получать список книг")
    public void shouldGetListOfBooks() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3).anyMatch(b -> b.equals(savedBook));
    }

    @Test
    @DisplayName("должен удалять книгу по id")
    public void shouldRemoveBookById() {
        bookRepository.deleteById(SAVED_BOOK_ID);
        assertThat(bookRepository.findById(SAVED_BOOK_ID)).isEmpty();

        bookRepository.save(savedBook);
    }

    @Test
    @DisplayName("должен доставать книгу по названию")
    public void shouldGetBookByTitle() {
        List<Book> books = bookRepository.findBooksByTitle(SAVED_BOOK_TITLE);
        assertThat(books).hasSize(1).contains(savedBook);
    }

    @Test
    @DisplayName("должен доставать книгу по автору")
    public void shouldGetBookByAuthor() {
        List<Book> books = bookRepository.findBooksByAuthors(new Author(SAVED_BOOK_AUTHOR));
        assertThat(books).hasSize(1).contains(savedBook);
    }

}