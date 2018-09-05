package com.education.paper.util;


import com.education.paper.entity.Knowledge;
import com.education.paper.entity.KnowledgeQuestionScore;
import com.education.paper.entity.QuestionScore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 计算知识点得分率 */
public class KnowledgeScoreRate {

    /**知识点 计算每列的和 */
    public float[] sumColumn(List<QuestionScore> questionScoreList, HttpServletRequest request){
        HttpSession session = request.getSession();

        //有多少个学生，即多少行
        int numjoin = (int)session.getAttribute("knowledgenumjoin");//实考学生人数，即行数

        int lie = (int)session.getAttribute("alllie");//列数，即所有小题个数
        float[] questionlieSum = new float[lie];

        QuestionScore questionScore = null;
        for (int i=0;i<numjoin;i++){
            questionScore = questionScoreList.get(i);
            for (int j=0;j<lie;j++){
                questionlieSum[j] += Float.parseFloat(questionScore.getScore()[j]);
            }
        }
        return questionlieSum;
    }

    /**知识点 绑定每道题成绩总和和对应知识点序号*/
    public ArrayList<KnowledgeQuestionScore> hebing(float[] questionlieSum, HttpServletRequest request){
        HttpSession session = request.getSession();
        int[] littleNum = (int[])session.getAttribute("littleNum");//每道大题的小题数
        int num = (int)session.getAttribute("knowledgeQuestionnum");//大题数目
        int lie = (int)session.getAttribute("alllie");//列数，即所有小题个数
        int[] numKnowledge = (int[])session.getAttribute("numKnowledge");//所有题目对应的知识点序号
        int numjoin = (int)session.getAttribute("knowledgenumjoin");//实考学生人数，即行数
        float[] questionSum = (float[])session.getAttribute("questionSum");//每道大题的满分

        float[] scorefull = new float[lie];//某列满分，即这道题总分*实考学生人数
        int n = 0;
        for (int i=0;i<num;i++){
            for (int j=0;j<littleNum[i];j++){
                float scoreper = questionSum[i]/littleNum[i];
                scorefull[n] = scoreper*numjoin;
                n++;
            }
        }
        ArrayList<KnowledgeQuestionScore> knowledgeQuestionScoreList = new ArrayList<KnowledgeQuestionScore>();
        for (int i=0;i<lie;i++){
            KnowledgeQuestionScore knowledgeQuestionScore = new KnowledgeQuestionScore(questionlieSum[i],numKnowledge[i],scorefull[i]);
            knowledgeQuestionScoreList.add(knowledgeQuestionScore);
        }
        return knowledgeQuestionScoreList;
    }

    /**知识点 计算每个知识点的得分率*/
    public float[] knowledgeRate(ArrayList<KnowledgeQuestionScore> knowledgeQuestionScoreList, HttpServletRequest request){
        HttpSession session = request.getSession();
        int[] littleNum = (int[])session.getAttribute("littleNum");//每道大题的小题数
        int lie = (int)session.getAttribute("alllie");//列数，即所有小题个数
        int[] numKnowledge = (int[])session.getAttribute("numKnowledge");//所有题目对应的知识点序号
        int numjoin = (int)session.getAttribute("knowledgenumjoin");//实考学生人数，即行数
        float[] questionSum = (float[])session.getAttribute("questionSum");//每道大题的满分

        ArrayList<Knowledge> knowledgelist = (ArrayList<Knowledge>)session.getAttribute("knowledgelistAll");
        int knowledgelistNum = knowledgelist.size();//知识点个数
        int[] knowledgeNumber = new int[knowledgelistNum];//该模块所有知识点的序号
        float[] knowledgescore = new float[knowledgelistNum];//知识点序号对应的所有列的和
        float[] knowledgescorefull = new float[knowledgelistNum];//知识点序号对应的所有列的满分和
        String[] knowledgeName = new String[knowledgelistNum];//知识点名字
        for (int i=0;i<knowledgelistNum;i++){
            Knowledge knowledge = knowledgelist.get(i);
            knowledgeNumber[i] = knowledge.getKnowledge_num();
            knowledgescore[i] = 0;
            knowledgescorefull[i] = 0;
        }

        for (int i=0;i<knowledgeQuestionScoreList.size();i++){
            KnowledgeQuestionScore knowledgeQuestionScore = knowledgeQuestionScoreList.get(i);
            int knowledgeindex = knowledgeQuestionScore.getKnowledgeindex();
            for (int j=0;j<knowledgelistNum;j++){
                if (knowledgeindex==knowledgeNumber[j]){//判断知识点序号是哪个
                    knowledgescore[j]+=knowledgeQuestionScore.getScore();
                    knowledgescorefull[j]+=knowledgeQuestionScore.getScorefull();
                    Knowledge knowledge = knowledgelist.get(j);
                    knowledgeName[j] = knowledge.getKnowledgeName();
                }
            }
            session.setAttribute("KnowledgeName",knowledgeName);
        }
        float[] scorerate = new float[knowledgelistNum];
        for (int i=0;i<knowledgelistNum;i++){
            if (knowledgeName[i]!=null){
                BigDecimal bd1 = null;//小数点位数限制
                //System.out.println("knowledgescore"+i+"  "+knowledgescore[i]);
                //System.out.println("knowledgescorefull"+i+"  "+knowledgescorefull[i]);
                scorerate[i] = knowledgescore[i]/knowledgescorefull[i];
                bd1 = new BigDecimal(scorerate[i]);
                bd1 = bd1.setScale(2,4);//保留两位小数，四舍五入
                scorerate[i] = bd1.floatValue();
            }
        }

        return scorerate;
    }


}
