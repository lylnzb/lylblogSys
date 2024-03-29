var ind;
var nowPage;

layui.config({base: '../layuiTablePlug/test/js/'}).use(['testTablePlug'], function () {
    var table = layui.table,
        form = layui.form;

    findCodeValue(form);

    //触发switch开关
    form.on('switch(encrypt)', function(data){
        var url = "";
        var wznm = $(data.elem).attr("data-value");
        var value = (data.elem.checked)?'Y':'N';
        if($(data.elem).attr("class") == 'onTop') {
            url = basePath + "admin/article/updateArticleToOnTop";
        }else if($(data.elem).attr("class") == 'iselite') {
            url = basePath + "admin/article/updateArticleToIselite";
        }
        $.ajax({
            url:url + "?wznm=" + wznm + "&value=" + value,
            type:"POST",
            asyns:false,
            success:function(resultData){
                if(resultData.code == '0') {
                    top.layer.alert("设置成功！");
                }else {
                    top.layer.alert("设置失败！");
                    if(value == "Y") {
                        data.elem.checked= false;
                    }else if(value == "N") {
                        data.elem.checked= true;
                    }
                    form.render();
                }
            },
            error:function () {
                top.layer.alert("系统异常！");
                if(value == "Y") {
                    data.elem.checked= false;
                }else if(value == "N") {
                    data.elem.checked= true;
                }
                form.render();
            }
        });
    });

    //监听工具条
    table.on('tool(tableEvent)', function(obj){
        var data = obj.data;
        if(obj.event == 'edit'){
            layer.open({
                type: 2,
                title: "文章发布",
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['100%', '100%'],
                content: basePath + 'admin/article/releaseArticle?type=update&articleId=' + data.articleId,
                end: function () {//层消失回调
                    layReload(1);
                }
            });
        }else if(obj.event == 'delete'){
            var lock = false; //默认未锁定
            top.layer.confirm("确定要删除该条数据吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var deleteIds = [];
                    deleteIds.push(data.articleId);
                    $.ajax({
                        url:basePath + "admin/article/deleteArticleInfo",
                        type:"POST",
                        data:JSON.stringify(deleteIds),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        success:function(resultData){
                            if(resultData.code==0){
                                parent.layer.alert(resultData.msg);
                                layReload(1);
                            }else{
                                parent.layer.alert(resultData.msg);
                            }
                        }
                    });
                }
            }, function(){

            });
        }else if(obj.event == 'preview'){
            window.open(basePath + "blog/previewArticle/" + data.wznm);
        }
    });

    tableIns = table.render({
        elem: '#table'
        ,url: basePath + 'admin/article/queryArticleInfo'
        ,method: 'post'
        ,contentType: "application/json; charset=utf-8"
        ,dataType:"json"
        ,skin:'nob'// 无边框风格
        ,loading: true
        // 是否开启字段筛选的记忆功能，支持true/false/'local'/'session'/其他 开启的情况下默认是session，除非显式的指定为'local'
        ,colFilterRecord: true
        // 是否开启智能reload的模式
        ,smartReloadModel: true
        ,cols: [[
            {checkbox: true, id:"idTest", width:'2%'}
            ,{field:'rk', title:'序号', width:'6%', align:'center'}
            ,{field:'articleTitle', title:'文章名称', width:'20%', align:'center'}
            ,{field:'columnName', title:'所属专栏', width:'10%', align:'center'}
            ,{field:'onTop', title:'是否置顶', width:'7%', align:'center', templet : function(data){
                    var check = '';
                    if(data.onTop == 'Y') {
                        check = 'checked="checked"';
                    }
                    var htmlStr = '<input class="onTop" type="checkbox" lay-skin="switch" lay-filter="encrypt" data-value="' + data.wznm + '" lay-text="是|否" ' + check + '>';
                    return htmlStr;
                }
            }
            ,{field:'iselite', title:'是否推荐', width:'7%', align:'center', templet : function(data){
                    var check = '';
                    if(data.iselite == 'Y') {
                        check = 'checked="checked"';
                    }
                    var htmlStr = '<input class="iselite" type="checkbox" lay-skin="switch" lay-filter="encrypt" data-value="' + data.wznm + '" lay-text="是|否" ' + check + '>';
                    return htmlStr;
                }
            }
            ,{field:'fromWayName', title:'来源方式', width:'7%', align:'center', templet : function(data){
                    return selectDictLabel('sys_article_form', data.fromWay);
                }
            }
            ,{field:'articleStatusName', title:'文章状态', width:'8%', align:'center', templet : function(data){
                    return selectDictLabel('sys_article_status', data.articleStatus);
                }
             }
            ,{field:'nickName', title:'发布人', width:'13%', align:'center'}
            ,{field:'createTime', title:'发布时间', width:'13%', align:'center'}
            ,{fixed:'right', title:'操作', width:'15.6%', align:'center', toolbar: '#barDemo'},
        ]]
        ,id:"idTest"
        ,done:function(res,curr,count){
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
        ,height : "full-195"
        ,page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });

});

$("#releaseArticle").click(function(){
    layer.open({
        type: 2,
        title: "文章发布",
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        area: ['100%', '100%'],
        content: basePath + 'admin/article/releaseArticle?type=add',
        end: function () {//层消失回调
            layReload(1);
        }
    });
});

function deleteArticle(){
    var checkedObjs = layui.table.checkStatus('idTest');//获取所有选中的节点
    if(checkedObjs.data.length < 1){
        top.layer.msg("请选择要删除的数据！");
        return false;
    }
    var lock = false; //默认未锁定
    top.layer.confirm("确定要批量删除数据吗？", {
        btn: ["确定","取消"], //按钮
        title: "提示",
        icon: 3
    }, function(index){
        if(!lock) {
            lock = true;
            var deleteIds = [];
            for(var i = 0;i < checkedObjs.data.length;i++){
                deleteIds.push(checkedObjs.data[i].articleId);
            }
            $.ajax({
                url:basePath + "admin/article/deleteArticleInfo",
                type:"POST",
                data:JSON.stringify(deleteIds),
                dataType:"json",
                contentType : 'application/json;charset=utf-8',
                success:function(resultData){
                    if(resultData.code==0){
                        parent.layer.alert(resultData.msg);
                        layReload(1);
                    }else{
                        parent.layer.alert(resultData.msg);
                    }
                }
            });
        }
    }, function(){

    });
}

//查询条件
function layReload(page){
    /*  */
    tableIns.reload({
        where: {
            articleTitle : $("#articleTitle").val(),
            columnId : $("#columnId").val(),
            articleStatus : $("#articleStatus").val()
        },
        page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            ,curr: (!!page?page:nowPage) //重新从第 1 页开始
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });
}

//字典初始化
function findCodeValue(form){
    loadSelect("#articleStatus","sys_article_status", form);
    loadSelectAllow("#columnId", form);
}