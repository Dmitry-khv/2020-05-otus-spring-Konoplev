package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document("authors")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private String id;
    @Field("name")
    private String name;

    public Author (String name) {
        this.name = name;
    }
}
