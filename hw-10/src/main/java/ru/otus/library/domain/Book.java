package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document("books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private String id;
    @Field("title")
    private String title;
    @DBRef
    @Field("authors")
    private List<Author> authors = new ArrayList<>();
    @DBRef
    @Field("genres")
    private List<Genre> genres = new ArrayList<>();
    @Field("comments")
    private List<Comment> comments = new ArrayList<>();

    public void addAuthor(Author...author) {
        authors.addAll(List.of(author));
    }

    public void addGenre(Genre...genre) {
        genres.addAll(List.of(genre));
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
