package ru.otus.hw2.service;

public interface MessageSourceService {
    String getMessage(String message);
    String getMessage(String message, String obj);
}
