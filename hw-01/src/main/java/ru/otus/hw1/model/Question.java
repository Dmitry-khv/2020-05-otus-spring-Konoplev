package ru.otus.hw1.model;

public class Question {

    private final String msg;

    public Question(String msg) {
        this.msg = msg;
    }

    public String readMessage(){
        return msg;
    }

}
