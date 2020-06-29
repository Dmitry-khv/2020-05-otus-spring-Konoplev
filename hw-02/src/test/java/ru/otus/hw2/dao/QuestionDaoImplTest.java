package ru.otus.hw2.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw2.model.Question;
import ru.otus.hw2.resourcemanager.ResourceData;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {

    @Mock
    private ResourceData resource;
    private QuestionDao questionDao;

    @BeforeEach
    public void setUp() {
        questionDao = new QuestionDaoImpl(resource);
    }


    @Test
    void returnCorrectQuestion() {
        given(questionDao.getQuestions()).willReturn(new Question("test message"));
        assertEquals("test message", questionDao.getQuestions().readMessage());
    }

    @Test
    void getAnswers() {
        given(questionDao.getAnswers()).willReturn(Arrays.asList("a", "a", "a", "b"));
        List<String> answ = Arrays.asList("a", "a", "a", "b");
        assertEquals(answ, questionDao.getAnswers());
    }
}