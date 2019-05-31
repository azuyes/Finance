
//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度

var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称

//定义从后台获取的相应科目的对应字段值，用于判断初始化时用户输入的数据之和和这些正确的值是否相等
var balanceCorrect=0;
var debitMoneySupCorrect=0;
var creditMoneySupCorrect=0;
var supMoneyCorrect=0;
var supQtyCorrect=0;
var debitQtySupCorrect=0;
var creditQtySupCorrect=0;
var leftQtyCorrect=0;

//用户输入的对应的各个字段的值
var supMoney = 0;
var debitMoneySup = 0;
var creditMoneySup = 0;
var balance = 0;
var supQty = 0;
var debitQtySup = 0;
var creditQtySup = 0;
var leftQty = 0;
var tableClassify ;//initSlTable数量表initJeTable金额表

var editIndex = undefined;

var showupperItemName  = '';

$(function () {

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

        if ($('#initJeTable').datagrid('validateRow', editIndex)){  //验证通过后结束编辑

            $('#initJeTable').datagrid('endEdit', editIndex);
            editIndex = undefined;

            return true;
        } else {
            return false;
        }
    }

    function onClickCell(index, field,value){   //点击金额表单元格事件
        if (endEditing()){

            reloadFooterJe();  //重新加载计算合计

            $('#initJeTable').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }
    }

    function endSlEditing(){    //判断是否结束编辑数量表
        if (editIndex == undefined){return true}

        if ($('#initSlTable').datagrid('validateRow', editIndex)){
            $('#initSlTable').datagrid('endEdit', editIndex);
            editIndex = undefined;
            return true;
        } else {
            return false;
        }
    }

    function onClickSlCell(index, field,value){ //点击数量表单元格事件
        if (endSlEditing()){
            reloadFooterSl();
            $('#initSlTable').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndex = index;

        }
    }

    $('#level').val(level_);
    $('#superior_account').val(upper_No_);
    dealSubStru();//保存科目级数
    dealPrecisions();
    showCaption(upper_No_,level_.toString());

    obj = {
        //上级点击事件，在帮助窗口选择科目编号时
        uperLevel_click : function () {
            if (level_ == 1) {
                return;
            }
            level_--;
            $('#level').val(level_);
            if(level_ == 1){
                showupperItemName = "";
            }else{
                showupperItemName = showupperItemName.split("\\",level_-1) + "\\";
            }

            $("#upperIntemName_label").text(showupperItemName);
            // $("#uperItemName").textbox('setValue', showupperItemName);
            // document.getElementById("#uperItemName").innerHTML=showupperItemName;
            upper_No_ = getCatNo(upper_No_, level_ - 1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },
        //下级点击事件，在帮助窗口选择科目编号时
        nextLevel_click : function () {
            var row = $('#itemTable').datagrid('getSelected');
            if(row==null){
                $.messager.alert('提示','请选择一条记录','info');
                return;}//判断是否选中行
            level_++;
            $('#level').val(level_);

            showupperItemName = showupperItemName+row.itemName+"\\";
            $("#upperIntemName_label").text(showupperItemName);

            upper_No_ = getCatNo(row.itemNo,level_-1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
            $('#itemTable').datagrid("selectRow", 0);
        },


    }

    //初始对话框
    $('#box').dialog({
        width: 380,
        height: 190,
        title: '往来单位科目选择',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                itemNoSelected();
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#box').dialog('close');
            }
        }],
    });

    //帮助窗口
    $('#helpWin').window({
        width: 480,
        height: 500,
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
    });

    //初始化加载，解决页脚行不加载问题。
    var dataJe ='{"total":0,"rows":[],"footer":[{"companyName":"合计","supMoney":0,"debitMoneySup":0,"creditMoneySup":0,"balance":0}]}';
    var dataSl ='{"total":0,"rows":[],"footer":[{"companyName":"合计","supMoney":0,"debitMoneySup":0,"creditMoneySup":0,"balance":0,"supQty":0,"debitQtySup":0,"creditQtySup":0,"leftNo":0}]}';
    var jsonJe  = JSON.parse(dataJe);
    var jsonSl = JSON.parse(dataSl);
    //主界面数据表格
    $('#initJeTable').datagrid({
        //height:'90%',
        width: '100%',
        showFooter: true,
        data : jsonJe,
        iconCls: 'icon-edit',
        singleSelect: true,
        fitColumns: true,
        striped: true,
        onClickCell: onClickCell,   //单击单元格事件
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
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'debitMoneySup',
                title: '本年借方金额',
                halign:'center',
                align: 'right',
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'creditMoneySup',
                title: '本年贷方金额',
                halign:'center',
                align: 'right',
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'balance',
                title: '当前金额',
                halign:'center',
                align: 'right',
                width: '10%',
                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'FinLevel6',
                title: '年初数量',
                halign:'center',
                align: 'right',
                width: '10%'
            },
            {
                field: 'FinLevel7',
                title: '本年借方数量',
                halign:'center',
                align: 'right',
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

    //主界面数据表格
    $('#initSlTable').datagrid({
        //height:'90%',
        width: '100%',
        showFooter: true,
        data : jsonSl,
        iconCls: 'icon-edit',
        singleSelect: true,
        // rownumbers: true,
        fitColumns: true,
        striped: true,
        onClickCell: onClickSlCell,
        columns: [[
            {
                field: 'companyNo',
                title: '单位编号',
                align: 'center',
                width: '9%',
            },
            {
                field: 'itemNo',
                title: '科目编号',
                align: 'center',
                hidden: true
            },
            {
                field: 'companyName',
                title: '单位名称',
                align: 'center',
                width: '10%',

            },
            {
                field: 'supMoney',
                title: '年初金额',
                halign:'center',
                align: 'right',
                width: '10%',

                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'debitMoneySup',
                title: '本年借方金额',
                halign:'center',
                align: 'right',
                width: '10%',

                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'creditMoneySup',
                title: '本年贷方金额',
                halign:'center',
                align: 'right',
                width: '10%',

                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'balance',
                title: '当前金额',
                halign:'center',
                align: 'right',
                width: '10%',

                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
                formatter: function (value,row,index) {
                    return thousandFormatter(value);
                }
            },
            {
                field: 'supQty',
                title: '年初数量',
                halign:'center',
                align: 'right',
                width: '10%',

                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },
            {
                field: 'debitQtySup',
                title: '本年借方数量',
                halign:'center',
                align: 'right',
                width: '10%',

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
                width: '10%',

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
                width: '10%',

                editor:{
                    type:'numberbox',
                    options:{precision:2}
                },
            },
        ]],
        toolbar: '#tb',
    });

    //帮助窗口数据表格
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        columns: [[
            {
                field: 'itemNo',
                title: '编号',
                width: 120,
                formatter: function (value,row,index) {
                    var length = getNumLength(level_);
                    var no = value.substring(0,length);
                    return no;
                }
            },
            {
                field: 'itemName',
                title: '名称',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                width: 80,
                formatter: function (value,row,index) {
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'item',
                title: '级数',
                width: 80
            },
        ]],
        toolbar: '#tb_helpWin',
        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件

            if (rowData.finLevel == 1){
                $.messager.alert('提示', '该科目没有下级', 'info');
                return;
            }
            level_++;
            upper_No_ = getCatNo(rowData.itemNo, level_ - 1); //获取选中的分类编号

            showupperItemName = showupperItemName+rowData.itemName+"\\";
            $("#upperIntemName_label").text(showupperItemName);

            showCaption(upper_No_, level_);


        }
    });


})


//帮助窗口，选定科目时事件
function selectClick() {

    var row = $('#itemTable').datagrid("getSelected");
    if(row.finLevel == 0){
        $.messager.alert('警告操作！', '科目非明细！', 'warning');
    }else if(row.exchang == 0){
        $.messager.alert('警告操作！', '该科目不是往来科目！', 'warning');
    }else{
        itemName = row.itemName;
        $('#helpWin').window('close');
        $("#itemNo_input").val(row.itemNo);
    }

}

//初始对话框，填好科目编号后，点击确定时的处理事件
function itemNoSelected (){
    itemNo = $('#itemNo_input').val();
    if(itemNo == ""){
        $.messager.alert('警告操作！', '科目不存在！', 'warning');
    }else{
        $.ajax({
            type: 'POST',
            url: getRealPath() + '/ContactsInitialization/getItemByIdAndCompany/' + itemNo ,
            contentType: 'application/json',
            success: function (result) {
                if(result.code == 100) {
                    var data = result.extend.LswljeOrLswlsl.lskmzdVoForContacts;  //获取科目字典信息，加载在页面最上面进行显示
                    var loadData = result.extend.LswljeOrLswlsl.lswljeQueryVoList;  //获取金额或者数量信息，用于在je或sl表进行显示

                    if(data.finLevel == 0){
                        $.messager.alert('警告操作！', '科目非明细！', 'warning');
                    }else if(data.exchang == 0){
                        $.messager.alert('警告操作！', '该科目不是往来科目！', 'warning');
                    }else{  //当在初始化对话框输入或选择的编号为往来科目时，进行数据加载

                        $('#helpWin').window('close');
                        $('#box').dialog('close');
                        document.getElementById("initializtion").style.visibility = "visible";

                        $("#itemNo_label").text(itemNo);
                        $("#itemName_label").text(itemName);

                        if(data.accType == 'J'){ //金额账
                            $('#initJeTable').datagrid('loadData', loadData);
                            document.getElementById("initJe").style.visibility = "visible";

                            reloadFooterJe();

                            tableClassify = 'initJeTable';

                            balanceCorrect =data.balance;
                            debitMoneySupCorrect=data.debitMoneySup;
                            creditMoneySupCorrect=data.creditMoneySup;
                            supMoneyCorrect=data.supMoney;


                            // var rows = $('#initJeTable').datagrid('getFooterRows');
                            // rows[0]['companyNo'] = '合计';
                            // rows[0]['supMoney'] = supMoney;
                            // rows[0]['debitMoney'] = debitMoney;
                            // rows[0]['creditMoney'] = creditMoney;
                            // rows[0]['balance'] = balance;
                            // $('#initJeTable').datagrid('reloadFooter');

                            // $('#initJeTable').datagrid('appendRow', {
                            //     companyNo: '<span style=" font-weight: bold">合计</span>',
                            //     supMoney: '<span style=" font-weight: bold ;disabled:disabled">' + compute("supMoney") + '</span>',
                            //     debitMoney: '<span style=" font-weight: bold;disabled:disabled">' + compute("debitMoney") + '</span>',
                            //     creditMoney: '<span style=" font-weight: bold;disabled:disabled">' + compute("creditMoney") + '</span>',
                            //     balance: '<sinitJepan style=" font-weight: bold;disabled:disabled">' + compute("balance") + '</span>',
                            // });
                        }else{
                            $('#initSlTable').datagrid('loadData', loadData);
                            document.getElementById("initSl").style.visibility = "visible";

                            reloadFooterSl();
                            tableClassify = 'initSlTable';

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

                    //$('#initTable').datagrid('loadData', data);
                }else{
                    $.messager.alert('警告操作！', '科目不存在！', 'warning');
                }
            }
        });
    }
}

function reloadFooterJe(){  //计算je表的页脚行总计
    supMoney = compute("supMoney");
    debitMoneySup = compute("debitMoneySup");
    creditMoneySup = compute("creditMoneySup");
    balance = compute("balance");

    $('#initJeTable').datagrid('reloadFooter',
        [{"companyName":"合计","supMoney":supMoney,"debitMoneySup":debitMoneySup,"creditMoneySup":creditMoneySup,"balance":balance}]
    );
}

//指定列求和
function compute(colName) {
    var rows = $('#initJeTable').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}


function reloadFooterSl(){  //计算sl表的页脚行总计
    supMoney = computeSl("supMoney");
    debitMoneySup = computeSl("debitMoneySup");
    creditMoneySup = computeSl("creditMoneySup");
    balance = computeSl("balance");
    supQty = computeSl("supQty");
    debitQtySup = computeSl("debitQtySup");
    creditQtySup = computeSl("creditQtySup");
    leftQty = computeSl("leftQty");

    $('#initSlTable').datagrid('reloadFooter',
        [{"companyName":"合计","supMoney":supMoney,"debitMoneySup":debitMoneySup,"creditMoneySup":creditMoneySup,"balance":balance,"supQty":supQty,"debitQtySup":debitQtySup,"creditQtySup":creditQtySup,"leftQty":leftQty}]
    );
}

//指定列求和
function computeSl(colName) {
    var rows = $('#initSlTable').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

//存盘事件
function save(){
    var rows;

    if(tableClassify == 'initJeTable'){
        if ($('#initJeTable').datagrid('validateRow', editIndex)){
            $('#initJeTable').datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
        reloadFooterJe();

        rows = $('#initJeTable').datagrid('getRows');

        var mark = 'correct';

        for (var i = 0; i < rows.length; i++) {
            if(parseFloat(rows[i].supMoney)+parseFloat(rows[i].debitMoneySup)-parseFloat(rows[i].creditMoneySup)!=parseFloat(rows[i].balance)){
                $.messager.alert('提示', rows[i].companyName+'的当前余额<><br>年初余额+本年借方-本年贷方，请修改！', 'info');
                mark = 'error';
            }
        }

        if(mark == 'correct'){
            if(supMoneyCorrect!=supMoney){
                $.messager.alert('提示', '各单位的年初余额<>其对应科目的<br>年初余额，请修改！', 'warning');
                mark = 'error';
            }
            if(debitMoneySupCorrect!=debitMoneySup){
                $.messager.alert('提示', '各单位的本年借方合计<>其对应科目的<br>借方合计，请修改！', 'warning');
                mark = 'error';
            }
            if(creditMoneySupCorrect!=creditMoneySup){
                $.messager.alert('提示', '各单位的本年贷方合计<>其对应科目的<br>本年贷方合计，请修改！', 'warning');
                mark = 'error';
            }
            if(balanceCorrect!=balance){
                $.messager.alert('提示', '各单位的当前金额<>其对应科目的<br>当前金额，请修改！', 'warning');
                mark = 'error';
            }
            if(mark == 'correct'){

                // var _list = {};
                 var rows = $('#initJeTable').datagrid('getRows');
                // for (var i = 0; i < rows.length; i++) {
                //     _list["LswljeOrLswlslQueryVos[" + i + "].companyNo"] = rows[i].companyNo; //这里list要和后台的参数名List<Category> list一样
                //     _list["LswljeOrLswlslQueryVos[" + i + "].companyName"] = rows[i].companyName;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].supMoney"] = rows[i].supMoney;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].debitMoney"] = rows[i].debitMoney;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].creditMoney"] = rows[i].creditMoney;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].balance"] = rows[i].balance;
                //
                // }

                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/ContactsInitialization/updateContactsInitial',
                    data:JSON.stringify(rows),
                    contentType: 'application/json;charset=utf-8',
                    success: function (result) {
                        if(result.code == 100) {
                            $.messager.alert('提示', '恭喜你，存盘成功！', 'info');
                        }else {
                            $.messager.alert('提示', '未知错误，请重试！', 'warning');
                        }
                    }
                });
            }
        }

        // var rowsJson= JSON.parse(rows);
        // var footerJson = JSON.parse(footer);
    }else{
        if ($('#initSlTable').datagrid('validateRow', editIndex)){
            $('#initSlTable').datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
        reloadFooterSl();

        rows = $('#initSlTable').datagrid('getRows');

        var mark = 'correct';

        for (var i = 0; i < rows.length; i++) {
            if(parseFloat(rows[i].supMoney)+parseFloat(rows[i].debitMoneySup)-parseFloat(rows[i].creditMoneySup)!=parseFloat(rows[i].balance)){
                $.messager.alert('提示', rows[i].companyName+'的当前余额<><br>年初余额+本年借方-本年贷方，请修改！', 'info');
                mark = 'error';
            }
            if(parseFloat(rows[i].supQty)+parseFloat(rows[i].debitQtySup)-parseFloat(rows[i].creditQtySup)!=parseFloat(rows[i].leftQty)){
                console.log(parseFloat(rows[i].supMoney));
                $.messager.alert('提示', rows[i].companyName+'的当前数量<><br>年初数量+本年借方数量-本年贷方数量，请修改！', 'info');
                mark = 'error';
            }
        }

        if(mark == 'correct'){
            if(supMoneyCorrect!=supMoney){
                $.messager.alert('提示', '各单位的年初余额<>其对应科目的<br>年初余额，请修改！', 'warning');
                mark = 'error';
            }
            if(debitMoneySupCorrect!=debitMoneySup){
                $.messager.alert('提示', '各单位的本年借方合计<>其对应科目的<br>借方合计，请修改！', 'warning');
                mark = 'error';
            }
            if(creditMoneySupCorrect!=creditMoneySup){
                $.messager.alert('提示', '各单位的本年贷方合计<>其对应科目的<br>本年贷方合计，请修改！', 'warning');
                mark = 'error';
            }
            if(balanceCorrect!=balance){
                $.messager.alert('提示', '各单位的当前金额<>其对应科目的<br>当前金额，请修改！', 'warning');
                mark = 'error';
            }
            if(supQtyCorrect!=supQty){
                $.messager.alert('提示', '各单位的年初数量<>其对应科目的<br>年初数量，请修改！', 'warning');
                mark = 'error';
            }
            if(debitQtySupCorrect!=debitQtySup){
                $.messager.alert('提示', '各单位的借方金额<>其对应科目的<br>借方金额，请修改！', 'warning');
                mark = 'error';
            }
            if(creditQtySupCorrect!=creditQtySup){
                $.messager.alert('提示', '各单位的贷方数量<>其对应科目的<br>贷方数量，请修改！', 'warning');
                mark = 'error';
            }
            if(leftQtyCorrect!=leftQty){
                $.messager.alert('提示', '各单位的当前数量<>其对应科目的<br>当前数量，请修改！', 'warning');
                mark = 'error';
            }
            if(mark == 'correct'){

                // var _list = {};
                var rows = $('#initSlTable').datagrid('getRows');
                // for (var i = 0; i < rows.length; i++) {
                //     _list["LswljeOrLswlslQueryVos[" + i + "].companyNo"] = rows[i].companyNo; //这里list要和后台的参数名List<Category> list一样
                //     _list["LswljeOrLswlslQueryVos[" + i + "].companyName"] = rows[i].companyName;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].supMoney"] = rows[i].supMoney;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].debitMoney"] = rows[i].debitMoney;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].creditMoney"] = rows[i].creditMoney;
                //     _list["LswljeOrLswlslQueryVos[" + i + "].balance"] = rows[i].balance;
                //
                // }

                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/ContactsInitialization/updateContactsInitialSl',
                    data:JSON.stringify(rows),
                    contentType: 'application/json;charset=utf-8',
                    success: function (result) {
                        if(result.code == 100) {
                            $.messager.alert('提示', '恭喜你，存盘成功！', 'info');
                        }
                        else{
                            $.messager.alert('提示', '未知错误，请重试！', 'warning');
                        }
                    }
                });
            }
        }

    }


}

//显示当前级的科目
function showCaption(num, level){
    var new_num = "";
    if(num==""){new_num="0";}
    else{new_num=num;}
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/ContactsInitialization/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lskmzdList;
                $('#itemTable').datagrid('loadData', data);
            }
        }
    });
}

//计算上级科目编号长度
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
                alert("请先设置科目结构");
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

//获取金额账式的数据
function getMoneyData() {
    var acc_level = (level_).toString();
    var acc_num = getFullNo(upper_No_, $('#account_number_input').val(), level_);
    var acc_input = $('#account_name_input').val();
    var acc_attr = $('#account_attribute_select').combobox('getValue');
    var acc_form = $('#account_form_select').combobox('getValue');
    var acc = $('#account_select').combobox('getValue');
    var begin = "", this_debit = "", this_credit = "", current = "";
    if(acc_form=="J"){
        begin = $('#begin_quantity_input').val();
        this_debit = $('#this_debit_quantity_input').val();
        this_credit = $('#this_credit_quantity_input').val();
        current = $('#quantity_input').val();
    }
    else{
        begin = $('#begin_remain_input').val();
        this_debit = $('#this_debit_input').val();
        this_credit = $('#this_credit_input').val();
        current = $('#balance_input').val();
    }
    var data = '{"item":"' + acc_level + '",' +
        '"itemNo":"' + acc_num + '",' +
        '"itemName":"' + acc_input + '",' +
        '"ele":"' + acc_attr + '",' +
        '"accType":"' + acc_form + '",' +
        '"spType":"' + acc + '",' +
        '"SupMoney":"' + begin + '",' +
        '"debitMoneySup":"' + this_debit + '",' +
        '"creditMoneySup":"' + this_credit + '",' +
        '"' + getBeginMonth("J") + '":"' + current + '"}';
    return data;
}

//获取数量账式的数据
function getQuantityData(){
    var acc_num = getFullNo(upper_No_, $('#account_number_input').val(), level_);
    var form_head_1 = $('#form_head1_input').val();
    var form_head_2 = $('#form_head2_input').val();
    var form_head_3 = $('#form_head3_input').val();
    var form_head_4 = $('#form_head4_input').val();
    var form_head_5 = $('#form_head5_input').val();
    var form_head_6 = $('#form_head6_input').val();
    var	begin = $('#begin_remain_input').val();
    var	this_debit = $('#this_debit_input').val();
    var	this_credit = $('#this_credit_input').val();
    var	current = $('#balance_input').val();
    var data = '{"itemNo":"' + acc_num + '",' +
        '"supQty":"' + begin + '",' +
        '"debitQtySup":"' + this_debit + '",' +
        '"creditQtySup":"' + this_credit + '",' +
        '"' + getBeginMonth("Y") + '":"' + current + '",'+
        '"head1":"' + form_head_1 + '",' +
        '"head2":"' + form_head_2 + '",' +
        '"head3":"' + form_head_3 + '",' +
        '"head4":"' + form_head_4 + '",' +
        '"head5":"' + form_head_5 + '",' +
        '"head6":"' + form_head_6 + '"}';
    return data;
}

//获得财务初始日期前一个月的月份，如果初始日期是一月，则使用上年结转金额
//type为J则输出金额相关的结转字段名，为Y则输出数量相关
function getBeginMonth(type){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/getBeginMonth/',
        contentType: 'application/json',
        success: function (result) {
            if(result=="01"){
                if(type=="J") return "supMoney";
                else if(type=="Y") return "supQty";
            }
            else if(result=="0"){
                alert("请先设置科目结构");
                //TODO：关闭标签页
            }
            else{
                if(type=="J") return "LeftMoney"+result;
                else if(type=="Y") return "LeftQty"+result;
            }
        },
    });
}

//获得完整的科目编号，即补0后的编号
function getFullNo(uperNo,thisNo,level) {
    var length = getZeroLength(level);
    var fullNo = uperNo.toString() + thisNo.toString();
    for(var i=0;i<length;i++){
        fullNo += "0";
    }
    return fullNo;
}

//批量enable或disable文本框
function dealTextBox(boxes, is_show){
    if(is_show==true){
        for(var i=0; i<boxes.length; i++){
            boxes[i].textbox('enable');
        }
    }
    else{
        for(var i=0;i<boxes.length;i++){
            boxes[i].textbox('disable');
            boxes[i].textbox('clear');
        }
    }
}

//获取金额和数量的小数精度
function dealPrecisions() {
    var money_prec = 2;
    var quan_prec = 2;
    precisions_.push(money_prec);
    precisions_.push(quan_prec);
}

function  searchItemNoClick() {
    searchHelp("#itemTable","#searchText_ItemNo","itemNo","itemName");
}

function searchHelp(eleTable,eleInput,no,name){
    var val = $(eleInput).val();
    var row = $(eleTable).datagrid("getSelected");
    var rowIndex ;
    if (row) {
        rowIndex = $(eleTable).datagrid('getRowIndex', row);
    }else{
        rowIndex = -1;
    }
    var gridData = $(eleTable).datagrid("getData");

    // $.each(gridData.rows, function () {
    //     console.log(this[no]);
    //     if(this[no].search(val) != -1 == this[name].search(val) != -1){
    //         $(eleTable).datagrid("selectRow", i);
    //     }else if(i == gridData.total -1){
    //         $.messager.alert('提示', '向下没有找到！', 'info');
    //     }
    // })
    var i = rowIndex +1;
    for (i; i < gridData.total; i++) {
        //console.log(gridData.rows[i][no]);
        if(gridData.rows[i][no].search(val) != -1 || gridData.rows[i][name].search(val) != -1){
            $(eleTable).datagrid("selectRow", i);
            return ;
        }
    }
    if(i == gridData.total ){
        $.messager.alert('提示', '向下没有找到！', 'info');
    }
}