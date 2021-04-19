var addOrUpdaType = GetQueryString("type");
var id = GetQueryString("id");

layui.use(['form','layer','jquery','table', 'laydate', 'element'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery
        ,upload = layui.upload;

    findCodeValue(form);

    form.verify({
        title:function(value) {
            if (value.length < 1) {
                return '网站名称不能为空！';
            }
        },
        url:function(value) {
            if (value.length < 1) {
                return '网站地址不能为空！';
            }
        },
        type:function(value) {
            if (value.length < 1) {
                return '网站类型不能为空！';
            }
        }
    });

    if(addOrUpdaType == "update"){
        $("#isAccord").hide();
        $.ajax({
            url: basePath + "link/queryLinkInfo",
            data:JSON.stringify({"id":id}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#title").val(data.title);
                $("#url").val(data.url);
                $("#type").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.type + ']').click();
                $("#sortOrder").val(data.sortOrder);
                $("#target").find('input[value=' + data.target + ']').prop("checked",true);
                $("#status").find('input[value=' + data.status + ']').prop("checked",true);
                $("#intro").val(data.intro);
                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var title = data.field.title;//网站名称
        var url = data.field.url;//网站地址
        var type = data.field.type;//网站类型
        var sortOrder = data.field.sortOrder;//排序
        var target = data.field.sys_link_yes_no;//是否开启浏览器新窗口
        var status = data.field.sys_link_show;//是否显示
        var intro = data.field.intro;//网站简况
        var paramData = {
            id : null == id ? "" : id,
            title : title,
            url : url,
            type : type,
            sortOrder : sortOrder,
            target : target,
            status : status,
            intro : intro
        }
        $.ajax({
            url: basePath + "link/addOrUpdaLinkInfo?type="+addOrUpdaType,
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
                    parent.layer.msg(resultData.msg, { icon: 2, time: 2000, shift: 6 });
                }
            }
        });
    });
});

//字典初始化
function findCodeValue(form){
    loadSelect("#type","sys_link_type", form);
    loadRadio("#target", "sys_link_yes_no", form);
    loadRadio("#status", "sys_link_show", form);
}
