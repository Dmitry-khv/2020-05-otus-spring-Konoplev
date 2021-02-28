package ru.otus.library.rest;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.BookRepository;


@AllArgsConstructor
@RestController
public class BookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookRepository bookRepository;

    @GetMapping("/api/book/list")
    public Flux<Book> getAllBooks() {
        LOGGER.info("get all books");
        return bookRepository.findAll();
    }

    @GetMapping("/api/book/{id}/view")
    public Mono<Book> bookPageView(@PathVariable("id") String id) {
        return bookRepository.findById(id);
    }

    @PostMapping(value = "/api/book/{id}/comment")
    public Mono<Void> addComment(@PathVariable("id") String id, @RequestBody Comment comment) {
        return bookRepository
                .findById(id)
                .doOnNext(book -> book.addComment(comment))
                .flatMap(bookRepository::save)
                .onErrorResume(e -> Mono.empty())
                .then();
    }

    @PostMapping("/api/book/create")
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("book/{id}/delete")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }
}
