package com.education.paper.dao;

import com.education.paper.entity.Knowledge;

import java.util.ArrayList;

public interface KnowledgeDao {

    /*tb_knowledge 查询所有知识点的所有信息*/
    public ArrayList<Knowledge> selectAllKnowledge() throws Exception;
    /*tb_knowledge 查询topindex!=0的所有知识点的所有信息*/
    public ArrayList<Knowledge> selectIndexNot0() throws Exception;
    /*tb_knowledge 插入一条知识点信息*/
    public void insertOneKnowledge(Knowledge knowledge) throws Exception;

}
