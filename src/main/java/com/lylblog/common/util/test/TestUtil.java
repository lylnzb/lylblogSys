package com.lylblog.common.util.test;

/**
 * @Author: lyl
 * @Date: 2021/3/12 13:48
 */
public class TestUtil {

    public static void main(String[] args){
        int arr[] = {23,12,56,34,65,87,38,13,19,50,26,76};

        for( int i = 1; i < arr.length; i++ ) {
            //抽一张牌
            int temp = arr[i];
            int j = i;
            while( j > 0 &&  temp < arr[j -1] ) {
                arr[j] = arr[j -1];
                j--;
            }
            arr[j] = temp;
        }

        for( int i = 1; i < arr.length; i++ ){
            System.out.print(arr[i] + ",");
        }
    }
}
