/**
 * 点击事件
 */
$(document).on('click','.isLogin',function(){
    var isLogin=$("#isAuthenticated").val();
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();

        autoCenter();
    }else{
        window.open("/user/userCenter");
    }
});

/**
 * 发送邮箱验证码
 */
function sendemail() {
    var obj = new Object();
    obj.tos = $("#registerUserEmail").val();
    if(obj.tos == null || obj.tos == '') {
        $("#registerUserEmail").parents(".field").siblings(".error").find(".transition").text("电子邮箱不能为空");
        $("#registerUserEmail").parents(".field").siblings(".error").show();
        return;
    }
    if(1 == vailEmail($("#registerUserEmail"), obj.tos)){
        return;
    }
    $.ajax({
        url: basePath + 'toEmail',
        type: "POST",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            var obj = $("#sendBtn");
            settime(obj);
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
    /**
     * 是否为空验证
     * @type {number}
     */
    var traverse = 0;
    $("#registerForm .required").each(function(index,result){
        var value = $(this).val();
        if(value == null || value == ''){
            $(this).parents(".field").siblings(".error").show();
            traverse += 1;
        }else{
            if($(this).attr("id") == 'registerUserEmail'){
                traverse += vailEmail(this,value);
                traverse += validationEmail(value);
            }else {
                $(this).parents(".field").siblings(".error").hide();
            }
        }
    });
    var obj = new Object();
    obj.nickname = $("#registerNickName").val();     //  用户昵称
    obj.email = $("#registerUserEmail").val();       //  用户邮箱
    obj.password = $("#registerUserPwd").val();      //  密码
    obj.vCode = $("#code").val();                    //  邮箱验证码
    if(traverse == 0){
        $.ajax({
            url: basePath + 'registerUser',
            type: "POST",
            async: false,
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (resultData) {
                if(resultData.code == 0){
                    location.reload();
                }else{
                    cocoMessage.error(resultData.msg);
                }
            },
            error: function () {

            }
        });
    }
});

function validationEmail(newEmail) {
    var num = 0;
    $.ajax({
        url: basePath + "validationEmail?newEmail=" + newEmail,
        type:"POST",
        async: false,
        success:function(resultData){
            if(resultData.code!=0){
                $("#sendBtn").removeAttr('onclick');
                $("#sendBtn").removeAttr('style');
                $("#registerUserEmail").parents(".field").siblings(".error").find(".transition").text(resultData.msg);
                $("#registerUserEmail").parents(".field").siblings(".error").show();
                num = 1;
            }else {
                $("#sendBtn").attr('onclick', 'sendemail();');
                $("#sendBtn").attr("style", "cursor: url('../../img/cur/link.cur'), pointer;color: white;background-color: #2185d0!important;");
                $("#registerUserEmail").parents(".field").siblings(".error").hide();
                num = 0;
            }
        }
    });
    return num;
}

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

    var obj = new Object();
    obj.email = $("#userEmail").val();
    obj.password = $("#userPwd").val();
    if(traverse == 0){
        $.ajax({
            url: basePath + 'login',
            type: "POST",
            data: JSON.stringify(obj),
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (resultData) {
                if(resultData.code == 0){
                    localRefresh('/common/headerRefresh', '#header');
                    $(".login").hide();
                    $(".bg").hide();
                }else{
                    cocoMessage.error(resultData.msg);
                }
            },
            error: function () {

            }
        });
    }
});

$(document).on('click','.adminlogin',function(){
    $(".bg").show();
    $(".login").show();
    $(".userLogin").show();
    $(".userRegister").hide();
    $(".userRetrievePas").hide();

    autoCenter();
});

$(document).on('click','.registerlogin',function(){
    $(".bg").show();
    $(".login").show();
    $(".userLogin").hide();
    $(".userRegister").show();
    $(".userRetrievePas").hide();

    autoCenter();
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

$("#registerUserEmail").blur(function() {
    var value = $(this).val();
    if(value != null && value != '') {
        if(1 == vailEmail(this, value)) {
            $("#sendBtn").removeAttr('onclick');
            $("#sendBtn").removeAttr('style');
        }else {
            validationEmail(value);
        }
    }else {
        $("#sendBtn").removeAttr('onclick');
        $("#sendBtn").removeAttr('style');
    }
});

$(".userLogin .close.icon, .userRegister .close.icon, .userRetrievePas .close.icon").click(function(){
    $(".bg").hide();
    $(".login").hide();
});

/**
 * 第三方账号登录
 * @param loginType
 */
function thirdPartyLogin(loginType) {
    if(loginType == 'QQ') {
        window.open('/login/qq', 'QQ登录', 'left=0,top=0,width=' + (screen.availWidth - 10) + ',height=' + (screen.availHeight - 55) + ',toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
    }
}

$(function(){
    menuInit();
});

function autoCenter() {

    diatitle1.onmousedown = down;
    diatitle2.onmousedown = down;
    diatitle3.onmousedown = down;
    document.onmousemove = move;
    document.onmouseup = up;

    var bodyW = document.documentElement.clientWidth;
    var bodyH = document.documentElement.clientHeight;
    var elW = 0;
    var elH = 0;
    dialog.style.left = ((bodyW - elW) / 2 - 175) + 'px';
    dialog.style.top = ((bodyH - elH) / 2 - 230) + 'px';
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

function custom_close(type, result) {
    if(type == 'bind') {
        if(result.code == '0') {
            queryUserAuthsInfoByYhnm();
            cocoMessage.success(result.msg, 3000);
        }else {
            cocoMessage.error(result.msg, 3000);
        }
    }else if(type == 'login') {
        if(result.code == '0') {
            localRefresh('/common/headerRefresh', '#header');
            $(".login").hide();
            $(".bg").hide();
        }else {
            cocoMessage.error(result.msg, 3000);
        }
    }
}