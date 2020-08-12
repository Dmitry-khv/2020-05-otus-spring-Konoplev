package ru.otus.library.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.List;

@Data
@Builder
public class BookDTO {
    private long id;
    private String title;
    private Author author;
    private Genre genre;
    private List<Comment> comment;
}
