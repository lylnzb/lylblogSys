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
        ,url: basePath + 'admin/operLog/queryOperLogInfo'
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
            ,{field:'nickName', title:'用户昵称', width:'15%', align:'center'}
            ,{field:'loginName', title:'登录邮箱', width:'15%', align:'center'}
            ,{field:'title', title:'模块', width:'10%', align:'center'}
            ,{field:'action', title:'功能请求', width:'19.5%', align:'center'}
            ,{field:'operIp', title:'主机IP', width:'14%', align:'center'}
            ,{field:'status', title:'状态', width:'7%', align:'center'
                , templet:function(value){
                    if (value.status == '1') {
                        return '<span class="badge progress-bar-success" style="margin-top: 5px;">成功</span>';
                    } else if (value.status == '2') {
                        return '<span class="badge badge-primary" style="margin-top: 5px;">失败</span>';
                    }
                }
             }
            ,{field:'operTime', title:'操作时间', width:'13%', align:'center'}
            ,{field:'right', title:'操作', width:'5.4%', align:'center', toolbar: '#barDemo'},
        ]]
        ,done:function(res,curr,count){
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
        ,height: 'full-110'
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

//查询条件
function layReload(){
    /*  */
    tableIns.reload({
        where: {},
        page: false
    });
}