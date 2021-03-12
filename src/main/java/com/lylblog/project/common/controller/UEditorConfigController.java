package com.lylblog.project.common.controller;

import com.baidu.ueditor.ActionEnter;
import com.lylblog.common.util.file.FileUploadUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.lylblog.framework.config.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lyl
 * UEditor编辑器java后台配置控制类
 * @Date: 2020/12/29 21:03
 */
@RestController
@RequestMapping("/")
public class UEditorConfigController {

    /**
     * UEditor编辑器配置文件初始化
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/ueditorConfig", method = RequestMethod.GET)
    public String ueditorConfig(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding( "utf-8" );
            response.setHeader("Content-Type" , "text/html");

            String filePath = this.getClass().getResource("/static/common/ueditor1_4_3_3/jsp/common/config.json").getPath();
            String rootPath = "";
            for(int i = 0;i < filePath.split("/").length;i++){
                if(i == 0 || i == filePath.split("/").length - 1){
                    continue;
                }
                rootPath += filePath.split("/")[i] + "/";
            }
            return new ActionEnter( request, rootPath ).exec();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/uploadImage")
    public Map<String, Object> uploadImage(@RequestParam("upfile") MultipartFile imgFile,
                                           HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            //时间格式初始化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //相对应用的基目录
            String baseDir = LylBlogConfig.getUeditorfile() + "/upload/image/" + sdf.format(new Date()) + "/";
            //上传文件类型
            String suffix = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
            //返回上传成功的文件名
            String fileName = FileUploadUtil.upload(baseDir, imgFile, suffix);
            // 是否上传成功
            resultMap.put("state", "SUCCESS");
            // 现在文件名称
            resultMap.put("title", fileName);
            // 文件原名称
            resultMap.put("original", imgFile.getOriginalFilename());
            // 文件类型 .+后缀名
            resultMap.put("type", suffix);
            // 文件路径
            resultMap.put("url", "/upload/image/" + sdf.format(new Date()) + "/" + fileName);
            // 文件大小（字节数）
            resultMap.put("size", imgFile.getSize() + "");
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
