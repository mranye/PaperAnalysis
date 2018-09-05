package com.education.paper.util;

import com.education.paper.entity.Paper;
import com.education.paper.entity.QuestionScore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class QuestionscoreRate {

    /** 计算每列的和 */
    public float[] sumColumn(List<QuestionScore> questionScoreList, HttpServletRequest request){
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        //有多少个学生，即多少行
        int num = paper.getNumber_join();

        //有多少道大题，即有多少列
        int question_num = paper.getQuestion_num();
        float[] questionSum = new float[question_num];

        QuestionScore questionScore = null;
        for (int i=0;i<num;i++){
            questionScore = questionScoreList.get(i);
            for (int j=0;j<question_num;j++){
                questionSum[j] += Float.parseFloat(questionScore.getScore()[j]);
            }
        }
        return questionSum;
    }

    /** 计算每列的得分率 */
    public float[] columnScoreRate(float[] questionSum,String[] scoreRate, HttpServletRequest request){
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        //有多少个学生，即多少行
        int num = paper.getNumber_join();

        //有多少道大题，即有多少列
        int question_num = paper.getQuestion_num();
        float[] colScoreRate = new float[question_num];

        for (int i=0;i<question_num;i++){
            float scoreall = Float.parseFloat(scoreRate[i]);
            float zong = scoreall * num;
            colScoreRate[i] = questionSum[i] / zong;
        }
        return colScoreRate;
    }

}
