package ru.otus.hw2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizTest {

    @Mock
    private QuestionService questionService;
    @Mock
    private IOService ioService;
    @Mock
    private GreetingService greetingService;

    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        quiz = new Quiz(questionService, ioService, greetingService, 3);
    }

    @Test
    public void correctInitialization() {
        assertNotNull(quiz);
    }

}