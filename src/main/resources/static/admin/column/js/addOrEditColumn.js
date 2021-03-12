var columnId = GetQueryString("columnId");
var type = GetQueryString("type");
top.config.thirdIframe = window.document;

layui.use(['form', 'layer'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,$ = layui.jquery;

    findCodeValue(form);

    //此处即为 radio 的监听事件
    form.on('radio(judgeM)', function(data){
        var level = data.value;//被点击的radio的value值
        if(level == "1"){
            $("#isAccord").hide();
            $("#columnUrl").attr({"readonly":"readonly"});
            $("#columnUrl").val("*");
            $("#iconUrl").val("");
        }else{
            $("#isAccord").show();
            $("#columnUrl").removeAttr("readonly");
            $("#columnUrl").val("");
        }
    });

    //自定义表单认证只需要非空认证可以直接在元素中添加属性lay-verify="required"
    //test指表单元素lay-verify="test"标识
    form.verify({
        columnIcon:function(value) {
            if (value.length < 1) {
                return '请选择栏目图标！';
            }
        },
        columnName:function(value) {
            if (value.length < 1) {
                return '栏目名称不能为空！';
            }
        },
        columnUrl:function(value) {
            if (value.length < 1) {
                return '栏目路径不能为空！';
            }
        },
        keywords:function(value) {
            if (value.length < 1) {
                return '栏目关键词不能为空！';
            }
        },
        description:function(value) {
            if (value.length < 1) {
                return '栏目说明不能为空！';
            }
        }
    });

    if(type == "update"){
        $.ajax({
            url: basePath + "webColumn/queryWebColumnInfoById?columnId=" + columnId,
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#iconUrl").val(data.columnIcon);
                $("#parentId").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.parentId + ']').click();
                $("#columnName").val(data.columnName);
                $("#columnUrl").val(data.columnUrl);
                $("#keywords").val(data.keywords);
                $("#orderBy").val(data.orderBy);
                $("#isDefault").find('input[value=' + data.isDefault + ']').prop("checked",true);
                $("#isHidden").find('input[value=' + data.isHidden + ']').prop("checked",true);
                $("#isAllow").find('input[value=' + data.isAllow + ']').prop("checked",true);
                $("#description").val(data.description);
                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var columnIcon = data.field.iconUrl;//栏目图标
        var parentId = data.field.parentId;//所属父栏目
        var columnName = data.field.columnName;//栏目名称
        var columnUrl = data.field.columnUrl;//栏目路径
        var keywords = data.field.keywords;//栏目关键词
        var orderBy = data.field.orderBy;//排列顺序
        var isDefault = data.field.web_column_attribute;//栏目属性
        var isHidden = data.field.sys_hide_show;//栏目状态
        var isAllow = data.field.sys_column_yes_no;//是否允许发布文章
        var description = data.field.description;//栏目说明
        var paramData = {
            columnId : null == columnId ? "" : columnId,
            columnIcon : columnIcon,
            parentId : parentId,
            columnName : columnName,
            columnUrl : columnUrl,
            keywords : keywords,
            orderBy : orderBy,
            isDefault : isDefault,
            isHidden : isHidden,
            isAllow : isAllow,
            description : description
        }
        $.ajax({
            url: basePath + "webColumn/addOrUpdateWebColumnInfo?type="+type,
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

//字典初始化
function findCodeValue(form){
    loadRadio("#isDefault", "web_column_attribute", form, "judgeM");
    loadRadio("#isHidden", "sys_hide_show", form);
    loadRadio("#isAllow", "sys_column_yes_no", form);

    $.ajax({
        url:basePath+'/webColumn/queryParentColumn',
        type:"POST",
        async:false,
        success:function(data){
            for(var i = 0; i<data.data.length;i++){
                $("#parentId").append(new Option(data.data[i].columnName , data.data[i].columnId));
                //下拉菜单渲染 把内容加载进去
                form.render();
            }
        },
        error:function(){
            alert("初始化选项失败");
        }
    });
}

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
        content: '../semantic-ui/icon.html'
    });
});