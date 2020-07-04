package ru.otus.hw2.model;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class TestResult {

    private final Student student;
    private Map<Question, Answer> studentTest = new HashMap<>();

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
