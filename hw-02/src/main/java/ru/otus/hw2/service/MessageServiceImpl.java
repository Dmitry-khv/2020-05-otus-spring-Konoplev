package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw2.dao.MessageDao;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    private final MessageDao messageDao;
    private final IOService IOService;
    private final MessageSource messageSource;


    @Autowired
    public MessageServiceImpl(MessageDao messageDao, IOService IOService, MessageSource messageSource) {
        this.messageDao = messageDao;
        this.IOService = IOService;
        this.messageSource = messageSource;
    }

    @Override
    public void getQuestions() {
         IOService.print(messageDao.getQuestions().readMessage());
    }

    @Override
    public List<String> getAnswersList() {
        return messageDao.getAnswers();
    }


}
