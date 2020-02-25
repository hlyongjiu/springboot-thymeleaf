var checkadd = [false];

//num
var regnum = /^[0-9]*$/;
$('#coinSell').find('input').eq(0).change(function () {
    if (regnum.test($(this).val())) {
        success($(this), 0, checkadd, 0);
    } else {
        fail($(this), 0, '请输入数字', checkadd, 0);
    }
});

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

$('#btn-sell').click(function (e) {
    if (!checkadd.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkadd) {
            if (!checkadd[key]) {
                $('#coinSell').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var coinCount = document.getElementById("coinCount").value;
        var coin = {
            'sell': coinCount
        };
        var JSONdata = JSON.stringify(coin);
        $.ajax({
            type: "post",
            url: "/virtual/trade/user/sell",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result==1) {
                    alert("虚拟币不足");
                } else if(result == 2){
                    alert("卖出成功");
                    window.location.href = "/virtual/trade/user/index";
                }else{
                    alert("卖出失败")
                }
            }
        });
    }
});
