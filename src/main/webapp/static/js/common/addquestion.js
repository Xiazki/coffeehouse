$('#ask_form').on('valid.form',function(e,form){
    console.info("test_ask_form");
    $.ajax({
        url:"/que/add",
        dataType:'JSON',
        data:$(form).serialize(),
        type:'POST',
        success:function(data){

            var ret = eval("("+data+")");
            var modal = UIkit.modal(".uk-modal");
            if(ret.code == 1){
                    modal.hide();
                UIkit.notify("发布成功", {status:'success'});

            }else{
                UIkit.notify("发布失败", {status:'danger'});
            }
        },
        error:function(data){
            console.info("re");
            console.info(data);
            UIkit.notify("服务器异常", {status:'danger'});
        }
    });
});

