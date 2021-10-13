package com.lylblog.project.api.weixin.controller;

import com.lylblog.common.api.weixin.mp.aes.AesException;
import com.lylblog.common.api.weixin.mp.aes.SHA1;
import com.lylblog.common.api.weixin.mp.aes.WXBizMsgCrypt;
import com.lylblog.common.api.weixin.properties.WeixinProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Author: lyl
 * @Date: 2021/10/8 16:34
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {

    private Logger logger = LoggerFactory.getLogger(WeixinController.class);

    @RequestMapping("/api/serverConfig")
    @ResponseBody
    public String serverConfig(String echostr, String signature, String timestamp, String nonce) throws AesException {
        //排序
        String[] arr = {"lylBlogToken", timestamp, nonce};
        Arrays.sort(arr);

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        //sha1Hex 加密
        MessageDigest md = null;
        String temp = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            temp = byteToStr(digest);
            logger.info("加密后的token:"+temp);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if ((temp.toLowerCase()).equals(signature)){
            return echostr;
        }
        return null;
    }

    private static String byteToStr(byte[] byteArray){
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    private static String byteToHexStr(byte mByte){
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4)& 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

}
