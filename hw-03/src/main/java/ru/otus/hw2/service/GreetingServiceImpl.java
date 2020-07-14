package ru.otus.hw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw2.model.Student;

@Service
@RequiredArgsConstructor
public class GreetingServiceImpl implements GreetingService {
    private final IOService ioService;
    private final MessageSourceService messageSourceService;

    @Override
    public Student greetStudent() {
        ioService.print(messageSourceService.getMessage("greeting"));
        ioService.print(messageSourceService.getMessage("firsName"));
        String firstName = ioService.read();
        ioService.print(messageSourceService.getMessage("lastName"));
        String lastName = ioService.read();
        return new Student(firstName, lastName);
    }
}
