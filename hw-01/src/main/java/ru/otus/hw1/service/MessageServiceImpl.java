package ru.otus.hw1.service;

import ru.otus.hw1.dao.MessageDao;
import ru.otus.hw1.model.Message;

public class MessageServiceImpl implements MessageService{
    private final MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Message getMessage() {
        return messageDao.getMessage();
    }
}
