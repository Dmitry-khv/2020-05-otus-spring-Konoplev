package ru.otus.hw1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw1.dao.MessageDao;
import ru.otus.hw1.model.Message;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageDao messageDao;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageServiceImpl(messageDao);
    }

    @Test
    void getTrueMessage() {
        given(messageService.getMessage()).willReturn(new Message("test message"));
        assertEquals("test message", messageService.getMessage().readMessage());
    }

    @Test
    void getFalseMessage() {
        given(messageService.getMessage()).willReturn(new Message("false test message"));
        assertNotEquals("test message", messageService.getMessage().readMessage());
    }
}