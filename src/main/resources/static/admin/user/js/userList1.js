$(function () {
    change();
});

function change(){
    $(".dropdown-menu>li").click(function(){
        $(".change").html($(this).html())
    })
}

var ind;
var nowPage;
layui.config({base: '/admin/layuiTablePlug/test/js/'}).use(['testTablePlug'], function () {
    var table = layui.table,
        form = layui.form;

    findCodeValue(form);

    //监听工具条
    table.on('tool(tableEvent)', function(obj){
        var data = obj.data;
        if(obj.event == 'delete'){//查看
            var lock = false; //默认未锁定
            top.layer.confirm("确定停用该条数据信息吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var userIds = [];
                    userIds.push(data.userId);
                    $.ajax({
                        url: basePath + "admin/disableUserInfo",
                        type:"POST",
                        data:JSON.stringify(userIds),
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
                layer.close(index);
            }, function(){

            });
        }else if(obj.event == 'edit'){
            top.layer.open({
                type: 2,
                title: "编辑用户",
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['800px', '600px'],
                content: basePath + '/admin/user/addOrUpdaUser?type=update&userId=' + data.userId + "&yhnm=" + data.yhnm,
                end: function () {//层消失回调
                    layReload();
                }
            });
        }else if(obj.event == 'reset'){
            var lock = false; //默认未锁定
            top.layer.confirm("初始化密码为12345678,确定要重置吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var emails = [];
                    emails.push(data.email);
                    $.ajax({
                        url:basePath + "admin/resetPassword",
                        type:"POST",
                        data:JSON.stringify(emails),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        success:function(resultData){
                            if(resultData.code==0){
                                parent.layer.alert(resultData.msg);
                                location.reload();
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
        ,url: basePath + 'admin/queryUserList'
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
            {checkbox: true, id:"idTest", width:'2%', align:'center'}
            ,{field:'rk', title:'序号', width:'5%', align:'center'}
            ,{field:'nickname', title:'用户昵称', width:'15%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.nickName == null){
                        return "暂无";
                    }else{
                        return data.nickName;
                    }
                }
            }
            ,{field:'email', title:'用户邮箱', width:'16%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.email == null){
                        return "暂无";
                    }else{
                        return data.email;
                    }
                }
            }
            ,{field:'sex', title:'性别', width:'5%', align:'center',
                templet : function(data) {// 替换数据
                    return "男";
                }
            }
            ,{field:'rolename', title:'所属角色', width:'10%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.rolename == null){
                        return "暂无";
                    }else{
                        return data.rolename;
                    }
                }
            }
            ,{field:'signature', title:'个性签名', width:'19.7%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.signature == null){
                        return "暂无";
                    }else{
                        return data.signature;
                    }
                }
            }
            ,{field:'valid', title:'账号状态', width:'10%', align:'center', templet:'#valId'}
            ,{field:'toolses', title:'操作', width:'17.4%', align:'center', toolbar: '#barDemo'}
        ]]
        ,id:"idTest"
        , done: function () {
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
        ,height : "full-195"
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });

});


function addUser(){
    top.layer.open({
        type: 2,
        title: '添加新用户',
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        area: ['790px', '680px'],
        content: basePath + '/admin/user/addOrUpdaUser?type=add',
        end: function () {//层消失回调
            layReload();
        }
    });
}

function reset_password(){
    var checkedObjs = layui.table.checkStatus('idTest');//获取所有选中的节点
    if(checkedObjs.data.length < 1){
        layer.msg("请选择要重置的用户数据！");
        return false;
    }

    var lock = false; //默认未锁定
    top.layer.confirm("初始化密码为12345678,确定要重置吗？", {
        btn: ["确定","取消"], //按钮
        title: "提示",
        icon: 3
    }, function(index){
        if(!lock) {
            lock = true;
            var emails = [];
            for(var i = 0;i < checkedObjs.data.length;i++){
                emails.push(checkedObjs.data[i].email);
            }
            $.ajax({
                url:basePath + "admin/resetPassword",
                type:"POST",
                data:JSON.stringify(emails),
                dataType:"json",
                contentType : 'application/json;charset=utf-8',
                success:function(resultData){
                    if(resultData.code==0){
                        parent.layer.alert(resultData.msg);
                        location.reload();
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
    $("#nickname").val("");
    $("#roleid").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();
});

//查询条件
function layReload(page){
    /*  */
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            roleId:$('#roleid option:selected').val(),
            nickName:$("#nickname").val(),
            valid:$('#accountType option:selected').val()
        }
        ,page: {
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
    loadSelect("#roleid", "role", form);
    loadSelect("#accountType", "sys_account_status", form);
}
