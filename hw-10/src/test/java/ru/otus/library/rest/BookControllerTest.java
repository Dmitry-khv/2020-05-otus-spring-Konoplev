package ru.otus.library.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.otus.library.repository.BookRepositoryCustomImpl;
import ru.otus.library.rest.dto.BookDto;
import ru.otus.library.service.DBBookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private DBBookService service;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetAllBooks() throws JsonProcessingException {
        ResponseEntity responseEntity = testRestTemplate.getForEntity("/api/books", Object.class);
        assertThat(responseEntity.getStatusCodeValue()).isBetween(200, 400);

        ObjectMapper mapper = new ObjectMapper();
        List<BookDto> books = service.getBooks().stream()
                .map(BookDto::toDto).collect(Collectors.toList());
        String expectedBooks = mapper.writeValueAsString(books);
        assertThat(responseEntity.getBody()).isEqualTo(expectedBooks);
    }
}