//会话过期弹出框
function admin_del(msg) {
    layer.alert(msg, {
        title:'系统提示'
        , skin: 'layui-layer-lan'
        ,closeBtn: 0
    }, function(index) {
        layer.close(index);
        location.reload();
    });
}

function getCookie(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

function getSessionId(){
    var c_name = 'sid';
    var c_start=document.cookie.indexOf(c_name + "=");
    if(c_start!=-1){
        c_start=c_start + c_name.length+1;
        var c_end=document.cookie.indexOf(";",c_start);
        if(c_end==-1) c_end=document.cookie.length;
        return unescape(document.cookie.substring(c_start,c_end));
    }else{
        //手动从后台传来sessionid
        var sessionId = $('#sessionId').val();
        return sessionId;
    }
}

var socket;
if(typeof(WebSocket) == "undefined") {
    console.log("您的浏览器不支持WebSocket");
}else{
    console.log("您的浏览器支持WebSocket");
    console.log(getSessionId());
    //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
    var socketUrl= basePath + "/imserver/"+getSessionId();
    socketUrl=socketUrl.replace("https","ws").replace("http","ws");

    if(socket!=null){
        socket.close();
        socket=null;
    }
    socket = new WebSocket(socketUrl);
    //打开事件
    socket.onopen = function() {
        console.log("websocket已打开");
    };
    //获得消息事件
    socket.onmessage = function(msg) {
        var result = JSON.parse(msg.data);
        console.log(result);
        //admin_del(msg.data);
        custom_close(result.type, result.result);
        //发现消息进入    开始处理前端触发逻辑
    };
    //关闭事件
    socket.onclose = function() {
        console.log("websocket已关闭");
    };
    //发生了错误事件
    socket.onerror = function() {
        console.log("websocket发生了错误");
    }
}