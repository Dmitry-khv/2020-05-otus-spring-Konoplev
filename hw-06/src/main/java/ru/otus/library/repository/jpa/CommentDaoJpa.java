package ru.otus.library.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentDao;

import javax.persistence.*;
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
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment != null) {
            em.remove(comment);
        }
    }

    @Override
    public Optional<Comment> getCommentById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }
}
