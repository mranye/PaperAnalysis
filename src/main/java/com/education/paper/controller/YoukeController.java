package com.education.paper.controller;

import com.education.paper.entity.Paper;
import com.education.paper.entity.Teacher;
import com.education.paper.service.YoukeService;
import com.education.paper.util.DocUtil;
import com.education.paper.util.ExcelUtil;
import com.education.paper.util.ImageUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/youke")
public class YoukeController {

    @Autowired
    YoukeService youkeService;

    /*跳转到游客设置试卷信息界面*/
    @RequestMapping(value = "youkeInput")
    public String gotoYoukeInput() {
        return "YoukeInput";
    }

    /*跳转到游客上传文件界面*/
    @RequestMapping(value = "youkeUpload")
    public String gotoYoukeUpload() {
        return "YoukeUpload";
    }

    /*跳转到游客下载报告界面*/
    @RequestMapping(value = "youkeDownload")
    public String gotoYoukeDownload() {
        return "YoukeDownload";
    }

    /*跳转到游客填写分析界面*/
    @RequestMapping(value = "youkePaper")
    public String gotoYoukePaper() {
        return "YoukePaper";
    }

    /*下载Excel模板*/
    @RequestMapping(value = "downloadMuban")
    public String downloadMuban( HttpServletRequest request, HttpServletResponse response){
        /*response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+ fileName);*/
        ServletOutputStream out = null;
        ExcelUtil excelUtil = new ExcelUtil();
        SXSSFWorkbook workbook = excelUtil.exportExcel(request);
        String fileName = "学生成绩列表.xlsx";
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
            //response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            //response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xlsx").getBytes(), "utf-8"));

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

    /* 游客 分析Excel数据、计算并跳转*/
    @RequestMapping(value = "youkeJisuan")
    public String youkeJisuan(@RequestParam(value="filename") MultipartFile file, HttpServletRequest request) {
        /*获取Excel文件名字*/
        if(file==null) return "redirect:/youke/youkeUpload";//判断文件是否为空
        String name=file.getOriginalFilename(); //获取文件名
        long size=file.getSize();//进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        if(name==null || ("").equals(name) && size==0) return"redirect:/youke/youkeUpload";
        boolean b = youkeService.excelInfoImport(name,file,request);//导入Excel数据。参数：文件名，文件。
        if(b){
            String Msg ="导入EXCEL成功！";
            request.getSession().setAttribute("msg",Msg);
        }else{
            String Msg ="导入EXCEL失败！";
            request.getSession().setAttribute("msg",Msg);
        }
        /*页面跳转*/
        return "redirect:/youke/youkePaper";
    }

    /*游客填写试卷信息后提交*/
    @RequestMapping(value = "titleMessage")
    public String getTitleMessage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Paper paper = new Paper();
        paper.setUsername(request.getParameter("username"));
        String course = request.getParameter("course");
        String college = request.getParameter("college");
        paper.setProfession(request.getParameter("profession"));
        paper.setCredit(request.getParameter("credit"));
        String question_num = request.getParameter("question_num");
        if (question_num.trim().length() == 0 || question_num.equals("")) {
            paper.setQuestion_num(5);
            int num = 5;
            session.setAttribute("questionnum", num);
        } else {
            paper.setQuestion_num(Integer.parseInt(question_num));
            session.setAttribute("questionnum", Integer.parseInt(question_num));
        }
        paper.setTerm(request.getParameter("term"));
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
        String study_year = request.getParameter("study_year");
        if (study_year.trim().length()==0||study_year.equals("")){
            String year = exam_time.substring(0,4);
            paper.setStudy_year(year);
        }else{
            paper.setStudy_year(study_year);
        }
        session.setAttribute("youkeTitle", paper);
        session.setAttribute("youkeTitleCourse", course);
        session.setAttribute("youkeTitleCollege", college);
        return "redirect:/youke/youkeUpload";

    }

    /*传递游客填写的三个试卷分析的答案*/
    @RequestMapping(value = "youkeAnswers")
    public String youkeAnswers(String answer1,String answer2,String answer3,HttpServletRequest request){
        HttpSession session = request.getSession();
        String[] answers = new String[]{answer1,answer2,answer3};
        session.setAttribute("answers",answers);
        return "redirect:/youke/youkeDownload";
    }

    //static String MODELPATH = "/uploadFiles/…";
    static String MODELPATH = "/WEB-INF/uploadFiles/file/";
    /*游客下载试卷分析*/
    @RequestMapping(value = "youkeFinalDownload")
    //public String youkeFinalDownload(@RequestBody String barBase64Info, HttpServletRequest request, HttpServletResponse response){
    public String youkeFinalDownload( HttpServletRequest request, HttpServletResponse response){

        /*System.out.println(barBase64Info);*/
        HttpSession session = request.getSession();
        try {

            //引入导出word的工具类
            DocUtil docUtil = new DocUtil();
            //引入处理图片的工具类，包含将base64编码解析为图片并保存本地，获取图片本地路径
            ImageUtil imageUtil = new ImageUtil();
            //建立map存储所要导出到word的各种数据和图像，不能使用自己项目封装的类型，例如PageData
            Map<String, Object> dataMap = new HashMap<String, Object>();

            // 获取数据
            Paper paperTitle = (Paper)session.getAttribute("youkeTitle");
            String course = (String)session.getAttribute("youkeTitleCourse");
            String college = (String)session.getAttribute("youkeTitleCollege");
            int number_join = (int)session.getAttribute("numjoin");
            Paper paperJisuan = (Paper)session.getAttribute("jisuanPaper");
            String[] answers = (String[])session.getAttribute("answers");

            //这一步，进行图片的处理，获取前台传过来的图片base64编码，在利用工具类解析图片保存到本地，然后利用工具类获取图片本地地址
            String barBase64Info = request.getParameter("barBase64Info");
            //System.out.println(barBase64Info);
            String path = "D:";
            String image1 = ImageUtil.savePictoServer(barBase64Info, path);
            image1  = imageUtil.getImageStr(image1);

            //将以上处理的数据都存入dataMap 中
            dataMap.put("study_year",paperTitle.getStudy_year());
            dataMap.put("term",paperTitle.getTerm());
            dataMap.put("username",paperTitle.getUsername());
            dataMap.put("course",course);
            dataMap.put("college",college);
            dataMap.put("profession",paperTitle.getProfession());
            dataMap.put("credit",paperTitle.getCredit());
            dataMap.put("question_num",paperTitle.getQuestion_num());
            dataMap.put("year_term",paperTitle.getStudy_year()+"学年"+paperTitle.getTerm()+"学期");
            dataMap.put("exam_time",paperTitle.getExam_time());
            dataMap.put("number_join",number_join);
            dataMap.put("score_high",paperJisuan.getScore_high());
            dataMap.put("score_lowest",paperJisuan.getScore_lowest());
            dataMap.put("rate_pass",paperJisuan.getRate_pass());
            dataMap.put("score_average",paperJisuan.getScore_average());
            dataMap.put("score_variance",paperJisuan.getScore_variance());
            dataMap.put("people0to9",paperJisuan.getPeople0to9());
            dataMap.put("people10to19",paperJisuan.getPeople10to19());
            dataMap.put("people20to29",paperJisuan.getPeople20to29());
            dataMap.put("people30to39",paperJisuan.getPeople30to39());
            dataMap.put("people40to49",paperJisuan.getPeople40to49());
            dataMap.put("people50to59",paperJisuan.getPeople50to59());
            dataMap.put("people60to69",paperJisuan.getPeople60to69());
            dataMap.put("people70to79",paperJisuan.getPeople70to79());
            dataMap.put("people80to89",paperJisuan.getPeople80to89());
            dataMap.put("people90",paperJisuan.getPeople90());
            dataMap.put("per0to9",paperJisuan.getPer0to9());
            dataMap.put("per10to19",paperJisuan.getPer10to19());
            dataMap.put("per20to29",paperJisuan.getPer20to29());
            dataMap.put("per30to39",paperJisuan.getPer30to39());
            dataMap.put("per40to49",paperJisuan.getPer40to49());
            dataMap.put("per50to59",paperJisuan.getPer50to59());
            dataMap.put("per60to69",paperJisuan.getPer60to69());
            dataMap.put("per70to79",paperJisuan.getPer70to79());
            dataMap.put("per80to89",paperJisuan.getPer80to89());
            dataMap.put("per90",paperJisuan.getPer90());
            dataMap.put("answer1",answers[0]);
            dataMap.put("answer2",answers[1]);
            dataMap.put("answer3",answers[2]);
            dataMap.put("image",image1);

            //进行word文件的处理
            request.setCharacterEncoding("utf-8");
            File file = null;
            InputStream fin = null;
            OutputStream out = null;
            String filename = "分析报告.doc";
            //dataMap是上面处理完的数据，MODELPATH是模板文件的存储路径，"wordMuban.xml"是相应的模板文件
            file = docUtil.createDoc(dataMap, MODELPATH, "wordMuban.xml", request);
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


        session.removeAttribute("youkeTitle");
        session.removeAttribute("youkeTitleCourse");
        session.removeAttribute("youkeTitleCollege");
        session.removeAttribute("answers");

        return "/youke/youkeFinalDownload";
    }


}
