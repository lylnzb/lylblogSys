layui.use(['form', 'layer'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,$ = layui.jquery;

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        dictType:function(value) {
            if (value.length < 1) {
                return '类别编码不能为空！';
            }
        },
        dictType:function(value) {
            if (value.length < 1) {
                return '类别名称不能为空！';
            }
        }
    });

    //监听提交
    form.on('submit(save)', function(data){
        var dictType = data.field.dictType;//类别编码
        var dictName = data.field.dictName;//类别名称
        var valId = data.field.valid;//状态
        var remark = data.field.remark;//备注
        var paramData = {
            dictType : dictType,
            dictName : dictName,
            valId : valId,
            remark : remark
        }
        $.ajax({
            url: basePath + "dict/addOrEditDictTypeInfo?type=add",
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

    $("#close").on("click", function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前页
    });
});