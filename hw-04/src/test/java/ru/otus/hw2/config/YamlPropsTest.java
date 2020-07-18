package ru.otus.hw2.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YamlPropsTest {

    @Autowired
    private YamlProps yamlProps;

    @Test
    void shouldCorrectGetQuestionFileFromYaml() {
        assertEquals("data_en.csv", yamlProps.getQuestionFile());
    }

    @Test
    void shouldCorrectGetQuestionCountFromYaml() {
        assertEquals(5, yamlProps.getQuestionsCount());
    }

    @Test
    void shouldCorrectGetAnswerCountFromYaml() {
        assertEquals(3, yamlProps.getAnswersCount());
    }

    @Test
    void shouldCorrectGetLocaleFromYaml() {
        assertEquals(Locale.ENGLISH, yamlProps.getLocale());
    }
}