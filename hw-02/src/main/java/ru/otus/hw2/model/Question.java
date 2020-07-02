package ru.otus.hw2.model;


import java.util.ArrayList;
import java.util.List;

public class Question {

    private String question;
    private List<Answer> answers = new ArrayList<>();

    public String getQuestion(){
        return question;
    }

    public List<Answer>getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
