$(document).ready(function(){
    //获取元素距浏览器顶部的高度
    var gerTop = $('#header').offset().top;
    var constant = 0;
    if(gerTop < constant){
        gerTop = constant;
    }
    //监控滚动栏scroll
    $(document).scroll(function(){
        //获取当前滚动栏scroll的高度
        var scrTop = $(window).scrollTop();
        //如果元素距顶部的高度 等于 当前滚动栏的高度，开始悬浮；否则清空悬浮
        //position: fixed;top: 0;z-index: 901;width: 100%;background: #fff;box-shadow: 0 2px 6px 0 rgba(0,0,0,.17);
        if(scrTop > gerTop ){
            $('#header').css({'position':'fixed','top':'0','z-index':'901','width':'100%','background':'#fff','box-shadow':'0 2px 6px 0 rgba(0,0,0,.17)'});
        }else{
            $('#header').attr("style","");
        }
    });
});