package com.elt.util;

import java.util.Properties;

/**
 * Created by 肖安华 on java.
 * 读取文件的工具包
 */
public class ReadUtil {
    private static Properties properties=new Properties();

    static {
        try {
            //加载的文件一定要放在src目录下
            properties.load(ReadUtil.class.getResourceAsStream("/data.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getMass(String proname){
        return properties.getProperty(proname);
    }
}
