package ru.otus.hw2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommandService {

    private final Quiz quiz;

    @ShellMethod(value = "launch quiz command", key = {"s", "start"})
    public String launchQuiz() {
        quiz.run();
        return "The end";
    }
}
