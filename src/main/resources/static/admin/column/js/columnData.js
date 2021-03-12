var insTb = "";

layui.config({
    base: '../treetable-lay/'
}).use(['layer', 'util', 'treeTable'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var util = layui.util;
    var treeTable = layui.treeTable;

    // 渲染表格
    insTb = treeTable.render({
        elem: '#demoTreeTb',
        url: basePath+'webColumn/queryWebColumnInfo',
        height: 'full-70',
        skin:'nob',// 无边框风格
        tree: {
            iconIndex: 2,
            isPidData: true,
            idName: 'columnId',
            pidName: 'parentId'
        },
        cols: [[
            {type: 'checkbox'},
            {title: '序号', type: 'numbers', width: '10%'},
            /*{title: '栏目图标', align: 'center',hide: true},*/
            {title: '栏目名称', minWidth: 165 ,
                templet: function(d){
                    if(null != d.columnIcon && "*" != d.columnIcon){
                        return "<i class=\""+d.columnIcon+" icon\"></i>"+d.columnName+"";
                    }else{
                        return "<i class=\"file outline icon\"></i>"+d.columnName+"";
                    }
                }
            },
            {title: '栏目地址', field: 'columnUrl', align: 'center'},
            {title: '栏目属性', field: 'attributeName', align: 'center'},
            {title: '栏目状态', align: 'center', templet: "#isHidden"},
            {title: '创建时间', field: 'createTime', align: 'center'},
            {title: '操作', align: 'center', toolbar: '#tbBar', width: '30%'}
        ]],
        id:"idTest",
        done: function () {
            $(".ew-tree-icon.layui-icon.layui-icon-layer").css("display","none");
            $(".ew-tree-icon.layui-icon.layui-icon-file").css("display","none");
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
    });

    // 工具列点击事件
    treeTable.on('tool(demoTreeTb)', function (obj) {
        var event = obj.event;
        var data = obj.data;
        if (event === 'del') {
            var lock = false; //默认未锁定
            top.layer.confirm("确定要删除该条栏目数据吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var deleteIds = [];
                    deleteIds.push(data.columnId);
                    $.ajax({
                        url:basePath + "webColumn/deleteWebColumnInfo",
                        type:"POST",
                        data:JSON.stringify(deleteIds),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        success:function(resultData){
                            if(resultData.code==0){
                                parent.layer.alert(resultData.msg);
                                refresh();
                            }else{
                                parent.layer.alert(resultData.msg);
                            }
                        }
                    });
                }
            }, function(){

            });
        } else if (event === 'edit') {
            top.layer.open({
                type: 2,
                title: '编辑栏目',
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['780px', '680px'],
                content: '../admin/column/addOrEditColumn.html?type=update&columnId=' + data.columnId,
                end: function () {//层消失回调
                    refresh();
                }
            });
        }
    });

});

function addColumn(){
    top.layer.open({
        type: 2,
        title: '添加栏目',
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        area: ['750px', '680px'],
        content: '../admin/column/addOrEditColumn.html?type=add',
        end: function () {//层消失回调
            refresh();
        }
    });
}

function deleteColumn(){
    var checkedObjs = insTb.checkStatus('idTest');//获取所有选中的节点
    if(checkedObjs.length < 1){
        top.layer.msg("请选择要删除的栏目数据！");
        return false;
    }
    var lock = false; //默认未锁定
    top.layer.confirm("确定要批量删除栏目吗？", {
        btn: ["确定","取消"], //按钮
        title: "提示",
        icon: 3
    }, function(index){
        if(!lock) {
            lock = true;
            var deleteIds = [];
            for(var i = 0;i < checkedObjs.length;i++){
                deleteIds.push(checkedObjs[i].columnId);
            }
            $.ajax({
                url:basePath + "webColumn/deleteWebColumnInfo",
                type:"POST",
                data:JSON.stringify(deleteIds),
                dataType:"json",
                contentType : 'application/json;charset=utf-8',
                success:function(resultData){
                    if(resultData.code==0){
                        parent.layer.alert(resultData.msg);
                        refresh();
                    }else{
                        parent.layer.alert(resultData.msg);
                    }
                }
            });
        }
    }, function(){

    });
}

function refresh(){
    insTb.refresh();
}