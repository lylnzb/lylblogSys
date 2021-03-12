package com.lylblog.common.util.file;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @Author: lyl
 * @Date: 2020/11/17 17:32
 */
public class FileDownloadUtil {

    private static final Logger logger = getLogger(FileDownloadUtil.class);

    public static void download(HttpServletResponse response, String filePath, String fileName, String encode) {
        response.setContentType("application/octet-stream;charset=" + encode);
        String downLoadPath = filePath;
        File file = new File(downLoadPath);
        try (
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())
        ) {
            long fileLength = file.length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-Disposition","inline;filename=" + new String((fileName).getBytes("utf-8"),"ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 以流的方式下载
     *
     * @param path
     * @param response
     * @return
     */
    public HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(".".lastIndexOf(filename) + 1).toUpperCase();
            // 以流的形式下载文件。
            byte[] buffer;
            try (InputStream fis = new BufferedInputStream(new FileInputStream(path))) {
                buffer = new byte[fis.available()];
                fis.read(buffer);
            }
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    /**
     * 下载本地文件
     *
     * @param response
     * @throws FileNotFoundException
     */
    public void downloadLocal(HttpServletResponse response) throws IOException {
        // 下载本地文件
        // 文件的默认保存名
        String fileName = "Operator.doc";
        // 读到流中
        // 文件的存放路径
        try (InputStream inStream = new FileInputStream("c:/Operator.doc")) {
            // 设置输出的格式
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            try {
                while ((len = inStream.read(b)) > 0) {
                    response.getOutputStream().write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载网络文件
     *
     * @throws IOException
     */
    public void downloadNet() throws IOException {
        // 下载网络文件
        int bytesum = 0;
        int byteread;

        URL url = new URL("windine.blogdriver.com/logo.gif");

        URLConnection conn = url.openConnection();
        try (InputStream inStream = conn.getInputStream(); FileOutputStream fs = new FileOutputStream("c:/abc.gif")) {
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        }
    }

    public void downLoadIsOnLine(String fileName, HttpServletRequest request, HttpServletResponse response, boolean isOnLine) throws IOException {
        String absolutePath = fileName;
        File f = new File(absolutePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        //解决中文乱码
        String userAgent = request.getHeader("user-agent").toLowerCase();
        fileName = f.getName();
        String downloadFileName;

        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            downloadFileName = URLEncoder.encode((fileName), "UTF-8");
        } else {
            downloadFileName = new String((fileName).getBytes(StandardCharsets.UTF_8), "iso-8859-1");
        }
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        // 在线打开方式
        if (isOnLine) {
            URL u = new URL("file:///" + absolutePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + downloadFileName);
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + downloadFileName);
        }

        try (BufferedInputStream br = new BufferedInputStream(new FileInputStream(f)); OutputStream out = response.getOutputStream()) {

            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }
}
