package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw2.dao.QuestionDao;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final IOService ioService;


    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public String getQuestions() {
         return questionDao.getQuestions().readMessage();
    }

    @Override
    public List<String> getAnswersList() {
        return questionDao.getAnswers();
    }


}
