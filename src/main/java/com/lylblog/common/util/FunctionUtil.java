/**   
* @Title: FunctionUtil.java
* @Package com.cshg.basicframe.util
* @Description: 
* @author yuanshuai
* @date Jan 4, 2017 10:16:04 AM
* @version V1.0
*/
package com.lylblog.common.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author YShuai
 *
 */
public class FunctionUtil {
	private static Pattern p=Pattern.compile("^[-\\+]?[.\\d]*$");
	
	/**
	 * 获得UUID
	 * @return
	 */
	public static String GetGuid() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}

	/**
	 * 获得当前时间字符串（默认格式）
	 * @return yyyy/MM/dd HH24:mi:ss
	 */
	public static String getCurrentTimeStr() {
		Date dt=new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return df.format(dt);
	}
	
	/**
	 * 获得当前时间字符串（自定格式）
	 * @param format
	 * @return
	 */
	public static String getCurrentTimeStr(String format) {
		Date dt=new Date();
		DateFormat df = new SimpleDateFormat(format);
		return df.format(dt);
	}
	
	/**
	 * 获得随机数字串
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length){
		String val = "";
		Random random = new Random();
		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++){
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}
	
	/**
	 * 生成随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length){
		String val = "";
		Random random = new Random();
		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++){
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum)){
				//输出是大写字母还是小写字母
//				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				int temp = 97;
				val += (char)(random.nextInt(26) + temp);
			}else if("num".equalsIgnoreCase(charOrNum)){
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	/**
	 * 字符串转日期
	 * @param str
	 * @param fmt
	 */
	public static Date parseStrToDate(String str, String fmt){
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		Date date = null;
		try{
			date = format.parse(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 字符串转日期 "yyyy/MM/dd HH:mm:ss"
	 * @param str 
	 */
	public static Date parseStrToDate(String str){
		String fmt = "yyyy/MM/dd HH:mm:ss";
		return parseStrToDate(str, fmt);
	}
	
	
	/** * 解析base64
	 * @param base64Info 
	 * @return 
	 * @throws IOException 
	 * */ 
	public static byte[] decodeBase64(String base64Info) throws IOException{ 
		if(null==base64Info||"".equals(base64Info)){ return null; }   
		String[] arr = base64Info.split("base64,");  
		byte[] buffer = Base64.decodeBase64(arr[1]);
		 return buffer;
	}
	public static byte[] decode2Base64(String base64Info) throws IOException{ 
		if(null==base64Info||"".equals(base64Info)){ return null; }   
		byte[] buffer = Base64.decodeBase64(base64Info); 
		 return buffer;
	}
	/** * 编码base64
	 * @param base64Info 
	 * @return 
	 * @throws IOException 
	 * */ 
	public static String encodeBase64(byte[] arr) throws IOException{ 
		if(null==arr||arr.length==0){ return null; }
		String buffer = Base64.encodeBase64String(arr); 
		 return buffer;
	}
	
	/**
	 * 文件转byte
	 * @param filePath
	 * @return
	 */
	public static byte[] file2byte(String filePath) {
	    byte[] buffer = null;
	    File file = new File(filePath);
	    FileInputStream fis = null;
	    ByteArrayOutputStream bos = null;
	    try{
	    	fis = new FileInputStream(file);
	        bos = new ByteArrayOutputStream();
	        byte[] b = new byte[1024];
	        int n;
	        while ((n = fis.read(b)) != -1) {
	            bos.write(b, 0, n);
	        }
	        buffer = bos.toByteArray();
	    } catch (Exception e) {
	        // e.printStackTrace();
	    }finally{
	    	if(null!=bos){
	    		try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	if(null!=fis){
	    		try {
					fis.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
	    	}
	    }
	    return buffer;
	}
	
	/**
	 * 文件转byte
	 * @param file
	 * @return
	 */
	public static byte[] file2byte(File file) {
	    byte[] buffer = null;
	    FileInputStream fis = null;
	    ByteArrayOutputStream bos = null;
	    try{
	    	fis = new FileInputStream(file);
	        bos = new ByteArrayOutputStream();
	        byte[] b = new byte[1024];
	        int n;
	        while ((n = fis.read(b)) != -1) {
	            bos.write(b, 0, n);
	        }
	        buffer = bos.toByteArray();
	    } catch (Exception e) {
	        // e.printStackTrace();
	    }finally{
	    	if(null!=bos){
	    		try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	if(null!=fis){
	    		try {
					fis.close();
				} catch (IOException e) { 
					e.printStackTrace();
				}
	    	}
	    }
	    return buffer;
	}
	
	 /** 
     * byte转文件
     */  
    public static File byte2file(byte[] bfile, String filePath) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        } 
        return file;
    } 
	 
	/**
	 * 复制文件
	 * @param path
	 * @param path1
	 */
	public static File fileCopy(String path,String path1) {
		FileInputStream input = null;
		FileOutputStream output = null;
		File file = new File(path);
		File file1 = new File(path1);
		try {
			input = new FileInputStream(file);//"文件路径"
			output = new FileOutputStream(file1);//"目标文件路径"
 
			byte[] bt = new byte[1024];
			int realbyte = 0;
			/**      input.read(bt)
		     * Reads up to <code>b.length</code> bytes of data from this input
		     * stream into an array of bytes. This method blocks until some input
		     * is available.
		     *
		     * @param      b   the buffer into which the data is read.
		     * @return     the total number of bytes read into the buffer, or
		     *             <code>-1</code> if there is no more data because the end of
		     *             the file has been reached.
		     * @exception  IOException  if an I/O error occurs.
		     */
			while ((realbyte = input.read(bt)) > 0) {
				
				/**   output.write(bt,0,realbyte)
			     * Writes <code>len</code> bytes from the specified byte array
			     * starting at offset <code>off</code> to this file output stream.
			     *
			     * @param      b     the data.
			     * @param      off   the start offset in the data.
			     * @param      len   the number of bytes to write.
			     * @exception  IOException  if an I/O error occurs.
			     */
				output.write(bt,0,realbyte);
			}
			
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file1;
	}
	
	/**
	 * 判断字符串全为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		if(!"".equals(null==str?"":str)){
		for (int i = str.length();--i>=0;){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
			return true;
		}else{
			return	true;
		}
	}
	/**
	 * 判断字符长度
	 * @param str
	 * @param len
	 * @return
	 */
	public static boolean checkLength(String str,int len){
		if(!"".equals(null==str?"":str)){
			if(str.length()<=len){
				return true;
			}else{
				return false;
			} 
		}else{
			return true;
		}
	}
	/**
	 * 字符串是否为时间根式
	 * @param str
	 * @param fmt
	 * @return
	 */
	public static boolean isValidDate(String str,String fmt) {
	    boolean convertSuccess=true;
	    SimpleDateFormat format = new SimpleDateFormat(fmt);
	    try { 
	       format.setLenient(false);
	       format.parse(str);
	    } catch (ParseException e) { 
	       convertSuccess=false;
	    } 
	    return convertSuccess;
	}
	
	public static boolean isDouble(String str) {   
        return p.matcher(str).matches();  
	}  
	
	
	/**
     * 用户身份证号码的打码隐藏加星号加* 18位和非18位身份证处理均可成功处理 参数异常返回null
     * 
     * @param idCardNum
     *            身份证号码
     * @param front
     *            需要显示前几位
     * @param end
     *            需要显示末几位
     * @return 处理完成的身份证
     */
    public static String idMask(String idCardNum, int front, int end)
    {
        // 身份证不能为空
        if (idCardNum == null || "".equals(idCardNum))
        {
            return null;
        }
        // 需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length())
        {
            return null;
        }
        // 需要截取的不能小于0

        if (front < 0 || end < 0)
        {
            return null;
        }
        // 计算*的数量
        //int asteriskCount = idCardNum.length() - (front + end);
        StringBuffer asteriskStr = new StringBuffer();
        /*for (int i = 0; i < asteriskCount; i++)
        {
            asteriskStr.append("*");
        }*/
        asteriskStr.append("******");
        String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
        return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
    }
    
    /**
     * 获取两个日期相差的月数
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }
}
