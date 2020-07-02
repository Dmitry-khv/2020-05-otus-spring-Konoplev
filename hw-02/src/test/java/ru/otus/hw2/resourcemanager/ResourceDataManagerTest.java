package ru.otus.hw2.resourcemanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import ru.otus.hw2.model.Question;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ResourceDataManagerTest {

    private ResourceData resourceData;
    private final String questionPath = "data.csv";
    private final String answersPath = "correct.answer.csv";
    private final List<String> answers = Arrays.asList("Correct answers");
    private List<Question> questionList;

    @BeforeEach
    public void setUp() {
        ClassLoader cl = getClass().getClassLoader();

        File questions = new File(cl.getResource(questionPath).getFile());
        Resource questionSource = new FileSystemResource(questions);

        File answers = new File(cl.getResource(answersPath).getFile());
        Resource answerSource = new FileSystemResource(answers);

        resourceData = new ResourceDataManager(questionSource, answerSource);
        questionList = resourceData.getQuestions();
        resourceData.setUpTrueAnswers(questionList);

    }


    @Test
    void correctQuestionFileParsing() {
        assertEquals(5, questionList.size());
    }

    @Test
    void correctAnswersFileParsing() {
        assertEquals(answers, resourceData.getAnswers());
    }

    @Test
    void correctDefineQuestion() {
        String question = "1.Question";
        String notQuestion = "Not question";
        assertTrue(resourceData.isQuestion(question));
        assertFalse(resourceData.isQuestion(notQuestion));
    }

    @Test
    void correctDefineAnswers() {
        int trueAnswer1 = 1;
        int trueAnswer3 = 1;
        int trueAnswer5 = 2;

        resourceData.setUpTrueAnswers(questionList);

        assertTrue(questionList.get(trueAnswer1-1).getAnswers().get(trueAnswer1-1).isRight());
        assertTrue(questionList.get(trueAnswer3-1).getAnswers().get(trueAnswer3-1).isRight());
        assertTrue(questionList.get(trueAnswer5-1).getAnswers().get(trueAnswer5-1).isRight());
    }
}