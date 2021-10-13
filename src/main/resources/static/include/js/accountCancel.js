function accountCancel(){
    var isLogin=$("#isAuthenticated").val();
    if(isLogin == 'false'){
        $(".bg").show();
        $(".login").show();
        $(".userLogin").show();
        $(".userRegister").hide();
        $(".userRetrievePas").hide();
    }else {
        initCollection();
        $(".bg").show();
        $(".accountCancel").show();
    }
}

$(".accountCancel .close.icon").click(function(){
    $(".bg").hide();
    $(".accountCancel").hide();
});