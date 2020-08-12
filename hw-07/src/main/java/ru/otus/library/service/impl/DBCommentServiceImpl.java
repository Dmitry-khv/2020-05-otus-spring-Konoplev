package ru.otus.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.service.DBCommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBCommentServiceImpl implements DBCommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public List<Comment> getCommentsByBookId(long id) {
        return commentRepository.getCommentsByBook_Id(id);
    }
}
