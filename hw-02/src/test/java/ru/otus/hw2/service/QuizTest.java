package ru.otus.hw2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizTest {

    @Mock
    private MessageService messageService;
    @Mock
    private IOService ioService;
    @Mock
    private MessageSource messageSource;

    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        quiz = new Quiz(messageService, ioService, messageSource);
    }

    @Test
    public void correctInitialization() {
        assertNotNull(quiz);
    }

}