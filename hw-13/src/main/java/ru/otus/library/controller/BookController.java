package ru.otus.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.rest.dto.BookDto;
import ru.otus.library.service.impl.DBAuthorServiceImpl;
import ru.otus.library.service.impl.DBBookServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {

    private static final String START_PAGE = "index";
    private static final String CREATE_PAGE = "edit";
    private static final String BOOK_VIEW_PAGE = "bookView";
    private static final String BOOK_LIST_PAGE = "list";
    private final DBBookServiceImpl bookService;
    private final DBAuthorServiceImpl authorService;

    @GetMapping("/")
    public String startPageView(Model model) {
        model.addAttribute("book", new Book());
        return START_PAGE;
    }

    @PostMapping("/")
    public RedirectView findBookById(Model model, Book book) {
        return new RedirectView(String.format("/book/%s", book.getId()), true);
    }

    @GetMapping("book/create")
    public String addBookView(Model model) {
        BookDto book = new BookDto();
        book.getAuthors().add(new Author());
        book.getGenres().add(new Genre());
        model.addAllAttributes(Map.of(
                "action", "Save",
                "book", book
        ));
        return CREATE_PAGE;
    }

    @PostMapping("book/create")
    public RedirectView saveBook(BookDto book, Model model) {
        Book saved = bookService.saveBook(BookDto.toDomain(book));
        model.addAttribute("book", saved);
        model.addAttribute("action", "Save");
        return new RedirectView("/", true);
    }

    @GetMapping("book/list")
    public String listPageView(Model model) {
        List<BookDto> books = bookService.getBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return BOOK_LIST_PAGE;
    }

    @GetMapping("book/{id}")
    public String bookPageView(Model model, @PathVariable String id) {
        BookDto book = BookDto.toDto(bookService.getBookById(id));
        model.addAttribute("book", book);
        model.addAttribute("comment", new Comment());
        return BOOK_VIEW_PAGE;
    }

    @PostMapping("/book/{id}/comment")
    public RedirectView addComment(@PathVariable String id, Model model, Comment comment, Book book) {
        bookService.addNewCommentToBook(book.getId(), comment);
        BookDto saved = BookDto.toDto(book);
        model.addAttribute("book", saved);
        return new RedirectView(String.format("/book/%s", id), true);
    }

    @GetMapping("book/{id}/delete")
    public RedirectView deleteBook(@PathVariable String id) {
        bookService.deleteBookById(id);
        return new RedirectView("/book/list", true);
    }
}
