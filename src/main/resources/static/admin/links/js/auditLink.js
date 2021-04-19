var addOrUpdaType = GetQueryString("type");
var id = GetQueryString("id");

layui.use(['form','layer','jquery','table', 'laydate', 'element'], function(){
    var form = layui.form
        ,$ = layui.jquery;

    form.verify({
        auditNotThrough:function(value) {
            if (value.length < 1) {
                return '请输入审核不通过原因！';
            }
        }
    });

    if(addOrUpdaType == "update"){
        $("#isAccord").hide();
        $.ajax({
            url: basePath + "admin/link/queryLinkInfo",
            data:JSON.stringify({"id":id}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);

                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(audit)', function(data){
        var auditNotThrough = data.field.auditNotThrough;//审核不通过原因
        var paramData = {
            id : id,
            auditPerson : auditNotThrough,
            auditStatus : "2"
        }
        $.ajax({
            url: basePath + "admin/link/auditLinkData",
            type:"POST",
            data:JSON.stringify(paramData),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                if(resultData.code==0){
                    parent.layer.msg(resultData.msg, { icon: 1, time: 2000, shift: 5 });
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭当前页
                }else{
                    //parent.layer.alert(resultData.msg);
                    parent.layer.msg(resultData.msg, { icon: 2, time: 2000, shift: 5 });
                }
            }
        });
    });
});
