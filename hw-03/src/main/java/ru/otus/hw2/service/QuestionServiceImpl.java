package ru.otus.hw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw2.dao.QuestionDao;
import ru.otus.hw2.model.Question;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    @Override
    public List<Question> getQuestions() {
         return questionDao.getQuestions();
    }
}
