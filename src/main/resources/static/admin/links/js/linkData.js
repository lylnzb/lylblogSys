var nowPage;

layui.config({base: '../admin/layuiTablePlug/test/js/'}).use(['testTablePlug'], function () {
    var table = layui.table,
        form = layui.form;

    findCodeValue(form);

    //监听工具条
    table.on('tool(tableEvent)', function(obj){
        var data = obj.data;
        if(obj.event == 'edit'){
            top.layer.open({
                type: 2,
                title: "友情链接编辑",
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['790px', '580px'],
                content: basePath + '/link/addOrUpdaLink?type=update&id='+data.id,
                end: function () {//层消失回调
                    layReload();
                }
            });
        }else if(obj.event == 'delete'){
            var lock = false; //默认未锁定
            top.layer.confirm("确定要删除该数据吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var deleteIds = [];
                    deleteIds.push(data.id);
                    $.ajax({
                        url:basePath + "link/deleteLikeInfo",
                        type:"POST",
                        data:JSON.stringify(deleteIds),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        success:function(resultData){
                            if(resultData.code==0){
                                parent.layer.alert(resultData.msg);
                                layReload();
                            }else{
                                parent.layer.alert(resultData.msg);
                            }
                        }
                    });
                }
            }, function(){

            });
        }
    });

    tableIns = table.render({
        elem: '#table'
        ,url: basePath + 'link/queryLinkInfo'
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
            ,{field:'title', title:'网址名称', width:'17%', align:'center'}
            ,{field:'url', title:'网址地址', width:'23%', align:'center'}
            ,{field:'submitName', title:'提交人', width:'9%', align:'center'}
            ,{field:'submitTime', title:'提交时间', width:'11%', align:'center'}
            ,{field:'status', title:'是否显示', width:'10%', align:'center', templet : '#status'}
            ,{field:'auditStatus', title:'审核状态', width:'10%', align:'center', templet : '#auditStatus'}
            ,{field:'right', title:'操作', width:'12.4%', align:'center', toolbar: '#barDemo'},
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
        ,height : "full-165"
        ,page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });

});

$("#applyLinks").click(function(){
    top.layer.open({
        type: 2,
        title: '友链申请',
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        area: ['790px', '580px'],
        content: basePath + '/link/addOrUpdaLink?type=add',
        end: function () {//层消失回调
            layReload();
        }
    });
});

function deleteLinks(){
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
                deleteIds.push(checkedObjs.data[i].id);
            }
            $.ajax({
                url:basePath + "link/deleteLikeInfo",
                type:"POST",
                data:JSON.stringify(deleteIds),
                dataType:"json",
                contentType : 'application/json;charset=utf-8',
                success:function(resultData){
                    if(resultData.code==0){
                        parent.layer.alert(resultData.msg);
                        layReload();
                    }else{
                        parent.layer.alert(resultData.msg);
                    }
                }
            });
        }
    }, function(){

    });
}

$("#seach").click(function(){
    layReload(1);
});

$("#reset").click(function(){
    $("#title").val("");
    $("#isShow").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();
    $("#audit").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();
});

//查询条件
function layReload(page){
    /*  */
    tableIns.reload({
        where: {
            title : $("#title").val(),
            status : $("#isShow").val(),
            auditStatus : $("#audit").val()
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
    loadSelect("#audit","link_audit_status", form);
    loadSelect("#isShow","sys_link_show", form);
}