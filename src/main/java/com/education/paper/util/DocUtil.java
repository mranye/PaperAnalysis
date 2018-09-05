package com.education.paper.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

/** 导出word工具类*/
public class DocUtil {

    public Configuration configure=null;

    public DocUtil(){
        configure= new Configuration(Configuration.getVersion());
        configure.setDefaultEncoding("utf-8");
    }

    /**
     * 根据Doc模板生成word文件
     * @param dataMap 需要填入模板的数据
     * @param downloadType 文件名称
     * @param modelPath 保存路径 */
    public File createDoc(Map<String,Object> dataMap, String modelPath, String downloadType, HttpServletRequest request){
        String name = "temp" + (int) (Math.random() * 100000) + ".doc";
        File f = new File(name);
        //加载需要装填的模板
        Template template=null;
        try {

            //设置模板装置方法和路径，FreeMarker支持多种模板装载方法。可以重servlet，classpath,数据库装载。
            //加载模板文件，放在/WEB-INF/uploadFiles/file/下
            //System.out.println(modelPath);
            configure.setServletContextForTemplateLoading(request.getServletContext(), modelPath);
            //设置对象包装器
            //configure.setObjectWrapper(new DefaultObjectWrapper());
            //设置异常处理器
            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            //定义Template对象，注意模板类型名字与downloadType要一致
            template=configure.getTemplate(downloadType);

            Writer out = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            template.process(dataMap, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return f;
    }

}
