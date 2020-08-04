package ru.otus.library.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addOrUpdateComment(Comment comment) {
        if(comment.getId() == 0) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    @Override
    public List<Comment> getCommentsByBookId(long id) {
        String getAllQuery = "select c from Comment c where c.book.id= :id";
        TypedQuery<Comment> query = em.createQuery(getAllQuery, Comment.class);
        query.setParameter("id", id);
        List<Comment> comments = new ArrayList<>();
        query.getResultList().forEach(comment ->
                comments.add(new Comment(comment.getId(), comment.getBook(), comment.getComment())));
        return comments;
    }

    @Override
    public void deleteById(long id) {
        String deleteQuery = "delete from Comment c where c.id = :id";
        Query query = em.createQuery(deleteQuery);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }
}
