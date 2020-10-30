package ru.otus.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ComponentScan("ru.otus.library.events")
@DisplayName("BookRepository должен")
class BookRepositoryTest {
    private static final String ACTUAL_BOOK_TITLE = "Властелин колец";
    private static final String ACTUAL_BOOK_AUTHOR = "Толкиен";
    private static final String ACTUAL_BOOK_GENRE = "Фэнтэзи";
    private static final String ACTUAL_COMMENT_1 = "не плохо";
    private static final String ACTUAL_COMMENT_2 = "бывало и лучше";
    private static final String SAVED_BOOK_ID = "1";
    private static final String SAVED_BOOK_TITLE = "Lord of the Ring";
    private static final String SAVED_BOOK_AUTHOR_ID = "1a";
    private static final String SAVED_BOOK_GENRE_ID_1 = "1g";
    private static final String SAVED_BOOK_GENRE_ID_3 = "3g";
    private static final String SAVED_BOOK_AUTHOR = "Tolkien";
    private static final String SAVED_BOOK_GENRE = "Fantasy";
    private static final String SAVED_COMMENT_1 = "amazing";
    private static final String SAVED_COMMENT_2 = "great";
    private static final String SAVED_COMMENT_3 = "very nice";
    private static final int SAVED_BOOK_LIST_SIZE = 4;


    private Book savedBook;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        savedBook = new Book();
        Author savedAuthor = authorRepository.findById(SAVED_BOOK_AUTHOR_ID).block();
        Genre savedGenre1 = genreRepository.findById(SAVED_BOOK_GENRE_ID_1).block();
        Genre savedGenre3 = genreRepository.findById(SAVED_BOOK_GENRE_ID_3).block();
        savedBook.setId(SAVED_BOOK_ID);
        savedBook.setTitle(SAVED_BOOK_TITLE);
        savedBook.addAuthor(savedAuthor);
        savedBook.addGenre(savedGenre1, savedGenre3);
        savedBook.getComments().addAll(List.of(new Comment(SAVED_COMMENT_1), new Comment(SAVED_COMMENT_2), new Comment(SAVED_COMMENT_3)));
        bookRepository.save(savedBook).block();
    }

    @Test
    @DisplayName("должен доставать книгу по id")
    public void shouldGetBookById() {
        Book book = bookRepository.findById(SAVED_BOOK_ID).block();
        assertThat(book).isNotNull().matches(b -> b.getTitle().equals(SAVED_BOOK_TITLE))
                .matches(b -> b.getAuthors().stream().anyMatch(a -> a.getName().equals(SAVED_BOOK_AUTHOR)))
                .matches(b -> b.getGenres().stream().anyMatch(g -> g.getName().equals(SAVED_BOOK_GENRE)));
    }


    @Test
    @DisplayName("должен получать список книг")
    public void shouldGetListOfBooks() {
        List<Book> books = bookRepository.findAll().collectList().block();
        assertThat(books).hasSize(SAVED_BOOK_LIST_SIZE).anyMatch(b -> b.equals(savedBook));
    }

    @Test
    @DisplayName("должен удалять книгу по id")
    public void shouldRemoveBookById() {
        bookRepository.deleteById(SAVED_BOOK_ID).block();
        assertThat(bookRepository.findById(SAVED_BOOK_ID).blockOptional()).isEmpty();
        bookRepository.save(savedBook).block();
    }


    @Test
    @DisplayName("должен доставать книгу по id автора")
    public void shouldGetBookByAuthor() {
        List<Book> books = bookRepository.findAllByAuthorId(SAVED_BOOK_AUTHOR_ID);
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("должен доставать книги по жанру")
    public void shouldGetBooksByGenre() {
        List<Book> books = bookRepository.findAllByGenreId(SAVED_BOOK_GENRE_ID_1);
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("должен сохранять нового автора жанр в коллекции после добавления новой книги")
    public void shouldSaveAuthorAfterSaveNewBook() {
        Book book = new Book();
        book.setTitle(ACTUAL_BOOK_TITLE);
        book.addAuthor(new Author(ACTUAL_BOOK_AUTHOR));
        book.addGenre(new Genre(ACTUAL_BOOK_GENRE));
        book.getComments().addAll(List.of(new Comment(ACTUAL_COMMENT_1), new Comment(ACTUAL_COMMENT_2)));
        Book savedBook = bookRepository.save(book).block();
        assertThat(savedBook).isNotNull();
        savedBook.getAuthors().forEach(a -> assertThat(a.getId() != null));
        savedBook.getGenres().forEach(g -> assertThat(g.getId() != null));
        bookRepository.delete(savedBook);
    }

    @Test
    @DisplayName("должен удалять автора из книги при удалении автора")
    public void shouldCascadeDeleteAuthor() {
        Book bookWith1Author = new Book();
        bookWith1Author.setTitle(ACTUAL_BOOK_TITLE);
        Author author = new Author(ACTUAL_BOOK_AUTHOR);
        bookWith1Author.addAuthor(author);
        bookWith1Author = bookRepository.save(bookWith1Author).block();
        authorRepository.delete(author).block();
        assert bookWith1Author != null;
        assertThat(bookRepository.findById(bookWith1Author.getId()).block())
                .matches(b -> b.getAuthors().isEmpty());
        bookRepository.delete(savedBook).block();

        Book bookWith2Authors = new Book();
        bookWith2Authors.setTitle(ACTUAL_BOOK_TITLE);
        Author author1 = new Author(ACTUAL_BOOK_AUTHOR);
        Author author2 = new Author(SAVED_BOOK_AUTHOR);
        bookWith2Authors.addAuthor(author1, author2);
        bookWith2Authors = bookRepository.save(bookWith2Authors).block();
        assertThat(bookWith2Authors).isNotNull();
        authorRepository.delete(author1).block();
        assertThat(bookRepository.findById(bookWith2Authors.getId()).blockOptional()).isNotEmpty()
                .map(Book::getAuthors).contains(List.of(author2));
        bookRepository.delete(bookWith2Authors).block();
    }

    @Test
    @DisplayName("должен удалять жанр из книги при удалении жанра")
    public void shouldCascadeDeleteGenre() {
        Book bookWith1Genre = new Book();
        bookWith1Genre.setTitle(ACTUAL_BOOK_TITLE);
        Genre genre = new Genre(ACTUAL_BOOK_GENRE);
        bookWith1Genre.addGenre(genre);
        bookWith1Genre = bookRepository.save(bookWith1Genre).block();
        assertThat(bookWith1Genre).isNotNull();
        genreRepository.delete(genre).block();
        assertThat(bookRepository.findById(bookWith1Genre.getId()).blockOptional().get()).isNotNull()
                .matches(b -> b.getGenres().isEmpty());
        bookRepository.delete(bookWith1Genre);

        Book bookWith2Genres = new Book();
        bookWith2Genres.setTitle(ACTUAL_BOOK_TITLE);
        Genre genre1 = new Genre(ACTUAL_BOOK_GENRE);
        Genre genre2 = new Genre(SAVED_BOOK_GENRE);
        bookWith2Genres.addGenre(genre1, genre2);
        bookWith2Genres = bookRepository.save(bookWith2Genres).block();
        genreRepository.delete(genre1).block();
        assertThat(bookRepository.findById(bookWith2Genres.getId()).block())
                .extracting(Book::getGenres).isEqualTo(List.of(genre2));
        bookRepository.delete(bookWith2Genres).block();
    }
}