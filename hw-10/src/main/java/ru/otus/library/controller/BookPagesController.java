package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.rest.dto.AuthorDto;
import ru.otus.library.rest.dto.BookDto;
import ru.otus.library.service.impl.DBAuthorServiceImpl;
import ru.otus.library.service.impl.DBBookServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookPagesController {

    private final DBBookServiceImpl bookService;
    private final DBAuthorServiceImpl authorService;

    private static final String START_PAGE = "index";
    private static final String CREATE_PAGE = "edit";
    private static final String BOOK_VIEW_PAGE = "bookView";
    private static final String BOOK_LIST_PAGE = "list";

    @GetMapping("/")
    public String startPageView(Model model) {
        model.addAttribute("book", new Book());
        return START_PAGE;
    }

    @PostMapping("/")
    public RedirectView findBookById(Book book) {
        return new RedirectView(String.format("/book/%s", book.getId()), true);
    }

    @GetMapping("book/create")
    public String addBookView(Model model) {
        BookDto book = new BookDto();
        List<AuthorDto> authors = authorService.getAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());

        model.addAllAttributes(Map.of(
                "action", "Save",
                "book", book,
                "authors", authors,
                "authorDto", new AuthorDto()));
        return CREATE_PAGE;
    }

    @PostMapping("book/create")
    public RedirectView saveBook(BookDto book, Model model) {
        Book saved = bookService.saveBook(BookDto.toDomain(book));
        model.addAttribute("book", saved);
        model.addAttribute("action", "Save");
        return new RedirectView("/", true);
    }

    @GetMapping("/book")
    public String listPageView() {
        return BOOK_LIST_PAGE;
    }

    @GetMapping("/book/{id}")
    public String bookPageView(Model model, @PathVariable("id") String id) {
        model.addAttribute("comment", new Comment());
        model.addAttribute("book-id", id);
        return BOOK_VIEW_PAGE;
    }

    @GetMapping("book/{id}/delete")
    public RedirectView deleteBook(@PathVariable String id) {
        bookService.deleteBookById(id);
        return new RedirectView("/book/list", true);
    }
}
