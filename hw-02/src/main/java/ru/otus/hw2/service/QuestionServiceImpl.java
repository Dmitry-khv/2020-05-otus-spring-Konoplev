package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw2.dao.QuestionDao;
import ru.otus.hw2.model.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;


    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getQuestions() {
         return questionDao.getQuestions();
    }
}
