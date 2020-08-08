package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final LibraryService libraryService;

    //add newBook newAuthor newGenre
    //add newBook Толстой Проза
    @ShellMethod(value = "add new book", key = {"add", "insert"})
    public String addBook(String title, String authorName, String genreName) {
        Book book = Book.builder()
                .title(title)
                .author(new Author(authorName))
                .genre(new Genre(genreName))
                .build();
        long id = libraryService.saveBook(book);
        return String.format("Added new book id: %d", id);
    }

    //id 1
    @ShellMethod(value = "get book by id", key = "id")
    public String getBookById(long id) {
        Book book = libraryService.getBookById(id);
        return String.format("Book: %s", book.toString());
    }

    //title Хоббит
    @ShellMethod(value = "get book by title", key = {"t", "title"})
    public String getBookByTitle(String title) {
        List<Book> books = libraryService.getBookByTitle(title);
        return String.format("Books with tittle: %s\n%s", title, books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n")));
    }

    // author-books Толкиен
    @ShellMethod(value = "get books by author name" , key = "author-books")
    public String getBookByAuthorName(String authorName) {
        List<Book> books = libraryService.getBooksByAuthorName(authorName);
        return String.format("Books with author: %s\n%s", authorName, books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n")));
    }

    @ShellMethod(value = "get list of books", key = "list")
    public String getAllBooks() {
        return String.format("Books:\n%s", libraryService.getListBooks().stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n")));
    }

    //upd 1 "Властелин"
    @ShellMethod(value = "update book", key = {"upd", "update"})
    public String updateBookTitleById(long id, String title) {
        libraryService.updateBookTitle(id, title);
        return String.format("Book updated id:%d", id);
    }

    @ShellMethod(value = "remove book by id", key = {"rm", "remove"})
    public String removeBookById(long id) {
        libraryService.deleteBookById(id);
        return String.format("book was deleted id:%d", id);
    }

    //comment-id 1
    @ShellMethod(value = "get comments by book id", key = {"cid", "comment-id"})
    public String getCommentsForBook(long id) {
        List<Comment> comments = libraryService.getBookById(id).getComment();
        if(!comments.isEmpty()) {
            return String.format("Comments for book id: %d:\n%s", id,
                    comments.stream().map(Comment::getComment).collect(Collectors.joining("\n")));
        } else {
            return "No comments";
        }
    }

    //add-comment 1 замечательно
    @ShellMethod(value = "add comment to book", key = {"ac", "add-comment"})
    public String addCommentToBook(long id, String commentText) {
        libraryService.addNewCommentToBookById(id, commentText);
        return String.format("Added comment: %s to book id: %d", commentText, id);
    }
}
