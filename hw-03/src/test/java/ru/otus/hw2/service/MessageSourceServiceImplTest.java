package ru.otus.hw2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.hw2.config.YamlProps;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;


@SpringBootTest
class MessageSourceServiceImplTest {
    @MockBean
    private MessageSourceService messageSource;

    @BeforeEach
    public void setUp() {
        given(messageSource.getMessage("line1")).willReturn("first");
        given(messageSource.getMessage("line2", "second")).willReturn("second");
    }

    @Test
    void getMessage() {
        assertEquals("first", messageSource.getMessage("line1"));
        assertEquals("second", messageSource.getMessage("line2", "second"));
    }
}