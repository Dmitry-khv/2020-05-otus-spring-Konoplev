package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw2.model.Student;

import java.util.List;

@Service
public class Quiz {
    private final QuestionService questionService;
    private final IOService ioService;
    private final int successCountAnswers;
    private final int questionCount;


    @Autowired
    public Quiz(QuestionService questionService, IOService ioService,
                @Value("${success.count.answer}") int successCountAnswers,
                @Value("${question.count}") int questionCount) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.successCountAnswers = successCountAnswers;
        this.questionCount = questionCount;
    }

    public void run() {
        Student student = greet();
        ioService.print("Мы начинаес!");
        ioService.print("___________________");
        ioService.print(questionService.getQuestions());
        ioService.print("Введите ответ каждый с новой строки");

        for (int i = 0; i < questionCount; i++) {
            student.setTest(ioService.read());
        }

        checkQuiz(student);

        if(student.isTestSuccess()) {
            ioService.print("Поздравляю? Вы молодец!");
        } else {
            ioService.print("Не плохое начало, давайте попробуем еще раз!");
        }

    }

    public Student greet() {
        ioService.print("Привет, вы готовы к тесту?");
        ioService.print("Введите ваше имя:");
        String firstName = ioService.read();
        ioService.print("Введите вашу фамилию:");
        String lastName = ioService.read();
        return new Student(firstName, lastName);
    }

    public void checkQuiz(Student student) {
        int correctCountAnswers = 0;
        List<String> studentAnswers = student.getTest();
        List<String> correctAnswers = questionService.getAnswersList();

        for (int i = 0; i < questionCount; i++) {
            if (studentAnswers.get(i).equals(correctAnswers.get(i)))
                correctCountAnswers++;
        }
        if(correctCountAnswers >= successCountAnswers) {
            student.setTestResult(true);
        }
    }
}
