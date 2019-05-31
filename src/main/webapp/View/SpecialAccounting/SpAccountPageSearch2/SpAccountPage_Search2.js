//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = [];//存储科目结构
var precisions_ = [];//存储金额和数量小数精度

var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称

var year = "";
var month = "";
var m = "";
var from = "";
var to = "";
var catNo1 = "";  //核算分类1编号
var spNo1 = "";   //核算对象1编号
var catNo2 = "";  //核算分类1编号
var spNo2 = "";   //核算对象1编号
var searchAccType = "J";//查询格式（数量账，金额账，S，J）
var searchOption = "N";//查询选项（是否包含着账前凭证）
var queryDataType1 = "";//第三种查询，在两个都输入通配符时候，弹出的对话框选择的值
var queryDataType2 = "";//第四种查询，在两个都输入通配符时候，弹出的对话框选择的值

var searchKind = '';//查询种类，总共有4类查询，1、输科目，单位通配符2、科目通配符，输单位 3、两都输 4、两都通配符

var spLevel1ForHelp = "1";
var spLevel2ForHelp = "1";
var uperSpNo1ForHelp = '';//帮助窗口上级核算编号
var uperSpNo2ForHelp = '';//帮助窗口上级核算编号

var winKind = 1;

var current_date_ = "";

var flag = true;


var showupperItemName  = '';
var showupperSpName1 = '';
var showupperSpName2 = '';

$(function() {

    dealSubStru();//保存科目级数
    dealPrecisions();
    showCaption(upper_No_, level_.toString());

    obj = {
        //上级点击事件
        uperLevel_click: function () {
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

            $('#upperItemName_label').text(showupperItemName);
            upper_No_ = getCatNo(upper_No_, level_ - 1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },
        //下级点击事件
        nextLevel_click: function () {
            var row = $('#itemTable').datagrid('getSelected');
            if (row == null) {
                return;
            }//判断是否选中行
            level_++;
            $('#level').val(level_);
            showupperItemName = showupperItemName+row.itemName+"\\";

            $('#upperItemName_label').text(showupperItemName);
            upper_No_ = getCatNo(row.itemNo, level_ - 1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
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
    };

    $('#itemNo_Input').textbox('textbox').bind('dblclick', function () {
        $('#itemNo_HelpWin').window('open');
    })

    $('#catNo1_Input').textbox('textbox').bind('dblclick', function () {
        $('#catNo1_HelpWin').window('open');
    });

    $('#spNo1_Input').textbox('textbox').bind('dblclick', function() {
        var no = $('#catNo1_Input').val();
        if(no != ""){
            spLevel1ForHelp = '1';
            winKind = 1;
            showSpNoForHelp("","1",no);
            $('#spNo1_HelpWin').window('open');
        }else{
            $.messager.alert('提示！', '请输入核算类别1编号！', 'info');
        }
    });
    
    $('#catNo2_Input').textbox('textbox').bind('dblclick', function() {
        $('#catNo2_HelpWin').window('open');
    });

    $('#spNo2_Input').textbox('textbox').bind('dblclick', function() {
        var no = $('#catNo2_Input').val();
        if(no != ""){
            spLevel2ForHelp = '1';
            winKind = 2;
            showSpNoForHelp("","1",no);
            $('#spNo2_HelpWin').window('open');
        }else{
            $.messager.alert('提示！', '请输入核算类别2编号！', 'info');
        }

    });

    $('#spAccountPageSearch_Win').window({
        width: 435,
        height: 430,
        title: '专项核算账页查询',
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

    //帮助窗口数据表格itemTable
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect: true,
        columns: [[
            {
                field: 'itemNo',
                title: '编号',
                width: 120,
                formatter: function (value, row, index) {
                    var length = getNumLength(level_);
                    var no = value.substring(0, length);
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
                formatter: function (value, row, index) {
                    switch (value) {
                        case 1:
                            return "是";
                        case 0:
                            return "否";
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
        width: '100%',
        height: '100%',
        fit: true,
        fitColumns: false,
        singleSelect: true,
        url: getRealPath() + '/SpAccountCategory/getSpAccountCategory',
        columns: [[
            {
                field: 'catNo',
                title: '编号',
                align: 'center',
                width: 120
            },
            {
                field: 'catName',
                title: '名称',
                align: 'center',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                align: 'center',
                width: 80,

            },
            {
                field: 'spLevel',
                title: '级数',
                align: 'center',
                width: 80
            },

        ]],
        toolbar: '#tbCatNo1_helpWin',
    });


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
        width: '100%',
        height: '100%',
        fit: true,
        fitColumns: false,
        singleSelect: true,
        url: getRealPath() + '/SpAccountCategory/getSpAccountCategory',
        columns: [[
            {
                field: 'catNo',
                title: '编号',
                align: 'center',
                width: 120
            },
            {
                field: 'catName',
                title: '名称',
                align: 'center',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                align: 'center',
                width: 80,

            },
            {
                field: 'spLevel',
                title: '级数',
                align: 'center',
                width: 80
            },

        ]],
        toolbar: '#tbCatNo2_helpWin',
    });



    //帮助窗口
    $('#spNo2_HelpWin').window({
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


    //查询结果显示表
    $('#main_table').datagrid({
        width: '100%',
        multiSort: false,
        toolbar: '#button_group',
        singleSelect: true,
        showFooter: true,
        columns: [
            [{
                field: 'year',
                title: '年',
                width: '8%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'voucherNo',
                title: '凭证号',
                width: '10%',
                rowspan: 2,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.lspzk1['voucherNo'];
                }
            }, {
                field: 'summary',
                title: '摘要',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.lspzk1['summary'];
                }
            }, {
                field: 'titleDebit',
                title: '借方',
                width: '12%',
                colspan: 2,
                halign:'center',
                align: 'right',
            }, {
                field: 'debit',
                title: '借方',
                width: '12%',
                rowspan: 2,
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.lspzk1['bkpDirection'] == 'J')
                        return thousandFormatter(row.lspzk1['money']);
                }
            }, {
                field: 'titleCredit',
                title: '贷方',
                width: '12%',
                colspan: 2,
                halign:'center',
                align: 'right',
            }, {
                field: 'credit',
                title: '贷方',
                rowspan: 2,
                width: '12%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    if (row.lspzk1['bkpDirection'] == 'D')
                        return thousandFormatter(row.lspzk1['money']);
                }
            }, {
                field: 'bkpDirection',
                title: '借贷方向',
                rowspan: 2,
                width: '4%',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.money > 0) {
                        return '借';
                    }
                    else if (row.money < 0) {
                        return '贷';
                    }
                    else {
                        return '平';
                    }
                }
            }, {
                field: 'titleBalance',
                title: '余额',
                colspan: 3,
                width: '12%',
                halign:'center',
                align: 'right',
            }, {
                field: 'balance',
                title: '余额',
                rowspan: 2,
                width: '12%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    return thousandFormatter(row.money);
                }
            }], [{
                field: 'month',
                title: '月',
                width: '2%',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.lspzk1['inputDate'] != null)
                        return row.lspzk1['inputDate'].substr(4, 2);
                }
            }, {
                field: 'day',
                title: '日',
                width: '2%',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.lspzk1['inputDate'] != null)
                        return row.lspzk1['inputDate'].substr(6, 2);
                }
            }, {
                field: 'debitQty',
                title: '数量',
                width: '10%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.lspzk1['bkpDirection'] == 'J')
                        return row.lspzk1['qty'];
                }
            }, {
                field: 'debitMoney',
                title: '金额',
                width: '8%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    if (row.lspzk1['bkpDirection'] == 'J')
                        return thousandFormatter(row.lspzk1['money']);
                }
            }, {
                field: 'creditQty',
                title: '数量',
                width: '8%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.lspzk1['bkpDirection'] == 'D')
                        return row.lspzk1['qty'];
                }
            }, {
                field: 'creditMoney',
                title: '金额',
                width: '8%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    if (row.lspzk1['bkpDirection'] == 'D')
                        return thousandFormatter(row.lspzk1['money']);
                }
            }, {
                field: 'leftQty',
                title: '数量',
                width: '8%',
                halign:'center',
                align: 'right',
                hidden: true,
                formatter: function (value, row, index) {
                    if (row.qty != null)
                        return row.qty;
                }
            }, {
                field: 'leftPrice',
                title: '单价',
                width: '8%',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.money != null && row.qty != null)
                        return row.money / row.qty;
                }
            }, {
                field: 'leftMoney',
                title: '金额',
                width: '8%',
                halign:'center',
                align: 'right',
                formatter: function (value, row, index) {
                    if (row.money != null)
                        return thousandFormatter(row.money);
                }
            }]
        ],
        toolbar: '#tbSearchAccountPage',
    });
});

//输入完查询条件，点击查询时事件
function searchClick(){
    itemNo = $('#itemNo_Input').val();
    catNo1 = $('#catNo1_Input').val();
    spNo1 = $('#spNo1_Input').val();
    catNo2 = $('#catNo2_Input').val();
    spNo2 = $('#spNo2_Input').val();
    year = $('#year_Input').val();
    var month_from = $('#monthFrom_Input').val() < 10 ? '0' + $('#monthFrom_Input').val() : $('#monthFrom_Input').val();
    var month_to =$('#monthTo_Input').val() < 10 ? '0' + $('#monthTo_Input').val() : $('#monthTo_Input').val();
    var day_from = '01';
    var day_to = getMonthDayCount(year, month_to);
    from = year + month_from + day_from;
    to = year + month_to + day_to;
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

    //第一种查询情况：输入科目编号，核算分类编号，输入核算编号为通配符
    if(itemNo != "...." && spNo1 != "..." && spNo2 != "..." && itemNo.indexOf("....") == -1){
        querySpAccountPage2();
        $('#spAccountPageSearch_Win').window('close');
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
    }
    else{
        queryMultiCol(from, to, itemNo, catNo1);
        $('#spAccountPageSearch_Win').window('close');
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
    }

}

//当所有的判断都正确后执行查询操作
function querySpAccountPage2(){
    var params = {};
    //params.XX必须与Spring Mvc controller中的参数名称一致
    //否则在controller中使用@RequestParam绑定
    params.from = from;
    params.to = to;
    params.itemNo = itemNo;
    params.spCatNo1 = catNo1;
    params.spNo1 = spNo1;
    params.spCatNo2 = catNo2;
    params.spNo2 = spNo2;

    $.ajax({
        type:'post',
        url: getRealPath() + '/SpAccountPageSearch/querySpAccountPage2',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var lspzk1VoForSearches = result.extend.lspzk1VoForSearches;
                if(lspzk1VoForSearches[0] != null && lspzk1VoForSearches[0].qty == null){
                    setDatagridByformat('S');
                }
                else{
                    setDatagridByformat('J');
                }
                $('#main_table').datagrid('loadData', lspzk1VoForSearches); //需要添加具体的data内容

                headInfoShow(result.extend.headInfo);
            }else{

            }
        }
    });
}
function setDatagridByformat(actype){
    var table = $('#main_table');
    $('#main_table').datagrid('getColumnOption','yearColumn').title = year;
    if(actype == 'J'){
        table.datagrid('hideColumn','titleCredit');
        table.datagrid('showColumn','credit');
        table.datagrid('hideColumn','titleDebit');
        table.datagrid('showColumn','debit');
        table.datagrid('hideColumn','titleBalance');
        table.datagrid('showColumn','balance');

        table.datagrid('hideColumn','creditQty');
        table.datagrid('hideColumn','creditMoney');
        table.datagrid('hideColumn','debitQty');
        table.datagrid('hideColumn','debitMoney');
        table.datagrid('hideColumn','leftQty');
        table.datagrid('hideColumn','leftMoney');
        table.datagrid('hideColumn','leftPrice');
    }

    if(actype == 'S'){
        table.datagrid('showColumn','titleCredit');
        table.datagrid('hideColumn','credit');
        table.datagrid('showColumn','titleDebit');
        table.datagrid('hideColumn','debit');
        table.datagrid('showColumn','titleBalance');
        table.datagrid('hideColumn','balance');

        table.datagrid('showColumn','creditQty');
        table.datagrid('showColumn','creditMoney');
        table.datagrid('showColumn','debitQty');
        table.datagrid('showColumn','debitMoney');
        table.datagrid('showColumn','leftQty');
        table.datagrid('showColumn','leftMoney');
        table.datagrid('showColumn','leftPrice');
    }

    table.datagrid();
}


function queryMultiCol(from, to, itemNo, catNo1, catNo2){

    //前台获取
    var columns1, columns2;
    var params1 = {};
    params1.id = itemNo;
    params1.catNo1 = catNo1;
    params1.catNo2 = catNo2;

    $.ajax({
        type: "POST",
        url: getRealPath() + '/SpAccountPageSearch/getAllColumn',
        dataType: "json",
        async: false,//同步请求
        data: params1,
        success: function(data){
            columns1 = data.columns1;
            columns2 = data.columns2;
            $("#main_table").datagrid({
                columns : [columns1, columns2]
            });
            $('#main_table').datagrid('getColumnOption','year').title = year;
            $('#main_table').datagrid();
        }
    });

    var params2 = {};
    params2.from = from;
    params2.to = to;
    params2.itemNo = itemNo;
    params2.spCatNo1 = catNo1;
    params2.spNo1 = spNo1;
    params2.spCatNo2 = catNo2;
    params2.spNo2 = spNo2;
    params2.searchOption = 'J';

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/SpAccountPageSearch/querySpAccountPageDefault',
        data: params2,
        dataType:"json",
        success:function(data){
            $('#main_table').datagrid('loadData', data.extend.result); //需要添加具体的data内容

            headInfoShow(data.extend.headInfo);
        }

    });


}

//当输入科目编号和单位编号都不是通配符时判断科目编号和单位编号是否对应关联了。
function judgeCompanyConnectItem(){
    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsPageSearch/judgeCompanyConnectItem/itemNo'+itemNo+'/companyNo'+companyNo,
        contentType:'application/json;charset=utf-8',
        success:function(result){
            if(result.code == 100){
                return  true;
            }else{
                $.messager.alert('提示', '输入的科目非单位所对应的往来科目！请重新输入！', 'info');
                return false;
            }
        }
    });
}

//判断输入的单位编号是否正确
function judgeCompanyNoInput(){
    companyNo = $('#companyNo_Input').val();
    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsPageSearch/judgeContactsCompanyNo/companyNo'+companyNo,
        contentType:'application/json;charset=utf-8',
        data: data,
        success:function(result){
            var lswldw  = result.lswldw;
            if(lswldw != null){
                return true;
            }else{
                $.messager.alert('提示', '该单位没有对应往来科目！请重新选择单位', 'info');
                return false;
            }
        }
    });

}

//判断输入的科目编号是否正确
function judgeItemNoInput(){
    itemNo = $('#itemNo_Input').val();
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/ContactsInitialization/getItemByIdAndCompany/' + itemNo ,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.LswljeOrLswlsl.lskmzdVoForContacts;  //获取科目字典信息，加载在页面最上面进行显示
                if(data.exchang == 0){
                    $.messager.alert('警告操作！', '该科目不是往来科目！', 'warning');
                }else{  //当在初始化对话框输入或选择的编号为往来科目时，进行数据加载

                    $('#helpWin').window('close');
                    $('#box').dialog('close');
                    document.getElementById("initializtion").style.visibility = "visible";

                    $("#itemNo").textbox('setValue',itemNo);
                    $("#itemName").textbox('setValue',itemName);
                    $('#itemNo').textbox('textbox').attr('readonly',true);  //设置输入框为禁用
                    $('#itemName').textbox('textbox').attr('readonly',true);  //设置输入框为禁用

                }
                //$('#initTable').datagrid('loadData', data);
            }else{
                $.messager.alert('警告操作！', '科目不存在！', 'warning');
            }
        }
    });
}

//帮助窗口，选定科目时事件
function selectItemNoClick(type) {

    var row = $('#itemTable').datagrid("getSelected");
    if(row.supAcc1 == null || row.supAcc1 == ""){
        $.messager.alert('警告操作！', '该科目不是核算科目！', 'warning');
    }else{
        //选定指定科目
        if(type == 1) {
            itemNo = row.itemNo;
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue', row.itemNo);
            $("#catNo1_Input").textbox('setValue', row.supAcc1);
            $("#catNo2_Input").textbox('setValue', row.supAcc2);
            //$("#spNo1_Input").textbox('setValue', '...');
        }
        //帮助窗口，选定科目为父级时事件（即保留父级编号，用通配符省略剩余编号）
        else if(type == 2){
            itemNo = getCatNo(row.itemNo, level_) + '....';
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue', itemNo);
            $("#catNo1_Input").textbox('setValue', row.supAcc1);
            $("#catNo2_Input").textbox('setValue', row.supAcc2);
        }
        //帮助窗口，选定省略或不选择科目
        else{
            itemNo = '....';
            $('#itemNo_HelpWin').window('close');
            $("#itemNo_Input").textbox('setValue', itemNo);
        }
    }
}

//帮助窗口，选定分类编号时事件
function selectcatNo1Click(){
    var row = $('#catNo1Table').datagrid("getSelected");
    catNo1 = row.catNo;
    $('#catNo1_HelpWin').window('close');
    $("#catNo1_Input").textbox('setValue',row.catNo);
    $("#spNo1_Input").textbox('setValue','');
}

//帮助窗口，选定分类编号时事件
function selectcatNo2Click(){
    var row = $('#catNo2Table').datagrid("getSelected");
    catNo1 = row.catNo;
    $('#catNo2_HelpWin').window('close');
    $("#catNo2_Input").textbox('setValue',row.catNo);
    $("#spNo2_Input").textbox('setValue','');
}

//帮助窗口，选定核算编号时事件
function selectSpNo1Click(){
    var row = $('#spNo1Table').datagrid("getSelected");
    spNo1 = row.spNo;
    $('#spNo1_HelpWin').window('close');
    $("#spNo1_Input").textbox('setValue',row.spNo);
}

//帮助窗口，省略科目编号
function noSelectSpNo1Click(){
    spNo1 = '...';
    $('#spNo1_HelpWin').window('close');
    $("#spNo1_Input").textbox('setValue',row.spNo);
}

//帮助窗口，选定核算编号时事件
function selectSpNo2Click(){
    var row = $('#spNo2Table').datagrid("getSelected");
    spNo1 = row.spNo;
    $('#spNo2_HelpWin').window('close');
    $("#spNo2_Input").textbox('setValue',row.spNo);
}

//帮助窗口，省略科目编号
function noSelectSpNo2Click(){
    spNo1 = '...';
    $('#spNo2_HelpWin').window('close');
    $("#spNo2_Input").textbox('setValue',row.spNo);
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
            }
        }
    });
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
                }else if(winKind ==2){
                    $('#spNo2Table').datagrid('loadData', lshszds); //需要添加具体的data内容
                }
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

//检查指定年月的天数总数
function getMonthDayCount(year, month){
    var d = new Date(year, month, 0);
    return d.getDate();
}

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}

//获取金额和数量的小数精度
function dealPrecisions() {
    var money_prec = 2;
    var quan_prec = 2;
    precisions_.push(money_prec);
    precisions_.push(quan_prec);
}



//获取宽度
function fixWidth(percent)
{
    return document.body.clientWidth * percent ; //这里你可以自己做调整
}

//获取高度
function fixHeight(percent)
{
    return document.body.clientHeight -70 ; //这里你可以自己做调整
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

function headInfoShow(headInfo){
    $("#head_info_area").append("科目：");
    if(headInfo.itemName != null){
        $("#head_info_area").append(headInfo.itemName);
    }
    if(headInfo.spName1 != null){
        $("#head_info_area").append('/'+headInfo.spName1);
    }
    if(headInfo.spName2 != null){
        $("#head_info_area").append('/'+headInfo.spName2);
    }
    if(headInfo.head1Key != null && headInfo.head1Key != ""){
        $("#head_info_area").append('  '+headInfo.head1Key + ':' + headInfo.head1Value+'  ');
    }
    if(headInfo.head2Key != null && headInfo.head2Key != ""){
        $("#head_info_area").append('  '+headInfo.head2Key + ':' + headInfo.head2Value+'  ');
    }
    if(headInfo.head3Key != null && headInfo.head3Key != ""){
        $("#head_info_area").append('  '+headInfo.head3Key + ':' + headInfo.head3Value+'  ');
    }
    if(headInfo.head4Key != null && headInfo.head4Key != ""){
        $("#head_info_area").append('  '+headInfo.head4Key + ':' + headInfo.head4Value+'  ');
    }
    if(headInfo.head5Key != null && headInfo.head5Key != ""){
        $("#head_info_area").append('  '+headInfo.head5Key + ':' + headInfo.head5Value+'  ');
    }
    if(headInfo.head6Key != null && headInfo.head6Key != ""){
        $("#head_info_area").append('  '+headInfo.head6Key + ':' + headInfo.head6Value+'  ');
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
