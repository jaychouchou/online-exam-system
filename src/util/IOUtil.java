package com.elt.util;

import java.io.*;

/**
 * Created by 肖安华 on java.
 * 获得BufferedRead的工具类
 */
public class IOUtil {
    private static BufferedReader br;
    public static BufferedReader getRead(String filepath){
        try {
            br=new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }
    public static void close(){
        if (br!=null){
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
