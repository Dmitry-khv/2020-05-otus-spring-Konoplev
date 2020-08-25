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
import java.util.Set;

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
    private static final String SAVED_BOOK_GENRE_ID = "1g";
    private static final String SAVED_BOOK_AUTHOR = "Tolkien";
    private static final String SAVED_BOOK_GENRE = "Fantasy";
    private static final String SAVED_COMMENT_1 = "amazing";
    private static final String SAVED_COMMENT_2 = "great";
    private static final String SAVED_COMMENT_3 = "very nice";
    private static final int SAVED_BOOK_LIST_SIZE = 3;


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
        Author savedAuthor = authorRepository.findById(SAVED_BOOK_AUTHOR_ID).get();
        Genre savedGenre = genreRepository.findById(SAVED_BOOK_GENRE_ID).get();
        savedBook.setId(SAVED_BOOK_ID);
        savedBook.setTitle(SAVED_BOOK_TITLE);
        savedBook.addAuthor(savedAuthor);
        savedBook.addGenre(savedGenre);
        savedBook.getComments().addAll(List.of(new Comment(SAVED_COMMENT_1), new Comment(SAVED_COMMENT_2), new Comment(SAVED_COMMENT_3)));
    }

    @Test
    @DisplayName("должен доставать книгу по id")
    public void shouldGetBookById() {
        Book book = bookRepository.findById(SAVED_BOOK_ID).get();
        assertThat(book).isNotNull().matches(b -> b.getTitle().equals(SAVED_BOOK_TITLE))
                .matches(b -> b.getAuthors().stream().anyMatch(a -> a.getName().equals(SAVED_BOOK_AUTHOR)))
                .matches(b -> b.getGenres().stream().anyMatch(g -> g.getName().equals(SAVED_BOOK_GENRE)));
    }


    @Test
    @DisplayName("должен получать список книг")
    public void shouldGetListOfBooks() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(SAVED_BOOK_LIST_SIZE).anyMatch(b -> b.equals(savedBook));
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
    @DisplayName("должен доставать книгу по id автора")
    public void shouldGetBookByAuthor() {
        List<Book> books = bookRepository.findAllByAuthorId(SAVED_BOOK_AUTHOR_ID);
        assertThat(books).hasSize(2).contains(savedBook);
    }

    @Test
    @DisplayName("должен доставать книги по жанру")
    public void shouldGetBooksByGenre() {
        List<Book> books = bookRepository.findAllByGenreName(SAVED_BOOK_GENRE);
        assertThat(books).hasSize(2).contains(savedBook);
    }

    @Test
    @DisplayName("должен сохранять нового автора жанр в коллекции после добавления новой книги")
    public void shouldSaveAuthorAfterSaveNewBook() {
        Book book = new Book();
        book.setTitle(ACTUAL_BOOK_TITLE);
        book.addAuthor(new Author(ACTUAL_BOOK_AUTHOR));
        book.addGenre(new Genre(ACTUAL_BOOK_GENRE));
        book.getComments().addAll(List.of(new Comment(ACTUAL_COMMENT_1), new Comment(ACTUAL_COMMENT_2)));
        Book savedBook = bookRepository.save(book);
        savedBook.getAuthors().forEach(a-> assertThat(a.getId() != null));
        savedBook.getGenres().forEach(g-> assertThat(g.getId() != null));
        bookRepository.delete(savedBook);
    }

    @Test
    @DisplayName("должен удалять автора из книги при удалении автора")
    public void shouldDeleteBookIfHasNotAuthors() {
        Book bookWith1Author = new Book();
        bookWith1Author.setTitle(ACTUAL_BOOK_TITLE);
        Author author = new Author(ACTUAL_BOOK_AUTHOR);
        bookWith1Author.addAuthor(author);
        bookWith1Author = bookRepository.save(bookWith1Author);
        assertThat(bookWith1Author.getId()).isNotEqualTo(null);
        authorRepository.delete(author);
        assertThat(bookRepository.findById(bookWith1Author.getId()).get()).isNotNull()
                .matches(b -> b.getAuthors().isEmpty());
        bookRepository.delete(bookWith1Author);

        Book bookWith2Authors = new Book();
        bookWith2Authors.setTitle(ACTUAL_BOOK_TITLE);
        Author author1 = new Author(ACTUAL_BOOK_AUTHOR);
        Author author2 = new Author(SAVED_BOOK_AUTHOR);
        bookWith2Authors.addAuthor(author1, author2);
        bookWith2Authors = bookRepository.save(bookWith2Authors);
        assertThat(bookWith2Authors.getId()).isNotEqualTo(null);
        authorRepository.delete(author1);
        assertThat(bookRepository.findById(bookWith2Authors.getId())).isNotEmpty()
                .map(Book::getAuthors).contains(Set.of(author2));
        bookRepository.delete(bookWith2Authors);
    }

}