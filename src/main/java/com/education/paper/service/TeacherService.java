package com.education.paper.service;

import com.education.paper.entity.Paper;
import com.education.paper.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface TeacherService {

    /*教师登录*/
    public Teacher login(String username,String password) throws Exception;
    //public String signup(Teacher teacher) throws Exception;
    /*教师注册*/
    public String signup(String username,String password) throws Exception;
    /*教师重置密码*/
    public String forgetPassword(String username,String password, HttpServletRequest request) throws Exception;
    /*教师个人信息管理*/
    public String userManager(Teacher teacher, Paper paper) throws Exception;
    /*教师点击个人信息-左侧菜单后查询*/
    public String userManagerMenu(String username,HttpServletRequest request) throws Exception;
    /*教师填写试卷信息*/
    public String analysisMessage(Paper paper,HttpServletRequest request) throws Exception;
    /*教师填写题目设置*/
    public String analysisQuestion(String[] type,String[] scoreRate,HttpServletRequest request) throws Exception;
    /*教师填写试卷信息-左侧菜单后查询*/
    public String analysisMessageMenu(String username,HttpServletRequest request) throws Exception;
    /*导入Excel中学生成绩*/
    public boolean excelInfoImport(String name, MultipartFile file, HttpServletRequest request);
    /*教师填写三个试卷分析*/
    public String analysisAnswer(Paper paper) throws Exception;
    /*查询该老师历史生成的所有报告*/
    public String paperList(Teacher teacher, HttpServletRequest request) throws Exception;
    /*删除一条分析报告*/
    public String deleteOnePaper(int paper_num) throws Exception;

}
