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
    var table = layui.table,
        form = layui.form;

    //监听工具条
    table.on('tool(tableEvent)', function(obj){
        var data = obj.data;
        if(obj.event == 'logout'){
            var lock = false; //默认未锁定
            top.layer.confirm("确定要强制选中用户下线吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    $.ajax({
                        url:basePath + "admin/online/forceLogout?sessionId=" + data.sessionId,
                        type:"POST",
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
        ,url: basePath + 'admin/online/selectUserOnlineList'
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
            {field:'sessionId', title:'会话编号', width:'19.7%', align:'center'}
            ,{field:'nickName', title:'用户昵称', width:'9%', align:'center'}
            ,{field:'onlineIp', title:'主机', width:'7%', align:'center'}
            ,{field:'loginAddress', title:'登录地址', width:'9%', align:'center'}
            ,{field:'browser', title:'浏览器', width:'8%', align:'center'}
            ,{field:'onlineSystem', title:'操作系统', width:'9%', align:'center'}
            ,{field:'sessionStatus', title:'会话状态', width:'7%', align:'center'
                , templet:function(value){
                    if (value.sessionStatus == 'on_line') {
                        return '<span class="badge progress-bar-success" style="margin-top: 5px;">在线</span>';
                    } else if (value.sessionStatus == 'off_line') {
                        return '<span class="badge badge-primary" style="margin-top: 5px;">离线</span>';
                    }
                }
             }
            ,{field:'loginTime', title:'登录时间', width:'13%', align:'center'}
            ,{field:'lastAccessTime', title:'最后访问时间', width:'12%', align:'center'}
            ,{field:'toolses', title:'操作', width:'7%', align:'center', toolbar: '#barDemo'}
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
        ,page: false
    });

});

//查询条件
function layReload(){
    /*  */
    tableIns.reload({
        where: {},
        page: false
    });
}