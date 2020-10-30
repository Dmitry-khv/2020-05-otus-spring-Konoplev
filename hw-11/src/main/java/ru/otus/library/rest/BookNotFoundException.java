package ru.otus.library.rest;

public class BookNotFoundException extends Exception{

    public BookNotFoundException() {
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
