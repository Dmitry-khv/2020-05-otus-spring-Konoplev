package ru.otus.hw2.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw2.model.Question;
import ru.otus.hw2.resourcemanager.ResourceData;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceData resource;

    @Override
    public List<Question> getQuestions() {
        return resource.getQuestions();
    }

    @Override
    public List<String> getAnswers() {
        return resource.getAnswers();
    }
}
