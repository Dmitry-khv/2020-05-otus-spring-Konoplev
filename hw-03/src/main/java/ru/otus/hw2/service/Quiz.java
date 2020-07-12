package ru.otus.hw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw2.config.YamlProps;
import ru.otus.hw2.model.Answer;
import ru.otus.hw2.model.Question;
import ru.otus.hw2.model.Student;
import ru.otus.hw2.model.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Quiz {
    private final QuestionService questionService;
    private final IOService ioService;
    private final GreetingService greetingService;
    private final YamlProps yamlProps;
    private final MessageSourceService messageSourceService;


    public void run() {
        Student student = greetingService.greetStudent();
        TestResult testResult = new TestResult();
        ioService.print("___________________");

        ioService.print(messageSourceService.getMessage("taskQuiz"));
        List<Question> questions = questionService.getQuestions();
        for (Question question : questions) {
            readQuestionAsString(question);
            Answer studentAnswer = writeAnswer();
            testResult.addQuestionAndAnswer(question, studentAnswer);
        }


        boolean isTestOk = testResult.getTestResult(yamlProps.getAnswersCount());

        if (isTestOk) {
            ioService.print(messageSourceService.getMessage("congrats", student.getFirstName()));
        } else {
            ioService.print(messageSourceService.getMessage("repeat", student.getFirstName()));
        }
    }

    public void readQuestionAsString(Question question) {
        ioService.print(question.getQuestion());
        List<Answer> allAnswers = question.getAnswers();
        for (Answer answer : allAnswers) {
            ioService.print(answer.getAnswerAsString());
        }
    }

    public Answer writeAnswer() {
        return new Answer(ioService.read());
    }
}
