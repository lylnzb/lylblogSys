var basePath="http://www.lylBlog.com/";//业务系统basePath

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
                    htmlStr += '<a href="' + basePath + data.data[i].menuUrl +'" class="m-item item m-mobile-hide" style="float: left"><i class="'+ data.data[i].icon +' icon"></i>'+ data.data[i].menuName +'</a>';
                }else if(data.data[i].isDefault == '1'){
                    htmlStr += '<div class="ui printing dropdown link item" style="float: left" onclick="window.open(\'' + basePath + data.data[i].menuUrl + '\', target=\'_self\')">';
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

function getLabelInfo(){
    var columnId = GetQueryString("columnId");
    $.ajax({
        url:basePath+'/common/getLabelList?columnId=' + columnId,
        type:"POST",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            console.log(resultData);
            var data = resultData.data;
            var htmlStr = '<ul class="sidebar_content divTags">';
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