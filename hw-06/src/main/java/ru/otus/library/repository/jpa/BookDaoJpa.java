package ru.otus.library.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
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
        String delete = "delete from Book b where b.id= :id";
        Query query = em.createQuery(delete);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        try {
            String select = "select b from Book b " +
                    "join fetch b.author join fetch b.genre where b.id= :id";
            TypedQuery<Book> query = em.createQuery(select, Book.class);
            query.setParameter("id", id);
            Book book = query.getSingleResult();
            Book newBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
            return Optional.of(newBook);
        } catch (NoResultException e) {
            LOG.error("book is not found id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        String getByTitleQuery = "select b from Book b join fetch b.author join fetch b.genre where b.title= :title";
        TypedQuery<Book> query = em.createQuery(getByTitleQuery, Book.class);
        query.setParameter("title", title);
        return getBooks(query.getResultList());
    }

    @Override
    public List<Book> getAllBook() {
        String getAllQuery = "select b from Book b join fetch b.author join fetch b.genre";
        TypedQuery<Book> query = em.createQuery(getAllQuery, Book.class);
        return getBooks(query.getResultList());
    }

    private List<Book> getBooks(List<Book> resultList) {
        List<Book> books = new ArrayList<>();
        resultList.forEach(b ->
                books.add(new Book(b.getId(), b.getTitle(), b.getAuthor(), b.getGenre())));
        return books;
    }

    @Override
    public List<Book> getBooksByAuthorName(String name) {
        String stringQuery = "select b from Book b " +
                "join fetch b.author join fetch b.genre where b.author.name= :name";
        TypedQuery<Book> query = em.createQuery(stringQuery, Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void addNewCommentToBookById(long id, String commentText) {
        String stringQuery = "select b from Book b join fetch b.comment where b.id= :id";
        TypedQuery<Book> query = em.createQuery(stringQuery, Book.class);
        query.setParameter("id", id);
        List<Comment> comments = query.getSingleResult().getComment();
        comments.add(new Comment(commentText));
    }
}