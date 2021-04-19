package com.lylblog.common.util.validation;

/**
 * 正则表达式工具
 * @Author: lyl
 * @Date: 2021/4/7 17:25
 */
public class RegularExpressUtil {

    /** 整数  */
    public static final String  V_INTEGER="^-?[1-9]\\d*$";

    /**  正整数 */
    public static final String V_Z_INDEX="^[1-9]\\d*$";

    /**负整数 */
    public static final String V_NEGATIVE_INTEGER="^-[1-9]\\d*$";

    /** 数字 */
    public static final String V_NUMBER="^([+-]?)\\d*\\.?\\d+$";

    /**正数 */
    public static final String V_POSITIVE_NUMBER="^[1-9]\\d*|0$";

    /** 负数 */
    public static final String V_NEGATINE_NUMBER="^-[1-9]\\d*|0$";

    /** 浮点数 */
    public static final String V_FLOAT="^([+-]?)\\d*\\.\\d+$";

    /** 正浮点数 */
    public static final String V_POSTTIVE_FLOAT="^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";

    /** 负浮点数 */
    public static final String V_NEGATIVE_FLOAT="^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$";

    /** 非负浮点数（正浮点数 + 0） */
    public static final String V_UNPOSITIVE_FLOAT="^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";

    /** 非正浮点数（负浮点数 + 0） */
    public static final String V_UN_NEGATIVE_FLOAT="^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";

    /** 邮件 */
    public static final String V_EMAIL="^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /** 颜色 */
    public static final String V_COLOR="^[a-fA-F0-9]{6}$";

    /** url */
    public static final String V_URL="^((https|http|ftp|rtsp|mms)?://)"
            + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
            + "|" // 允许IP和DOMAIN（域名）
            + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
            + "[a-z]{2,6})" // first level domain- .com or .museum
            + "(:[0-9]{1,4})?" // 端口- :80 <br>
            + "((/?)|" // a slash isn't required if there is no file name
            + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

    /** 仅中文 */
    public static final String V_CHINESE="^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

    /** 仅ACSII字符 */
    public static final String V_ASCII="^[\\x00-\\xFF]+$";

    /** 邮编 */
    public static final String V_ZIPCODE="^\\d{6}$";

    /** 手机 */
    public static final String V_MOBILE="^(1)[0-9]{10}$";

    /** ip地址 */
    public static final String V_IP4="^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

    /** 非空 */
    public static final String V_NOTEMPTY="^\\S+$";

    /** 图片  */
    public static final String V_PICTURE="(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

    /**  压缩文件  */
    public static final String V_RAR="(.*)\\.(rar|zip|7zip|tgz)$";

    /** 日期 */
    public static final String V_DATE="^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

    /** QQ号码  */
    public static final String V_QQ_NUMBER="^[1-9]*[1-9][0-9]*$";

    /** 电话号码的函数(包括验证国内区号,国际区号,分机号) */
    public static final String V_TEL="^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";

    /** 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串 */
    public static final String V_USERNAME="^\\w+$";

    /** 字母 */
    public static final String V_LETTER="^[A-Za-z]+$";

    /** 大写字母  */
    public static final String V_LETTER_U="^[A-Z]+$";

    /** 小写字母 */
    public static final String V_LETTER_I="^[a-z]+$";

    /** 身份证  */
    public static final String V_IDCARD ="^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

    /**验证密码(数字和英文同时存在)*/
    public static final String V_PASSWORD_REG="[A-Za-z]+[0-9]";

    /**验证密码长度(6-18位)*/
    public static final String V_PASSWORD_LENGTH="^\\d{6,18}$";

    /**验证两位数*/
    public static final String V_TWO＿POINT="^[0-9]+(.[0-9]{2})?$";

    /**验证一个月的31天*/
    public static final String V_31DAYS="^((0?[1-9])|((1|2)[0-9])|30|31)$";
}
