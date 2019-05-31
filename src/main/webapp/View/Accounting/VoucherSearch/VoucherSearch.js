/**
 * Created by ChenZH on 2018/7/6.
 */
var current_date_ = '';

$(function () {
    dealConfigs();

    var sql = [];
    var main_detail = 'main';
    var detail = {'feature': false, 'contact': false, 'spacc': false, 'original': false};

    $('#main_window').css("display", "none");

    $('#main').click(function(){
        main_detail = 'main';

        $('#feature').attr("disabled",true);
        $('#contact').attr("disabled",true);
        $('#spacc').attr("disabled",true);
        $('#original').attr("disabled",true);

        $('#feature').attr("checked",false);
        $('#contact').attr("checked",false);
        $('#spacc').attr("checked",false);
        $('#original').attr("checked",false);
    });

    $('#detail').click(function(){
        main_detail = 'detail';

        $('#feature').attr("disabled",false);
        $('#contact').attr("disabled",false);
        $('#spacc').attr("disabled",false);
        $('#original').attr("disabled",false);
    });

    $('#yes_button').click(function () {
        $('#search_window').dialog('close');
        $('#main_window').css("display", "block");

        if($('#feature').attr("checked")) detail.feature = true;
        if($('#contact').attr("checked")) detail.contact = true;
        if($('#spacc').attr("checked")) detail.spacc = true;
        if($('#original').attr("checked")) detail.original = true;

        var year_month = getYearMonth();
        var is_detail = main_detail == 'detail' ? 1 : 0;//'from Lspzk1 where inputDate like \'' + year_month + '%\''
        queryOriginalVoucher(is_detail, year_month, 0);
        //'from%20Lspzk1%20where%20inputDate%20like%20\'' + year_month + '%\''
        setDatagridByformat(main_detail, detail);

        $('#s_year').val($('#year').val());
        $('#s_month').val($('#month').val());
    });

    obj = {
        condSearch: function () {
            $('#cond_window').window('open');
        },

        voucherSearch: function () {
            if($('#main_table').datagrid('getSelected') != null){
                var row = $('#main_table').datagrid('getSelected');
                var voucher_no = row.lspzk1.voucherNo;
                var input_date = row.lspzk1.inputDate;
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
            else{
                sql.push($('#left_box').textbox('getValue'));
            }

            if($('#middle_box').textbox('getValue') == '包含'){
                sql.push('like','\'%'+$('#right_box').textbox('getValue')+'%\'');
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
            var year_month = getYearMonth();
            var is_detail = main_detail == 'detail' ? 1 : 0;
            var sql_str = sql.join(' ');
            queryOriginalVoucher(is_detail, year_month, sql_str);
        },

        showSql: function () {
            var total = '';
            for(var i = 0; i < sql.length; i++){
                var print = sql[i];
                switch(print){
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

                    case 'voucherNo':
                        print = '凭证编号';
                        break;
                    case 'inputNo':
                        print = '凭证日期';
                        break;
                    case 'itemNo':
                        print = '科目编号';
                        break;
                    case 'money':
                        print = '金额';
                        break;
                    case 'bkpDirection':
                        print = '借贷方向';
                        break;
                    case '\'J\'':
                        print = '借方';
                        break;
                    case '\'D\'':
                        print = '贷方';
                        break;
                    case 'debitMoney':
                        print = '借方金额';
                        break;
                    case 'creditMoney':
                        print = '贷方金额';
                        break;
                    case 'summary':
                        print = '摘要';
                        break;
                    case 'preActDoc':
                        print = '制单姓名';
                        break;
                    case 'auditorName':
                        print = '复核姓名';
                        break;
                    case 'DirectorName':
                        print = '记账姓名';
                        break;
                }
                total = total + print + " ";
            }
            $('#sql_box').textbox('setValue', total);
        }
    };

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

    $('#search_window').window({
        width: 330,
        height: 220,
        padding: 10,
        title: '凭证流水查询',
        iconCls:'icon-search',
        collapsible: false,
        minimizable: false,
        closeable: false,
        resizable: true,
        maximizable: false,
        modal: true
    });

    $('#cond_list').datalist({
        height: '100%',
        checkbox: false,
        onClickRow: function (index, row) {
            $('#left_box').textbox('setValue', row.value);
            $('#left_box').textbox('setText', row.text);
        }
    });

    $('#main_table').datagrid({
        width: '100%',
        multiSort: false,
        toolbar: '#button_group',
        singleSelect: true,
        showFooter: true,
        columns: [
            [{
                field: 'voucherNo',
                title: '凭证编号',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['voucherNo'];
                }
            }, {
                field: 'entryNo',
                title: '分录编号',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['entryNo'];
                }
            }, {
                field: 'inputDate',
                title: '日期',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['inputDate'];
                }
            }, {
                field: 'summary',
                title: '摘要',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['summary'];
                }
            }, {
                field: 'itemNo',
                title: '科目编号',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['itemNo'];
                }
            }, {
                field: 'companyNo',
                title: '单位编号',
                width: '6%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['companyNo'];
                }
            }, {
                field: 'spNo1',
                title: '核算编号1',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    if(row.lshspz){
                        return row.lshspz['spNo1'];
                    }
                }
            }, {
                field: 'spNo2',
                title: '核算编号2',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    if (row.lshspz) {
                        return row.lshspz['spNo2'];
                    }
                }
            }, {
                field: 'money',
                title: '金额',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.money;
                }
            }, {
                field: 'debitMoney',
                title: '借方金额',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    if(row.lspzk1['bkpDirection'] == 'J'){
                        if(row.lsyspz){
                            return row.lsyspz['money'];
                        }
                        if(row.lshspz){
                            return row.lshspz['money'];
                        }
                        return row.lspzk1['money'];
                    }
                }
            }, {
                field: 'creditMoney',
                title: '贷方金额',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    if(row.lspzk1['bkpDirection'] == 'D'){
                        if(row.lsyspz){
                            return row.lsyspz['money'];
                        }
                        if(row.lshspz){
                            return row.lshspz['money'];
                        }
                        return row.lspzk1['money'];
                    }
                }
            }, {
                field: 'preActDoc',
                title: '制单',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['preActDoc'];
                }
            }, {
                field: 'auditorNo',
                title: '复核',
                width: '12%',
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lspzk1['auditorNo'];
                }
            }]
        ]
    });

    $('#voucher_table').datagrid({
        width: '100%',
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

});

function dealConfigs(){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/getConfigsForSearch/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                //处理财务日期
                current_date_ = result.extend.current_date;
                $('#year').numberbox('setValue', parseInt(current_date_.substr(0, 4)));
                $('#month').numberbox('setValue', parseInt(current_date_.substr(4, 2)));
            }
        }
    });
}

//查询
function queryOriginalVoucher(is_detail, year_month, sql){
    var data = {
        "sql": sql
    };
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/queryVouchersWithSql/' + is_detail + '/' + year_month,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzk1VoForSearches;
                $('#main_table').datagrid('loadData', data);
                //reloadFooterTotal();
            }else {
                var data = result.extend.errorInfo;
                $.messager.alert('提示', data, 'info');
            }
        }
    });
}

function getYearMonth(){
    var year = $('#year').numberbox('getValue');
    var month = $('#month').numberbox('getValue');
    var m = month < 10 ? '0' + month : month;
    return year + m;
}

function setDatagridByformat(main_detail, detail){
    var table = $('#main_table');

    if(main_detail == 'main'){
        table.datagrid('hideColumn', 'debitMoney');
        table.datagrid('hideColumn', 'creditMoney');
        table.datagrid('hideColumn', 'itemNo');
        table.datagrid('hideColumn', 'entryNo');
        table.datagrid('showColumn', 'money');

        table.datagrid('hideColumn', 'companyNo');
        table.datagrid('hideColumn', 'spNo1');
        table.datagrid('hideColumn', 'spNo2');
    }
    else {
        table.datagrid('hideColumn', 'money');
        table.datagrid('showColumn', 'itemNo');
        table.datagrid('showColumn', 'entryNo');
        table.datagrid('showColumn', 'debitMoney');
        table.datagrid('showColumn', 'creditMoney');

        if (detail.feature) {
        }
        else {
        }


        if (detail.contact) {
            table.datagrid('showColumn', 'companyNo');
        }
        else {
            table.datagrid('hideColumn', 'companyNo');
        }

        if (detail.spacc) {
            table.datagrid('showColumn', 'spNo1');
            table.datagrid('showColumn', 'spNo2');
        }
        else {
            table.datagrid('hideColumn', 'spNo1');
            table.datagrid('hideColumn', 'spNo2');
        }

        if (detail.original) {
        }
        else {
        }
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