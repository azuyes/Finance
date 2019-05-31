/**
 * Created by ChenZH on 2018/5/22.
 */
$(function() {
    $.messager.confirm('确认对话框', '是否要进行月结处理？', function(r){
        if (r){
            checkCurrentDate();
        }
        else{
            //TODO:关闭选项卡
            return;
        }
    });

});

function checkCurrentDate(){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/MonthlyStatement/checkCurrentDate',
        contentType: 'application/json',
        success: function(result) {
            if(result.code == 100) {
                var date = result.extend.date;
                var day_count = getMonthDayCount(date.substr(0, 4),date.substr(4, 2));
                if(day_count == date.substr(6, 2)){
                    checkReviewedStatement(date.substr(0, 6));
                }
                else{
                    $.messager.alert('提示', '财务日期未到月末', 'info');
                }
            }
            else{
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}


//检查指定年月的天数总数
function getMonthDayCount(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}

//检查凭证是否全部审核
function checkReviewedStatement(year_month){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/MonthlyStatement/checkReviewedStatement/'+ year_month,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.info;
                if(data == 'pass'){
                    //$.messager.alert('提示', '当月凭证均已审核', 'info');
                    checkLossNProfitBalance(year_month)
                }
                else{
                    $.messager.alert('提示', '当月尚有未审核凭证', 'info');
                }
            }
            else {
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}

function checkLossNProfitBalance(year_month){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/MonthlyStatement/checkLossNProfitBalance/'+ year_month,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.info;
                if(data == 'pass'){
                    //$.messager.alert('提示', '当月损益类科目余额为0', 'info');
                    checkCurrentMonth(year_month);
                }
                else{
                    $.messager.alert('提示', '当月损益类科目余额不为0', 'info');
                }
            }
            else {
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}

//检查是否到了12月需要年结
function checkCurrentMonth(year_month){
    var url = '';
    if(year_month.substr(4, 2) == '12'){
        url = getRealPath() + '/MonthlyStatement/yearlyStatement/' + year_month.substr(0, 4);
    }
    else{
        url = getRealPath() + '/MonthlyStatement/initNewMonth/' + year_month;
    }
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                $.messager.alert('提示', '月结已完成', 'info');
            }
            else {
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