package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.hw2.model.Answer;
import ru.otus.hw2.model.Question;
import ru.otus.hw2.model.Student;

import java.util.List;

@Service
public class Quiz {
    private final QuestionService questionService;
    private final IOService ioService;
    private final Greeting greeting;
    private final int successCountAnswers;
    private final int questionCount;


    @Autowired
    public Quiz(QuestionService questionService, IOService ioService,
                Greeting greeting, @Value("${success.count.answer}") int successCountAnswers,
                @Value("${question.count}") int questionCount) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.greeting = greeting;
        this.successCountAnswers = successCountAnswers;
        this.questionCount = questionCount;
    }

    public void run() {
        Student student = greeting.greetStudent();
        ioService.print("Мы начинаем!");
        ioService.print("___________________");
        ioService.print(readQuestions());
        ioService.print("Введите ответ, каждый с новой строки");

        for (int i = 0; i < questionCount; i++) {
            student.setTest(new Answer(ioService.read()));
        }

        checkQuiz(student);

        if (student.getTestResult()) {
            ioService.print("Поздравляю? Вы молодец!");
        } else {
            ioService.print("Не плохое начало, давайте попробуем еще раз!");
        }
    }

    private String readQuestions() {
        StringBuilder sb = new StringBuilder();
        List<Question> questions = questionService.getQuestions();
        for (Question question : questions) {
            sb.append(question.getQuestion()).append("\n");
            for (Answer answer : question.getAnswers()) {
                sb.append(answer.getOptionAnswer()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void checkQuiz(Student student) {
        int correctCountAnswers = 0;
        List<Answer> studentAnswers = student.getTest();
        List<Question> questionList = questionService.getQuestions();

        for (int count = 0; count < questionList.size(); count++) {
            Question question = questionList.get(count);
            List<Answer> answers = question.getAnswers();
            for (Answer answer : answers) {

                if (answer.getOptionAnswer().startsWith(studentAnswers.get(count).getOptionAnswer())) {
                    if (answer.isRight()) {
                        correctCountAnswers++;
                    }
                }
            }
        }
        if (correctCountAnswers >= successCountAnswers) {
            student.setTestResult(true);
        }
    }
}
