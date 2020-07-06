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
        TestResult testResult = new TestResult();
        ioService.print("Мы начинаем!");
        ioService.print("___________________");

        ioService.print("Введите ответ, каждый с новой строки");
        List<Question> questions = questionService.getQuestions();
        for (Question question : questions) {
            readQuestionAsString(question);
            Answer studentAnswer = writeAnswer();
            testResult.addQuestionAndAnswer(question, studentAnswer);
        }


        boolean isTestOk = testResult.getTestResult(successCountAnswers);

        if (isTestOk) {
            ioService.print("Поздравляю? Вы молодец!");
        } else {
            ioService.print("Не плохое начало, давайте попробуем еще раз!");
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
