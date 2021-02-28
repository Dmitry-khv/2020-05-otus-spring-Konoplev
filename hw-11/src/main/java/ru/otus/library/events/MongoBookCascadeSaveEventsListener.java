package ru.otus.library.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.GenreRepository;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeSaveEventsListener extends AbstractMongoEventListener<Book> {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        Book book = event.getSource();
        if(!book.getAuthors().isEmpty()) {
            book.getAuthors().stream().filter(a -> Objects.isNull(a.getId())).forEach(author -> authorRepository.save(author).block());
        }
        if(!book.getGenres().isEmpty()) {
            book.getGenres().stream().filter(g -> Objects.isNull(g.getId())).forEach(genre -> genreRepository.save(genre).block());
        }
    }
}
