package ru.otus.hw2.resourcemanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.hw2.model.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceDataManager implements ResourceData{
    private static final Logger LOG = LoggerFactory.getLogger(ResourceDataManager.class);
    private final Resource questionSource;
    private final Resource answerSource;

    @Autowired
    public ResourceDataManager(@Value("classpath:data.csv") Resource questionSource,
                               @Value("classpath:correct.answer.csv") Resource answerSource) {
        this.questionSource = questionSource;
        this.answerSource = answerSource;
    }

    @Override
    public Question getQuestions() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(questionSource.getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            LOG.error("question file not found...", e);
        }



        return new Question(result.toString());
    }

    @Override
    public List<String> getAnswers() {
        List<String> answersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(answerSource.getFile()))){
            String answer;
            while((answer = reader.readLine()) != null) {
                answersList.add(answer);
            }
        } catch (IOException e) {
            LOG.error("answer file not found...", e);
        }
        return answersList;
    }
}
