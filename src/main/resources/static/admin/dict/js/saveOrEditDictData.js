var dictCode  = GetQueryString("dictCode");
var dictType = GetQueryString("dictType");
var type = GetQueryString("type");

layui.use(['form', 'layer'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,$ = layui.jquery;

    $("#dictType").val(dictType);

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        dictLabel:function(value) {
            if (value.length < 1) {
                return '字典标签不能为空！';
            }
        },
        dictValue:function(value) {
            if (value.length < 1) {
                return '字典键值不能为空！';
            }
        },
        dictType:function(value) {
            if (value.length < 1) {
                return '字典类型不能为空！';
            }
        }
    });

    if(type == "update"){
        $.ajax({
            url: basePath + "dict/queryDictDataInfo",
            data:JSON.stringify({"dictCode":dictCode}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#dictLabel").val(data.dictLabel);
                $("#dictValue").val(data.dictValue);
                $("#dictSort").val(data.dictSort);
                $("#remark").val(data.remark);
                $("#isDefault").find('input[value=' + resultData.data[0].isDefault + ']').prop("checked",true);
                $("#valid").find('input[value=' + resultData.data[0].valId + ']').prop("checked",true);

                form.render("radio");
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var dictLabel = data.field.dictLabel;//字典标签
        var dictValue = data.field.dictValue;//字典键值
        var dictType = data.field.dictType;//字典类型
        var dictSort = data.field.dictSort;//字典排序
        var isDefault = data.field.isDefault;//系统默认
        var valId = data.field.valid;//状态
        var remark = data.field.remark;//备注
        var paramData = {
            dictCode : (type == 'update') ? dictCode : '',
            dictLabel : dictLabel,
            dictValue : dictValue,
            dictType : dictType,
            dictSort : dictSort,
            isDefault : isDefault,
            valId : valId,
            remark : remark
        }
        $.ajax({
            url: basePath + "dict/addOrEditDictDataInfo?type="+type,
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