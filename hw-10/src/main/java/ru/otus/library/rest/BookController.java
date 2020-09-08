package ru.otus.library.rest;

import lombok.AllArgsConstructor;
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
    private final DBAuthorServiceImpl authorService;

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getBooks().stream()
                .map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/api/book/{id}")
    public BookDto getBook(@PathVariable String id) {
        return BookDto.toDto(bookService.getBookById(id));
    }
}
