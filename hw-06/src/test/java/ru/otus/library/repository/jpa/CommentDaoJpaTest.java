package ru.otus.library.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для комментариев")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private CommentDaoJpa commentDao;

    private static final String NEW_COMMENT = "new comment";
    private static final String ACTUAL_COMMENT_1 = "не плохо";
    private static final String ACTUAL_COMMENT_2 = "бывало и лучше";
    private static final long ACTUAL_COMMENT_ID = 1L;
    private static final long ACTUAL_BOOK_ID = 1L;
    private static final long EXPECTED_COMMENT_ID = 4L;
    private static final int COMMENT_LIST_SIZE = 2;

    @Test
    @DisplayName("должен добавлять новый комментарий в существующую книгу")
    public void shouldAddNewComment() {
        Book book = em.find(Book.class, ACTUAL_BOOK_ID);
        assertThat(book).isNotNull();
        Comment comment = new Comment(0, book, NEW_COMMENT);
        commentDao.addOrUpdateComment(comment);
        book = em.find(Book.class, ACTUAL_BOOK_ID);
        assertThat(book).isNotNull().extracting(b -> b.getComment().get(COMMENT_LIST_SIZE))
                .extracting("id", "comment")
                .containsExactly(EXPECTED_COMMENT_ID, NEW_COMMENT);
    }

    @Test
    @DisplayName("должен доставать список комментариев к книге по ее id")
    public void shouldGetAllComment() {
        Book book = em.find(Book.class, ACTUAL_BOOK_ID);
        assertThat(book).isNotNull();
        List<Comment> comments = commentDao.getCommentsByBookId(ACTUAL_BOOK_ID);
        assertThat(comments).isNotNull().hasSize(COMMENT_LIST_SIZE)
                .anyMatch(c -> c.getComment().equals(ACTUAL_COMMENT_1))
                .anyMatch(c -> c.getComment().equals(ACTUAL_COMMENT_2));
    }

    @Test
    @DisplayName("должен редактировать комментарий по его id")
    public void shouldUpdateCommentById() {
        Comment comment = em.find(Comment.class, ACTUAL_COMMENT_ID);
        comment.setComment(NEW_COMMENT);
        commentDao.addOrUpdateComment(comment);
        Comment updatedComment = em.find(Comment.class, ACTUAL_COMMENT_ID);
        assertThat(updatedComment).isNotNull().extracting("id", "comment")
                .containsExactly(ACTUAL_COMMENT_ID, NEW_COMMENT);
    }

    @Test
    @DisplayName("должен удалять комментарий по его id")
    public void shouldDeleteCommentById() {
        commentDao.deleteById(ACTUAL_COMMENT_ID);
        Comment comment = em.find(Comment.class, ACTUAL_COMMENT_ID);
        assertThat(comment).isNull();
    }

    @Test
    @DisplayName("должен получать комментарий по его id")
    public void shouldGetCommentById() {
        Comment comment = commentDao.getCommentById(ACTUAL_COMMENT_ID).get();
        assertThat(comment).isNotNull().extracting("id", "comment")
                .containsExactly(ACTUAL_COMMENT_ID, ACTUAL_COMMENT_1);
    }

}