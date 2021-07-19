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

$("#save").on('click', function() {
    var area = $("#province").children(".active").attr("data-value") + ',' + $("#city").children(".active").attr("data-value") + ',' + $("#area").children(".active").attr("data-value");
    var paramDate = new Object();
    paramDate.sex = $("input[name='sex']:checked").val();                 //  性别
    paramDate.nickname = $("#nickname").val();                            //  用户昵称
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
            $(this).siblings(".error").hide();
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
                $(elem).siblings(".error").text(resultData.msg);
                $(elem).siblings(".error").show();
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
        $(elem).siblings(".error").text("请输入8~20位字母和数字组合的密码");
        $(elem).siblings(".error").show();
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
        $(elem).siblings(".error").text("与输入的新密码不一致");
        $(elem).siblings(".error").show();
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
                $(this).siblings(".error").text("请输入旧密码");
            }else if($(this).attr("id") == 'newPwd') {
                $(this).siblings(".error").text("请输入新密码");
            }else if($(this).attr("id") == 'confirmNewPwd') {
                $(this).siblings(".error").text("请确认新密码");
            }
            $(this).attr("style", "width: 280px;border-style:solid;border-color:red;");
            $(this).siblings(".error").show();
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
            $(this).siblings(".error").hide();
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
        $(elem).siblings(".error").text("邮箱格式错误");
        $(elem).siblings(".error").show();
        $("#getCode").attr("style", "color: darkgray;width: 110px;text-align: center;");
        $("#getCode").text("获取验证码");
        $("#getCode").removeAttr('onclick');
        falg = 1;
    }else {
        $.ajax({
            url: basePath + "user/validationEmail?newEmail=" + value,
            type:"POST",
            asyns:false,
            success:function(resultData){
                if(resultData.code!=0){
                    $(elem).attr("style", "width: 280px;border-style:solid;border-color:red;");
                    $(elem).siblings(".error").text(resultData.msg);
                    $(elem).siblings(".error").show();
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
                htmlStr += '<div>未查询到文章信息</div>';
                $("#page").hide();
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
    layer.confirm("确定要删除评论信息吗？", {
        btn: ["确定","取消"] //按钮
        , skin: 'layui-layer-lan'
        , closeBtn: 0
        , icon: 3
    }, function(index){
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
    }, function(){

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