package ru.otus.library.repository;

import ru.otus.library.domain.Comment;

public interface BookRepositoryCustom {
    void addCommentToBook(long id, Comment comment);
}
