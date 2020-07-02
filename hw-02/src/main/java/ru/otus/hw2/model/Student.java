package ru.otus.hw2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Student {
    private final String firstName;
    private final String lastName;
    @Getter
    private List<Answer> test = new ArrayList<>();

    @Setter
    @Getter
    private boolean testResult = false;

    public void setTest(Answer answer) {
        test.add(answer);
    }

    public boolean getTestResult() {
        return testResult;
    }

    public void setTestResult(boolean result) {
        this.testResult = result;
    }
}
