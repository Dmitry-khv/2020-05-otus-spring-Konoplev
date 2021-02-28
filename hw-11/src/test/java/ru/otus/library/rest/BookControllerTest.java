package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.otus.library.domain.Book;
import ru.otus.library.repository.BookRepository;



import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private BookRepository bookRepository;

    private ResponseEntity responseEntity;
    private static final String BOOK_ID = "1";

    @Test
    public void shouldGetAllBooks() {
        responseEntity = testRestTemplate.getForEntity("/api/book/list", Object.class);
        assertThat(responseEntity.getStatusCodeValue()).isBetween(200, 400);
    }

    @Test
    public void shouldGetBookById() {
        responseEntity = testRestTemplate.getForEntity(String.format("/api/book/%s/view", BOOK_ID), Object.class);
        assertThat(responseEntity.getStatusCodeValue()).isBetween(200, 400);
        Object obj = responseEntity.getBody();
        Book bookFromWeb = new ObjectMapper().convertValue(obj, Book.class);
        Book bookFromDb = bookRepository.findById(BOOK_ID).block();
        assertThat(bookFromWeb).isEqualTo(bookFromDb);
    }
}