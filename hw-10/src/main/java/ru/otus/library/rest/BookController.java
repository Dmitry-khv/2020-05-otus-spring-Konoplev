package ru.otus.library.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.rest.dto.BookDto;
import ru.otus.library.service.impl.DBAuthorServiceImpl;
import ru.otus.library.service.impl.DBBookServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class BookController {

    private final DBBookServiceImpl bookService;

    @GetMapping("/api/books")
    public String getAllBooks() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<BookDto> books = bookService.getBooks().stream()
                .map(BookDto::toDto).collect(Collectors.toList());
        return mapper.writeValueAsString(books);
    }
}
