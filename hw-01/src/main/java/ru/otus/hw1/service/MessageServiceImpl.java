package ru.otus.hw1.service;

import ru.otus.hw1.dao.MessageDao;
import ru.otus.hw1.model.Question;

import java.io.OutputStream;

public class MessageServiceImpl implements MessageService{
    private final MessageDao messageDao;
    private final PrintService printService;

    public MessageServiceImpl(MessageDao messageDao, PrintService printService) {
        this.messageDao = messageDao;
        this.printService = printService;
    }

    @Override
    public void getMessage(OutputStream outputStream) {
         printService.print(messageDao.getMessage().readMessage());
    }
}
