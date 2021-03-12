package com.lylblog.project.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回结果的对象
 * @author xym
 * @date 2018-8-3
 * @varsion 1.0
 */
public class ResultObj {

    public static final int CODE_OK = 0;
    public static final int CODE_FAIL = 1;
    public static final int CODE_TIMEOUT = 9;

    public static final String MSG_OK = "操作成功";
    public static final String MSG_FAIL = "操作失败";
    public static final String MSG_TIMEOUT = "操作超时";

    //结果状态
    private Integer code;
    //结果消息
    private String msg;
    //列表行数
    @JsonInclude(Include.NON_NULL)
    private Integer count;
    //结果集合
    @JsonInclude(Include.NON_NULL)
    private List<?> data;

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public List<?> getData() {
        return data;
    }
    public void setData(List<?> data) {
        this.data = data;
    }

    public ResultObj(){
    }
    private ResultObj(Integer code , String msg){
        this.code = code;
        this.msg = msg;
    }
    private ResultObj(Integer code , String msg, Integer count,List<?> data){
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public static ResultObj ok() {
        return ok("");
    }

    public static ResultObj ok(String msg) {
        String message = MSG_OK;
        if(null != msg && !"".equals(msg)){
            message = msg;
        }
        return new ResultObj(CODE_OK, message);
    }

    public static ResultObj ok(Object obj) {
        if(obj instanceof List) {
            List<Object> list = (ArrayList<Object>) obj;
            return ok(list.size(), list);
        }else {
            List<Object> list = new ArrayList<Object>();
            list.add(obj);
            return ok(1, list);
        }
    }

    public static ResultObj ok(int count, List<?> data) {
        return new ResultObj(CODE_OK, MSG_OK, count, data);
    }

    public static ResultObj fail() {
        return fail("");
    }

    public static ResultObj fail(String msg) {
        String message = MSG_FAIL;
        if(null != msg && !"".equals(msg)){
            message = msg;
        }
        return new ResultObj(CODE_FAIL, message);
    }

    public static ResultObj fail(int fail, String msg) {
        String message = MSG_FAIL;
        if(null != msg && !"".equals(msg)){
            message = msg;
        }
        return new ResultObj(fail, message);
    }

    public static ResultObj timeout() {
        return timeout("");
    }

    public static ResultObj timeout(String msg) {
        String message = MSG_TIMEOUT;
        if(! StringUtils.isEmpty(msg)) {
            message = message + ", " + msg;
        }
        message = message + ".";
        return new ResultObj(CODE_TIMEOUT, message);
    }
    @Override
    public String toString() {
        return "ResultObj [code=" + code + ", msg=" + msg + ", count=" + count
                + ", data=" + data + "]";
    }
}
