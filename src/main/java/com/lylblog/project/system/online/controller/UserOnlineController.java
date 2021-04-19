package com.lylblog.project.system.online.controller;

import com.lylblog.common.util.shiro.ShiroUtils;
import com.lylblog.framework.server.WebSocketServer;
import com.lylblog.framework.shiro.session.OnlineSessionDAO;
import com.lylblog.project.common.bean.ResultObj;
import com.lylblog.project.system.online.bean.OnlineSession;
import com.lylblog.project.system.online.bean.UserOnlineBean;
import com.lylblog.project.system.online.service.UserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author: lyl
 * @Date: 2021/2/22 14:34
 */
@Controller
@RequestMapping("/admin/online")
public class UserOnlineController {

    private static String BASEPATH = "/admin/online";

    @Resource
    private UserOnlineService userOnlineService;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    @RequestMapping("/userOnline")
    public String userOnline(){
        return BASEPATH + "/userOnline";
    }

    /**
     * 查询会话集合
     *
     */
    @RequestMapping("/selectUserOnlineList")
    @ResponseBody
    public ResultObj selectUserOnlineList(@RequestBody UserOnlineBean userOnline) {
        try{
            return ResultObj.ok(userOnlineService.selectUserOnlineList(userOnline));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultObj.fail("操作失败");
    }

    //@RequiresPermissions("monitor:online:forceLogout")
    @PostMapping("/forceLogout")
    @ResponseBody
    public ResultObj forceLogout(String sessionId)
    {
        UserOnlineBean online = userOnlineService.selectOnlineById(sessionId);
        if (sessionId.equals(ShiroUtils.getSessionId()))
        {
            return ResultObj.fail("当前登陆用户无法强退");
        }
        if (online == null)
        {
            return ResultObj.fail("用户已下线");
        }
        OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
        if (onlineSession == null)
        {
            return ResultObj.fail("用户已下线");
        }
        onlineSession.setStatus(OnlineSession.OnlineStatus.off_line);
        online.setSessionStatus(OnlineSession.OnlineStatus.off_line);
        onlineSessionDAO.update(onlineSession);
        userOnlineService.saveOnline(online);

        try{
            WebSocketServer.sendInfo("您已被强制退出，请重新登录！！！",sessionId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResultObj.ok();
    }

}
