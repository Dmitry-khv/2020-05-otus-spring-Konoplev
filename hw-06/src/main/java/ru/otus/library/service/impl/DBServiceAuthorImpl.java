package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.jpa.AuthorDaoJpa;
import ru.otus.library.service.DBServiceAuthor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBServiceAuthorImpl implements DBServiceAuthor {
    private static final Logger LOG = LoggerFactory.getLogger(DBServiceAuthorImpl.class);
    private final AuthorDaoJpa authorDao;

    @Override
    @Transactional
    public long addAuthor(Author author) {
        if(author != null) {
            return authorDao.addAuthor(author);
        } else {
            LOG.error("author is null");
            return 0;
        }
    }

    @Override
    @Transactional
    public void removeAuthor(long id) {
        authorDao.removeAuthor(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(long id) {
        return authorDao.getAuthorById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorByName(String name) {
        Optional<Author> author = authorDao.getAuthorByName(name);
        Author refreshAuthor = author.orElse(null);
        return refreshAuthor;
    }

    @Override
    public List<Author> getListAuthors() {
        return authorDao.getAllAuthors();
    }
}
