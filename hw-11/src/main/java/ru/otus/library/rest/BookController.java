package ru.otus.library.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.service.impl.DBBookServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
public class BookController {

    private final DBBookServiceImpl bookService;

    @GetMapping("/api/book/list")
    public String getAllBooks() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = bookService.getBooks();
        return mapper.writeValueAsString(books);
    }

    @GetMapping("/api/book/{id}/view")
    public String bookPageView(@PathVariable("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bookService.getBookById(id));
    }

    @PostMapping(value = "/api/book/{id}/comment")
    public void addComment(@PathVariable("id") String id, @RequestBody Comment comment) {
        bookService.addNewCommentToBook(id, comment);
    }

    @PostMapping("/api/book/create")
    public void createBook(@RequestBody Book book, HttpServletResponse response) throws IOException {
        bookService.saveBook(book);
        response.sendRedirect("/api/book/list");
    }
}
