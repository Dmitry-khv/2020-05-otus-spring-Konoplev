package ru.otus.hw2.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw2.model.Answer;
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
    void getAnswers() {
        given(questionDao.getAnswers()).willReturn(Arrays.asList(new Answer("a"), new Answer("b")));
        List<String> answ = Arrays.asList("a", "b");
        for (int i = 0; i < answ.size(); i++) {
            assertEquals(answ.get(i), questionDao.getAnswers().get(i).getAnswerAsString());
        }
    }
}