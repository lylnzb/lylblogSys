$(function() {
    getBlogRecommended();

    $('.menu.toggle').click(function () {
        $('.m-item').toggleClass('m-mobile-hide')
    })
});

$(window).scroll(function() {
    if ($(window).scrollTop() > 50) {
        $(".bt-box.top").fadeIn(200);
    }else {
        $(".bt-box.top").fadeOut(200);
    }
});

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