package com.lylblog.project.system.music.bean;

import com.lylblog.project.common.bean.ParaBean;

/**
 * @Author: lyl
 * @Date: 2020/11/15 13:07
 */
public class WebMusicBean extends ParaBean {
    private String musicId;     //	歌曲编号
    private String musicName;   //	歌名
    private String singer;      //	歌手
    private String coverUrl;    //	封面路径
    private String musicUrl;    //	歌曲路径
    private String lyricsUrl;   //	歌词路径
    private String length;      //  时长
    private String lyrics;      //  歌词内容
    private String languages;   //	语种
    private String style;       //	风格
    private String gedan;       //  所属歌单
    private String description; //	歌曲描述

    private String fileId;      //	文件编号
    private String fileName;    //	文件名称
    private String fileUrl;     //	文件路径
    private String fileType;    //	文件类型
    private String valid;       //	有效标志[1:有效,0:无效]

    private String languageName;//  语种名称
    private String styleName;   //  风格名称
    private String gedanName;   //  所属歌单名称


    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getLyricsUrl() {
        return lyricsUrl;
    }

    public void setLyricsUrl(String lyricsUrl) {
        this.lyricsUrl = lyricsUrl;
    }

    public String getGedan() {
        return gedan;
    }

    public void setGedan(String gedan) {
        this.gedan = gedan;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getGedanName() {
        return gedanName;
    }

    public void setGedanName(String gedanName) {
        this.gedanName = gedanName;
    }
}
