package com.education.paper.entity;


/**
 * 试卷分析实体类
 */
public class Paper {

    private int paper_num;//试卷分析序号
    private String username;//登录用户名
    private String profession;//考试专业年级
    //private int class_hour;//学时
    private String credit;//学时学分
    private int question_num;//大题数目
    private String study_year;//学年
    private String term;//学期
    private String exam_time;//考试日期
    private int number_all;//应考人数
    private int number_join;//实考人数
    private float score_high;//最高分
    private float score_lowest;//最低分
    private float rate_pass;//及格率
    private float score_average;//平均分
    private float score_variance;//均方差
    private float rate_difficulty;//试题难度分布率
    private float rate_distribution;//区分度分布率
    private int people0to9;//0-9分人数
    private int people10to19;//10-19分人数
    private int people20to29;//20-29分人数
    private int people30to39;//30-39分人数
    private int people40to49;//40-49分人数
    private int people50to59;//50-59分人数
    private int people60to69;//60-69分人数
    private int people70to79;//70-79分人数
    private int people80to89;//80-89分人数
    private int people90;//90分以上人数
    private float per0to9;//0-9分百分比
    private float per10to19;//10-19分百分比
    private float per20to29;//20-29分百分比
    private float per30to39;//30-39分百分比
    private float per40to49;//40-49分百分比
    private float per50to59;//50-59分百分比
    private float per60to69;//60-69分百分比
    private float per70to79;//70-79分百分比
    private float per80to89;//80-89分百分比
    private float per90;//90分以上百分比
    private String answer1;//老师填写分析1
    private String answer2;//老师填写分析2
    private String answer3;//老师填写分析3
    private String analysis_time;//分析报告生成时间
    private String questionType1;//第一大题的题目类型
    private String questionType2;
    private String questionType3;
    private String questionType4;
    private String questionType5;
    private String questionType6;
    private String questionType7;
    private String questionType8;
    private String questionType9;
    private float questionScoreRate1;//第一大题的得分率
    private float questionScoreRate2;
    private float questionScoreRate3;
    private float questionScoreRate4;
    private float questionScoreRate5;
    private float questionScoreRate6;
    private float questionScoreRate7;
    private float questionScoreRate8;
    private float questionScoreRate9;


    public int getPaper_num() {
        return paper_num;
    }

    public void setPaper_num(int paper_num) {
        this.paper_num = paper_num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    /*public int getClass_hour() {
        return class_hour;
    }

    public void setClass_hour(int class_hour) {
        this.class_hour = class_hour;
    }*/

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }

    public String getStudy_year() {
        return study_year;
    }

    public void setStudy_year(String study_year) {
        this.study_year = study_year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getExam_time() {
        return exam_time;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
    }

    public int getNumber_all() {
        return number_all;
    }

    public void setNumber_all(int number_all) {
        this.number_all = number_all;
    }

    public int getNumber_join() {
        return number_join;
    }

    public void setNumber_join(int number_join) {
        this.number_join = number_join;
    }

    public float getScore_high() {
        return score_high;
    }

    public void setScore_high(float score_high) {
        this.score_high = score_high;
    }

    public float getScore_lowest() {
        return score_lowest;
    }

    public void setScore_lowest(float score_lowest) {
        this.score_lowest = score_lowest;
    }

    public float getRate_pass() {
        return rate_pass;
    }

    public void setRate_pass(float rate_pass) {
        this.rate_pass = rate_pass;
    }

    public float getScore_average() {
        return score_average;
    }

    public void setScore_average(float score_average) {
        this.score_average = score_average;
    }

    public float getScore_variance() {
        return score_variance;
    }

    public void setScore_variance(float score_variance) {
        this.score_variance = score_variance;
    }

    public float getRate_difficulty() {
        return rate_difficulty;
    }

    public void setRate_difficulty(float rate_difficulty) {
        this.rate_difficulty = rate_difficulty;
    }

    public float getRate_distribution() {
        return rate_distribution;
    }

    public void setRate_distribution(float rate_distribution) {
        this.rate_distribution = rate_distribution;
    }

    public int getPeople0to9() {
        return people0to9;
    }

    public void setPeople0to9(int people0to9) {
        this.people0to9 = people0to9;
    }

    public int getPeople10to19() {
        return people10to19;
    }

    public void setPeople10to19(int people10to19) {
        this.people10to19 = people10to19;
    }

    public int getPeople20to29() {
        return people20to29;
    }

    public void setPeople20to29(int people20to29) {
        this.people20to29 = people20to29;
    }

    public int getPeople30to39() {
        return people30to39;
    }

    public void setPeople30to39(int people30to39) {
        this.people30to39 = people30to39;
    }

    public int getPeople40to49() {
        return people40to49;
    }

    public void setPeople40to49(int people40to49) {
        this.people40to49 = people40to49;
    }

    public int getPeople50to59() {
        return people50to59;
    }

    public void setPeople50to59(int people50to59) {
        this.people50to59 = people50to59;
    }

    public int getPeople60to69() {
        return people60to69;
    }

    public void setPeople60to69(int people60to69) {
        this.people60to69 = people60to69;
    }

    public int getPeople70to79() {
        return people70to79;
    }

    public void setPeople70to79(int people70to79) {
        this.people70to79 = people70to79;
    }

    public int getPeople80to89() {
        return people80to89;
    }

    public void setPeople80to89(int people80to89) {
        this.people80to89 = people80to89;
    }

    public int getPeople90() {
        return people90;
    }

    public void setPeople90(int people90) {
        this.people90 = people90;
    }

    public float getPer0to9() {
        return per0to9;
    }

    public void setPer0to9(float per0to9) {
        this.per0to9 = per0to9;
    }

    public float getPer10to19() {
        return per10to19;
    }

    public void setPer10to19(float per10to19) {
        this.per10to19 = per10to19;
    }

    public float getPer20to29() {
        return per20to29;
    }

    public void setPer20to29(float per20to29) {
        this.per20to29 = per20to29;
    }

    public float getPer30to39() {
        return per30to39;
    }

    public void setPer30to39(float per30to39) {
        this.per30to39 = per30to39;
    }

    public float getPer40to49() {
        return per40to49;
    }

    public void setPer40to49(float per40to49) {
        this.per40to49 = per40to49;
    }

    public float getPer50to59() {
        return per50to59;
    }

    public void setPer50to59(float per50to59) {
        this.per50to59 = per50to59;
    }

    public float getPer60to69() {
        return per60to69;
    }

    public void setPer60to69(float per60to69) {
        this.per60to69 = per60to69;
    }

    public float getPer70to79() {
        return per70to79;
    }

    public void setPer70to79(float per70to79) {
        this.per70to79 = per70to79;
    }

    public float getPer80to89() {
        return per80to89;
    }

    public void setPer80to89(float per80to89) {
        this.per80to89 = per80to89;
    }

    public float getPer90() {
        return per90;
    }

    public void setPer90(float per90) {
        this.per90 = per90;
    }

    public String getQuestionType1() {
        return questionType1;
    }

    public void setQuestionType1(String questionType1) {
        this.questionType1 = questionType1;
    }

    public String getQuestionType2() {
        return questionType2;
    }

    public void setQuestionType2(String questionType2) {
        this.questionType2 = questionType2;
    }

    public String getQuestionType3() {
        return questionType3;
    }

    public void setQuestionType3(String questionType3) {
        this.questionType3 = questionType3;
    }

    public String getQuestionType4() {
        return questionType4;
    }

    public void setQuestionType4(String questionType4) {
        this.questionType4 = questionType4;
    }

    public String getQuestionType5() {
        return questionType5;
    }

    public void setQuestionType5(String questionType5) {
        this.questionType5 = questionType5;
    }

    public String getQuestionType6() {
        return questionType6;
    }

    public void setQuestionType6(String questionType6) {
        this.questionType6 = questionType6;
    }

    public String getQuestionType7() {
        return questionType7;
    }

    public void setQuestionType7(String questionType7) {
        this.questionType7 = questionType7;
    }

    public String getQuestionType8() {
        return questionType8;
    }

    public void setQuestionType8(String questionType8) {
        this.questionType8 = questionType8;
    }

    public String getQuestionType9() {
        return questionType9;
    }

    public void setQuestionType9(String questionType9) {
        this.questionType9 = questionType9;
    }

    public float getQuestionScoreRate1() {
        return questionScoreRate1;
    }

    public void setQuestionScoreRate1(float questionScoreRate1) {
        this.questionScoreRate1 = questionScoreRate1;
    }

    public float getQuestionScoreRate2() {
        return questionScoreRate2;
    }

    public void setQuestionScoreRate2(float questionScoreRate2) {
        this.questionScoreRate2 = questionScoreRate2;
    }

    public float getQuestionScoreRate3() {
        return questionScoreRate3;
    }

    public void setQuestionScoreRate3(float questionScoreRate3) {
        this.questionScoreRate3 = questionScoreRate3;
    }

    public float getQuestionScoreRate4() {
        return questionScoreRate4;
    }

    public void setQuestionScoreRate4(float questionScoreRate4) {
        this.questionScoreRate4 = questionScoreRate4;
    }

    public float getQuestionScoreRate5() {
        return questionScoreRate5;
    }

    public void setQuestionScoreRate5(float questionScoreRate5) {
        this.questionScoreRate5 = questionScoreRate5;
    }

    public float getQuestionScoreRate6() {
        return questionScoreRate6;
    }

    public void setQuestionScoreRate6(float questionScoreRate6) {
        this.questionScoreRate6 = questionScoreRate6;
    }

    public float getQuestionScoreRate7() {
        return questionScoreRate7;
    }

    public void setQuestionScoreRate7(float questionScoreRate7) {
        this.questionScoreRate7 = questionScoreRate7;
    }

    public float getQuestionScoreRate8() {
        return questionScoreRate8;
    }

    public void setQuestionScoreRate8(float questionScoreRate8) {
        this.questionScoreRate8 = questionScoreRate8;
    }

    public float getQuestionScoreRate9() {
        return questionScoreRate9;
    }

    public void setQuestionScoreRate9(float questionScoreRate9) {
        this.questionScoreRate9 = questionScoreRate9;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnalysis_time() {
        return analysis_time;
    }

    public void setAnalysis_time(String analysis_time) {
        this.analysis_time = analysis_time;
    }
}
