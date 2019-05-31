
//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度

var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称

var spLevel = 1; //核算对象的级数
var upper_spNo = ""; //核算编号，在点击下级的时候获得，用于返回上一级和查找下一级的内容

var year = "";
var month = "";
var m = "";
var catNo1 = "";  //核算分类1编号
var spNo1 = "";   //核算对象1编号
var searchAccType = "J";//查询格式（数量账，金额账，S，J）
var searchOption = "N";//查询选项（是否包含着账前凭证）
var queryDataType1 = "";//第三种查询，在两个都输入通配符时候，弹出的对话框选择的值
var queryDataType2 = "";//第四种查询，在两个都输入通配符时候，弹出的对话框选择的值

var searchKind = '';//查询种类，总共有4类查询，1、输科目，单位通配符2、科目通配符，输单位 3、两都输 4、两都通配符

var yearOmonth = 'month';
var is_show_all = 0;

var current_date_ = "";

var flag = true;

var ym = '月';

var winKind = '1';//帮助窗口的类别，1为科目编号帮助窗口，2为核算类别编号帮助窗口，3为核算编号帮助窗口
var spLevelForHelp = '1';//帮助窗口核算编号的级数
var uperSpNoForHelp = '';//帮助窗口上级核算编号

var showupperItemName  = '';
var showupperSpName = '';

$(function(){
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

        uperLevelSp_click : function () {
            var no = $('#catNo1_Input').val();
            if(spLevelForHelp != 1){//判断是否为首级

                if(spLevelForHelp == 3){
                    uperSpNoForHelp = uperSpNoForHelp.substring(0,4);
                }else if(spLevelForHelp ==2){
                    uperSpNoForHelp = "";
                }
                spLevelForHelp--;
                if(spLevelForHelp == 1){
                    showupperSpName = "";
                }else{
                    showupperSpName = showupperSpName.split("\\",spLevelForHelp) + "\\";
                }
                $("#upperSpName1_label").text(showupperSpName);
                // $("#uperSpName_Show").textbox('setValue', showupperSpName);
                showSpNoForHelp(uperSpNoForHelp,spLevelForHelp,no);
            }else{
                $.messager.alert('提示', '当前已为末页！', 'info');
            }
        },
        //下级点击事件，在帮助窗口选择科目编号时
        nextLevelSp_click : function () {
            var no = $('#catNo1_Input').val();
            var row = $('#spNo1Table').datagrid('getSelected');
            if (row) {//判断是否选中行
                if(spLevelForHelp != 3){//判断是否为末级
                    var length = 0;
                    if(spLevelForHelp == 1){
                        length = 4;
                    }else if(spLevelForHelp ==2){
                        length = 8;
                    }
                    spLevelForHelp++;
                    showupperSpName = showupperSpName+row.spName+"\\";

                    $("#upperSpName1_label").text(showupperSpName);
                    uperSpNoForHelp = row.spNo.substring(0,length);
                    showSpNoForHelp(uperSpNoForHelp,spLevelForHelp,no);
                }else{
                    $.messager.alert('提示', '当前已为末页！', 'info');
                }
            }else{
                $.messager.alert('提示', '请选择一条记录！', 'info');
            }
        },

        //主界面点击下级时事件
        nextLevelClick : function(){

            if(searchKind == "1"){
                nextLevel1();
            }else if(searchKind == "2"){
                nextLevel2();
            }else if(searchKind == "3"){
                nextLevel3();
            }else if(searchKind == "4"){
                nextLevel4();
            }


        },

        //主界面点击上级事件时
        uperLevelClick : function(){
            if(searchKind == "1"){
                uperLevel1();
            }else if(searchKind == "2"){
                uperLevel2();
            }else if(searchKind == "3"){
                uperLevel3();
            }else if(searchKind == "4"){
                uperLevel4();
            }
        },

        //主界面点击全显时
        allDisplayClick : function(){
            if(searchKind == "1"){
                allDisplay1();
            }else if(searchKind == "2"){
                allDisplay2();
            }else if(searchKind == "3"){
                allDisplay3();
            }else if(searchKind == "4"){
                allDisplay4();
            }
        },

        //主界面点击本月时
        switchYearToMonth: function () {
             ym = '月';

            yearOmonth = 'month';
            $('#year_button').linkbutton('enable');
            $('#month_button').linkbutton('disable');

            var table = $('#main_table');
            table.datagrid('getColumnOption','titleSupMoney').title = '上'+ym+'结转';
            table.datagrid('getColumnOption','supMoney').title = '上'+ym+'结转';
            table.datagrid('getColumnOption','thisMonth').title = '本'+ym+'发生';
            table.datagrid('getColumnOption','thisMonthCredit').title = '本'+ym+'贷方';
            table.datagrid();
            if(searchKind == "1"){
                showSpInfo(upper_spNo, spLevel);
            }else if(searchKind == "2"){
                showItemInfo(upper_No_,level_);
            }

        },

        //主界面点击本年时
        switchMonthToYear: function () {

            yearOmonth = 'year';
             ym = '年';
            $('#year_button').linkbutton('disable');
            $('#month_button').linkbutton('enable');

            var table = $('#main_table');
            table.datagrid('getColumnOption','titleSupMoney').title = '上'+ym+'结转';
            table.datagrid('getColumnOption','supMoney').title = '上'+ym+'结转';
            table.datagrid('getColumnOption','thisMonth').title = '本'+ym+'发生';
            table.datagrid('getColumnOption','thisMonthCredit').title = '本'+ym+'贷方';
            table.datagrid();
            if(searchKind == "1"){
                showSpInfo(upper_spNo, spLevel);
            }else if(searchKind == "2"){
                showItemInfo(upper_No_,level_);
            }
        },
    }

    $('#balanceSearch_Win').window({
        width: 390,
        height: 350,
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
            $("#upperIntemName_label").text(showupperItemName);

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

        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件
            var no = $('#catNo1_Input').val();
            if(spLevelForHelp != 3){//判断是否为末级
                var length = 0;
                if(spLevelForHelp == 1){
                    length = 4;
                }else if(spLevelForHelp ==2){
                    length = 8;
                }
                spLevelForHelp++;
                showupperSpName = showupperSpName+rowData.spName+"\\";

                $("#upperSpName1_label").text(showupperSpName);
                uperSpNoForHelp = rowData.spNo.substring(0,length);
                showSpNoForHelp(uperSpNoForHelp,spLevelForHelp,no);
            }else{
                $.messager.alert('提示', '当前已为末页！', 'info');
            }


        }
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
        toolbar: '#tbSpNo2_helpWin',
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

    //第二级查询窗口，选择查询的类型
    $('#queryDataType_Win2').window({
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
                title: '核算编号',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {

                    var level;
                    var offset = '';
                    if(is_show_all){
                        level = row.lshszd['spLevel'];
                        for(var i = 1; i < level; i ++){
                            offset += '&nbsp;&nbsp;&nbsp;&nbsp;';
                        }
                    }
                    return offset + row.lshszd['spNo'];
                }
            }, {
                field: 'spName',
                title: '核算名称',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lshszd['spName'];
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
                title: '借或贷',
                width: '4%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {

                    if(m == 1 || yearOmonth == 'year' ){
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
                    if(m == 1 || yearOmonth == 'year') return thousandFormatter(row.lshsje['supMoney']);
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
                title: '借或贷',
                width: '4%',
                rowspan: 2,
                halign:'center',
                align: 'right',
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
                    if( yearOmonth == 'year') return thousandFormatter(row.lshsje['balance']);
                    else return thousandFormatter(row.lshsje['balance'+getFullMonth(m)]);
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
                    if(m == 1 || yearOmonth == 'year') return row.lshssl['supQty'];
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

                    if(m==1 || yearOmonth == 'year') return thousandFormatter(row.lshsje['supMoney']);
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
                    if( yearOmonth == 'year') return row.lshssl['debitQty'];
                    else return row.lshssl['debitQty'+getFullMonth(m)];

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

                    if(yearOmonth == 'year') return thousandFormatter(row.lshsje['debitMoney']);
                    else return thousandFormatter(row.lshsje['debitMoney'+getFullMonth(m)]);

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
                    if(yearOmonth == 'year') return row.lshsje['creditQty'];
                    else return row.lshssl['creditQty'+getFullMonth(m)];
                }
            }, {
                field: 'creditMoney',
                title: '贷方',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);
                    if(yearOmonth == 'year') return thousandFormatter(row.lshsje['creditMoney']);
                    else return thousandFormatter(row.lshsje['creditMoney'+getFullMonth(m)]);
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
                    if(yearOmonth == 'year') return row.lshsje['creditQty'];
                    else return row.lshssl['leftQty'+getFullMonth(m)];
                }
            }, {
                field: 'creditMoneyAcm',
                title: '贷方',
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);
                    if(yearOmonth == 'year') return thousandFormatter(row.lshsje['balance']);
                    else return thousandFormatter(row.lshsje['balance'+getFullMonth(m)]);
                }
            }]
        ],
        toolbar: '#tb',
    });

    $('#itemNo_Input').textbox('textbox').bind('dblclick', function() {
        winKind = "1";
        judgeSelected("#itemTable");
        $('#itemNo_HelpWin').window('open');
    })

    $('#catNo1_Input').textbox('textbox').bind('dblclick', function() {
        winKind = "2";
        judgeSelected("#catNo1Table");
        $('#catNo1_HelpWin').window('open');
    });

    $('#spNo1_Input').textbox('textbox').bind('dblclick', function() {
        var no = $('#catNo1_Input').val();
        if(no != ""){
            spLevelForHelp = '1';
            showSpNoForHelp("","1",no);
            winKind = "3";
            judgeSelected("#spNo1Table")
            $('#spNo1_HelpWin').window('open');
        }else{
            $.messager.alert('提示！', '请先输入核算类别编号再选择核算编号！', 'info');
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
                $('#spNo1Table').datagrid('loadData', lshszds); //需要添加具体的data内容
                judgeSelected("#spNo1Table");
            }
        }
    });
}

function allDisplay1(){
    if(is_show_all){
        $('#show_button').linkbutton({text:"全显"});
        $('#uper_button').linkbutton('enable');
        $('#next_button').linkbutton('enable');

        if(ym == '月'){
            $('#year_button').linkbutton('enable');
            $('#month_button').linkbutton('disable');
        }else{
            $('#year_button').linkbutton('disable');
            $('#month_button').linkbutton('enable');
        }

        showSpInfo("",1);
        is_show_all = 0;
    }
    else{
        $('#uper_button').linkbutton('disable');
        $('#next_button').linkbutton('disable');
        $('#month_button').linkbutton('disable');
        $('#year_button').linkbutton('disable');
        $('#show_button').linkbutton({text:"分级"});
        spLevel = 1;
        showSpInfo("",0);
        is_show_all = 1;
    }
}

function uperLevel1(){
    if(spLevel != 1){//判断是否为首级

        if(spLevel == 3){
            upper_spNo = upper_spNo.substring(0,4);
        }else if(spLevel ==2){
            upper_spNo = "";
        }

        spLevel--;

        showSpInfo(upper_spNo,spLevel);
    }else{
        $.messager.alert('提示', '当前已为末页！', 'info');
    }
}

function nextLevel1(){

    var row = $('#main_table').datagrid('getSelected');
    if (row) {//判断是否选中行
        if(spLevel != 3){//判断是否为末级
            var length = 0;
            if(spLevel == 1){
                length = 4;
            }else if(spLevel ==2){
                length = 8;
            }
            spLevel++;
            upper_spNo = row.lshszd.spNo.substring(0,length);
            showSpInfo(upper_spNo,spLevel);
        }else{
            $.messager.alert('提示', '当前已为末页！', 'info');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }
}

function allDisplay2(){
    if(is_show_all){
        $('#show_button').linkbutton({text:"全显"});
        $('#uper_button').linkbutton('enable');
        $('#next_button').linkbutton('enable');
        $('#month_button').linkbutton('disable');
        if(ym == '月'){
            $('#year_button').linkbutton('enable');
            $('#month_button').linkbutton('disable');
        }else{
            $('#year_button').linkbutton('disable');
            $('#month_button').linkbutton('enable');
        }

        showItemInfo("",1);
        is_show_all = 0;
    }
    else{
        $('#uper_button').linkbutton('disable');
        $('#next_button').linkbutton('disable');
        $('#month_button').linkbutton('disable');
        $('#year_button').linkbutton('disable');
        $('#show_button').linkbutton({text:"分级"});
        level_ = 1;
        showItemInfo("",0);
        is_show_all = 1;
    }
}

function uperLevel2(){

    if(level_ != 1){//判断是否为首级

        level_--;
        upper_No_=getCatNo(upper_No_,level_-1);

        showItemInfo(upper_No_,level_);
    }else{
        $.messager.alert('提示', '当前已为末页！', 'info');
    }
}

function nextLevel2(){

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
}

function allDisplay3(){
    if(is_show_all){
        $('#show_button').linkbutton({text:"全显"});
        $('#uper_button').linkbutton('enable');
        $('#next_button').linkbutton('enable');
        $('#month_button').linkbutton('disable');

        showSpInfo3("",1);
        is_show_all = 0;
    }
    else{
        $('#uper_button').linkbutton('disable');
        $('#next_button').linkbutton('disable');
        $('#show_button').linkbutton({text:"分级"});
        level_ = 1;
        showSpInfo3("",0);
        is_show_all = 1;
    }
}

function uperLevel3(){

    if(spLevel != 1){//判断是否为首级

        if(spLevel == 3){
            upper_spNo = upper_spNo.substring(0,4);
        }else if(spLevel ==2){
            upper_spNo = "";
        }

        spLevel--;

        showSpInfo3(upper_spNo,spLevel);
    }else{
        $.messager.alert('提示', '当前已为末页！', 'info');
    }
}

function nextLevel3(){

    var row = $('#main_table').datagrid('getSelected');
    if (row) {//判断是否选中行
        if(spLevel != 3){//判断是否为末级
            var length = 0;
            if(spLevel == 1){
                length = 4;
            }else if(spLevel ==2){
                length = 8;
            }
            spLevel++;
            upper_spNo = row.spNo.substring(0,length);
            showSpInfo3(upper_spNo,spLevel);
        }else{
            $.messager.alert('提示', '当前已为末页！', 'info');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }
}

function allDisplay4(){
    if(is_show_all){
        $('#show_button').linkbutton({text:"全显"});
        $('#uper_button').linkbutton('enable');
        $('#next_button').linkbutton('enable');
        showSpInfo4("",1);
        is_show_all = 0;
    }
    else{
        $('#uper_button').linkbutton('disable');
        $('#next_button').linkbutton('disable');
        $('#show_button').linkbutton({text:"分级"});
        level_ = 1;
        showSpInfo4("",0);
        is_show_all = 1;
    }
}

function uperLevel4(){

    if(spLevel != 1){//判断是否为首级

        if(spLevel == 3){
            upper_spNo = upper_spNo.substring(0,4);
        }else if(spLevel ==2){
            upper_spNo = "";
        }

        spLevel--;

        showSpInfo4(upper_spNo,spLevel);
    }else{
        $.messager.alert('提示', '当前已为末页！', 'info');
    }
}

function nextLevel4(){

    var row = $('#main_table').datagrid('getSelected');
    if (row) {//判断是否选中行
        if(spLevel != 3){//判断是否为末级
            var length = 0;
            if(spLevel == 1){
                length = 4;
            }else if(spLevel ==2){
                length = 8;
            }
            spLevel++;
            upper_spNo = row.spNo.substring(0,length);
            showSpInfo4(upper_spNo,spLevel);
        }else{
            $.messager.alert('提示', '当前已为末页！', 'info');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }
}

//显示当前级的核算信息
function showSpInfo(spNo, spLevel){

    var params = {};
    //params.XX必须与Spring Mvc controller中的参数名称一致
    //否则在controller中使用@RequestParam绑定
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.spCatNo = catNo1;
    params.spNo = spNo;
    params.spLevel = spLevel;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance1',
        dataType:"json",
        data: params,
        success: function (result) {
            if(result.code == 100) {
                var spAccountBalanceQueryVos = result.extend.spAccountBalanceQueryVos;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVos); //需要添加具体的data内容
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
    searchAccType = $("input[name=searchFormat]:checked").val();//查询格式（数量账，金额账，S，J）
    searchOption = $("input[name=searchOption]:checked").val();//查询选项（是否包含着账前凭证）



    var isValidItemNo=$('#itemNo_Input').textbox("isValid");
    var isValidCatNo1=$('#catNo1_Input').textbox("isValid");
    var isValidSpNo1=$('#spNo1_Input').textbox("isValid");
    if(!isValidItemNo || !isValidCatNo1 || !isValidSpNo1){
        flag = false;
        return ;
    }

    //第一种查询情况：输入科目编号，核算分类编号，输入核算编号为通配符
    if(itemNo != "...." && spNo1 == "..." && itemNo.indexOf("....") == -1  ){
        searchKind = '1';
        setDatagridByformat(searchAccType,searchKind);
        search1();
        $('#balanceSearch_Win').window('close');
        $('#month_button').linkbutton('disable');
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
    }

    //第二种查询情况：输入科目编号为通配符，输入核算分类编号，核算编号
    if(itemNo == "...." && spNo1 != "..." ){
        searchKind = '2';
        level_ = '1';
        setDatagridByformat(searchAccType,searchKind);
        search2();
        $('#balanceSearch_Win').window('close');
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
    }

    //第三种查询情况：
    if(itemNo.lastIndexOf("....") != -1 && itemNo !="...." && spNo1 == "..." ){
        $('#queryDataType_Win1').window('open');
        searchKind = '3';
        return;
    }

    //第四种查询情况：
    if(itemNo == "...." && spNo1 == "..."){
        $('#queryDataType_Win2').window('open');
        searchKind = '4';
        return;
    }

    $.messager.alert('提示', '请输入正确的查询格式！', 'info');

}

//当所有的判断都正确后执行查询操作
function search1(){
    var params = {};
    //params.XX必须与Spring Mvc controller中的参数名称一致
    //否则在controller中使用@RequestParam绑定
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.spCatNo = catNo1;
    params.spNo = spNo1;
    params.spLevel = spLevel;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance1',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var spAccountBalanceQueryVos = result.extend.spAccountBalanceQueryVos;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVos); //需要添加具体的data内容
                $('#searchTime_label').text(year+'年'+m+'月');
                $('#searchItem_label').text(result.extend.spAccountBalanceQueryVos[0].lskmzd.itemName);
            }else{
            }
        }
    });
}

function search2(){
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.item = level_;
    params.spCatNo = catNo1;
    params.spNo = spNo1;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance2',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var spAccountBalanceQueryVos = result.extend.spAccountBalanceQueryVos;
                level_ = 1;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVos); //需要添加具体的data内容
                $('#searchTime_label').text(year+'年'+m+'月');
                $('#searchItem_label').text(result.extend.spAccountBalanceQueryVos[0].lshszd.spName);
                $('#searchItem_show').text('核算名称：');
            }else{
            }
        }
    });
}

//显示当前级的科目信息
function showItemInfo(itemNo,item){
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.item = item;
    params.spCatNo = catNo1;
    params.spNo = spNo1;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/querySpAccountBalance2',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var spAccountBalanceQueryVos = result.extend.spAccountBalanceQueryVos;
                $('#main_table').datagrid('loadData', spAccountBalanceQueryVos); //需要添加具体的data内容
            }else{
            }
        }
    });
}

function searchClick3(){

    //前台获取
    var columns;

    $.ajax({
        type: "GET",
        url: getRealPath() + '/SpAccountBalanceSearch/getAllColumn3/'+catNo1+'/'+itemNo,
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
    params.spCatNo = catNo1;
    params.spNo = spNo1;
    params.spLevel = spLevel;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType1;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/getAllInfo3',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win1').window('close');

            document.getElementById("mainDisplay").style.visibility = "visible";
            $('#year_button').linkbutton('disable');
            $('#month_button').linkbutton('disable');
        }
    });
}

//显示当前级的核算信息
function showSpInfo3(spNo, spLevel){

    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.spCatNo = catNo1;
    params.spNo = spNo;
    params.spLevel = spLevel;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType1;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/getAllInfo3',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win1').window('close');
            document.getElementById("mainDisplay").style.visibility = "visible";
        }
    });
}

function searchClick4(){

    //前台获取
    var columns;

    $.ajax({
        type: "GET",
        url: getRealPath() + '/SpAccountBalanceSearch/getAllColumn4/'+catNo1,
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

    queryDataType2 = $("input[name=queryDataType2]:checked").val();
    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.spCatNo = catNo1;
    params.spNo = spNo1;
    params.spLevel = spLevel;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType2;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/getAllInfo4',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win2').window('close');

            $('#searchTime_label').text(year+'年'+m+'月');

            document.getElementById("mainDisplay").style.visibility = "visible";

            $('#year_button').linkbutton('disable');
            $('#month_button').linkbutton('disable');
        }
    });
}

//显示当前级的核算信息
function showSpInfo4(spNo, spLevel){

    var params = {};
    params.year = year;
    params.month = m;
    params.itemNo = itemNo;
    params.spCatNo = catNo1;
    params.spNo = spNo;
    params.spLevel = spLevel;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType2;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountBalanceSearch/getAllInfo4',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win2').window('close');
            document.getElementById("mainDisplay").style.visibility = "visible";
        }
    });
}

//帮助窗口，选定科目时事件
function selectItemNoClick() {
    var row = $('#itemTable').datagrid('getSelected');
    if (row) {//判断是否选中行
        if(row.supAcc1 == null || row.supAcc1 == ""){
            $.messager.alert('警告操作！', '该科目不是核算科目！', 'warning');
        }else{
            itemNo = row.itemNo;
            //itemName = row.itemName;
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue',row.itemNo);
            $("#catNo1_Input").textbox('setValue',row.supAcc1);
            $("#spNo1_Input").textbox('setValue','...');
        }
    }else{
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }
}

//帮助窗口，选定科目为通配符时事件
function selectItemNoForWildcardClick() {
    var row = $('#itemTable').datagrid('getSelected');
    if (row) {//判断是否选中行
        if(row.supAcc1 == null ){
            $.messager.alert('警告操作！', '该科目不是核算科目！', 'warning');
        }else if(row.supAcc2 != null ){
            $.messager.alert('警告操作！', '请选择只有一个核算对象的科目！', 'warning');
        }else{
            itemNo = row.itemNo;
            var itemDisplay = getCatNo(itemNo,level_)+"....";
            //itemName = row.itemName;
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue',itemDisplay);
            $("#catNo1_Input").textbox('setValue',row.supAcc1);
            $("#spNo1_Input").textbox('setValue','...');
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
function wildcardForSpNoClick() {

    $("#spNo1_Input").textbox('setValue','...');
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

function  searchItemNoClick() {
    searchHelp("#itemTable","#searchText_ItemNo","itemNo","itemName");
}

function searchCatNo1Click(){
    searchHelp("#catNo1Table","#searchText_CatNo1","catNo","catName");
}

function searchSpNo1Click(){
    searchHelp("#spNo1Table","#searchText_SpNo1","spNo","spName");
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
        url: getRealPath() + '/SpAccountObjectDef/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
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

//获得财务初始日期前一个月的月份，如果初始日期是一月，则使用上年结转金额，type为J则输出金额相关的结转字段名，为Y则输出数量相关
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

//根据查询金额账或数量账，查找种类，设置main_table的格式展示
function setDatagridByformat(searchAccType,searchKind){
    var table = $('#main_table');

    if(searchKind == '1' || searchKind == '3'){
        table.datagrid('hideColumn','itemNo');
        table.datagrid('hideColumn','itemName');
    }
    if(searchKind == '2'){
        table.datagrid('hideColumn','spNo1');
        table.datagrid('hideColumn','spName');
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


