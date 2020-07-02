package ru.otus.hw2.resourcemanager;

import ru.otus.hw2.model.Question;

import java.util.List;

public interface ResourceData {
    List<Question> getQuestions();

    List<String> getAnswers();

    boolean isQuestion(String line);

    void setUpTrueAnswers(List<Question> questionList);
}
