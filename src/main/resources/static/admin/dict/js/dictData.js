var tableIns;
var tableIns1;
var nowPage;
layui.config({base: '../admin/layuiTablePlug/test/js/'}).use(['testTablePlug'], function () {
    var table = layui.table,
        form = layui.form;

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        dictType:function(value) {
            if (value.length < 1) {
                return '类别编码不能为空！';
            }
        },
        dictName:function(value) {
            if (value.length < 1) {
                return '类别名称不能为空！';
            }
        }
    });

    //监听提交
    form.on('submit(save)', function(data){
        var dictId = data.field.dictId;//类别编号
        var dictType = data.field.dictType;//类别编码
        var dictName = data.field.dictName;//类别名称
        var valId = data.field.valId;//状态
        var remark = data.field.remark;//备注
        var paramData = {
            dictId : dictId,
            dictType : dictType,
            dictName : dictName,
            valId : valId,
            remark : remark
        }
        $.ajax({
            url: basePath + "dict/addOrEditDictTypeInfo?type=update",
            type:"POST",
            data:JSON.stringify(paramData),
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
    });

    table.on('row(tableEvent)', function(obj){
        var data = obj.data;
        $.ajax({
            url: basePath + "dict/queryDictTypeInfo",
            type:"POST",
            data:JSON.stringify({dictId : data.dictId}),
            dataType:"json",
            async: false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#dictId").val(data.dictId);
                $("#dictType").val(data.dictType);
                $("#dictName").val(data.dictName);
                $("#valId").siblings("div.layui-form-select").find('dl').find("dd[lay-value='"+data.valId+"']").click();
                $("#remark").val(data.remark);
            }
        });
        layReload1();
        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

    //监听工具条
    table.on('tool(tableEvent)', function(obj){
        var data = obj.data;
        var lock = false; //默认未锁定
        top.layer.confirm("确定删除该条类型信息吗？", {
            btn: ["确定","取消"], //按钮
            title: "提示",
            icon: 3
        }, function(index){
            if(!lock) {
                lock = true;
                $.ajax({
                    url: basePath + "dict/deleteDictTypeInfo",
                    type:"POST",
                    data:JSON.stringify({dictType:data.dictType}),
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
            layer.close(index);
        }, function(){

        });
    });

    //监听工具条
    table.on('tool(tableEvent1)', function(obj){
        var data = obj.data;
        if(obj.event == 'delete'){//查看
            var lock = false; //默认未锁定
            top.layer.confirm("确定删除该条数据信息吗？", {
                btn: ["确定","取消"], //按钮
                title: "提示",
                icon: 3
            }, function(index){
                if(!lock) {
                    lock = true;
                    $.ajax({
                        url: basePath + "dict/deleteDictDataInfo",
                        type:"POST",
                        data:JSON.stringify({dictCode:data.dictCode}),
                        dataType:"json",
                        contentType : 'application/json;charset=utf-8',
                        success:function(resultData){
                            if(resultData.code==0){
                                parent.layer.alert(resultData.msg);
                                layReload1();
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
                title: "编辑字典数据",
                shadeClose: true,
                shade: 0.5,
                closeBtn:1,
                area: ['800px', '600px'],
                content: basePath + '/dict/saveOrEditDictData?type=update&dictType='+$("#dictType").val()+'&dictCode='+data.dictCode,
                end: function () {//层消失回调
                    layReload1();
                }
            });
        }
    });

    tableIns = table.render({
        elem: '#table'
        ,url: basePath + 'dict/queryDictTypeInfo'
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
            {field:'dictType', title:'类别编码', width:'28%', align:'center'}
            ,{field:'dictName', title:'类别名称', width:'40%', align:'center'}
            ,{field:'valId', title:'有效标识', width:'17%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.valId == 0){
                        return "无效";
                    }else if(data.valId == 1){
                        return "有效";
                    }
                }
            }
            ,{field:'right', title:'操作', width:'15%', align:'center', toolbar: '#barDemo'},
        ]]
        ,done:function(res,curr,count){
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
        ,where : {
            parentId : '3'
        }
        //,height : "450"
        ,height : "full-160"
        ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
            //,curr: 5 //设定初始在第 5 页
            ,groups: 1 //只显示 1 个连续页码
            ,first: false //不显示首页
            ,last: false //不显示尾页
        }
    });

    tableIns1 = table.render({
        elem: '#table1'
        ,url: basePath + 'dict/queryDictDataInfo'
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
            {field:'dictValue', title:'字典键值', width:'25%', align:'center'}
            ,{field:'dictLabel', title:'字典标签', width:'35%', align:'center'}
            ,{field:'valId', title:'状态', width:'17%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.valId == 0){
                        return "停用";
                    }else if(data.valId == 1){
                        return "正常";
                    }
                }
            }
            ,{field:'right', title:'操作', width:'22.7%', align:'center', toolbar: '#barDemo1'},
        ]]
        ,done:function(res,curr,count){
            $('th').css({ 'color': 'black', 'font-weight': 'bold'})//用于设置表头的样式，th即代表表头
            $(".layui-table-box").css("border-width","0px");
            $(".layui-table-header tr").css("height","25px");
            $(".layui-table-header tr span").css("color","#666666");
            $(".layui-table-body tr").css("height","25px");
            $(".layui-form-checkbox").css("style","margin-top: 5px;");
        }
        ,where : {
            dictType:'99999'
        }
        ,height :("full-"+($(".news_right").height()-348))
        ,page: false
    });
});

$("#addDictType").click(function(){
    top.layer.open({
        type: 2,
        title: "新增字典类型",
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        area: ['800px', '500px'],
        content: basePath + '/dict/saveOrEditDictType',
        end: function () {//层消失回调
            layReload(1);
        }
    });
});

$("#addDictData").click(function(){
    if($("#dictType").val() == null || $("#dictType").val() == ""){
        layer.msg("请选择字典类别！");
    }else{
        top.layer.open({
            type: 2,
            title: "新增字典数据",
            shadeClose: true,
            shade: 0.5,
            closeBtn:1,
            area: ['800px', '600px'],
            content: basePath + '/dict/saveOrEditDictData?type=add&dictType='+$("#dictType").val(),
            end: function () {//层消失回调
                layReload1();
            }
        });
    }
});

//查询
$("#query").on("click",function(){
    layReload(1);
});

//查询条件
function layReload(page){
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            dictName:$("#codeValue").val(),
            dictType:$("#codeData").val()
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

//查询条件
function layReload1(){
    tableIns1.reload({
        where: { //设定异步数据接口的额外参数，任意设
            dictType:$("#dictType").val()
        }
    });
}