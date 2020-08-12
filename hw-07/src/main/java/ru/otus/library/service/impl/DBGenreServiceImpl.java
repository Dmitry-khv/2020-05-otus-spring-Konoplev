package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.DBGenreService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DBGenreServiceImpl implements DBGenreService {

    private final GenreRepository repository;

    @Override
    @Transactional
    public Genre addGenre(Genre genre) {
        Genre savedGenre = repository.findGenreByGenreName(genre.getGenreName()).orElse(null);
        return Objects.requireNonNullElseGet(savedGenre, () -> repository.save(genre));
    }
}
