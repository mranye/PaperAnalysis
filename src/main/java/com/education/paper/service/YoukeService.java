package com.education.paper.service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface YoukeService {

    /*导入Excel中学生成绩*/
    public boolean excelInfoImport(String name,MultipartFile file,HttpServletRequest request);

}
