package ru.otus.hw2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw2.service.Quiz;

@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Quiz quiz = context.getBean(Quiz.class);
        quiz.run();
    }
}
