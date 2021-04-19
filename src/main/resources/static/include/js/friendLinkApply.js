var isLogin=$("#isAuthenticated").val();
function openLink(){
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();
    }else {
        $(".bg").show();
        $("#friendLinkApply").show();
        $(".linkApply").show();
    }

}

$(".linkApply .close.icon").click(function(){
    $(".bg").hide();
    $("#friendLinkApply").hide();
});

$(".btn.close").click(function(){
    $(".bg").hide();
    $("#friendLinkApply").hide();
});