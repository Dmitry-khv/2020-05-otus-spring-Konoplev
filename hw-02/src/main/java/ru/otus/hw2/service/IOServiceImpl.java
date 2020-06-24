package ru.otus.hw2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

@Service
public class IOServiceImpl implements IOService {
    private final PrintStream printStream;

    @Autowired
    public IOServiceImpl(OutputStream outputStream) {
        this.printStream = new PrintStream(outputStream);
    }

    @Override
    public void print(String msg) {
        printStream.print(msg);
    }
}
