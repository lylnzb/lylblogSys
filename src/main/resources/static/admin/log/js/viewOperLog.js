var logId = GetQueryString("logId");

layui.use(['form', 'layer'], function() {
    var form = layui.form, $ = layui.jquery;

    $.ajax({
        url: basePath + "admin/operLog/queryOperLogDetailInfo?logId=" + logId,
        type:"POST",
        success:function(resultData){
            console.log(resultData);
            $("#operModule").val(resultData.obj.operModule);
            $("#loginInfo").val(resultData.obj.loginInfo);
            $("#requestUrl").val(resultData.obj.requestUrl);
            $("#operMethod").val(resultData.obj.operMethod);
            $("#requestParam").val(resultData.obj.requestParam);
            if(resultData.obj.status == '2'){
                $("#error").show();
                $("#errorMsg").val(resultData.obj.errorMsg);
            }
            $("#valid").find('input[value=' + resultData.obj.status + ']').prop("checked",true);

            form.render("radio");
        }
    });
});
