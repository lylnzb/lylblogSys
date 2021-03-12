package com.lylblog.common.util.file;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * 文件处理工具类
 * 
 * @author ruoyi
 */
public class FileUtil {

    /**
     * base64字符串转化成图片
     * @param imgStr
     * @param filePath
     * @return
     */
    public static boolean GenerateImage(String imgStr,String filePath) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null){ //图像数据为空
            return false;
        }
        imgStr = imgStr.split(",")[1];
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0) {//调整异常数据
                    b[i]+=256;
                }
            }
            File file = new File(filePath);
            //判断目标文件所在的目录是否存在
            if(!file.getParentFile().exists()) {
                //如果目标文件所在的目录不存在，则创建父目录
                System.out.println("目标文件所在目录不存在，准备创建它！");
                if(!file.getParentFile().mkdirs()) {
                    System.out.println("创建目标文件所在目录失败！");
                    return false;
                }
            }
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 判断图片base64字符串的文件格式
     * @param base64ImgData
     * @return
     */
    public static String checkImageBase64Format(String base64ImgData) {
        String imgStr = base64ImgData.substring(0, 25);
        String type = "";
        if (imgStr.contains("bmp")) {
            type = "bmp";
        } else if (imgStr.contains("png")) {
            type = "png";
        } else if (imgStr.contains("jpg") || imgStr.contains("jpeg")) {
            type = "jpg";
        }
        return type;
    }

    /**
     * 获取文件内容信息
     *
     * @throws Exception
     */
    public static String getLyrics(String fileUrl){
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fileUrl));

            byte[] b = new byte[2048];
            int len = -1;
            StringBuffer lyrics = new StringBuffer();
            while ((len = in.read(b, 0, b.length)) != -1) {
                String str = new String(b, 0, len, "UTF-8");
                lyrics.append(str);
            }
            return lyrics.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                //关闭流对象
                in.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 获取MP3文件时长
     * @param filePath
     * @return
     */
    public static String getMP3ToDuration(String filePath){
        File file = new File(filePath);
        try {
            MP3File f = (MP3File) AudioFileIO.read(file);
            MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
            return parseMinutes(audioHeader.getTrackLength());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 秒转分:秒格式
     * @param seconds
     * @return
     */
    public static String parseMinutes(Integer seconds){
        Integer minutes = seconds / 60;
        Integer remainingSeconds = seconds % 60;
        return minutes + ":" + remainingSeconds;
    }

    public static void main(String[] args) throws Exception{
        //testIO2();
        String fileUrl = "E://musicfile/0dcf85b44b224cc2818bf8869c1b8370/38dcff49d8c073353fa93c582c54fbbe.mp3";
        System.out.println(fileUrl.substring(0,fileUrl.lastIndexOf("/")+1));
    }
}
