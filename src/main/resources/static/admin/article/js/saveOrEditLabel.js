var labelId = GetQueryString("labelId");
var type = GetQueryString("type");

layui.use(['form', 'layer'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,$ = layui.jquery;

    findCodeValue(form);

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        labelName:function(value) {
            if (value.length < 1) {
                return '标签名称不能为空！';
            }
        },
        columnId:function(value) {
            if (value.length < 1) {
                return '请选择专栏！';
            }
        }
    });

    if(type == "update"){
        $.ajax({
            url: basePath + "article/queryLabelInfo",
            type:"POST",
            data:JSON.stringify({"labelId":labelId}),
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#labelName").val(data.labelName);
                $("#labelRemarks").val(data.labelRemarks);
                $("#description").val(data.description);
                $("#columnId").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.columnId + ']').click();
                $("#isAllow").find('input[value=' + data.isAllow + ']').prop("checked",true);
                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var labelName = data.field.labelName;//标签名称
        var columnId = data.field.columnId;//所属专栏
        var labelRemarks = data.field.labelRemarks;//标签寄语
        var valid = data.field.sys_label_status;//是否可用
        var description = data.field.description;//标签描述
        var paramData = {
            labelId : (null == labelId || ''  == labelId) ? "" : labelId,
            labelName : labelName,
            columnId : columnId,
            labelRemarks : labelRemarks,
            valid : valid,
            description : description
        }
        $.ajax({
            url: basePath + "article/addOrUpdaLabelInfo?type=" + type,
            type:"POST",
            data:JSON.stringify(paramData),
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

    //字典初始化
    function findCodeValue(form){
        loadRadio("#isAllow", "sys_label_status", form);
        loadSelectAllow("#columnId", form);
    }

    $("#close").on("click", function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前页
    });
});