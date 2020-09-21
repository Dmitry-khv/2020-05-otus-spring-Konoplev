package ru.otus.library.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.domain.Book;
import ru.otus.library.service.impl.DBBookServiceImpl;

import java.util.List;

@AllArgsConstructor
@RestController
public class BookController {

    private final DBBookServiceImpl bookService;

    @GetMapping("/api/books")
    public String getAllBooks() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Book> books = bookService.getBooks();
        return mapper.writeValueAsString(books);
    }

    @GetMapping("/api/book/{id}")
    public String bookPageView(@PathVariable("id") String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(bookService.getBookById(id));
    }
}
