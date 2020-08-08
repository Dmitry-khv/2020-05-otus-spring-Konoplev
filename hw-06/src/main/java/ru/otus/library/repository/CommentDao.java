package ru.otus.library.repository;

import ru.otus.library.domain.Comment;

import java.util.Optional;

public interface CommentDao {
    void addOrUpdateComment(Comment comment);
    void deleteById(long actualCommentId);
    Optional<Comment> getCommentById(long actualCommentId);
}
