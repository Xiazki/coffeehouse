$('#message_form').on('valid.form', function (e, form) {
    console.info("message_form");
    $.ajax({
        url: "/mess/send",
        dataType: 'JSON',
        data: $(form).serialize(),
        type: 'POST',
        success: function (data) {
            var ret = eval("(" + data + ")");
           // var currentUrl = window.location.href;
            if (ret.code == 1) {
                var modal = UIkit.modal(".uk-modal");
                    modal.hide();

                UIkit.notify("提交成功", {status: 'success'});
               // window.location = currentUrl;
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

var onTabClick = function(type,thisId){

    $.ajax({
        url:"/mess/receive/list",
        data:{
            status:1,
            type:type
        },
        dataType:"JSON",
        type:"POST",
        success:function(data){
            var ret = eval("(" + data + ")");
            var tc = $("#tab_content");
            var messList = ret.data;
            if(ret.code == 1){
                if(messList!=null&&messList.length!=0){
                    tc.html("");
                    for(var i=0;i<messList.length;i++){
                        tc.append("<div class='uk-panel uk-alert uk-alert-large'>" +
                            messList[i].fromCoffeer.nickName+
                            "给您发送了私信: "+
                            messList[i].content+
                            "<div class='uk-text-right'>" +
                            "<a href='javascript:void(0);' onclick='showMessHis("+
                            messList[i].fromCoffeer.id+
                            ","+
                            messList[i].toCoffeer.id+
                            ")'>查看历史记录</a>" +
                            "</div></div>")
                    }
                }else{
                    tc.html("");
                    tc.append("<div class='uk-text-success uk-text-center'>" +
                        "当前没有通知信息" +
                        "</div>");
                }
            }else {
                UIkit.notify("获取失败", {status: 'danger'});
            }
        },
        error:function(data){
            UIkit.notify("服务器异常", {status: 'danger'});
        }
    });
}

var showMessHis = function(fromId,toId){
    $.ajax({
        url:"/mess/his",
        data:{
            fromId:fromId
        },
        dataType:"JSON",
        type:"POST",
        success:function(data){
            var ret = eval("(" + data + ")");
            var tc = $("#tab_content");
            var messList = ret.data;
            if(ret.code == 1) {
                if (messList != null && messList.length != 0) {
                    tc.html("");
                    for (var i = 0; i < messList.length; i++) {
                        if (messList[i].fromCoffeer.id == fromId) {
                            tc.append("<div class='uk-alert uk-alert-large'>" +
                                messList[i].fromCoffeer.nickName +
                                ":" +
                                messList[i].content +
                                "</div>");
                        }else {
                            tc.append("<div class='uk-alert uk-alert-success uk-alert-large uk-text-right'>" +
                                messList[i].content +
                                ":" +
                                messList[i].fromCoffeer.nickName +
                                "</div>");
                        }
                    }
                }
            }
        },
        error:function(data){
            UIkit.notify("服务器异常", {status: 'danger'});
        }
    });
}