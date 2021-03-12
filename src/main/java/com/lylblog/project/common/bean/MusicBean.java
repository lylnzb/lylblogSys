package com.lylblog.project.common.bean;

/**
 * @Author: lyl
 * @Date: 2020/11/19 9:10
 */
public class MusicBean {
    private boolean basic;    //是否歌单
    private String name;      //歌名
    private String singer;    //歌手
    private String img;       //歌曲封面
    private String src;       //歌曲路径
    private String lrc;       //歌词内容
    private String time;      //时长

    public boolean isBasic() {
        return basic;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
