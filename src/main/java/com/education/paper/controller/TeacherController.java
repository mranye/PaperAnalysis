package com.education.paper.controller;

import com.education.paper.entity.Paper;
import com.education.paper.entity.Teacher;
import com.education.paper.service.TeacherService;
import com.education.paper.util.DocUtil;
import com.education.paper.util.ExcelUtil;
import com.education.paper.util.ImageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    /*登录*/
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request) {
        try {
            Teacher teacher = teacherService.login(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("currentTeacher", teacher);
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMessage", e.getMessage());
            //request.setAttribute("message",e.getMessage());
            //e.printStackTrace();
            return "forward:WEB-INF/jsp/SignUpOrIn.jsp";
        }
        return "redirect:/allOptions";
    }

    /*注册*/
    @RequestMapping("/signup")
    public String signup(String username, String password, String again_password, HttpServletRequest request) {

        if (again_password.trim().equals(password.trim())) {
            try {
                Teacher teacherIn = new Teacher();
                teacherIn.setUsername(username);
                teacherIn.setPassword(password);
                String message = teacherService.signup(username, password);
                HttpSession session = request.getSession();
                //session.setAttribute("signupTeacher",teacherIn);
                session.setAttribute("currentTeacher", teacherIn);
                session.setAttribute("signupMessage", message);
            } catch (Exception e) {
                //request.setAttribute("message",e.getMessage());
                e.printStackTrace();
                return "forward:WEB-INF/jsp/SignUpOrIn.jsp";
            }
        } else {
            String message = "确认密码与密码不一致";
            HttpSession session = request.getSession();
            session.setAttribute("signupMessage", message);
            return "forward:WEB-INF/jsp/SignUpOrIn.jsp";
        }
        return "redirect:/allOptions";
    }

    /*忘记密码*/
    @RequestMapping("/updatePassword")
    public String updatePassword(String username, String password, String again_password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (again_password.trim().equals(password.trim())) {
            try {
                String message = teacherService.forgetPassword(username, password,request);
                session.setAttribute("forgetMessage", message);
                if (message!=null){
                    return "forward:WEB-INF/jsp/ForgetPassword.jsp";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "forward:WEB-INF/jsp/ForgetPassword.jsp";
            }
        } else {
            String message = "确认密码与密码不一致";
            session.setAttribute("forgetMessage", message);
            return "forward:WEB-INF/jsp/ForgetPassword.jsp";
        }
        return "redirect:/signUpOrIn";
    }

    /*教师信息设置--提交*/
    @RequestMapping(value = "/userMessageSubmit")
    public String userMessage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("currentTeacher");
        Paper paper = (Paper) session.getAttribute("currentPaper");
        String username = teacher.getUsername();
        paper.setUsername(username);
        String teacher_name = request.getParameter("teacher_name");
        /*if (teacher_name!=null||!teacher_name.equals("")){
            teacher.setTeacher_name(teacher_name);
        }*/
        if (!"".equals(teacher_name) && teacher_name != null) {
            teacher.setTeacher_name(teacher_name);
        }
        String college = request.getParameter("college");
        if (!"".equals(college) && college != null) {
            teacher.setCollege(college);
        }
        String course = request.getParameter("course");
        if (!"".equals(course) && course != null) {
            teacher.setCourse(course);
        }
        String credit = request.getParameter("credit");
        if (!"".equals(credit) && credit != null) {
            paper.setCredit(credit);
        }

        session.setAttribute("currentTeacher", teacher);
        session.setAttribute("currentPaper", paper);

        try {
            teacherService.userManager(teacher, paper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/userManagerMessage";
    }

    /*教师试卷信息设置--提交*/
    @RequestMapping(value = "/analysisMessageSubmit")
    public String analysisMessageSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Paper paper = (Paper) session.getAttribute("currentPaper");
        String profession = request.getParameter("profession");
        paper.setProfession(profession);
        String term = request.getParameter("term");
        paper.setTerm(term);
        /*大题数目若未填写，则默认为5*/
        String question_num = request.getParameter("question_num");
        if (question_num.trim().length() == 0 || question_num.equals("")) {
            paper.setQuestion_num(5);
            int num = 5;
            session.setAttribute("userQuestionnum", num);
        } else {
            paper.setQuestion_num(Integer.parseInt(question_num));
            session.setAttribute("userQuestionnum", Integer.parseInt(question_num));
        }
        String exam_time = request.getParameter("exam_time");
        //若用户未选择日期，则使用系统当前日期
        if (exam_time.trim().length() == 0 || exam_time.equals("")) {
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            exam_time = sf.format(date);
            paper.setExam_time(exam_time);
        } else {
            paper.setExam_time(exam_time);
        }
        /*若用户未填写学年，则默认为考试日期的年份*/
        String study_year = request.getParameter("study_year");
        if (study_year.trim().length() == 0 || study_year.equals("")) {
            String year = exam_time.substring(0, 4);
            paper.setStudy_year(year);
        } else {
            paper.setStudy_year(study_year);
        }
        session.setAttribute("currentPaper", paper);
        try {
            teacherService.analysisMessage(paper, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*跳转到导入Excel界面*/
        return "redirect:/liAnalysisInput";

    }


    /*点击菜单“个人信息”选项*/
    @RequestMapping("/userManagerMessage")
    public String userManager(HttpServletRequest request) {
        /*查询所有个人设置里的信息*/
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("currentTeacher");
        String username = teacher.getUsername();
        try {
            teacherService.userManagerMenu(username, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/userManager";
    }

    /*教师题目类型满分分数设置--提交*/
    @RequestMapping(value = "/analysisQuestionSubmit")
    public String analysisQuestionSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Paper paper = (Paper) session.getAttribute("currentPaper");
        int num = 0;
        num = paper.getQuestion_num();
            if (num==0){
            num = 5;
        }
        String[] type = new String[num];
        String[] scoreRate = new String[num];
        for (int i = 0;i<num;i++){
            type[i] = request.getParameter("type"+i);
            scoreRate[i] = request.getParameter("scoreRate"+i);
        }
        for (int i = 0;i<num;i++){
            if(i == 0){
                paper.setQuestionType1(type[i]);
                //paper.setQuestionScoreRate1(scoreRate[i]);
            }else if (i==1){
                paper.setQuestionType2(type[i]);
                //paper.setQuestionScoreRate2(scoreRate[i]);
            }else if (i==2){
                paper.setQuestionType3(type[i]);
                //paper.setQuestionScoreRate3(scoreRate[i]);
            }else if (i==3){
                paper.setQuestionType4(type[i]);
                //paper.setQuestionScoreRate4(scoreRate[i]);
            }else if (i==4){
                paper.setQuestionType5(type[i]);
                //paper.setQuestionScoreRate5(scoreRate[i]);
            }else if (i==5){
                paper.setQuestionType6(type[i]);
                //paper.setQuestionScoreRate6(scoreRate[i]);
            }else if (i==6){
                paper.setQuestionType7(type[i]);
                //paper.setQuestionScoreRate7(scoreRate[i]);
            }else if (i==7){
                paper.setQuestionType8(type[i]);
                //paper.setQuestionScoreRate8(scoreRate[i]);
            }else if (i==8){
                paper.setQuestionType9(type[i]);
                //paper.setQuestionScoreRate9(scoreRate[i]);
            }
        }

        session.setAttribute("currentPaper", paper);
        try {
            teacherService.analysisQuestion(type,scoreRate,request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*跳转到教师填写试卷分析界面*/
        return "redirect:/liAnalysisAnswer";
    }

    /*点击菜单“试卷分析--题目设置”选项*/
    @RequestMapping("/liAnalysisQuestion")
    public String analysisQuestion(HttpServletRequest request) {

        return "redirect:/analysisQuestion";
    }

    /*点击菜单“试卷分析--信息设置”选项*/
    @RequestMapping("/liAnalysisMessage")
    public String analysisMessage(HttpServletRequest request) {
        String message = null ;
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("currentTeacher");
        String username = teacher.getUsername();
        try {
            message = teacherService.analysisMessageMenu(username,request);
            session.setAttribute("liMessageToast", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/analysisMessage";
    }

    /*点击菜单“试卷分析--数据录入”选项*/
    @RequestMapping("/liAnalysisInput")
    public String analysisInput(HttpServletRequest request) {

        return "redirect:/analysisInput";
    }

    /*教师试卷Exccel数据录入--下载模板*/
    @RequestMapping(value = "/analysisInputDownload")
    public String analysisInputDownload(HttpServletRequest request, HttpServletResponse response) {
        ExcelUtil excelUtil = new ExcelUtil();
        ServletOutputStream out = null;
        SXSSFWorkbook workbook = excelUtil.exportExcelUser(request);
        String fileName = "学生成绩列表.xlsx";
        // 设置response参数，可以打开下载页面
        try {
            response.reset();
            String header = request.getHeader("User-Agent");
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

    /*教师试卷Exccel数据录入--提交*/
    @RequestMapping(value = "/analysisInputSubmit")
    public String analysisInputSubmit(@RequestParam(value="filename") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Paper paper = (Paper) session.getAttribute("currentPaper");
        /*获取Excel文件名字*/
        //判断文件是否为空
        if(file==null) return "redirect:/liAnalysisInput";
        //获取文件名
        String name=file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(name==null || ("").equals(name) && size==0) return"redirect:/liAnalysisInput";
        //导入Excel数据。参数：文件名，文件。
        boolean b = teacherService.excelInfoImport(name,file,request);
        if(b){
            String Msg ="导入EXCEL成功！";
            request.getSession().setAttribute("Usermsg",Msg);
        }else{
            String Msg ="导入EXCEL失败！";
            request.getSession().setAttribute("Usermsg",Msg);
        }

        /*跳转到题目设置界面*/
        return "redirect:/liAnalysisQuestion";

    }

    /*点击菜单“试卷分析--填写分析”选项*/
    @RequestMapping("/liAnalysisAnswer")
    public String analysisAnswer() {

        return "redirect:/analysisAnswer";
    }

    /*教师填写三个试卷分析--提交*/
    @RequestMapping(value = "/analysisAnswerSubmit")
    public String analysisAnswerSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Paper paper = (Paper) session.getAttribute("currentPaper");
        String answer1 = request.getParameter("answer1");
        String answer2 = request.getParameter("answer2");
        String answer3 = request.getParameter("answer3");
        paper.setAnswer1(answer1);
        paper.setAnswer2(answer2);
        paper.setAnswer3(answer3);
        //报告生成日期为系统当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String analysis_time = sf.format(date);
        paper.setAnalysis_time(analysis_time);
        session.setAttribute("currentPaper", paper);
        try {
            teacherService.analysisAnswer(paper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*跳转到教师下载分析报告界面*/
        return "redirect:/liAnalysisPaper";
    }

    /*登录后生成、下载报告*/
    @RequestMapping("/liAnalysisPaper")
    public String analysisPaper() {

        return "redirect:/analysisPaper";
    }


    static String MODELPATH = "/WEB-INF/uploadFiles/file/";
    /*登录后下载试卷分析*/
    @RequestMapping("/analysisPaperDownload")
    public String analysisPaperDownload(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            //引入导出word的工具类
            DocUtil docUtil = new DocUtil();
            //引入处理图片的工具类，包含将base64编码解析为图片并保存本地，获取图片本地路径
            ImageUtil imageUtil = new ImageUtil();
            //建立map存储所要导出到word的各种数据和图像，不能使用自己项目封装的类型，例如PageData
            Map<String, Object> dataMap = new HashMap<String, Object>();

            // 获取数据
            Paper paper = (Paper)session.getAttribute("currentPaper");
            Teacher teacher = (Teacher)session.getAttribute("currentTeacher");

            //这一步，进行图片的处理，获取前台传过来的图片base64编码，在利用工具类解析图片保存到本地，然后利用工具类获取图片本地地址
            String barBase64Info = request.getParameter("barBase64Info");
            //System.out.println(barBase64Info);
            String path = "D:";
            String image1 = ImageUtil.savePictoServer(barBase64Info, path);
            image1  = imageUtil.getImageStr(image1);

            //将以上处理的数据都存入dataMap 中
            dataMap.put("study_year",paper.getStudy_year());
            dataMap.put("term",paper.getTerm());
            dataMap.put("username",teacher.getTeacher_name());
            dataMap.put("course",teacher.getCourse());
            dataMap.put("college",teacher.getCollege());
            dataMap.put("profession",paper.getProfession());
            dataMap.put("credit",paper.getCredit());
            dataMap.put("question_num",paper.getQuestion_num());
            dataMap.put("year_term",paper.getStudy_year()+"学年"+paper.getTerm()+"学期");
            dataMap.put("exam_time",paper.getExam_time());
            dataMap.put("number_join",paper.getNumber_join());
            dataMap.put("score_high",paper.getScore_high());
            dataMap.put("score_lowest",paper.getScore_lowest());
            dataMap.put("rate_pass",paper.getRate_pass());
            dataMap.put("score_average",paper.getScore_average());
            dataMap.put("score_variance",paper.getScore_variance());
            dataMap.put("rate_difficulty",paper.getRate_difficulty());
            dataMap.put("rate_distribution",paper.getRate_distribution());
            dataMap.put("people0to9",paper.getPeople0to9());
            dataMap.put("people10to19",paper.getPeople10to19());
            dataMap.put("people20to29",paper.getPeople20to29());
            dataMap.put("people30to39",paper.getPeople30to39());
            dataMap.put("people40to49",paper.getPeople40to49());
            dataMap.put("people50to59",paper.getPeople50to59());
            dataMap.put("people60to69",paper.getPeople60to69());
            dataMap.put("people70to79",paper.getPeople70to79());
            dataMap.put("people80to89",paper.getPeople80to89());
            dataMap.put("people90",paper.getPeople90());
            dataMap.put("per0to9",paper.getPer0to9());
            dataMap.put("per10to19",paper.getPer10to19());
            dataMap.put("per20to29",paper.getPer20to29());
            dataMap.put("per30to39",paper.getPer30to39());
            dataMap.put("per40to49",paper.getPer40to49());
            dataMap.put("per50to59",paper.getPer50to59());
            dataMap.put("per60to69",paper.getPer60to69());
            dataMap.put("per70to79",paper.getPer70to79());
            dataMap.put("per80to89",paper.getPer80to89());
            dataMap.put("per90",paper.getPer90());
            dataMap.put("answer1",paper.getAnswer1());
            dataMap.put("answer2",paper.getAnswer2());
            dataMap.put("answer3",paper.getAnswer3());
            dataMap.put("image",image1);

            List<String> listType = new ArrayList<String>();
            List<String> listRate = new ArrayList<String>();
            for (int i=0;i<paper.getQuestion_num();i++){
                if(i == 0){
                    listType.add(paper.getQuestionType1());
                    String rate = paper.getQuestionScoreRate1()+"";
                    listRate.add(rate);
                }else if (i==1){
                    listType.add(paper.getQuestionType2());
                    String rate = paper.getQuestionScoreRate2()+"";
                    listRate.add(rate);
                }else if (i==2){
                    listType.add(paper.getQuestionType3());
                    String rate = paper.getQuestionScoreRate3()+"";
                    listRate.add(rate);
                }else if (i==3){
                    listType.add(paper.getQuestionType4());
                    String rate = paper.getQuestionScoreRate4()+"";
                    listRate.add(rate);
                }else if (i==4){
                    listType.add(paper.getQuestionType5());
                    String rate = paper.getQuestionScoreRate5()+"";
                    listRate.add(rate);
                }else if (i==5){
                    listType.add(paper.getQuestionType6());
                    String rate = paper.getQuestionScoreRate6()+"";
                    listRate.add(rate);
                }else if (i==6){
                    listType.add(paper.getQuestionType7());
                    String rate = paper.getQuestionScoreRate7()+"";
                    listRate.add(rate);
                }else if (i==7){
                    listType.add(paper.getQuestionType8());
                    String rate = paper.getQuestionScoreRate8()+"";
                    listRate.add(rate);
                }else if (i==8){
                    listType.add(paper.getQuestionType9());
                    String rate = paper.getQuestionScoreRate9()+"";
                    listRate.add(rate);
                }
            }
            dataMap.put("listType",listType);
            dataMap.put("listRate",listRate);

            //进行word文件的处理
            request.setCharacterEncoding("utf-8");
            File file = null;
            InputStream fin = null;
            OutputStream out = null;
            String filename = "试卷分析报告.doc";
            //dataMap是上面处理完的数据，MODELPATH是模板文件的存储路径，"UserWordMuban.xml"是相应的模板文件
            file = docUtil.createDoc(dataMap, MODELPATH, "UserWordMuban.xml", request);
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/analysisPaper";
    }

    /*点击菜单“报告管理”选项*/
    @RequestMapping("/liPaperManager")
    public String paperManager(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("currentTeacher");
        /*根据username查询所有生成过的分析报告*/
        try {
            teacherService.paperList(teacher,request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/paperManager";
    }

    /*“报告管理”中-点击报告名字查看报告具体内容*/
    @RequestMapping("/paperlistShow")
    public String paperlistShow(int id,HttpServletRequest request) {
        HttpSession session = request.getSession();
        ArrayList<Paper> paperList = (ArrayList<Paper>)session.getAttribute("paperlist");
        int i = id-1;
        Paper paper = paperList.get(i);
        /*System.out.println(paper.getPaper_num());*/
        session.setAttribute("showPaper",paper);
        return "redirect:/paperManagerShow";
    }

    /*“报告管理”中--删除某份报告*/
    @RequestMapping("/paperlistDelete")
    public String paperlistDelete(int id,HttpServletRequest request) {
        HttpSession session = request.getSession();
        ArrayList<Paper> paperList = (ArrayList<Paper>)session.getAttribute("paperlist");
        int i = id-1;
        //session.setAttribute("paperlistid",id);
        Paper paper = paperList.get(i);
        int papaer_num = paper.getPaper_num();
        try {
            teacherService.deleteOnePaper(papaer_num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/liPaperManager";
    }

    /*“报告管理”中--下载某份报告*/
    @RequestMapping("/paperlistShowDownload")
    public String paperlistShowDownload(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            //引入导出word的工具类
            DocUtil docUtil = new DocUtil();
            //引入处理图片的工具类，包含将base64编码解析为图片并保存本地，获取图片本地路径
            ImageUtil imageUtil = new ImageUtil();
            //建立map存储所要导出到word的各种数据和图像，不能使用自己项目封装的类型，例如PageData
            Map<String, Object> dataMap = new HashMap<String, Object>();

            // 获取数据
            /*ArrayList<Paper> paperList = (ArrayList<Paper>)session.getAttribute("paperlist");
            int id = (int)session.getAttribute("paperlistid");
            int i = id-1;
            Paper paper = paperList.get(i);*/
            Paper paper = (Paper)session.getAttribute("showPaper");
            Teacher teacher = (Teacher)session.getAttribute("currentTeacher");

            //这一步，进行图片的处理，获取前台传过来的图片base64编码，在利用工具类解析图片保存到本地，然后利用工具类获取图片本地地址
            String barBase64Info = request.getParameter("barBase64Info");
            //System.out.println(barBase64Info);
            String path = "D:";
            String image1 = ImageUtil.savePictoServer(barBase64Info, path);
            image1  = imageUtil.getImageStr(image1);

            //将以上处理的数据都存入dataMap 中
            dataMap.put("study_year",paper.getStudy_year());
            dataMap.put("term",paper.getTerm());
            dataMap.put("username",paper.getUsername());
            dataMap.put("course",teacher.getCourse());
            dataMap.put("college",teacher.getCollege());
            dataMap.put("profession",paper.getProfession());
            dataMap.put("credit",paper.getCredit());
            dataMap.put("question_num",paper.getQuestion_num());
            dataMap.put("year_term",paper.getStudy_year()+"学年"+paper.getTerm()+"学期");
            dataMap.put("exam_time",paper.getExam_time());
            dataMap.put("number_join",paper.getNumber_join());
            dataMap.put("score_high",paper.getScore_high());
            dataMap.put("score_lowest",paper.getScore_lowest());
            dataMap.put("rate_pass",paper.getRate_pass());
            dataMap.put("score_average",paper.getScore_average());
            dataMap.put("score_variance",paper.getScore_variance());
            dataMap.put("people0to9",paper.getPeople0to9());
            dataMap.put("people10to19",paper.getPeople10to19());
            dataMap.put("people20to29",paper.getPeople20to29());
            dataMap.put("people30to39",paper.getPeople30to39());
            dataMap.put("people40to49",paper.getPeople40to49());
            dataMap.put("people50to59",paper.getPeople50to59());
            dataMap.put("people60to69",paper.getPeople60to69());
            dataMap.put("people70to79",paper.getPeople70to79());
            dataMap.put("people80to89",paper.getPeople80to89());
            dataMap.put("people90",paper.getPeople90());
            dataMap.put("per0to9",paper.getPer0to9());
            dataMap.put("per10to19",paper.getPer10to19());
            dataMap.put("per20to29",paper.getPer20to29());
            dataMap.put("per30to39",paper.getPer30to39());
            dataMap.put("per40to49",paper.getPer40to49());
            dataMap.put("per50to59",paper.getPer50to59());
            dataMap.put("per60to69",paper.getPer60to69());
            dataMap.put("per70to79",paper.getPer70to79());
            dataMap.put("per80to89",paper.getPer80to89());
            dataMap.put("per90",paper.getPer90());
            dataMap.put("answer1",paper.getAnswer1());
            dataMap.put("answer2",paper.getAnswer2());
            dataMap.put("answer3",paper.getAnswer3());
            dataMap.put("image",image1);

            List<String> listType = new ArrayList<String>();
            List<String> listRate = new ArrayList<String>();
            for (int j=0;j<paper.getQuestion_num();j++){
                if(j == 0){
                    listType.add(paper.getQuestionType1());
                    String rate = paper.getQuestionScoreRate1()+"";
                    listRate.add(rate);
                }else if (j==1){
                    listType.add(paper.getQuestionType2());
                    String rate = paper.getQuestionScoreRate2()+"";
                    listRate.add(rate);
                }else if (j==2){
                    listType.add(paper.getQuestionType3());
                    String rate = paper.getQuestionScoreRate3()+"";
                    listRate.add(rate);
                }else if (j==3){
                    listType.add(paper.getQuestionType4());
                    String rate = paper.getQuestionScoreRate4()+"";
                    listRate.add(rate);
                }else if (j==4){
                    listType.add(paper.getQuestionType5());
                    String rate = paper.getQuestionScoreRate5()+"";
                    listRate.add(rate);
                }else if (j==5){
                    listType.add(paper.getQuestionType6());
                    String rate = paper.getQuestionScoreRate6()+"";
                    listRate.add(rate);
                }else if (j==6){
                    listType.add(paper.getQuestionType7());
                    String rate = paper.getQuestionScoreRate7()+"";
                    listRate.add(rate);
                }else if (j==7){
                    listType.add(paper.getQuestionType8());
                    String rate = paper.getQuestionScoreRate8()+"";
                    listRate.add(rate);
                }else if (j==8){
                    listType.add(paper.getQuestionType9());
                    String rate = paper.getQuestionScoreRate9()+"";
                    listRate.add(rate);
                }
            }
            dataMap.put("listType",listType);
            dataMap.put("listRate",listRate);

            //进行word文件的处理
            request.setCharacterEncoding("utf-8");
            File file = null;
            InputStream fin = null;
            OutputStream out = null;
            String filename = "试卷分析报告.doc";
            //dataMap是上面处理完的数据，MODELPATH是模板文件的存储路径，"UserWordMuban.xml"是相应的模板文件
            file = docUtil.createDoc(dataMap, MODELPATH, "UserWordMuban.xml", request);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*跳转到登录后菜单界面*/
    @RequestMapping("/allOptions")
    public String gotoAllOptions() {
        return "AllOptions";
    }

    /*跳转到登录界面（首页）*/
    @RequestMapping("/signUpOrIn")
    public String gotoSignUpOrIn() {
        return "SignUpOrIn";
    }

    /*跳转到重置密码界面（忘记密码）*/
    @RequestMapping("/forgetPassword")
    public String gotoForgetPassword() {
        return "ForgetPassword";
    }

    /*跳转到登录后用户信息管理界面*/
    @RequestMapping("/userManager")
    public String gotoUserManager() {
        return "UserManager";
    }

    /*跳转到登录后设置试卷界面*/
    @RequestMapping("/analysisMessage")
    public String gotoAnalysisMessage() {
        return "AnalysisMessage";
    }

    /*跳转到登录后题目设置界面*/
    @RequestMapping("/analysisQuestion")
    public String gotoAnalysisQuestion() {
        return "AnalysisQuestion";
    }

    /*跳转到登录后导入Excel界面*/
    @RequestMapping("/analysisInput")
    public String gotoAnalysisInput() {
        return "AnalysisInput";
    }

    /*跳转到登录后教师填写分析界面*/
    @RequestMapping("/analysisAnswer")
    public String gotoAnalysisAnswer() {
        return "AnalysisAnswer";
    }

    /*跳转到登录后生成试卷界面*/
    @RequestMapping("/analysisPaper")
    public String gotoAnalysisPaper() {
        return "AnalysisPaper";
    }

    /*跳转到登录后报告管理界面*/
    @RequestMapping("/paperManager")
    public String gotoPaperManager() {
        return "PaperManager";
    }

    /*跳转到报告管理列表--分析报告展示*/
    @RequestMapping("/paperManagerShow")
    public String gotoPaperManagerShow() {
        return "PaperManagerShow";
    }

}
