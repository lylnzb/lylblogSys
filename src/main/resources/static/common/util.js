var basePath="http://www.lylblog.com/";//业务系统basePath
// var basePath="http://289755ii86.wicp.vip/";//业务系统basePath

var dialog = document.getElementById("loginPage");
var diatitle1 = document.getElementById("title1");
var diatitle2 = document.getElementById("title2");
var diatitle3 = document.getElementById("title3");
var isDraging = false; //是否可拖拽的标记

$(function() {
    getBlogRecommended();
});

$(window).scroll(function() {
    if ($(window).scrollTop() > 50) {
        $(".bt-box.top").fadeIn(200);
    }else {
        $(".bt-box.top").fadeOut(200);
    }
});

//当点击跳转链接后，回到页面顶部位置
$(".bt-box.top").click(function() {
    $('body,html').animate({
            scrollTop: 0
        },
        500);
    return false;
});

//当点击跳转链接后，回到页面顶部位置
$(".bt-box.bottom").click(function() {
    $('body,html').animate({
            scrollTop: $(document).height()
        },
        500);
    return false;
});

$("body").delegate(".newslist li","mouseover", function(){
    $(this).addClass("active");
    $(this).siblings(".active").removeClass("active");
});

$("body").delegate(".newslist li","mouseout", function(){
    $(this).removeClass("active");
    $('.newslist li:first-child').addClass("active");
});

//cocomessage消息提示框 配置全局参数
cocoMessage.config({duration: 10000});

/**
 * localStorage存值的方法
 * @param key
 * @param value
 */
function localSet(key,value){
    var curTime = new Date().getTime();
    localStorage.setItem(key,JSON.stringify({data:value,time:curTime}));
}

/**
 * localStorage取值方法
 * @param key
 * @returns
 */
function localGet(key){
    var data = localStorage.getItem(key);
    var dataObj = JSON.parse(data);
    if(!dataObj)return;
    return dataObj.data;
}

/**
 * 获取url参数的方法
 * @param name 参数名称
 * @returns
 */
function GetQueryString(name) {
    var query = window.location.search.substring(1); //获取url中"?"符后的字串，截取？后的字符串
    var vars = query.split("&");  //字符串按照&拆分
    for(var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");  //获取每一个参数
        if(pair[0] == name) {
            return pair[1];   //获取参数值
        }
    }
    return("");
}

/**
 *	打开窗口的方法
 * openWin(url,title,type,funName,width,height) 在top层中使用
 **/
function openWin(url, title, funName, widths, heights) {
    var height = null != heights ? heights : document.documentElement.clientHeight;
    var height_v = null != heights ? heights : parent.$(document).height();
    var width = null != widths ? widths : parent.$(document).width();
    var index = top.layer.open({
        type: 2
        , area: [width + 'px', (height_v == height ? height : height + 40) + 'px']
        //,offset: [0,0]
        , shade: 0.3
        , maxmin: false
        , title: title
        , content: url
        , end: function () {//层消失回调
            if (undefined != funName) {
                callback(funName);
            } else {

            }
        }
    });
}

/**
 * 初始化下拉框的方法
 * @param ele
 * @param code
 * @param form
 */
function loadSelect(ele,code,form){
    $.ajax({
        url:basePath+'/common/queryCodeValue',
        type:"POST",
        data:{"dictType":code},
        async:false,
        success:function(data){
            for(var i = 0; i<data.data.length;i++){
                $(ele).append(new Option(data.data[i].dictLabel , data.data[i].dictValue));
                //下拉菜单渲染 把内容加载进去
                form.render();
            }
        },
        error:function(){
            alert("初始化选项失败");
        }
    });
}

/**
 * 查询字典值
 * @param dictType
 * @param value
 */
function selectDictLabel(dictType, value){
    var elem = "";
    $.ajax({
        url:basePath+'/common/queryCodeValueByCode?dictType=' + dictType + "&values=" + value,
        type:"POST",
        async:false,
        success:function(data){
            var obj = data.obj;
            elem = "<span class='badge badge-" + obj.dictStyle + "' style='margin-top: 5px;'>" + obj.dictLabel + "</span>"
        },
        error:function(){
            alert("初始化选项失败");
        }
    });
    return elem;
}

/**
 * 所属专栏初始化下拉框的方法
 * @param ele
 * @param code
 * @param form
 */
function loadSelectAllow(ele,form){
    $.ajax({
        url:basePath+'admin/webColumn/queryWebColumnByAllow',
        type:"POST",
        async:false,
        success:function(data){
            for(var i = 0; i<data.data.length;i++){
                $(ele).append(new Option(data.data[i].columnName , data.data[i].columnId));
                //下拉菜单渲染 把内容加载进去
                form.render();
            }
        },
        error:function(){
            alert("初始化选项失败");
        }
    });
}

/**
 * 初始化单选框的方法
 * @param ele
 * @param code
 * @param form
 */
function loadRadio(ele,code,form,filter){
    $.ajax({
        url:basePath+'/common/queryCodeValue',
        type:"POST",
        data:{"dictType":code},
        async:false,
        success:function(data){
            for(var i = 0; i<data.data.length;i++){
                var checked = "";
                var layFilter = "";
                if(data.data[i].isDefault == 'Y'){
                    checked = "checked=''";
                }
                if(filter != null && filter != null){
                    layFilter = "lay-filter='"+filter+"'";
                }
                $(ele).append("<input type=\"radio\" name=\""+code+"\" value=\""+data.data[i].dictValue+"\" "+layFilter+" title=\""+data.data[i].dictLabel+"\" "+checked+"\">");
                //下拉菜单渲染 把内容加载进去
                form.render();
            }
        },
        error:function(){
            alert("初始化选项失败");
        }
    });
}

function download(url) {
    var a = document.createElement('a');
    a.href=basePath + "/"+ url;
    a.click();
}

function Cancel() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

function menuInit(){
    $.ajax({
        url:basePath+'/common/queryMeunInfo',
        type:"POST",
        async:false,
        success:function(data){
            console.log(data);
            var htmlStr = '';
            for(var i = 0; i < data.data.length; i++){
                if(data.data[i].isDefault == '2'){
                    htmlStr += '<a href="' + basePath + data.data[i].menuUrl +'" class="m-item item m-mobile-hide"><i class="'+ data.data[i].icon +' icon"></i>'+ data.data[i].menuName +'</a>';
                }else if(data.data[i].isDefault == '1'){
                    htmlStr += '<div id="menu" class="ui printing dropdown link m-item item m-mobile-hide">';
                    htmlStr += '    <i class="'+ data.data[i].icon +' icon"></i>'+ data.data[i].menuName +'<i class="dropdown icon"></i>';
                    htmlStr += '    <div class="menu" data-filtered="filtered">';
                    for(var j = 0;j < data.data[i].childList.length; j++){
                        htmlStr += '    <div class="item" data-filtered="filtered"><a href="' + basePath + data.data[i].childList[j].menuUrl + '" style="color: #0C0C0C">'+ data.data[i].childList[j].menuName +'</a></div>';
                    }
                    htmlStr += '    </div>';
                    htmlStr += '</div>';
                }
            }
            $("#menu").html(htmlStr);
            $('.ui.dropdown').dropdown({
                on: 'hover'
            });
        },
        error:function(){
            alert("初始化选项失败4");
        }
    });
}

/**
 * 局部刷新方法
 * @param url
 * @param elem
 */
function localRefresh(url, elem){
    $.ajax({
        url: url,
        type: 'GET',
        async: false,
        success: function (data) {
            //重新局部渲染
            $(elem).html(data);
            //导航栏重新渲染
            menuInit();
            //登录框展示
            $(".bg").show();
            $(".login").show();
            $(".userLogin").show();
            $(".userRegister").hide();
            $(".userRetrievePas").hide();
        }
    })
}

//HTML反转义
function HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}

function escape2Html(text) {
    var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
    return text.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
}

// taken from mo.js demos
function isIOSSafari() {
    var userAgent;
    userAgent = window.navigator.userAgent;
    return userAgent.match(/iPad/i) || userAgent.match(/iPhone/i);
};

// taken from mo.js demos
function isTouch() {
    var isIETouch;
    isIETouch = navigator.maxTouchPoints > 0 || navigator.msMaxTouchPoints > 0;
    return [].indexOf.call(window, 'ontouchstart') >= 0 || isIETouch;
};

// taken from mo.js demos
var isIOS = isIOSSafari(),
    clickHandler = isIOS || isTouch() ? 'touchstart' : 'click';

function extend( a, b ) {
    for( var key in b ) {
        if( b.hasOwnProperty( key ) ) {
            a[key] = b[key];
        }
    }
    return a;
}

function Animocon(el, options) {
    this.el = el;
    this.options = extend( {}, this.options );
    extend( this.options, options );
    this.checked = false;
    this.timeline = new mojs.Timeline();
    for(var i = 0, len = this.options.tweens.length; i < len; ++i) {
        this.timeline.add(this.options.tweens[i]);
    }
    var self = this;
    this.el.addEventListener(clickHandler, function() {
        var isGiveLike = $(self.el).find("span").attr('data-text');
        if(isGiveLike == 'true'){
            self.checked = true;
        }

        var isLogin=$("#isAuthenticated").val();
        if(isLogin == 'false'){
            $(".bg").show();
            $(".login").show();
            $(".userLogin").show();
            $(".userRegister").hide();
            $(".userRetrievePas").hide();

            autoCenter();
        }else {
            var commentId = $(self.el).find("span").attr('data-id');
            var praiseNum = $(self.el).siblings("span").text();
            if( self.checked ) {
                var falg = giveOrCancelLike(commentId, false);
                if(falg){
                    self.options.onUnCheck(self.el);
                    $(self.el).siblings("span").text(parseInt(praiseNum) - 1);
                }

            }else {
                var falg = giveOrCancelLike(commentId, true);
                if(falg){
                    self.options.onCheck(self.el);
                    self.timeline.start();

                    $(self.el).siblings("span").text(parseInt(praiseNum) + 1);
                }

            }
        }

        self.checked = !self.checked;
    });
}
Animocon.prototype.options = {
    tweens : [],
    onCheck : function() { return false; },
    onUnCheck : function() { return false; }
};

function giveLike() {
    var items = [].slice.call(document.querySelectorAll('.like'));
    for(var i = 0;i < items.length;i++){
        /* Icon 1 */
        var el1 = items[i].querySelector('button.icobutton'), el1span = el1.querySelector('span');
        new Animocon(el1, {
            tweens : [
                // burst animation
                new mojs.Burst({
                    parent: el1,
                    duration: 1700,
                    shape : 'circle',
                    fill: '#C0C1C3',
                    x: '50%',
                    y: '50%',
                    opacity: 0.6,
                    childOptions: { radius: {15:0} },
                    radius: {30:90},
                    count: 6,
                    isRunLess: true,
                    easing: mojs.easing.bezier(0.1, 1, 0.3, 1)
                }),
                // ring animation
                new mojs.Transit({
                    parent: el1,
                    duration: 700,
                    type: 'circle',
                    radius: {0: 60},
                    fill: 'transparent',
                    stroke: '#C0C1C3',
                    strokeWidth: {20:0},
                    opacity: 0.6,
                    x: '50%',
                    y: '50%',
                    isRunLess: true,
                    easing: mojs.easing.sin.out
                }),
                // icon scale animation
                new mojs.Tween({
                    parent: el1,
                    duration : 1200,
                    onUpdate: function(progress) {
                        var elem = this.o.parent;
                        var $span = document.getElementById($(elem).find("span").attr('id'));
                        if(progress > 0.3) {
                            var elasticOutProgress = mojs.easing.elastic.out(1.43*progress-0.43);
                            $span.style.WebkitTransform = $span.style.transform = 'scale3d(' + elasticOutProgress + ',' + elasticOutProgress + ',1)';
                        } else {
                            $span.style.WebkitTransform = $span.style.transform = 'scale3d(0,0,1)';
                        }
                    }
                })
            ],
            onCheck : function(elem) {
                elem.style.color = 'red';
                $(elem).find("span").attr('data-text', true);
            },
            onUnCheck : function(elem) {
                elem.style.color = '#C0C1C3';
                $(elem).find("span").attr('data-text', false);
            }
        });
    }
}

/**
 * 点赞或者取消赞功能操作
 * @param isGiveLike
 */
function giveOrCancelLike(commentId, isGiveLike){
    var falg = false;

    var obj = new Object();
    obj.typeId = commentId;
    obj.type = "1";
    $.ajax({
        url:basePath+'/comment/addGreatInfo?isGiveLike=' + isGiveLike,
        type:"POST",
        async: false,
        data:JSON.stringify(obj),
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            if(resultData.code == 0){
                falg = true;
            }else {
                falg = false;
            }
        },
        error:function(){
            alert("系统异常！");
        }
    });
    return falg;
}

/**
 * 获取文章标签信息
 */
function getLabelInfo(){
    var columnId = GetQueryString("columnId");
    $.ajax({
        url:basePath+'/common/getLabelList?columnId=' + columnId,
        type:"POST",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            console.log(resultData);
            var data = resultData.data;
            var htmlStr = '<ul class="sidebar_content divTags" style="margin-top: -15px;margin-left: -5px;">';
            for(var i = 0; i < data.length; i++){
                htmlStr += '<li><a href="/blog/blogList?columnId=' + columnId + '&labelId=' + data[i].labelId + '" title="' + data[i].labelName + '">' + data[i].labelName + '<span class="tag-count"> (' + data[i].articleNum + ')</span></a></li>';
            }
            htmlStr += '</ul>';
            $("#divTags").html(htmlStr);
        },
        error:function(){
            alert("系统异常！");
        }
    });
}

/**
 * 查询文章推荐信息
 */
function getBlogRecommended() {
    $.ajax({
        url:basePath+'/common/getBlogRecommended',
        type:"POST",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            console.log(resultData);
            var data = resultData.data;
            var htmlStr = '';
            for(var i = 0; i < data.length; i++){
                var calss = "";
                if(i == 0){
                    calss = "active";
                }
                htmlStr += '<li class="' + calss + '">';
                htmlStr += '    <i content="' + (i + 1) + '"></i>';
                htmlStr += '    <a href="' + data[i].articleUrl + '" target="_blank">' + (data[i].articleTitle.length > 15?(data[i].articleTitle.substr(0, 15) + '...'):data[i].articleTitle) + '</a>';
                htmlStr += '    <p>' + data[i].articleDec + '</p>';
                htmlStr += '</li>';
            }
            $(".newslist").html(htmlStr);
        },
        error:function(){
            alert("系统异常！");
        }
    });
}

/**
 * 退出登录
 */
function loginOut(){
    $.ajax({
        url: basePath + 'loginOut',
        type: "POST",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            if(resultData.code == 0){
                location.reload();
            }else{
                layui.layer.msg(resultData.msg);
            }
        },
        error: function () {

        }
    });
}

$(document).ready(function() {
    try {
        // 操作系统平台
        var clientPlatform = navigator.platform;
        // User-agent头部值
        var clientUserAgent = navigator.userAgent;
        var clientBrowseMessage = getClientBrowseName(clientUserAgent.toLowerCase());
        // 浏览器系统
        var clientBrowsePlatform = clientBrowseMessage[0];
        // 浏览器真名
        var clientBrowseName = clientBrowseMessage[1];
        // 浏览器版本
        var clientBrowseVersion = clientBrowseMessage[2];

        // 浏览器ip
        var clientBrowseIp = '未知';

        // 浏览器地理位置
        var clientBrowseCity = '未知';

        try {
            clientBrowseIp = returnCitySN["cip"];
            clientBrowseCity = localAddress["province"] + localAddress["city"];
            $("#city").text(clientBrowseCity);
        } catch (err) {
            // 处理错误
        }

        var clientBrowseAppAndPc = '未知';
        if ((clientUserAgent
            .match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
            clientBrowseAppAndPc = '移动端';
        } else {
            clientBrowseAppAndPc = 'PC端';
        }

        // 浏览地址
        var clientBrowseUrl = window.location.href;

        var paramDate = {
            "clientPlatform" : clientPlatform,
            "clientUserAgent" : clientUserAgent,
            "clientBrowsePlatform" : clientBrowsePlatform,
            "clientBrowseName" : clientBrowseName,
            "clientBrowseVersion" : clientBrowseVersion,
            "clientBrowseIp" : clientBrowseIp,
            "clientBrowseCity" : clientBrowseCity,
            "clientBrowseAppAndPc" : clientBrowseAppAndPc,
            "clientBrowseUrl" : clientBrowseUrl
        };

        $.ajax({
            url : basePath + "/common/insertBlogBrowseLogInfo",
            type:"POST",
            data:JSON.stringify(paramDate),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            async : false,
            cache : true,
            success : function(data) {
            }
        });

    } catch (err) {
        // 处理错误
    }
});

// 获取具体浏览器
function getClientBrowseName(agent) {
    var arr = [];
    var system = agent.split(' ')[1].split(' ')[0].split('(')[1];
    arr.push(system);
    var regStr_edge = /edge\/[\d.]+/gi;
    var regStr_ie = /trident\/[\d.]+/gi;
    var regStr_ff = /firefox\/[\d.]+/gi;
    var regStr_chrome = /chrome\/[\d.]+/gi;
    var regStr_saf = /safari\/[\d.]+/gi;
    var regStr_opera = /opr\/[\d.]+/gi;
    // IE
    if (agent.indexOf("trident") > 0) {
        arr.push(agent.match(regStr_ie)[0].split('/')[0]);
        arr.push(agent.match(regStr_ie)[0].split('/')[1]);
        return arr;
    }
    // Edge
    if (agent.indexOf('edge') > 0) {
        arr.push(agent.match(regStr_edge)[0].split('/')[0]);
        arr.push(agent.match(regStr_edge)[0].split('/')[1]);
        return arr;
    }
    // firefox
    if (agent.indexOf("firefox") > 0) {
        arr.push(agent.match(regStr_ff)[0].split('/')[0]);
        arr.push(agent.match(regStr_ff)[0].split('/')[1]);
        return arr;
    }
    // Opera
    if (agent.indexOf("opr") > 0) {
        arr.push(agent.match(regStr_opera)[0].split('/')[0]);
        arr.push(agent.match(regStr_opera)[0].split('/')[1]);
        return arr;
    }
    // Safari
    if (agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0) {
        arr.push(agent.match(regStr_saf)[0].split('/')[0]);
        arr.push(agent.match(regStr_saf)[0].split('/')[1]);
        return arr;
    }
    // Chrome
    if (agent.indexOf("chrome") > 0) {
        arr.push(agent.match(regStr_chrome)[0].split('/')[0]);
        arr.push(agent.match(regStr_chrome)[0].split('/')[1]);
        return arr;
    } else {
        arr.push('未知');
        arr.push('未知');
        return arr;
    }
}

var time = $("#onlineTime").val();
let timearr = time.replace(" ", ":").replace(/\:/g, "-").split("-");
//计时器
window.siteTime = function(){
    window.setTimeout("siteTime()", 1000);
    var seconds = 1000;
    var minutes = seconds * 60;
    var hours = minutes * 60;
    var days = hours * 24;
    var years = days * 365;
    var today = new Date();
    var todayYear = today.getFullYear();
    var todayMonth = today.getMonth()+1;
    var todayDate = today.getDate();
    var todayHour = today.getHours();
    var todayMinute = today.getMinutes();
    var todaySecond = today.getSeconds();
    var t1 = Date.UTC(parseInt(timearr[0]),parseInt(timearr[1]),parseInt(timearr[2]),parseInt(timearr[3]),parseInt(timearr[4]),parseInt(timearr[5]));
    var t2 = Date.UTC(todayYear,todayMonth,todayDate,todayHour,todayMinute,todaySecond);
    var diff = t2-t1;
    var diffYears = Math.floor(diff/years);
    var diffDays = Math.floor((diff/days)-diffYears*365);
    var diffHours = Math.floor((diff-(diffYears*365+diffDays)*days)/hours);
    var diffMinutes = Math.floor((diff-(diffYears*365+diffDays)*days-diffHours*hours)/minutes);
    var diffSeconds = Math.floor((diff-(diffYears*365+diffDays)*days-diffHours*hours-diffMinutes*minutes)/seconds);
    var html = "";
    if(diffYears){
        html += '<span style="color:#C40000;">'+diffYears+'</span>';
        html += '<span style="color:#2F889A">年</span>';
        html += '<span style="color:#C40000;">'+diffDays+'</span>';
        html += '<span style="color:#2F889A">天</span>';
        html += '<span style="color:#C40000;">'+diffHours+'</span>';
        html += '<span style="color:#2F889A">时</span>';
        html += '<span style="color:#C40000;">'+diffMinutes+'</span>';
        html += '<span style="color:#2F889A">分</span>';
        html += '<span style="color:#C40000;">'+diffSeconds+'</span>';
        html += '<span style="color:#2F889A">秒</span>';
    }else{
        html += '<span style="color:#C40000;">'+diffDays+'</span>';
        html += '<span style="color:#2F889A">天</span>';
        html += '<span style="color:#C40000;">'+diffHours+'</span>';
        html += '<span style="color:#2F889A">时</span>';
        html += '<span style="color:#C40000;">'+diffMinutes+'</span>';
        html += '<span style="color:#2F889A">分</span>';
        html += '<span style="color:#C40000;">'+diffSeconds+'</span>';
        html += '<span style="color:#2F889A">秒</span>';
    }
    $("#sitetime").html(html);
}

siteTime();

$(function(){
    $("#backtop").each(function(){
        $(this).find(".weixin").mouseenter(function(){
            $(this).find(".pic").fadeIn("fast")
        });
        $(this).find(".weixin").mouseleave(function(){
            $(this).find(".pic").fadeOut("fast")
        });
    });
});