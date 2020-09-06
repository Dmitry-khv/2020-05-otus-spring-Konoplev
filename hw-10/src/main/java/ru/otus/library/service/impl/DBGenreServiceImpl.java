package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.DBGenreService;
import ru.otus.library.service.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DBGenreServiceImpl implements DBGenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Genre save(Genre genre) {
        Optional<Genre> saved = genreRepository.findByName(genre.getName());
        return saved.orElseGet(() -> genreRepository.save(genre));
    }
}
