package ru.otus.hw1;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw1.model.Question;
import ru.otus.hw1.service.MessageService;

public class Main {
    private static ClassPathXmlApplicationContext context;
    private static MessageService messageService;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("/spring-context.xml");
        messageService = context.getBean(MessageService.class);
        Main runner = new Main();
        runner.run();
    }
    public void run() {
        messageService.getMessage(System.out);
        context.close();
    }
}
