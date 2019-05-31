$(function(){
    getCurrentDate();

    $('#time_box').window({
        width: 320,
        height: 160,
        title: '财务日期维护',
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        collapsible: false
    });
    $('#yes_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-ok',
        onClick: function () {
            setCurrentDate();
        }
    });
    $('#cancel_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-cancel'
    });
});

function getCurrentDate(){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/MonthlyStatement/checkCurrentDate',
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var date = result.extend.date;
                fillDate(date);
            }
            else{
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}

function fillDate(date){
    var y = parseInt(date.substr(0, 4), 10);
    var m = parseInt(date.substr(4, 2), 10);
    var d = parseInt(date.substr(6, 2), 10);
    $('#former_year').numberbox('setValue', y);
    $('#former_mouth').numberbox('setValue', m);
    $('#former_day').numberbox('setValue', d);
    $('#new_year').numberbox('setValue', y);
    $('#new_mouth').numberbox('setValue', m);
    $('#new_day').numberbox('setValue', d);
}

function setCurrentDate(){
    var y = $('#new_year').numberbox('getValue');
    var m = $('#new_mouth').numberbox('getValue');
    var d = $('#new_day').numberbox('getValue');
    var date = y+(m<10?('0'+m):m)+(d<10?('0'+d):d);
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/MonthlyStatement/setCurrentDate/' + date,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var return_date = result.extend.date;
                if (return_date == date) {
                    fillDate(date);
                    $.messager.alert("提示", "财务日期修改成功", "info");
                }
                else {
                    $.messager.alert("提示", "财务日期修改失败", "info");
                }
            }
            else{
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}

//获取路径
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
//
//     return basePath ;
// }