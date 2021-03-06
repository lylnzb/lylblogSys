var isLogin=$("#isAuthenticated").val();
var is = GetQueryString("isLogin");
$(function(){
    /**
     * 如果用户未登录，则弹出登录框
     */
    if(is == 'no' && isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();
    }
});

/**
 * 点击事件
 */
$(document).on('click','.isLogin',function(){
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();
    }else{
        window.open("/user/userCenter");
    }
});

/**
 * 发送邮箱验证码
 */
function sendemail() {
    var obj = new Object();
    obj.tos = $(".email").val();
    $.ajax({
        url: basePath + 'toEmail',
        type: "POST",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            var obj = $("#btn");
            settime(obj);
            console.log('发送邮件');
        },
        error: function () {

        }
    });
}

/**
 * 邮箱验证码一分钟倒计时
 * @param obj
 */
var countdown = 60;
function settime(obj) {
    if (countdown == 0) {
        obj.attr('onclick','sendemail();');
        obj.text("重新发送");
        countdown = 60;
        return;
    } else {
        obj.removeAttr('onclick');
        obj.text(countdown+"S");
        countdown--;
    }
    setTimeout(function() {
        settime(obj);
    }, 1000);
}

/**
 * 用户注册点击事件
 */
$("#register").click(function(){
    var obj = new Object();
    obj.email = $(".email").val();
    obj.password = $("#password").val();
    obj.vCode = $("#code").val();
    $.ajax({
        url: basePath + 'registerUser',
        type: "POST",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            if(resultData.code == 0){
                layui.layer.msg("注册成功");
            }else{
                layui.layer.msg(resultData.msg);
            }
        },
        error: function () {

        }
    });
});

/**
 * 登录输入框失焦事件
 */
$("#loginForm .required").blur(function(){
    var value = $(this).val();
    if(value != null && value != ''){
        var falg = 0;
        if($(this).attr("id") == 'userEmail'){
            falg = vailEmail(this,value);
        }
        if(falg == 0){
            $(this).parents(".field").siblings(".error").hide();
        }
    }
});

function vailEmail(elem,value){
    var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if(!myreg.test(value)){
        $(elem).parents(".field").siblings(".error").find(".transition").text("邮箱格式不正确");
        $(elem).parents(".field").siblings(".error").show();
        return 1;
    }
    return 0;
}

$("#login").click(function(){
    var obj = new Object();
    obj.email = $("#userEmail").val();
    obj.password = $("#userPwd").val();

    /**
     * 用户邮箱和密码是否为空验证
     * @type {number}
     */
    var traverse = 0;
    $("#loginForm .required").each(function(index,result){
        var value = $(this).val();
        if(value == null || value == ''){
            $(this).parents(".field").siblings(".error").show();
            traverse += 1;
        }else{
            if($(this).attr("id") == 'userEmail'){
                traverse += vailEmail(this,value);
            }
        }
    });
    if(traverse == 0){
        $.ajax({
            url: basePath + 'login',
            type: "POST",
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (resultData, XMLHttpRequest) {
                if(resultData.code == 0){
                    localRefresh('/common/headerRefresh', '#header');
                    $(".login").hide();
                    $(".bg").hide();
                }else{
                    layui.layer.msg(resultData.msg);
                }
            },
            error: function () {

            }
        });
    }
});

$(".loginOut").click(function(){
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
});

$(document).on('click','#adminlogin',function(){
    $(".bg").show();
    $(".login").show();
    $(".userLogin").show();
    $(".userRegister").hide();
    $(".userRetrievePas").hide();
});

$(document).on('click','#registerlogin',function(){
    $(".bg").show();
    $(".login").show();
    $(".userLogin").hide();
    $(".userRegister").show();
    $(".userRetrievePas").hide();
});

$(".loginBtn").click(function(){
    $(".userLogin").show();
    $(".userRegister").hide();
    $(".userRetrievePas").hide();
});

$(".registerBtn").click(function(){
    $(".userRetrievePas").hide();
    $(".userLogin").hide();
    $(".userRegister").show();
});

$(".forgotPasBtn").click(function(){
    $(".userRetrievePas").show();
    $(".userLogin").hide();
    $(".userRegister").hide();
});

$(".userLogin .close.icon").click(function(){
    $(".bg").hide();
    $(".login").hide();
});

$(function(){
    menuInit();
});

//登陆页面的拖拽功能实现代码
window.onload=function() {
    var dialog = document.getElementById("loginPage");
    var diatitle1 = document.getElementById("title1");
    var diatitle2 = document.getElementById("title2");
    var diatitle3 = document.getElementById("title3");
    var isDraging = false; //是否可拖拽的标记
    diatitle1.onmousedown = down;
    diatitle2.onmousedown = down;
    diatitle3.onmousedown = down;
    document.onmousemove = move;
    document.onmouseup = up;

    //登陆层居中
    autoCenter();

    function autoCenter() {
        var bodyW = document.documentElement.clientWidth;
        var bodyH = document.documentElement.clientHeight;
        var elW = dialog.offsetWidth;
        var elH = dialog.offsetHeight;
        dialog.style.left = ((bodyW - elW) / 2 - 190) + 'px';
        dialog.style.top = ((bodyH - elH) / 2 - 190) + 'px';
    }

    //按下
    function down() {
        diatitle1.style.cursor = "move";
        diatitle2.style.cursor = "move";
        diatitle3.style.cursor = "move";
        isDraging = true;
        objleft = dialog.offsetLeft;
        objtop = dialog.offsetTop;
        posX = parseInt(mousePosition(event).x)
        posY = parseInt(mousePosition(event).y);
        offsetX = posX - objleft;
        offsetY = posY - objtop;
    }

    //移动
    function move(event) {
        if (isDraging == true) {
            var x = mousePosition(event).x - offsetX;
            var y = mousePosition(event).y - offsetY;
            var w = document.documentElement.clientWidth - dialog.offsetWidth;
            var h = document.documentElement.clientHeight - dialog.offsetHeight;
            x = Math.min(w, Math.max(0, x));
            y = Math.min(h, Math.max(0, y));
            dialog.style.left = x + 'px';
            dialog.style.top = y + 'px';
        }
    }

    //松开
    function up() {
        isDraging = false;
    }

    function mousePosition(evt) {
        var xPos, yPos;
        evt = evt || window.event;
        if (evt.pageX) {
            xPos = evt.pageX;
            yPos = evt.pageY;
        } else {
            xPos = evt.clientX + document.body.scrollLeft - document.body.clientLeft;
            yPos = evt.clientY + document.body.scrollTop - document.body.clientTop;
        }
        return {
            x: xPos,
            y: yPos
        }
    }
}
