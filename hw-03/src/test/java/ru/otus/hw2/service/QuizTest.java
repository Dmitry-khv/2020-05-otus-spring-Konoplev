package ru.otus.hw2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw2.config.YamlProps;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizTest {

    @Mock
    private QuestionService questionService;
    @Mock
    private IOService ioService;
    @Mock
    private GreetingService greetingService;
    @Mock
    private YamlProps yamlProps;
    @Mock
    private MessageSourceService messageSourceService;

    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        quiz = new Quiz(questionService, ioService, greetingService, yamlProps, messageSourceService);
    }

    @Test
    public void correctInitialization() {
        assertNotNull(quiz);
    }

}