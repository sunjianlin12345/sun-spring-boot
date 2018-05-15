package com.sunjianlin.sunspringboot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sunjianlin
 * 2018年03月16日 16:52:47
 */
public class CodeUtil {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String getCharByUUId() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return  shortBuffer.toString();
    }

    //U1418628302
    public static String getCodeByUUId(String prefix) {
//        int prefix = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
//         0 代表前面补充0
//         10 代表长度为10
//         d 代表参数为正数型
        return  prefix+ String.format("%010d", hashCodeV);
    }

    //20180316165826799
    public static String getCodeByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

    /**
     * 根据指定字符分割，并组成驼峰命名格式
     * @param filed
     * @param regex
     * @param lower true为首字母小写
     * @return
     */
    public static String convertLowerOrUpper(String filed, String regex, boolean lower){
        StringBuffer result = new StringBuffer("");
        String[] chars = filed.split(regex);
        String first = lower ?
                convertFirstChar(chars[0], false)
                : convertFirstChar(chars[0], true);
        result.append(first);
        for(int i=1; i<chars.length; i++) {
            result.append(convertFirstChar(chars[i], true));
        }
        return result.toString();
    }

    /**
     * 首字母大小写转换
     * @param str
     * @param beginUp
     * @return
     */
    public static String convertFirstChar(String str, Boolean beginUp) {
        char[] ch = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            if (i == 0 && beginUp) {// 如果首字母需大写
                sbf.append(charToUpperCase(ch[i]));
            } else {
                sbf.append(charToLowerCase(ch[i]));
            }
        }
        return sbf.toString();
    }

    /**
     * 大小写转换
     * @param str
     * @param beginUp
     * @return
     */
    public static String convertString(String str, Boolean beginUp) {
        char[] ch = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            if (beginUp) {
                sbf.append(charToUpperCase(ch[i]));
            } else {
                sbf.append(charToLowerCase(ch[i]));
            }
        }
        return sbf.toString();
    }


    /**
     * 转大写
     * @param ch
     * @return
     */
    private static char charToUpperCase(char ch) {
        if (ch <= 122 && ch >= 97) {
            ch -= 32;
        }
        return ch;
    }

    /**
     * 转小写
     * @param ch
     * @return
     */
    private static char charToLowerCase(char ch) {
        if (ch <= 90 && ch >= 65) {
            ch += 32;
        }
        return ch;
    }

}
