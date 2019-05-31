/**
 * Created by ChenZH on 2018/7/5.
 */
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = [];//存储科目结构
var current_date_ = "";

$(function () {
    dealConfigs();
    showCaption(upper_No_,level_.toString());

    var actype = 'money';
    var sql = [];

    $('#main_window').css("display", "none");

    $('#yes_button').click(function () {
        $('#search_window').dialog('close');
        $('#main_window').css("display", "block");

        actype = $("input[name='actype']:checked").val();

        setDatagridByformat(actype);

        queryDetail(current_date_.substr(0, 6), $('#item').val(), 0);

        $('#s_year').val($('#year').val());
        $('#s_month').val($('#month').val());
        $('#s_day').val($('#day').val());

        $('#main_table').datagrid('getColumnOption','debitMoneyAcm').title = $('#year').val();
    });

    obj = {
        selectItem: function () {
            showCaption(upper_No_,level_);
            $('#help_window').window('open');
        },

        //帮助窗口上级点击事件
        uperLevel_click : function () {
            if(level_==1){return;}
            level_--;
            $('#level').val(level_);
            upper_No_=getCatNo(upper_No_,level_-1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },
        //帮助窗口下级点击事件
        nextLevel_click : function () {
            var row = $('#itemTable').datagrid('getSelected');
            if(row==null){return;}//判断是否选中行
            level_++;
            $('#level').val(level_);
            upper_No_ = getCatNo(row.itemNo,level_-1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },
        //选定科目字典
        selectClick : function () {
            var row = $('#itemTable').datagrid("getSelected");
            $('#help_window').window('close');
            $('#item').val(row.itemNo);
            $('#s_itemNo').val(row.itemNo);
            $('#s_itemName').val(row.itemName);
        },

        condSearch: function () {
            $('#cond_window').window('open');
        },

        voucherSearch: function () {
            if($('#main_table').datagrid('getSelected') != null){
                var voucher_no = $('#main_table').datagrid('getSelected').voucherNo;
                var input_date = $('#main_table').datagrid('getSelected').inputDate;
                showVoucherTable(voucher_no, input_date);
                $('#voucher_window').window('open');
            }
        },


        equal: function () {
            $('#middle_box').textbox('setValue', '=');
        },
        greater: function () {
            $('#middle_box').textbox('setValue', '>');
        },
        less: function () {
            $('#middle_box').textbox('setValue', '<');
        },
        contain: function () {
            $('#middle_box').textbox('setValue', '包含');
        },
        notEqual: function () {
            $('#middle_box').textbox('setValue', '!=');
            $('#middle_box').textbox('setText', '≠');
        },
        notGreater: function () {
            $('#middle_box').textbox('setValue', '<=');
            $('#middle_box').textbox('setText', '≤');
        },
        notLess: function () {
            $('#middle_box').textbox('setValue', '>=');
            $('#middle_box').textbox('setText', '≥');
        },
        and: function () {
            sql.push('and');
            obj.showSql();
        },
        or: function () {
            sql.push('or');
            obj.showSql();
        },
        not: function () {
            sql.push('not');
            obj.showSql();
        },
        left: function () {
            sql.push('( ');
            obj.showSql();
        },
        right: function () {
            sql.push(' ) ');
            obj.showSql();
        },
        backspace: function () {
            sql.pop();
            obj.showSql();

        },
        reselect: function () {
            $('#left_box').textbox('clear');
            $('#middle_box').textbox('clear');
            $('#right_box').textbox('clear');
        },
        confirm: function () {

            if($('#left_box').textbox('getValue') == 'debitMoney'){
                sql.push('(','bkpDirection','=','\'J\'','and','money');
            }
            else if($('#left_box').textbox('getValue') == 'creditMoney'){
                sql.push('(','bkpDirection','=','\'D\'','and','money');
            }
            else if($('#left_box').textbox('getValue') == 'month'){
                sql.push('substring(inputDate,5,2)');
            }
            else if($('#left_box').textbox('getValue') == 'day'){
                sql.push('substring(inputDate,7,2)');
            }
            else{
                sql.push($('#left_box').textbox('getValue'));
            }

            if($('#middle_box').textbox('getValue') == '包含'){
                sql.push('like','%',$('#right_box').textbox('getValue'),'%');
            }
            else{
                sql.push($('#middle_box').textbox('getValue'),'\'' + $('#right_box').textbox('getValue') + '\'');
            }

            if($('#left_box').textbox('getValue') == 'debitMoney' || $('#left_box').textbox('getValue') == 'creditMoney'){
                sql.push(')');
            }

            $('#left_box').textbox('clear');
            $('#middle_box').textbox('clear');
            $('#right_box').textbox('clear');

            obj.showSql();
        },
        query: function () {
            var sql_str = sql.join(' ');
            queryDetail(current_date_.substr(0, 6), $('#item').val(), sql_str);
        },

        showSql: function () {
            var total = '';
            for(var i = 0; i < sql.length; i++){
                var print = sql[i];
                switch(print){
                    case '%':
                        print = '';
                        break;
                    case 'like':
                        print = '包含';
                        break;
                    case 'and':
                        print = '并且';
                        break;
                    case 'or':
                        print = '或';
                        break;
                    case 'not':
                        print = '和';
                        break;
                    case '!=':
                        print = '≠';
                        break;
                    case '>=':
                        print = '≥';
                        break;
                    case '<=':
                        print = '≤';
                        break;

                    case 'substring(inputDate,5,2)':
                        print = '月';
                        break;
                    case 'substring(inputDate,7,2)':
                        print = '日';
                        break;
                    case 'voucherNo':
                        print = '凭证编号';
                        break;
                    case 'summary':
                        print = '摘要';
                        break;
                    case 'debitMoney':
                        print = '借方金额';
                        break;
                    case 'creditMoney':
                        print = '贷方金额';
                        break;
                    case 'balance':
                        print = '当前余额';
                        break;
                }
                total = total + print + " ";
            }
            $('#sql_box').textbox('setValue', total);
        }
    };

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
                formatter: function (value,row,index) {
                    return row.lspzk1['voucherNo'];
                }
            }, {
                field: 'summary',
                title: '摘要',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['summary'];
                }
            }, {
                field: 'titleDebit',
                title: '借方',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'debit',
                title: '借方',
                width: '12%',
                rowspan: 2,
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lspzk1['bkpDirection'] == 'J')
                        return row.lspzk1['money'];
                }
            }, {
                field: 'titleCredit',
                title: '贷方',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'credit',
                title: '贷方',
                rowspan: 2,
                width: '12%',
                align: 'center',
                formatter: function(value, row, index){
                    if(row.lspzk1['bkpDirection'] == 'D')
                        return row.lspzk1['money'];
                }
            }, {
                field: 'bkpDirection',
                title: '借贷方向',
                rowspan: 2,
                width: '4%',
                align: 'center',
                formatter: function(value, row, index){
                    if(row.money > 0){
                        return '借';
                    }
                    else if(row.money < 0){
                        return '贷';
                    }
                    else{
                        return '平';
                    }
                }
            }, {
                field: 'titleBalance',
                title: '余额',
                colspan: 3,
                width: '12%',
                align: 'center'
            }, {
                field: 'balance',
                title: '余额',
                rowspan: 2,
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    return row.money;
                }
            }], [{
                field: 'month',
                title: '月',
                width: '2%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lspzk1['inputDate'] != null)
                        return row.lspzk1['inputDate'].substr(4, 2);
                }
            }, {
                field: 'day',
                title: '日',
                width: '2%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lspzk1['inputDate'] != null)
                        return row.lspzk1['inputDate'].substr(6, 2);
                }
            }, {
                field: 'debitQty',
                title: '数量',
                width: '10%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lspzk1['bkpDirection'] == 'J')
                        return row.lspzk1['qty'];
                }
            }, {
                field: 'debitMoney',
                title: '金额',
                width: '8%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lspzk1['bkpDirection'] == 'J')
                        return row.lspzk1['money'];
                }
            }, {
                field: 'creditQty',
                title: '数量',
                width: '8%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lspzk1['bkpDirection'] == 'D')
                        return row.lspzk1['qty'];
                }
            }, {
                field: 'creditMoney',
                title: '金额',
                width: '8%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lspzk1['bkpDirection'] == 'D')
                        return row.lspzk1['money'];
                }
            }, {
                field: 'leftQty',
                title: '数量',
                width: '8%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.qty != null)
                        return row.qty;
                }
            }, {
                field: 'leftPrice',
                title: '单价',
                width: '8%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.money != null && row.qty != null)
                        return row.money / row.qty;
                }
            }, {
                field: 'leftMoney',
                title: '金额',
                width: '8%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.money != null)
                        return row.money;
                }
            }]
        ]
    });

    //帮助窗口数据表格
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        //双击进入下一级事件
        onDblClickRow : function (rowIndex, rowData) {
            obj.nextLevel_click();
        },
        toolbar: '#tb_helpWin',
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
            }
        ]]
    });

    $('#voucher_table').datagrid({
        toolbar: '#voucher_button',
        columns: [[
            {
                field: 'entryNo',
                title: '分录编号',
                rowspan: 2,
                align: 'center',
                hidden: true
            },
            {
                field: 'itemNo',
                title: '科目编号',
                rowspan: 2,
                align: 'center',
                hidden: true
            },
            {
                field: 'accType',
                title: '账式',
                rowspan: 2,
                align: 'center',
                hidden: true
            },
            {
                field: 'journal',
                title: '日记账',
                rowspan: 2,
                align: 'center',
                hidden: true
            },
            {
                field: 'supAcc1',
                title: '专项核算1',
                rowspan: 2,
                align: 'center',
                hidden: true
            },
            {
                field: 'supAcc2',
                title: '专项核算2',
                rowspan: 2,
                align: 'center',
                hidden: true
            },
            {
                field: 'level_1',
                title: '总账科目',
                width: '16%',
                rowspan: 2,
                align: 'center'
            },
            {
                field: 'level_2',
                title: '二级科目',
                width: '16%',
                rowspan: 2,
                align: 'center'
            },
            {
                field: 'level_3',
                title: '三级科目',
                width: '16%',
                rowspan: 2,
                align: 'center'
            },
            {
                field: 'level_detail',
                title: '明细科目',
                width: '16%',
                rowspan: 2,
                align: 'center'
            },
            {
                field: 'money',
                title: '金额',
                width: '32%',
                colspan: 4,
                align: 'center'
            }],[
            {
                field: 'debitMoney',
                title: '借方金额',
                width: '16%',
                align: 'center'
            },
            {
                field: 'debitQty',
                title: '借方数量',
                align: 'center',
                hidden: true
            },
            {
                field: 'creditMoney',
                title: '贷方金额',
                width: '16%',
                align: 'center'
            },
            {
                field: 'creditQty',
                title: '贷方数量',
                align: 'center',
                hidden: true
            }
        ]]
    });


    //凭证查询窗口
    $('#voucher_window').window({
        title: '凭证查询',
        width: 1024,
        height: 768,
        closed: true,
        resizable: false,
        minimizable: false,
        maximizable: false,
        collapsible: false
    });

    $('#sql_box').textbox({
        width: 415,
        height: 140,
        multiline: true,
        editable: false
    });

    $('#cond_window').window({
        width: 470,
        height: 550,
        padding: 10,
        title: '条件查询',
        icon: 'icon-search',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
        modal: true
    });

    $('#cond_list').datalist({
        checkbox: false,
        onClickRow: function (index, row) {
            $('#left_box').textbox('setValue', row.value);
            $('#left_box').textbox('setText', row.text);
        }
    });
    
    //帮助窗口
    $('#help_window').window({
        width: 480,
        height: 500,
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
        modal: true
    });
    
    $('#search_window').window({
        width: 350,
        height: 220,
        title: '明细账查询',
        iconCls:'icon-search',
        collapsible: false,
        minimizable: false,
        closeable: false,
        resizable: true,
        maximizable: false,
        modal: true
    })
});

function dealConfigs(){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/getConfigsForSearch/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                //处理科目结构
                var stru = result.extend.sub_stru.split("");
                for(var i=0;i<stru.length;i++){
                    sub_stru_[i]=stru[i];
                }
                //处理财务日期
                current_date_ = result.extend.current_date;
                $('#year').numberbox('setValue', parseInt(current_date_.substr(0, 4)));
                $('#month').numberbox('setValue', parseInt(current_date_.substr(4, 2)));
                $('#day').numberbox('setValue', parseInt(current_date_.substr(6, 2)));
            }
        }
    });
}

//初始范围查询
function queryDetail(year_month, itemNo, sql){
    var new_sql = sql;
    if(sql == 0){
        new_sql = '0';
    }

    var data = {
        "sql": new_sql
    };
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/queryDetailWithSql/' + year_month + '/' + itemNo,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzk1VoForSearches;
                $('#main_table').datagrid('loadData', data);
                //reloadFooterTotal();
            }
            else {
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
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
        url: getRealPath() + '/CaptionOfAccount/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lskmzdList;
                $('#itemTable').datagrid('loadData', data);
            }
        }
    });
}

function getFullMonth(number){
    return number >= 10 ? number.toString() : '0' + number.toString();
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

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}

//计算补零长度
function getZeroLength(level) {
    var length = 0;
    for(var i=level; i<sub_stru_.length; i++){
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

function setDatagridByformat(actype){
    var table = $('#main_table');

    if(actype == 'money'){
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

    if(actype == 'qty'){
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

//显示当前凭证的分录
function showVoucherTable(voucher_no, input_date){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountingVouchers/queryVouchersForDatagrid/' + voucher_no + '/' + input_date,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzk1QueryVoList;
                $('#voucher_table').datagrid('loadData', data);
                $('#atta').val($('#voucher_table').datagrid('getRows').length);
                if(data != null){
                    $('#year_month_day').val(parseInt(data[0]['inputDate'].substr(0,4)) + '年' + parseInt(data[0]['inputDate'].substr(4,2)) + '月' + parseInt(data[0]['inputDate'].substr(6,2)) + '日'); 
                    $('#voucher_no').val(data[0]['voucherNo']);
                    $('#summary').val(data[0]['summary']);
                    $('#auditor').val(data[0]['auditor']);
                    $('#preact_doc').val(data[0]['preActDoc']);
                }
                $('#voucher_table').datagrid('sort', 'entryNo');
                //reloadFooterTotal();
            }
        }
    });
}

//获取路径
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
// }