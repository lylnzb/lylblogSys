package com.lylblog.common.util;

import java.util.List;
import java.util.Map;

/**
 * @description: list对象转二维数组 工具类
 * @Author: lyl
 * @Date: 2020/11/19 9:27
 */
public class EntityToArrayUtil {

    /**
     * 转二维数组
     * @param map
     * @param <T>
     * @return
     */
    public static <T> Object[][] toArray(Map<Integer, T> map){
        Object[][] result = new Object[map.size()][];

        Integer i = 0;
        //keySet获取map集合key的集合
        for(Integer key:map.keySet()){
            List list = (List) map.get(key);

            //定义一个Object类型的不规则数组
            result[i] = new Object[list.size()];
            for(int j = 0;j < list.size();j++){
                result[i][j] = list.get(j);
            }
            i++;
        }
        return result;
    }
}
