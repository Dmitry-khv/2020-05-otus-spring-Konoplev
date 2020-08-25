package ru.otus.library.shell;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.impl.DBBookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final DBBookServiceImpl bookService;

    //add newBook newAuthor newGenre
    //add newBook Толстой Проза
    @ShellMethod(value = "add new book", key = {"add", "insert"})
    public String addBook(String title, String authorName, String genreName) {
        Author author = new Author(authorName);
        Genre genre = new Genre(genreName);
        Book book = new Book();
        book.setTitle(title);
        book.addAuthor(author);
        book.addGenre(genre);
        Book savedBook = bookService.saveBook(book);
        return String.format("Added new book id: %s", savedBook.toString());
    }

    //id 1
    @ShellMethod(value = "get book by id", key = "id")
    public String getBookById(String id) {
        Book book = bookService.getBookById(id);
        return String.format("Book: %s", book.toString());
    }

    //title "Lord of the Ring"
    @ShellMethod(value = "get book by title", key = {"t", "title"})
    public String getBookByTitle(String title) {
        List<Book> books = bookService.getBooksByTitle(title);
        return String.format("Books with tittle: %s\n%s", title, books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n")));
    }

    // author-books 1a
    @ShellMethod(value = "get books by author name" , key = "author-books")
    public String getBookByAuthorName(String authorid) {
        List<Book> books = bookService.getBooksByAuthorId(authorid);
        return String.format("Books with author: %s\n%s", authorid, books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n")));
    }

    @ShellMethod(value = "get list of books", key = "list")
    public String getAllBooks() {
        return String.format("Books:\n%s", bookService.getBooks().stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n")));
    }

    //upd 1 "Властелин"
    @ShellMethod(value = "update book", key = {"upd", "update"})
    public String updateBookTitle(String id, String title) {
        bookService.updateBookTitle(id, title);
        return String.format("Book title updated id:%s title:%s" , id, title);
    }

    @ShellMethod(value = "remove book by id", key = {"rm", "remove"})
    public String removeBook(String id) {
        bookService.deleteBookById(id);
        return String.format("book was deleted id:%s", id);
    }

    //comment-id 1
    @ShellMethod(value = "get comments by book id", key = {"cid", "comment-id"})
    public String getBookComments(String id) {
        List<Comment> comments = bookService.getCommentsByBookId(id);
        if(!comments.isEmpty()) {
            return String.format("Comments for book id: %s:\n%s", id,
                    comments.stream().map(Comment::getComment).collect(Collectors.joining("\n")));
        } else {
            return "No comments";
        }
    }

    //add-comment 1 замечательно
    @ShellMethod(value = "add comment to book", key = {"ac", "add-comment"})
    public String addCommentToBook(String id, String commentText) {
        Comment comment = new Comment(commentText);
        bookService.addNewCommentToBook(id, comment);
        return String.format("Added comment: %s to book id: %s", commentText, id);
    }
}
