package ru.otus.library.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.domain.Author;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorDto {
    private String id;
    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public static Author toDomain(AuthorDto dto) {
        return new Author(dto.id, dto.name);
    }
}
