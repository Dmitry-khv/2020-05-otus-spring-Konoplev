package ru.otus.library.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentDao;

import javax.persistence.*;
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
    public List<Comment> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("comment-with-book");
        String getAllQuery = "select c from Comment c";
        TypedQuery<Comment> query = em.createQuery(getAllQuery, Comment.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
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
