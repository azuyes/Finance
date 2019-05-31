$(document).ready(function () {
    //初始化表格
    DataBak();
})
function DataBak() {

    //点击关闭按钮页面销毁
    $("#databak").window({
        onClose: function () {
            $("#databak").window('destroy');//销毁
            window.parent.tabsClose();
        }
    });

    $("#tt").tree({//数据表单
        data:[{
            "id": "000000",
            "text": "财务功能管理",
            "children": [{
                "id": "010000",
                "text": "账务处理"
            }, {
                "id": "020000",
                "text": "报表管理"
            }]
        }],
        checkbox: true,
        cascadeCheck: true,
    });
    $('#databakquit').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-cancel'
    });
    $('#databaksave').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-ok',
    });


    $('#turnintoDialog').dialog({
        width: 300,
        height: 200,
        title: '数据恢复转入',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {  //点击确定按钮提交表单的方法
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
                $("#importForm").form('submit',{//AJAX方式的提交表单

                    url:getRealPath()+'/DataManagement/upLoadFile',
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
                $('#turnintoDialog').dialog('close');
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#turnintoDialog').dialog('close');
            }
        }],
    });

    //转出的按键
    $('#turnoutDialog').dialog({
        width: 300,
        height: 200,
        title: '数据备份转出',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {  //点击确定按钮提交表单的方法
                var nodes = $('#tt').tree('getChecked');
                var tablename = '';
                var path;
                for (var i = 0; i < nodes.length; i++) {
                    if (tablename != '') tablename += ',';
                    tablename += nodes[i].text;
                }
                path = document.getElementById("backupFile").value;
                document.getElementById("path1").value = path;
                document.getElementById("tableName1").value = tablename;//用隐藏的input标签进行传值
                $.messager.progress({
                    text: '正在备份中...',
                });
                $("#exportForm").form('submit',{//AJAX方式的提交表单

                    url:getRealPath()+'/DataManagement/downloadFile',
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
                $('#turnintoDialog').dialog('close');
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#turnoutDialog').dialog('close');
            }
        }],
    });
};


//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath ;
}