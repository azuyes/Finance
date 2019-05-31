
//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度

var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称

var balanceCorrect=0;
var debitMoneySupCorrect=0;
var creditMoneySupCorrect=0;
var supMoneyCorrect=0;
var supQtyCorrect=0;
var debitQtySupCorrect=0;
var creditQtySupCorrect=0;
var leftQtyCorrect=0;

var supMoney = 0;
var debitMoneySup =0 ;
var creditMoneySup =0 ;
var balance = 0;
var supQty = 0;
var debitQtySup = 0;
var creditQtySup = 0;
var leftQty = 0;
var tableClassify ;//initSlTable数量表initJeTable金额表

var editIndex = undefined;

var spLevel  = 1;

var supAcc2 ;
var finLevel;
var accType;

var spNo;

var editIndex = undefined;

var showupperItemName = "";

var flagSave = 0; //定义一个标志，在spLevel为1的是点击保存为1，然后才能点击关闭窗口

var begin_month_ = "";

$(function () {

    dealConfigs();

    $.extend($.fn.datagrid.methods, {    //重写datagrid方法，使其支持单元格操作
        editCell: function(jq,param){
            return jq.each(function(){
                var opts = $(this).datagrid('options');
                var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor1 = col.editor;
                    if (fields[i] != param.field){
                        col.editor = null;
                    }
                }
                $(this).datagrid('beginEdit', param.index);
                for(var i=0; i<fields.length; i++){
                    var col = $(this).datagrid('getColumnOption', fields[i]);
                    col.editor = col.editor1;
                }
            });
        }
    });

    function endEditing(){  //判断是否结束编辑金额表
        if (editIndex == undefined){return true}

        if ($('#specialTableJe1').datagrid('validateRow', editIndex)){  //验证通过后结束编辑

            $('#specialTableJe1').datagrid('endEdit', editIndex);
            editIndex = undefined;

            return true;
        } else {
            return false;
        }
    }

    function onClickJe1Cell(index, field,value){   //点击金额表单元格事件
        $('#close_button1').linkbutton('disable');
        var rows = $('#specialTableJe1').datagrid('getRows');
        var row = rows[index];
        var finLevel = row.finLevel;

        if(finLevel == '1'){
            if (endEditing()){

                reloadFooterJe1();  //重新加载计算合计

                $('#specialTableJe1').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
                editIndex = index;
            }
        }

    }

    function endSlEditing(){    //判断是否结束编辑数量表
        if (editIndex == undefined){return true}

        if ($('#specialTableSl1').datagrid('validateRow', editIndex)){
            $('#specialTableSl1').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    function onClickSl1Cell(index, field,value){ //点击数量表单元格事件
        $('#close_button1').linkbutton('disable');
        var rows = $('#specialTableSl1').datagrid('getRows');
        var row = rows[index];
        var finLevel = row.finLevel;

        if(finLevel == '1'){
            if (endSlEditing()){

                reloadFooterSl1();  //重新加载计算合计

                $('#specialTableSl1').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
                editIndex = index;
            }
        }
    }

    function endEditingJe2(){  //判断是否结束编辑金额表
        if (editIndex == undefined){return true}

        if ($('#specialTableJe2').datagrid('validateRow', editIndex)){  //验证通过后结束编辑

            $('#specialTableJe2').datagrid('endEdit', editIndex);
            editIndex = undefined;

            return true;
        } else {
            return false;
        }
    }

    function onClickJe2Cell(index, field,value){   //点击金额表单元格事件
        var rows = $('#specialTableJe2').datagrid('getRows');
        var row = rows[index];
        var finLevel = row.finLevel;

        if (endEditingJe2()){

            reloadFooterJe2();  //重新加载计算合计

            $('#specialTableJe2').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }

    }

    function endEditingSl2(){    //判断是否结束编辑数量表
        if (editIndex == undefined){return true}

        if ($('#specialTableSl2').datagrid('validateRow', editIndex)){
            $('#specialTableSl2').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    function onClickSl2Cell(index, field,value){ //点击数量表单元格事件
        var rows = $('#specialTableSl2').datagrid('getRows');
        var row = rows[index];

        if (endEditingSl2()){

            reloadFooterSl2();  //重新加载计算合计

            $('#specialTableSl2').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }
    }

    dealSubStru();//保存科目级数

    showCaption(upper_No_,level_.toString());

    obj = {
        //上级点击事件
        uperLevel_click: function () {
            if (level_ == 1) {
                $.messager.alert('提示', '1级科目没有上级', 'info');
                return;
            }
            level_--;
            upper_No_ = getCatNo(upper_No_, level_ - 1);

            if(level_ == 1){
                showupperItemName = "";
            }else{
                showupperItemName = showupperItemName.split("\\",level_-1) + "\\";
            }

            $("#upperItemName_label").text(showupperItemName);
            $("#upperItemNo_label").text(upper_No_);
            $("#levelNow_label").text(level_);

            showCaption(upper_No_, level_);
        },
        //下级点击事件
        nextLevel_click: function () {
            var row = $('#main_table').datagrid('getSelected');
            if (row == null) {
                $.messager.alert('提示', '请选择科目', 'info');
                return;
            }
            if (row.lskmzd.finLevel == 1){
                $.messager.alert('提示', '该科目没有下级', 'info');
                return;
            }
            level_++;
            upper_No_ = getCatNo(row.lskmzd.itemNo, level_ - 1); //获取选中的分类编号

            showupperItemName = showupperItemName+row.itemName+"\\";
            $("#upperItemName_label").text(showupperItemName);
            $("#upperItemNo_label").text(upper_No_);
            $("#levelNow_label").text(level_);

            showCaption(upper_No_, level_);
        },

    }

    $('#main_table').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        //url: 'content.json',
        rownumbers: true,
      //  fitColumns:true,
        singleSelect:true,
        striped: true,
        columns: [[
			{
			    field: 'itemNo',
			    title: '科目',
			    align: 'center',
			   // width: '10%',
                formatter: function (value,row,index) {
                    value = row.lskmzd.itemNo;
                    var length = getNumLength(level_);
                    var no = value.substring(0,length);
                    return no;
                }
			},
			{
			    field: 'itemName',
			    title: '科目名称',
			    align: 'center',
			    //width: '15%',
                formatter: function (value,row,index) {
                    value = row.lskmzd.itemName;
                    return value;
                }
			},
			{
			    field: 'finLevel',
			    title: '明细否',
			    align: 'center',
			    //width: '8%',
                formatter: function (value,row,index) {
                    value = row.lskmzd.finLevel;
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
			},
            {
                field: 'spAccName1',
                title: '核算类别1',
                align: 'center',
                //width: '12%',
            },
            {
                field: 'spAccName2',
                title: '核算类别2',
                align: 'center',
                width: '12%',
            },
            {
                field: 'supMoney',
                title: '年初余额',
                halign:'center',
                align: 'right',
                //width: '10%',
                formatter: function (value,row,index) {

                    value = row.lskmzd.supMoney;
                    return thousandFormatter(value);
                }
            },
			{
			    field: 'debitMoneySup',
			    title: '本年借方',
                halign:'center',
                align: 'right',
			   // width: '10%',
                formatter: function (value,row,index) {
                    value = row.lskmzd.debitMoneySup;
                    return thousandFormatter(value);
                }
			},
			{
			    field: 'creditMoneySup',
			    title: '本年贷方',
                halign:'center',
                align: 'right',
			    //width: '10%',
                formatter: function (value,row,index) {
                    value = row.lskmzd.creditMoneySup;
                    return thousandFormatter(value);
                }
			},
            {
                field: 'balance',
                title: '当前余额',
                halign:'center',
                align: 'right',
                //width: '10%',
                formatter: function (value,row,index) {

                    if(row.lskmzd) value = row['lskmzd'][getBeginMonth("J")];

                    return thousandFormatter(value);
                }
            },

        ]],
        toolbar: '#tb',

        //下级点击事件
        nextLevel_click: function () {
            var row = $('#main_table').datagrid('getSelected');
            if (row == null) {
                $.messager.alert('提示', '请选择科目', 'info');
                return;
            }
            if (row.lskmzd.finLevel == 1){
                $.messager.alert('提示', '该科目没有下级', 'info');
                return;
            }
            level_++;
            $('#level').val(level_);
            upper_No_ = getCatNo(row.lskmzd.itemNo, level_ - 1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },

        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件

            if (rowData.lskmzd.finLevel == 1){
                $.messager.alert('提示', '该科目没有下级', 'info');
                return;
            }
            level_++;
            upper_No_ = getCatNo(rowData.lskmzd.itemNo, level_ - 1); //获取选中的分类编号

            showupperItemName = showupperItemName+rowData.lskmzd.itemName+"\\";
            $("#upperItemName_label").text(showupperItemName);
            $("#upperItemNo_label").text(upper_No_);
            $("#levelNow_label").text(level_);

            showCaption(upper_No_, level_);


        }
    });

    $('#specialWinJe1').window({
        width: 800,
        height: 500,
        title: '专项核算初始',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable:false,
        closable:false,
    });

    $('#specialWinSl1').window({
        width: 800,
        height: 500,
        title: '专项核算初始',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable:false,
    });

    $('#specialWinJe2').window({
        width: 800,
        height: 500,
        title: '专项核算初始',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable:false,
    });

    $('#specialWinSl2').window({
        width: 800,
        height: 500,
        title: '专项核算初始',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable:false,
    });

    //主界面数据表格
    $('#initJeTable').datagrid({
        //height:'90%',
        width: '100%',
        showFooter: true,

        iconCls: 'icon-edit',
        singleSelect: true,
        fitColumns: true,
        striped: true,
        title: '往来初始化',
       // onClickCell: onClickCell,   //单击单元格事件
        columns: [[
            {
                field: 'companyNo',
                title: '单位编号',
                align: 'center',
                width: '9%',
            },
            {
                field: 'companyName',
                title: '单位名称',
                align: 'center',
                width: '10%',
            },
            {
                field: 'itemNo',
                title: '科目编号',
                align: 'center',
                hidden: true
            },
            {
                field: 'supMoney',
                title: '年初金额',
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },
            {
                field: 'debitMoneySup',
                title: '本年借方金额',
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'creditMoneySup',
                title: '本年贷方金额',
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'balance',
                title: '当前金额',
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'FinLevel6',
                title: '年初数量',
                align: 'center',
                width: '10%'
            },
            {
                field: 'FinLevel7',
                title: '本年借方数量',
                align: 'center',
                width: '10%'
            },
            {
                field: 'FinLevel8',
                title: '本年贷方数量',
                align: 'center',
                width: '10%'
            },
            {
                field: 'FinLevel9',
                title: '当前数量',
                align: 'center',
                width: '10%'
            },
        ]],
        toolbar: '#tbJe',
    });



    //初始化加载，解决页脚行不加载问题。
    var dataJe ='{"total":0,"rows":[],"footer":[{"spName1":"合计","supMoney":0,"debitMoneySup":0,"creditMoneySup":0,"balance":0}]}';
    var dataSl ='{"total":0,"rows":[],"footer":[{"spName1":"合计","supMoney":0,"debitMoneySup":0,"creditMoneySup":0,"balance":0,"supQty":0,"debitQtySup":0,"creditQtySup":0,"leftNo":0}]}';
    var jsonJe  = JSON.parse(dataJe);
    var jsonSl = JSON.parse(dataSl);
    //主界面数据表格
    $('#specialTableJe1').datagrid({
        width: '100%',
        // height: '100%',
        iconCls: 'icon-chart',
        rownumbers: true,
        data:jsonJe,
        singleSelect:true,
        showFooter: true,
        fitColumns:true,
        striped: true,
        onClickCell: onClickJe1Cell,
        columns: [[
            {
                field: 'spNo1',
                title: '编号',
                align: 'center',
                //width: '12%'
            },
            {
                field: 'spName1',
                title: '核算对象',
                align: 'center',
                //width: '12.5%'
            },
            {
                field: 'finLevel',
                title: '明细否',
                align: 'center',
                //width: '8%',
                formatter: function (value,row,index) {
                    value = row.finLevel;
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'supMoney',
                title: '年初金额',
               // width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'debitMoneySup',
                title: '本年借方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'creditMoneySup',
                title: '本年贷方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'balance',
                title: '当前金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'FinLevel6',
                title: '年初数量',
                align: 'center',
                ///width: '10%'
            },
            {
                field: 'FinLevel7',
                title: '本年借方数量',
                align: 'center',
                //width: '10%'
            },
            {
                field: 'FinLevel8',
                title: '本年贷方数量',
                align: 'center',
                //width: '10%'
            },
            {
                field: 'FinLevel9',
                title: '当前数量',
                align: 'center',
                //width: '10%'
            },

        ]],
        toolbar: '#tbInit',

    });

    $('#specialTableSl1').datagrid({
        width: '100%',
        // height: '100%',
        iconCls: 'icon-chart',
        rownumbers: true,
        showFooter: true,
        fitColumns:true,
        data:jsonSl,
        singleSelect:true,
        striped: true,
        onClickCell: onClickSl1Cell,
        columns: [[
            {
                field: 'spNo1',
                title: '编号',
                align: 'center',
                //width: '12%'
            },
            {
                field: 'spName1',
                title: '核算对象',
                align: 'center',
                //width: '12.5%'
            },
            {
                field: 'finLevel',
                title: '明细否',
                align: 'center',
                //width: '8%',
                formatter: function (value,row,index) {
                    value = row.finLevel;
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'supMoney',
                title: '年初金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'debitMoneySup',
                title: '本年借方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'creditMoneySup',
                title: '本年贷方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'balance',
                title: '当前金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'supQty',
                title: '年初数量',
                halign:'center',
                align: 'right',
               // width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },
            {
                field: 'cebitQtySup',
                title: '本年借方数量',
                halign:'center',
                align: 'right',
                //width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },
            {
                field: 'creditQtySup',
                title: '本年贷方数量',
                halign:'center',
                align: 'right',
                //width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },
            {
                field: 'leftQty',
                title: '当前数量',
                halign:'center',
                align: 'right',
                //width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },

        ]],
        toolbar: '#tbInitSl1'
    });

    $('#specialTableJe2').datagrid({
        width: '100%',
        // height: '100%',
        iconCls: 'icon-chart',
        data:jsonJe,
        rownumbers: true,
        showFooter: true,
        fitColumns:true,
        onClickCell: onClickJe2Cell,
        striped: true,
        columns: [[
            {
                field: 'spNo1',
                title: '核算对象1编号',
                align: 'center',
                //width: '12%'
            },
            {
                field: 'spName1',
                title: '核算对象1',
                align: 'center',
                //width: '12.5%'
            },
            {
                field: 'spNo2',
                title: '核算对象2编号',
                align: 'center',
                //width: '12%'
            },
            {
                field: 'spName2',
                title: '核算对象',
                align: 'center',
                //width: '12.5%'
            },
            {
                field: 'finLevel',
                title: '明细否',
                align: 'center',
                //width: '8%',
                formatter: function (value,row,index) {
                    return "是";
                }
            },
            {
                field: 'supMoney',
                title: '年初金额',
                // width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'DebitMoneySup',
                title: '本年借方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'CreditMoneySup',
                title: '本年贷方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'balance',
                title: '当前金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'FinLevel6',
                title: '年初数量',
                align: 'center',
                ///width: '10%'
            },
            {
                field: 'FinLevel7',
                title: '本年借方数量',
                align: 'center',
                //width: '10%'
            },
            {
                field: 'FinLevel8',
                title: '本年贷方数量',
                align: 'center',
                //width: '10%'
            },
            {
                field: 'FinLevel9',
                title: '当前数量',
                align: 'center',
                //width: '10%'
            },

        ]],
        toolbar: '#tbInit1'
    });

    $('#specialTableSl2').datagrid({
        width: '100%',
        // height: '100%',
        iconCls: 'icon-chart',
        data:jsonSl,
        rownumbers: true,
        fitColumns:true,
        showFooter: true,
        onClickCell: onClickSl2Cell,
        striped: true,
        columns: [[
            {
                field: 'spNo1',
                title: '编号',
                align: 'center',
                //width: '12%'
            },
            {
                field: 'spName1',
                title: '核算对象',
                align: 'center',
                //width: '12.5%'
            },
            {
                field: 'spNo2',
                title: '编号',
                align: 'center',
                //width: '12%'
            },
            {
                field: 'spName2',
                title: '核算对象',
                align: 'center',
                //width: '12.5%'
            },
            {
                field: 'finLevel',
                title: '明细否',
                align: 'center',
                //width: '8%',
                formatter: function (value,row,index) {
                    return "是";
                }
            },
            {
                field: 'supMoney',
                title: '年初金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'debitMoneySup',
                title: '本年借方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'creditMoneySup',
                title: '本年贷方金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'balance',
                title: '当前金额',
                //width: '12%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                },
            },
            {
                field: 'supQty',
                title: '年初数量',
                // width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',

            },
            {
                field: 'debitQtySup',
                title: '本年借方数量',
                //width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',

            },
            {
                field: 'creditQtySup',
                title: '本年贷方数量',
                //width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
            },
            {
                field: 'leftQty',
                title: '当前数量',
                //width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                halign:'center',
                align: 'right',
            },


        ]],
        toolbar: '#tbInit2'
    });
})

function close1(){
    $("#specialWinJe1").window('close');
}
function close2(){
    $("#specialWinSl1").window('close');
}

//点击保存时
function save2() {
    var rows; //获取进行修改的行数据

    if (tableClassify == 'specialTableJe2') {

        var markJe2 = 'correct';

        if ($('#specialTableJe2').datagrid('validateRow', editIndex)) {
            $('#specialTableJe2').datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
        reloadFooterJe2();
        rows = $('#specialTableJe2').datagrid('getChanges');

        for (var i = 0; i < rows.length; i++) {
            if(parseFloat(rows[i].supMoney)+parseFloat(rows[i].debitMoneySup)-parseFloat(rows[i].creditMoneySup)!=parseFloat(rows[i].balance)){
                $.messager.alert('提示', rows[i].spName1+','+rows[i].spName2+'的当前余额<>年初余额+本年借方-本年贷方，请修改！', 'info');
                markJe2 = 'error';
            }
        }

        if(markJe1 == 'correct') {
            if (supMoneyCorrect != supMoney) {
                $.messager.alert('提示', '各核算对象的年初余额<>其对应科目的年初余额，请修改！', 'warning');
                markJe2 = 'error';
            }else if (debitMoneySupCorrect != debitMoneySup) {
                $.messager.alert('提示', '各核算对象的本年借方合计<>其对应科目的借方合计，请修改！', 'warning');
                markJe2 = 'error';
            }else if (creditMoneySupCorrect != creditMoneySup) {
                $.messager.alert('提示', '各核算对象的本年贷方合计<>其对应科目的本年贷方合计，请修改！', 'warning');
                markJe2 = 'error';
            }else if (balanceCorrect != balance) {
                $.messager.alert('提示', '各核算对象的当前金额<>其对应科目的当前金额，请修改！', 'warning');
                markJe2 = 'error';
            }
            if(markJe2 == 'error'){
                return ;
            }
        }

        if(markJe2 == 'correct'){
            save2Submit(rows);
        }


    } else {
        var markSl2 = 'correct';
        if ($('#specialTableSl2').datagrid('validateRow', editIndex)) {
            $('#specialTableSl2').datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
        reloadFooterSl2();
        rows = $('#specialTableSl2').datagrid('getChanges');

        for (var i = 0; i < rows.length; i++) {
            if(parseFloat(rows[i].supMoney)+parseFloat(rows[i].debitMoneySup)-parseFloat(rows[i].creditMoneySup)!=parseFloat(rows[i].balance)){
                $.messager.alert('提示', rows[i].spName1+','+rows[i].spName2+'的当前余额<>年初余额+本年借方-本年贷方，请修改！', 'info');
                markSl2 = 'error';
            }else if(parseFloat(rows[i].supQty)+parseFloat(rows[i].debitQtySup)-parseFloat(rows[i].creditQtySup)!=parseFloat(rows[i].leftQty)){
                $.messager.alert('提示', rows[i].spName1+','+rows[i].spName2+'的当前数量<>年初数量+本年借方数量-本年贷方数量，请修改！', 'info');
                markSl2 = 'error';
            }
        }

        if (markSl2 == 'correct') {
            if (supMoneyCorrect != supMoney) {
                $.messager.alert('提示', '各核算对象的年初余额<>其对应科目的年初余额，请修改！', 'warning');
                markSl2 = 'error';
            }else if (debitMoneySupCorrect != debitMoneySup) {
                $.messager.alert('提示', '各核算对象的本年借方合计<>其对应科目的借方合计，请修改！', 'warning');
                markSl2 = 'error';
            }else if (creditMoneySupCorrect != creditMoneySup) {
                $.messager.alert('提示', '各核算对象的本年贷方合计<>其对应科目的本年贷方合计，请修改！', 'warning');
                markSl2 = 'error';
            }else if (balanceCorrect != balance) {
                $.messager.alert('提示', '各核算对象的当前金额<>其对应科目的当前金额，请修改！', 'warning');
                markSl2 = 'error';
            }else if (supQtyCorrect != supQty) {
                $.messager.alert('提示', '各核算对象的年初数量<>其对应科目的年初数量，请修改！', 'warning');
                markSl2 = 'error';
            }else if (debitQtySupCorrect != debitQtySup) {
                $.messager.alert('提示', '各核算对象的借方金额<>其对应科目的借方金额，请修改！', 'warning');
                markSl2 = 'error';
            }else if (creditQtySupCorrect != creditQtySup) {
                $.messager.alert('提示', '各核算对象的贷方数量<>其对应科目的贷方数量，请修改！', 'warning');
                markSl2 = 'error';
            }else if (leftQtyCorrect != leftQty) {
                $.messager.alert('提示', '各核算对象的当前数量<>其对应科目的当前数量，请修改！', 'warning');
                markSl2 = 'error';
            }
            if(markSl2 == 'error'){
                return ;
            }
        }
        if(markSl2 == 'correct'){
            save2Submit(rows);
        }

    }
}

function save2Submit(rows){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountInitialization/updateSpCount2/' + itemNo,
        data: JSON.stringify(rows),
        contentType: 'application/json;charset=utf-8',
        success: function (result) {
            if (result.code == 100) {
                $.messager.alert('提示', '恭喜你，存盘成功！', 'info');
            } else {
                $.messager.alert('提示', '未知错误，请重试！', 'warning');
            }
        }
    });
}

//点击保存时
function save1() {
    var rows; //获取进行修改的行数据

    if (tableClassify == 'specialTableJe1') {

        var markJe1 = 'correct';

        if ($('#specialTableJe1').datagrid('validateRow', editIndex)) {
            $('#specialTableJe1').datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
        reloadFooterJe1();
        rows = $('#specialTableJe1').datagrid('getChanges');

        for (var i = 0; i < rows.length; i++) {
            if(parseFloat(rows[i].supMoney)+parseFloat(rows[i].debitMoneySup)-parseFloat(rows[i].creditMoneySup)!=parseFloat(rows[i].balance)){
                $.messager.alert('提示', rows[i].spName1+'的当前余额<>年初余额+本年借方-本年贷方，请修改！', 'info');
                markJe1 = 'error';
            }
        }
        if (spLevel == 1) {
            if(markJe1 == 'correct') {
                if (supMoneyCorrect != supMoney) {
                    $.messager.alert('提示', '各核算对象的年初余额<>其对应科目的年初余额，请修改！', 'warning');
                    markJe1 = 'error';
                }
                if (debitMoneySupCorrect != debitMoneySup) {
                    $.messager.alert('提示', '各核算对象的本年借方合计<>其对应科目的借方合计，请修改！', 'warning');
                    markJe1 = 'error';
                }
                if (creditMoneySupCorrect != creditMoneySup) {
                    $.messager.alert('提示', '各核算对象的本年贷方合计<>其对应科目的本年贷方合计，请修改！', 'warning');
                    markJe1 = 'error';
                }
                if (balanceCorrect != balance) {
                    $.messager.alert('提示', '各核算对象的当前金额<>其对应科目的当前金额，请修改！', 'warning');
                    markJe1 = 'error';
                }
                if(markJe1 == 'error'){
                    return ;
                }

            }
            if(markJe1 == 'correct') {
                $('#close_button1').linkbutton('enable');

            }
        }

        if(markJe1 == 'correct'){
            save1Submit(rows);
        }


    } else {
        var markSl1 = 'correct';
        if ($('#specialTableSl1').datagrid('validateRow', editIndex)) {
            $('#specialTableSl1').datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
        reloadFooterSl1();
        rows = $('#specialTableSl1').datagrid('getChanges');

        for (var i = 0; i < rows.length; i++) {
            if(parseFloat(rows[i].supMoney)+parseFloat(rows[i].debitMoneySup)-parseFloat(rows[i].creditMoneySup)!=parseFloat(rows[i].balance)){
                $.messager.alert('提示', rows[i].spName1+'的当前余额<>年初余额+本年借方-本年贷方，请修改！', 'info');
                markSl1 = 'error';
            }
            if(parseFloat(rows[i].supQty)+parseFloat(rows[i].debitQtySup)-parseFloat(rows[i].creditQtySup)!=parseFloat(rows[i].leftQty)){
                console.log(parseFloat(rows[i].supMoney));
                $.messager.alert('提示', rows[i].spName1+'的当前数量<>年初数量+本年借方数量-本年贷方数量，请修改！', 'info');
                markSl1 = 'error';
            }
        }

        if (spLevel == 1) {
            if (markSl1 == 'correct') {
                if (supMoneyCorrect != supMoney) {
                    $.messager.alert('提示', '各单位的年初余额<>其对应科目的年初余额，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (debitMoneySupCorrect != debitMoneySup) {
                    $.messager.alert('提示', '各单位的本年借方合计<>其对应科目的借方合计，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (creditMoneySupCorrect != creditMoneySup) {
                    $.messager.alert('提示', '各单位的本年贷方合计<>其对应科目的本年贷方合计，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (balanceCorrect != balance) {
                    $.messager.alert('提示', '各单位的当前金额<>其对应科目的当前金额，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (supQtyCorrect != supQty) {
                    $.messager.alert('提示', '各单位的年初数量<>其对应科目的年初数量，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (debitQtySupCorrect != debitQtySup) {
                    $.messager.alert('提示', '各单位的借方金额<>其对应科目的借方金额，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (creditQtySupCorrect != creditQtySup) {
                    $.messager.alert('提示', '各单位的贷方数量<>其对应科目的贷方数量，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if (leftQtyCorrect != leftQty) {
                    $.messager.alert('提示', '各单位的当前数量<>其对应科目的当前数量，请修改！', 'warning');
                    markSl1 = 'error';
                }
                if(markSl1 == 'error'){
                    return ;
                }
            }
            if(markSl1 == 'correct') {
                $('#close_button2').linkbutton('enable');

            }
        }
        if(markSl1 == 'correct'){
            save1Submit(rows);
        }

    }
}

function save1Submit(rows){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountInitialization/updateSpCount1/' + itemNo,
        data: JSON.stringify(rows),
        contentType: 'application/json;charset=utf-8',
        success: function (result) {
            if (result.code == 100) {
                $.messager.alert('提示', '恭喜你，存盘成功！', 'info');
            } else {
                $.messager.alert('提示', '未知错误，请重试！', 'warning');
            }
        }
    });
}

function initialization_click(){

    spLevel = 1;

    var row = $('#main_table').datagrid('getSelected');
    if (row) {//判断是否选中行
        finLevel = row.lskmzd.finLevel;
        if(finLevel == 1){//判断是否为末级

            itemNo = row.lskmzd.itemNo;  //获取要初始化的科目的编号

            supAcc2 = row.lskmzd.supAcc2
            if(supAcc2 == null || supAcc2 == ""){  //对应一个专项核算类别
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/SpAccountInitialization/getItemByIdAndSpecial1/' + itemNo +'/'+spLevel,
                    contentType: 'application/json',
                    success: function (result) {
                        if (result.code == 100) {
                            var data = result.extend.lshsjeOrLshsslForInit.lskmzdVoForSpecial;
                            var loadData = result.extend.lshsjeOrLshsslForInit.lshsjeOrLshsslQueryVos;

                            accType = row.lskmzd.accType
                            if(accType == 'J') {//金额账
                                $('#specialWinJe1').window('open');
                                $('#close_button1').linkbutton('disable');
                                $('#specialTableJe1').datagrid('loadData', loadData);

                                reloadFooterJe1();

                                tableClassify = 'specialTableJe1';

                                balanceCorrect = data.balance;
                                debitMoneySupCorrect = data.debitMoneySup;
                                creditMoneySupCorrect = data.creditMoneySup;
                                supMoneyCorrect = data.supMoney;
                            } else{
                                $('#specialWinSl1').window('open');
                                $('#close_button2').linkbutton('disable');
                                $('#specialTableSl1').datagrid('loadData', loadData);

                                reloadFooterSl1();

                                tableClassify = 'specialTableSl1';

                                balanceCorrect =data.balance;
                                debitMoneySupCorrect=data.debitMoneySup;
                                creditMoneySupCorrect=data.creditMoneySup;
                                supMoneyCorrect=data.supMoney;
                                supQtyCorrect=data.supQty;
                                debitQtySupCorrect=data.debitQtySup;
                                creditQtySupCorrect=data.creditQtySup;
                                leftQtyCorrect=data.leftQty;
                            }

                        }
                    },
                });


            }else { //对应两个专项核算对象
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/SpAccountInitialization/getItemByIdAndSpecial2/' + itemNo,
                    contentType: 'application/json',
                    success: function (result) {
                        if (result.code == 100) {
                            var data = result.extend.lshsjeOrLshsslForInit.lskmzdVoForSpecial;
                            var loadData = result.extend.lshsjeOrLshsslForInit.lshsjeOrLshsslQueryVos;

                            accType = row.lskmzd.accType
                            if(accType == 'J') {//金额账
                                $('#specialWinJe2').window('open');
                                $('#specialTableJe2').datagrid('loadData', loadData);

                                reloadFooterJe2();

                                tableClassify = 'specialTableJe2';

                                balanceCorrect =data.balance;
                                debitMoneySupCorrect=data.debitMoneySup;
                                creditMoneySupCorrect=data.creditMoneySup;
                                supMoneyCorrect=data.supMoney;
                            } else{
                                $('#specialWinSl2').window('open');
                                $('#specialTableSl2').datagrid('loadData', loadData);

                                reloadFooterSl2();

                                tableClassify = 'specialTableSl2';

                                balanceCorrect =data.balance;
                                debitMoneySupCorrect=data.debitMoneySup;
                                creditMoneySupCorrect=data.creditMoneySup;
                                supMoneyCorrect=data.supMoney;
                                supQtyCorrect=data.supQty;
                                debitQtySupCorrect=data.debitQtySup;
                                creditQtySupCorrect=data.creditQtySup;
                                leftQtyCorrect=data.leftQty;
                            }
                        }
                    },
                });

            }

        }else{
            $.messager.alert('提示', '请选择明细科目进行初始化！', 'info');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }

}

function reloadFooterJe1(){  //计算je表的页脚行总计
    supMoney = compute("supMoney");
    debitMoneySup = compute("debitMoneySup");
    creditMoneySup = compute("creditMoneySup");
    balance = compute("balance");

    $('#specialTableJe1').datagrid('reloadFooter',
        [{"spName1":"合计","supMoney":supMoney,"debitMoneySup":debitMoneySup,"creditMoneySup":creditMoneySup,"balance":balance}]
    );
}

//指定列求和
function compute(colName) {
    var rows = $('#specialTableJe1').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

function reloadFooterSl1(){  //计算sl表的页脚行总计
    supMoney = computeSl("supMoney");
    debitMoneySup = computeSl("debitMoneySup");
    creditMoneySup = computeSl("creditMoneySup");
    balance = computeSl("balance");
    supQty = computeSl("supQty");
    debitQtySup = computeSl("debitQtySup");
    creditQtySup = computeSl("creditQtySup");
    leftQty = computeSl("leftQty");

    $('#specialTableSl1').datagrid('reloadFooter',
        [{"spName1":"合计","supMoney":supMoney,"debitMoneySup":debitMoneySup,"creditMoneySup":creditMoneySup,"balance":balance,"supQty":supQty,"debitQtySup":debitQtySup,"creditQtySup":creditQtySup,"leftQty":leftQty}]
    );
}

//指定列求和
function computeSl(colName) {
    var rows = $('#specialTableSl1').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

function reloadFooterJe2(){  //计算je表的页脚行总计
    supMoney = computeJe2("supMoney");
    debitMoneySup = computeJe2("debitMoneySup");
    creditMoneySup = computeJe2("creditMoneySup");
    balance = computeJe2("balance");

    $('#specialTableJe2').datagrid('reloadFooter',
        [{"spName1":"合计","supMoney":supMoney,"debitMoneySup":debitMoneySup,"creditMoneySup":creditMoneySup,"balance":balance}]
    );
}

//指定列求和
function computeJe2(colName) {
    var rows = $('#specialTableJe2').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

function reloadFooterSl2(){  //计算sl表的页脚行总计
    supMoney = computeSl2("supMoney");
    debitMoneySup = computeSl2("debitMoneySup");
    creditMoneySup = computeSl2("creditMoneySup");
    balance = computeSl2("balance");
    supQty = computeSl2("supQty");
    debitQtySup = computeSl2("debitQtySup");
    creditQtySup = computeSl2("creditQtySup");
    leftQty = computeSl2("leftQty");

    $('#specialTableSl2').datagrid('reloadFooter',
        [{"spName1":"合计","supMoney":supMoney,"debitMoneySup":debitMoneySup,"creditMoneySup":creditMoneySup,"balance":balance,"supQty":supQty,"debitQtySup":debitQtySup,"creditQtySup":creditQtySup,"leftQty":leftQty}]
    );
}

//指定列求和
function computeSl2(colName) {
    var rows = $('#specialTableSl2').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

//计算上级科目编号
function getCatNo(num,level){
    var length;
    var no;
    if(level != 1){
        length  = 4;
        no = num.substring(0,length);
    }else{
        no = 0;
    }
    return no;
}

function uperLevelInit_click() {
    if(spLevel==1){
        $.messager.alert('提示', '当前已为第一级！', 'info');
    }else{
        spLevel--;
        if(spLevel == 2){
            no = spNo.substring(0,4);
            showSpAccount(no,spLevel);
        }
        if(spLevel ==1){
            no = 0;
            $.ajax({
                type: 'POST',
                url: getRealPath() + '/SpAccountInitialization/getItemByIdAndSpecial1/' + itemNo +'/'+spLevel,
                contentType: 'application/json',
                success: function (result) {
                    if (result.code == 100) {
                        var loadData = result.extend.lshsjeOrLshsslForInit.lshsjeOrLshsslQueryVos;

                        if(accType == 'J') {//金额账
                            $('#specialTableJe1').datagrid('loadData', loadData);

                            reloadFooterJe1();

                            tableClassify = 'specialTableJe1';

                        } else{
                            $('#specialTableSl1').datagrid('loadData', loadData);

                            reloadFooterSl1();

                            tableClassify = 'specialTableSl1';
                        }
                    }
                },
            });
        }
    }

}
//下级点击事件
function nextLevelInit_click() {
    var row = $('#specialTableJe1').datagrid('getSelected');
    if(row==null){  //判断是否选中行
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }else if(spLevel == 3){
        $.messager.alert('提示', '当前已为末页！', 'info');
    }else{
        spLevel++;
        spNo = row.spNo1;
        if(spLevel == 2){
            no = spNo.substring(0,4);
        }
        if(spLevel ==3){
            no = spNo.substring(0,8);
        }

        showSpAccount(no,spLevel);
    }

}

//下级点击事件
function nextLevelInitSl1_click() {
    var row = $('#specialTableSl1').datagrid('getSelected');
    if(row==null){  //判断是否选中行
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }else if(spLevel == 3){
        $.messager.alert('提示', '当前已为末页！', 'info');
    }else{
        spLevel++;
        spNo = row.spNo1;
        if(spLevel == 2){
            no = spNo.substring(0,4);
        }
        if(spLevel ==3){
            no = spNo.substring(0,8);
        }

        showSpAccount(no,spLevel);
    }

}

//显示当前级的科目
function showSpAccount(no, spLevel){

    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountInitialization/querySpAccountByLevel/' + no + '/' + spLevel + '/' + itemNo,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var loadData1 = result.extend.lshsjeOrLshsslQueryVos;
                if(accType == 'J') {//金额账

                    $('#specialTableJe1').datagrid('loadData', loadData1);

                    reloadFooterJe1();

                    tableClassify = 'specialTableJe1';

                } else{
                    $('#specialTableSl1').datagrid('loadData', loadData1);

                    reloadFooterSl1();

                    tableClassify = 'specialTableSl1';

                }
            }
            }
    });
}

//显示当前级的科目
function showCaption(num, level){
    var new_num = "";
    if(num==""){new_num="0";}
    else{new_num=num;}
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountInitialization/getCaptionOfSpAccount/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lskmzdQueryVos;
                $('#main_table').datagrid('loadData', data);
            }
        }
    });
}

//计算当前级别科目编号长度
function getNumLength(level) {
    if(level==0){
        return 0;
    }
    var length = 0;
    for(var i=0;i<level;i++){
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

//计算补零长度
function getZeroLength(level) {
    var length = 0;
    for(var i=level; i<sub_stru_.length; i++){
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

//获取科目级数
function dealSubStru() {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/getSubjectStructure/',
        contentType: 'application/json',
        success: function (result) {
            var stru = result.split("");
            for(var i=0;i<stru.length;i++){
                sub_stru_[i]=stru[i];
            }
            if(result=="0"){
                $.messager.alert('提示', "请先设置科目结构", 'info');
                //TODO：关闭标签页
            }
        },
    });
}

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}



//获取特殊科目数据
function getData(id) {
    var sup_acc1 = $('#special_accounting_1_input').prop("name");
    var sup_acc2 = $('#special_accounting_2_input').prop("name");
    var journal = null;
    var exchange = null;
    $('#edit_account_box').window('open');
    //判断是否为日记账
    if($('#daily_account').prop("checked")){
        journal = 1;
    }else{
        journal = 0;
    }
    //判断是否为往来
    if($('#dealing').prop("checked")){
        exchange = 1;
    }else{
        exchange = 0;
    }
    var data = '{"itemNo":"' + id + '",' +
        '"supAcc1":"' + sup_acc1 + '",' +
        '"supAcc2":"' + sup_acc2 + '",' +
        '"journal":"' + journal + '",' +
        '"exchange":"' + exchange + '"}';
    return data;

}

//获取特殊科目的Map表


function dealConfigs(){
    $.ajax({
        type: 'POST',
        async: false,
        url: getRealPath() + '/CaptionOfAccount/getConfigsForCap/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                //处理科目结构
                var stru = result.extend.sub_stru.split("");
                for(var i=0;i<stru.length;i++){
                    sub_stru_[i]=stru[i];
                }
                //处理初始日期
                begin_month_ = result.extend.begin_date.substr(4, 2);

            }
        }
    });
}

//获得财务初始日期前一个月的月份，如果初始日期是一月，则使用上年结转金额
//type为J则输出金额相关的结转字段名，为Y则输出数量相关
function getBeginMonth(type){
    if(begin_month_=="01"){
        if(type=="J") return "supMoney";
        else if(type=="Y") return "supQty";
    }
    else if(begin_month_=="0"){
        $.messager.alert('提示', "请先设置科目结构", 'info');
        //TODO：关闭标签页
    }
    else{
        var m = parseInt(begin_month_) - 1;
        var last_month = m < 10 ? ('0' + m.toString()) : m.toString();
        if(type=="J") return "balance"+last_month;
        else if(type=="Y") return "leftQty"+last_month;
    }
}