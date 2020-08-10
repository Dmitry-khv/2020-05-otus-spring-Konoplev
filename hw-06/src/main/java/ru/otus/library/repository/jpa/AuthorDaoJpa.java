package ru.otus.library.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorDao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorDaoJpa.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void removeAuthor(long id) {
        Author author = em.find(Author.class, id);
        if(author != null) {
            em.remove(author);
        }
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        try {
            String findByName = "select a from Author a where a.name = :name";
            Query query = em.createQuery(findByName);
            query.setParameter("name", name);
            Author author = (Author) query.getSingleResult();
            return Optional.of(author);
        } catch (NoResultException e) {
            LOG.info("author not found name, add {}", name);
            return Optional.empty();
        }
    }
}
