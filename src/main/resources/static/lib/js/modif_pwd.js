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

//旧密码
$('#modify_pwd').find('input').eq(0).change(function () {
    if ($(this).val().length < 3) {
        fail($(this), 0, '密码不能少于3位', checkadd, 0);
    } else {
        success($(this), 0, checkadd, 0);
    }
});

//新密码
$('#modify_pwd').find('input').eq(1).change(function () {
    if ($(this).val().length < 3) {
        fail($(this), 1, '新密码不能少于3位', checkadd, 0);
    } else {
        success($(this), 1, checkadd, 0);
    }
});

//add
$('#btn-modify').click(function (e) {
    if (!checkadd.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkadd) {
            if (!checkadd[key]) {
                $('#modify_pwd').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var oPwd = document.getElementById("o-pwd").value;
        var nPwd = document.getElementById("n-pwd").value;
        var user = {
            'pwd': oPwd
        };
        var JSONdata = JSON.stringify(user);
        $.ajax({
            type: "post",
            url: "/virtual/user/modifyPwd?nPwd=" + nPwd,
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result) {
                    alert("修改成功");
                    window.location.href = "/virtual/homepage";
                } else {
                    alert("修改失败");
                }
            }
        });
    }
});