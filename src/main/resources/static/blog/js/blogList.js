var columnId = GetQueryString("columnId");
var labelId = GetQueryString("labelId");

$(document).ready(function(){
    $('.pointing.menu .item').tab();

    //初始换文章列表
    initBlogInfo();
    //获取所属栏目标签信息
    getLabelInfo();

    //悬浮变色事件
    $('.newslist li').mouseover(function(){
        $(this).addClass("active");
        $(this).siblings(".active").removeClass("active");
    });
    $('.newslist li').mouseout(function(){
        $(this).removeClass("active");
        $('.newslist li:first-child').addClass("active");
    });
});

function initBlogInfo(page, limit){
    var obj = new Object();
    obj.columnId = columnId;
    obj.labelId = labelId;
    obj.articleTitle = $("#articleTitle").val();
    obj.page = page;
    obj.limit = limit;
    $.ajax({
        url: basePath + 'blog/queryBlogInfo',
        type: "POST",
        data:JSON.stringify(obj),
        dataType:"json",
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            var data = resultData.data;
            var htmlStr = '';
            layui.use(['laypage', 'layer'], function () {
                var page = layui.laypage;
                page.render({
                    elem: 'page',
                    count: resultData.count,
                    curr: obj.page,
                    limit: obj.limit,
                    jump: function (obj, first) {
                        if (!first) {
                            initBlogInfo(obj.curr, obj.limit)
                        }
                    }
                });
            });
            if(resultData.code == '0'){
                for(var i = 0;i < data.length;i++){
                    var style = '';
                    if(i == 0){
                        style = 'style="margin-top: -14px"';
                    }
                    htmlStr += '<article class="post_box" ' + style + '>';
                    htmlStr += '    <div class="post-img col-xs-4 backc2">';
                    htmlStr += '        <a>';
                    htmlStr += '            <img class="img-responsive img-rounded imgs" src="' + data[i].articleImg + '" style="display: block;">';
                    htmlStr += '            <em></em>';
                    htmlStr += '        </a>';
                    htmlStr += '    </div>';
                    htmlStr += '    <div class="post-left">';
                    htmlStr += '        <h3>';
                    htmlStr += '           <a class="blogTitle" href="' + data[i].articleUrl + '" target="_blank">';
                    htmlStr += '              ' + data[i].articleTitle;
                    htmlStr += '           </a>';
                    htmlStr += '        </h3>';
                    htmlStr += '        <div class="post-con">';
                    htmlStr += '            <span class="title-l"></span>';
                    htmlStr += '            ' + data[i].articleDec + '';
                    htmlStr += '        </div>';
                    htmlStr += '        <aside class="item-meta" style="margin-right: 10px;">';
                    htmlStr += '            <div class="ui horizontal link list">';
                    htmlStr += '                <div class="item">';
                    htmlStr += '                    <i class="user icon"></i>' + data[i].releasePeople;
                    htmlStr += '                </div>';
                    htmlStr += '                <div class="item">';
                    htmlStr += '                    <i class="calendar icon"></i>' + data[i].releaseTime;
                    htmlStr += '                </div>';
                    htmlStr += '               <div class="item">';
                    htmlStr += '                   <i class="eye icon"></i> 浏览(' + data[i].hits + ')';
                    htmlStr += '               </div>';
                    htmlStr += '               <div class="item">';
                    htmlStr += '                    <i class="comment icon"></i> 评论(' + data[i].postNum + ')';
                    htmlStr += '                </div>';
                    htmlStr += '            </div>';
                    htmlStr += '        </aside>';
                    htmlStr += '    </div> ';
                    htmlStr += '</article>';
                }
            }else {
                htmlStr += '<div>未查询到文章信息</div>';
                $("#page").hide();
            }
            $(".blogList").html(htmlStr);

            var val = $("#articleTitle").val();
            if(val != null && val != ''){
                $(".blogTitle").textSearch(val,{markColor: "red"});
            }

        },
        error: function () {

        }
    });
}