package ru.otus.hw1.resourcemanager;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import ru.otus.hw1.model.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceDataManager implements ResourceData{
    private final Resource dataSource;
//    private static Logger logger = LoggerFactory.getLogger(ResourceDataManager.class);

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
//            logger.error(e.getMessage(), e);
        }
        return new Message(result.toString());
    }
}
