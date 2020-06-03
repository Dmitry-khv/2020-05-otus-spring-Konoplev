package ru.otus.hw1.model;

public class Message {

    private final String msg;

    public Message(String msg) {
        this.msg = msg;
    }

    public String readMessage(){
        return msg;
    }

}
