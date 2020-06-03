package ru.otus.hw1.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw1.model.Message;
import ru.otus.hw1.resourcemanager.ResourceData;
import static org.mockito.BDDMockito.given;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageDaoImplTest {

    @Mock
    private ResourceData resourceData;

    private MessageDao messageDao;

    @BeforeEach
    void setup() {
        messageDao = new MessageDaoImpl(resourceData);
    }

    @Test
    void getMessage() {
        given(messageDao.getMessage()).willReturn(new Message("test message"));
        assertEquals("test message", messageDao.getMessage().readMessage());
    }
}