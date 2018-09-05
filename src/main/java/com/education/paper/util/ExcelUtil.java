package com.education.paper.util;

import com.education.paper.entity.Paper;
import com.education.paper.entity.QuestionScore;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**Excel文件上传、下载、读取内容*/
public class ExcelUtil {

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ExcelUtil(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    /** 验证是否是Excel文件*/
    public boolean validateExcel(String filePath){
        if (filePath == null ||!(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){
            errorMsg = "文件名不是Excel格式";
            return false;
        }
        return true;
    }

    /**游客  读Excel文件，获取每题成绩集合*/
    public List<QuestionScore> getExcelInfo(String fileName,MultipartFile Mfile,HttpServletRequest request){
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
        File file = new  File("D:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) file.mkdirs();
        //新建一个文件
        File file1 = new File("D:\\fileupload\\" + new Date().getTime() + ".xlsx");
        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化每题成绩的集合
        List<QuestionScore> questionScoreList = new ArrayList<QuestionScore>();
        //初始化输入流
        InputStream is = null;
        try{
            //验证文件名是否合格
            if(!validateExcel(fileName)){
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //根据excel里面的内容读取客户信息
            questionScoreList = getExcelInfo(is, isExcel2003,request);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return questionScoreList;
    }

    /** 登录后  读Excel文件，获取每题成绩集合*/
    public List<QuestionScore> getExcelInfoUser(String fileName,MultipartFile Mfile,HttpServletRequest request){
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
        File file = new  File("D:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) file.mkdirs();
        //新建一个文件
        File file1 = new File("D:\\fileupload\\" + new Date().getTime() + ".xlsx");
        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化每题成绩的集合
        List<QuestionScore> questionScoreList = new ArrayList<QuestionScore>();
        //初始化输入流
        InputStream is = null;
        try{
            //验证文件名是否合格
            if(!validateExcel(fileName)){
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //根据excel里面的内容读取客户信息
            questionScoreList = getExcelInfoUser(is, isExcel2003,request);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return questionScoreList;
    }

    /** 游客  根据excel里面的内容读取每题成绩*/
    public  List<QuestionScore> getExcelInfo(InputStream is,boolean isExcel2003,HttpServletRequest request){
        List<QuestionScore> questionScoreList = null;
        try {
            /*根据版本选择创建Workbook的方式*/
            Workbook wb = null;
            //当excel是2003时
            if(isExcel2003){
                wb = new HSSFWorkbook(is);
            }
            else{//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面的信息
            questionScoreList=readExcelValue(wb,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionScoreList;
    }

    /** 登录后  根据excel里面的内容读取每题成绩*/
    public  List<QuestionScore> getExcelInfoUser(InputStream is,boolean isExcel2003,HttpServletRequest request){
        List<QuestionScore> questionScoreList = null;
        try {
            /*根据版本选择创建Workbook的方式*/
            Workbook wb = null;
            //当excel是2003时
            if(isExcel2003){
                wb = new HSSFWorkbook(is);
            }
            else{//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面的信息
            questionScoreList=readExcelValueUser(wb,request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionScoreList;
    }

    /** 游客  读取Excel的值*/
    public List<QuestionScore> readExcelValue(Workbook wb,HttpServletRequest request){
        HttpSession session = request.getSession();
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);
        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();
        //System.out.println("Excel有"+totalRows+"行");
        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }

        //初始化每题成绩的集合
        List<QuestionScore> questionScoreList = new ArrayList<QuestionScore>();
        //获取用户输入的大题数目
        /*HttpSession session = request.getSession();
        Paper paper = (Paper) session.getAttribute("youkeTitle");
        int question_num = paper.getQuestion_num();
        System.out.println(question_num+"道题目");*/

        int numjoin = totalRows-1;
        session.setAttribute("numjoin", numjoin);
        //System.out.println("Excel有"+totalCells+"列");
        int question_num = (int)session.getAttribute("questionnum");

        QuestionScore questionScore;

        Row row = null;
        Cell cell = null;

        //循环Excel行数,从第二行开始。标题不入库
        for(int r=1;r<totalRows;r++) {
            row = sheet.getRow(r);
            if (row == null) continue;
            questionScore = new QuestionScore(totalRows);

            String[] lie = new String[totalCells];
            //循环Excel的列
            for (int c=row.getFirstCellNum();c<this.totalCells;c++){
                /*设置单元格类型*/
                row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
                cell = row.getCell(c);
                if (cell!=null){
                    lie[c] = cell.getStringCellValue();
                    //lie[c] =cell.getNumericCellValue()+"";
                    /*if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        lie[c] = String.valueOf(cell.getNumericCellValue());
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                        lie[c] =String.valueOf(cell.getBooleanCellValue());
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                        lie[c] = String.valueOf(cell.getCellFormula());
                    } if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        lie[c] = String.valueOf(cell.getNumericCellValue());
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                        lie[c] =String.valueOf(cell.getBooleanCellValue());
                    } else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
                        lie[c] = String.valueOf(cell.getCellFormula());
                    } else {
                        lie[c] = cell.getStringCellValue();
                    }*/
                }
            }
            questionScore.setScore(lie);
            questionScoreList.add(questionScore);
        }
        return questionScoreList;
    }

    /** 登录后  读取Excel的值*/
    public List<QuestionScore> readExcelValueUser(Workbook wb,HttpServletRequest request){
        HttpSession session = request.getSession();
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);
        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();
        //System.out.println("Excel有"+totalRows+"行");
        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }

        //初始化每题成绩的集合
        List<QuestionScore> questionScoreList = new ArrayList<QuestionScore>();
        //获取用户输入的大题数目
        /*HttpSession session = request.getSession();
        Paper paper = (Paper) session.getAttribute("youkeTitle");
        int question_num = paper.getQuestion_num();
        System.out.println(question_num+"道题目");*/

        int numjoin = totalRows-1;
        session.setAttribute("numjoin", numjoin);
        //System.out.println("Excel有"+totalCells+"列");
        Paper paper = (Paper)session.getAttribute("currentPaper");
        int question_num = paper.getQuestion_num();
        paper.setNumber_join(numjoin);
        session.setAttribute("currentPaper",paper);

        QuestionScore questionScore;

        Row row = null;
        Cell cell = null;

        //循环Excel行数,从第二行开始。标题不入库
        for(int r=1;r<totalRows;r++) {
            row = sheet.getRow(r);
            if (row == null) continue;
            questionScore = new QuestionScore(totalRows);

            String[] lie = new String[totalCells];
            //循环Excel的列
            for (int c=row.getFirstCellNum();c<this.totalCells;c++){
                /*设置单元格类型*/
                row.getCell(c).setCellType(Cell.CELL_TYPE_STRING);
                cell = row.getCell(c);
                if (cell!=null){
                    lie[c] = cell.getStringCellValue();
                }
            }
            questionScore.setScore(lie);
            questionScoreList.add(questionScore);
        }
        return questionScoreList;
    }

    /**游客  创建Excel文档,导出Excel(xlsx)*/
    public SXSSFWorkbook exportExcel(HttpServletRequest request){
        HttpSession session = request.getSession();
        int question_num = (int)session.getAttribute("questionnum");

        //System.out.println("question_num"+question_num);
        String[] title = new String[question_num];
        int m = 0;
        for (int i=0;i<title.length;i++){
            m = i+1;
            title[i] = "第"+m+"道大题成绩";
        }
        //创建一个工作簿对象
        SXSSFWorkbook wb = new  SXSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("学生各题成绩");
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<title.length;i++){
            sheet.setColumnWidth((short) i, (short) (40 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
        // 创建单元格格式
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setLocked(false);

        // 创建字体
        Font f = wb.createFont();

        // 创建字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBold(true);//粗体显示

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        /*cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);*/

        //设置列名
        for(int i=0;i<title.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(cs);
        }

        return wb;
    }

    /**登录后  创建Excel文档,导出Excel(xlsx)*/
    public SXSSFWorkbook exportExcelUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        int question_num = paper.getQuestion_num();//获取大题数目
        String[] title = new String[question_num];//Excel中第一行表头内容
        int m = 0;
        for (int i=0;i<title.length;i++){
            m = i+1;
            title[i] = "第"+m+"道大题成绩";
        }
        SXSSFWorkbook wb = new  SXSSFWorkbook(); //创建一个工作簿对象
        Sheet sheet = wb.createSheet("学生各题成绩"); // 创建第一个sheet（页），并命名
        for(int i=0;i<title.length;i++){  // 设置每列列宽
            sheet.setColumnWidth((short) i, (short) (40 * 150));
        }
        Row row = sheet.createRow((short) 0);// 创建第一行
        CellStyle cs = wb.createCellStyle();// 创建单元格格式
        cs.setAlignment(HorizontalAlignment.CENTER);//单元格居中
        cs.setLocked(false);
        Font f = wb.createFont();// 创建字体
        f.setFontHeightInPoints((short) 10); // 创建字体样式（用于列名）
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBold(true);//粗体显示
        cs.setFont(f); // 设置第一种单元格的样式（用于列名）
        for(int i=0;i<title.length;i++){ //为第一行设置列名
            Cell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(cs);
        }
        return wb;
    }



}
