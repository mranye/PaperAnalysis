package com.education.paper.entity;

public class QuestionScore {

    private int question_number;//大题数目
    private String[] score;

    public QuestionScore(int question_number) {
        this.question_number = question_number;
        this.score = new String[question_number];
    }

    public QuestionScore(int question_number, String[] score) {
        this.question_number = question_number;
        this.score = new String[question_number];
        for (int i=0;i<question_number;i++){
            this.score[i] = score[i];
        }
    }

    public int getQuestion_number() {
        return question_number;
    }

    public void setQuestion_number(int question_number) {
        this.question_number = question_number;
    }

    public String[] getScore() {
        return score;
    }

    public void setScore(String[] score) {
        this.score = score;
    }
}
