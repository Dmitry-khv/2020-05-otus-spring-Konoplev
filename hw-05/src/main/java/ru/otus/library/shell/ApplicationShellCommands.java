package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.DBServiceAuthor;
import ru.otus.library.service.DBServiceBook;
import ru.otus.library.service.DBServiceGenre;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final DBServiceBook serviceBook;
    private final DBServiceAuthor serviceAuthor;
    private final DBServiceGenre serviceGenre;

    @ShellMethod(value = "add new book", key = {"add", "insert"})
    public String addBook(String title, String author, String genre) {
        long authorId = checkAuthor(author);
        long genreId = checkGenre(genre);
        long bookId = serviceBook.addBook(new Book(title, new Author(authorId, author), new Genre(genreId, genre)));
        return String.format("Added new book id:%d", bookId);
    }

    @ShellMethod(value = "get book by id", key = "id")
    public String getBookById(long id) {
        Book book = serviceBook.getBookById(id);
        return String.format("Got book: %s", book.toString());
    }

    @ShellMethod(value = "get book by title", key = {"n", "name"})
    public String getBookByTitle(String title) {
        Book book = serviceBook.getBookByTitle(title);
        return String.format("Got book: %s", book.toString());
    }

    @ShellMethod(value = "get list of books", key = {"l", "list"})
    public String getAllBooks() {
        return String.format("List of books:\n%s", serviceBook.getListBooks().stream().map(Book::toString).collect(Collectors.joining("\n")));
    }

    @ShellMethod(value = "update book", key = {"u", "update"})
    public String updateBookById(long id, String title, String author, String genre) {
        long authorId = checkAuthor(author);
        long genreId = checkGenre(genre);
        serviceBook.updateBookById(id, new Book(title, new Author(authorId, author), new Genre(genreId, genre)));
        return String.format("Book updated id:%d", id);
    }

    @ShellMethod(value = "remove book by id", key = {"r", "remove"})
    public String removeBookById(long id) {
        serviceBook.deleteBookById(id);
        return String.format("book was deleted id:%d", id);
    }

    private long checkGenre(String genreName) {
        Genre genre = serviceGenre.getGenreByName(genreName);
        if (genre != null) {
            return genre.getId();
        } else {
            return serviceGenre.addGenre(new Genre(genreName));
        }
    }

    public long checkAuthor(String authorName) {
        Author author = serviceAuthor.getAuthorByName(authorName);
        if(author != null) {
            return author.getId();
        } else {
            return serviceAuthor.addAuthor(new Author(authorName));
        }
    }
}
