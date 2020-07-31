package ru.otus.library.repository.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final static Logger LOG = LoggerFactory.getLogger(GenreDao.class);
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String genre = rs.getNString("genre");
            return new Genre(id, genre);
        }
    }

    @Override
    public long addGenre(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into genres (genre) values (:genre)",
                new MapSqlParameterSource(Map.of("genre", genre.getGenre())), keyHolder);
        LOG.info("add new Genre: {}", genre);
        return keyHolder.getKey().longValue();
    }


    @Override
    public Optional<Genre> getGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return Optional.ofNullable(namedParameterJdbcOperations
                .queryForObject("select id, genre from genres where id= :id", params, new GenreMapper()));
    }

    @Override
    public Optional<Genre> getGenreByName(String genre) {
        try {
            Map<String, Object> params = Collections.singletonMap("genre", genre);
            String selectByName = "select id, genre from genres where genre= :genre";
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(selectByName, params, new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            LOG.error(String.format("Genre is not found name:%s", genre), e.getMessage());
            return Optional.empty();
        }    }

    @Override
    public List<Genre> getAllGenres() {
        return namedParameterJdbcOperations.query("select id, genre from genres", new GenreMapper());
    }
}
