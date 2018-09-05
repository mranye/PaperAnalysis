package com.education.paper.controller;

import com.education.paper.entity.Knowledge;
import com.education.paper.entity.KnowledgeRate;
import com.education.paper.entity.Paper;
import com.education.paper.entity.Teacher;
import com.education.paper.service.KnowledgeService;
import com.education.paper.util.DocUtil;
import com.education.paper.util.ExcelKnowledgeUtil;
import com.education.paper.util.ExcelUtil;
import com.google.gson.Gson;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("")
public class KnowledgeController {

    @Autowired
    KnowledgeService knowledgeService;

    /*知识点管理--添加知识点*/
    @RequestMapping("/knowledgeManagerSubmit")
    public String knowledgeManagerSubmit(String knowledgeName,int topindex,String topindex2,HttpServletRequest request) {
        HttpSession session = request.getSession();
        /*String knowledgeName = request.getParameter("knowledgeName");
        int topindex = request.getParameter("topindex");*/
        System.out.println(knowledgeName+"    "+topindex+"  "+topindex2);
        int topindex2int = 0;
        if (topindex2==null){
            topindex2int = 0;
        }else{
            topindex2int = Integer.parseInt(topindex2);
        }
        Knowledge knowledge = new Knowledge();
        knowledge.setKnowledgeName(knowledgeName);
        knowledge.setTopindex(topindex);
        knowledge.setTopindex2(topindex2int);
        try {
            knowledgeService.addOneKnowledge(knowledge);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/liKnowledgeManager";
    }

    /*知识点Ajax处理*/
    @RequestMapping("/AjaxKnowledgeSelect")
    public String AjaxKnowledgeSelect(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        int TopKnowledgeNum = Integer.parseInt(request.getParameter("TopKnowledgeNum"));
        //System.out.println(TopKnowledgeNum);
        ArrayList<Knowledge> knowledgeSecondIndex = (ArrayList<Knowledge>)session.getAttribute("knowledgeSecondList");
        int secondNum = 0;
        if (knowledgeSecondIndex==null){

        }else{
            secondNum = knowledgeSecondIndex.size();
        }
        ArrayList<Knowledge> knowledgeSecondSelectList = new ArrayList<Knowledge>();
        for (int j = 0;j<secondNum;j++) {
            Knowledge knowledgeSecond = knowledgeSecondIndex.get(j);
            int secondTopindex = knowledgeSecond.getTopindex();
            int secondTopindex2 = knowledgeSecond.getTopindex2();
            if (secondTopindex2==0){
                if (secondTopindex == TopKnowledgeNum) {
                    knowledgeSecondSelectList.add(knowledgeSecond);
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(knowledgeSecondSelectList);
        //System.out.println(json);
        try {
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*点击菜单“知识点分析--知识点管理”选项*/
    @RequestMapping("/liKnowledgeManager")
    public String knowledgeManager(HttpServletRequest request) {
        try {
            knowledgeService.knowledgeManagerMenu(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/knowledgeManager";
    }

    /*知识点  基本信息设置*/
    @RequestMapping("/knowledgeMessageSubmit")
    public String knowledgeMessageSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Paper paperNew = (Paper)session.getAttribute("currentPaper");
        Teacher teacher = (Teacher) session.getAttribute("currentTeacher");
        Paper knowledgePaper = null;
        if (paperNew==null){
            knowledgePaper = new Paper();
            knowledgePaper.setUsername(teacher.getUsername());
        }else {
            knowledgePaper = paperNew;
        }
        String profession = request.getParameter("profession");
        String exam_time = request.getParameter("exam_time");
        //若用户未选择日期，则使用系统当前日期
        if (exam_time.trim().length() == 0 || exam_time.equals("")) {
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            exam_time = sf.format(date);
        }
        String study_year = request.getParameter("study_year");
        /*大题数目若未填写，则默认为5*/
        String question_num = request.getParameter("question_num");
        if (question_num.trim().length() == 0 || question_num.equals("")) {
            knowledgePaper.setQuestion_num(5);
            int questionnum = 5;
            session.setAttribute("knowledgeQuestionnum", questionnum);
        } else {
            knowledgePaper.setQuestion_num(Integer.parseInt(question_num));
            int questionnum = Integer.parseInt(question_num);
            session.setAttribute("knowledgeQuestionnum", questionnum);
        }
        /*若用户未填写学年，则默认为考试日期的年份*/
        if (study_year.trim().length() == 0 || study_year.equals("")) {
            String year = exam_time.substring(0, 4);
            knowledgePaper.setStudy_year(year);
        }else{
            knowledgePaper.setStudy_year(study_year);
        }
        String term = request.getParameter("term");
        int topindex = Integer.parseInt(request.getParameter("topindex"));//一级知识点序号


        knowledgePaper.setProfession(profession);
        knowledgePaper.setExam_time(exam_time);

        knowledgePaper.setTerm(term);
        session.setAttribute("knowledgePaper",knowledgePaper);
        session.setAttribute("topindex",topindex);

        /*获取当前知识点模块的所有知识点*/
        System.out.println(topindex);
        ArrayList<Knowledge> knowledgeAllList = (ArrayList<Knowledge>)session.getAttribute("knowledgeAllList");
        ArrayList<Knowledge> knowledgelistAll = new ArrayList<Knowledge>();
        ArrayList<Knowledge> knowledgelistFirst = new ArrayList<Knowledge>();
        ArrayList<Knowledge> knowledgelistSecond = new ArrayList<Knowledge>();
        for (int a = 0;a<knowledgeAllList.size();a++){
            Knowledge knowledge = knowledgeAllList.get(a);
            if(knowledge.getTopindex()==topindex){
                knowledgelistAll.add(knowledge);
                int index2 = knowledge.getTopindex2();
                if (index2==0){
                    knowledgelistFirst.add(knowledge);
                }else{
                    knowledgelistSecond.add(knowledge);
                }
            }
        }
        session.setAttribute("knowledgelistAll",knowledgelistAll);//该模块下所有一级二级知识点
        session.setAttribute("knowledgelistFirst",knowledgelistFirst);//该模块下所有一级知识点
        session.setAttribute("knowledgelistSecond",knowledgelistSecond);//该模块下所有二级知识点

        return "redirect:/knowledgeScoreAll";
    }

    /*知识点 设置题目总分及包含小题数目*/
    @RequestMapping("/knowledgeScoreAllSubmit")
    public String knowledgeScoreAllSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int questionnum = (int)session.getAttribute("knowledgeQuestionnum");
        int[] littleNum = new int[questionnum];//每道大题的小题数
        float[] questionSum = new float[questionnum];//每道大题的满分
        for(int i=0;i<questionnum;i++){
            int j = i+1;
            littleNum[i] = Integer.parseInt(request.getParameter("littleNum"+j));
            questionSum[i] = Float.parseFloat(request.getParameter("questionSum"+j));
            //System.out.println("littleNum"+i+"=  "+littleNum[i]);
            //System.out.println("questionSum"+i+"=  "+questionSum[i]);
        }

        session.setAttribute("littleNum",littleNum);
        session.setAttribute("questionSum",questionSum);


        return "redirect:/knowledgeQuestion";
    }

    /*点击菜单“知识点分析--知识点管理”选项*/
    @RequestMapping("/liKnowledgeMessage")
    public String knowledgeMessage(HttpServletRequest request) {
        try {
            knowledgeService.knowledgeMessageMenu(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/knowledgeMessage";
    }

    /*知识点题目绑定Ajax处理*/
    @RequestMapping("/AjaxKnowledgeQuestion")
    public String AjaxKnowledgeQuestion(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        int OneIdNum = Integer.parseInt(request.getParameter("OneIdNum"));
        //System.out.println(TopKnowledgeNum);
        ArrayList<Knowledge> knowledgelistSecond = (ArrayList<Knowledge>)session.getAttribute("knowledgelistSecond");
        int secondNum = 0;
        if (knowledgelistSecond==null){

        }else{
            secondNum = knowledgelistSecond.size();
        }
        ArrayList<Knowledge> knowledgeSecondSelectList = new ArrayList<Knowledge>();
        for (int j = 0;j<secondNum;j++) {
            Knowledge knowledgeSecond = knowledgelistSecond.get(j);
            int secondTopindex = knowledgeSecond.getTopindex();
            int secondTopindex2 = knowledgeSecond.getTopindex2();
            if (secondTopindex2==OneIdNum){
                    knowledgeSecondSelectList.add(knowledgeSecond);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(knowledgeSecondSelectList);
        //System.out.println(json);
        try {
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*知识点设置*/
    @RequestMapping("/knowledgeQuestionSubmit")
    public String knowledgeQuestionSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int[] littleNum = (int[])session.getAttribute("littleNum");//每道大题的小题数
        int num = (int)session.getAttribute("knowledgeQuestionnum");//大题数目
        int lie = 0;//所有小题个数
        for (int i=0;i<littleNum.length;i++){
            lie+=littleNum[i];
        }
        session.setAttribute("alllie",lie);
        int m = 0;
        int[] numKnowledge1 = new int[lie];//每道小题对应的一级知识点的序号
        int[] numKnowledge2 = new int[lie];//每道小题对应的二级知识点的序号
        int[] numKnowledge = new int[lie];//最终每道小题对应知识点序号
        for (int i=0;i<num;i++){
            for (int j=0;j<littleNum[i];j++){
                numKnowledge1[m] = Integer.parseInt(request.getParameter("numOneKnowledge"+i+"and"+j));
                String numKnowledge2String = request.getParameter("numKnowledge"+i+"and"+j);
                if (numKnowledge2String==null||numKnowledge2String.equals("")){
                    numKnowledge2[m] = 0;
                }else {
                    numKnowledge2[m] = Integer.parseInt(numKnowledge2String);
                }

                if (numKnowledge2[m]==0){
                    numKnowledge[m] = numKnowledge1[m];

                }else{
                    numKnowledge[m] = numKnowledge2[m];

                }
                System.out.println("numKnowledge1  "+numKnowledge1[m]);
                System.out.println("numKnowledge2  "+numKnowledge2[m]);
                System.out.println("numKnowledge  "+numKnowledge[m]);
                m++;
            }
        }
        session.setAttribute("numKnowledge",numKnowledge);
        return "redirect:/liKnowledgeInput";
    }

    /*知识点 下载Excel模板*/
    @RequestMapping(value = "/knowledgeDownloadMuban")
    public String knowledgeDownloadMuban( HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        ExcelKnowledgeUtil excelUtil = new ExcelKnowledgeUtil();
        SXSSFWorkbook workbook = excelUtil.exportExcel(request);
        String fileName = "学生成绩列表详细版.xlsx";
        // 设置response参数，可以打开下载页面
        try {
            response.reset();
            String header = request.getHeader("User-Agent");
            //response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            if (header.toLowerCase().indexOf("msie")>0){
                response.setHeader("Content-Disposition", "attachment;filename="+ java.net.URLEncoder.encode(fileName,"utf-8"));
            }else{
                response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            }
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*知识点 分析Excel数据、计算并跳转*/
    @RequestMapping("/knowledgeInputSubmit")
    public String knowledgeInputSubmit(@RequestParam(value="filename") MultipartFile file, HttpServletRequest request) {
        /*获取Excel文件名字*/
        //判断文件是否为空
        if(file==null) return "redirect:/liKnowledgeInput";
        //获取文件名
        String name=file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name==null || ("").equals(name) && size==0) return"redirect:/youke/youkeUpload";
        //导入Excel数据。参数：文件名，文件。
        boolean b = knowledgeService.excelInfoImport(name,file,request);
        if(b){
            String Msg ="导入EXCEL成功！";
            request.getSession().setAttribute("msg",Msg);
        }else{
            String Msg ="导入EXCEL失败！";
            request.getSession().setAttribute("msg",Msg);
        }

        return "redirect:/liKnowledgeAnswer";
    }

    /*点击菜单“知识点分析--成绩录入”选项*/
    @RequestMapping("/liKnowledgeInput")
    public String knowledgeInput(HttpServletRequest request) {

        return "redirect:/knowledgeInput";
    }

    /*填写知识点试卷分析*/
    @RequestMapping("/knowledgeAnswerSubmit")
    public String knowledgeAnswerSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String answer1 = request.getParameter("answer1");
        String answer2 = request.getParameter("answer2");
        String answer3 = request.getParameter("answer3");
        Paper paper = (Paper)session.getAttribute("knowledgePaper");
        paper.setAnswer1(answer1);
        paper.setAnswer2(answer2);
        paper.setAnswer3(answer3);
        session.setAttribute("knowledgePaper", paper);

        return "redirect:/liKnowledgePaper";
    }

    /*点击菜单“知识点分析--填写分析”选项*/
    @RequestMapping("/liKnowledgeAnswer")
    public String knowledgeAnswer(HttpServletRequest request) {

        return "redirect:/knowledgeAnswer";
    }

    static String MODELPATH = "/WEB-INF/uploadFiles/file/";
    /*下载知识点试卷分析*/
    @RequestMapping("/knowledgePaperDownload")
    public String knowledgePaperDownload(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        try {
            //引入导出word的工具类
            DocUtil docUtil = new DocUtil();
            //建立map存储所要导出到word的各种数据和图像，不能使用自己项目封装的类型，例如PageData
            Map<String, Object> dataMap = new HashMap<String, Object>();

            // 获取数据
            Paper paper = (Paper)session.getAttribute("knowledgePaper");
            Teacher teacher = (Teacher)session.getAttribute("currentTeacher");
            int knowledgenumjoin = (int)session.getAttribute("knowledgenumjoin");
            int userQuestionnum = (int)session.getAttribute("knowledgeQuestionnum");
            //将以上处理的数据都存入dataMap 中
            dataMap.put("study_year",paper.getStudy_year());
            dataMap.put("term",paper.getTerm());
            dataMap.put("username",teacher.getTeacher_name());
            dataMap.put("course",teacher.getCourse());
            dataMap.put("college",teacher.getCollege());
            dataMap.put("profession",paper.getProfession());
            dataMap.put("year_term",paper.getStudy_year()+"学年"+paper.getTerm()+"学期");
            dataMap.put("knowledgenumjoin",knowledgenumjoin);
            dataMap.put("question_num",userQuestionnum);
            dataMap.put("exam_time",paper.getExam_time());
            dataMap.put("answer1",paper.getAnswer1());
            dataMap.put("answer2",paper.getAnswer2());
            dataMap.put("answer3",paper.getAnswer3());

            float[] scorerate = (float[])session.getAttribute("knowledgeScoreRate");
            String[] knowledgeName = (String[])session.getAttribute("KnowledgeName");
            List<KnowledgeRate> listknowledge = new ArrayList<KnowledgeRate>();
            for (int i=0;i<knowledgeName.length;i++) {
                if (knowledgeName[i] != null) {
                    String rate = scorerate[i]+"";
                    KnowledgeRate knowledgeRate = new KnowledgeRate(knowledgeName[i],rate);
                    listknowledge.add(knowledgeRate);
                }
            }
            dataMap.put("listknowledge",listknowledge);

            //进行word文件的处理
            request.setCharacterEncoding("utf-8");
            File file = null;
            InputStream fin = null;
            OutputStream out = null;
            String filename = "知识点试卷分析报告.doc";

            //dataMap是上面处理完的数据，MODELPATH是模板文件的存储路径，"KnowledgeWordMuban.xml"是相应的模板文件
            file = docUtil.createDoc(dataMap, MODELPATH, "KnowledgeWordMuban.xml", request);
            fin = new FileInputStream(file);
            response.setContentLength((int) file.length());//需要传递这个长度，不然下载文件后，打开提示内容有问题，如docx等
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "iso8859-1"));
            out = response.getOutputStream();
            byte[] buffer = new byte[1024]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
            if (fin != null)
                fin.close();
            if (out != null)
                out.close();
            if (file != null)
                file.delete(); // 删除临时文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/liKnowledgePaper";
    }

    /*下载知识点分析报告界面*/
    @RequestMapping("/liKnowledgePaper")
    public String knowledgePaper(HttpServletRequest request) {

        return "redirect:/knowledgePaper";
    }

    /*跳转到知识点管理界面*/
    @RequestMapping("/knowledgeManager")
    public String gotoKnowledgeManager() {
        return "KnowledgeManager";
    }

    /*跳转到知识点中，信息设置第一个界面*/
    @RequestMapping("/knowledgeMessage")
    public String gotoKnowledgeMessage() {
        return "KnowledgeMessage";
    }

    /*跳转到知识点中，信息设置第二个界面*/
    @RequestMapping("/knowledgeScoreAll")
    public String gotoKnowledgeScoreAll() {
        return "KnowledgeScoreAll";
    }

    /*跳转到给题目设置知识点界面*/
    @RequestMapping("/knowledgeQuestion")
    public String gotoKnowledgeQuestion() {
        return "KnowledgeQuestion";
    }

    /*跳转到录入成绩界面*/
    @RequestMapping("/knowledgeInput")
    public String gotoKnowledgeInput() {
        return "KnowledgeInput";
    }

    /*跳转到填写分析界面*/
    @RequestMapping("/knowledgeAnswer")
    public String gotoKnowledgeAnswer() {
        return "KnowledgeAnswer";
    }

    /*跳转到知识点分析报告下载界面*/
    @RequestMapping("/knowledgePaper")
    public String gotoKnowledgePaper() {
        return "KnowledgePaper";
    }


}
