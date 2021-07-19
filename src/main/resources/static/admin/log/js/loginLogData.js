var ind;
$(function () {
    change();
});

function change(){
    $(".dropdown-menu>li").click(function(){
        $(".change").html($(this).html())
    })
}

layui.config({base: '../layuiTablePlug/test/js/'}).use(['testTablePlug'], function () {
    var table = layui.table;

    tableIns = table.render({
        elem: '#table'
        ,url: basePath + 'admin/loginLog/queryLoginLogInfo'
        ,method: 'post'
        ,contentType: "application/json; charset=utf-8"
        ,dataType:"json"
        ,skin:'nob'// 无边框风格
        ,loading: true
        //,size:'sm'
        // 是否开启字段筛选的记忆功能，支持true/false/'local'/'session'/其他 开启的情况下默认是session，除非显式的指定为'local'
        ,colFilterRecord: true
        // 是否开启智能reload的模式
        ,smartReloadModel: true
        ,cols: [[
            {checkbox: true, id:"idTest", width:'2%'}
            ,{field:'loginName', title:'用户昵称', width:'15%', align:'center'}
            ,{field:'onlineIp', title:'主机', width:'10%', align:'center'}
            ,{field:'loginAddress', title:'登录地址', width:'9%', align:'center'}
            ,{field:'browser', title:'浏览器', width:'12%', align:'center'}
            ,{field:'loginSystem', title:'操作系统', width:'12%', align:'center'}
            ,{field:'loginType', title:'状态', width:'7%', align:'center'
                , templet:function(value){
                    if (value.loginType == '1') {
                        return '<span class="badge progress-bar-success" style="margin-top: 5px;">成功</span>';
                    } else if (value.loginType == '2') {
                        return '<span class="badge badge-primary" style="margin-top: 5px;">失败</span>';
                    }
                }
             }
            ,{field:'loginMsg', title:'操作消息', width:'20%', align:'center'}
            ,{field:'loginTime', title:'登录时间', width:'13%', align:'center'}
        ]]
        ,done:function(res,curr,count){
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
        ,height: 'full-100'
        ,id:"idTest"
        ,page: {
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });

});

function deleteLoginLog(){
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
                deleteIds.push(checkedObjs.data[i].logId);
            }
            $.ajax({
                url:basePath + "admin/loginLog/deleteLoginLogInfo",
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

//查询条件
function layReload(){
    /*  */
    tableIns.reload({
        where: {}
    });
}