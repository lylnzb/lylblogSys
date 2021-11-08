var basePath="http://www.lylblog.com.cn/";//业务系统basePath

var dialog = document.getElementById("loginPage");
var diatitle1 = document.getElementById("title1");
var diatitle2 = document.getElementById("title2");
var diatitle3 = document.getElementById("title3");
var isDraging = false; //是否可拖拽的标记

var time = $("#onlineTime").val();
let timearr = time.replace(" ", ":").replace(/\:/g, "-").split("-");

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

//当点击跳转链接后，回到页面顶部位置
$(".bt-box.top").click(function() {
    $('body,html').animate({
            scrollTop: 0
        },
        500);
    return false;
});

//当点击跳转链接后，回到页面底部位置
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