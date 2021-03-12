var type = GetQueryString("type");
//var yhnm = GetQueryString("yhnm");
var userId = GetQueryString("userId");

layui.use(['form','layer','jquery','table', 'laydate', 'element'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery
        ,upload = layui.upload;

    findCodeValue(form);

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#blog-image'
        ,url: basePath + '/admin/uploadIcon' //改成您自己的上传接口
        ,type:"POST"
        ,data: {
            yhnm: function() {
                return $("input:hidden[name='yhnm']").val();
            }
        }
        ,accept: 'images'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#icon').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传成功
            if(res.code == 0){
                $("#yhnm").val(res.data[0].split("_")[0]);
                return layer.msg('上传成功');
            }else{
                return layer.msg('上传失败');
            }
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    form.verify({
        nickname:function(value) {
            if (value.length < 1) {
                return '昵称不能为空！';
            }
        },
        email:function(value) {
            if (value.length < 1) {
                return '邮箱不能为空！';
            }
            var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
            if(!myreg.test(value)){
                return '邮箱格式错误！';
            }
        },
        pwd:function(value) {
            if (value.length < 1) {
                return '密码不能为空！';
            }
        }
    });

    if(type == "update"){
        $("#isAccord").hide();
        $.ajax({
            url: basePath + "admin/queryUserList",
            data:JSON.stringify({"userId":userId}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#yhnm").val(data.yhnm);
                if(null != data.iconUrl && '' != data.iconUrl){
                    $("#icon").attr("src",basePath + "/profile/"+data.iconUrl);
                }
                $("#nickname").val(data.nickName);
                $("#email").val(data.email);
                $("#signature").val(data.signature);
                $("#sex").find('input[value=' + resultData.data[0].sex + ']').prop("checked",true);
                $("#roleid").find('input[value=' + resultData.data[0].roleId + ']').prop("checked",true);
                $("#valid").find('input[value=' + resultData.data[0].valid + ']').prop("checked",true);

                form.render("radio");
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var yhnm = $("#yhnm").val();
        var email = data.field.email;//用户邮箱
        var sex = data.field.sys_user_sex;//性别
        var nickName = data.field.nickname;//用户昵称
        var password = data.field.pwd;//用户密码
        var roleId = data.field.role;//角色id
        var signature = data.field.signature;//个性签名
        var valid = data.field.sys_normal_disable;//状态
        var paramData = {
            userId : userId,
            yhnm : yhnm,
            email : email,
            sex : sex,
            nickName : nickName,
            password : password,
            roleId : roleId,
            signature : signature,
            valid : valid
        }
        $.ajax({
            url: basePath + "admin/addOrEditUserInfo?type="+type,
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
});

//字典初始化
function findCodeValue(form){
    loadRadio("#sex", "sys_user_sex", form);
    loadRadio("#roleid", "role", form);
    loadRadio("#valid", "sys_normal_disable", form);
}

/**
 * 邮箱失焦事件
 */
$("#email").blur(function(){
    var value = $(this).val();
    if(value != null && value != ''){
        var falg = vailEmail(this,value);
    }
});

function vailEmail(elem,value){
    var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if(!myreg.test(value)){
        parent.layer.msg("邮箱格式错误！");
        return 1;
    }
    return 0;
}