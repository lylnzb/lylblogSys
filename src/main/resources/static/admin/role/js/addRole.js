var setting = {
    view: {
        dblClickExpand: false
    },
    check: {
        enable: true
    },
    callback: {
        onRightClick: OnRightClick,
        onClick:OnClick
    }
};
var zNodes =[];
var zTree, rMenu,zTreeObj;
var type = GetQueryString("type");
var roleId = GetQueryString("roleId");

function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        //showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        zTree.selectNode(treeNode);
        //showRMenu("node", event.clientX, event.clientY);
    }
}

function OnClick(event, treeId, treeNode){

}

layui.use(['form','table','tree'], function(){
    var form = layui.form;

    findCodeValue(form);

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        rolename:function(value) {
            if (value.length < 1) {
                return '角色名称不能为空！';
            }
        },
        roleKey:function(value) {
            if (value.length < 1) {
                return '权限字符不能为空！';
            }
        }
        ,
        orderBy:function(value) {
            if (value.length < 1) {
                return '显示顺序不能为空！';
            }
        }
    });

    $.ajax({
        url: basePath + "admin/queryPermInfoToTree",
        type:"POST",
        async:false,
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            zNodes = resultData.data[0];
            //树形菜单初始化
            zTreeObj = $.fn.zTree.init($("#treeDemo") , setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
            rMenu = $("#rMenu");
        }
    });

    if(type == "update"){
        $.ajax({
            url: basePath + "admin/queryRoleList",
            data:JSON.stringify({"roleid":roleId}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                $("#rolename").val(resultData.data[0].rolename);
                $("#roledesc").val(resultData.data[0].roledesc);
                $("#roleKey").val(resultData.data[0].rolekey);
                $("#orderBy").val(resultData.data[0].orderBy);
                $("#valid").find('input[value=' + resultData.data[0].valid + ']').prop("checked",true);
                for(var i = 0;i < resultData.data[0].treeList.length;i++){
                    zTree.checkNode(zTree.getNodeByParam("id", resultData.data[0].treeList[i]), true);//权限选中
                }
                form.render("radio");
            }
        });
    }else{

    }

    //监听提交
    form.on('submit(save)', function(data){
        var role = data.field;
        var rolename = role.rolename;
        var roledesc = role.roledesc;
        var roleKey = role.roleKey;
        var orderBy = role.orderBy;
        var valId = role.sys_normal_disable;//状态
        var list = [];

        var checkedObjs = zTreeObj.getCheckedNodes(true);  //获取所有选中的节点
        if(checkedObjs.length < 1){
            layer.msg("请选择权限！");
            return false;
        }

        for(var i=0;i<checkedObjs.length;i++) {
            var obj = checkedObjs[i];//将选中的值放到input中传到后台
            list.push(obj.id);
        }
        var data = {
            roleid : roleId,
            rolename : rolename,
            roledesc : roledesc,
            roleKey : roleKey,
            orderBy : orderBy,
            valid : valId,
            treeList : list
        }
        $.ajax({
            url:basePath + "admin/addRoleInfo?type="+type,
            type:"POST",
            data:JSON.stringify(data),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                if(resultData.code==0){
                    parent.layer.alert(resultData.msg, {icon:2});
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭当前页
                }else{
                    parent.layer.alert(resultData.msg);
                }
            }
        });
    });

    // 获取选中节点的id
    function getChecked_list(data) {
        var id = "";
        $.each(data, function (index, item) {
            if (id != "") {
                id = id + "," + item.id;
            }
            else {
                id = item.id;
            }
            if(undefined == item.children){
                return;
            }
            var i = getChecked_list(item.children);
            if (i != "") {
                id = id + "," + i;
            }
        });
        return id;
    }

    //字典初始化
    function findCodeValue(form){
        loadRadio("#valid", "sys_normal_disable", form);
    }
});