package ru.otus.hw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw2.model.Student;

@Service
@RequiredArgsConstructor
public class GreetingServiceImpl implements GreetingService {
    private final IOService ioService;

    @Override
    public Student greetStudent() {
        ioService.print("Привет, вы готовы к тесту?");
        ioService.print("Введите ваше имя:");
        String firstName = ioService.read();
        ioService.print("Введите вашу фамилию:");
        String lastName = ioService.read();
        return new Student(firstName, lastName);
    }
}
