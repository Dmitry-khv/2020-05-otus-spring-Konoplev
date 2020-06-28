package ru.otus.hw2.resourcemanager;

import ru.otus.hw2.model.Question;

import java.util.List;

public interface ResourceData {
    Question getQuestions();

    List<String> getAnswers();
}
