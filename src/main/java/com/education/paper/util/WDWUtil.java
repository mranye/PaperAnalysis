package com.education.paper.util;

/**判断Excel文件版本*/
public class WDWUtil {

    /**是否是2003的excel，返回true则是2003*/
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**是否是2007的excel，返回true则是2007*/
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}
