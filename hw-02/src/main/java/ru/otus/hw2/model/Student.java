package ru.otus.hw2.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String firstName;
    private final String lastName;
    private List<String> test = new ArrayList<>();
    private boolean testResult = false;


    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<String> getTest() {
        return test;
    }

    public boolean isTestSuccess() {
        return testResult;
    }

    public void setTestResult(boolean result) {
        this.testResult = result;
    }

    public void setTest( String answer) {
        test.add(answer);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
