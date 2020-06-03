package ru.otus.hw1.resourcemanager;

import org.springframework.core.io.Resource;
import ru.otus.hw1.model.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceDataManager implements ResourceData{
    private final Resource dataSource;

    public ResourceDataManager(Resource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Message getMessage() {
        StringBuffer result = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataSource.getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            result.append("file not found or smt else");
        }
        return new Message(result.toString());
    }
}
