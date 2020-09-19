package ru.otus.library.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetAllBooks() {
        ResponseEntity responseEntity = testRestTemplate.getForEntity("/api/books", Object.class);
        assertThat(responseEntity.getStatusCodeValue()).isBetween(200, 400);
    }
}