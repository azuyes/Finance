$(function () {
});
function denglu() {

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
    
    var userNo = $('#userNo').val();
    var userPass = $('#userPass').val();
    var dataSource = $('#dataSource').val();
    var result= "";
    var userName ;
    //做一层验证
    if (userNo == '') {
        document.getElementById("showInfo").style.display = "block";
        document.getElementById("showInfo").innerText = "用户编号不能为空!";
        //alert('用户名不能为空!');
    } else if (userPass == '') {
        document.getElementById("showInfo").style.display = "block";
        document.getElementById("showInfo").innerText = "密码不能为空!";
    }
    else {
        // $.ajax({
        //     type: "post",   //post提交方式默认是get
        //     url: getRealPath()+'/DataManagement/changeDatabase/'+dataSource,
        //     async: false,
        //     error: function () {
        //         alert("数据库更改失败!");
        //     },
        //     success: function (data) {
        //
        //     }
        // });
        $.ajax({
            type: "post",   //post提交方式默认是get
            url: getRealPath()+'/UserManagement/login/'+userNo+"/"+userPass,
            async: false,
            success: function (data) {
                if(data.code==100) {
                    result = data.extend.userName;
                }else {
                    result = " ";
                }
            }
        });

        if (result == " ") {
            //document.getElementById("showInfo").style.display = "block";
            $('#showInfo').css('display','block');
            document.getElementById("showInfo").innerText = "用户编号不存在或密码错误!";
            alert("用户名或者密码错误!");
        }
        else if (result != " ") {
            //alert('!');
            //window.location.href="View/Index/index.html";
            window.open("View/Index/index.html?userName="+result+"&userNo="+userNo+"", '_self');
            document.cookie="status=success;path=/";
            //document.cookie="userName=" + result + ";path=/";
            document.cookie="userNo=" + userNo + ";path=/";

            window.localStorage.setItem('userName', result);
            window.localStorage.setItem('userNo', userNo);
        }
    }
}

function addDatabase(){
    $.ajax({
        type: "post",   //post提交方式默认是get
        url: getRealPath()+'/DataManagement/createDatabase/cxfinance2',
        async: false,
        error: function () {
            alert("数据库添加失败!");
        },
        success: function (data) {

        }
    });
}

function hideShowPsw() {

    var demo_img = document.getElementById("img");
    var demoInput = document.getElementById("userPass");
    if (demoInput.type == "password") {
        demoInput.type = "text";
        demo_img.src = "View/Resourse/Login/visible.png";
    }else {
        demoInput.type = "password";
        demo_img.src = "View/Resourse/Login/invisible.png";
    }
}

function setup() {
    window.open('setup.html');
}

// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
//     return basePath ;
// }
