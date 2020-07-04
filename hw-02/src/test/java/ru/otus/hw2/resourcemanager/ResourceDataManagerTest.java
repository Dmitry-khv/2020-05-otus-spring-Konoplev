package ru.otus.hw2.resourcemanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import ru.otus.hw2.model.Answer;
import ru.otus.hw2.model.Question;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("класс ResourceManagerTest должен")
@ExtendWith(MockitoExtension.class)
class ResourceDataManagerTest {

    private ResourceData resourceData;
    private static final String QUESTION_PATH = "data.csv";
    private static final String CORRECT_ANSWER_CSV = "correct.answer.csv";
    private List<Question> questionList;

    @BeforeEach
    public void setUp() {
        ClassLoader cl = getClass().getClassLoader();

        File questions = new File(cl.getResource(QUESTION_PATH).getFile());
        Resource questionSource = new FileSystemResource(questions);

        File answers = new File(cl.getResource(CORRECT_ANSWER_CSV).getFile());
        Resource answerSource = new FileSystemResource(answers);

        resourceData = new ResourceDataManager(questionSource, answerSource);
        questionList = resourceData.getQuestions();
        resourceData.setUpTrueAnswers(questionList);

    }


    @DisplayName("корректно парсить файл вопросов")
    @Test
    void correctQuestionFileParsing() {
        assertEquals(5, questionList.size());
    }

    @DisplayName("корректоно парсить файл ответов")
    @Test
    void correctAnswersFileParsing() {
        assertEquals("a", resourceData.getAnswers().get(0).getAnswerAsString());
        assertEquals("b", resourceData.getAnswers().get(1).getAnswerAsString());
        assertEquals("a", resourceData.getAnswers().get(2).getAnswerAsString());
        assertEquals("a", resourceData.getAnswers().get(3).getAnswerAsString());
        assertNotEquals("a", resourceData.getAnswers().get(4).getAnswerAsString());
    }

    @DisplayName("отделять вопросы в файле")
    @Test
    void correctDefineQuestion() {
        String question = "1.Question";
        String notQuestion = "Not question";
        assertTrue(resourceData.isQuestion(question));
        assertFalse(resourceData.isQuestion(notQuestion));
    }

    @DisplayName("отделять отвуты в файле")
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