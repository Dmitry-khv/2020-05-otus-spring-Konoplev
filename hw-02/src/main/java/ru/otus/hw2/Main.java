package ru.otus.hw2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw2.service.MessageService;

@ComponentScan
@Configuration
public class Main {
    private static AnnotationConfigApplicationContext context;
    private static MessageService messageService;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Main.class);
        messageService = context.getBean(MessageService.class);
        Main runner = new Main();
        runner.run();
    }
    public void run() {
//        messageService.getMessage();
        messageService.getMessage(System.out);
        context.close();
    }
}
