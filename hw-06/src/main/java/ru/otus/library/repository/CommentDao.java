package ru.otus.library.repository;

import ru.otus.library.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    void addOrUpdateComment(Comment comment);
    List<Comment> findAll();
    void deleteById(long actualCommentId);
    Optional<Comment> getCommentById(long actualCommentId);
}
