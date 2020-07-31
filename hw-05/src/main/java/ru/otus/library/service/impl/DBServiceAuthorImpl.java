package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.impl.AuthorDaoJdbc;
import ru.otus.library.service.DBServiceAuthor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceAuthorImpl implements DBServiceAuthor {
    private final AuthorDaoJdbc authorDao;

    @Override
    public long addAuthor(Author author) {
        return authorDao.addAuthor(author);
    }

    @Override
    public void removeAuthor(long id) {
        authorDao.removeAuthor(id);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.getAuthorById(id).get();
    }

    @Override
    public Author getAuthorByName(String name) {
        Optional<Author> author = authorDao.getAuthorByName(name);
        return author.orElse(null);
    }

    @Override
    public List<Author> getListAuthors() {
        return authorDao.getAllAuthors();
    }
}
