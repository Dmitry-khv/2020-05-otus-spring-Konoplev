package ru.otus.hw2.model;


public class Question {

    private final String question;

    public Question(String question) {
        this.question = question;
    }

    public String readMessage(){
        return question;
    }

}
