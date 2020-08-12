package ru.otus.library.repository;

import lombok.RequiredArgsConstructor;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.service.NotFoundException;


@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final BookRepository bookRepository;

    @Override
    public void addCommentToBook(long id, Comment comment) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        book.getComment().add(comment);
    }
}
