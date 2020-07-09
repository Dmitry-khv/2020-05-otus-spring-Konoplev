package ru.otus.hw2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import ru.otus.hw2.config.YamlProps;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class MessageSourceServiceImplTest {
    @Autowired
    private MessageSourceService messageSource;

    @Test
    void getMessage() {
        assertEquals("first", messageSource.getMessage("line1"));
        assertEquals("second", messageSource.getMessage("line2", "second"));
    }
}