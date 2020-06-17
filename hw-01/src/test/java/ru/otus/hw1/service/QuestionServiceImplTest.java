package ru.otus.hw1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw1.dao.MessageDao;
import ru.otus.hw1.model.Question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private MessageDao messageDao;

    @Mock
    private PrintService printService;

    private MessageService messageService;


    @BeforeEach
    void setUp() {
        messageService = new MessageServiceImpl(messageDao, printService);
    }

}