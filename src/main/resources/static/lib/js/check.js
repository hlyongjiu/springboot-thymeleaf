var checkadd = [false, false];

//校验成功函数
function success(Obj, counter, check, offset) {
    Obj.parent().parent().removeClass('has-error').addClass('has-success');
    $('.tips').eq(counter + offset).hide();
    $('.glyphicon-ok').eq(counter + offset).show();
    $('.glyphicon-remove').eq(counter + offset).hide();
    check[counter] = true;
}

// 校验失败函数
function fail(Obj, counter, msg, check, offset) {
    Obj.parent().parent().removeClass('has-success').addClass('has-error');
    $('.glyphicon-remove').eq(counter + offset).show();
    $('.glyphicon-ok').eq(counter + offset).hide();
    $('.tips').eq(counter + offset).text(msg).show();
    check[counter] = false;
}

//用户名
$('#add').find('input').eq(0).change(function () {
    if ($(this).val().length < 1) {
        fail($(this), 0, '用户名太短', checkadd, 0);
    } else {
        success($(this), 0, checkadd, 0);
    }
});

//密码
$('#add').find('input').eq(1).change(function () {
    if ($(this).val().length < 3) {
        fail($(this), 1, '密码不能少于3位', checkadd, 0);
    } else {
        success($(this), 1, checkadd, 0);
    }
});

//add
$('#btn-add').click(function (e) {
    if (!checkadd.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkadd) {
            if (!checkadd[key]) {
                $('#add').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var bname = document.getElementById("ad-bname").value;
        var pwd = document.getElementById("ad-pwd").value;
        var user = {
            'userName': bname, 'pwd': pwd
        };
        var JSONdata = JSON.stringify(user);
        $.ajax({
            type: "post",
            url: "/virtual/user/register",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result) {
                    alert("注册成功");
                    window.location.href = "/virtual/index";
                } else {
                    alert("注册失败");
                }
            }
        });
    }
});