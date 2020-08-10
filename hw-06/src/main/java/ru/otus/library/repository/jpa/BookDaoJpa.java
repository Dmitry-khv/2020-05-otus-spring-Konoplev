package ru.otus.library.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.BookDao;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {
    private static final Logger LOG = LoggerFactory.getLogger(BookDaoJpa.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public long insertOrUpdateBook(Book book) {
        if(book.getId() == 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book.getId();
    }

    @Override
    public void deleteBookById(long id) {
        Book book = em.find(Book.class, id);
        if(book != null) {
            em.remove(book);
        }
    }

    @Override
    public Optional<Book> getBookById(long id) {
        try {
            EntityGraph<?> entityGraph = em.getEntityGraph("book-with-author-and-genre");
            Map<String, Object> properties = Collections.singletonMap("javax.persistence.fetchgraph", entityGraph);
            Book book = em.find(Book.class, id, properties);
            return Optional.ofNullable(book);
        } catch (NoResultException e) {
            LOG.error("book is not found id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-author-and-genre");
        String getByTitleQuery = "select b from Book b where b.title= :title";
        TypedQuery<Book> query = em.createQuery(getByTitleQuery, Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooks() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-author-and-genre");
        String getAllQuery = "select b from Book b";
        TypedQuery<Book> query = em.createQuery(getAllQuery, Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}