package ru.otus.hw2.dao;

import ru.otus.hw2.model.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getQuestions();

    List<String> getAnswers();
}
