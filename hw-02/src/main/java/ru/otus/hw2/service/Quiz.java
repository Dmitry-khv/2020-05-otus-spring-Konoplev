package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw2.model.Answer;
import ru.otus.hw2.model.Question;
import ru.otus.hw2.model.Student;
import ru.otus.hw2.model.TestResult;

import java.util.List;

@Service
public class Quiz {
    private final QuestionService questionService;
    private final IOService ioService;
    private final GreetingService greetingService;
    private final int successCountAnswers;


    @Autowired
    public Quiz(QuestionService questionService, IOService ioService,
                GreetingService greetingService, @Value("${success.count.answer}") int successCountAnswers) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.greetingService = greetingService;
        this.successCountAnswers = successCountAnswers;
    }

    public void run() {
        Student student = greetingService.greetStudent();
        TestResult testResult = new TestResult(student);
        ioService.print("Мы начинаем!");
        ioService.print("___________________");

        readQuestionAndAnswersAsString(testResult);

        boolean isTestOk = testResult.getTestResult(successCountAnswers);

        if (isTestOk) {
            ioService.print("Поздравляю? Вы молодец!");
        } else {
            ioService.print("Не плохое начало, давайте попробуем еще раз!");
        }
    }

    public void readQuestionAndAnswersAsString(TestResult testResult) {
        ioService.print("Введите ответ, каждый с новой строки");
        List<Question> questions = questionService.getQuestions();
        for (Question question : questions) {
            ioService.print(question.getQuestion());
            List<Answer> allAnswers = question.getAnswers();
            for (Answer answer : allAnswers) {
                ioService.print(answer.getAnswerAsString());
            }

            Answer studentAnswer = new Answer(ioService.read());
            testResult.addQuestionAndAnswer(question, studentAnswer);
        }
    }
}
