package com.education.paper.service;

import com.education.paper.entity.Knowledge;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface KnowledgeService {

    /*点击左侧菜单“知识点管理”后查询所有知识点*/
    public String knowledgeManagerMenu(HttpServletRequest request) throws Exception;
    /*添加一个知识点*/
    public String addOneKnowledge(Knowledge knowledge) throws Exception;

    /*点击左侧菜单“信息设置”后查询一级知识点*/
    public String knowledgeMessageMenu(HttpServletRequest request) throws Exception;

    /*导入Excel中学生成绩*/
    public boolean excelInfoImport(String name, MultipartFile file, HttpServletRequest request);
}
