package com.lylblog.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 主键生成工具类
 */
public class IdUtil {
    /**
     * 生成16位不重复的随机数，含数字+大小写
     * @return
     */
    public static String getGUID() {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(2);
            switch (type){
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char)(rd.nextInt(25)+65));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }

    /**
     *
     * 功能描述: <br>
     * 主键生成器32位
     *
     * @return
     */
    public static final synchronized String getUUID() {
        String uuid = UUID.randomUUID().toString();
        StringBuilder sb = new StringBuilder(32);
        sb.append(uuid.substring(0, 8));
        sb.append(uuid.substring(9, 13));
        sb.append(uuid.substring(14, 18));
        sb.append(uuid.substring(19, 23));
        sb.append(uuid.substring(24, 36));
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(getUUID());
    }
}

