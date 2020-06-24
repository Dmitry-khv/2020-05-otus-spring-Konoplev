package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw2.dao.MessageDao;

import java.io.OutputStream;

@Service
public class MessageServiceImpl implements MessageService{
    private final MessageDao messageDao;
    private final IOService IOService;

    @Autowired
    public MessageServiceImpl(MessageDao messageDao, IOService IOService) {
        this.messageDao = messageDao;
        this.IOService = IOService;
    }

    @Override
    public void getMessage(OutputStream outputStream) {
         IOService.print(messageDao.getQuestions().readMessage());
    }
}
