package com.lylblog.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ConfigurationProperties(prefix = "lylblog")
public class LylBlogConfig {

    /** 项目名称 */
    private String name;
    /** 版本 */
    private String version;
    /** 版权年份 */
    private String copyrightYear;
    /** 头像上传路径 */
    private static String profile;
    /** 音乐上传路径 */
    private static String musicfile;
    /** 文章上传路径 */
    private static String articlefile;
    /** 百度编辑器文件保存路径 */
    private static String ueditorfile;
    /** 项目基础路径*/
    private static String basePath;
    /** 获取地址开关 */
    private static boolean addressEnabled;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        LylBlogConfig.profile = profile;
    }

    public static String getMusicfile() {
        return musicfile;
    }

    public void setMusicfile(String musicfile) {
        LylBlogConfig.musicfile = musicfile;
    }

    public static String getArticlefile() {
        return articlefile;
    }

    public void setArticlefile(String articlefile) {
        LylBlogConfig.articlefile = articlefile;
    }

    public static String getUeditorfile() {
        return ueditorfile;
    }

    public void setUeditorfile(String ueditorfile) {
        LylBlogConfig.ueditorfile = ueditorfile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        LylBlogConfig.addressEnabled = addressEnabled;
    }

    public static String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        LylBlogConfig.basePath = basePath;
    }
}
