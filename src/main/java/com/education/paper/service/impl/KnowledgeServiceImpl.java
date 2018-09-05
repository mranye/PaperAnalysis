package com.education.paper.service.impl;

import com.education.paper.dao.KnowledgeDao;
import com.education.paper.entity.Knowledge;
import com.education.paper.entity.KnowledgeQuestionScore;
import com.education.paper.entity.Paper;
import com.education.paper.entity.QuestionScore;
import com.education.paper.service.KnowledgeService;
import com.education.paper.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Autowired
    KnowledgeDao knowledgeDao;

    /** 点击左侧菜单“知识点管理”后查询所有知识点 */
    @Override
    public String knowledgeManagerMenu(HttpServletRequest request) throws Exception {

        HttpSession session = request.getSession();
        ArrayList<Knowledge> knowledgeArrayList = new ArrayList<Knowledge>();
        ArrayList<Knowledge> knowledgeOneIndex = new ArrayList<Knowledge>();//一级知识点
        ArrayList<Knowledge> knowledgeSecondIndex = new ArrayList<Knowledge>();//一级知识点
        knowledgeArrayList = knowledgeDao.selectAllKnowledge();
        for (int i=0;i<knowledgeArrayList.size();i++){
            Knowledge knowledge = knowledgeArrayList.get(i);
            if(knowledge.getTopindex()==0){
                knowledgeOneIndex.add(knowledge);
                //System.out.println("一级知识点  "+knowledge.getKnowledge_num()+"  "+knowledge.getKnowledgeName()+"  "+knowledge.getTopindex());
            }else{
                knowledgeSecondIndex.add(knowledge);
                //System.out.println("二级知识点  "+knowledge.getKnowledge_num()+"  "+knowledge.getKnowledgeName()+"  "+knowledge.getTopindex());
            }
        }
        session.setAttribute("knowledgeFirstList",knowledgeOneIndex);
        session.setAttribute("knowledgeSecondList",knowledgeSecondIndex);

        return null;
    }


    /** 添加一个知识点 */
    @Override
    public String addOneKnowledge(Knowledge knowledge) throws Exception {

        knowledgeDao.insertOneKnowledge(knowledge);

        return null;
    }

    /** 点击左侧菜单“信息设置”后查询一级知识点 */
    @Override
    public String knowledgeMessageMenu(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        ArrayList<Knowledge> knowledgeArrayList = new ArrayList<Knowledge>();
        ArrayList<Knowledge> knowledgeOneIndex = new ArrayList<Knowledge>();//一级知识点
        knowledgeArrayList = knowledgeDao.selectAllKnowledge();
        for (int i=0;i<knowledgeArrayList.size();i++){
            Knowledge knowledge = knowledgeArrayList.get(i);
            if(knowledge.getTopindex()==0){
                knowledgeOneIndex.add(knowledge);
                //System.out.println("一级知识点  "+knowledge.getKnowledge_num()+"  "+knowledge.getKnowledgeName()+"  "+knowledge.getTopindex());
            }
        }
        session.setAttribute("knowledgeFirstList",knowledgeOneIndex);
        session.setAttribute("knowledgeAllList",knowledgeArrayList);
        return null;
    }

    /** 导入Excel中学生成绩 */
    @Override
    public boolean excelInfoImport(String name, MultipartFile file, HttpServletRequest request) {
        boolean b = false;
        //创建处理EXCEL
        ExcelKnowledgeUtil excelUtil = new ExcelKnowledgeUtil();
        //解析excel，获取题目数目集合。
        List<QuestionScore> questionScoreList = excelUtil.getExcelInfo(name ,file,request);

        if (questionScoreList!=null){
            b = true;
        }

        //对成绩进行计算
        HttpSession session = request.getSession();
        session.setAttribute("knowledgescoreList",questionScoreList);

        KnowledgeScoreRate knowledgeScoreRate = new KnowledgeScoreRate();
        float[] questionlieSum = knowledgeScoreRate.sumColumn(questionScoreList,request);//Excel中每列的和
        /*绑定每道题成绩总和及对应知识点序号*/
        ArrayList<KnowledgeQuestionScore> knowledgeQuestionScoreList = knowledgeScoreRate.hebing(questionlieSum,request);
        float[] scorerate = knowledgeScoreRate.knowledgeRate(knowledgeQuestionScoreList,request);
        /*for (int i=0;i<scorerate.length;i++){
            System.out.println("得分率"+i+"   "+scorerate[i]);
        }*/
        session.setAttribute("knowledgeScoreRate",scorerate);


        return b;
    }
}
