package ru.otus.hw2.resourcemanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw2.model.Answer;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceDataManagerTest {
    private final List<Answer> shouldBeInAnswerFile = Arrays.asList(new Answer("a"), new Answer("b"));
    @Autowired
    private ResourceDataManager resourceDataManager;

    @Test
    void getAnswers() {
        for (int i = 0; i < shouldBeInAnswerFile.size(); i++) {
            assertEquals(shouldBeInAnswerFile.get(i).getAnswerAsString(), resourceDataManager.getAnswers().get(i).getAnswerAsString());
        }
    }
}