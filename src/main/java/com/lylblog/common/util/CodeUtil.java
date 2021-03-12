package com.lylblog.common.util;

/**
 * 验证码生成工具类
 * 类说明:	生成验证码
 */
public class CodeUtil {


    /**
     * 成随机6位数
     * @return
     */
    public static String getcode(){
        return (int) ((Math.random() * 9 + 1) * 100000)+"";
    }

    /**
     * 成随机4位数
     * @return
     */
    public static String getFour(){
        return (int) ((Math.random() * 9 + 1) * 1000)+"";
    }
}
