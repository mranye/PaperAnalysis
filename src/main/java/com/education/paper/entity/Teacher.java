package com.education.paper.entity;

/**教师实体类*/
public class Teacher {

    private int teacher_num;//教师标号
    private String username;//登录注册用户名
    private String password;//密码
    private String teacher_name;//教师姓名
    private String course;//课程名字
    private String college;//所在院系

    public int getTeacher_num() {
        return teacher_num;
    }

    public void setTeacher_num(int teacher_num) {
        this.teacher_num = teacher_num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        //this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
