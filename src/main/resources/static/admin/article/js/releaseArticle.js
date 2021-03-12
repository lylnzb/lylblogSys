var articleId = GetQueryString("articleId");
var type = GetQueryString("type");
var thumb;

//全局定义一次, 加载formSelects
layui.config({
    base: '../admin/layuiTablePlug/layui/plug/formSelects/dist/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    formSelects: 'formSelects-v4'
});
//加载模块
layui.use(['jquery', 'formSelects'], function(){
    var $ = layui.jquery
        ,form = layui.form
        ,formSelects = layui.formSelects;

    findCodeValue(form);
    $(".xm-select-tips.xm-select-none.xm-select-empty").text("请选择所属专栏");
    //百度富文本框初始化
    var editor = UE.getEditor('edit', {zIndex: 10});
    if(UE.Editor.prototype._bkGetActionUrl == undefined){
        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    }
    UE.Editor.prototype.getActionUrl = function(action) {
        //判断路径   这里是config.json 中设置执行上传的action名称
        if (action == 'uploadimage') {
            return '/uploadImage';
        } else if (action == 'uploadvideo') {
            return '';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }

    // select下拉框选中触发事件
    form.on("select(fromWay)", function(data){
        console.log(data);
        if(data.value == '2'){
            $("#fromUrl").removeAttr("readonly");
        }else{
            $("#fromUrl").attr("readonly", "readonly");
            $("#fromUrl").val("");
        }
    });

    // select下拉框选中触发事件
    form.on("select(college)", function(data){
        console.log(data);
        if(data.value == null || data.value == ''){
            $(".xm-select-tips.xm-select-none.xm-select-empty").text("请选择所属专栏");
        }else{
            $.ajax({
                url: basePath + "article/queryLabelInfo",
                type:"POST",
                data:JSON.stringify({"columnId":data.value}),
                async:false,
                contentType : 'application/json;charset=utf-8',
                success:function(resultData){
                    console.log(resultData);
                    var arrs = [];
                    for(var i = 0;i < resultData.data.length;i++){
                        var obj = new Object();
                        obj.name = resultData.data[i].labelName;
                        obj.value = resultData.data[i].labelId;
                        arrs.push(obj);
                    }
                    //local模式
                    formSelects.data('select1', 'local', {arr: arrs});
                }
            });
        }
    });

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        articleTitle:function(value) {
            if (value.length < 1) {
                return '文章标题不能为空！';
            }
        },
        columnId:function(value) {
            if (value.length < 1) {
                return '请选择所属专栏！';
            }
        },
        copyFrom:function(value) {
            if (value.length < 1) {
                return '来源名称不能为空！';
            }
        },
        articleLabel:function(value) {
            if (value.length < 1) {
                return '请选择文章标签！';
            }
        },
        fromWay:function(value) {
            if (value.length < 1) {
                return '请选择来源方式！';
            }
        },
        fromUrl:function(value) {
            if ($("#fromWay option:selected").val() == '2' && value.length < 1) {
                return '请选择来源方式！';
            }
        },
        columnId:function(value) {
            if (value.length < 1) {
                return '请选择所属专栏！';
            }
        },
        isFile:function(value) {
            if (null == $("#showImg").attr("src") || "" == $("#showImg").attr("src")) {
                return '请上传文章封面！';
            }
        },
        content:function(value) {
            if (editor.getContent().length < 1) {
                return '文章内容不能为空！';
            }
        }
    });

    if(type == "update"){
        $.ajax({
            url: basePath + "article/queryArticleInfo",
            type:"POST",
            data:JSON.stringify({"articleId":articleId}),
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                //文章标题
                $("#articleTitle").val(data.articleTitle);
                //所属专栏
                $("#columnId").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.columnId + ']').click();
                //来源名称
                $("#copyFrom").val(data.copyFrom);
                //来源方式
                $("#fromWay").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.fromWay + ']').click();
                //来源网址
                $("#fromUrl").val(data.fromUrl);
                //文章封面
                if(data.thumb != null && data.thumb != ''){
                    $("#uploadDemoView").show();
                    thumb = basePath + "/articlefile/" + data.thumb;
                    $("#showImg").attr("src", basePath + "/articlefile/" + data.thumb);
                }
                //是否置顶
                (data.onTop == 'Y')?$("#onTop").attr("checked", "checked"):$("#onTop").removeAttr("checked");
                //是否推荐
                (data.iselite == 'Y')?$("#iselite").attr("checked", "checked"):$("#iselite").removeAttr("checked");
                //开启简介
                (data.openIntroduce == 'Y')?$("#openIntroduce").attr("checked", "checked"):$("#openIntroduce").removeAttr("checked");
                //允许评论
                (data.allowComment == 'Y')?$("#allowComment").attr("checked", "checked"):$("#allowComment").removeAttr("checked");
                //允许转载
                (data.allowReprinted == 'Y')?$("#allowReprinted").attr("checked", "checked"):$("#allowReprinted").removeAttr("checked");
                //文章摘要
                $("#abstracts").val(data.abstracts);
                //文章正文
                editor.ready(function(){editor.setContent(data.content)});
                //文章标签
                formSelects.value('select1', data.articleLabel.split(","), true);
                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var articleTitle = data.field.articleTitle;//文章标题
        var columnId = data.field.columnId;//columnId
        var copyFrom = data.field.copyFrom;//来源名称
        var articleLabel = data.field.articleLabel;//文章标签
        var fromWay = data.field.fromWay;//来源方式
        var fromUrl = data.field.fromUrl;//来源网址
        var abstracts = data.field.abstracts;//文章摘要
        var content = data.field.editorValue;//文章内容
        var onTop = ($("#onTop").attr("checked") == "checked")?"Y":"N";//是否置顶
        var iselite = ($("#iselite").attr("checked") == "checked")?"Y":"N";//是否推荐
        var openIntroduce = ($("#openIntroduce").attr("checked") == "checked")?"Y":"N";//开启简介
        var allowComment = ($("#allowComment").attr("checked") == "checked")?"Y":"N";//允许评论
        var allowReprinted= ($("#allowReprinted").attr("checked") == "checked")?"Y":"N";//允许转载
        var file = (thumb != null && thumb != '')?"":$("#showImg").attr("src");
        var articleStatus = "";
        if(data.elem.id == 'bc'){//存为草稿
            articleStatus = "1";
        }else if(data.elem.id == 'fb'){//发布博客
            articleStatus = "2";
        }
        var paramData = {
            articleId : (null == articleId || ''  == articleId) ? "" : articleId,
            articleTitle : articleTitle,
            columnId : columnId,
            copyFrom : copyFrom,
            articleLabel : articleLabel,
            fromWay : fromWay,
            fromUrl : fromUrl,
            abstracts : abstracts,
            content : content,
            onTop : onTop,
            iselite : iselite,
            openIntroduce : openIntroduce,
            allowComment : allowComment,
            allowReprinted : allowReprinted,
            file : file,
            articleStatus : articleStatus
        };
        console.log(paramData);
        $.ajax({
            url: basePath + "article/addOrUpdaArticleInfo?type=" + type,
            type:"POST",
            data:JSON.stringify(paramData),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                if(resultData.code==0){
                    parent.layer.alert(resultData.msg);
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭当前页
                }else{
                    parent.layer.alert(resultData.msg);
                }
            }
        });
    });
});

function viewImg(file){
    var reader = new FileReader();	// 实例化一个FileReader对象，用于读取文件
    var img = document.getElementById('showImg'); 	// 获取要显示图片的标签

    //读取File对象的数据
    reader.onload = function(evt){
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
    thumb = "";
    $("#uploadDemoView").show();
}

$("#closeView").click(function(){
    $("#showImg").attr("src", "");
    $("#uploadDemoView").hide();
});

//字典初始化
function findCodeValue(form){
    loadSelect("#fromWay", "sys_article_form", form)
    loadSelectAllow("#columnId", form);
}