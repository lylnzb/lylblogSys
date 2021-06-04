var commentId = GetQueryString("commentId");
var type = GetQueryString("type");
if(type == '2'){
    $("#isHidden").hide();
}

layui.use(['form','layer','jquery','table', 'laydate', 'element'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery
        ,upload = layui.upload;

    $.ajax({
        url: basePath + "admin/comment/queryCommentInfo",
        data:JSON.stringify({"commentId":commentId}),
        type:"POST",
        async:false,
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            console.log(resultData);
            $("#submitName").val(resultData.data[0].submitName);
            $("#replyName").val((null != resultData.data[0].replyName && "" != resultData.data[0].replyName)?resultData.data[0].replyName:"无");
            $("#articleName").val(resultData.data[0].articleName);
            $("#submitTime").val(resultData.data[0].submitTime);
            $("#commentContent").html(resultData.data[0].commentContent);
        }
    });
});