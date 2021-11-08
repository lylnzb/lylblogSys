package com.lylblog.project.common.service.impl;

import com.lylblog.common.util.EntityToArrayUtil;
import com.lylblog.common.util.FunctionUtil;
import com.lylblog.common.util.RSAUtil;
import com.lylblog.common.util.redis.RedisUtil;
import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.project.common.bean.*;
import com.lylblog.project.common.mapper.CommonMapper;
import com.lylblog.project.common.service.CommonService;
import com.lylblog.project.login.bean.UserLoginBean;
import com.lylblog.project.system.article.bean.ArticleBean;
import com.lylblog.project.system.article.mapper.ArticleMapper;
import com.lylblog.project.system.blogSet.bean.BlogSetBean;
import com.lylblog.project.system.comment.bean.CommentBean;
import com.lylblog.project.system.comment.mapper.CommentMapper;
import com.lylblog.project.system.dict.bean.DictDataBean;
import com.lylblog.project.system.log.bean.BrowseLogBean;
import com.lylblog.project.system.log.mapper.LogMapper;
import com.lylblog.project.webSite.blog.bean.WebArticleBean;
import com.lylblog.project.webSite.comment.bean.WebCommentBean;
import com.lylblog.project.webSite.comment.bean.WebGreatBean;
import com.lylblog.project.webSite.index.bean.ArticleListBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lylblog.framework.config.*;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonMapper commonMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private LogMapper logMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据编码类别查询字典
     * @param dictType
     * @return
     */
    public ResultObj queryCodeValue(String dictType){
        List<DictDataBean> dictDataList = commonMapper.queryCodeValue(dictType);
        return ResultObj.ok(dictDataList);
    }

    /**
     * 根据编码类别和字典值查询字典
     * @param dictType
     * @return
     */
    public ResultObj queryCodeValueByCode(String dictType, String values) {
        DictDataBean dictDataBean = commonMapper.queryCodeValueByCode(dictType, values);
        return ResultObj.ok(dictDataBean);
    }

    /**
     * 查询音乐列表
     * @return
     */
    public Object[][] queryMusicList(){
        //查询音乐歌单数据
        List<DictDataBean> dictDataList = commonMapper.queryCodeValue("web_music_gedan");

        Map<Integer, Object> musicMap = new HashMap<Integer, Object>();
        Integer i = 1;
        for(DictDataBean dict : dictDataList) {
            List<MusicBean> musicList = commonMapper.queryMusicList(dict.getDictValue());
            for(MusicBean music : musicList){
                music.setImg(LylBlogConfig.getBasePath() + "musicfile/" + music.getImg());
                music.setSrc(LylBlogConfig.getBasePath() + "musicfile/" + music.getSrc());
            }
            musicMap.put(i, musicList);
            i++;
        }
        return EntityToArrayUtil.toArray(musicMap);
    }

    /**
     * 导航栏初始化
     * @return
     */
    public ResultObj queryMeunInfo(){
        List<MenuBean> menuList = commonMapper.queryMeunInfo("2",null);
        for(MenuBean menu : menuList){
            if("1".equals(menu.getIsDefault())){
                menu.setChildList(commonMapper.queryMeunInfo("1", menu.getId()));
            }
        }
        return ResultObj.ok(menuList.size(), menuList);
    }

    /**
     * 根据栏目编号获取标签信息
     * @param columnId
     * @return
     */
    public ResultObj getLabelList(String columnId){
        List<LabelBean> labelList = commonMapper.getLabelList(columnId);
        return ResultObj.ok(labelList.size(), labelList);
    }

    /**
     * 获取站长推荐文章信息
     * @return
     */
    public ResultObj getBlogRecommended() {
        BlogSetBean blogSet = commonMapper.getBlogConfiguration();
        List<ArticleListBean> articleList = commonMapper.getBlogRecommended(blogSet.getBlogsetRecommendedShowNum().toString());
        return ResultObj.ok(articleList.size(), articleList);
    }

    /**
     * 获取博客配置信息
     * @return
     */
    public BlogSetBean getBlogConfiguration(){
        return commonMapper.getBlogConfiguration();
    }

    /**
     * 获取所有省份
     * @return
     */
    public ResultObj getProvince(){
        List<AreaBean> provinceList = commonMapper.getProvince();
        return ResultObj.ok(provinceList.size(), provinceList);
    }

    /**
     * 通过省份行政区划编码获取城市
     * @param code
     * @return
     */
    public ResultObj getCityByProvinceCode(String code){
        List<AreaBean> cityList = commonMapper.getCityByProvinceCode(code);
        return ResultObj.ok(cityList.size(), cityList);
    }

    /**
     * 通过城市行政区划编码获取地区
     * @param code
     * @return
     */
    public ResultObj getAreaByCityCode(String code){
        List<AreaBean> areaList = commonMapper.getAreaByCityCode(code);
        return ResultObj.ok(areaList.size(), areaList);
    }

    /**
     * 生成个人动态信息数据
     * @param obj
     * @param type
     */
    public void aspectDynamicInfo(Object obj, int type) {
        //获取当前用户信息
        UserLoginBean userBean = ShiroUtils.getUserInfo();

        DynamicBean dynamic = new DynamicBean();
        dynamic.setDynamicType(String.valueOf(type));          //动态类型
        dynamic.setOperYhnm(userBean.getYhnm());               //操作人用户内码
        dynamic.setOperTime(FunctionUtil.getCurrentTimeStr()); //操作时间
        if(1 == type) {//文章评论
            CommentBean comment = (CommentBean) obj;
            ArticleBean article = articleMapper.getArticleInfoByWznm(comment.getWznm());

            dynamic.setWznm(comment.getWznm());                    //文章内码
            dynamic.setArticleTitle(article.getArticleTitle());    //文章名称
            dynamic.setCommentContent(comment.getCommentContent());//评论内容
        }else if(2 == type) {//文章评论回复
            CommentBean comment = (CommentBean) obj;
            ArticleBean article = articleMapper.getArticleInfoByWznm(comment.getWznm());
            CommentBean webComment = commentMapper.getCommentByReplyId(comment.getReplyId());

            dynamic.setWznm(comment.getWznm());                    //文章内码
            dynamic.setArticleTitle(article.getArticleTitle());    //文章名称
            dynamic.setCommentYhnm(webComment.getSubmitPerson());  //评论用户内码
            dynamic.setCommentContent(comment.getCommentContent());//评论内容
        }else if(3 == type || 4 == type) {//文章评论点赞或取消点赞
            WebGreatBean webGreat = (WebGreatBean) obj;
            CommentBean comment = commentMapper.getCommentByReplyId(webGreat.getTypeId());
            ArticleBean article = articleMapper.getArticleInfoByWznm(comment.getWznm());

            dynamic.setWznm(comment.getWznm());                    //文章内码
            dynamic.setArticleTitle(article.getArticleTitle());    //文章名称
            dynamic.setCommentYhnm(comment.getSubmitPerson());  //评论用户内码
        }else if(5 == type) {//留言反馈
            CommentBean comment = (CommentBean) obj;
            dynamic.setCommentContent(comment.getCommentContent());//评论内容
        }else if(6 == type) {//留言反馈回复
            CommentBean comment = (CommentBean) obj;
            CommentBean webComment = commentMapper.getCommentByReplyId(comment.getReplyId());

            dynamic.setCommentYhnm(webComment.getSubmitPerson());  //评论用户内码
            dynamic.setCommentContent(comment.getCommentContent());//评论内容
        }else if(7 == type || 8 == type) {//留言反馈点赞或取消点赞
            WebGreatBean webGreat = (WebGreatBean) obj;
            CommentBean comment = commentMapper.getCommentByReplyId(webGreat.getTypeId());

            dynamic.setCommentYhnm(comment.getSubmitPerson());  //评论用户内码
        }
        commonMapper.insertDynamicInfo(dynamic);
    }

    /**
     * 新增博客浏览日志记录
     * @param browseLog
     * @return
     */
   public ResultObj insertBlogBrowseLogInfo(BrowseLogBean browseLog){
       int count = logMapper.insertBlogBrowseLogInfo(browseLog);
       if(count > 0){
           return ResultObj.ok();
       }
       return ResultObj.fail();
   }

    /**
     * 获取RSA算法公钥
     * @return
     */
    public String getPublicKey() throws Exception {
        KeyPair kp = RSAUtil.generateRSAKeyPair();
        RSAPublicKey pubk = (RSAPublicKey) kp.getPublic();//生成公钥
        RSAPrivateKey prik= (RSAPrivateKey) kp.getPrivate();//生成私钥
        String publicKey = RSAUtil.getKeyString(pubk);
        String privateKey = RSAUtil.getKeyString(prik);

        //公钥保存进Redis缓存
        redisUtil.setEx(ShiroUtils.getSessionId() + "_privateKey", privateKey, 10, TimeUnit.SECONDS);

        return publicKey;
    }

}
