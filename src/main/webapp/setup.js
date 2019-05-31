$(function () {
});
function setup() {

    //自定义validatebox规则
    $.extend($.fn.validatebox.defaults.rules,{
        equalTo: {
            validator: function (value, param) {
                return value == $(param[0]).val();
            },
            message: '两次输入的字符不一至'
        },
        idCode:{
            validator:function(value,param){
                return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
            },
            message: '请输入正确的身份证号'
        },
        loginName: {
            validator: function (value, param) {
                return /^[\u0391-\uFFE5\w]+$/.test(value);
            },
            message: '登录名称只允许汉字、英文字母、数字及下划线。'
        }
    });
    
    var userName = $('#jdbc_username').val();
    var userPass = $('#jdbc_password').val();
    var driver = $('#jdbc_driver').val();
    var url = $('#database').val();
    var result= "";
    //var userName ;
    //做一层验证
    if (userName == '') {
        document.getElementById("showInfo").style.display = "block";
        document.getElementById("showInfo").innerText = "用户名不能为空!";
        //alert('用户名不能为空!');
    } else if (userPass == '') {
        document.getElementById("showInfo").style.display = "block";
        document.getElementById("showInfo").innerText = "密码不能为空!";
    }
    else {
        $.ajax({
            type: "post",   //post提交方式默认是get
            url: getRealPath()+'/DataManagement/setup/'+userName+"/"+userPass+"/"+driver,
            async: false,
            error: function () {
                alert("error!");
            },
            success: function (data) {
                if(data.code==100) {
                    alert("ok!");
                }else {
                    alert("no!");
                }
            }
        });
    }
}


function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath ;
}
