package com.education.paper.service.impl;

import com.education.paper.dao.TeacherDao;
import com.education.paper.dao.PaperDao;
import com.education.paper.entity.Paper;
import com.education.paper.entity.QuestionScore;
import com.education.paper.entity.Teacher;
import com.education.paper.service.TeacherService;
import com.education.paper.util.ExcelUtil;
import com.education.paper.util.Jisuan;
import com.education.paper.util.QuestionscoreRate;
import com.education.paper.util.ScoreSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    TeacherDao teacherDao;
    @Autowired
    PaperDao paperDao;

    /**登录操作*/
    @Override
    public Teacher login(String username,String password) throws Exception {

        //String message = null;
        if (username==null||username.trim().length()==0){
            //message = "用户名不能为空";
            throw new Exception("用户名不能为空");
        }
        if (password==null||password.trim().length()==0){
            //message = "密码不能为空";
            throw new Exception("密码不能为空");
        }
        Teacher teacherOut = teacherDao.selectLoginByUsername(username);
        if (teacherOut==null){
            //message = "账号不存在";
            throw new Exception("账号不存在");
        }
        if (!teacherOut.getPassword().equals(password)){
            //message = "密码不正确";
            throw new Exception("密码不正确");
        }

        return teacherOut;
    }

    /**教师注册*/
    @Override
    public String signup(String username,String password) throws Exception {
        String message = null;
        if (username==null||username.trim().length()==0){
            message = "请填写用户名";
            //throw new Exception("请填写用户名");
            return message;
        }
        if (password==null||password.trim().length()==0){
            message = "请填写密码";
            //throw new Exception("请填写密码");
            return message;
        }
        Teacher teacherOut = teacherDao.selectLoginByUsername(username);
        if (teacherOut==null){
            Teacher teacherIn=new Teacher();
            teacherIn.setUsername(username);
            teacherIn.setPassword(password);
            teacherDao.insertTeacherNameAndPwd(teacherIn);
        }else {
            message = "该用户名已被注册";
        }
        return message;
    }

    /**教师重置密码*/
    @Override
    public String forgetPassword(String username, String password, HttpServletRequest request) throws Exception {
        String message = null;
        HttpSession session = request.getSession();
        if (username==null||username.trim().length()==0){
            message = "请填写用户名";
            return message;
        }
        if (password==null||password.trim().length()==0){
            message = "请填写密码";
            return message;
        }
        Teacher teacherOut = teacherDao.selectLoginByUsername(username);
        if (teacherOut==null){
            message = "用户名不存在";
            return message;
        }else {
            Teacher teacherIn = new Teacher();
            teacherIn.setUsername(username);
            teacherIn.setPassword(password);
            teacherDao.updatePassword(teacherIn);
        }

        return message;
    }

    /**教师个人信息管理*/
    @Override
    public String userManager(Teacher teacher, Paper paper) throws Exception {
        String message = null;
        teacherDao.updateTeacherAll(teacher);
        String username = teacher.getUsername();
        /*ArrayList<Paper> paperList = paperDao.selectAllByUsername(username);
        if (paperList==null||paperList.size()==0){
            paperDao.insertUsername(paper);
        }else {
            //Paper paperIf = paperList.get(paperList.size()-1);
            paperDao.updateCreditAndQNum(paper);
        }*/
        Paper paperOne = paperDao.selectAllByUsernameOne(username);
        if (paperOne==null){
            paperDao.insertUsername(paper);
        }else {
            paperDao.updateCreditAndQNum(paper);
        }
        return message;
    }

    /**教师个人信息管理--左侧菜单*/
    @Override
    public String userManagerMenu(String username,HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = teacherDao.selectLoginByUsername(username);
        if (teacher!=null){
            session.setAttribute("currentTeacher", teacher);
        }
        ArrayList<Paper> paperList = paperDao.selectAllByUsername(username);
        if (paperList==null||paperList.size()==0){
            Paper paperNew = new Paper();
            paperNew.setUsername(teacher.getUsername());
            paperDao.insertUsername(paperNew);
            /*int question_num = paperNew.getQuestion_num();
            session.setAttribute("userQuestionnum",question_num);*/
            session.setAttribute("currentPaper", paperNew);
        }else {
            Paper paperIf = paperList.get(paperList.size()-1);
            /*int question_num = paperIf.getQuestion_num();
            session.setAttribute("userQuestionnum",question_num);*/
            session.setAttribute("currentPaper", paperIf);
        }
        /*Paper paperOne = paperDao.selectAllByUsernameOne(username);
        if (paperOne!=null){
            session.setAttribute("currentPaper", paperOne);
        }*/

        return null;
    }

    /**教师填写试卷信息*/
    @Override
    public String analysisMessage(Paper paper, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("currentTeacher");
        String username = teacher.getUsername();
        Paper paperOne = paperDao.selectAllByUsernameOne(username);
        if (paperOne==null){
            paperDao.insertUsername(paper);
            paperDao.updateOtherTitle(paper);
        }else {
            paperDao.updateOtherTitle(paper);
        }

        return null;
    }

    /**教师填写题目设置*/
    @Override
    public String analysisQuestion(String[] type,String[] scoreRate, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        boolean b = false;
        //获取题目数目集合。
        List<QuestionScore> questionScoreList = (List<QuestionScore>)session.getAttribute("scoreList");

        if (questionScoreList!=null){
            b = true;
        }

        //计算每道题得分率
        QuestionscoreRate questionscoreRate = new QuestionscoreRate();
        BigDecimal bd1 = null;//小数点位数限制
        float[] questionSum = questionscoreRate.sumColumn(questionScoreList,request);//每列的和
        //System.out.println(questionSum[0]+"   "+questionSum[1]+"   "+questionSum[2]+"   "+questionSum[3]);
        float[] colScoreRate = questionscoreRate.columnScoreRate(questionSum,scoreRate,request);//每列得分率
        for (int i=0;i<colScoreRate.length;i++){
            bd1 = new BigDecimal(colScoreRate[i]);
            bd1 = bd1.setScale(2,4);//保留两位小数，四舍五入
            colScoreRate[i] = bd1.floatValue();
        }
        Paper paper = (Paper)session.getAttribute("currentPaper");
        int num = paper.getQuestion_num();
        for (int i = 0;i<num;i++){
            if(i == 0){
                paper.setQuestionScoreRate1(colScoreRate[i]);
            }else if (i==1){
                paper.setQuestionScoreRate2(colScoreRate[i]);
            }else if (i==2){
                paper.setQuestionScoreRate3(colScoreRate[i]);
            }else if (i==3){
                paper.setQuestionScoreRate4(colScoreRate[i]);
            }else if (i==4){
                paper.setQuestionScoreRate5(colScoreRate[i]);
            }else if (i==5){
                paper.setQuestionScoreRate6(colScoreRate[i]);
            }else if (i==6){
                paper.setQuestionScoreRate7(colScoreRate[i]);
            }else if (i==7){
                paper.setQuestionScoreRate8(colScoreRate[i]);
            }else if (i==8){
                paper.setQuestionScoreRate9(colScoreRate[i]);
            }
        }

        //计算试题难度分布率、区分度分布率
        Jisuan jisuan = new Jisuan();
        float rate_difficulty = jisuan.rateDifficulty(questionSum,scoreRate,request);//试题难度分布率
        paper.setRate_difficulty(rate_difficulty);
        float rate_distribution = jisuan.rateDistribution(questionScoreList,scoreRate,request);//区分度分布率
        paper.setRate_distribution(rate_distribution);

        paperDao.updateQuestion(paper);
        session.setAttribute("currentPaper",paper);
        return null;
    }

    /**教师填写试卷信息--左侧菜单*/
    @Override
    public String analysisMessageMenu(String username,HttpServletRequest request) throws Exception {
        String message = null;
        HttpSession session = request.getSession();
        Teacher teacher = teacherDao.selectLoginByUsername(username);
        if (teacher!=null){
            session.setAttribute("currentTeacher", teacher);
        }
        ArrayList<Paper> paperList = paperDao.selectAllByUsername(username);

        if (paperList==null||paperList.size()==0){
            message = "请先到“个人信息”中编辑信息并保存";
        }else {
            Paper paperIf = paperList.get(paperList.size()-1);
            /*int question_num = paperIf.getQuestion_num();
            session.setAttribute("userQuestionnum",question_num);*/
            session.setAttribute("currentPaper", paperIf);
        }
        return message;
    }

    /**导入Excel中学生成绩*/
    @Override
    public boolean excelInfoImport(String name, MultipartFile file, HttpServletRequest request) {
        boolean b = false;
        //创建处理EXCEL
        ExcelUtil excelUtil = new ExcelUtil();
        //解析excel，获取题目数目集合。
        List<QuestionScore> questionScoreList = excelUtil.getExcelInfoUser(name ,file,request);

        if (questionScoreList!=null){
            b = true;
        }

        //对成绩进行计算
        HttpSession session = request.getSession();
        session.setAttribute("scoreList",questionScoreList);

       // QuestionScore questionScore = questionScoreList.get(0);
        BigDecimal bd1 = null;//小数点位数限制
        BigDecimal bd2 = null;//小数点位数限制
        Paper paper = (Paper)session.getAttribute("currentPaper");
        Jisuan jisuan = new Jisuan();
        ScoreSection scoreSection = new ScoreSection();
        float[] sum = jisuan.sumRowUser(questionScoreList,request);
        /*for (int i = 0; i < sum.length; i++) {
            System.out.println(sum[i]);
        }*/
        float high = jisuan.scoreHigh(sum);
        float low = jisuan.scoreLowest(sum);
        float rate_pass = jisuan.scoreRatePass(sum);
        BigDecimal bd3 = null;//小数点位数限制
        bd3 = new BigDecimal(rate_pass);
        bd3 = bd3.setScale(2,4);//保留两位小数，四舍五入
        rate_pass = bd3.floatValue();
        float score_average = jisuan.scoreaverage(sum);
        bd1 = new BigDecimal(score_average);
        bd1 = bd1.setScale(2,4);//保留两位小数，四舍五入
        score_average = bd1.floatValue();
        float score_variance = jisuan.scoreAverVariance(sum,score_average);
        bd2 = new BigDecimal(score_variance);
        bd2 = bd2.setScale(2,4);//保留两位小数，四舍五入
        score_variance = bd2.floatValue();
        paper.setScore_high(high);
        paper.setScore_lowest(low);
        paper.setRate_pass(rate_pass);
        paper.setScore_average(score_average);
        paper.setScore_variance(score_variance);
        /*分数段的人数和百分比*/
        int people0to9 = (int)scoreSection.score0to9(sum)[0];
        float per0to9 = scoreSection.score0to9(sum)[1];
        BigDecimal bdp0 = new BigDecimal(per0to9);
        bdp0 = bdp0.setScale(2,4);//保留两位小数，四舍五入
        per0to9 = bdp0.floatValue();
        int people10to19 = (int)scoreSection.score10to19(sum)[0];
        float per10to19 = scoreSection.score10to19(sum)[1];
        BigDecimal bdp1 = new BigDecimal(per10to19);
        bdp1 = bdp1.setScale(2,4);//保留两位小数，四舍五入
        per10to19 = bdp1.floatValue();
        int people20to29 = (int)scoreSection.score20to29(sum)[0];
        float per20to29 = scoreSection.score20to29(sum)[1];
        BigDecimal bdp2 = new BigDecimal(per20to29);
        bdp2 = bdp2.setScale(2,4);//保留两位小数，四舍五入
        per20to29 = bdp2.floatValue();
        int people30to39 = (int)scoreSection.score30to39(sum)[0];
        float per30to39 = scoreSection.score30to39(sum)[1];
        BigDecimal bdp3 = new BigDecimal(per30to39);
        bdp3 = bdp3.setScale(2,4);//保留两位小数，四舍五入
        per30to39 = bdp3.floatValue();
        int people40to49 = (int)scoreSection.score40to49(sum)[0];
        float per40to49 = scoreSection.score40to49(sum)[1];
        BigDecimal bdp4 = new BigDecimal(per40to49);
        bdp4 = bdp4.setScale(2,4);//保留两位小数，四舍五入
        per40to49 = bdp4.floatValue();
        int people50to59 = (int)scoreSection.score50to59(sum)[0];
        float per50to59 = scoreSection.score50to59(sum)[1];
        BigDecimal bdp5 = new BigDecimal(per50to59);
        bdp5 = bdp5.setScale(2,4);//保留两位小数，四舍五入
        per50to59 = bdp5.floatValue();
        int people60to69 = (int)scoreSection.score60to69(sum)[0];
        float per60to69 = scoreSection.score60to69(sum)[1];
        BigDecimal bdp6 = new BigDecimal(per60to69);
        bdp6 = bdp6.setScale(2,4);//保留两位小数，四舍五入
        per60to69 = bdp6.floatValue();
        int people70to79 = (int)scoreSection.score70to79(sum)[0];
        float per70to79 = scoreSection.score70to79(sum)[1];
        BigDecimal bdp7 = new BigDecimal(per70to79);
        bdp7 = bdp7.setScale(2,4);//保留两位小数，四舍五入
        per70to79 = bdp7.floatValue();
        int people80to89 = (int)scoreSection.score80to89(sum)[0];
        float per80to89 = scoreSection.score80to89(sum)[1];
        BigDecimal bdp8 = new BigDecimal(per80to89);
        bdp8 = bdp8.setScale(2,4);//保留两位小数，四舍五入
        per80to89 = bdp8.floatValue();
        int people90 = (int)scoreSection.score90(sum)[0];
        float per90 = scoreSection.score90(sum)[1];
        BigDecimal bdp9 = new BigDecimal(per90);
        bdp9 = bdp9.setScale(2,4);//保留两位小数，四舍五入
        per90 = bdp9.floatValue();
        paper.setPeople0to9(people0to9);
        paper.setPer0to9(per0to9);
        paper.setPeople10to19(people10to19);
        paper.setPer10to19(per10to19);
        paper.setPeople20to29(people20to29);
        paper.setPer20to29(per20to29);
        paper.setPeople30to39(people30to39);
        paper.setPer30to39(per30to39);
        paper.setPeople40to49(people40to49);
        paper.setPer40to49(per40to49);
        paper.setPeople50to59(people50to59);
        paper.setPer50to59(per50to59);
        paper.setPeople60to69(people60to69);
        paper.setPer60to69(per60to69);
        paper.setPeople70to79(people70to79);
        paper.setPer70to79(per70to79);
        paper.setPeople80to89(people80to89);
        paper.setPer80to89(per80to89);
        paper.setPeople90(people90);
        paper.setPer90(per90);
        session.setAttribute("currentPaper",paper);
        try {
            paperDao.updateJisuan(paper);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    /**教师填写三个试卷分析*/
    @Override
    public String analysisAnswer(Paper paper) throws Exception {
        paperDao.updateAnswers(paper);

        return null;
    }

    /**查询该老师历史生成的所有报告*/
    @Override
    public String paperList( Teacher teacher, HttpServletRequest request) throws Exception {
        String message = null;
        HttpSession session = request.getSession();
        String username = teacher.getUsername();
        ArrayList<Paper> paperList = paperDao.selectAllByUsername(username);
        Paper paperOne = paperDao.selectAllByUsernameOne(username);
        if (paperList==null||paperList.size()==0){
            message = "您还未生成过报告";
        }else {
            if (paperList.size()==1){
                if (paperOne!=null) {
                    message = "您还未生成过报告";
                }else {
                    /*生成了一个完整报告*/
                    session.setAttribute("paperlist", paperList);
                }
            }else{
                if (paperOne!=null) {
                    message = "您还未生成过报告";
                    /*去掉最后一个不完整的报告*/
                    int index = paperList.size()-1;
                    paperList.remove(index);
                    session.setAttribute("paperlist", paperList);
                }else {
                    /*生成了>=一个完整报告*/
                    session.setAttribute("paperlist", paperList);
                }
            }

        }

        return message;
    }

    /**删除一条分析报告*/
    @Override
    public String deleteOnePaper(int paper_num) throws Exception {
        paperDao.deleteOneByNum(paper_num);
        return null;
    }


}
