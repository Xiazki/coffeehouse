//$('#answer_form').on('valid.form', addComment(e,form))
//$('#comment_form').on('valid.form', addComment(e,form))
//var addComment = function(e,form){
//    console.info("answer_form");
//    $.ajax({
//        url:"/comm/add",
//        dataType:'JSON',
//        data:$(form).serialize(),
//        type:'POST',
//        success:function(data){
//            var ret = eval("("+data+")");
//            var currentUrl = window.location.href;
//            if(ret.code == 1){
//                UIkit.notify("提交成功", {status:'success'});
//                window.location = currentUrl;
//            }else{
//                //后期删除
//                UIkit.notify("提交失败", {status:'danger'});
//            }
//        },
//        error:function(data){
//            console.info("re");
//            console.info(data);
//            UIkit.notify("服务器异常", {status:'danger'});
//        }
//    });
//}

$('#answer_form').on('valid.form', function (e, form) {
    console.info("answer_form");
    $.ajax({
        url: "/comm/add",
        dataType: 'JSON',
        data: $(form).serialize(),
        type: 'POST',
        success: function (data) {
            var ret = eval("(" + data + ")");
            var currentUrl = window.location.href;
            if (ret.code == 1) {
                UIkit.notify("提交成功", {status: 'success'});
                window.location = currentUrl;
            } else {
                //后期删除
                UIkit.notify("提交失败", {status: 'danger'});
            }
        },
        error: function (data) {
            console.info("re");
            console.info(data);
            UIkit.notify("服务器异常", {status: 'danger'});
        }
    });
});

//$('#comment_form').on('valid.form', function(e,form){
//    console.info("answer_form");
//    $.ajax({
//        url:"/comm/add",
//        dataType:'JSON',
//        data:$(form).serialize(),
//        type:'POST',
//        success:function(data){
//            var ret = eval("("+data+")");
//            console.info(data);
//            var currentUrl = window.location.href;
//            if(ret.code == 1){
//                UIkit.notify("提交成功", {status:'success'});
//                window.location = currentUrl;
//            }else{
//                //后期删除
//                UIkit.notify("提交失败", {status:'danger'});
//            }
//        },
//        error:function(data){
//            console.info("re");
//            console.info(data);
//            UIkit.notify("服务器异常", {status:'danger'});
//        }
//    });
//});

var onCommentSubmit = function (answerId, type, contentId) {
    if ($("#" + contentId).val() == null) {
        UIkit.notify("评论不能为空", {status: 'danger'});
        return;
    }
    $.ajax({
        url: "/comm/add",
        dataType: 'JSON',
        data: {
            entityId: answerId,
            type: type,
            content: $("#" + contentId).val()
        },
        type: 'POST',
        success: function (data) {
            var ret = eval("(" + data + ")");
            console.info(data);
            var currentUrl = window.location.href;
            if (ret.code == 1) {
                UIkit.notify("提交成功", {status: 'success'});
                window.location = currentUrl;
            } else {
                //后期删除
                UIkit.notify("提交失败", {status: 'danger'});
            }
        },
        error: function (data) {
            console.info("re");
            console.info(data);
            UIkit.notify("服务器异常", {status: 'danger'});
        }
    });
}

var showComment = function (id, ansId) {
    var selector = "#" + id + ansId;
    if ($(selector).is(":hidden")) {
        if ($(selector).attr("flag") == "false") {
            $.ajax({
                url: "/comm/list",
                dataType: "JSON",
                data: {
                    entityId: ansId,
                    type: "answer_comment"
                },
                type: "POST",
                success: function (data) {
                    var ret = eval("(" + data + ")");
                    console.info(ret);
                    var comList = ret.data;
                    if (comList == null) {
                        console.info("commList is null");
                        return;
                    }
                    var ul = selector + " ul"
                    for (var com = 0; com < comList.length; com++) {
                        $(ul).append("<li>"
                            + "<p class='indent'>"
                            + "<a href='#'>" + comList[com].coffeer.nickName + ":</a>"
                            + comList[com].content
                            + "</p>"
                            + "</li>");
                    }
                    $(selector).attr("flag", "true");
                },
                error: function (data) {

                }
            });
        }

        $(selector).show();
    } else {
        $(selector).hide();
    }
}

var addAtti = function (entityId, type, atti) {
    var like = $("#icon_like_"+entityId);
    var dislike = $("#icon_dislike_"+entityId);
    $.ajax({
        url: "/opr/atti",
        dataType: "JSON",
        data: {
            entityId: entityId,
            type: type,
            enAtti: atti
        },
        type: "POST",
        success: function (data) {
            var ret = eval("(" + data + ")");
            $("#like_" + entityId).html(ret.data[0]);
            $("#dislike_" + entityId).html(ret.data[1]);
            if (ret.code == 1) {
                if (atti == 1) {
                    if($(like).hasClass("uk-icon-thumbs-o-up")){
                        $(like).removeClass("uk-icon-thumbs-o-up");
                        $(like).addClass("uk-icon-thumbs-up");
                    }
                    if(dislike.hasClass("uk-icon-thumbs-down")){
                        $(dislike).removeClass("uk-icon-thumbs-down");
                        $(dislike).addClass("uk-icon-thumbs-o-down");
                    }
                } else {
                    if($(dislike).hasClass("uk-icon-thumbs-o-down")){
                        $(dislike).removeClass("uk-icon-thumbs-o-down");
                        $(dislike).addClass("uk-icon-thumbs-down");
                    }
                    if(like.hasClass("uk-icon-thumbs-up")){
                        $(like).removeClass("uk-icon-thumbs-up");
                        $(like).addClass("uk-icon-thumbs-o-up");
                    }
                }
            }else {
                UIkit.notify(ret.reMsg, {status: 'danger'});
            }
        },
        error: function (data) {
            UIkit.notify("服务器异常", {status: 'danger'});
        }
    })
}

