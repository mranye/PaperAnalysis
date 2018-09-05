package com.education.paper.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**echarts图片处理工具类*/
public class ImageUtil {

    /**
     * 从path这个地址获取一张图片然后转为base64码
     * @param imgName  图片的名字（带后缀的）
     * @param path     图片存放的路径*/
    public static String getImageFromServer(String imgName,String path)throws Exception{
        BASE64Encoder encoder = new BASE64Encoder();
        File f = new File(path+imgName);
        if(!f.exists()){
            f.createNewFile();
        }
        BufferedImage bi = ImageIO.read(f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        byte[] bytes = baos.toByteArray();
        return encoder.encodeBuffer(bytes).trim();
    }

    /**
     * 将一个base64转换成图片保存在path文件夹下,命名随机
     * @param path  是一个文件夹路径*/
    public static String savePictoServer(String base64String,String path)throws Exception{

        BASE64Decoder decoder = new BASE64Decoder();
        //要把+在上传时变成的空格再改为+
        base64String = base64String.replaceAll(" ", "+");
        //去掉“data:image/png;base64,”后面才是base64编码，去掉之后才能解析
        base64String = base64String.replace("data:image/png;base64,","");
        //在本地指定位置建立文件夹，path由控制台那边进行定义
        String realPath = path+"/"+"echarts";
        File dir=new File(realPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String fileName=path+"\\"+"echarts"+"\\"+ UUID.randomUUID().toString()+".png";
        try {
            byte[] buffer = decoder.decodeBuffer(base64String);
            OutputStream os = new FileOutputStream(fileName);
            for(int i =0;i<buffer.length;++i){
                if(buffer[i]<0){//调整异常数据
                    buffer[i]+=256;
                }
            }
            os.write(buffer);
            os.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return fileName;
    }

    /**
     * 读取图片在本地存储的位置*/
    public String getImageStr(String imgFile) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

}
