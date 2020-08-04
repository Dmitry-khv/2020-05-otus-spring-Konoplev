package ru.otus.library.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.GenreDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJpa implements GenreDao {
    private static final Logger LOG = LoggerFactory.getLogger(GenreDaoJpa.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public long addGenre(Genre genre) {
        if(genre.getId() == 0) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
        return genre.getId();
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        Genre genre = null;
        try {
            String findByName = "select g from Genre g where g.genre= :name";
            Query query = em.createQuery(findByName);
            query.setParameter("name", name);
            genre = (Genre) query.getSingleResult();
        } catch (NoResultException e){
            LOG.info("genre not found name, add {}", name);
        }
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        String getAll = "select g from Genre g";
        Query query = em.createQuery(getAll);
        return query.getResultList();
    }
}
