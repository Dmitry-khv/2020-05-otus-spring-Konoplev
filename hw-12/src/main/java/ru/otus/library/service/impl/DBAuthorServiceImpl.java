package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.service.DBAuthorService;
import ru.otus.library.service.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBAuthorServiceImpl implements DBAuthorService {

    private final AuthorRepository repository;

    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Override
    public Author getById(String id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Author save(Author author) {
        Optional<Author> saved = repository.findByName(author.getName());
        return saved.orElseGet(() -> repository.save(author));
    }
}
