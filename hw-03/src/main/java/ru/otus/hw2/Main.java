package ru.otus.hw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw2.config.YamlProps;
import ru.otus.hw2.service.Quiz;

//@PropertySource("classpath:application.properties")
//@ComponentScan
//@Configuration
@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        context.getBean(Quiz.class).run();
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
//        Quiz quiz = context.getBean(Quiz.class);
//        quiz.run();
    }
}
