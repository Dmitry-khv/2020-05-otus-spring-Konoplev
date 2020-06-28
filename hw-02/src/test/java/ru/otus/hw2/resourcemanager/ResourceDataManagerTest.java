package ru.otus.hw2.resourcemanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResourceDataManagerTest {

    private ResourceData resourceData;
    private final String questionPath = "data.csv";
    private final String answersPath = "correct.answer.csv";
    private final String question = "Test question file\n";
    private final List<String> answers = Arrays.asList("Correct answers");

    @BeforeEach
    public void setUp() {
        ClassLoader cl = getClass().getClassLoader();

        File questions = new File(cl.getResource(questionPath).getFile());
        Resource questionSource = new FileSystemResource(questions);

        File answers = new File(cl.getResource(answersPath).getFile());
        Resource answerSource = new FileSystemResource(answers);

        resourceData = new ResourceDataManager(questionSource, answerSource);
    }


    @Test
    void correctQuestionFileParsing() {
        assertEquals(question, resourceData.getQuestions().readMessage());
    }

    @Test
    void correctAnswersFileParsing() {
        assertEquals(answers, resourceData.getAnswers());
    }
}