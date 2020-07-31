package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.impl.GenreDaoJdbc;
import ru.otus.library.service.DBServiceGenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBServiceGenreImpl implements DBServiceGenre {
    private final GenreDaoJdbc genreDao;

    @Override
    public long addGenre(Genre genre) {
        return genreDao.addGenre(genre);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.getGenreById(id).get();
    }

    @Override
    public Genre getGenreByName(String name) {
        return genreDao.getGenreByName(name).orElse(null);
    }

    @Override
    public List<Genre> getListGenres() {
        return genreDao.getAllGenres();
    }
}
