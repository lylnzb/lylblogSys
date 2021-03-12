$(document).ready(function(){
    $(".newsitem").on("click","li", function(){
        $(this).addClass("newscurrent").siblings().removeClass("newscurrent");
        $(".newsitem>div:eq("+$(this).index()+")").show().siblings().hide();
    });
});