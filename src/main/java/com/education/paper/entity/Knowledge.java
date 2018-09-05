package com.education.paper.entity;

/**
 * 知识点实体类
 */
public class Knowledge {

    private int knowledge_num;//知识点序号
    private String knowledgeName;//知识点名字
    private int topindex;//对应知识点模块序号，若为0，表示自身为知识点模块
    private int topindex2;//对应的一级知识点序号，若为0，表示自身为一级知识点或模块

    public int getTopindex2() {
        return topindex2;
    }

    public void setTopindex2(int topindex2) {
        this.topindex2 = topindex2;
    }

    public int getKnowledge_num() {
        return knowledge_num;
    }

    public void setKnowledge_num(int knowledge_num) {
        this.knowledge_num = knowledge_num;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public int getTopindex() {
        return topindex;
    }

    public void setTopindex(int topindex) {
        this.topindex = topindex;
    }
}
