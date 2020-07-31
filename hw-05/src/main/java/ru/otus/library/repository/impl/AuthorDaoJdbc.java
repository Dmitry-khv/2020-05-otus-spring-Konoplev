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
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final static Logger LOG = LoggerFactory.getLogger(AuthorDaoJdbc.class);

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getNString("name");
            return new Author(id, name);
        }
    }

    @Override
    public long addAuthor(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into authors (name) values(:name)",
                new MapSqlParameterSource(Map.of("name", author.getName())), keyHolder);
        LOG.info("add new Author: {}", author);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void removeAuthor(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from authors where id= :id", params);
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String selectById = "select id, name from authors where id= :id";
        return Optional.ofNullable(namedParameterJdbcOperations
                .queryForObject(selectById, params, new AuthorMapper()));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        try {
            Map<String, Object> params = Collections.singletonMap("name", name);
            String selectByName = "select id, name from authors where name= :name";
            return Optional.ofNullable(namedParameterJdbcOperations.queryForObject(selectByName, params, new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            LOG.error(String.format("Author is not found name:%s", name), e.getMessage());
            return Optional.empty();
        }

    }


    @Override
    public List<Author> getAllAuthors() {
        return namedParameterJdbcOperations.query("select id, name from authors", new AuthorMapper());
    }
}
