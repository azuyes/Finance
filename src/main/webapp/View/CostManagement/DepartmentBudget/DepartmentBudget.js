var ItemID = "null";
var ItemIdLast = "null";//设置上一个选择的科目编号，用来为当查不到数据返回时使用
var Level = 1;
var maxLevel = 5;//设置科目级数的最大值，
var departmentSpID = "null";
var departmentLevel = 1;
var Action = "init";
var editRow;

$(document).ready(function () {
    getMaxLevel();
    //初始化表格
    InitGrid(Level, ItemID);
});

//获得最大的核算级数
function getMaxLevel() {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/DepartmentBudget/DepartmentBudget_getMaxLevel/'  ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                maxLevel = result.extend.DepartmentBudget_getMaxLevel;
            }
        }
    });
}

//----------------------------进入到科目选择编辑界面-----------------------------------------
function InitGrid(Level, ItemID) {
    if(Action === "nullInit"){//当查询的结果为空时，返回到该界面
        $.messager.alert('提醒', '未获得有效数据！');
        Action = "init";
    }else if(Action === "nullLevel"){//当科目的下一级为空的时候，返回该科目所在的界面
        $.messager.alert("提示", "没有下一级了", "info");
        Action = "init";
    }
    $('#grid').datagrid({
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        // onClickCell: onClickCell,
        // onAfterEdit:onAfterEdit,
        checkOnSelect: false,
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
            { title: '项目级次', field: 'ItemLevel', width: 40 },
            //{ title: '年度预算', field: 'AnnualBudget', width: 40, editor: 'numberbox'}
        ]],
        toolbar: [{
            id: 'btnLast',
            text: '上一级',
            iconCls: 'icon-back',
            handler: function () {
                LastLevel();//实现上一级
            }
        }, '-', {
            id: 'btnNext',
            text: '下一级',
            iconCls: 'icon-next',
            handler: function () {
                NextLevel();//实现下一级
            }
        }, '-', {
            text: "开始录入",
            iconCls: "icon-edit",
            handler: function () {
                //endEdit();
                openEditDatagrid();
            }
        }],
        onClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    });
    Get_annualBudgetInfo(Level, ItemID);
    if (Level === 1) {
        $('#btnLast').linkbutton('disable');//1级的时候没有上一级，将上一级按钮设为不可用
    } else {
        $('#btnLast').linkbutton('enable');
    }
    if (Level === maxLevel) {
        $('#btnNext').linkbutton('disable');//最高级的时候没有下一级，将下一级按钮设为不可用
    } else {
        $('#btnNext').linkbutton('enable');
    }
}

//获取图表项目名称
function Get_annualBudgetInfo(Level, ItemID) {
    var Level_string = Level.toString();
    $.ajax({
        type: "POST",
        url: getRealPath() + '/DepartmentBudget/DepartmentBudget_getAnnualBudget/' + Level_string + '/' + ItemID ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_graphAll = result.extend.DepartmentBudget_getAnnualBudgetInfo;
                console.log(data_graphAll);
                $('#grid').datagrid("loadData",data_graphAll);
            }else if(result.code == 101) {
                Action = "nullLevel";
                Level = parseInt(Level) - 1;
                InitGrid(Level, ItemIdLast);
            }
        }
    });
}

//上一级按钮点击事件
function LastLevel() {
    $('#grid').datagrid('clearSelections');
    //Action = "last";
    if (parseInt(Level) -1>0) {
        Level = parseInt(Level) - 1;
        InitGrid(Level, ItemID);
    } else {
        $.messager.alert("提示", "没有上一级了", "info");
    }
}

//下一级按钮点击事件
function NextLevel() {
    var row = $('#grid').datagrid('getSelected');
    $('#grid').datagrid('loadData', { total: 0, rows: [] });//可以将残留的行数清空
    endEdit();
    if (row) {
        ItemIdLast = ItemID;//这里用来记录上一个所选科目的编号
        ItemID = row.ItemID;
        //Action="next";
        Level = row.ItemLevel;
        if (parseInt(Level) < maxLevel) {
            Level = parseInt(Level) + 1;
            $('#grid').datagrid('clearSelections');
            //InitGrid(Level, ItemID);
        } else {
            $.messager.alert("提示", "没有下一级了", "info");
        }
    } else {
        $.messager.alert("提示", "请先选择一条记录！", "info");
    }
    InitGrid(Level, ItemID);
}

//----------------------选择部门界面--------------------------------------
function openEditDatagrid() {
    var row = $('#grid').datagrid('getSelected');
    if(row === null){
        $.messager.alert("提示", "请先选择一条记录！", "info");
    }else {
        Level = row.ItemLevel;
        var selectedItemID = row.ItemID;
        $('#grid').datagrid({
            height: function () {
                return document.body.clientHeight * 0.9
            },
            width: function () {
                return document.body.clientWidth * 0.9
            },
            onClickCell: onClickCell,
            onAfterEdit: onAfterEdit,
            checkOnSelect: false,
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
                //{field: 'ck', checkbox: true},   //选择
                {title: '项目编号', field: 'ItemID', width: 40},
                {title: '项目名称', field: 'ItemName', width: 40},
                {title: '核算项目编号1', field: 'spNo1', width: 40},
                {title: '核算项目名称1', field: 'spName1', width: 40},
                {title: '核算项目编号2', field: 'spNo2', width: 40},
                {title: '核算项目名称2', field: 'spName2', width: 40},
                {title: '年度预算', field: 'AnnualBudget', width: 40, editor: 'numberbox'}
            ]],
            toolbar: [{
                id: 'btnLast',
                text: '返回',
                iconCls: 'icon-back',
                handler: function () {
                    backInitGrid();//实现上一级
                }
            }],
            onClickRow: function (rowIndex, rowData) {//获取选中的行
                $('#grid').datagrid('uncheckAll');
                $('#grid').datagrid('checkRow', rowIndex);
            }
        });
        get_itemSpBudgetInfo(selectedItemID);
    }
    // if (Level === 1) {
    //     $('#btnLast').linkbutton('disable');//1级的时候没有上一级，将上一级按钮设为不可用
    // } else {
    //     $('#btnLast').linkbutton('enable');
    // }
    // if (Level === levelMax) {
    //     $('#btnNext').linkbutton('disable');//最高级的时候没有下一级，将下一级按钮设为不可用
    // } else {
    //     $('#btnNext').linkbutton('enable');
    // }
}

//获取图表项目名称
function get_itemSpBudgetInfo(selectedItemID) {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/DepartmentBudget/get_itemSpBudgetInfo/' +  selectedItemID ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                var data_graphAll = result.extend.DepartmentBudget_getItemSpBudgetInfo;
                console.log(data_graphAll);
                $('#grid').datagrid("loadData",data_graphAll);
            }else if(result.code == 101){
                Action = "nullInit";
                InitGrid(Level, ItemID);
            }
        }
    });
}

var editIndex = undefined;
function endEditing() {//该方法用于关闭上一个焦点的editing状态
    if (editIndex === undefined) {
        return true
    }
    if ($('#grid').datagrid('validateRow', editIndex)) {
        $('#grid').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

//点击单元格事件：
function onClickCell(index,field,value) {
    if (endEditing()) {
        if(field === "AnnualBudget"){
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
            $(ed.target).focus();
        }
        editIndex = index;
    }
    $('#grid').datagrid('onClickRow')
}

//单元格失去焦点执行的方法
function onAfterEdit(index, row, changes) {
    var updated = $('#grid').datagrid('getChanges', 'updated');
    if (updated.length < 1) {
        editRow = undefined;
        $('#grid').datagrid('unselectAll');
        return;
    } else {
        // 传值
        submitForm(index, row, changes);
    }
}

//提交数据
function submitForm(index, row, changes) {
//	alert( row.resultId+"--"+changes.finalResult)daliyResultRate;
    var annualBudget = row.AnnualBudget;//成绩id
    if (annualBudget === "") {
        $.messager.alert('提醒', '没有录入该学生平时成绩！');
        $("#dg").datagrid('reload');
        return;
    }
    var itemId = row.ItemID;//平时成绩比例
    var spNo1 = row.spNo1;
    var spNo2 = row.spNo2;
    // var r = /^-?[1-9]/;//判断输入的是正整数
    // if (!r.test(finalRusult)) {
    //     $.messager.alert('提醒', '请输入正整数！');
    //     return;
    // }
    var num = 0;
    $.ajax({
        type: "POST",
        url: getRealPath()+'/DepartmentBudget/saveSpItemBudgetInfo/' + annualBudget + '/' + itemId + '/' + spNo1 + '/' + spNo2  ,
        contentType: 'json',
        success: function(result) {
            if (result.code == 100) {
                num++;
                if (num >= 1) {
                    $.messager.alert('提醒', '修改成功！');
                }
                else {
                    $.messager.alert("提示", "修改失败，请重新操作！", "info");
                }
            }
        }
    });
}

function endEdit(){
    var rows = $('#grid').datagrid('getRows');
    for ( var i = 0; i < rows.length; i++) {
        $('#grid').datagrid('endEdit', i);
    }
}

//上一级按钮点击事件
function backInitGrid() {
    $('#grid').datagrid('clearSelections');
    //Action = "last";
    if (parseInt(Level)>0) {
        Level = parseInt(Level);
        $('#grid').datagrid('loadData', { total: 0, rows: [] });
        InitGrid(Level, ItemID);
    } else {
        $.messager.alert("提示", "没有上一级了", "info");
    }
}

function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}