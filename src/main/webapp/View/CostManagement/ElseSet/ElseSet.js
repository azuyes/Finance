var datacom1 = [
    {
        "id" : "核算类别",
        "text" : "核算类别"
    },
    {
        "id" : "核算编号",
        "text" : "核算编号"
    },
    {
        "id" : "科目编号",
        "text" : "科目编号"
    }
];
var datacom2 = [
    {
        "id" : "科目编号",
        "text" : "科目编号"
    },
    {
        "id" : "自定义字段",
        "text" : "自定义字段"
    }
];
var datacom3 = [
    {
        "id" : "1",
        "text" : "是"
    },
    {
        "id" : "0",
        "text" : "否"
    }
];
var initGrid_selectFunction = null;
var selectItemNameValue = null;
var selectItemTypeValue = null;
var itemNo = null;
var itemNo_defined = null;
var itemNo_fixed = null;//固定项目编号
var itemName_fixed = null;//固定项目名称
var relateItemNo_fixed = null;//固定项目关联字段编号
var relateItemName_fixed = null;//固定项目关联字段名称
var relateItemType_fixed = null;//固定项目关联字段类型
var selectRelatedItemType = null;//固定项目关联的字段类型
var selectRelatedItemName = null;//固定项目关联的字段名称
var selectRelatedItemNo = null;//固定项目关联的字段编号

$(document).ready(function () {
    //初始化表格
    InitGrid();
});

$(function () {
    //项目类型下拉菜单
    $('#ItemType_defined').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom2,
        onSelect: function () {
            $('#ItemName_defined').combobox('clear');
            $('#ItemID_defined').val('');
            selectItemTypeValue = $('#ItemType_defined').combobox('getText');
            itemNameSelect_defined(selectItemTypeValue);
        }
    });

    //项目名称下拉菜单
    $('#ItemName_defined').combobox({
        valueField: '1',
        textField: '0',
        onSelect: function () {
            selectItemNameValue = $('#ItemName_defined').combobox('getText');
            itemNo = $('#ItemName_defined').combobox('getValue');
            $('#ItemID_defined').val(itemNo);
            //itemNoSelect(selectItemNameValue);
        }
    });

    //关联字段类型下拉菜单
    $('#RelatedItemType').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom1,
        onSelect: function () {
            $('#RelatedItemName').combobox('clear');
            $('#RelatedItemNo').combobox('clear');
            selectRelatedItemType = $('#RelatedItemType').combobox('getText');
            relatedItemNameSelect_fixed(selectRelatedItemType);
            relatedItemNoSelect_fixed(selectRelatedItemType);
        }
    });

    //关联字段名称下拉菜单
    $('#RelatedItemName').combobox({
        valueField: 'id',
        textField: 'text',
        onSelect: function () {
            selectItemNameValue = $('#RelatedItemName').combobox('getText');
            relateItemNo_fixed = $('#RelatedItemName').combobox('getValue');
            $('#RelatedItemNo').combobox('setValues',relateItemNo_fixed);
            //itemNoSelect(selectItemNameValue);
        }
    });

    //关联字段编号下拉菜单
    $('#RelatedItemNo').combobox({
        valueField: 'id',
        textField: 'text',
        onSelect: function () {
            selectItemNameValue = $('#RelatedItemNo').combobox('getText');
            relateItemName_fixed = $('#RelatedItemNo').combobox('getValue');
            $('#RelatedItemName').combobox('setValues',relateItemName_fixed);
            //itemNoSelect(selectItemNameValue);
        }
    });

    //关联字段编号下拉菜单
    $('#IsShow').combobox({
        valueField: 'id',
        textField: 'text',
        onSelect: function () {
            var selectShowText = $('#IsShow').combobox('getText');
            var selectShowValue = $('#IsShow').combobox('getValue');
            $('#IsShow').combobox('setValues',selectShowText);
            //itemNoSelect(selectItemNameValue);
        }
    });
});

//--------------------  第一界面  -----------------------------------
//初始化datagrid,界面是第一个
function InitGrid() {
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        //url: getRealPath()+'/QuotaSet/getGraphAll/' ,   //指向后台的Action来获取当前菜单的信息的Json格式的数据
        title: '其他设置界面',
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
        sortName: 'SetName',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'SetName',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ck', checkbox: true },   //选择
            { title: '设置内容', field: 'SetName', width: 40 },
        ]],
        toolbar: [{
            id: 'btnNext',
            text: '下一级',
            iconCls: 'icon-next',
            handler: function () {
                NextLevel1();//实现下一级
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_initGrid();
}

//获取图表项目名称
function Get_initGrid() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ElseSet/getInitGrid/',
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_initGrid = result.extend.InitGrid;
                $('#grid').datagrid("loadData",data_initGrid);
            }
        }
    });
}

//下一级按钮点击事件
function NextLevel1() {
    var row = $('#grid').datagrid('getSelected');
    if (row) {
        initGrid_selectFunction = row.SetName;
        if(initGrid_selectFunction === "自定义字段"){
            definedItemGrid();
        }else if(initGrid_selectFunction === "字段关联"){
            fixedItemGrid();
        }else{
            showMenuSelect();
        }
    } else {
        $.messager.alert("提示", "请先选择一条记录！", "info");
        InitGrid();
    }
}

//--------------------  自定义字段界面 ---------------------------------
//自定义界面，显示存在的自定义科目
function definedItemGrid() {
    $('#grid').datagrid('clearSelections');
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        title: "自定义项目",
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
        sortName: 'ItemID',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'ItemID',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ck', checkbox: true },   //选择
            { title: '项目编号', field: 'ItemID', width: 40 },
            { title: '项目名称', field: 'ItemName', width: 40 },
        ]],
        toolbar: [{
            id: 'btnLast',
            text: '上一级',
            iconCls: 'icon-back',
            handler: function () {
                LastLevel();//实现下一级
            }
        }, '-', {
            id: 'btnEdit',
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                editGrid_defined();//实际上是进入新的界面
            }
        }, '-', {
            id: 'btnAdd',
            text: '添加',
            iconCls: 'icon-add',
            handler: function () {
                ShowAddDialog1();//实现添加记录的页面
            }
        }, '-',  {
            id: 'btnDelete',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                deleteData1();//实现直接删除数据的方法
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_definedItemGridInfo();
}

//获取自定义表格的信息
function Get_definedItemGridInfo(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ElseSet/getDefinedItemGridInfo/',
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_definedItemGridInfo = result.extend.definedItemGridInfo;
                $('#grid').datagrid("loadData",data_definedItemGridInfo);
            }
        }
    });
}

//上一级按钮点击事件
function LastLevel() {
    InitGrid();
}

//添加
function ShowAddDialog1() {
    $('#fm').form('clear');//清空内容

    $('#dlg').dialog('open').dialog('setTitle', '添加指标');//打开对话框
    document.getElementById("test").value = "add";
}

//删除行
function deleteData1() {
    var adminID = document.getElementById("AdminID").value = "0";
    var test = document.getElementById("test").value = "delete";
    var row = $('#grid').datagrid('getSelections');
    var num = 0;
    if (row === null || row.length < 1) {
        $.messager.alert("提示", "请选择要删除的行！", "info");
    } else {
        $.messager.confirm("删除确认", "您确认删除选定的记录吗？", function (deleteAction) {
            if (deleteAction) {
                for (var i = 0; i < row.length; i++) {
                    var ItemID = row[i].ItemID;
                    $.ajax({
                        type: "POST",
                        url: getRealPath() + '/ElseSet/deleteDefinedItemInfo/' + ItemID ,
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
                        definedItemGrid();
                        $.messager.alert("提示", "恭喜您，成功删除" + num + "条信息！", "info");
                    }
                    else {
                        $.messager.alert("提示", "删除失败，请重新操作！", "info");
                        definedItemGrid();
                        return;
                    }
                }
            }
        });
    }
}

//保存信息
function saveData() {
    var ItemID = document.getElementById("ItemID").value;
    var ItemName = document.getElementById("ItemName").value;

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
            url: getRealPath() + '/ElseSet/addDefinedItemInfo/'  + ItemID + '/' + ItemName  ,
            contentType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 100) {
                    $('#dlg').dialog('close');
                    if(result.extend.addDefinedItemInfo === "成功") {
                        $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                    }else if(result.extend.addDefinedItemInfo === "编号重复"){
                        $.messager.alert("提示", "编号已存在", "info");
                    }
                    $('#grid').datagrid('clearSelections'); //清空选中的行
                    definedItemGrid();
                } else {
                    $.messager.alert("提示", "添加失败，请重新操作！", "info");
                    //return;
                }
            }
        });
    }
}

//--------------------  固定字段界面 ---------------------------------
//自定义界面，显示存在的自定义科目(固定字段界面)
function fixedItemGrid() {
    $('#grid').datagrid('clearSelections');
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        title: "字段关联",
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
        sortName: 'ItemID',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'ItemID',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ck', checkbox: true },   //选择
            { title: '项目编号', field: 'ItemID', width: 40 },
            { title: '项目名称', field: 'ItemName', width: 40 },
            { title: '关联字段编号', field: 'relateItemNo', width: 40 },
            { title: '关联字段名称', field: 'relateItemName', width: 40 },
            { title: '关联字段类型', field: 'relateItemType', width: 40 },
        ]],
        toolbar: [{
            id: 'btnLast',
            text: '上一级',
            iconCls: 'icon-back',
            handler: function () {
                LastLevel();//实现下一级
            }
        }, '-', {
            id: 'btnEdit',
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                editGrid_fixed();//实际上是进入新的界面
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_fixedItemGridInfo();
}

//获取固定字段的信息(固定字段界面)
function Get_fixedItemGridInfo(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ElseSet/getFixedItemGridInfo/',
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_fixedItemGridInfo = result.extend.fixedItemGridInfo;
                $('#grid').datagrid("loadData",data_fixedItemGridInfo);
            }
        }
    });
}

//修改，判断是否符合条件（固定字段界面）
function editGrid_fixed() {
    var row = $('#grid').datagrid('getSelected');
    if (row === null || row.length < 1) {
        $.messager.alert("提示", "请先选择一条记录！", "info");
    } else if(row.length > 1){
        $.messager.alert("提示", "请只选择一条记录！", "info");
    }else if(row.length = 1) {
        itemNo_fixed = row.ItemID;
        itemName_fixed = row.ItemName;
        ShowEditDialog_fixed(itemNo_fixed,itemName_fixed);
    }
}

//具体字段添加（固定字段界面）
function ShowEditDialog_fixed(itemNo_fixed,itemName_fixed) {
    $('#fm_fixed').form('clear');//清空内容
    $('#ItemID_fixed').val(itemNo_fixed);
    $('#ItemName_fixed').val(itemName_fixed);

    // //赋值后验证一次
    // $('#ItemID_fixed').validatebox('validate');
    // $('#ItemName_fixed').validatebox('validate');

    $('#ItemID_fixed').attr("disabled", "disabled");//图表编号是主键，设为不可修改
    $('#ItemName_fixed').attr("disabled", "disabled");

    $('#dlg_fixed').dialog('open').dialog('setTitle', '添加指标');//打开对话框
    document.getElementById("test_fixed").value = "edit";
}

//关联字段名称下拉菜单的数据获取（固定字段界面）
function relatedItemNameSelect_fixed(selectRelatedItemType) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ElseSet/getRelateItemNameSelect_fixed/'  + selectRelatedItemType,
        contentType: 'application/json',
        async: false,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.relateItemNameSelect_fixed;
                console.log(data1);
                $('#RelatedItemName').combobox("loadData", data1);
            }
        }
    });
}

//关联字段编号下拉菜单的数据获取（固定字段界面）
function relatedItemNoSelect_fixed(selectRelatedItemType) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ElseSet/getRelateItemNoSelect_fixed/'  + selectRelatedItemType,
        contentType: 'application/json',
        async: false,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.relateItemNoSelect_fixed;
                console.log(data1);
                $('#RelatedItemNo').combobox("loadData", data1);
            }
        }
    });
}

//保存信息（自定义字段修改）
function saveData_fixed() {
    relateItemType_fixed = $('#RelatedItemType').combobox('getText');
    relateItemNo_fixed = $('#RelatedItemNo').combobox('getText');
    var ItemID_fixed = document.getElementById("ItemID_fixed").value;
    var test = document.getElementById("test_fixed").value;

    if (test == "edit") {
        $.ajax({
            type: "POST",
            url: getRealPath() + '/ElseSet/addFixedItemInfo_fixed/' + relateItemType_fixed + '/' + relateItemNo_fixed + '/' + ItemID_fixed,
            contentType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 100) {
                    $('#dlg_fixed').dialog('close');
                    if(result.extend.addFixedItemInfo_fixed === "成功") {
                        $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                    }else if(result.extend.addFixedItemInfo_fixed === "编号重复"){
                        $.messager.alert("提示", "编号已存在", "info");
                    }
                    //$('#grid').datagrid('clearSelections'); //清空选中的行
                    fixedItemGrid();
                } else {
                    $.messager.alert("提示", "添加失败，请重新操作！", "info");
                    //return;
                }
            }
        });
    }
}

//--------------------  自定义字段修改界面 ---------------------------------
//修改，判断是否符合条件（自定义字段修改）
function editGrid_defined() {
    var row = $('#grid').datagrid('getSelected');
    if (row === null || row.length < 1) {
        $.messager.alert("提示", "请先选择一条记录！", "info");
    } else if(row.length > 1){
        $.messager.alert("提示", "请只选择一条记录！", "info");
    }else if(row.length = 1) {
        itemNo_defined = row.ItemID;
        ShowEditGrid_defined(itemNo_defined);
    }
}

//显示自定义字段具体包含的科目字段（自定义字段修改）
function ShowEditGrid_defined(itemNo_defined) {
    $('#grid').datagrid('clearSelections');
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        height: function () {
            return document.body.clientHeight * 0.9
        },
        width: function () {
            return document.body.clientWidth * 0.9
        },
        title: "自定义项目修改",
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
        sortName: 'ItemID',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'ItemID',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            {field: 'ck', checkbox: true},   //选择
            {title: '项目编号', field: 'ItemID', width: 40},
            {title: '项目名称', field: 'ItemName', width: 40},
            {title: '项目类型', field: 'ItemType', width: 40},
        ]],
        toolbar: [{
            id: 'btnLast',
            text: '上一级',
            iconCls: 'icon-back',
            handler: function () {
                LastLevel_defined();//实现下一级
            }
        }, '-', {
            id: 'btnAdd',
            text: '添加',
            iconCls: 'icon-add',
            handler: function () {
                ShowAddDialog_defined();//实现添加记录的页面
            }
        }, '-', {
            id: 'btnDelete',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                deleteData_defined(itemNo_defined);//实现直接删除数据的方法
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_definedItemInfo(itemNo_defined);
}

//获取自定义字段的信息（自定义字段修改）
function Get_definedItemInfo(itemNo_defined){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ElseSet/getDefinedItemInfo/' + itemNo_defined ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_definedItemInfo = result.extend.definedItemInfo;
                if(data_definedItemInfo != null){
                    $('#grid').datagrid("loadData",data_definedItemInfo);
                }else{
                    $('#grid').datagrid("loadData",{ total: 0, rows: [] });
                }
            }
        }
    });
}

//上一级,返回自定义字段显示界面（自定义字段修改）
function LastLevel_defined() {
    definedItemGrid();
}

//具体字段添加（自定义字段修改）
function ShowAddDialog_defined() {
    $('#fm_defined').form('clear');//清空内容
    // var selectItemTypeValue = "科目编号";
    // itemNameSelect_defined(selectItemTypeValue);
    $('#dlg_defined').dialog('open').dialog('setTitle', '添加指标');//打开对话框
    document.getElementById("test_defined").value = "add";
}

//项目名称下拉菜单的数据获取（自定义字段修改）
function itemNameSelect_defined(selectItemTypeValue) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ElseSet/getItemNameSelect_defined/'  + selectItemTypeValue + '/' + itemNo_defined,
        contentType: 'application/json',
        async: false,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.itemNameSelect_defined;
                console.log(data1);
                $('#ItemName_defined').combobox("loadData", data1);
            }
        }
    });
}

//保存信息（自定义字段修改）
function saveData_defined() {
    var ItemID_defined = document.getElementById("ItemID_defined").value;
    var ItemName_defined = document.getElementById("ItemName_defined").value;

    var test = document.getElementById("test_defined").value;

    if (test == "add") {
        $.ajax({
            type: "POST",
            url: getRealPath() + '/ElseSet/addDefinedItemInfo_defined/' + itemNo_defined + '/' + ItemID_defined + '/' + selectItemTypeValue,
            contentType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 100) {
                    $('#dlg_defined').dialog('close');
                    if(result.extend.addDefinedItemInfo_defined === "成功") {
                        $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                    }else if(result.extend.addDefinedItemInfo_defined === "编号重复"){
                        $.messager.alert("提示", "编号已存在", "info");
                    }
                    $('#grid').datagrid('clearSelections'); //清空选中的行
                    ShowEditGrid_defined(itemNo_defined);
                } else {
                    $.messager.alert("提示", "添加失败，请重新操作！", "info");
                    //return;
                }
            }
        });
    }
}

//删除行（自定义字段修改）
function deleteData_defined(itemNo_defined) {
    var adminID = document.getElementById("AdminID").value = "0";
    var test = document.getElementById("test").value = "delete";
    var row = $('#grid').datagrid('getSelections');
    var num = 0;
    if (row === null || row.length < 1) {
        $.messager.alert("提示", "请选择要删除的行！", "info");
    } else {
        $.messager.confirm("删除确认", "您确认删除选定的记录吗？", function (deleteAction) {
            if (deleteAction) {
                for (var i = 0; i < row.length; i++) {
                    var ItemID = row[i].ItemID;
                    selectItemTypeValue = row[i].ItemType;
                    $.ajax({
                        type: "POST",
                        url: getRealPath() + '/ElseSet/deleteItemInfo_defined/' + ItemID + '/' + itemNo_defined + '/' + selectItemTypeValue,
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
                        ShowEditGrid_defined(itemNo_defined);
                        $.messager.alert("提示", "恭喜您，成功删除" + num + "条信息！", "info");
                    }
                    else {
                        $.messager.alert("提示", "删除失败，请重新操作！", "info");
                        ShowEditGrid_defined(itemNo_defined);
                        return;
                    }
                }
            }
        });
    }
}

//----------------------显示菜单栏界面---------------------------------------------
//界面，显示菜单栏（如：部门、产品）
function showMenuSelect() {
    $('#grid').datagrid('clearSelections');
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        title: "显示设置",
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
        sortName: 'MenuName',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'MenuName',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ck', checkbox: true },   //选择
            {
                title: '菜单栏名称',
                field: 'MenuName',
                width: 40
            },
            {
                title: '是否显示',
                field: 'IsShow',
                width: 40
            }
        ]],
        toolbar: [{
            id: 'btnLast',
            text: '上一级',
            iconCls: 'icon-back',
            handler: function () {
                LastLevel();//实现上一级
            }
        }, '-', {
            id: 'btnEdit',
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                editGrid_menuShow();//实际上是进入新的界面
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        },
    });
    Get_MenuInfo();
}

//获取菜单栏的信息（显示设置）
function Get_MenuInfo(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ElseSet/getMenuInfo/' ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_menuInfo = result.extend.menuInfo;
                if(data_menuInfo != null){
                    $('#grid').datagrid("loadData",data_menuInfo);
                }else{
                    $('#grid').datagrid("loadData",{ total: 0, rows: [] });
                }
            }
        }
    });
}

//修改，判断是否符合条件（显示设置）
function editGrid_menuShow() {
    var row = $('#grid').datagrid('getSelected');
    if (row === null || row.length < 1) {
        $.messager.alert("提示", "请先选择一条记录！", "info");
    } else if(row.length > 1){
        $.messager.alert("提示", "请只选择一条记录！", "info");
    }else if(row.length = 1) {
        var menuName = row.MenuName;
        ShowEditDialog_menuShow(menuName);
    }
}

//具体字段添加（显示设置）
function ShowEditDialog_menuShow(menuName) {
    $('#fm_menuShow').form('clear');//清空内容
    $('#MenuName').val(menuName);
    $('#MenuName').attr("disabled", "disabled");//图表编号是主键，设为不可修改
    $('#dlg_menuShow').dialog('open').dialog('setTitle', '修改');//打开对话框
    $('#IsShow').combobox('loadData',datacom3);//清空内容
    document.getElementById("test_MenuName").value = "edit";
}

//保存信息（显示设置）
function saveData_menuShow() {
    var IsShow = $('#IsShow').combobox('getText');
    var MenuName = document.getElementById("MenuName").value;
    var test = document.getElementById("test_MenuName").value;
    if (test == "edit") {
        $.ajax({
            type: "POST",
            url: getRealPath() + '/ElseSet/addMenuInfo/' + MenuName + '/' + IsShow,
            contentType: 'json',
            async: false,
            success: function (result) {
                if (result.code == 100) {
                    $('#dlg_menuShow').dialog('close');
                    if(result.extend.addMenuInfo === "成功") {
                        $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                    }else if(result.extend.addMenuInfo === "编号重复"){
                        $.messager.alert("提示", "编号已存在", "info");
                    }
                    //$('#grid').datagrid('clearSelections'); //清空选中的行
                    showMenuSelect();
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