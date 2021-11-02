var countdown = 60;


layui.use(['form', 'layedit', 'laydate', 'upload'],function() {
    var form = layui.form,
        layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate,
        upload = layui.upload;

    //执行一个laydate实例
    laydate.render({
        elem: '#birthday' //指定元素
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#workTime' //指定元素
    });
});

$('select.dropdown').dropdown();

$('.ui.radio.checkbox').checkbox();

//头像框鼠标移入事件
$(".widget_avatar_head").mouseover(function() {
   $(".avatar-hover").show();
});

//头像框鼠标移出事件
$(".widget_avatar_head").mouseout(function() {
    $(".avatar-hover").hide();
});

//调用头像上传页面
$(".widget_avatar_head").on("click", function() {
    $(".bg").show();
    $("#avatar-modal").show();
});

$("#hybrid").mouseover(function() {
    $("#userMenu").show();
});

$("#hybrid").mouseout(function() {
    $("#userMenu").hide();
});

$(".dataList").mouseover(function() {
    $(".edit-icon").show();
});

$(".dataList").mouseout(function() {
    $(".edit-icon").hide();
});

$(".dataList").on('click', function() {
    $(".editUser").show();
    $(".dataList").hide();
});

$("#cancel").on('click', function() {
    $(".editUser").hide();
    $(".dataList").show();
});

$('#province').on('click', function (){
    var code = $(this).children(".active").attr("data-value");
    getCityByProvinceCode(code);
});

$('#city').on('click', function (){
    var code = $(this).children(".active").attr("data-value");
    getAreaByCityCode(code);
});

$("body").delegate("#nameClick","click", function(){
    $(".collection-font").hide();
    $(".collectionNameInput").show();
});

$("body").delegate("#desClick","click", function(){
    $(".collection-desc").hide();
    $(".collectionDesInput").show();
});

$("body").delegate("#nameClose","click", function(){
    $(".collection-font").show();
    $(".collectionNameInput").hide();
});

$("body").delegate("#desClose","click", function(){
    $(".collection-desc").show();
    $(".collectionDesInput").hide();
});

$("body").delegate(".menu-item","click", function(){
    if($(this).hasClass("menu-item-new")){
        $(".bg").show();
        $(".addCollection").show();
        $("#collection-box1").hide();
        $("#collection-box2").show();
        $("#collection-box3").hide();
    }else {
        $(this).siblings().removeClass("active-menu-item");
        $(this).addClass("active-menu-item");
    }
});

$(".personData span").on('click', function() {
    switchTab(this, 'myProfileTab', 2);
});

$(".return").on('click', function() {
    switchTab(this, 'myAccountTab', 2);
    $(".loginRecord").hide();
});

/**
 * 个人资料信息保存点击事件
 */
$("#save").on('click', function() {
    var area = $("#province").children(".active").attr("data-value") + ',' + $("#city").children(".active").attr("data-value") + ',' + $("#area").children(".active").attr("data-value");
    var paramDate = new Object();
    paramDate.sex = $("input[name='sex']:checked").val();                 //  性别
    paramDate.nickName = $("#nickname").val();                            //  用户昵称
    paramDate.realName = $("#realName").val();                            //  真实姓名
    paramDate.area = area;                                                //  所在地区
    paramDate.birthday = $("#birthday").val();                            //  出生日期
    paramDate.workTime = $("#workTime").val();                            //  开始工作日期
    paramDate.professional = $("#professional").val();                    //  职业
    paramDate.company = $("#company").val();                              //  公司
    paramDate.signature = $("#signature").val();                          //  个性签名
    $.ajax({
        url: basePath + "user/updatePersonalData",
        type:"POST",
        data:JSON.stringify(paramDate),
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            if(resultData.code==0){
                cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                queryPersonalData();
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
        }
    });
});

/**
 * 修改密码输入框失焦事件
 */
$("#updatePwdForm .required").blur(function(){
    var value = $(this).val();
    if(value != null && value != ''){
        var falg = 0;
        if($(this).attr("id") == 'oldPwd'){
            falg = validationOldPwd(this, value);
        }else if($(this).attr("id") == 'newPwd') {
            falg = validationNewPwd(this, value);
        }else if($(this).attr("id") == 'confirmNewPwd') {
            falg = validationConfirmNewPwd(this, value);
        }
        if(falg == 0){
            $(this).attr("style", "width: 280px;");
            $(this).parents(".input").siblings(".error").hide();
        }
    }
});

/**
 * 校验旧密码
 * @param elem
 * @param oldPwd
 * @returns {number}
 */
function validationOldPwd(elem, oldPwd) {
    var falg = 0;
    $.ajax({
        url: basePath + "user/validationPwd?oldPwd=" + oldPwd,
        type:"POST",
        asyns:false,
        success:function(resultData){
            if(resultData.code!=0){
                $(elem).attr("style", "width: 280px;border-style:solid;border-color:red;");
                $(elem).parents(".input").siblings(".error").text(resultData.msg);
                $(elem).parents(".input").siblings(".error").show();
                falg = 1;
            }else {
                falg = 0;
            }
        }
    });
    return falg;
}

/**
 * 校验新密码
 * @param elem
 * @param newPwd
 * @returns {number}
 */
function validationNewPwd(elem, newPwd) {
    var patrn=/^(\w){8,20}$/;
    if (!patrn.exec(newPwd)) {
        $(elem).attr("style", "width: 280px;border-style:solid;border-color:red;");
        $(elem).parents(".input").siblings(".error").text("请输入8~20位字母和数字组合的密码");
        $(elem).parents(".input").siblings(".error").show();
        return 1;
    }
    return 0;
}

/**
 * 校验新密码与确认新密码是否一致
 * @param elem
 * @param confirmNewPwd
 * @returns {number}
 */
function validationConfirmNewPwd(elem, confirmNewPwd) {
    var newPwd = $("#newPwd").val();
    if(newPwd != confirmNewPwd){
        $(elem).attr("style", "width: 280px;border-style:solid;border-color:red;");
        $(elem).parents(".input").siblings(".error").text("与输入的新密码不一致");
        $(elem).parents(".input").siblings(".error").show();
        return 1;
    }
    return 0;
}

//修改密码
$("#updatePwd").click(function() {
    var oldPwd = $("#oldPwd").val();
    var newPwd = $("#newPwd").val();
    //var confirmNewPwd = $("#confirmNewPwd").val();

    var traverse = 0;
    $("#updatePwdForm .required").each(function(){
        var value = $(this).val();
        if(value == null || value == ''){
            if($(this).attr("id") == 'oldPwd'){
                $(this).parents(".input").siblings(".error").text("请输入旧密码");
            }else if($(this).attr("id") == 'newPwd') {
                $(this).parents(".input").siblings(".error").text("请输入新密码");
            }else if($(this).attr("id") == 'confirmNewPwd') {
                $(this).parents(".input").siblings(".error").text("请确认新密码");
            }
            $(this).attr("style", "width: 280px;border-style:solid;border-color:red;");
            $(this).parents(".input").siblings(".error").show();
            traverse += 1;
        }else{
            if($(this).attr("id") == 'oldPwd'){
                traverse += validationOldPwd(this, value);
            }else if($(this).attr("id") == 'newPwd') {
                traverse += validationNewPwd(this, value);
            }else if($(this).attr("id") == 'confirmNewPwd') {
                traverse += validationConfirmNewPwd(this, value);
            }
        }
    });
    if(traverse == 0){
        $.ajax({
            url: basePath + "user/updatePwd?oldPwd=" + oldPwd + "&newPwd=" + newPwd,
            type:"POST",
            success:function(resultData){
                if(resultData.code==0){
                    cocoMessage.success("密码修改成功！将在3秒钟后退出系统。。。", 3000); //duration为0时显示关闭按钮
                    setTimeout(function (){
                        loginOut();
                    }, 3000);
                }else{
                    cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                }
            }
        });
    }
});

/**
 * 绑定邮箱输入框失焦事件
 */
$("#bindingEmailForm .required").blur(function(){
    var value = $(this).val();
    if(value != null && value != ''){
        var falg = 0;
        if($(this).attr("id") == 'newEmail'){
            falg = vailEmail(this, value);
        }
        if(falg == 0){
            //$(this).attr("style", "width: 280px;");
            $(this).parents(".input").siblings(".error").hide();
        }
    }
});

/**
 * 验证邮箱格式
 * @param elem
 * @param value
 * @returns {number}
 */
function vailEmail(elem,value){
    var falg = 0;

    var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if(!myreg.test(value)){
        $(elem).attr("style", "width: 280px;border-style:solid;border-color:red;");
        $(elem).parents(".input").siblings(".error").text("邮箱格式错误");
        $(elem).parents(".input").siblings(".error").show();
        $("#getCode").attr("style", "color: darkgray;width: 110px;text-align: center;");
        $("#getCode").text("获取验证码");
        $("#getCode").removeAttr('onclick');
        falg = 1;
    }else {
        $.ajax({
            url: basePath + "validationEmail?newEmail=" + value,
            type:"POST",
            async: false,
            success:function(resultData){
                if(resultData.code!=0){
                    $(elem).attr("style", "width: 280px;border-style:solid;border-color:red;");
                    $(elem).parents(".input").siblings(".error").text(resultData.msg);
                    $(elem).parents(".input").siblings(".error").show();
                    $("#getCode").attr("style", "color: darkgray;width: 110px;text-align: center;");
                    $("#getCode").text("获取验证码");
                    $("#getCode").removeAttr('onclick');
                    falg = 1;
                }else {
                    $("#getCode").attr('onclick', 'sendemail();');
                    $("#getCode").attr("style", "cursor: url('../../img/cur/link.cur'), pointer;color: white;background-color: #2185d0!important;width: 110px;text-align: center;");
                    falg = 0;
                }
            }
        });

    }
    return falg;
}

/**
 * 发送邮箱验证码
 */
function sendemail() {
    var obj = new Object();
    obj.tos = $(".newEmail").val();
    $.ajax({
        url: basePath + 'toEmail',
        type: "POST",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            var obj = $("#getCode");
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
 * 查询我的评论列表
 * @param page
 * @param limit
 */
function queryMyCommentList(page, limit){
    var obj = new Object();
    obj.page = page;
    obj.limit = limit;
    $.ajax({
        url: basePath + 'user/queryMyCommentsByYhnm',
        type: "POST",
        data:JSON.stringify(obj),
        dataType:"json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            var data = resultData.data;
            var htmlStr = '';
            layui.use(['laypage', 'layer'], function () {
                var page = layui.laypage;
                page.render({
                    elem: 'commentPage',
                    count: resultData.count,
                    curr: obj.page,
                    limit: obj.limit,
                    jump: function (obj, first) {
                        if (!first) {
                            queryMyCommentList(obj.curr, obj.limit)
                        }
                    }
                });
            });
            if(resultData.code == '0'){
                for(var i = 0;i < data.length;i++){
                    htmlStr += '<li class="msg-item cursor-active msg-unread">';
                    htmlStr += '    <div class="msg-item-logo">';
                    htmlStr += '        <img src="/profile/' + data[i].icon + '" class="user-logo">';
                    htmlStr += '    </div>';
                    htmlStr += '    <div class="msg-item-content">';
                    htmlStr += '        <span class="msg-title item-title">';
                    if(data[i].commentType == '1'){
                        htmlStr += '        <a href="/blog/detail/' + data[i].wznm + '" target="_blank">' + data[i].articleName + '</a>';
                    }
                    if(data[i].commentType == '2'){
                        htmlStr += '        <a href="/message/messageFeedback">' + data[i].articleName + '</a>';
                    }
                    htmlStr += '        </span>';
                    htmlStr += '        <a class="btn-rush csdnc-trash" style="float: right;"><i id="delComment" class="trash alternate icon" onclick="delComment(\'' + data[i].commentId + '\')"></i></a>';
                    htmlStr += '        <div class="msg-text clearfix comment-text">';
                    htmlStr += '            <div class="comment-content">';
                    if(data[i].commentType == '2' && (data[i].replyName == '' || data[i].replyName == null)){
                        htmlStr += '            <p class="comment-content-title">您评论了该网站</p>';
                    }else if(data[i].commentType == '2' && data[i].replyName != '' && data[i].replyName != null) {
                        htmlStr += '            <p class="comment-content-title">您回复了<a>' + data[i].replyName + '</a>的留言</p>';
                    }else if(data[i].commentType == '1' && (data[i].replyName == '' || data[i].replyName == null)) {
                        htmlStr += '            <p class="comment-content-title">您评论了该文章</p>';
                    }else if(data[i].commentType == '1' && data[i].replyName != '' && data[i].replyName != null) {
                        htmlStr += '            <p class="comment-content-title">您回复了<a>' + data[i].replyName + '</a>的评论</p>';
                    }
                    htmlStr += '                <p class="bb-span-wrap comment-content-detail">' + data[i].comments + '</p>';
                    htmlStr += '            </div>';
                    htmlStr += '            <em class="fr">' + data[i].commentTime + '</em>';
                    htmlStr += '        </div>';
                    htmlStr += '    </div> ';
                    htmlStr += '</li>';
                }
            }else {
                htmlStr += "   <div style='margin-top: 20px;text-align: center;'>";
                htmlStr += "       <img style='width: 200px;' src='/img/noData.png'><br/><br/>";
                htmlStr += "       <span style='color: darkcyan;'>您还没有评论哦~</span>";
                htmlStr += "   <div>";
                $("#commentPage").hide();
            }
            $(".commentList").html(htmlStr);
        },
        error: function () {

        }
    });
}

/**
 * 查询我的友链申请
 * @param linkStatus
 */
function queryMyLinks(linkStatus) {
    $.ajax({
        url: basePath + 'user/queryMyLinks?linkStatus=' + linkStatus,
        type: "POST",
        success: function (resultData) {
            var data = resultData.data;
            var htmlStr = '';
            if(resultData.code == '0'){
                $("#noLinks").hide();
                $("#linkTable").show();
                for(var i = 0;i < data.length;i++){
                    htmlStr += '<tr>';
                    htmlStr += '    <td>' + data[i].linkName + '</td>';
                    htmlStr += '    <td>' + data[i].linkUrl + '</td>';
                    htmlStr += '    <td>' + data[i].linkStatus + '</td>';
                    htmlStr += '    <td>' + (null == data[i].feedbackMsg?"暂无":data[i].feedbackMsg) + '</td>';
                    htmlStr += '    <td><a class="btn btn-success btn-xs" onclick="editMyLink(' + data[i].linkId + ')"><i class="fa fa-edit"></i> 更新友链</a></td>';
                    htmlStr += '</tr>';
                }
            }else {
                $("#noLinks").show();
                $("#linkTable").hide();
            }
            $(".linkList").html(htmlStr);
        },
        error: function () {

        }
    });
}

/**
 * 删除评论信息
 * @param commentId
 */
function delComment(commentId) {
    var lock = false; //默认未锁定
    confirm({
        title: '提示',
        content: '确定要删除评论信息吗？'
    }).then(() => {
        if(!lock) {
            lock = true;
            $.ajax({
                url:basePath + "user/delMyComment?commentId=" + commentId,
                type:"POST",
                success:function(resultData){
                    if(resultData.code==0){
                        cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                        queryMyCommentList(1, 5);
                    }else{
                        cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                    }
                    layer.close(index);//关闭当前页
                }
            });
        }
    }).catch(() => {

    });
}

/**
 * 编辑友链
 * @param linkId
 */
function editMyLink(linkId) {
    $.ajax({
        url:basePath + "user/queryMyLinksById?linkId=" + linkId,
        type:"POST",
        success:function(resultData){
            if(resultData.code==0){
                $("#linkTitle").text("友链更新");
                $(".bg").show();
                $("#friendLinkApply").show();
                $(".linkApply").show();

                $("#linkId").val(resultData.obj.linkId);
                $("#webSiteName").val(resultData.obj.linkName);
                $("#webSiteUrl").val(resultData.obj.linkUrl);
                $("#editor").html(resultData.obj.intro);
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
            layer.close(index);//关闭当前页
        }
    });
}

/**
 * 查询个人资料详情
 */
function queryPersonalData(){
    $.ajax({
        url:basePath + "user/queryPersonalData",
        type:"POST",
        success:function(resultData){
            if(resultData.code==0){
                $(".nickname").text(resultData.obj.nickName);
                $(".nickname").val(resultData.obj.nickName);
                if(resultData.obj.realName == null || resultData.obj.realName == ''){
                    $(".realName").addClass("no-data");
                    $(".realName").text("未填写");
                }else {
                    $(".realName").removeClass("no-data");
                    $(".realName").text(resultData.obj.realName);
                    $(".realName").val(resultData.obj.realName);
                }
                if(resultData.obj.sex == null || resultData.obj.sex == ''){
                    $(".sex").html('<i class="mars icon" style="color: blue;font-weight: bold"></i>');
                }else if(resultData.obj.sex == 0){
                    $(".sex").html('<i class="mars icon" style="color: blue;font-weight: bold"></i>');
                }else if(resultData.obj.sex == 1){
                    $(".sex").html('<i class="venus icon" style="color: red;font-weight: bold"></i>');
                }
                if(resultData.obj.area == null || resultData.obj.area == ''){
                    $(".area").addClass("no-data");
                    $(".area").text('未选择');
                }else {
                    $(".area").removeClass("no-data");
                    $(".area").text(resultData.obj.area);
                }
                if(resultData.obj.provinceCode != null && resultData.obj.provinceCode != ''){
                    $("#province .item").each(function() {
                        var province = $(this).attr("data-value");
                        if(province == resultData.obj.provinceCode){
                            //$(this).click();
                            $(this).addClass("active");
                            $(this).addClass("selected");
                            $("#provinceInput").val(province);
                            $("#provinceText").text($(this).text());
                            $("#provinceText").removeClass("default");
                        }
                    });
                }
                if(resultData.obj.cityCode != null && resultData.obj.cityCode != ''){
                    $.ajax({
                        url:basePath + "common/getCityByProvinceCode?code=" + resultData.obj.provinceCode,
                        type:"POST",
                        success:function(result){
                            if(result.code==0){
                                var htmlStr = '';
                                var data = result.data;
                                for(var i = 0; i < data.length; i++){
                                    htmlStr += '<div class="item" data-value="' +data[i].regionCode  + '">' + data[i].regionName + '</div>';
                                }
                                $("#city").html(htmlStr);
                                $("#city .item").each(function() {
                                    var cityCode = $(this).attr("data-value");
                                    if(cityCode == resultData.obj.cityCode){
                                        //$(this).click();
                                        $(this).addClass("active");
                                        $(this).addClass("selected");
                                        $("#cityInput").val(cityCode);
                                        $("#cityText").text($(this).text());
                                        $("#cityText").removeClass("default");
                                        console.log($("#cityText").text());
                                    }
                                });
                            }
                        }
                    });
                }
                if(resultData.obj.areaCode != null && resultData.obj.areaCode != ''){
                    $.ajax({
                        url:basePath + "common/getAreaByCityCode?code=" + resultData.obj.cityCode,
                        type:"POST",
                        success:function(result){
                            if(result.code==0){
                                var htmlStr = '';
                                var data = result.data;
                                for(var i = 0; i < data.length; i++){
                                    htmlStr += '<div class="item" data-value="' +data[i].regionCode  + '">' + data[i].regionName + '</div>';
                                }
                                $("#area").html(htmlStr);
                                $("#area .item").each(function() {
                                    var areaCode = $(this).attr("data-value");
                                    if(areaCode == resultData.obj.areaCode){
                                        $(this).addClass("active");
                                        $(this).addClass("selected");
                                        $("#areaInput").val(areaCode);
                                        $("#areaText").text($(this).text());
                                        $("#areaText").removeClass("default");
                                        console.log($("#areaText").text());
                                    }
                                });
                            }
                        }
                    });
                }
                if(resultData.obj.birthday == null || resultData.obj.birthday == ''){
                    $(".birthday").addClass("no-data");
                    $(".birthday").text('未设置');
                }else {
                    $(".birthday").removeClass("no-data");
                    $(".birthday").text(resultData.obj.birthday);
                    $(".birthday").val(resultData.obj.birthday);
                }
                if(resultData.obj.workTime == null || resultData.obj.workTime == ''){
                    $(".workTime").addClass("no-data");
                    $(".workTime").text('未选择');
                }else {
                    $(".workTime").removeClass("no-data");
                    $(".workTime").text(resultData.obj.workTime);
                    $(".workTime").val(resultData.obj.workTime);
                }
                if(resultData.obj.professional == null || resultData.obj.professional == ''){
                    $(".professional").addClass("no-data");
                    $(".professional").text('未填写');
                }else {
                    $(".professional").removeClass("no-data");
                    $(".professional").text(resultData.obj.professional);
                    $(".professional").val(resultData.obj.professional);
                }
                if(resultData.obj.company == null || resultData.obj.company == ''){
                    $(".company").addClass("no-data");
                    $(".company").text('未填写');
                }else {
                    $(".company").removeClass("no-data");
                    $(".company").text(resultData.obj.company);
                    $(".company").val(resultData.obj.company);
                }
                if(resultData.obj.signature == null || resultData.obj.signature == ''){
                    $(".signature").addClass("no-data");
                    $(".signature").text('未填写');
                }else {
                    $(".signature").removeClass("no-data");
                    $(".signature").text(resultData.obj.signature);
                    $(".signature").val(resultData.obj.signature);
                }
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
        }
    });
}

/**
 * 查询省级下拉框
 */
function getProvince() {
    $.ajax({
        url:basePath + "common/getProvince",
        type:"POST",
        success:function(resultData){
            if(resultData.code==0){
                var htmlStr = '';
                var data = resultData.data;
                for(var i = 0; i < data.length; i++){
                    htmlStr += '<div class="item" data-value="' +data[i].regionCode  + '">' + data[i].regionName + '</div>';
                }
                $("#province").html(htmlStr);
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
        }
    });
}

/**
 * 查询市级下拉框
 * @param code
 */
function getCityByProvinceCode(code) {
    $.ajax({
        url:basePath + "common/getCityByProvinceCode?code=" + code,
        type:"POST",
        asyns:false,
        success:function(resultData){
            if(resultData.code==0){
                var htmlStr = '';
                var data = resultData.data;
                for(var i = 0; i < data.length; i++){
                    htmlStr += '<div class="item" data-value="' +data[i].regionCode  + '">' + data[i].regionName + '</div>';
                }
                $("#city").html(htmlStr);
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
            console.log("你进来了没？");
        }
    });
}

/**
 * 查询区县下拉框
 * @param code
 */
function getAreaByCityCode(code) {
    $.ajax({
        url:basePath + "common/getAreaByCityCode?code=" + code,
        type:"POST",
        asyns:false,
        success:function(resultData){
            if(resultData.code==0){
                var htmlStr = '';
                var data = resultData.data;
                for(var i = 0; i < data.length; i++){
                    htmlStr += '<div class="item" data-value="' +data[i].regionCode  + '">' + data[i].regionName + '</div>';
                }
                $("#area").html(htmlStr);
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
        }
    });
}

/**
 * 初始化收藏文件夹
 */
function initCollection(){
    $.ajax({
        url: basePath + "myCollection/queryFavoriteInfo",
        type:"POST",
        success:function(resultData){
            if(resultData.code==0){
                var htmlStr = "";
                htmlStr += '<li class="menu-item menu-item-new">';
                htmlStr += '    <div class="imgBox"></div>';
                htmlStr += '    <span>新建收藏夹</span> ';
                htmlStr += '</li>';
                if(resultData.data != null && resultData.data.length > 0) {
                    for(var i = 0;i < resultData.data.length;i++) {
                        var active = "";
                        if(i == 0) {
                            active = ' active-menu-item';
                            showCollectionData(resultData.data[i].id);
                        }
                        htmlStr += '<li class="menu-item' + active + '" onclick="showCollectionData(' + resultData.data[i].id + ')">';
                        htmlStr += '    <div class="menu-item-title">';
                        htmlStr += '        <div class="imgBox"></div>';
                        htmlStr += '        <span class="favoriteSpan">' + resultData.data[i].favoriteName + '</span>';
                        htmlStr += '    </div>';
                        htmlStr += '    <div class="menu-item-like">';
                        htmlStr += '        <span class="comment">';
                        htmlStr += '            <div class="imgBox"></div>';
                        htmlStr += '            <span>' + resultData.data[i].count + '</span>';
                        htmlStr += '        </span>';
                        htmlStr += '    </div>';
                        htmlStr += '</li>';
                    }
                }else {
                    showCollectionData("");
                }
                $(".ultab-sub-menu").html(htmlStr);
            }else{
                cocoMessage.error('系统异常', 3000); //loading可以选择是否展示关闭按钮
            }
        }
    });
}

/**
 * 展示指定收藏夹下的收藏数据
 * @param collectId
 */
function showCollectionData(favoriteId) {
    if(favoriteId != null && favoriteId != '') {
        $.ajax({
            url: basePath + "myCollection/showCollectionData?id=" + favoriteId,
            type:"POST",
            success:function(resultData){
                if(resultData.code==0){
                    var htmlStr = "";
                    htmlStr += '<div class="titleHead">';
                    htmlStr += '    <div class="nameBox">';
                    htmlStr += '        <span class="collection-dir collection-font collection-detail">';
                    htmlStr += '            <span class="collection-folder-name">';
                    htmlStr += '                ' + resultData.obj.favoriteName + '';
                    htmlStr += '                <i class="edit icon" id="nameClick"></i>';
                    htmlStr += '            </span>';
                    htmlStr += '        </span>';
                    htmlStr += '        <div class="input-edit-box collectionNameInput" style="display: none">';
                    htmlStr += '            <div class="collection-edit-name el-input">';
                    htmlStr += '                <input type="text" id="favorite" class="el-input__inner favoriteName" maxlength="20" placeholder="请输入内容" value="" />';
                    htmlStr += '            </div>';
                    htmlStr += '            <img id="nameClose" src="../img/closeIcon.svg">';
                    htmlStr += '            <img id="nameSave" onclick="updateFavorite(' + resultData.obj.id + ', 1)" src="../img/saveIcon.svg">';
                    htmlStr += '        </div>';
                    htmlStr += '        <span class="collection-info collection-detail collection-detail-d">';
                    htmlStr += '            <p class="collection-text collection-detail collection-desc">';
                    htmlStr += '                ' + ((null == resultData.obj.describe || '' == resultData.obj.describe)?'请添加收藏夹描述':resultData.obj.describe) + '';
                    htmlStr += '                <i class="edit icon" id="desClick"></i>';
                    htmlStr += '            </p>';
                    htmlStr += '            <div class="input-edit-box collectionDesInput" style="margin-top: 10px;display: none">';
                    htmlStr += '                <div class="collection-edit-name el-input">';
                    htmlStr += '                    <input type="text" maxlength="200" id="describe" placeholder="请输入内容" class="el-input__inner describe">';
                    htmlStr += '                </div>';
                    htmlStr += '                <img id="desClose" src="../img/closeIcon.svg">';
                    htmlStr += '                <img id="desSave" onclick="updateFavorite(' + resultData.obj.id + ', 2)" src="../img/saveIcon.svg">';
                    htmlStr += '            </div>';
                    htmlStr += '            <p class="collection-edit-box">';
                    htmlStr += '                <em class="cursor" onclick="deleteFavoriteInfo(' + resultData.obj.id + ', ' + resultData.obj.collLists.length + ')" style="cursor:url(\'../../img/cur/link.cur\'), pointer">删除收藏夹</em>';
                    htmlStr += '            </p>';
                    htmlStr += '        </span>';
                    htmlStr += '    </div> ';
                    htmlStr += '</div> ';
                    htmlStr += '<div class="tab-content-box">';
                    if(null != resultData.obj.collLists && 0 == resultData.obj.collLists.length) {
                        htmlStr += "   <div style='margin-top: 20px;text-align: center;'>";
                        htmlStr += "       <img style='width: 200px;' src='/img/noData.png'><br/><br/>";
                        htmlStr += "       <span style='color: darkcyan;'>您还没有添加收藏哦~</span>";
                        htmlStr += "   <div>";
                    }else {
                        htmlStr += '    <ul class="collection-sublist">';
                        for(var i = 0;i < resultData.obj.collLists.length;i++) {
                            htmlStr += '        <li>';
                            htmlStr += '            <div class="collection-con">';
                            htmlStr += '                <span>';
                            htmlStr += '                    <span class="collection-dir">';
                            htmlStr += '                        <em class="conllection-type">BLOG</em>';
                            htmlStr += '                        <span class="subtitle"><a href="' + basePath + 'blog/detail/' + resultData.obj.collLists[i].wznm + '" target="_blank">' + resultData.obj.collLists[i].articleTitle + '</a></span>';
                            htmlStr += '                    </span>';
                            htmlStr += '                </span>';
                            htmlStr += '            </div>';
                            htmlStr += '            <div class="collect-detail-right">';
                            htmlStr += '                <a class="collect-cancel" onclick="deleteCollectionData(' + resultData.obj.collLists[i].collectionId + ', ' + resultData.obj.id + ')"><i class="star outline icon" style="font-size: 18px;color: #F46036;font-weight: bold;"></i></a>';
                            htmlStr += '            </div>';
                            htmlStr += '        </li>';
                        }
                        htmlStr += '    </ul>';
                    }
                    htmlStr += '</div>';
                    $(".list-wrap").html(htmlStr);
                }else{
                    cocoMessage.error('系统异常', 3000); //loading可以选择是否展示关闭按钮
                }
            }
        });
    }else {
        var htmlStr = "<div style='margin-top: 20px;text-align: center;'>";
        htmlStr += "       <img style='width: 200px;' src='/img/noData.png'><br/><br/>";
        htmlStr += "       <span style='color: darkcyan;'>您还没有新建收藏夹哦~</span>";
        htmlStr += "   <div>";
        $(".list-wrap").html(htmlStr);
    }
}

/**
 * 删除文件夹信息
 * @param favoriteId
 */
function deleteFavoriteInfo(favoriteId, count) {
    if(count > 0){
        getFavorite();
        $(".bg").show();
        $(".addCollection").show();
        $("#collection-box1").hide();
        $("#collection-box2").hide();
        $("#collection-box3").show();

        $("#favoriteId").val(favoriteId);
    }else {
        confirm({
            title: '提示',
            content: '是否要删除该文件夹？'
        }).then(() => {
            $.ajax({
                url:basePath + "myCollection/deleteFavoriteInfo?id=" + favoriteId,
                type:"POST",
                success:function(resultData){
                    if(resultData.code==0){
                        cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                        initCollection();
                    }else{
                        cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                    }
                }
            });
        }).catch(() => {

        });
    }
}

/**
 * 获取文件夹下拉框列表
 */
function getFavorite() {
    $.ajax({
        url:basePath + "myCollection/queryFavoriteInfo",
        type:"POST",
        success:function(resultData){
            if(resultData.code==0){
                var htmlStr = '';
                var data = resultData.data;
                for(var i = 0; i < data.length; i++){
                    htmlStr += '<div class="item" data-value="' +data[i].id  + '">' + data[i].favoriteName + '</div>';
                }
                $("#favoriteText").addClass("default");
                $("#favoriteText").text("移动到其他收藏夹");
                $("#favoriteList").html(htmlStr);
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
        }
    });
}

/**
 * 修改收藏夹信息
 * @param favoriteId 收藏夹ID
 * @param type 类型（1.修改名称  2.修改收藏夹描述）
 */
function updateFavorite(favoriteId, type) {
    var favoriteName = $("#favorite").val();
    var describe = $("#describe").val();
    var obj = new Object();
    obj.id = favoriteId;
    obj.favoriteName = favoriteName;
    obj.describe = describe;
    if(type == '1' && (favoriteName == null || favoriteName == '')){
        cocoMessage.error("收藏夹名称不能为空!", 3000);
        return false;
    }
    if(type == '2' && (describe == null || describe == '')){
        cocoMessage.error("收藏夹描述不能为空!", 3000);
        return false;
    }
    $.ajax({
        url:basePath + "myCollection/updateFavoriteInfo?type=" + type,
        type:"POST",
        data:JSON.stringify(obj),
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            if(resultData.code==0){
                cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                showCollectionData(favoriteId);
                if(type == '1') {
                    $(".ultab-sub-menu").find(".active-menu-item").find(".favoriteSpan").text(favoriteName);
                }
            }else{
                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
            }
        }
    });
}

/**
 * 取消收藏
 * @param collectId
 * @param favoriteId
 */
function deleteCollectionData(collectId, favoriteId) {
    confirm({
        title: '提示',
        content: '是否要取消收藏？'
    }).then(() => {
        $.ajax({
            url:basePath + "myCollection/deleteCollectionDataByCollectId?collectionId=" + collectId,
            type:"POST",
            success:function(resultData){
                if(resultData.code==0){
                    cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                    showCollectionData(favoriteId);
                }else{
                    cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                }
            }
        });
    }).catch(() => {

    });
}

/**
 * 查询个人动态信息
 */
function queryDynamicInfo(pageNum, limit) {
    layui.use(['laypage', 'layer', 'flow'], function () {
        var flow = layui.flow;
        flow.load({
            elem: '#acitve' //流加载容器
            ,isAuto: false
            ,isLazyimg: true
            ,done: function(page, next){ //执行下一页的回调
                var paramData = new Object();
                paramData.page = page;
                paramData.limit = limit;
                $.ajax({
                    url:basePath + "user/queryDynamicInfo",
                    type:"POST",
                    asyns: false,
                    data: JSON.stringify(paramData),
                    contentType: 'application/json;charset=utf-8',
                    success:function(resultData){
                        if(resultData.code==0){
                            var data = resultData.data;
                            var total = pageTotal(resultData.count, limit);
                            var lis = [];
                            for(var i = 0;i < data.length;i++) {
                                var htmlStr = "";
                                htmlStr += '<li style="min-height: 55px">';
                                htmlStr += '    <div class="imgbox">';
                                htmlStr += '        <img src="' + data[i].nickIcon + '" />';
                                htmlStr += '    </div>';
                                htmlStr += '    <div class="contentbox">';
                                htmlStr += '        <p>' + data[i].nickName + '</p>';
                                htmlStr += '        <span>' + data[i].operTime + '</span>';
                                if(data[i].dynamicType == '1'){
                                    htmlStr += '    <div class="text">您对文章《' + data[i].articleTitle + '》进行了评论：' + data[i].commentContent + '</div>';
                                }else if(data[i].dynamicType == '2') {
                                    htmlStr += '    <div class="text">在文章《' + data[i].articleTitle + '》中，您对『' + data[i].commentName + '』的评论进行了回复：' + data[i].commentContent + '</div>';
                                }else if(data[i].dynamicType == '3') {
                                    htmlStr += '    <div class="text">在文章《' + data[i].articleTitle + '》中，您对『' + data[i].commentName + '』的评论进行了点赞</div>';
                                }else if(data[i].dynamicType == '4') {
                                    htmlStr += '    <div class="text">在文章《' + data[i].articleTitle + '》中，您对『' + data[i].commentName + '』的评论取消了点赞</div>';
                                }else if(data[i].dynamicType == '5') {
                                    htmlStr += '    <div class="text">您在羅氏博客网站中进行了留言：' + data[i].commentContent + '</div>';
                                }else if(data[i].dynamicType == '6') {
                                    htmlStr += '    <div class="text">您对『' + data[i].commentName + '』的留言进行了回复：' + data[i].commentContent + '</div>';
                                }else if(data[i].dynamicType == '7') {
                                    htmlStr += '    <div class="text">您对『' + data[i].commentName + '』的留言进行了点赞</div>';
                                }else if(data[i].dynamicType == '8') {
                                    htmlStr += '    <div class="text">您对『' + data[i].commentName + '』的留言取消了点赞</div>';
                                }

                                htmlStr += '    </div>';
                                htmlStr += '</li>';
                                lis.push(htmlStr);
                            }
                            next(lis.join(''), page < total); //假设总页数为 6
                        }else{
                            var htmlStr = "<div style='margin-top: 20px;text-align: center;'>";
                            htmlStr += "       <img style='width: 200px;' src='/img/noData.png'><br/><br/>";
                            htmlStr += "       <span style='color: darkcyan;'>暂无个人动态信息哦~</span>";
                            htmlStr += "   <div>";
                            $(".flow-default").html(htmlStr);
                        }
                    }
                });
            }
        });
    });
}

/**
 * 是否已绑定第三方账号
 */
function queryUserAuthsInfoByYhnm() {
    $.ajax({
        url: basePath + 'user/queryUserAuthsInfoByYhnm',
        type: "POST",
        asyns: false,
        success: function (resultData) {
            if(resultData.code == '0'){
                var data = resultData.data;
                $(".bind_list li").each(function() {
                    var $elem = $(this);
                    if(data.length != 0) {
                        for(var i = 0;i < data.length;i++){
                            if($elem.attr("data-type") == data[i].appType) {
                                $elem.find(".handle_text").addClass("remove_text");
                                $elem.find(".handle_text").text("解绑");
                                $elem.find(".handle_text").attr("onClick", "bindUserAuths(" + $elem.attr("data-type") + " ,'unbund', '" + data[i].appUserId + "')");
                            }else {
                                $elem.find(".handle_text").removeClass("remove_text");
                                $elem.find(".handle_text").text("绑定");
                                $elem.find(".handle_text").attr("onClick", "bindUserAuths(" + $elem.attr("data-type") + " ,'bind', '')");
                            }
                        }
                    }else {
                        $elem.find(".handle_text").removeClass("remove_text");
                        $elem.find(".handle_text").text("绑定");
                        $elem.find(".handle_text").attr("onClick", "bindUserAuths(" + $elem.attr("data-type") + " ,'bind', '')");
                    }
                });
            }
        },
        error: function () {

        }
    });
}

/**
 * 第三方账号绑定与解绑
 * @param loginType
 * @param bindType
 * @param openId
 */
function bindUserAuths(loginType, bindType, openId) {
    if(bindType == 'unbund') {
        confirm({
            title: '提示',
            content: '是否确定要解除绑定？'
        }).then(() => {
            $.ajax({
                url: basePath + 'user/unbundUserAuths?openId=' + openId,
                type: "POST",
                asyns: false,
                success: function (resultData) {
                    if(resultData.code == 0) {
                        queryUserAuthsInfoByYhnm();
                        cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                    }else {
                        cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                    }
                },
                error: function () {

                }
            });
        }).catch(() => {

        });
    }else {
        if(loginType == '1') {
            window.open('/api/qq/bindQQ', 'QQ登录', 'left=0,top=0,width=' + (screen.availWidth - 10) + ',height=' + (screen.availHeight - 55) + ',toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
        }else if(loginType == '2') {
            cocoMessage.warning("敬请期待。。。");
        }
    }
}

/**
 * 总页数@param（总条数，每页总条数）
 */
function pageTotal(rowCount, pageSize) {
    console.log("总条数"+rowCount+"每页总条数"+pageSize)
    if (rowCount == null || rowCount == "") {
        return 0;
    } else {
        if (pageSize != 0 &&
            rowCount % pageSize == 0) {
            return parseInt(rowCount / pageSize);
        }
        if (pageSize != 0 &&
            rowCount % pageSize != 0) {
            return parseInt(rowCount / pageSize) + 1;
        }
    }
}

