$("#collectionClick").on('click', function() {
    var isLogin=$("#isAuthenticated").val();
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();
    }else {
        initCollection();
        $(".bg").show();
        $(".addCollection").show();
        $(".collectionBtn").show();
        $(".collectionInput").hide();
        $("#collectionName").val("");
    }
});

$(".collectionBox .close.icon").click(function(){
    $(".bg").hide();
    $("#addCollection").hide();
});

$("body").delegate(".collectionItem","click", function(){
    if(!$(this).hasClass("select")){
        $(this).siblings().removeClass("select");
        $(this).addClass("select");
    }
});

$(".collectionBtn").on('click', function() {
    $(this).hide();
    $(".collectionInput").show();
});

$(".collectionInput button").on('click', function() {
    var collectionName = $("#collectionName").val();
    if(collectionName == null || collectionName == ''){
        $("#collectionName").css({"border-style":"solid", "border-color":"red"});
        cocoMessage.error("名称不能为空！", 3000); //duration为0时显示关闭按钮
        return false;
    }
    var paramData = new Object();
    paramData.favoriteName = $("#collectionName").val();
    $.ajax({
        url: basePath + "myCollection/addFavoriteInfo",
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
                $(".addCollection").hide();
            }
        },
        success:function(resultData){
            if(resultData.code==0){
                cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                initCollection();
                $(".collectionBtn").show();
                $(".collectionInput").hide();
                $("#collectionName").val("");
            }else{
                cocoMessage.error(resultData.msg, 3000); //loading可以选择是否展示关闭按钮
            }
        }
    });
});

$(".collectionFooter button").on('click', function() {
    var favoriteId = $(".collectionItem.select").children("span").attr("data-id");
    if(favoriteId == null || favoriteId == ''){
        cocoMessage.error("请选择收藏夹！", 3000); //duration为0时显示关闭按钮
        return false;
    }
    var paramData = new Object();
    paramData.favoriteId = favoriteId;
    paramData.wznm = $("#wznm").val();
    $.ajax({
        url: basePath + "myCollection/addCollectionData",
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
                $(".addCollection").hide();
            }
        },
        success:function(resultData){
            if(resultData.code==0){
                cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                $(".bg").hide();
                $(".addCollection").hide();
                $("#collectionSpan .star.outline.icon").css("color", "red");
                $("#collectionClick").css("color", "red");
                $("#collectionNum").text(parseInt($("#collectionNum").text()) + 1);
            }else{
                cocoMessage.error(resultData.msg, 3000); //loading可以选择是否展示关闭按钮
            }
        }
    });
});

$(".btn-box .ok").on("click", function() {
    if(null == $("#favoriteName").val() || '' == $("#favoriteName").val()){
        cocoMessage.error("收藏夹名称不能为空");
        return false;
    }
    var paramData = new Object();
    paramData.favoriteName = $("#favoriteName").val();
    paramData.editor = $("#editor").html();
    $.ajax({
        url: basePath + "myCollection/addFavoriteInfo",
        type:"POST",
        data:JSON.stringify(paramData),
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        //设置ajax请求结束后的执行动作
        complete : function(XMLHttpRequest) {
            // 通过XMLHttpRequest取得响应头
            var redirect = XMLHttpRequest.getResponseHeader("isLogin");//未登录
            if (redirect == 'false') {
                window.location.reload();
            }
        },
        success:function(resultData){
            if(resultData.code==0){
                cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                $(".bg").hide();
                $(".addCollection").hide();
                initCollection();
            }else{
                cocoMessage.error(resultData.msg, 3000); //loading可以选择是否展示关闭按钮
            }
        }
    });
});

$(".btn-box .del").on("click", function() {
    var favoriteId = $("#favoriteId").val();
    var targetId =  $("#favoriteList").children(".active").attr("data-value");
    var type = $("input[name='fruit']:checked").attr("tabindex");
    if(type == 1 && (undefined == targetId || null == targetId || "" == targetId)) {
        cocoMessage.error("请选择要移动的文件内容！");
        return false;
    }
    confirm({
        title: '提示',
        content: '是否要删除该文件夹？'
    }).then(() => {
        $.ajax({
            url:basePath + "myCollection/deleteFavoriteInfo?id=" + favoriteId + '&type=' + type + '&targetId=' + targetId,
            type:"POST",
            success:function(resultData){
                if(resultData.code==0){
                    cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                    initCollection();
                    $(".bg").hide();
                    $(".addCollection").hide();
                }else{
                    cocoMessage.error(resultData.msg, 3000); //duration为0时显示关闭按钮
                }
            }
        });
    }).catch(() => {

    });
});

function initCollection(){
    $.ajax({
        url: basePath + "myCollection/queryFavoriteInfo",
        type:"POST",
        //设置ajax请求结束后的执行动作
        complete : function(XMLHttpRequest) {
            // 通过XMLHttpRequest取得响应头
            var redirect = XMLHttpRequest.getResponseHeader("isLogin");//未登录
            if (redirect == 'false') {
                localRefresh('/common/headerRefresh', '#header');
                $(".addCollection").hide();
            }
        },
        success:function(resultData){
            if(resultData.code==0){
                var htmlStr = "";
                for(var i = 0;i < resultData.data.length;i++) {
                    var near = "";
                    if(resultData.data[i].count > 0) {
                        near = '<i class="near"></i>';
                    }
                    htmlStr += '<li class="collectionItem' + ((i == 0)?' select':'') + '">';
                    htmlStr += '    ' + near + '<span data-id="' + resultData.data[i].id + '">' + resultData.data[i].favoriteName + '</span> ';
                    htmlStr += '</li>';
                }
                $(".collectionItems").html(htmlStr);
            }else{
                cocoMessage.error('系统异常', 3000); //loading可以选择是否展示关闭按钮
            }
        }
    });
}