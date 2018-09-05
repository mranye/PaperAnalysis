package com.education.paper.dao;

import com.education.paper.entity.Teacher;
import org.apache.ibatis.annotations.Param;


public interface TeacherDao {

    /*tb_teacher 根据用户名查询所有信息*/
    public Teacher selectLoginByUsername(String username)throws Exception;
    /*tb_teacher 插入用户名密码*/
    public void insertTeacherNameAndPwd(Teacher teacher) throws Exception;
    //public void insertTeacherNameAndPwd(String username,String password) throws Exception;
    /*tb_teacher 根据用户名更新信息*/
    public void updateTeacherAll(Teacher teacher) throws Exception;
    /*tb_teacher 重置密码*/
    public void updatePassword(Teacher teacher) throws Exception;


}
