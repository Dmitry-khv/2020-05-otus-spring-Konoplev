package ru.otus.hw2.model;

public class Answer {

    private final String optionAnswer;
    private boolean isRight = false;

    public Answer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }

    public String getOptionAnswer() {
        return optionAnswer;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
