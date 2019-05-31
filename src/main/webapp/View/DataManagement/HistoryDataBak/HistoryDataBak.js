$(function () {
    $("#tt").tree({
        data:[{
            "id": "000000",
            "text": "财务功能管理",
            "children": [{
                "id": "010000",
                "text": "账务处理",
            }, {
                "id": "020000",
                "text": "报表管理",
            }]
        }],
        checkbox: true,
        cascadeCheck: true,
    });
    $('#databakquit').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-undo'
    });
    $('#databaksave').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-redo'
    });

    $('#chooseyear').combobox({
        valueField: 'year',
        textField: 'year',
        panelHeight: 'auto',
    });

    var data = [];
    var startYear;
    var thisYear = new Date().getUTCFullYear();
    var endYear = thisYear + 1;
    for (startYear = endYear - 4; startYear <=endYear; startYear++) {
        data.push({ "year": startYear });
    }
    $('#chooseyear').combobox("loadData",data);//下拉框加载数据
    $('#chooseyear').combobox("setValue", thisYear);//设置默认值是今年


});

function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath ;
}

//历史数据恢复
function historyupLoadFile() {  //点击确定按钮提交表单的方法
    var nodes = $('#tt').tree('getChecked');
    var tablename = '';
    var path;
    for (var i = 0; i < nodes.length; i++) {
        if (tablename != '') tablename += ',';
        tablename += nodes[i].text;
    }
    path = document.getElementById("txtFile").value;
    document.getElementById("path").value = path;
    document.getElementById("tableName").value = tablename;//用隐藏的input标签进行传值
    $.messager.progress({
        text: '正在上传中...',
    });
    $("#exportForm").form('submit',{//AJAX方式的提交表单

        url:getRealPath()+'/DataManagement/historyupLoadFile',
        onSubmit : function() {  //提交一个有效并且避免重复提交的表单
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
            }
            return isValid;	// 返回false终止表单提交

        },
        success : function(result) {
            $.messager.progress('close');
            if(result=='true'){
                $.messager.show({
                    title: '提示',
                    msg: "转入成功！",
                });
            }else {
                $.messager.alert({
                    title:'警告',
                    msg:"转入失败！",
                });
            }
        }
    });
}

//历史数据备份
function historyDownloadFile() {  //点击确定按钮提交表单的方法
    var nodes = $('#tt').tree('getChecked');
    var tablename = '';
    var path;
    for (var i = 0; i < nodes.length; i++) {
        if (tablename != '') tablename += ',';
        tablename += nodes[i].text;
    }
    path = document.getElementById("txtFile").value;
    document.getElementById("path").value = path;
    document.getElementById("tableName").value = tablename;//用隐藏的input标签进行传值

    $.messager.progress({
        text: '正在备份中...',
    });
    $("#exportForm").form('submit',{//AJAX方式的提交表单

        url:getRealPath()+'/DataManagement/historyDownloadFile',
        onSubmit : function() {  //提交一个有效并且避免重复提交的表单
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
            }
            return isValid;	// 返回false终止表单提交

        },
        success : function(result) {
            $.messager.progress('close');
            if(result=='true'){
                $.messager.show({
                    title: '提示',
                    msg: "备份成功！",
                });
            }else {
                $.messager.alert({
                    title:'警告',
                    msg:"备份失败！",
                });
            }
        }
    });
}