package ru.otus.hw2.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw2.service.Quiz;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {

    private final Quiz quiz;

    @ShellMethod(value = "launch quiz command", key = {"s", "start"})
    public String launchQuiz() {
        quiz.run();
        return "The end";
    }

    @ShellMethod(value = "get test result", key = {"r", "result"})
    @ShellMethodAvailability(value = "isAvailableTestResult")
    public String getStudentTestResult() {
        return quiz.getStudentTestResultAsString();
    }

    public Availability isAvailableTestResult() {
        return quiz.isTestDone() ? Availability.available() : Availability.unavailable("Should take the Quiz");
    }

}
