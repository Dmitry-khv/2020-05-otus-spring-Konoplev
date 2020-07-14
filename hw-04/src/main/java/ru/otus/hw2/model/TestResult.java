package ru.otus.hw2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TestResult {

    private final Map<Question, Answer> studentTest = new HashMap<>();

    public void addQuestionAndAnswer(Question question, Answer answer) {
        studentTest.put(question, answer);
    }

    public boolean getTestResult(int shouldBeCorrectAnswers) {
        AtomicInteger correctCountAnswers = new AtomicInteger();
        studentTest.forEach((question, studentAnswer) -> {
            List<Answer> quizAnswersList = question.getAnswers();
            for (Answer quizAnswer : quizAnswersList) {
                if (quizAnswer.getAnswerAsString().startsWith(studentAnswer.getAnswerAsString())) {
                    if (quizAnswer.isRight()) {
                        correctCountAnswers.getAndIncrement();
                    }
                }
            }
        });
        int correctStudentAnswers = correctCountAnswers.get();
        return correctStudentAnswers >= shouldBeCorrectAnswers;
    }

}
