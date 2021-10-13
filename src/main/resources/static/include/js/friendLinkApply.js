function openLink(){
    var isLogin=$("#isAuthenticated").val();
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();

        autoCenter();
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