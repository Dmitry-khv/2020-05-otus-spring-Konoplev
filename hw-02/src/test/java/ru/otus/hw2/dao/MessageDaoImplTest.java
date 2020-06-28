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
class MessageDaoImplTest {

    @Mock
    private ResourceData resource;
    private MessageDao messageDao;

    @BeforeEach
    public void setUp() {
        messageDao = new MessageDaoImpl(resource);
    }


    @Test
    void returnCorrectQuestion() {
        given(messageDao.getQuestions()).willReturn(new Question("test message"));
        assertEquals("test message", messageDao.getQuestions().readMessage());
    }

    @Test
    void getAnswers() {
        given(messageDao.getAnswers()).willReturn(Arrays.asList("a", "a", "a", "b"));
        List<String> answ = Arrays.asList("a", "a", "a", "b");
        assertEquals(answ, messageDao.getAnswers());
    }
}