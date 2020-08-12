package ru.otus.library.service;

import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;

@Service
public interface DBAuthorService {
    Author addAuthor(Author author);
}
