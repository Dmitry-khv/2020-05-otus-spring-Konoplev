package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.jpa.BookDaoJpa;
import ru.otus.library.repository.jpa.CommentDaoJpa;
import ru.otus.library.service.DBServiceComment;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DBServiceCommentImpl implements DBServiceComment {
    private static final Logger LOG = LoggerFactory.getLogger(DBServiceCommentImpl.class);
    private final CommentDaoJpa commentDao;
    private final BookDaoJpa bookDao;

    @Override
    @Transactional
    public void addOrUpdateComment(Comment comment) {
        if (comment != null) {
            commentDao.addOrUpdateComment(comment);
        } else {
            LOG.error("Comment is null");
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Comment> getCommentsByBookId(long id) {
        return bookDao.getBookById(id).get().getComment();
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentById(long id) {
        return commentDao.getCommentById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        commentDao.getCommentById(id).orElseThrow();
    }
}
