var offsetX;
var offsetY;
$(function (){
    showFriendLinkInfo();
});

cocoMessage.config({
    //配置全局参数
    duration: 10000,
});

/**
 * 输入框失焦事件
 */
$("#likeForm .required").change(function(){
    var value = $(this).val();
    if(value != null && value != ''){
        var falg = 0;
        if($(this).attr("id") == 'webSiteUrl'){
            falg = vailUrl(this,value);
        }
        if(falg == 0){
            $(this).parents(".field").siblings(".error").hide();
        }
    }
});

$(".ok").click(function(){
    /**
     * 是否为空验证
     * @type {number}
     */
    var traverse = 0;
    $("#likeForm .required").each(function(index,result){
        var value = $(this).val();
        if(value == null || value == ''){
            $(this).parents(".field").siblings(".error").show();
            traverse += 1;
        }else{
            if($(this).attr("id") == 'webSiteUrl'){
                traverse += vailUrl(this,value);
            }
        }
    });
    if(traverse == 0){
        var linkId = $("#linkId").val();
        if(linkId == null || linkId == '') {
            var lock = false; //默认未锁定
            top.layer.confirm("是否确定要提交申请？", {
                btn: ["是","否"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var paramData = {
                        title : $("#webSiteName").val(),
                        url : $("#webSiteUrl").val(),
                        intro : $("#editor").text()
                    }
                    $.ajax({
                        url: basePath + "links/applyLinks",
                        type:"POST",
                        data:JSON.stringify(paramData),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        //设置ajax请求结束后的执行动作
                        complete : function(XMLHttpRequest) {
                            // 通过XMLHttpRequest取得响应头
                            var redirect = XMLHttpRequest.getResponseHeader("isLogin");//未登录
                            if (redirect == 'false') {
                                localRefresh('/common/headerRefresh', '#header');
                                $(".bg").show();
                                top.layer.close(index);
                            }
                        },
                        success:function(resultData){
                            if(resultData.code==0){
                                cocoMessage.success("您的申请已提交，请等待审核通过。", 3000); //duration为0时显示关闭按钮
                                $(".bg").hide();
                                $("#friendLinkApply").hide();
                            }else{
                                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                            }
                            layer.close(index);
                        }
                    });
                }
            }, function(){

            });
        }else {
            var lock = false; //默认未锁定
            top.layer.confirm("是否确定要更新友链信息？", {
                btn: ["是","否"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var paramData = {
                        id : linkId,
                        title : $("#webSiteName").val(),
                        url : $("#webSiteUrl").val(),
                        intro : $("#editor").text()
                    }
                    $.ajax({
                        url: basePath + "links/updateLinks",
                        type:"POST",
                        data:JSON.stringify(paramData),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        success:function(resultData){
                            if(resultData.code==0){
                                cocoMessage.success("您的友链已经更新，请等待审核通过。", 3000); //duration为0时显示关闭按钮
                                $(".bg").hide();
                                $("#friendLinkApply").hide();
                                queryMyLinks("");
                            }else{
                                cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                            }
                            layer.close(index);
                        }
                    });
                }
            }, function(){

            });
        }
    }
});

function vailUrl(elem,value){
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80 <br>
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var myreg=new RegExp(strRegex);
    if(!myreg.test(value)){
        $(elem).parents(".field").siblings(".error").find(".transition").text("网站地址格式不正确");
        $(elem).parents(".field").siblings(".error").show();
        return 1;
    }
    return 0;
}

function showFriendLinkInfo(){
    $.ajax({
        url: basePath + 'links/queryLinksInfo',
        type: "POST",
        //async:false,
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            console.log(resultData);
            var htmlStr = '';
            for(var i = 0;i < Object.keys(resultData).length;i++){
                htmlStr += '<h3 class="sideTab-title">';
                htmlStr +=      Object.keys(resultData)[i];
                htmlStr += '    <div class="right menu" style="float: right;margin-top: -5px">';
                htmlStr += '        <div class="item">';
                htmlStr += '            <div class="ui transparent icon input">';
                htmlStr += '                <span class="ly_button">';
                htmlStr += '                    <a style="color: #0ea432" class="apply" onclick="openLink()">点击加入</a>';
                htmlStr += '                </span>';
                htmlStr += '            </div>';
                htmlStr += '        </div>';
                htmlStr += '    </div> ';
                htmlStr += '</h3>';
                htmlStr += '<ul class="site_tj" class="webmasterRec" style="margin-top: -5px;padding-bottom: 40px;"> ';
                for(var j = 0;j < Object.values(resultData)[i].length;j++){
                    var target = '';
                    if(Object.values(resultData)[i][j].target == 1){
                        target = 'target="_blank"';
                    }
                    htmlStr += '<li><a href="' + Object.values(resultData)[i][j].url + '" ' + target + '>' + Object.values(resultData)[i][j].title + '</a></li>';
                }
                htmlStr += '</ul>';
            }
            $(".friendlink").html(htmlStr);
        },
        error: function () {

        }
    });
}