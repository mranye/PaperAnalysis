package com.education.paper.dao;

import com.education.paper.entity.Paper;

import java.util.ArrayList;
import java.util.List;

public interface PaperDao {

    /*tb_paper 根据用户名查询所有paper的所有信息*/
    public ArrayList<Paper> selectAllByUsername(String username)throws Exception;
    /*tb_paper 根据用户名查询未计算最高值等信息的paper*/
    public Paper selectAllByUsernameOne(String username)throws Exception;
    /*tb_paper 插入用户名，学分，大题数目的一条信息*/
    public void insertUsername(Paper paper)throws Exception;
    /*tb_paper 根据用户名更新学分，大题数目*/
    public void updateCreditAndQNum(Paper paper)throws Exception;
    /*tb_paper 根据用户名更新专业，学年，学期，考试时间*/
    public void updateOtherTitle(Paper paper)throws Exception;
    /*tb_paper 根据用户名更新题目设置*/
    public void updateQuestion(Paper paper)throws Exception;
    /*tb_paper 根据用户名更新所有分析数据*/
    public void updateJisuan(Paper paper)throws Exception;
    /*tb_paper 根据用户名更新教师填写分析答案，报告生成时间*/
    public void updateAnswers(Paper paper)throws Exception;
    /*tb_paper 根据试卷id paper_num 删除一条试卷信息*/
    public void deleteOneByNum(int paper_num)throws Exception;

}
