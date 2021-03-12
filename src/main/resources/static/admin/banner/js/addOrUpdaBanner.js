var type = GetQueryString("type");
var bannerId = GetQueryString("bannerId");

layui.use(['form','layer','jquery','table', 'laydate', 'element'], function(){
    var form = layui.form
        ,$ = layui.jquery;

    findCodeValue(form);

    form.verify({
        isUploadBannerImg:function(value) {
            if (null == $("#showBannerImg").attr("src") || "" == $("#showBannerImg").attr("src")) {
                return '请上传轮播图！';
            }
        },
        bannerTitle:function(value) {
            if (value.length < 1) {
                return '轮播图标题不能为空！';
            }
        },
        bannerUrl:function(value) {
            if (value.length < 1) {
                return '轮播图url不能为空！';
            }
        },
        valid:function(value) {
            if (value.length < 1) {
                return '请选择是否展示！';
            }
        }
    });

    if(type == "update"){
        $.ajax({
            url: basePath + 'banner/queryBannerInfo',
            data:JSON.stringify({"bannerId":bannerId}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                //$("#musicId").val(data.musicId);
                if(data.bannerImg != null && data.bannerImg != ''){
                    $("#uploadBannerView").show();
                    $("#showBannerImg").attr("src", data.bannerImg);
                }
                $("#bannerTitle").val(data.bannerTitle);
                $("#bannerUrl").val(data.bannerUrl);
                $("#orderBy").val(data.orderBy);
                $("#valid").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.valid + ']').click();
                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var bannerImg = $("#showBannerImg").attr("src");     //轮播图
        var bannerTitle = data.field.bannerTitle;            //歌曲名称
        var bannerUrl = data.field.bannerUrl;                //歌手
        var valid = data.field.valid;                        //语种
        var orderBy = data.field.orderBy;                    //风格
        var paramData = {
            bannerId: (type == 'update') ? bannerId : '',
            bannerImg: bannerImg,
            bannerTitle: bannerTitle,
            bannerUrl: bannerUrl,
            valid: valid,
            orderBy: orderBy
        }
        console.log(paramData);
        $.ajax({
            url: basePath + "banner/addOrUpdaBannerInfo?type="+type,
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
    loadSelect("#valid", "sys_banner_show", form);
}

function viewImg(file){
    var reader = new FileReader();	// 实例化一个FileReader对象，用于读取文件
    var img = document.getElementById('showBannerImg'); 	// 获取要显示图片的标签

    //读取File对象的数据
    reader.onload = function(evt){
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
    thumb = "";
    $("#uploadBannerView").show();
}

$("#closeBannerImgView").click(function(){
    $("#showBannerImg").attr("src", "");
    $("#uploadBannerView").hide();
});