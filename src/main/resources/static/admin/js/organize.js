/**
 * Created by Administrator on 2016/8/4.
 */
var setting = {
    view: {
        dblClickExpand: true,
        selectedMulti : true,//可以多选
        showLine: true
    },
    check: {
        enable: false,   //true / false 分别表示 显示 / 不显示 复选框或单选框
        chkStyle: "checkbox"  //勾选框类型(checkbox 或 radio）
    },
    callback: {
        onRightClick: OnRightClick,
        onClick:OnClick
    }
};
var zNodes =[];
var tableIns;
var ind;
var zTree, rMenu;
top.config.thirdIframe = window.document;

$(document).ready(function(){
    nav();
    init();
});

layui.use(['form', 'table','tree'], function(){
    var table = layui.table,
        form = layui.form;

    // select下拉框选中触发事件
    form.on("select(college)", function(data){
        console.log(data);
        if(data.value == '1' || data.value == '2'){
            $("#permUrl").attr({"readonly":"readonly"});
            $("#permUrl").val("*");
        }else {
            $("#permUrl").removeAttr("readonly");
            $("#permUrl").val("");
        }
    });

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        couponType:function(value) {
            if (value.length < 1) {
                return '请选择上级菜单！';
            }
        },
        iconUrl:function(value) {
            if ($("#permType").val() != '2' && value.length < 1) {
                return '图标代码不能为空！';
            }
        },
        permname:function(value) {
            if (value.length < 1) {
                return '权限名称不能为空！';
            }
        },
        permType:function(value) {
            if (value.length < 1) {
                return '权限类型不能为空！';
            }
        },
        permUrl:function(value) {
            if (value.length < 1) {
                return '菜单路径不能为空！';
            }
        },
        permControl:function(value) {
            if (value.length < 1) {
                return '授权标识不能为空！';
            }
        }
    });

    //监听提交
    form.on('submit(save)', function(data){
        var permId = $("#permId").val();//权限id
        var parentId = $("#parentId").val();//父权限id
        var couponType = $("#grainProperty").val();//上级菜单
        var iconUrl = $("#iconUrl").val();//图标代码
        var permname = $("#permname").val();//权限名称
        var permType = $("#permType").val();//权限类型
        var permUrl = $("#permUrl").val();//菜单路径
        var permControl = $("#permControl").val();//授权标识
        var permOrder = $("#permOrder").val();//菜单顺序位置
        var permdesc = $("#permdesc").val();//权限描述
        var data = {
            permId : permId,
            parentId : parentId,
            couponType : couponType,
            iconUrl : iconUrl,
            permname : permname,
            permType : permType,
            permUrl : permUrl,
            permission : permControl,
            permOrder : permOrder,
            permdesc : permdesc
        }
        $.ajax({
            url: basePath + "admin/addPermInfo?type=update",
            type:"POST",
            data:JSON.stringify(data),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                if(resultData.code==0){
                    parent.layer.alert(resultData.msg);
                    init();
                }else{
                    parent.layer.alert(resultData.msg);
                }
            }
        });
    });

    tableIns = table.render({
        elem: '#table'
        ,url: basePath + 'admin/qeuryPermInfoByConditions'
        ,method: 'post'
        ,contentType: "application/json; charset=utf-8"
        ,dataType:"json"
        ,cols: [[
             {checkbox: true, id:"idTest", width:'2%'}
            ,{field:'permId', title:'编号', width:'6%', align:'center'}
            ,{field:'permname', title:'权限名称', width:'20%', align:'center'}
            ,{field:'permType', title:'类型', width:'15%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.permType == 0){
                        return "目录";
                    }else if(data.permType == 1){
                        return "菜单";
                    }else{
                        return "按钮";
                    }
                }
             }
            ,{field:'valid', title:'有效标识', width:'18%', align:'center',
                templet : function(data) {// 替换数据
                    if(data.valid == 0){
                        return "无效";
                    }else if(data.valid == 1){
                        return "有效";
                    }
                }
             }
            ,{field:'permdesc', title:'描述', width:'39%', align:'center'}
        ]]
        ,id:"idTest"
        ,done:function(res,curr,count){
            layer.close(ind);
        }
        ,where : {
            parentId : '99999'
        }
        ,height : "230"
        ,page: false
    });


});

//取消权限功能
$("#cancelPerm").click(function() {
    var checkStatus = layui.table.checkStatus("idTest")
        ,data = checkStatus.data;
    if(data.length<1) {
        parent.layer.msg("请至少选择一条数据！");
    }else {
        var lock = false; //默认未锁定
        layer.confirm("确定要批量取消权限吗？", {
            btn: ["确定","取消"], //按钮
            title: "提示",
            icon: 3
        }, function(index){
            if(!lock) {
                lock = true;
                var permIds = [];
                for(var i = 0;i < data.length;i++){
                    var obj = new Object();
                    obj.permId = data[i].permId;
                    permIds.push(obj);
                }
                $.ajax({
                    url: basePath + "admin/cancelOrRestorePermInfo?valid=0",
                    type:"POST",
                    data:JSON.stringify(permIds),
                    dataType:"json",
                    contentType : 'application/json;charset=utf-8',
                    success:function(resultData){
                        if(resultData.code==0){
                            parent.layer.alert("权限取消成功");
                            init();
                            layReload($('#permId').val());
                        }else{
                            parent.layer.alert("权限取消失败");
                        }
                    }
                });
                layer.close(index);
            }
        }, function(){

        });
    }
});

//恢复权限功能
$("#restorePerm").click(function() {
    var checkStatus = layui.table.checkStatus("idTest")
        ,data = checkStatus.data;
    if(data.length<1) {
        parent.layer.msg("请至少选择一条数据！");
    }else {
        var lock = false; //默认未锁定
        layer.confirm("确定要批量恢复权限吗？", {
            btn: ["确定","取消"], //按钮
            title: "提示",
            icon: 3
        }, function(index){
            if(!lock) {
                lock = true;
                var permIds = [];
                for(var i = 0;i < data.length;i++){
                    var obj = new Object();
                    obj.permId = data[i].permId;
                    permIds.push(obj);
                }
                $.ajax({
                    url: basePath + "admin/cancelOrRestorePermInfo?valid=1",
                    type:"POST",
                    data:JSON.stringify(permIds),
                    dataType:"json",
                    contentType : 'application/json;charset=utf-8',
                    success:function(resultData){
                        if(resultData.code==0){
                            parent.layer.alert("权限恢复成功");
                            init();
                            layReload($('#permId').val());
                        }else{
                            parent.layer.alert("权限恢复失败");
                        }
                    }
                });
                layer.close(index);
            }
        }, function(){

        });
    }
});

//弹窗
$("#iconUrl").on("click", function () {
    // openWin('../semantic-ui/icon.html', '选择图标', null, '1800','900');
    var index = top.layer.open({
        type: 2,
        title: '选择图标',
        shadeClose: true,
        shade: 0.5,
        closeBtn:1,
        maxmin: true,
        area: ['100%', '100%'],
        content: '../common/semantic-ui/icon.html'
    });
});

//新增权限
$("#addPerm").click(function(){
    if($("#dropdown_select").val() == null || $("#dropdown_select").val() == ''){
        layer.msg("请选择树节点！");
    }else{
        top.layer.open({
            type: 2,
            title: '新增权限',
            shadeClose: true,
            shade: 0.5,
            closeBtn:1,
            area: ['700px', '400px'],
            content: basePath + '/admin/perm/addOrUpdaPermission?parentName='+$("#dropdown_select").val()+'&parentId=' + $('#permId').val(),
            end: function () {//层消失回调
                init();
                layReload($('#permId').val());
            }
        });
    }
});

function nav(){
    $(".news_nav li").each(function(index){
        $(this).click(function(){
            $(".news_nav li").removeClass("nav_active");
            $(this).addClass("nav_active");
            $(".news_table>li").eq(index).show().siblings("li").stop().hide();
        })
    })
}

function init(){
    $.ajax({
        url: basePath + "admin/queryPermInfoToTree",
        type:"POST",
        async:false,
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            zNodes = resultData.data[0];
        }
    });
    //树形菜单初始化
    $.fn.zTree.init($("#treeDemo") , setting, zNodes);
    zTree = $.fn.zTree.getZTreeObj("treeDemo");
    rMenu = $("#rMenu");
}

function OnClick(event, treeId, treeNode){
    $("#dropdown_select").val(treeNode.permName);
    layReload(treeNode.id);
    qeuryPermInfoDeail(treeNode.id);
    disableButton(treeNode.permType);
}

//查询条件
function layReload(parentId){
    ind=layer.load(1);
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            parentId : parentId
        }
    });
}

//查询权限详情
function qeuryPermInfoDeail(permId){
    $.ajax({
        url: basePath + "admin/qeuryPermInfoByConditions",
        type:"POST",
        data:JSON.stringify({permId:permId}),
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            $("#permId").val(resultData.data[0].permId);
            $("#grainProperty").val(resultData.data[0].parentName);
            $("#parentId").val(resultData.data[0].parentId);
            $("#iconUrl").val(resultData.data[0].iconUrl);
            $("#permname").val(resultData.data[0].permname);
            $("#permType").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + resultData.data[0].permType + ']').click();
            $("#permUrl").val(resultData.data[0].permUrl);
            $("#permControl").val(resultData.data[0].permission);
            $("#permOrder").val(resultData.data[0].permOrder);
            $("#permdesc").val(resultData.data[0].permdesc);
        }
    });
}

//按钮禁用
function disableButton(permType){
    if(permType == '2'){
        $("#addPerm").attr("disabled","true");
    }else{
        $("#addPerm").removeAttr("disabled");
    }
}

function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        //showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        zTree.selectNode(treeNode);
        //showRMenu("node", event.clientX, event.clientY);
    }
}

function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type=="root") {
        $("#m_del").hide();
        $("#m_check").hide();
        $("#m_unCheck").hide();
    } else {
        $("#m_del").show();
        $("#m_check").show();
        $("#m_unCheck").show();
    }
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
    $("body").bind("mousedown", onBodyMouseDown);
}

function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}

function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
    }
}

var addCount = 1;
//添加事件
function addTreeNode(names) {
    hideRMenu();
    var newNode = { name:names + (addCount++)};
    if (zTree.getSelectedNodes()[0]) {
        newNode.checked = zTree.getSelectedNodes()[0].checked;
        zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
    } else {
        zTree.addNodes(null, newNode);
    }
}

function removeTreeNode() {
    hideRMenu();
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length>0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            if (confirm(msg)==true){
                zTree.removeNode(nodes[0]);
            }
        } else {
            zTree.removeNode(nodes[0]);
        }
    }
}

function checkTreeNode(checked) {
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length>0) {
        zTree.checkNode(nodes[0], checked, true);
    }
    hideRMenu();
}

function resetTree() {
    hideRMenu();
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}


