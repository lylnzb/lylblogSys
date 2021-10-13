

function showImg(id,file){
    var reader = new FileReader();	// 实例化一个FileReader对象，用于读取文件
    var img = '';
    if(id == 1){
        img = document.getElementById('showimg1'); 	// 获取要显示图片的标签
    }else if(id == 2){
        img = document.getElementById('showimg2'); 	// 获取要显示图片的标签
    }
    //读取File对象的数据
    reader.onload = function(evt){
        img.width  =  "80";
        img.height =  "80";
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
    if(id == 1){
        $("#imageBox1").show();
    }else if(id == 2){
        $("#imageBox2").show();
    }

    uploadFile = file.files[0];
}

$(document).on('click','.reply',function(){
    console.log($(this).attr("data-id"));
    $(".replyBar").remove();
    var html = '';
    html += '<div class="replyBar" style="margin-top: 5px;">';
    html += '    <div id="reply" class="editor" placeholder="对我说点什么吧...." contenteditable="true"></div>';
    html += '    <div class="app">';
    html += '        <div class="emoji" style="display:inline-block;">';
    html += '            <i style="font-size: 30px;cursor:pointer;" class="smile icon emotion"></i>';
    html += '        </div>';
    html += '        <div style="display:inline-block;">';
    html += '            <div id="imageBox2" style="display: none">';
    html += '                <img id="showimg2" src="" alt=""/>';
    html += '                <i id="close2" class="close icon" onclick="$(\'#imageBox2\').hide()" style="margin-top: -135px;margin-left: 130px;position: absolute;"></i>';
    html += '                <i class="caret up"></i>';
    html += '            </div>';
    html += '            <div id="uploadImg">';
    html += '                <input type="file" accept="image/*" style="cursor:pointer;" onchange="showImg(2,this)"/>';
    html += '            </div>';
    html += '            <i style="font-size: 30px;" class="image icon"></i>';
    html += '        </div>';
    html += '        <div class="submit">';
    html += '            <button class="ui primary button" clsss="sub_btn" onclick="releaseComment(2, \'' + wznm + '\', \'' + $(this).attr("data-id") + '\')">发布评论</button>';
    html += '        </div>';
    html += '    </div>';
    html += ' </div>';
    $(this).parent().append(html);

    $('.emotion').qqFace({
        id : 'facebox1',
        assign:'reply',
        path:'/common/qqFace/arclist/'	//表情存放的路径
    });
});

//悬浮变色事件
$('.like').mouseover(function(){
    $(this).css({
        'color':'#212E0A4E'
    });
});

$('.like').mouseout(function(){
    $(this).css({
        'color':'#333'
    });
});

$(".like").click(function(){
    var html = "+1";
    $(this).find('em').html(html);
    $(this).find('em').fadeIn(500);
    $(this).find('em').fadeOut(500);
    setTimeout(function () {
        $(this).attr('data-num', '9');
        $(this).find('.num').html('4');
    }, 500);
});

//悬浮变色事件
$('.newslist li').mouseover(function(){
    $(this).addClass("active");
    $(this).siblings(".active").removeClass("active");
});

$('.newslist li').mouseout(function(){
    $(this).removeClass("active");
    $('.newslist li:first-child').addClass("active");
});

/**
 * 评论发布
 */
function releaseComment(type, wznm, commentId){
    var isLogin = $("#isAuthenticated").val();
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();

        autoCenter();
    }else {
        var paramData = new Object();
        if(type == 1){
            paramData.commentContent = $("#editor1").html();
        }else if(type == 2){
            paramData.replyId = commentId;
            paramData.commentContent = $("#reply").html();
        }
        paramData.wznm = wznm;

        var formData=new FormData();
        formData.append("file", uploadFile);
        formData.append("paramData", JSON.stringify(paramData));
        $.ajax({
            url: basePath + "comment/releaseComment",
            type:"POST",
            data:formData,
            dataType:"json",
            processData:false,//对data参数进行序列化处理
            contentType:false,//内容编码类型
            cache:false,//不使用缓存
            //设置ajax请求结束后的执行动作
            complete : function(XMLHttpRequest) {
                // 通过XMLHttpRequest取得响应头
                var redirect = XMLHttpRequest.getResponseHeader("isLogin");//未登录
                if (redirect == 'false') {
                    localRefresh('/common/headerRefresh', '#header');
                }
            },
            success:function(resultData){
                if(resultData.code==0){
                    cocoMessage.success(resultData.msg, 3000); //duration为0时显示关闭按钮
                    commentList(wznm);
                    $("#totalCommentCount").text(parseInt($("#totalCommentCount").text())+ 1);//总评论数+1
                    $("#postNum").text(parseInt($("#totalCommentCount").text()));
                    $("#editor1").html("");//文本清空
                }else{
                    cocoMessage.error(resultData.msg, 3000); //loading可以选择是否展示关闭按钮
                }
            }
        });
    }
}

/**
 * 评论列表展示
 * @param wznm
 */
function commentList(wznm, page, limit){
    var paramData = new Object();
    paramData.wznm = wznm;
    paramData.page = page;
    paramData.limit = limit;
    $.ajax({
        url: basePath + "comment/commentList",
        type:"POST",
        data:JSON.stringify(paramData),
        asyns:false,
        dataType:"json",
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            var htmlStr = '';
            if(resultData.code == 0){
                var data = resultData.data;
                layui.use(['laypage', 'layer'], function () {
                    htmlStr += '<h3 class="sideTab-title">';
                    htmlStr += '    评论列表 ';
                    htmlStr += '</h3>';
                    htmlStr += '<div class="commentList" style="color: #333;font-size: 14px;width: 100%;margin-top: 20px;">';
                    htmlStr += '    <div class="ui comments" id="commentList" style="margin-top: -10px;">';
                    for(var i = 0;i < data.length;i++){
                        var style = "";
                        if(data[i].giveLike){
                            style = "color: red";
                        }
                        var bottom = "";
                        if(i == data.length - 1){
                            bottom = "style='margin-bottom: 20px;'";
                        }
                        htmlStr += '<div class="comment" ' + bottom + '>';
                        htmlStr += '    <a class="avatar">';
                        htmlStr += '        <img src="/profile/' + data[i].iconUrl + '">';
                        htmlStr += '    </a>';
                        htmlStr += '    <div class="content" style="width: 100%;">';
                        htmlStr += '        <a class="author" style="font-size: 14px;font-weight: bold">' + data[i].commitName + '</a>';
                        htmlStr += '        <div class="metadata" style="float: right;font-size: 14px;color: #8590a6;">';
                        htmlStr += '            <span class="date">' + data[i].time + '</span>';
                        htmlStr += '        </div>';
                        htmlStr += '        <div class="text" style="margin-top: 7px">' + data[i].content + '</div>';
                        if(null != data[i].imgPath && "" != data[i].imgPath){
                            htmlStr += '    <div class="imgShow">';
                            htmlStr += '        <img class="comment-item-img" style="display: block;width: 75pt;margin-top: 5px;transition: .3s;margin-bottom: 5px;" src="' + '/articlefile/' + data[i].imgPath + '" data-action="zoom">';
                            htmlStr += '    </div>';
                        }
                        htmlStr += '        <div class="actions">';
                        htmlStr += '            <a class="reply" data-id="'+ data[i].id +'" style="display:inline-block;font-size: 15px;font-weight: bold;">回复</a>';
                        htmlStr += '            <div class="like" style="display:inline-block;margin-left: -10px;">';
                        htmlStr += '                <button class="icobutton icobutton--thumbs-up" style="' + style + '"><span data-id="'+ data[i].id +'" data-text="' + data[i].giveLike + '" class="fa fa fa-thumbs-o-up" id="effect-' + i + '" style="font-size: 14px;font-weight: bold;"></span></button><span class="num" style="color: #666;font-size: 14px;font-weight: bold;display: inline-block;margin-left: 2px;">' + data[i].praiseNum + '</span>';
                        htmlStr += '            </div>';
                        htmlStr += '        </div>';
                        if(i != (data.length - 1) || data[i].childCommentList.length != 0){
                            htmlStr += '    <div class="ui divider" style="margin-top: 10px"></div>';
                        }
                        htmlStr += '    </div>';
                        htmlStr += '    <div class="comments" style="margin-top: -15px;margin-bottom: -28px;">';
                        for(var j = 0;j < data[i].childCommentList.length;j++){
                            var style = "";
                            if(data[i].childCommentList[j].giveLike){
                                style = "color: red";
                            }
                            htmlStr += '       <div class="comment">';
                            htmlStr += '          <a class="avatar"><img src="/profile/' + data[i].childCommentList[j].iconUrl + '"></a>';
                            htmlStr += '          <div class="content" style="width: 100%;">';
                            htmlStr += '              <a style="font-size: 14px;font-weight: bold">' + data[i].childCommentList[j].commitName + '</a><span style="margin-left: 5px;margin-right: 5px;color: #8590a6;">回复</span><a style="font-size: 14px;font-weight: bold">' + data[i].childCommentList[j].replyName + '</a>';
                            htmlStr += '              <div class="metadata" style="float: right;font-size: 14px;color: #8590a6;">';
                            htmlStr += '                  <span class="date">' + data[i].childCommentList[j].time + '</span>';
                            htmlStr += '              </div>';
                            htmlStr += '              <div class="text" style="margin-top: 7px">' + data[i].childCommentList[j].content + '</div>';
                            if(null != data[i].childCommentList[j].imgPath && "" != data[i].childCommentList[j].imgPath){
                                htmlStr += '          <div class="imgShow">';
                                htmlStr += '              <img class="comment-item-img" style="display: block;width: 75pt;margin-top: 5px;transition: .3s;margin-bottom: 5px;" src="' + '/articlefile/' + data[i].childCommentList[j].imgPath + '" data-action="zoom">';
                                htmlStr += '          </div>';
                            }
                            htmlStr += '              <div class="actions">';
                            htmlStr += '                  <a class="reply" data-id="'+ data[i].childCommentList[j].id +'" style="display:inline-block;font-size: 15px;font-weight: bold;">回复</a>';
                            htmlStr += '                  <div class="like" style="display:inline-block;margin-left: -10px;">';
                            htmlStr += '                      <button class="icobutton icobutton--thumbs-up" style="' + style + '"><span data-id="'+ data[i].childCommentList[j].id +'" data-text="' + data[i].childCommentList[j].giveLike + '" class="fa fa fa-thumbs-o-up" id="effect-' + i + '-' + j + '" style="font-size: 14px;font-weight: bold"></span></button><span class="num" style="color: #666;font-size: 14px;font-weight: bold;display: inline-block;margin-left: 2px;">' + data[i].childCommentList[j].praiseNum + '</span>';
                            htmlStr += '                  </div>';
                            htmlStr += '              </div>';
                            if(i != (data.length - 1) || j != data[i].childCommentList.length -1){
                                htmlStr += '    <div class="ui divider" style="margin-top: 10px"></div>';
                            }
                            htmlStr += '           </div>';
                            htmlStr += '        </div>';
                        }
                        htmlStr += '    </div>';
                        htmlStr += '</div>';
                    }
                    htmlStr += '    </div>';
                    htmlStr += '</div>';
                    $(".commentInfo").html(htmlStr);
                    //点赞特效
                    giveLike();

                    var page = layui.laypage;
                    page.render({
                        elem: 'page',
                        count: resultData.count,
                        curr: paramData.page,
                        limit: paramData.limit,
                        theme: '#108EE9',
                        first: '<em>首页</em>',
                        last: '<em>尾页</em>',
                        prev: '<em>上页</em>',
                        next: '<em>下页</em>',
                        jump: function (obj, first) {
                            if (!first) {
                                commentList(wznm, obj.curr, obj.limit)
                            }
                            $(".pagePlug").show();
                        }
                    });
                })
            }else {
                htmlStr += '<div id="noComment">';
                htmlStr += '    <label>还没有人评论？赶快抢个沙发~</label>';
                htmlStr += '</div>';
                $(".commentInfo").html(htmlStr);
            }
        }
    });
}