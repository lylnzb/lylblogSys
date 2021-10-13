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
        if(obj.event == 'edit'){
            top.layer.open({
                type: 2,
                title: "编辑角色",
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['790px', '620px'],
                content: basePath + '/admin/role/addOrUpdaRole?type=update&roleId='+data.roleid,
                end: function () {//层消失回调
                    layReload();
                }
            });
        }else if(obj.event == 'delete'){
            var lock = false; //默认未锁定
            top.layer.confirm("确定要删除该条角色数据吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    var deleteIds = [];
                    deleteIds.push(data.roleid);
                    $.ajax({
                        url:basePath + "admin/deleteRoleInfo",
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
        ,url: basePath + 'admin/queryRoleList'
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
            ,{field:'rolename', title:'角色名称', width:'11.1%', align:'center'}
            ,{field:'rolekey', title:'权限字符', width:'10%', align:'center'}
            ,{field:'orderBy', title:'显示顺序', width:'10%', align:'center'}
            ,{field:'valid', title:'状态', width:'18%', align:'center', templet : function(data){
                    return selectDictLabel('sys_status', data.valid);
                }
            }
            ,{field:'createTime', title:'创建时间', width:'20%', align:'center'}
            ,{field:'toolses', title:'操作', width:'23%', align:'center', toolbar: '#barDemo'}
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
        ,height: 'full-100'
        ,page: false
    });

});

function addOrUpdateRole(type){
    var checkedObjs = layui.table.checkStatus('idTest');//获取所有选中的节点
    var title = "";
    var param = "";
    if(type == 'add'){
        title = "添加角色";
    }else if(type == 'update'){
        title = "编辑角色";
        if(checkedObjs.data.length < 1){
            layer.msg("请选择要编辑的角色数据！");
            return false;
        }else if(checkedObjs.data.length != 1){
            layer.msg("且只能选择一条数据！");
            return false;
        }
        param = "&roleId="+checkedObjs.data[0].roleid;
    }
    top.layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        area: ['780px', '620px'],
        content: basePath + '/admin/role/addOrUpdaRole?type='+type+param,
        end: function () {//层消失回调
            layReload();
        }
    });
}

function deleteRole(){
    var checkedObjs = layui.table.checkStatus('idTest');//获取所有选中的节点
    if(checkedObjs.data.length < 1){
        top.layer.msg("请选择要删除的角色数据！");
        return false;
    }
    var lock = false; //默认未锁定
    top.layer.confirm("确定要批量删除角色吗？", {
        btn: ["确定","取消"], //按钮
        title: "提示",
        icon: 3
    }, function(index){
        if(!lock) {
            lock = true;
            var deleteIds = [];
            for(var i = 0;i < checkedObjs.data.length;i++){
                deleteIds.push(checkedObjs.data[i].roleid);
            }
            $.ajax({
                url:basePath + "admin/deleteRoleInfo",
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
        where: {},
        page: false
    });
}