package com.education.paper.entity;

public class KnowledgeQuestionScore {

    private float score;//某列成绩总和
    private int knowledgeindex;//该成绩对应的知识点序号
    private  float scorefull;//某列满分，即这道题总分*实考学生人数

    public KnowledgeQuestionScore(float score, int knowledgeindex, float scorefull) {
        this.score = score;
        this.knowledgeindex = knowledgeindex;
        this.scorefull = scorefull;
    }

    public float getScorefull() {
        return scorefull;
    }

    public void setScorefull(float scorefull) {
        this.scorefull = scorefull;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getKnowledgeindex() {
        return knowledgeindex;
    }

    public void setKnowledgeindex(int knowledgeindex) {
        this.knowledgeindex = knowledgeindex;
    }
}
