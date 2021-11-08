package com.lylblog.common.util;

/**
 * @Author: lyl
 * @Date: 2021/11/5 18:42
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(new Test().num());
    }

    public int num() {
        int num = 0;
        try{
            num = 5/1;
            return num;
        }catch (Exception e) {
            return num++;
        }finally {
            return 1;
        }
    }

}
