package ru.otus.hw2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class IOServiceImpl implements IOService, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOServiceImpl.class);
    private final BufferedReader reader;
    private final PrintStream printStream;

    public IOServiceImpl(InputStream is, OutputStream os) {
        reader = new BufferedReader(new InputStreamReader(is));
        printStream = new PrintStream(os);
    }

    @Override
    public String read() {
        String msg = null;
        try {
             msg = reader.readLine();
        } catch (IOException e) {
            LOGGER.error("Incorrect input", e);
        }
        return msg;
    }

    @Override
    public void print(String msg) {
        printStream.println(msg);
    }

    @Override
    public void print(int num) {
        printStream.println(num);
    }

    @Override
    public void close() throws Exception {
        reader.close();
        printStream.close();
    }

}
