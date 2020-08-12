package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для книги")
@DataJpaTest
class BookRepositoryJpaTest {
    private static final long EXPECTED_BOOK_ID = 3L;
    private static final String EXPECTED_BOOK_TITLE = "Анна Каренина";
    private static final String EXPECTED_BOOK_AUTHOR = "Толстой";
    private static final String EXPECTED_BOOK_GENRE = "Проза";
    private static final String EXPECTED_COMMENT = "Не читал";
    private static final long ACTUAL_BOOK_ID = 1L;
    private static final String ACTUAL_BOOK_TITLE = "Властелин колец";
    private static final String ACTUAL_BOOK_AUTHOR = "Толкиен";
    private static final long ACTUAL_BOOK_AUTHOR_ID = 2L;
    private static final String ACTUAL_BOOK_GENRE = "Фэнтэзи";
    private static final long ACTUAL_BOOK_GENRE_ID = 2L;
    private static final String ACTUAL_COMMENT_1 = "не плохо";
    private static final String ACTUAL_COMMENT_2 = "бывало и лучше";
    private static final int COMMENT_LIST_SIZE = 2;


    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен добавлять новую книгу с новым автором, жанром и комментарием")
    public void shouldAddNewBook() {
        Author author = new Author(0, EXPECTED_BOOK_AUTHOR);
        Genre genre = new Genre(0, EXPECTED_BOOK_GENRE);
        Comment comment = new Comment(0, EXPECTED_COMMENT);
        List<Comment> comments = Collections.singletonList(comment);

        Book expectedBook = new Book(0, EXPECTED_BOOK_TITLE, author, genre, comments);
        Book savedBook = bookRepository.save(expectedBook);
        assertThat(savedBook.getId()).isEqualTo(EXPECTED_BOOK_ID);

        Book actualBook = em.find(Book.class, EXPECTED_BOOK_ID);
        String actualName = actualBook.getAuthor().getName();
        String actualGenre = actualBook.getGenre().getGenreName();
        String actualComment = actualBook.getComment().get(0).getComment();

        assertThat(actualBook).isNotNull()
                .extracting(Book::getTitle)
                .isNotNull()
                .isEqualTo(EXPECTED_BOOK_TITLE);
        assertThat(actualName).isNotNull().isEqualTo(EXPECTED_BOOK_AUTHOR);
        assertThat(actualGenre).isNotNull().isEqualTo(EXPECTED_BOOK_GENRE);
        assertThat(actualComment).isNotNull().isEqualTo(EXPECTED_COMMENT);
    }

    @Test
    @DisplayName("должен доставать книгу по id")
    public void shouldGetBookById() {
        Book book = bookRepository.findById(ACTUAL_BOOK_ID).get();
        assertThat(book).isNotNull()
                .extracting("title", "author", "genre")
                .containsExactly(
                        ACTUAL_BOOK_TITLE,
                        new Author(ACTUAL_BOOK_AUTHOR_ID, ACTUAL_BOOK_AUTHOR),
                        new Genre(ACTUAL_BOOK_GENRE_ID, ACTUAL_BOOK_GENRE));
    }


    @Test
    @DisplayName("должен получать список книг")
    public void shouldGetListOfBooks() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(2)
                .allMatch(b -> !b.getTitle().equals(""))
                .anyMatch(b -> b.getTitle().equals(ACTUAL_BOOK_TITLE))
                .anyMatch(b -> b.getAuthor().getName().equals(ACTUAL_BOOK_AUTHOR))
                .anyMatch(b -> b.getGenre().getGenreName().equals(ACTUAL_BOOK_GENRE));
    }

    @Test
    @DisplayName("должен удалять книгу и комментарии к ней по id")
    public void shouldRemoveBookById() {
        bookRepository.deleteById(ACTUAL_BOOK_ID);
        Optional<Book> book = bookRepository.findById(ACTUAL_BOOK_ID);
        assertThat(book).isEmpty();
        Comment comment1 = em.find(Comment.class, 1L);
        assertThat(comment1).isNull();
        Comment comment2 = em.find(Comment.class, 2L);
        assertThat(comment2).isNull();
    }

    @Test
    @DisplayName("должен доставать список комментариев к книге по ее id")
    public void shouldGetAllComment() {
        List<Comment> comments = bookRepository.findById(ACTUAL_BOOK_ID).get().getComment();
        assertThat(comments).isNotNull().hasSize(COMMENT_LIST_SIZE)
                .anyMatch(c -> c.getComment().equals(ACTUAL_COMMENT_1))
                .anyMatch(c -> c.getComment().equals(ACTUAL_COMMENT_2));
    }
}