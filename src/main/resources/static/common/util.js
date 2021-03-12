var basePath="http://127.0.0.1/";//业务系统basePath

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
            alert("初始化选项失败1");
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
        url:basePath+'/webColumn/queryWebColumnByAllow',
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
            alert("初始化选项失败2");
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
            alert("初始化选项失败3");
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
                    htmlStr += '<div class="ui dropdown item" style="float: left">';
                    htmlStr += '    <i class="'+ data.data[i].icon +' icon"></i>'+ data.data[i].menuName +'<i class="dropdown icon"></i>';
                    htmlStr += '    <div class="menu" data-filtered="filtered">';
                    for(var j = 0;j < data.data[i].childList.length; j++){
                        htmlStr += '    <div class="item" data-filtered="filtered"><a href="' + basePath + data.data[i].childList[j].menuUrl +'" style="color: #0C0C0C">'+ data.data[i].childList[j].menuName +'</a></div>';
                    }
                    htmlStr += '    </div>';
                    htmlStr += '</div>';
                }
            }
            $("#menu").html(htmlStr);
            $('.ui.dropdown').dropdown();
        },
        error:function(){
            alert("初始化选项失败4");
        }
    });
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