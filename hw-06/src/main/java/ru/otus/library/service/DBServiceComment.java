package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface DBServiceComment {
    void addOrUpdateComment(Comment comment);
    List<Comment> getCommentsByBookId(long id);
    Comment getCommentById(long id);
    void deleteCommentById(long id);
}
