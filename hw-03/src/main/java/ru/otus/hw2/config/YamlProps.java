package ru.otus.hw2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class YamlProps {
    private String questionFile;
    private String answerFile;
    private Locale locale;
    private int questionsCount;
    private int answersCount;

    public void setQuestionFile(String questionFile) {
        this.questionFile = questionFile.replace("{}", locale.toString());
    }

    public Resource getQuestionSource(){
        return new FileSystemResource(getQuestionFile());
    }

    public Resource getAnswerSource() {
        return new FileSystemResource(getAnswerFile());
    }
}
