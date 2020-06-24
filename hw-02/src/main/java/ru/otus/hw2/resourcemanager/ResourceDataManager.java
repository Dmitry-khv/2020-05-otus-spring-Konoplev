package ru.otus.hw2.resourcemanager;

import org.springframework.core.io.Resource;
import ru.otus.hw2.model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceDataManager implements ResourceData{
    private final Resource dataSource;

    public ResourceDataManager(Resource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Question getMessage() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataSource.getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            result.append("file not found or smt else");
        }
        return new Question(result.toString());
    }
}
