package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw2.model.Student;

import java.util.List;
import java.util.Locale;

@Service
public class Quiz {
    private final MessageService messageService;
    private final IOService ioService;
    private final MessageSource messageSource;
    private static final Locale LOCALE_EN = Locale.ENGLISH;

    @Value("${success.count.answer}")
    private int successCountAnswers;

    @Value("${question.count}")
    private int questionCount;


    @Autowired
    public Quiz(MessageService messageService, IOService ioService, MessageSource messageSource) {
        this.messageService = messageService;
        this.ioService = ioService;
        this.messageSource = messageSource;
    }

    public void run() {
        Student student = greet();
        ioService.print("Let's do the Show!");
        ioService.print("___________________");
        messageService.getQuestions();
        ioService.print("Enter your answers each in a new line");

        for (int i = 0; i < questionCount; i++) {
            student.setTest(ioService.read());
        }

        checkQuiz(student);

        if(student.isTestSuccess()) {
            ioService.print(messageSource.getMessage("success", null, LOCALE_EN));
        } else {
            ioService.print(messageSource.getMessage("fail", null, LOCALE_EN));
        }

    }

    public Student greet() {
        ioService.print(messageSource.getMessage("greeting", null, LOCALE_EN));
        ioService.print(messageSource.getMessage("first.name", null, LOCALE_EN));
        String firstName = ioService.read();
        ioService.print(messageSource.getMessage("last.name", null, LOCALE_EN));
        String lastName = ioService.read();
        return new Student(firstName, lastName);
    }

    public void checkQuiz(Student student) {
        int correctCountAnswers = 0;
        List<String> studentAnswers = student.getTest();
        List<String> correctAnswers = messageService.getAnswersList();

        for (int i = 0; i < questionCount; i++) {
            if (studentAnswers.get(i).equals(correctAnswers.get(i)))
                correctCountAnswers++;
        }
        if(correctCountAnswers >= successCountAnswers) {
            student.setTestResult(true);
        }
    }
}
