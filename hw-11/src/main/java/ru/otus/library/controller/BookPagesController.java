package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.AuthorRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookPagesController {

    private final AuthorRepository authorRepository;

    private static final String START_PAGE = "index";
    private static final String CREATE_PAGE = "edit";
    private static final String BOOK_VIEW_PAGE = "bookView";
    private static final String BOOK_LIST_PAGE = "list";

    @GetMapping("/")
    public Mono <String> startPageView(Model model) {
        return Mono.fromCallable(() -> {
            model.addAttribute("book", new Book());
            return START_PAGE;
        });
    }

    @PostMapping("/")
    public RedirectView findBookById(Book book) {
        return new RedirectView(String.format("/book/%s/view", book.getId()), true);
    }

    @GetMapping("book/create")
    public Mono<String> addBookView(Model model) {
        return Mono.fromCallable(() -> {
            Mono<List<Author>> authors = authorRepository.findAll()
//                    .map(Author::toDto)
                    .collect(Collectors.toList());

            model.addAllAttributes(Map.of(
                    "action", "Save",
                    "book", new Book(),
                    "authors", authors,
                    "authorDto", new Author()));
            return CREATE_PAGE;
        });
    }

    @GetMapping("/book/list")
    public String listPageView() {
        return BOOK_LIST_PAGE;
    }

    @GetMapping("/book/{id}/view")
    public Mono<String> bookPageView(Model model, @PathVariable("id") String id) {
        return Mono.fromCallable(() -> {
            model.addAttribute("comment", new Comment());
            model.addAttribute("book-id", id);
            return BOOK_VIEW_PAGE;
        });
    }

    @GetMapping("book/{id}/delete")
    public RedirectView deleteBook(@PathVariable String id) {
        return new RedirectView("/book/list", true);
    }
}
