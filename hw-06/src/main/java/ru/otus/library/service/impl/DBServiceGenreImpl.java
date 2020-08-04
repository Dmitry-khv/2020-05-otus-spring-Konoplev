package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.jpa.GenreDaoJpa;
import ru.otus.library.service.DBServiceGenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBServiceGenreImpl implements DBServiceGenre {
    private static final Logger LOG = LoggerFactory.getLogger(DBServiceGenreImpl.class);
    private final GenreDaoJpa genreDao;

    @Override
    @Transactional
    public long addGenre(Genre genre) {
        if(genre != null) {
            return genreDao.addGenre(genre);
        } else {
            LOG.error("genre is null");
            return 0;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(long id) {
        return genreDao.getGenreById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreByName(String name) {
        return genreDao.getGenreByName(name).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getListGenres() {
        return genreDao.getAllGenres();
    }

}
