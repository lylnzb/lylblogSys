layui.use(['carousel', 'form'], function(){
    var carousel = layui.carousel;

    //卡片渲染方法
    showCardInfo();
    //最新文章渲染方法
    showArticleInfo();
    //轮播图渲染方法
    showBannerInfo(carousel);

    //悬浮变色事件
    $('.newslist li').mouseover(function(){
        $(this).addClass("active");
        $(this).siblings(".active").removeClass("active");
    });
    $('.newslist li').mouseout(function(){
        $(this).removeClass("active");
        $('.newslist li:first-child').addClass("active");
    });

    //悬浮变色事件
    $('.post_box').mouseover(function(){
        $(this).addClass("active");
        $(this).find("img").attr("style", "-webkit-transform: scale(1.1);-moz-transform: scale(1.1);-o-transform: scale(1.1);-ms-transform: scale(1.1);");
    });
    $('.post_box').mouseout(function(){
        $(this).removeClass("active");
        $(this).find("img").attr("style", "");
    });

    $('#hybrid select').dropdown({on: 'hover'});
});

function showBannerInfo(carousel){
    $.ajax({
        url: basePath + 'showBannerInfo',
        type: "POST",
        //async:false,
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            console.log(resultData);
            var data = resultData.data;
            var htmlStr = '<div carousel-item="">';
            for(var i = 0;i < data.length;i++){
                htmlStr += "<div>";
                htmlStr += "    <a href=\"" + data[i].bannerUrl + "\" target='_blank'><img src=\"" + data[i].bannerImg + "\" style=\"width: 100%;height: 100%\"></a>";
                htmlStr += "    <h3 class=\"slide-title\">" + data[i].bannerTitle + "</h3>";
                htmlStr += "</div>";
            }
            htmlStr += '</div>';
            $("#bannerData").html(htmlStr);
            //图片轮播
            carousel.render({
                elem: '#bannerData'
                ,width: '100%'
                ,height: '320px'
                ,interval: 4000
            });
        },
        error: function () {

        }
    });
}

function showCardInfo(){
    $.ajax({
        url: basePath + 'showCardInfo?articleType=102',
        type: "POST",
        //async:false,
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            console.log(resultData);
            var data = resultData.data;
            var htmlStr = '<div class="ui secondary pointing menu">';
            for(var i = 0;i < data.length;i++){
                var active = '';
                if(i ==0){
                    active = ' active';
                }
                htmlStr += '   <a class="item' + active + '" data-tab="tab-name-' + (i + 1) + '">' + data[i].tabName + '</a> ';
            }
            htmlStr += '   </div>';
            for(var i = 0;i < data.length;i++){
                var active = '';
                if(i ==0){
                    active = ' active';
                }
                htmlStr += '<div class="ui tab' + active + '" data-tab="tab-name-' + (i + 1) + '">';
                htmlStr += '    <div class="ui link cards">';
                for(var j = 0;j < data[i].cardList.length;j++){
                    htmlStr += '        <div class="ui card" style="max-width: 235px;height:300px;width: auto">';
                    htmlStr += '            <div class="image">';
                    htmlStr += '                <i class="ztpic"><img src="' + data[i].cardList[j].articleImg + '"></i>';
                    htmlStr += '            </div>';
                    htmlStr += '            <div class="content">';
                    htmlStr += '                <a class="header" style="font-size: 14px" href="' + data[i].cardList[j].articleUrl + '" target="_blank">' + data[i].cardList[j].articleTitle + '</a>';
                    htmlStr += '                <div class="description">';
                    htmlStr +=                      data[i].cardList[j].articleDec;
                    htmlStr += '                </div>';
                    htmlStr += '            </div>';
                    htmlStr += '            <div class="extra content" style="font-size: 10px">';
                    htmlStr += '                <a><i class="user icon"></i>' + data[i].cardList[j].releasePeople + '</a>';
                    htmlStr += '                <spen style="float: right">' + data[i].cardList[j].releaseTime + '</spen>';
                    htmlStr += '            </div> ';
                    htmlStr += '        </div>';
                }
                htmlStr += '    </div> ';
                htmlStr += '</div>';
            }
            $("#cardTab").html(htmlStr);
            //tab切换动态实现
            $('.pointing.menu .item').tab();
        },
        error: function () {

        }
    });
}

function showArticleInfo(){
    $.ajax({
        url: basePath + 'showArticleInfo',
        type: "POST",
        //async:false,
        contentType: 'application/json;charset=utf-8',
        success: function (resultData) {
            console.log(resultData);
            var data = resultData.data;
            var htmlStr = '';
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
                htmlStr += '              <span class="recommend-flag">新</span>';
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
            $("#new").html(htmlStr);
        },
        error: function () {

        }
    });
}

window.onunload = function() {

};