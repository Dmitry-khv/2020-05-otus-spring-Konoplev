package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.DBAuthorService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DBAuthorServiceImpl implements DBAuthorService {

    private final AuthorRepository repository;

    @Override
    public Author addAuthor(Author author) {
        Author savedAuthor = repository.findByName(author.getName()).orElse(null);
        return Objects.requireNonNullElseGet(savedAuthor, () -> repository.save(author));
    }
}
