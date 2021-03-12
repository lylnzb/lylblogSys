var type = GetQueryString("type");
var musicId = GetQueryString("musicId");

layui.use(['form','layer','jquery','table', 'laydate', 'element'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery
        ,upload = layui.upload;

    findCodeValue(form);

    //普通图片上传
    upload.render({
        elem: '#blog-image'
        ,url: basePath + '/webMusic/uploadMusicFile' //改成您自己的上传接口
        ,type:"POST"
        ,data: {
            musicId: function() {
                return $("input:hidden[name='musicId']").val();
            },
            fileType: function() {
                return "image";
            }
        }
        ,accept: 'images'
        ,done: function(res){
            var data = res.data[0];
            //如果上传成功
            if(res.code == 0){
                $("#musicId").val(data.musicId);
                $('#icon').attr('src', basePath + "musicfile/" + data.fileUrl); //
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

    upload.render({
        elem: '#uploadMusic'
        ,url: basePath + 'webMusic/uploadMusicFile' //改成您自己的上传接口
        ,type:"POST"
        ,data: {
            musicId: function() {
                return $("input:hidden[name='musicId']").val();
            },
            fileType: function() {
                return "audio";
            }
        }
        ,accept: 'file' //音频
        ,exts: 'mp3|m4a'
        ,done: function(res){
            var data = res.data[0];
            //如果上传成功
            if(res.code == 0){
                $("#musicId").val(data.musicId);
                $("#length").val(data.length);
                $('#downloadMusic').show();
                $('#downloadMusic').click(function () {
                    download('webMusic/downloadMusicFile?musicId='+data.musicId+'&fileType='+data.fileType);
                });
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

    upload.render({
        elem: '#uploadLyrics'
        ,url: basePath + 'webMusic/uploadMusicFile' //改成您自己的上传接口
        ,type:"POST"
        ,data: {
            musicId: function() {
                return $("input:hidden[name='musicId']").val();
            },
            fileType: function() {
                return "lyrics";
            }
        }
        ,accept: 'file' //文件
        ,exts: 'lrc'
        ,done: function(res){
            var data = res.data[0];
            //如果上传成功
            if(res.code == 0){
                $("#musicId").val(data.musicId);
                $("#lyrics").val(data.lyrics);
                $('#downloadLyrics').show();
                $('#downloadLyrics').click(function () {
                    download('webMusic/downloadMusicFile?musicId='+data.musicId+'&fileType='+data.fileType);
                });
                return layer.msg('上传成功');
            }else{
                return layer.msg('上传失败');
            }
        },
        error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    form.verify({
        musicName:function(value) {
            if (value.length < 1) {
                return '歌曲名称不能为空！';
            }
        },
        singer:function(value) {
            if (value.length < 1) {
                return '歌手不能为空！';
            }
        },
        length:function(value) {
            if (value.length < 1) {
                return '请上传歌曲文件！';
            }
        },
        lyrics:function(value) {
            if (value.length < 1) {
                return '请上传歌词文件！';
            }
        },
        languages:function(value) {
            if (value.length < 1) {
                return '语种不能为空！';
            }
        },
        style:function(value) {
            if (value.length < 1) {
                return '歌曲风格不能为空！';
            }
        },
        gedan:function(value) {
            if (value.length < 1) {
                return '请选择所属歌单！';
            }
        }
    });

    if(type == "update"){
        $("#isAccord").hide();
        $.ajax({
            url: basePath + "webMusic/queryMusicInfo",
            data:JSON.stringify({"musicId":musicId}),
            type:"POST",
            async:false,
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                console.log(resultData);
                var data = resultData.data[0];
                $("#musicId").val(data.musicId);
                $("#length").val(data.length);
                $("#lyrics").val(data.lyrics);
                if(null != data.coverUrl && '' != data.coverUrl){
                    $("#icon").attr("src",basePath + "/musicfile/"+data.coverUrl);
                }
                $("#musicName").val(data.musicName);
                $("#singer").val(data.singer);
                $("#valid").find('input[value=' + data.valid + ']').prop("checked",true);
                $("#description").val(data.description);
                $("#languages").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.languages + ']').click();
                $("#style").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.style + ']').click();
                $("#gedan").siblings("div.layui-form-select").find('dl').find('dd[lay-value=' + data.gedan + ']').click();
                $('#downloadMusic').show();
                $('#downloadMusic').click(function () {
                    download('webMusic/downloadMusicFile?musicId='+data.musicId+'&fileType=audio');
                });
                $('#downloadLyrics').show();
                $('#downloadLyrics').click(function () {
                    download('webMusic/downloadMusicFile?musicId='+data.musicId+'&fileType=lyrics');
                });
                form.render();
            }
        });
    }

    //监听提交
    form.on('submit(save)', function(data){
        var musicId = $("#musicId").val();
        var musicName = data.field.musicName;//歌曲名称
        var singer = data.field.singer;//歌手
        var languages = data.field.languages;//语种
        var style = data.field.style;//风格
        var gedan = data.field.gedan;//所属工单
        var length = data.field.length;//时长
        var lyrics = data.field.lyrics;//歌词
        var description = data.field.description;//歌曲说明
        var valId = data.field.sys_normal_disable;//状态
        var paramData = {
            musicId : musicId,
            musicName : musicName,
            singer : singer,
            languages : languages,
            style : style,
            gedan : gedan,
            length : length,
            lyrics : lyrics,
            description : description,
            valid : valId
        }
        $.ajax({
            url: basePath + "webMusic/addOrUpdateMusicInfo?type="+type,
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
    loadSelect("#languages","web_music_language", form);
    loadSelect("#style","web_music_style", form);
    loadSelect("#gedan","web_music_gedan", form);
    loadRadio("#valid", "sys_normal_disable", form);
}
