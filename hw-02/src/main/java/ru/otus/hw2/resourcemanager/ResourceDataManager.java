package ru.otus.hw2.resourcemanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.hw2.model.Answer;
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
    public List<Question> getQuestions() {
        List<Question> questionList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(questionSource.getFile()))) {
            String line;
            Question question = new Question();
            while ((line = reader.readLine()) != null) {
                if (isQuestion(line)) {
                    question = new Question();
                    question.setQuestion(line);
                } else if (!line.isEmpty()) {
                    question.addAnswer(new Answer(line));
                } else {
                    questionList.add(question);
                }
            }
            if (!questionList.contains(question)) {
                questionList.add(question);
            }
        } catch (IOException e) {
            LOG.error("question file not found {}", e.getMessage());
        }
        setUpTrueAnswers(questionList);
        return questionList;
    }

    public void setUpTrueAnswers(List<Question> questionList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(answerSource.getFile()))) {
            String trueAnswer;
            int count = 0;
            while ((trueAnswer = reader.readLine()) != null) {
                Question question = questionList.get(count);
                List<Answer> answers = question.getAnswers();
                for (Answer answer : answers) {
                    if (answer.getAnswerAsString().startsWith(trueAnswer)) {
                        answer.setRight(true);
                    }
                }
                count++;
            }
        } catch (IOException e) {

        }
    }

    public boolean isQuestion(String line) {
        boolean result = false;
        for (char c : line.toCharArray()) {
            if (Character.isDigit(c)) {
                result = true;
            } else {
                return false;
            }
            break;
        }
        return result;
    }

    @Override
    public List<Answer> getAnswers() {
        List<Answer> answersList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(answerSource.getFile()))){
            String answer;
            while((answer = reader.readLine()) != null) {
                answersList.add(new Answer(answer));
            }
        } catch (IOException e) {
            LOG.error("answer file not found...", e);
        }
        return answersList;
    }
}
