var CostItemID = "";
var Level = 1;
var Action = "init";
var gridNo = null;//当前图表的编号
var gridName = null;//当前图表的名称
var selectItemFunctionValue = null;//项目功能名称
var selectItemTypeValue = null;//项目数据类型
var selectItemNameValue = null;//项目名称
var itemNo = null;//项目编号

$(document).ready(function () {
    //初始化表格
    InitGrid();
});

$(function () {
    //项目功能下拉菜单
    $('#ItemFunction').combobox({
        valueField: '1',
        textField: '0',
        onSelect: function () {
            $('#ItemID').val("");
            $('#ItemType').combobox('clear');
            $('#ItemName').combobox('clear');
            selectItemFunctionValue = $('#ItemFunction').combobox('getText');
            itemTypeSelect(gridNo,selectItemFunctionValue);
            itemNameSelect(selectItemTypeValue);
        }
    });

    //项目数据类型下拉菜单
    $('#ItemType').combobox({
        valueField: '1',
        textField: '0',
        onSelect: function () {
            $('#ItemID').val("");
            $('#ItemName').combobox('clear');
            selectItemTypeValue = $('#ItemType').combobox('getText');
            itemNameSelect(selectItemTypeValue)
        }
    });

    //项目名称下拉菜单
    $('#ItemName').combobox({
        valueField: '1',
        textField: '0',
        onSelect: function () {
            selectItemNameValue = $('#ItemName').combobox('getText');
            itemNo = $('#ItemName').combobox('getValue');
            $('#ItemID').val(itemNo);
            //itemNoSelect(selectItemNameValue);
        }
    });
});

//--------------------  第一界面  -----------------------------------

//初始化datagrid,界面是第一个
function InitGrid() {
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        //url: getRealPath()+'/QuotaSet/getGraphAll/' ,   //指向后台的Action来获取当前菜单的信息的Json格式的数据
        //title: '产品维护',
        //iconCls: 'icon-save',
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        dataType: "json",
        fit: true,
        nowrap: true,
        autoRowHeight: false,
        striped: true,
        singleSelect: true,//只允许选中一行
        collapsible: true,
        pagination: false,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        sortName: 'ChartID',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'ChartID',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ck', checkbox: true },   //选择
            { title: '图表编号', field: 'ChartID', width: 40 },
            { title: '图表名称', field: 'ChartName', width: 40 },
        ]],
        toolbar: [{
            id: 'btnNext',
            text: '下一级',
            iconCls: 'icon-next',
            handler: function () {
                NextLevel();//实现下一级
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_graphAll();
}

//获取图表项目名称
function Get_graphAll() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/QuotaSet/getGraphAll/',
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_graphAll = result.extend.graphAll;
                console.log(data_graphAll);
                $('#grid').datagrid("loadData",data_graphAll);
            }
        }
    });
}

//下一级按钮点击事件
function NextLevel() {
    var row = $('#grid').datagrid('getSelected');
    if (row) {
        gridNo = row.ChartID;
        gridName = row.ChartName;
        gridInfoSet(gridNo,gridName);
        //Level = row.CostItemLevel;
        // if (parseInt(Level) + 1 < 5) {
        //     Level = parseInt(Level) + 1;
        //     InitGrid(Level, CostItemID, Action);
        // } else {
        //     $.messager.alert("提示", "没有下一级了", "info");
        // }
    } else {
        $.messager.alert("提示", "请先选择一条记录！", "info");
        InitGrid();
    }
}

//--------------------  第二界面 ---------------------------------

//图表显示内容设置
function gridInfoSet(gridNo,gridName) {
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        //url: getRealPath()+'/QuotaSet/getGraphAll/' ,   //指向后台的Action来获取当前菜单的信息的Json格式的数据
        //title: '产品维护',
        //iconCls: 'icon-save',
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        dataType: "json",
        fit: true,
        nowrap: true,
        autoRowHeight: false,
        striped: true,
        singleSelect: true,//只允许选中一行
        collapsible: true,
        pagination: false,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        sortName: 'ItemType',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'ItemID',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ck', checkbox: true },   //选择
            { title: '项目编号', field: 'ItemID', width: 40 },
            { title: '项目名称', field: 'ItemName', width: 40 },
            { title: '所属类型', field: 'ItemType', width: 40 },
            { title: '项目功能', field: 'ItemFunction', width: 40 },
        ]],
        toolbar: [{
            id: 'btnLast',
            text: '上一级',
            iconCls: 'icon-back',
            handler: function () {
                LastLevel();//实现下一级
            }
        }, '-',  {
            id: 'btnAdd',
            text: '添加',
            iconCls: 'icon-add',
            handler: function () {
                ShowAddDialog(gridNo,gridName);//实现添加记录的页面
            }
        }, '-',  {
            id: 'btnDelete',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                deleteData(gridNo,gridName);//实现直接删除数据的方法
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_graphInfo(gridNo);
    //itemTypeSelect(gridNo);
}

//获取指定具体图表的信息
function Get_graphInfo(gridNo){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/QuotaSet/getGraphInfo/' + gridNo ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_graphInfo = result.extend.graphInfo;
                console.log(data_graphInfo);
                $('#grid').datagrid("loadData",data_graphInfo);
            }
        }
    });
}

//上一级按钮点击事件
function LastLevel() {
    // Action = "last";
    // if (parseInt(Level) - 1 > 0) {
    //     Level = parseInt(Level) - 1;
    //     InitGrid(Level, CostItemID, Action);
    // } else {
    //     $.messager.alert("提示", "没有上一级了", "info");
    // }
    $('#grid').datagrid('clearSelections');
    InitGrid();
}

//添加
function ShowAddDialog(gridNo,gridName) {
    $('#fm').form('clear');//清空内容
    //获取要修改的字段
    $('#ChartID').val(gridNo);
    $('#ChartName').val(gridName);

    //赋值后验证一次
    $('#ChartID').validatebox('validate');
    $('#ChartName').validatebox('validate');

    $('#ChartID').attr("disabled", "disabled");//图表编号是主键，设为不可修改
    $('#ChartName').attr("disabled", "disabled");
    $('#ItemID').attr("disabled", "disabled");
    //$('#ItemType').attr("disabled", "disabled");
    //$('#CostItemID').val(CostItemID.substring(0, (Level - 1) * 2));

    itemFunctionSelect(gridNo);

    $('#dlg').dialog('open').dialog('setTitle', '添加指标');//打开对话框
    document.getElementById("test").value = "add";
}

//项目功能下拉菜单的数据获取
function itemFunctionSelect(gridNo) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/QuotaSet/getItemFunctionSelect/' + gridNo ,
        contentType: 'application/json',
        async: false,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.itemFunctionSelect;
                console.log(data1);
                $('#ItemFunction').combobox("loadData", data1);
                // var data = $('#DDL_SelectCompany').combobox('getData');
                // console.log(data);
                // $('#DDL_SelectCompany').combobox('select',data[0][0]);
            }
        }
    });
}

//根据项目功能获取项目类型数据
function itemTypeSelect(gridNo,selectItemFunctionValue) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/QuotaSet/getItemTypeSelect/' + gridNo +  '/' + selectItemFunctionValue ,
        contentType: 'application/json',
        async: false,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.itemTypeSelect;
                console.log(data1);
                $('#ItemType').combobox("loadData", data1);
                // var data = $('#DDL_SelectCompany').combobox('getData');
                // console.log(data);
                // $('#DDL_SelectCompany').combobox('select',data[0][0]);
            }
        }
    });
}

//项目名称下拉菜单的数据获取
function itemNameSelect(selectItemTypeValue) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/QuotaSet/getItemNameSelect/' + selectItemTypeValue,
        contentType: 'application/json',
        async: false,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.itemNameSelect;
                console.log(data1);
                $('#ItemName').combobox("loadData", data1);
                // var data = $('#DDL_SelectCompany').combobox('getData');
                // console.log(data);
                // $('#DDL_SelectCompany').combobox('select',data[0][0]);

            }
        }
    });
}

//删除行
function deleteData(gridNo,gridName) {
    var adminID = document.getElementById("AdminID").value = "0";
    var test = document.getElementById("test").value = "delete";
    var row = $('#grid').datagrid('getSelections');
    var num = 0;
    if (row.length < 1) {
        $.messager.alert("提示", "请选择要删除的行！", "info");
    } else {
        $.messager.confirm("删除确认", "您确认删除选定的记录吗？", function (deleteAction) {
            if (deleteAction) {
                for (var i = 0; i < row.length; i++) {
                    var ItemID = row[i].ItemID;
                    var ItemType = row[i].ItemType;
                    $.ajax({
                        type: "POST",
                        url: getRealPath() + '/QuotaSet/deleteGraphInfo/' + gridNo +  '/' + ItemID + '/' + ItemType,
                        contentType: 'json',
                        async: false,
                        success: function (result) {
                            if (result.code == 100) {
                                num++;
                            }
                        }
                    });
                    if (num >= 1) {
                        $('#grid').datagrid('clearSelections'); //清空选中的行
                        gridInfoSet(gridNo,gridName);
                        $.messager.alert("提示", "恭喜您，成功删除" + num + "条信息！", "info");
                    }
                    else {
                        $.messager.alert("提示", "删除失败，请重新操作！", "info");
                        gridInfoSet(gridNo,gridName);
                        return;
                    }
                }
            }
        });
    }
}

//保存信息
function saveData() {
    var adminID = document.getElementById("AdminID").value = "0";
    var gridNo = document.getElementById("ChartID").value;
    var gridName = document.getElementById("ChartName").value;
    var ItemID = document.getElementById("ItemID").value;
    var ItemType = $("#ItemType").combobox("getValue");
    var ItemFunction = $("#ItemFunction").combobox("getValue");
    //var CostItemName = document.getElementById("CostItemName").value;
    //var Viewable = $("#Viewable").combobox("getValue");

    var test = document.getElementById("test").value;

    if (test == "add") {
        // $('#fm').form('submit', {
        //     url: getRealPath() + '/QuotaSet/addGraphInfo/' + gridNo +  '/' + ItemID + '/' + ItemType,
        //     async: false,
        //     onSubmit: function () {
        //         return $(this).form('validate');
        //     },
        //     success: function (data) {
        //         if (data.extend.addgraphInfo === "成功") {
        //             $('#dlg').dialog('close');
        //             $.messager.alert("提示", "恭喜您，信息添加成功", "info");
        //             $('#grid').datagrid('clearSelections'); //清空选中的行
        //             gridInfoSet(gridNo,gridName);
        //             //$('#grid').datagrid('reload');
        //             //$('#fm').form('submit');
        //         }
        //         else {
        //             $.messager.alert("提示", "添加失败，请重新操作！", "info");
        //             return;
        //         }
        //     }
        // });
        $.ajax({
            type: "POST",
            url: getRealPath() + '/QuotaSet/addGraphInfo/' + gridNo +  '/' + ItemID + '/' + ItemType + '/' + ItemFunction ,
            contentType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 100) {
                    $('#dlg').dialog('close');
                    if(result.extend.addgraphInfo === "成功") {
                        $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                    }else if(result.extend.addgraphInfo === "编号重复"){
                        $.messager.alert("提示", "编号已存在", "info");
                    }
                    $('#grid').datagrid('clearSelections'); //清空选中的行
                    gridInfoSet(gridNo, gridName);
                } else {
                    $.messager.alert("提示", "添加失败，请重新操作！", "info");
                    //return;
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