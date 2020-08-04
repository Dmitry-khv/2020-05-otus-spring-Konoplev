package ru.otus.library.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorDaoJpa.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public long addAuthor(Author author) {
        if(author.getId() == 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        return author.getId();
    }

    @Override
    public void removeAuthor(long id) {
        String remove = "delete from Author a where a.id= :id";
        Query query = em.createQuery(remove);
        query.setParameter("id" ,id);
        query.executeUpdate();
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        Author author = null;
        try {
            String findByName = "select a from Author a where a.name = :name";
            Query query = em.createQuery(findByName);
            query.setParameter("name", name);
            author = (Author) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("author not found name, add {}", name);
        }
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        String getAll = "select a from Author a";
        Query query = em.createQuery(getAll, Author.class);
        return query.getResultList();
    }
}
