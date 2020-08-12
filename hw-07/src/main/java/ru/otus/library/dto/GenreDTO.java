package ru.otus.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDTO {
    private long id;
    private String genreName;
}
