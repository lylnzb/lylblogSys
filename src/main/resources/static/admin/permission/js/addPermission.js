var parentName = decodeURIComponent(GetQueryString("parentName"));
var parentId = GetQueryString("parentId");
top.config.thirdIframe = window.document;
$(function(){
    $("#grainProperty").val(parentName);
    $("#parentId").val(parentId);
});

layui.use(['form', 'layer'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,$ = layui.jquery;

    // select下拉框选中触发事件
    form.on("select(college)", function(data){
        console.log(data);
        if(data.value == '0' || data.value == '2'){
            $("#permUrl").attr({"readonly":"readonly"});
            $("#permUrl").val("*");
            $("#iconUrl").unbind("click");
            $("#iconUrl").val("*");
        }else {
            $("#permUrl").removeAttr("readonly");
            $("#permUrl").val("");
            $("#iconUrl").val("");
            //弹窗
            $("#iconUrl").on("click", function () {
                top.layer.open({
                    type: 2,
                    title: '选择图标',
                    shadeClose: true,
                    shade: 0.5,
                    skin: 'layui-layer-rim',
                    closeBtn:1,
                    maxmin: true,
                    area: ['100%', '100%'],
                    content: '../common/semantic-ui/icon.html'
                });
            });
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
        var parentId = $("#parentId").val();//上级权限id
        var iconUrl = $("#iconUrl").val();//图标代码
        var permname = $("#permname").val();//权限名称
        var permType = $("#permType").val();//权限类型
        var permUrl = $("#permUrl").val();//菜单路径
        var permControl = $("#permControl").val();//授权标识
        var permOrder = $("#permOrder").val();//菜单顺序位置
        var permdesc = $("#permdesc").val();//权限描述
        var data = {
            parentId : parentId,
            iconUrl : iconUrl,
            permname : permname,
            permType : permType,
            permUrl : permUrl,
            permission : permControl,
            permOrder : permOrder,
            permdesc : permdesc
        }
        $.ajax({
            url: basePath + "admin/addPermInfo?type=add",
            type:"POST",
            data:JSON.stringify(data),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                if(resultData.code==0){
                    parent.layer.alert(resultData.msg);
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭当前页
                }else{
                    parent.layer.alert(resultData.msg);
                }
            }
        });
    });

    //弹窗
    $("#iconUrl").on("click", function () {
        // openWin('../semantic-ui/icon.html', '选择图标', null, '1800','900');
        top.layer.open({
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

    $("#qk").on("click", function () {
        $("#treeclass").text("");
        $("#grainProperty").val("");//上级菜单
        $("#iconUrl").val("");//图标代码
        $("#permname").val("");//权限名称
        $("#permType").siblings("div.layui-form-select").find('dl').find("dd[lay-value='']").click();//权限类型
        $("#permUrl").val("");//菜单路径
        $("#permControl").val("");//授权标识
        $("#permOrder").val("");//菜单顺序位置
        $("#permdesc").val("");//权限描述
    });

    $("#close").on("click", function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前页
    });
});

function Cancel() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}