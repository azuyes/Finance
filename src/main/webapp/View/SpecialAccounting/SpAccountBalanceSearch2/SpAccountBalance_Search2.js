

//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度

var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称

var year = "";
var month = "";
var m = "";
var catNo1 = "";  //核算分类1编号
var spNo1 = "";   //核算对象1编号
var catNo2 = "";  //核算分类1编号
var spNo2 = "";   //核算对象1编号
var searchAccType = "J";//查询格式（数量账，金额账，S，J）
var searchOption = "N";//查询选项（是否包含着账前凭证）
var queryDataType = "";//第八种查询在两个都输入通配符时候，弹出的对话框选择的值
var queryDataType1 = "";//第七种查询，在两个都输入通配符时候，弹出的对话框选择的值

var searchKind = '';//查询种类，总共有4类查询，1、输科目，单位通配符2、科目通配符，输单位 3、两都输 4、两都通配符


var current_date_ = "";

var flag = true;

var spLevel1ForHelp = "1";
var spLevel2ForHelp = "1";
var uperSpNo1ForHelp = '';//帮助窗口上级核算编号
var uperSpNo2ForHelp = '';//帮助窗口上级核算编号

var winKind = 1;

var is_show_all = 0;

var showupperItemName  = '';
var showupperSpName1 = '';
var showupperSpName2 = '';


$(function(){
    dealSubStru();//保存科目级数
    dealPrecisions();
    showCaption(upper_No_,level_.toString());

    obj = {
        //上级点击事件
        uperLevel_click : function () {
            if(level_==1){return;}
            level_--;
            $('#level').val(level_);
            if(level_ == 1){
                showupperItemName = "";
            }else{
                showupperItemName = showupperItemName.split("\\",level_-1) + "\\";
            }

            $('#upperItemName_label').text(showupperItemName);
            upper_No_=getCatNo(upper_No_,level_-1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },
        //下级点击事件
        nextLevel_click : function () {
            var row = $('#itemTable').datagrid('getSelected');
            if(row==null){
                $.messager.alert('提示','请选择一条记录','info');
                return;}//判断是否选中行
            level_++;
            $('#level').val(level_);

            showupperItemName = showupperItemName+row.itemName+"\\";

            $('#upperItemName_label').text(showupperItemName);
            upper_No_ = getCatNo(row.itemNo,level_-1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },

        uperLevelSp1_click : function () {
            var no = $('#catNo1_Input').val();
            if(spLevel1ForHelp != 1){//判断是否为首级

                if(spLevel1ForHelp == 3){
                    uperSpNo1ForHelp = uperSpNo1ForHelp.substring(0,4);
                }else if(spLevel1ForHelp ==2){
                    uperSpNo1ForHelp = "";
                }
                spLevel1ForHelp--;

                if(spLevel1ForHelp == 1){
                    showupperSpName1 = "";
                }else{
                    showupperSpName1 = showupperSpName1.split("\\",spLevel1ForHelp) + "\\";
                }
                $("#uperSpName1_Show").text(showupperSpName1);

                showSpNoForHelp(uperSpNo1ForHelp,spLevel1ForHelp,no);
            }else{
                $.messager.alert('提示', '当前已为末页！', 'info');
            }
        },
        //下级点击事件，在帮助窗口选择科目编号时
        nextLevelSp1_click : function () {
            var no = $('#catNo1_Input').val();
            var row = $('#spNo1Table').datagrid('getSelected');
            if (row) {//判断是否选中行
                if(spLevel1ForHelp != 3){//判断是否为末级
                    var length = 0;
                    if(spLevel1ForHelp == 1){
                        length = 4;
                    }else if(spLevel1ForHelp ==2){
                        length = 8;
                    }
                    spLevel1ForHelp++;

                    showupperSpName1 = showupperSpName1+row.spName+"\\";
                    $("#uperSpName1_Show").text(showupperSpName1);

                    uperSpNo1ForHelp = row.spNo.substring(0,length);
                    showSpNoForHelp(uperSpNo1ForHelp,spLevel1ForHelp,no);
                }else{
                    $.messager.alert('提示', '当前已为末页！', 'info');
                }
            }else{
                $.messager.alert('提示', '请选择一条记录！', 'info');
            }
        },

        uperLevelSp2_click : function () {
            var no = $('#catNo2_Input').val();
            if(spLevel2ForHelp != 1){//判断是否为首级

                if(spLevel2ForHelp == 3){
                    uperSpNo2ForHelp = uperSpNo2ForHelp.substring(0,4);
                }else if(spLevel2ForHelp ==2){
                    uperSpNo2ForHelp = "";
                }
                spLevel2ForHelp--;

                if(spLevel2ForHelp == 1){
                    showupperSpName2 = "";
                }else{
                    showupperSpName2 = showupperSpName2.split("\\",spLevel2ForHelp) + "\\";
                }
                $("#uperSpName2_Show").text(showupperSpName2);

                showSpNoForHelp(uperSpNo2ForHelp,spLevel2ForHelp,no);
            }else{
                $.messager.alert('提示', '当前已为末页！', 'info');
            }
        },
        //下级点击事件，在帮助窗口选择科目编号时
        nextLevelSp2_click : function () {
            var no = $('#catNo2_Input').val();
            var row = $('#spNo2Table').datagrid('getSelected');
            if (row) {//判断是否选中行
                if(spLevel2ForHelp != 3){//判断是否为末级
                    var length = 0;
                    if(spLevel2ForHelp == 1){
                        length = 4;
                    }else if(spLevel2ForHelp ==2){
                        length = 8;
                    }
                    spLevel2ForHelp++;

                    showupperSpName2 = showupperSpName2+row.spName+"\\";
                    $("#uperSpName2_Show").text(showupperSpName2);

                    uperSpNo2ForHelp = row.spNo.substring(0,length);
                    showSpNoForHelp(uperSpNo2ForHelp,spLevel2ForHelp,no);
                }else{
                    $.messager.alert('提示', '当前已为末页！', 'info');
                }
            }else{
                $.messager.alert('提示', '请选择一条记录！', 'info');
            }
        },

        //主界面点击下级时事件
        nextLevelClick : function(){
            var row = $('#main_table').datagrid('getSelected');
            if (row) {//判断是否选中行
                if(level_ != 7){//判断是否为末级
                    level_++;
                    upper_No_ = getCatNo(row.lskmzd.itemNo,level_-1); //获取选中的分类编号
                    showItemInfo(upper_No_,level_);
                }else{
                    $.messager.alert('提示', '当前已为末页！', 'info');
                }
            }else{
                $.messager.alert('提示', '请选择一条记录！', 'info');
            }



        },

        //主界面点击上级事件时
        uperLevelClick : function(){
            if(level_ != 1){//判断是否为首级

                level_--;
                upper_No_=getCatNo(upper_No_,level_-1);

                showItemInfo(upper_No_,level_);
            }else{
                $.messager.alert('提示', '当前已为末页！', 'info');
            }
        },

        //主界面点击全显时
        allDisplayClick : function(){
            if(is_show_all){
                $('#show_button').linkbutton({text:"全显"});
                $('#uper_button').linkbutton('enable');
                $('#next_button').linkbutton('enable');
                showItemInfo("",1);
                is_show_all = 0;
            }
            else{
                $('#uper_button').linkbutton('disable');
                $('#next_button').linkbutton('disable');
                $('#show_button').linkbutton({text:"分级"});
                level_ = 1;
                showItemInfo("",0);
                is_show_all = 1;
            }
        },
    }

    $('#balanceSearch_Win').window({
        width: 390,
        height: 425,
        title: '专项核算余额查询',
        collapsible: false,
        minimizable: false,
        resizable: false,
        maximizable: false,
    });

    //帮助窗口
    $('#itemNo_HelpWin').window({
        width: 480,
        height: 500,
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
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
        toolbar: '#tbItem_helpWin',

        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件

            if (rowData.finLevel == 1){
                $.messager.alert('提示', '该科目没有下级', 'info');
                return;
            }
            level_++;
            upper_No_ = getCatNo(rowData.itemNo, level_ - 1); //获取选中的分类编号

            showupperItemName = showupperItemName+rowData.itemName+"\\";
            $("#upperItemName_label").text(showupperItemName);

            showCaption(upper_No_, level_);


        }
    });


    $('#catNo1_HelpWin').window({
        width: 480,
        height: 500,
        title: '专项核算类别',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#catNo1Table').datagrid({
        width:'100%',
        height:'100%',
        fit: true,
        fitColumns: false,
        singleSelect : true,
        url: getRealPath() + '/SpAccountCategory/getSpAccountCategory',
        columns: [[
            {
                field: 'catNo',
                title: '编号',
                align:'center',
                width: 120
            },
            {
                field: 'catName',
                title: '名称',
                align:'center',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                align:'center',
                width: 80,

            },
            {
                field: 'spLevel',
                title: '级数',
                align:'center',
                width: 80
            },

        ]],
        toolbar: '#tbCatNo1_helpWin',
    });


    //帮助窗口
    $('#spNo1_HelpWin').window({
        width: 480,
        height: 500,
        title: '专项核算类别',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    //帮助窗口数据表格
    $('#spNo1Table').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        columns: [[
            {
                field: 'spNo',
                title: '编号',
                width: 120,

            },
            {
                field: 'spName',
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
                field: 'spLevel',
                title: '级数',
                width: 80
            },
        ]],
        toolbar: '#tbSpNo1_helpWin',
    });


    //帮助窗口
    $('#catNo2_HelpWin').window({
        width: 480,
        height: 500,
        title: '专项核算类别',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#catNo2Table').datagrid({
        width:'100%',
        height:'100%',
        fit: true,
        fitColumns: false,
        singleSelect : true,
        url: getRealPath() + '/SpAccountCategory/getSpAccountCategory',
        columns: [[
            {
                field: 'catNo',
                title: '编号',
                align:'center',
                width: 120
            },
            {
                field: 'catName',
                title: '名称',
                align:'center',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                align:'center',
                width: 80,

            },
            {
                field: 'spLevel',
                title: '级数',
                align:'center',
                width: 80
            },

        ]],
        toolbar: '#tbCatNo2_helpWin',
    });

    //帮助窗口
    $('#spNo2_HelpWin').window({
        width: 480,
        height: 500,
        title: '专项核算编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
    });

    //帮助窗口数据表格
    $('#spNo2Table').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        columns: [[
            {
                field: 'spNo',
                title: '编号',
                width: 120,

            },
            {
                field: 'spName',
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
                field: 'spLevel',
                title: '级数',
                width: 80
            },
        ]],
        toolbar: '#tbSpNo2_helpWin',
    });

    //第二级查询窗口，选择查询的类型
    $('#queryDataType_Win').window({
        width: 400,
        height: 215,
        title: '选择查询数据',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    //第二级查询窗口，选择查询的类型
    $('#queryDataType_Win1').window({
        width: 400,
        height: 215,
        title: '选择查询数据',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    //查询结果显示表
    $('#main_table').datagrid({
        width: fixWidth(0.99),
        multiSort: false,
        toolbar: '#button_group',
        singleSelect: true,
        showFooter: true,
        columns: [
            [{
                field: 'spNo1',
                title: '核算编号1',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lshsje['spNo1'];
                }
            }, {
                field: 'spName1',
                title: '核算名称1',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lshszd1['spName'];
                }
            },{
                field: 'spNo2',
                title: '核算编号2',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lshsje['spNo2'];
                }
            }, {
                field: 'spName2',
                title: '核算名称2',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lshszd2['spName'];
                }
            }, {
                field: 'itemNo',
                title: '科目编号',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    var level  ;
                    var offset = '';
                    if(is_show_all){
                        level = row.lskmzd['item'];
                        for(var i = 1; i < level; i ++){
                            offset += '&nbsp;&nbsp;&nbsp;&nbsp;';
                        }
                    }
                    level = row.lskmzd['item'];
                    var length = getNumLength(level-1);
                    var no = row.lskmzd['itemNo'].substr(length,sub_stru_[level-1]);
                    return offset + no;
                }
            }, {
                field: 'itemName',
                title: '科目名称',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lskmzd['itemName'];
                }
            },{
                field: 'bkpDirectionSup',
                title: '借贷方向',
                width: '4%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {

                    if(m == 1  ){
                        if(row.lshsje['supMoney']>0){
                            return '借';
                        }else if(row.lshsje['supMoney']<0){
                            return '贷';
                        }else if(row.lshsje['supMoney']==0){
                            return '平';
                        }
                    }
                    else if(row.lshsje['balance'+getFullMonth(m-1)]>0){
                        return '借';
                    }else if(row.lshsje['balance'+getFullMonth(m-1)]<0){
                        return '贷';
                    }else if(row.lshsje['balance'+getFullMonth(m-1)]==0){
                        return '平';
                    }
                }
            }, {
                field: 'titleSupMoney',
                title: '上月结转',
                width: '12%',
                colspan: 2,
                halign:'center',
                align: 'right',
            },  {
                field: 'supMoney',
                title: '上月结转',
                width: '12%',
                rowspan: 2,
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;
                    if(m == 1 ) return thousandFormatter(row.lshsje['supMoney']);
                    else return thousandFormatter(row.lshsje['balance'+getFullMonth(m-1)]);
                }
            }, {
                field: 'thisMonth',
                title: '本月发生',
                width: '12%',
                colspan: 2,
                halign:'center',
                align: 'right',
            }, {
                field: 'thisMonthCredit',
                title: '本月贷方',
                width: '12%',
                colspan: 2,
                halign:'center',
                align: 'right',
            }, {
                field: 'bkpDirectionAcm',
                title: '借贷方向',
                width: '4%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    if(row.lshsje['debitMoney' + getFullMonth(m)] > row.lshsje['creditMoney' + getFullMonth(m)]){
                        return '借';
                    }
                    else if(row.lshsje['debitMoney' + getFullMonth(m)] < row.lshsje['creditMoney' + getFullMonth(m)]){
                        return '贷';
                    }
                    else {
                        return '平';
                    }
                }
            }, {
                field: 'titleBalance',
                title: '当前余额',
                colspan: 2,
                width: '12%',
                halign:'center',
                align: 'right',
            }, {
                field: 'balance',
                title: '当前余额',
                rowspan: 2,
                width: '12%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    //return row.lshsje['balance'+getFullMonth(m)];

                    return thousandFormatter(row.lshsje['balance'+getFullMonth(m)]);
                }
            }], [ {
                field: 'debitQtySup',
                title: '数量',
                width: '12%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lshssl == null) return null;
                    //var mm = current_date_.substr(4, 2);
                    //return row.lshssl['debitQty' + mm];
                    if(m == 1 ) return row.lshssl['supQty'];
                    else return row.lshssl['leftQty'+getFullMonth(m-1)];

                }
            }, {
                field: 'debitMoneySup',
                title: '借方',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);
                    //return row.lshsje['debitMoney' + getFullMonth(m)];

                    if(m==1 ) return thousandFormatter(row.lshsje['supMoney']);
                    else return thousandFormatter(row.lshsje['balance'+getFullMonth(m-1)]);
                }
            },  {
                field: 'debitQty',
                title: '数量',
                width: '12%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lshssl == null) return null;
                    //var mm = current_date_.substr(4, 2);
                    //return row.lshssl['debitQty' + mm];

                    return row.lshssl['debitQty'+getFullMonth(m)];

                }
            }, {
                field: 'debitMoney',
                title: '借方',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);
                    //return row.lshsje['debitMoney' + getFullMonth(m)];


                    return thousandFormatter(row.lshsje['debitMoney'+getFullMonth(m)]);

                }
            }, {
                field: 'creditQty',
                title: '数量',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    if(row.lshssl == null) return null;
                    //var mm = current_date_.substr(4, 2);
                    //return row.lshssl['creditQty'+getFullMonth(m)];

                    return row.lshssl['creditQty'+getFullMonth(m)];
                }
            }, {
                field: 'creditMoney',
                title: '贷方',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);

                    return thousandFormatter(row.lshsje['creditMoney'+getFullMonth(m)]);
                }
            }, {
                field: 'creditQtyAcm',
                title: '数量',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    if(row.lshssl == null) return null;
                    //var mm = current_date_.substr(4, 2);
                    //return row.lshssl['creditQty'+getFullMonth(m)];

                    return row.lshssl['leftQty'+getFullMonth(m)];
                }
            }, {
                field: 'creditMoneyAcm',
                title: '贷方',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);

                    return thousandFormatter(row.lshsje['balance'+getFullMonth(m)]);
                }
            }]
        ],
        toolbar: '#tb',
    });

    $('#itemNo_Input').textbox('textbox').bind('dblclick', function() {
        judgeSelected("#itemTable");
        $('#itemNo_HelpWin').window('open');
    })

    $('#catNo1_Input').textbox('textbox').bind('dblclick', function() {
        judgeSelected("#catNo1Table");
        $('#catNo1_HelpWin').window('open');
    });

    $('#spNo1_Input').textbox('textbox').bind('dblclick', function() {
        var no = $('#catNo1_Input').val();
        if(no != ""){
            spLevel1ForHelp = '1';
            winKind = 1;
            showSpNoForHelp("","1",no);
            judgeSelected("#spNo1Table");
            $('#spNo1_HelpWin').window('open');
        }else{
            $.messager.alert('提示！', '请输入核算类别1编号！', 'info');
        }
    });
    $('#catNo2_Input').textbox('textbox').bind('dblclick', function() {
        judgeSelected("#catNo2Table");
        $('#catNo2_HelpWin').window('open');
    });

    $('#spNo2_Input').textbox('textbox').bind('dblclick', function() {
        var no = $('#catNo2_Input').val();
        if(no != ""){
            spLevel2ForHelp = '1';
            winKind = 2;
            showSpNoForHelp("","1",no);
            judgeSelected("#spNo2Table");
            $('#spNo2_HelpWin').window('open');
        }else{
            $.messager.alert('提示！', '请输入核算类别2编号！', 'info');
        }

    });

});

function judgeSelected(ele){
    if($(ele).datagrid("getData").total>0){
        $(ele).datagrid("selectRow", 0);
    }
}

function showSpNoForHelp(uperSpNoForHelp,spLevelForHelp,catNo1){
    var params = {};
    //params.XX必须与Spring Mvc controller中的参数名称一致
    //否则在controller中使用@RequestParam绑定
    params.catNo = catNo1;
    params.spNo = uperSpNoForHelp;
    params.spLevel = spLevelForHelp;
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountBalanceSearch/queryLshszd',
        dataType:"json",
        data: params,
        success: function (result) {
            if(result.code == 100) {
                var lshszds = result.extend.lshszds;
                if(winKind == 1){
                    $('#spNo1Table').datagrid('loadData', lshszds); //需要添加具体的data内容
                    judgeSelected("#spNo1Table");
                }else if(winKind ==2){
                    $('#spNo2Table').datagrid('loadData', lshszds); //需要添加具体的data内容
                    judgeSelected("#spNo2Table");
                }
            }
        }
    });
}

function showItemInfo(itemNo,item){
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.item = item;
    params.catNo1 = catNo1;
    params.spNo1 = spNo1;
    params.catNo2 = catNo2;
    params.spNo2 = spNo2;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance6',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var spAccountBalanceQueryVo2s = result.extend.spAccountBalanceQueryVo2s;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVo2s); //需要添加具体的data内容
            }else{
            }
        }
    });
}


//输入完查询条件，点击查询时事件
function searchClick(){
    year = $('#year_Input').val();
    m = $('#month_Input').val();
    itemNo = $('#itemNo_Input').val();
    catNo1 = $('#catNo1_Input').val();
    spNo1 = $('#spNo1_Input').val();
    catNo2 = $('#catNo2_Input').val();
    spNo2 = $('#spNo2_Input').val();
    searchAccType = $("input[name=searchFormat]:checked").val();//查询格式（数量账，金额账，S，J）
    searchOption = $("input[name=searchOption]:checked").val();//查询选项（是否包含着账前凭证）

    var isValidItemNo=$('#itemNo_Input').textbox("isValid");
    var isValidCatNo1=$('#catNo1_Input').textbox("isValid");
    var isValidSpNo1=$('#spNo1_Input').textbox("isValid");
    var isValidCatNo2=$('#catNo2_Input').textbox("isValid");
    var isValidSpNo2=$('#spNo2_Input').textbox("isValid");
    if(!isValidItemNo || !isValidCatNo1 || !isValidSpNo1 || !isValidCatNo2 || !isValidSpNo2){
        flag = false;
        return ;
    }

    if(catNo1 == catNo2){
        $.messager.alert('提示','请输入不同的分类编号1和分类编号2','info');
        return ;
    }

    //第五种查询情况：输入科目编号，核算分类1编号，核算分类2编号；输入核算编号1，核算编号2为通配符
    if(itemNo != "...." && spNo1 == "..." && spNo2 == "..." && itemNo.indexOf("....") == -1  ){
        searchKind = '1';
        setDatagridByformat(searchAccType,searchKind);
        search5();
        $('#balanceSearch_Win').window('close');
        tbShow();
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
    }

    //第六种查询情况：输入科目编号为通配符，输入核算分类编号，核算编号
    if(itemNo == "...." && spNo1 != "..." && spNo2 != "..."){
        searchKind = '2';
        setDatagridByformat(searchAccType,searchKind);
        search6();
        $('#balanceSearch_Win').window('close');
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
    }

    //第七种查询情况：
    if(itemNo.lastIndexOf("....") != -1 && itemNo !="...." && spNo1 == "..." && spNo2 == "..."  ){
        $('#queryDataType_Win1').window('open');
        searchKind = '3';
        return ;
    }

    //第八种查询情况：
    if(itemNo == "...." && spNo1 == "..." && spNo2 == "..."){
        $('#queryDataType_Win').window('open');
        searchKind = '4';
        return ;
    }

    $.messager.alert('警告','请组合正确的查询条件','warning');

}

function tbShow(){
    $('#uper_button').linkbutton('disable');
    $('#next_button').linkbutton('disable');
    $('#show_button').linkbutton('disable');
}

//当所有的判断都正确后执行查询操作
function search5(){
    var params = {};
    //params.XX必须与Spring Mvc controller中的参数名称一致
    //否则在controller中使用@RequestParam绑定
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.catNo1 = catNo1;
    params.spNo1 = spNo1;
    params.catNo2 = catNo2;
    params.spNo2 = spNo2;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance5',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var spAccountBalanceQueryVo2s = result.extend.spAccountBalanceQueryVo2s;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVo2s); //需要添加具体的data内容
            }else{
            }
        }
    });
}

function search6(){
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.item = level_;
    params.catNo1 = catNo1;
    params.spNo1 = spNo1;
    params.catNo2 = catNo2;
    params.spNo2 = spNo2;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance6',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var spAccountBalanceQueryVo2s = result.extend.spAccountBalanceQueryVo2s;
                level_ = 1;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVo2s); //需要添加具体的data内容
            }else{
            }
        }
    });
}

function searchClick7(){

    //前台获取
    var columns;

    $.ajax({
        type: "GET",
        url: getRealPath() + '/SpAccountBalanceSearch/getAllColumn7/'+catNo1+'/'+catNo2+'/'+itemNo,
        dataType: "json",
        async:false,//同步请求
        success: function(data){
            columns = data.columns;
            initDataGrid();
        }
    });


    function initDataGrid() {
        $("#main_table").datagrid({
            //url : "api/v11/getAllInfo",// 加载的URL /EasyUiDemo/api/v11/getAllInfo
            columns : [columns],
        });
    }

    queryDataType1 = $("input[name=queryDataType1]:checked").val();
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.catNo1 = catNo1;
    params.spNo1 = spNo1;
    params.catNo2 = catNo2;
    params.spNo2 = spNo2;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType1;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/getAllInfo7',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win1').window('close');
            tbShow();
            document.getElementById("mainDisplay").style.visibility = "visible";
        }
    });
}

function searchClick8(){

    //前台获取
    var columns;

    $.ajax({
        type: "GET",
        url: getRealPath() + '/SpAccountBalanceSearch/getAllColumn8/'+catNo1+'/'+catNo2,
        dataType: "json",
        async:false,//同步请求
        success: function(data){
            columns = data.columns;
            initDataGrid();
        }
    });


    function initDataGrid() {
        $("#main_table").datagrid({
            //url : "api/v11/getAllInfo",// 加载的URL /EasyUiDemo/api/v11/getAllInfo
            columns : [columns],
        });
    }

    queryDataType = $("input[name=queryDataType]:checked").val();
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.catNo1 = catNo1;
    params.spNo1 = spNo1;
    params.catNo2 = catNo2;
    params.spNo2 = spNo2;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/getAllInfo8',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win').window('close');
            tbShow();
            document.getElementById("mainDisplay").style.visibility = "visible";
        }
    });
}


//帮助窗口，选定科目时事件
function selectItemNoClick() {

    var row = $('#itemTable').datagrid("getSelected");
    if (row) {//判断是否选中行
        if(row.supAcc1 == null || row.supAcc1 == "" || row.supAcc2 == null || row.supAcc2==""){
            $.messager.alert('警告操作！', '该科目不是交叉核算科目！请重新选择', 'warning');
        }else{
            itemNo = row.itemNo;
            //itemName = row.itemName;
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue',row.itemNo);
            $("#catNo1_Input").textbox('setValue',row.supAcc1);
            $("#spNo1_Input").textbox('setValue','...');
            $("#catNo2_Input").textbox('setValue',row.supAcc2);
            $("#spNo2_Input").textbox('setValue','...');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }
}

//帮助窗口，选定科目为通配符时事件
function selectItemNoForWildcardClick() {
    var row = $('#itemTable').datagrid('getSelected');
    if (row) {//判断是否选中行
         if(row.supAcc2 == null ){
            $.messager.alert('警告操作！', '请选择交叉核算的科目！', 'warning');
        }else{
            itemNo = row.itemNo;
            var itemDisplay = getCatNo(itemNo,level_)+"....";
            //itemName = row.itemName;
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue',itemDisplay);
            $("#catNo1_Input").textbox('setValue',row.supAcc1);
            $("#catNo2_Input").textbox('setValue',row.supAcc2);
            $("#spNo1_Input").textbox('setValue','...');
            $("#spNo2_Input").textbox('setValue','...');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }

}

//点击科目编号后面的通配符提示按钮
function wildcardForItemNoClick() {

    $("#itemNo_Input").textbox('setValue',"....");
}

//点击核算编号后面的通配符提示按钮
function wildcardForSpNo1Click() {

    $("#spNo1_Input").textbox('setValue','...');
}

//点击核算编号后面的通配符提示按钮
function wildcardForSpNo2Click() {

    $("#spNo2_Input").textbox('setValue','...');
}


//帮助窗口，选定分类编号时事件
function selectcatNo1Click(){
    var row = $('#catNo1Table').datagrid("getSelected");
    catNo1 = row.catNo;
    $('#catNo1_HelpWin').window('close');
    $("#catNo1_Input").textbox('setValue',row.catNo);
    $("#spNo1_Input").textbox('setValue',null);
}

//帮助窗口，选定核算编号时事件
function selectSpNo1Click(){
    var row = $('#spNo1Table').datagrid("getSelected");
    spNo1 = row.spNo;
    $('#spNo1_HelpWin').window('close');
    $("#spNo1_Input").textbox('setValue',row.spNo);
}

//帮助窗口，选定分类编号时事件
function selectcatNo2Click(){
    var row = $('#catNo2Table').datagrid("getSelected");
    catNo2 = row.catNo;
    $('#catNo2_HelpWin').window('close');
    $("#catNo2_Input").textbox('setValue',row.catNo);
    $("#spNo2_Input").textbox('setValue',null);
}

//帮助窗口，选定核算编号时事件
function selectSpNo2Click(){
    var row = $('#spNo2Table').datagrid("getSelected");
    spNo2 = row.spNo;
    $('#spNo2_HelpWin').window('close');
    $("#spNo2_Input").textbox('setValue',row.spNo);
}

function  searchItemNoClick() {
    searchHelp("#itemTable","#searchText_ItemNo","itemNo","itemName");
}

function searchCatNo1Click(){
    searchHelp("#catNo1Table","#searchText_CatNo1","catNo","catName");
}

function searchSpNo1Click(){
    searchHelp("#spNo1Table","#searchText_SpNo1","spNo","spName");
}

function searchCatNo2Click(){
    searchHelp("#catNo2Table","#searchText_CatNo2","catNo","catName");
}

function searchSpNo2Click(){
    searchHelp("#spNo2Table","#searchText_SpNo2","spNo","spName");
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


//显示当前级的科目
function showCaption(num, level){
    var new_num = "";
    if(num==""){new_num="0";}
    else{new_num=num;}
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountObjectDef/queryCaptionOfAccountByLevel1/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lskmzdList;
                $('#itemTable').datagrid('loadData', data);
                judgeSelected("#itemTable");
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

//获取金额和数量的小数精度
function dealPrecisions() {
    var money_prec = 2;
    var quan_prec = 2;
    precisions_.push(money_prec);
    precisions_.push(quan_prec);
}

function setDatagridByformat(searchAccType,searchKind){
    var table = $('#main_table');

    if(searchKind == '1' || searchKind == '3'){
        table.datagrid('hideColumn','itemNo');
        table.datagrid('hideColumn','itemName');
    }
    if(searchKind == '2'){
        table.datagrid('hideColumn','spNo1');
        table.datagrid('hideColumn','spName1');
        table.datagrid('hideColumn','spNo2');
        table.datagrid('hideColumn','spName2');
    }

    if(searchAccType == 'J' ){
        //上月：仅显示上月结转直接标题，二级标题全部隐藏
        table.datagrid('showColumn','supMoney');
        table.datagrid('getColumnOption','supMoney').title = '上月结转';
        table.datagrid('showColumn','bkpDirectionSup');

        table.datagrid('hideColumn','titleSupMoney');
        table.datagrid('hideColumn','debitQtySup');
        table.datagrid('hideColumn','debitMoneySup');

        //本月：不显示本月贷方，数量相关不显示
        table.datagrid('hideColumn','thisMonthCredit');
        table.datagrid('getColumnOption','thisMonth').title = '本月发生';

        table.datagrid('hideColumn','creditQty');
        table.datagrid('hideColumn','debitQty');
        table.datagrid('getColumnOption','debitMoney').title = '借方';
        table.datagrid('getColumnOption','creditMoney').title = '贷方';

        //当前：仅显示当前余额直接标题，二级标题全部隐藏
        table.datagrid('hideColumn','titleBalance');
        table.datagrid('hideColumn','creditMoneyAcm');
        table.datagrid('hideColumn','creditQtyAcm');
        table.datagrid('showColumn','balance');
        table.datagrid('showColumn','bkpDirectionAcm');
    }


    if(searchAccType == 'S' ){
        //显示借贷方向
        table.datagrid('showColumn','bkpDirectionSup');

        //上月：显示一级标题为：上月结转，二级标题改为:数量,金额
        table.datagrid('showColumn','titleSupMoney');
        table.datagrid('getColumnOption','titleSupMoney').title = '上月结转';
        table.datagrid('showColumn','debitMoneySup');
        table.datagrid('showColumn','debitQtySup');
        table.datagrid('getColumnOption','debitMoneySup').title = '金额';

        //隐藏显示金额账时的上月结转列
        table.datagrid('hideColumn','supMoney');

        //将显示金额账时的一级标题为：本月发生改为本月借方，二级标题显示借方数量，借方金额
        table.datagrid('getColumnOption','thisMonth').title = '本月借方';
        table.datagrid('showColumn','debitQty');
        table.datagrid('showColumn','debitMoney');
        table.datagrid('getColumnOption','debitMoney').title = '金额';

        //本月贷方：一级标题本月贷方，二级标题：数量，金额
        table.datagrid('showColumn','thisMonthCredit');
        table.datagrid('showColumn','creditQty');
        table.datagrid('showColumn','creditMoney');
        table.datagrid('getColumnOption','creditMoney').title = '金额';

        //显示借贷方向
        table.datagrid('showColumn','bkpDirectionAcm');

        //当前：显示一级标题为：当前余额，二级标题改为:数量,金额
        table.datagrid('showColumn','titleBalance');
        table.datagrid('getColumnOption','titleBalance').title = '当前余额';
        table.datagrid('showColumn','creditQtyAcm');
        table.datagrid('showColumn','creditMoneyAcm');
        table.datagrid('getColumnOption','creditMoneyAcm').title = '金额';
        //隐藏显示金额账时的当前余额
        table.datagrid('hideColumn','balance');
    }


    table.datagrid();
}

function getFullMonth(number){
    return number >= 10 ? number.toString() : '0' + number.toString();
}

//获取宽度
function fixWidth(percent)
{
    return document.body.clientWidth * percent ; //这里你可以自己做调整
}


