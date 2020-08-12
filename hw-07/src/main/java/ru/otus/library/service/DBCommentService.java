package ru.otus.library.service;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface DBCommentService {
    List<Comment> getCommentsByBookId(long id);
}
