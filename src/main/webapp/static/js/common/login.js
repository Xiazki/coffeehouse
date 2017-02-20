$('#login_form').on('valid.form',function(e,form){
    console.info("test1");
    $.ajax({
        url:"/lo/",
        dataType:'JSON',
        data:$(form).serialize(),
        type:'POST',
        success:function(data){

            var ret = eval("("+data+")");
            var currentUrl = window.location.href;
            if(ret.code == 1){
                var modal = UIkit.modal(".uk-modal");
                if(modal.isActive()){
                    modal.hide();
                }
                UIkit.notify("登陆成功", {status:'success'});

            }else if(ret.code == 2){ //跳转到上一个页面


                //多余 待删除
                UIkit.notify("登陆成功", {status:'success'});
                if(ret.reMsg!=null&&ret.reMsg != undefined){
                    window.location=ret.reMsg;
                    return;
                }
                //刷新当前页面
                window.location =currentUrl;
            } else{
                console.info(ret.reMsg);
                UIkit.notify(ret.reMsg, {status:'danger'});
            }
        },
        error:function(data){
            console.info("re");
            console.info(data);
            UIkit.notify("账号密码错误", {status:'danger'});
        }
    });
});

