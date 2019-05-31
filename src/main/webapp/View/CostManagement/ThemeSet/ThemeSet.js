var datacom1 = [
    {
        "id" : "dark",
        "text" : "dark"
    },
    {
        "id" : "chalk",
        "text" : "chalk"
    },
    {
        "id" : "infographic",
        "text" : "infographic"
    },
    {
        "id" : "essos",
        "text" : "essos"
    },
    {
        "id" : "macarons",
        "text" : "macarons"
    },
    {
        "id" : "purple-passion",
        "text" : "purple-passion"
    },
    {
        "id" : "theme1",
        "text" : "theme1"
    },
];
var CostItemID = "";
var Level = 1;
var Action = "init";
var chartID = null;//当前图表的编号
var chartName = null;//当前图表的名称
var selectChartTheme = null;//图表主题

$(document).ready(function () {
    //初始化表格
    InitGrid();
});

$(function () {
    //项目功能下拉菜单
    $('#ChartTheme').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom1,
        onSelect: function () {

        }
    });
});

//初始化datagrid,界面是第一个
function InitGrid() {
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
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
            { title: '图表主题', field: 'ChartTheme', width: 40 }
        ]],
        toolbar: [{
            id: 'btnEdit',
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                editGrid();//实际上是进入新的界面
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
function Get_graphAll(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ThemeSet/getGraphAll/',
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_graphAll = result.extend.graphAll_theme;
                $('#grid').datagrid("loadData",data_graphAll);
            }
        }
    });
}

//修改
function editGrid() {
    var row = $('#grid').datagrid('getSelected');
    if (row === null || row.length < 1) {
        $.messager.alert("提示", "请先选择一条记录！", "info");
    } else if(row.length > 1){
        $.messager.alert("提示", "请只选择一条记录！", "info");
    }else if(row.length = 1) {
        chartID = row.ChartID;
        chartName = row.ChartName;
        ShowEditDialog(chartID,chartName);
    }
}

//具体字段添加
function ShowEditDialog(chartID,chartName) {
    $('#fm').form('clear');//清空内容
    $('#ChartID').val(chartID);
    $('#ChartName').val(chartName);

    // //赋值后验证一次
    // $('#ItemID_fixed').validatebox('validate');
    // $('#ItemName_fixed').validatebox('validate');

    $('#ChartID').attr("disabled", "disabled");//图表编号是主键，设为不可修改
    $('#ChartName').attr("disabled", "disabled");

    $('#dlg').dialog('open').dialog('setTitle', '添加指标');//打开对话框
    document.getElementById("test").value = "edit";
}

//保存信息
function saveData() {
    var chartIDSelect = document.getElementById("ChartID").value;
    var chartThemeSelect = $('#ChartTheme').combobox('getText');

    $.ajax({
        type: "POST",
        url: getRealPath() + '/ThemeSet/addThemeInfo/' + chartIDSelect + '/' + chartThemeSelect ,
        contentType: 'json',
        async: false,
        success: function (result) {
            if (result.code == 100) {
                $('#dlg').dialog('close');
                if(result.extend.addThemeInfo === "成功") {
                    $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                }else if(result.extend.addThemeInfo === "编号重复"){
                    $.messager.alert("提示", "编号已存在", "info");
                }
                //$('#grid').datagrid('clearSelections'); //清空选中的行
                InitGrid();
            } else {
                $.messager.alert("提示", "添加失败，请重新操作！", "info");
                //return;
            }
        }
    });
}

function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;

}