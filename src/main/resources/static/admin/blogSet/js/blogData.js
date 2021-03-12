
layui.use(['form', 'table','tree'], function(){
    var form = layui.form,
        element = layui.element;

    element.on('tab(blogSet)', function (data) { $("#setTab").val(data.index); });

    findCodeValue(form);

    form.verify({
        isUploadWebIcon:function(value) {
            if ('0' == $("#setTab").val() && (value.length < 1 || value == "false")) {
                return '请上传网站图标！';
            }
        },
        basicsetTitle:function(value) {
            if ('0' == $("#setTab").val() && value.length < 1) {
                return '博客名称不能为空！';
            }
        },
        basicsetCopyrightNoticeInfo:function(value) {
            if ('0' == $("#setTab").val() && value.length < 1) {
                return '原创版权声明信息不能为空！';
            }
        },
        basicsetWebNoticeInfo:function(value) {
            if ('0' == $("#setTab").val() && value.length < 1) {
                return '底部网站版权信息不能为空！';
            }
        },
        basicsetBottomsiteInfo:function(value) {
            if ('0' == $("#setTab").val() && value.length < 1) {
                return '底部站点声明不能为空！';
            }
        },
        isUploadIcon:function(value) {
            if ('1' == $("#setTab").val() && (value.length < 1 || value == "false")) {
                return '请上传博主头像！';
            }
        },
        bloggersetBloggerName:function(value) {
            if ('1' == $("#setTab").val() && value.length < 1) {
                return '博主姓名不能为空！';
            }
        },
        bloggersetBloggerDes:function(value) {
            if ('1' == $("#setTab").val() && value.length < 1) {
                return '个性签名不能为空！';
            }
        },
        bloggersetBloggerQqNumber:function(value) {
            if ('1' == $("#setTab").val() && value.length < 1) {
                return '博主QQ不能为空！';
            }
        },
        isUploadCodeImg:function(value) {
            if ('1' == $("#setTab").val() && (value.length < 1 || value == "false")) {
                return '请上传微信二维码！';
            }
        },
        isUploadBackgroundImg:function(value) {
            if ('1' == $("#setTab").val() && (value.length < 1 || value == "false")) {
                return '请上传背景图片！';
            }
        },
        blogsetPerpageShowNum:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '每页显示文章条数不能为空！';
            }
        },
        blogsetLatestShowNum:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '最新文章显示条数不能为空！';
            }
        },
        blogsetScrollRecommendedShowNum:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '滚动推荐显示条数不能为空！';
            }
        },
        blogsetRankingShowNum:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '点击排行显示条数不能为空！';
            }
        },
        blogsetRecommendedShowNum:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '列表推荐显示条数不能为空！';
            }
        },
        blogsetSpecialRecdShowNum:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '书籍推荐显示条数不能为空！';
            }
        },
        isUploadDefaultImg:function(value) {
            if ('2' == $("#setTab").val() && (value.length < 1 || value == "false")) {
                return '请上传默认图片！';
            }
        },
        blogsetFriendLinks:function(value) {
            if ('2' == $("#setTab").val() && value.length < 1) {
                return '侧边栏友情链接不能为空！';
            }
        },
        eamilsetUsername:function(value) {
            if ('3' == $("#setTab").val() && value.length < 1) {
                return '发送邮件账户不能为空！';
            }
        },
        eamilsetPassword:function(value) {
            if ('3' == $("#setTab").val() && value.length < 1) {
                return 'eamilsetPassword不能为空！';
            }
        },
        eamilsetHost:function(value) {
            if ('3' == $("#setTab").val() && value.length < 1) {
                return 'smtp服务主机不能为空！';
            }
        },
        eamilsetProtocol:function(value) {
            if ('3' == $("#setTab").val() && value.length < 1) {
                return '请选择服务协议！';
            }
        },
        eamilsetDefaultEncoding:function(value) {
            if ('3' == $("#setTab").val() && value.length < 1) {
                return '请选择编码集！';
            }
        },
        eamilsetVerificationCode:function(value) {
            if ('3' == $("#setTab").val() && value.length < 1) {
                return '发送验证码邮箱内容模板不能为空！';
            }else if('3' == $("#setTab").val() && value.indexOf("${code}") == -1){
                return '该模板内未包含${code}';
            }
        }
    });

    $.ajax({
        url: basePath + "blogSet/viewBlogSetInfo",
        type:"POST",
        async:false,
        contentType : 'application/json;charset=utf-8',
        success:function(resultData){
            var data = resultData.data[0];
            /*网站图标*/
            $("#isUploadWebIcon").val(true);
            $("#webIcon").attr("src", data.basicsetWebsiteIco);
            /*博客名称*/
            $("#basicsetTitle").val(data.basicsetTitle);
            /*博客描述*/
            $("#basicsetDes").val(data.basicsetDes);
            /*全局允许评论*/
            (data.basicsetGlobalAllowComment == 'Y')?$("#basicsetGlobalAllowComment").attr("checked", "checked"):$("#basicsetGlobalAllowComment").removeAttr("checked");
            /*全局展示评论*/
            (data.basicsetGlobalShowComment == 'Y')?$("#basicsetGlobalShowComment").attr("checked", "checked"):$("#basicsetGlobalShowComment").removeAttr("checked");
            /*全局允许转载*/
            (data.basicsetGlobalAllowReprint == 'Y')?$("#basicsetGlobalAllowReprint").attr("checked", "checked"):$("#basicsetGlobalAllowReprint").removeAttr("checked");
            /*评论是否通知*/
            (data.basicsetCommentNotice == 'Y')?$("#basicsetCommentNotice").attr("checked", "checked"):$("#basicsetCommentNotice").removeAttr("checked");
            /*赞赏是否开启*/
            (data.basicsetOpenAppreciate == 'Y')?$("#basicsetOpenAppreciate").attr("checked", "checked"):$("#basicsetOpenAppreciate").removeAttr("checked");
            /*显示文章来源*/
            (data.basicsetShowArticleSource == 'Y')?$("#basicsetShowArticleSource").attr("checked", "checked"):$("#basicsetShowArticleSource").removeAttr("checked");
            /*评论自动审核*/
            (data.basicsetCommentAutoReview == 'Y')?$("#basicsetCommentAutoReview").attr("checked", "checked"):$("#basicsetCommentAutoReview").removeAttr("checked");
            /*是否添加原创版权声明*/
            (data.basicsetAddCopyrightNotice == 'Y')?$("#basicsetAddCopyrightNotice").attr("checked", "checked"):$("#basicsetAddCopyrightNotice").removeAttr("checked");
            /*原创版权声明信息*/
            $("#basicsetCopyrightNoticeInfo").val(data.basicsetCopyrightNoticeInfo);
            /*底部网站版权信息*/
            $("#basicsetWebNoticeInfo").val(data.basicsetWebNoticeInfo);
            /*底部站点声明*/
            $("#basicsetBottomsiteInfo").val(data.basicsetBottomsiteInfo);

            /*博主头像*/
            $("#isUploadIcon").val(true);
            $("#icon").attr("src", data.bloggersetBloggerProfile);
            /*博主姓名*/
            $("#bloggersetBloggerName").val(data.bloggersetBloggerName);
            /*个性签名*/
            $("#bloggersetBloggerDes").val(data.bloggersetBloggerDes);
            /*博主QQ*/
            $("#bloggersetBloggerQqNumber").val(data.bloggersetBloggerQqNumber);
            /*微信二维码*/
            $(".uploadCodeImg").show();
            $("#isUploadCodeImg").val(true);
            $("#showCodeImg").attr("src", data.bloggersetBloggerWechatOrcode);
            /*背景图片*/
            $(".uploadBackgroundImg").show();
            $("#isUploadBackgroundImg").val(true);
            $("#showBackgroundImg").attr("src", data.bloggersetBloggerBackroundImg);

            /*每页显示文章条数*/
            $("#blogsetPerpageShowNum").val(data.blogsetPerpageShowNum);
            /*最新文章显示条数*/
            $("#blogsetLatestShowNum").val(data.blogsetLatestShowNum);
            /*滚动推荐显示条数*/
            $("#blogsetScrollRecommendedShowNum").val(data.blogsetScrollRecommendedShowNum);
            /*点击排行显示条数*/
            $("#blogsetRankingShowNum").val(data.blogsetRankingShowNum);
            /*列表推荐显示条数*/
            $("#blogsetRecommendedShowNum").val(data.blogsetRecommendedShowNum);
            /*书籍推荐显示条数*/
            $("#blogsetSpecialRecdShowNum").val(data.blogsetSpecialRecdShowNum);
            /*文章无图使用默认图片*/
            (data.blogsetNoPicUseDefault == 'Y')?$("#blogsetNoPicUseDefault").attr("checked", "checked"):$("#blogsetNoPicUseDefault").removeAttr("checked");
            /*无封面图使用内容图*/
            (data.blogsetNoCoverpicUseContentpic == 'Y')?$("#blogsetNoCoverpicUseContentpic").attr("checked", "checked"):$("#blogsetNoCoverpicUseContentpic").removeAttr("checked");
            /*背景图片*/
            $(".uploadDefaultImg").show();
            $("#isUploadDefaultImg").val(true);
            $("#showDefaultImg").attr("src", data.blogsetDefaultPic);
            /*侧边栏友情链接*/
            $("#blogsetFriendLinks").val(data.blogsetFriendLinks);

            /*发送邮件账户*/
            $("#eamilsetUsername").val(data.eamilsetUsername);
            /*邮箱授权码*/
            $("#eamilsetPassword").val(data.eamilsetPassword);
            /*smtp服务主机*/
            $("#eamilsetHost").val(data.eamilsetHost);
            /*服务协议*/
            $("#eamilsetProtocol").siblings("div.layui-form-select").find('dl').find("dd[lay-value='" + data.eamilsetProtocol + "']").click();
            /*编码集*/
            $("#eamilsetDefaultEncoding").siblings("div.layui-form-select").find('dl').find("dd[lay-value='" + data.eamilsetDefaultEncoding + "']").click();
            /*发送验证码邮箱内容模板*/
            $("#eamilsetVerificationCode").val(data.eamilsetVerificationCode);

            form.render();
        }
    });

    //监听提交
    form.on('submit(save)', function(data){
        /*网站图标*/
        var webIcon = $("#webIcon").attr("src");
        /*博客名称*/
        var basicsetTitle = data.field.basicsetTitle;
        /*博客描述*/
        var basicsetDes = data.field.basicsetDes;
        /*全局允许评论*/
        var basicsetGlobalAllowComment = (data.field.basicsetGlobalAllowComment == "on")?"Y":"N";
        /*全局展示评论*/
        var basicsetGlobalShowComment = (data.field.basicsetGlobalShowComment == "on")?"Y":"N";
        /*全局允许转载*/
        var basicsetGlobalAllowReprint = (data.field.basicsetGlobalAllowReprint == "on")?"Y":"N";
        /*评论是否通知*/
        var basicsetCommentNotice = (data.field.basicsetCommentNotice == "on")?"Y":"N";
        /*赞赏是否开启*/
        var basicsetOpenAppreciate = (data.field.basicsetOpenAppreciate == "on")?"Y":"N";
        /*显示文章来源*/
        var basicsetShowArticleSource = (data.field.basicsetShowArticleSource == "on")?"Y":"N";
        /*评论自动审核*/
        var basicsetCommentAutoReview = (data.field.basicsetCommentAutoReview == "on")?"Y":"N";
        /*是否添加原创版权声明*/
        var basicsetAddCopyrightNotice = (data.field.basicsetAddCopyrightNotice == "on")?"Y":"N";
        /*原创版权声明信息*/
        var basicsetCopyrightNoticeInfo = $("#basicsetCopyrightNoticeInfo").val();
        /*底部网站版权信息*/
        var basicsetWebNoticeInfo = $("#basicsetWebNoticeInfo").val();
        /*底部站点声明*/
        var basicsetBottomsiteInfo = $("#basicsetBottomsiteInfo").val();

        /*博主头像*/
        var icon = $("#icon").attr("src");
        /*博主姓名*/
        var bloggersetBloggerName = data.field.bloggersetBloggerName;
        /*个性签名*/
        var bloggersetBloggerDes = data.field.bloggersetBloggerDes;
        /*博主QQ*/
        var bloggersetBloggerQqNumber = data.field.bloggersetBloggerQqNumber;
        /*微信二维码*/
        var showCodeImg = $("#showCodeImg").attr("src");
        /*背景图片*/
        var showBackgroundImg = $("#showBackgroundImg").attr("src");

        /*每页显示文章条数*/
        var blogsetPerpageShowNum = data.field.blogsetPerpageShowNum;
        /*最新文章显示条数*/
        var blogsetLatestShowNum = data.field.blogsetLatestShowNum;
        /*滚动推荐显示条数*/
        var blogsetScrollRecommendedShowNum = data.field.blogsetScrollRecommendedShowNum;
        /*点击排行显示条数*/
        var blogsetRankingShowNum = data.field.blogsetRankingShowNum;
        /*列表推荐显示条数*/
        var blogsetRecommendedShowNum = data.field.blogsetRecommendedShowNum;
        /*书籍推荐显示条数*/
        var blogsetSpecialRecdShowNum = data.field.blogsetSpecialRecdShowNum;
        /*文章无图使用默认图片*/
        var blogsetNoPicUseDefault = (data.field.blogsetNoCoverpicUseContentpic == "on")?"Y":"N";
        /*无封面图使用内容图*/
        var blogsetNoCoverpicUseContentpic = (data.field.blogsetNoCoverpicUseContentpic == "on")?"Y":"N";
        /*背景图片*/
        var showDefaultImg = $("#showDefaultImg").attr("src");
        /*侧边栏友情链接*/
        var blogsetFriendLinks = data.field.blogsetFriendLinks;

        /*发送邮件账户*/
        var eamilsetUsername = data.field.eamilsetUsername;
        /*邮箱授权码*/
        var eamilsetPassword = data.field.eamilsetPassword;
        /*smtp服务主机*/
        var eamilsetHost = data.field.eamilsetHost;
        /*服务协议*/
        var eamilsetProtocol = data.field.eamilsetProtocol;
        /*编码集*/
        var eamilsetDefaultEncoding = data.field.eamilsetDefaultEncoding;
        /*发送验证码邮箱内容模板*/
        var eamilsetVerificationCode = data.field.eamilsetVerificationCode;

        var setTab = $("#setTab").val();
        var paramData = new Object();
        if(0 == setTab){
            paramData.basicsetWebsiteIco = webIcon;
            paramData.basicsetTitle = basicsetTitle;
            paramData.basicsetDes = basicsetDes;
            paramData.basicsetGlobalAllowComment = basicsetGlobalAllowComment;
            paramData.basicsetGlobalShowComment = basicsetGlobalShowComment;
            paramData.basicsetGlobalAllowReprint = basicsetGlobalAllowReprint;
            paramData.basicsetCommentNotice = basicsetCommentNotice;
            paramData.basicsetOpenAppreciate = basicsetOpenAppreciate;
            paramData.basicsetShowArticleSource = basicsetShowArticleSource;
            paramData.basicsetCommentAutoReview = basicsetCommentAutoReview;
            paramData.basicsetAddCopyrightNotice = basicsetAddCopyrightNotice;
            paramData.basicsetCopyrightNoticeInfo = basicsetCopyrightNoticeInfo;
            paramData.basicsetWebNoticeInfo = basicsetWebNoticeInfo;
            paramData.basicsetBottomsiteInfo = basicsetBottomsiteInfo;
        }else if(1 == setTab){
            paramData.bloggersetBloggerProfile = icon;
            paramData.bloggersetBloggerName = bloggersetBloggerName;
            paramData.bloggersetBloggerDes = bloggersetBloggerDes;
            paramData.bloggersetBloggerQqNumber = bloggersetBloggerQqNumber;
            paramData.bloggersetBloggerWechatOrcode = showCodeImg;
            paramData.bloggersetBloggerBackroundImg = showBackgroundImg;
        }else if(2 == setTab){
            paramData.blogsetPerpageShowNum = blogsetPerpageShowNum;
            paramData.blogsetLatestShowNum = blogsetLatestShowNum;
            paramData.blogsetScrollRecommendedShowNum = blogsetScrollRecommendedShowNum;
            paramData.blogsetRankingShowNum = blogsetRankingShowNum;
            paramData.blogsetRecommendedShowNum = blogsetRecommendedShowNum;
            paramData.blogsetSpecialRecdShowNum = blogsetSpecialRecdShowNum;
            paramData.blogsetNoPicUseDefault = blogsetNoPicUseDefault;
            paramData.blogsetNoCoverpicUseContentpic = blogsetNoCoverpicUseContentpic;
            paramData.blogsetDefaultPic = showDefaultImg;
            paramData.blogsetFriendLinks = blogsetFriendLinks;
        }else if(3 == setTab){
            paramData.eamilsetUsername = eamilsetUsername;
            paramData.eamilsetPassword = eamilsetPassword;
            paramData.eamilsetHost = eamilsetHost;
            paramData.eamilsetProtocol = eamilsetProtocol;
            paramData.eamilsetDefaultEncoding = eamilsetDefaultEncoding;
            paramData.eamilsetVerificationCode = eamilsetVerificationCode;
        }
        console.log(paramData);
        $.ajax({
            url: basePath + "blogSet/configurationBlogSetInfo",
            type:"POST",
            data:JSON.stringify(paramData),
            dataType:"json",
            contentType : 'application/json;charset=utf-8',
            success:function(resultData){
                if(resultData.code==0){
                    parent.layer.alert(resultData.msg);
                }else{
                    parent.layer.alert(resultData.msg);
                }
            }
        });
    });
});

function viewImg(file, type){
    var reader = new FileReader();	// 实例化一个FileReader对象，用于读取文件
    var img = "";
    if(1 == type){
        img = document.getElementById('webIcon');           // 获取要显示图片的标签
    }else if(2 == type){
        img = document.getElementById('icon');              // 获取要显示图片的标签
    }else if(3 == type){
        img = document.getElementById('showCodeImg');       // 获取要显示图片的标签
    }else if(4 == type){
        img = document.getElementById('showBackgroundImg'); // 获取要显示图片的标签
    }else if(5 == type){
        img = document.getElementById('showDefaultImg');    // 获取要显示图片的标签
    }
    //读取File对象的数据
    reader.onload = function(evt){
        img.src = evt.target.result;
    }
    reader.readAsDataURL(file.files[0]);
    if(1 == type){
        $("#isUploadWebIcon").val(true);
    }else if(2 == type){
        $("#isUploadIcon").val(true);
    }else if(3 == type){
        $(".uploadCodeImg").show();
        $("#isUploadCodeImg").val(true);
    }else if(4 == type){
        $(".uploadBackgroundImg").show();
        $("#isUploadBackgroundImg").val(true);
    }else if(5 == type){
        $(".uploadDefaultImg").show();
        $("#isUploadDefaultImg").val(true);
    }
}

function closeView(type){
    if(1 == type){
        $("#isUploadWebIcon").val(false);
    }else if(2 == type){
        $("#isUploadIcon").val(false);
    }else if(3 == type){
        $("#showCodeImg").attr("src", "");
        $(".uploadCodeImg").hide();
        $("#isUploadCodeImg").val(false);
    }else if(4 == type){
        $("#showBackgroundImg").attr("src", "");
        $(".uploadBackgroundImg").hide();
        $("#isUploadBackgroundImg").val(false);
    }else if(5 == type){
        $("#showDefaultImg").attr("src", "");
        $(".uploadDefaultImg").hide();
        $("#isUploadDefaultImg").val(false);
    }
}

//字典初始化
function findCodeValue(form){
    loadSelect("#articleStatus","sys_article_status", form);
    loadSelect("#eamilsetProtocol","sys_email_protocol", form);
    loadSelect("#eamilsetDefaultEncoding","sys_java_coding", form);
    loadSelectAllow("#columnId", form);
}