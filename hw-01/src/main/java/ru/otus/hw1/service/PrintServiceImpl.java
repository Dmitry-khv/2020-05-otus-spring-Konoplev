package ru.otus.hw1.service;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintServiceImpl implements PrintService{
    private final PrintStream printStream;

    public PrintServiceImpl(OutputStream outputStream) {
        this.printStream = new PrintStream(outputStream);
    }

    @Override
    public void print(String msg) {
        printStream.print(msg);
    }
}
