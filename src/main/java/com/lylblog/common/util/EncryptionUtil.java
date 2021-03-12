package com.lylblog.common.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 加密工具类
 */
public class EncryptionUtil {

    /**
     * 加密
     * @param password
     * @return
     */
    public Map<String,String> encryption(String password){
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String pwd = new Md5Hash(password,salt,2).toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("password",pwd);
        map.put("salt",salt);
        return map;
    }

    public static Map<String,String> MD5Pwd(String username, String pwd) {
        // 加密算法MD5
        // salt盐 username + salt
        // 迭代次数
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String md5Pwd = new SimpleHash("MD5", pwd,
                ByteSource.Util.bytes(username + salt), 2).toHex();
        Map<String, String> map = new HashMap<String, String>();
        map.put("password",md5Pwd);
        map.put("salt",salt);
        return map;
    }
}
