package ru.otus.hw1;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw1.model.Message;
import ru.otus.hw1.service.MessageService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        MessageService messageService = context.getBean(MessageService.class);
        Message questions = messageService.getMessage();
        System.out.println(questions.readMessage());
    }
}
