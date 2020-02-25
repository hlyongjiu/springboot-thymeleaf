var checkRmbPut = [false];
var checkRmbGet = [false];
var checkCoinPut = [false];
var checkCoinGet = [false];

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

//num
var regnum = /^[0-9]*$/;
$('#rmbPut').find('input').eq(0).change(function () {
    if (regnum.test($(this).val())) {
        success($(this), 0, checkRmbPut, 0);
    } else {
        fail($(this), 0, '请输入数字', checkRmbPut, 0);
    }
});

//num
var regnum = /^[0-9]*$/;
$('#rmbGet').find('input').eq(0).change(function () {
    if (regnum.test($(this).val())) {
        success($(this), 0, checkRmbGet, 0);
    } else {
        fail($(this), 0, '请输入数字', checkRmbGet, 0);
    }
});

//num
var regnum = /^[0-9]*$/;
$('#coinPut').find('input').eq(0).change(function () {
    if (regnum.test($(this).val())) {
        success($(this), 0, checkCoinPut, 0);
    } else {
        fail($(this), 0, '请输入数字', checkCoinPut, 0);
    }
});

//num
var regnum = /^[0-9]*$/;
$('#coinGet').find('input').eq(0).change(function () {
    if (regnum.test($(this).val())) {
        success($(this), 0, checkCoinGet, 0);
    } else {
        fail($(this), 0, '请输入数字', checkCoinGet, 0);
    }
});

$('#btn-rmbPut').click(function (e) {
    if (!checkRmbPut.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkRmbPut) {
            if (!checkRmbPut[key]) {
                $('#rmbPut').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var rmbPutAmount = document.getElementById("rmbPutAmount").value;
        var rmbPutType = document.getElementById("rmbPutType").value;
        var rmb = {
            'amount': rmbPutAmount,
            'typeOne': 1,
            'comment': rmbPutType
        };
        var JSONdata = JSON.stringify(rmb);
        $.ajax({
            type: "post",
            url: "/virtual/finance/user/rmbPut",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result==1) {
                    alert("充值成功");
                    window.location.href = "/virtual/finance/user/index";
                } else{
                    alert("充值失败")
                }
            }
        });
    }
});

$('#btn-rmbGet').click(function (e) {
    if (!checkRmbGet.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkRmbGet) {
            if (!checkRmbGet[key]) {
                $('#rmbGet').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var rmbPutAmount = document.getElementById("rmbGetAmount").value;
        var rmbPutType = document.getElementById("rmbGetType").value;
        var rmb = {
            'amount': rmbPutAmount,
            'typeOne': 1,
            'comment': rmbPutType
        };
        var JSONdata = JSON.stringify(rmb);
        $.ajax({
            type: "post",
            url: "/virtual/finance/user/rmbGet",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result==1) {
                    alert("提现成功");
                    window.location.href = "/virtual/finance/user/index";
                } else if(result == 2){
                    alert("余额不足");
                }else{
                    alert("提现失败")
                }
            }
        });
    }
});

$('#btn-coinPut').click(function (e) {
    if (!checkCoinPut.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkCoinPut) {
            if (!checkCoinPut[key]) {
                $('#coinPut').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var rmbPutAmount = document.getElementById("coinPutAmount").value;
        var rmbPutType = document.getElementById("coinPutType").value;
        var rmb = {
            'amount': rmbPutAmount,
            'typeOne': 2,
            'comment': rmbPutType
        };
        var JSONdata = JSON.stringify(rmb);
        $.ajax({
            type: "post",
            url: "/virtual/finance/user/coinPut",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result==1) {
                    alert("转入成功");
                    window.location.href = "/virtual/finance/user/index";
                }else{
                    alert("转入失败")
                }
            }
        });
    }
});

$('#btn-coinGet').click(function (e) {
    if (!checkCoinGet.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkCoinGet) {
            if (!checkCoinGet[key]) {
                $('#coinGet').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var rmbPutAmount = document.getElementById("coinGetAmount").value;
        var rmbPutType = document.getElementById("coinGetType").value;
        var rmb = {
            'amount': rmbPutAmount,
            'typeOne': 1,
            'comment': rmbPutType
        };
        var JSONdata = JSON.stringify(rmb);
        $.ajax({
            type: "post",
            url: "/virtual/finance/user/coinGet",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result==1) {
                    alert("转出成功");
                    window.location.href = "/virtual/finance/user/index";
                } else if(result == 2){
                    alert("虚拟币不足");
                }else{
                    alert("转出失败")
                }
            }
        });
    }
});

