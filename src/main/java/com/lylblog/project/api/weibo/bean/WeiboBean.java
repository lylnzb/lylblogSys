package com.lylblog.project.api.weibo.bean;

import lombok.Data;

/**
 * @Author: lyl
 * @Date: 2021/11/4 17:30
 */
@Data
public class WeiboBean {

    private Integer id;  //用户UID
    private String idstr;//字符串型的用户UID
    private String screen_name;//用户昵称
    private String name;//友好显示名称
    private int province;//用户所在省级ID
    private int city;//用户所在城市ID
    private String location;//用户所在地
    private String description;//用户个人描述
    private String url;//用户博客地址
    private String profile_image_url;//用户头像地址（中图），50×50像素
    private String profile_url;//用户的微博统一URL地址
    private String domain;//用户的个性化域名
    private String weihao;//用户的微号
    private String gender;//性别，m：男、f：女、n：未知
    private String followers_count;//粉丝数
    private String friends_count;//	关注数
    private String statuses_count;//微博数
    private String favourites_count;//收藏数
    private String created_at;//用户创建（注册）时间
    private Boolean following;//暂未支持
    private Boolean allow_all_act_msg;//是否允许所有人给我发私信，true：是，false：否
    private Boolean geo_enabled;//是否允许标识用户的地理位置，true：是，false：否
    private Boolean verified;//是否是微博认证用户，即加V用户，true：是，false：否
    private Integer verified_type;//暂未支持
    private String remark;//用户备注信息，只有在查询用户关系时才返回此字段
    //private status	object	用户的最近一条微博信息字段 详细
    private Boolean allow_all_comment;//是否允许所有人对我的微博进行评论，true：是，false：否
    private String avatar_large;//用户头像地址（大图），180×180像素
    private String avatar_hd;//用户头像地址（高清），高清头像原图
    private String verified_reason;//认证原因
    private String follow_me;//该用户是否关注当前登录用户，true：是，false：否
    private Integer online_status;//用户的在线状态，0：不在线、1：在线
    private String bi_followers_count;//用户的互粉数
    private String lang;

}
