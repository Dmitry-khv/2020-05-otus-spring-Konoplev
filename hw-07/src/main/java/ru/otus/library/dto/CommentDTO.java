package ru.otus.library.dto;

import lombok.Builder;
import lombok.Data;
import ru.otus.library.domain.Book;

@Data
@Builder
public class CommentDTO {
    private long id;
    private Book book;
    private String comment;

}
