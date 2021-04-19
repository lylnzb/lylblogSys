var nowPage;

layui.config({base: '../layuiTablePlug/test/js/'}).use(['testTablePlug'], function () {
    var table = layui.table,
        form = layui.form;

    findCodeValue(form);

    //监听工具条
    table.on('tool(tableEvent)', function(obj){
        var data = obj.data;
        if(obj.event == 'approved'){
            var lock = false; //默认未锁定
            top.layer.confirm("确定要对该条数据进行审核吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    var paramData = {
                        "id" : data.id,
                        "auditStatus" : "1"
                    }
                    $.ajax({
                        url:basePath + "admin/link/auditLinkData",
                        type:"POST",
                        data:JSON.stringify(paramData),
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
        }else if(obj.event == 'auditNotThrough'){
            top.layer.open({
                type: 2,
                title: "审核不通过",
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['450px', '280px'],
                content: basePath + 'admin/link/auditLink?id='+data.id,
                end: function () {//层消失回调
                    layReload();
                }
            });
        }
    });

    tableIns = table.render({
        elem: '#table'
        ,url: basePath + 'admin/link/queryLinkShInfo'
        ,method: 'post'
        ,contentType: "application/json; charset=utf-8"
        ,dataType:"json"
        ,where: {
            auditStatus : "0"
        }
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
            ,{field:'submitTime', title:'提交时间', width:'14%', align:'center'}
            ,{field:'auditStatus', title:'审核状态', width:'10%', align:'center', templet : '#auditStatus'}
            ,{field:'right', title:'操作', width:'19%', align:'center', toolbar: '#barDemo'},
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

$("#seach").click(function(){
    layReload(1);
});

$("#reset").click(function(){
    $("#musicName").val("");
    $("#languages").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();
    $("#style").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();
    $("#valids").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();
});

//查询条件
function layReload(page){
    /*  */
    tableIns.reload({
        where: {
            auditStatus : "0"
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
}