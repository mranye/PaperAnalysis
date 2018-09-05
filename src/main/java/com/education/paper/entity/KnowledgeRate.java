package com.education.paper.entity;

/** 知识点分析报告下载word时，需要的包含知识点名字和得分率的类 */
public class KnowledgeRate {

    private String knowledgeName;
    private String scorerate;

    public KnowledgeRate() {
    }

    public KnowledgeRate(String knowledgeName, String scorerate) {
        this.knowledgeName = knowledgeName;
        this.scorerate = scorerate;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public String getScorerate() {
        return scorerate;
    }

    public void setScorerate(String scorerate) {
        this.scorerate = scorerate;
    }
}
