package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "book-with-author-and-genre", attributeNodes = {
        @NamedAttributeNode("author"), @NamedAttributeNode("genre")})
@NamedEntityGraph(name = "book-with-comment", attributeNodes = {
        @NamedAttributeNode("comment")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @ManyToOne(targetEntity = Author.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(targetEntity = Genre.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comment = new ArrayList<>();

    public static Builder newBuilder() {
        return new Book().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setId(long id) {
            Book.this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            Book.this.title = title;
            return this;
        }

        public Builder setAuthor(Author author) {
            Book.this.author = author;
            return this;
        }

        public Builder setGenre(Genre genre) {
            Book.this.genre = genre;
            return this;
        }

        public Builder setComment(List<Comment> comment) {
            Book.this.comment = comment;
            return this;
        }

        public Book build() {
            return Book.this;
        }
    }
}
