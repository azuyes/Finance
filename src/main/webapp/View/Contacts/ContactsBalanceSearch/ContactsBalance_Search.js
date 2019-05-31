

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
var catNo1 = "";
var companyNo = "";
var sortMethod = "";//排序方式
var searchAccType = "J";//查询格式（数量账，金额账，S，J）
var searchOption = "N";//查询选项（是否包含着账前凭证）
var queryDataType = "";//在两个都输入通配符时候，弹出的对话框选择的值

var searchKind = '';//查询种类，总共有4类查询，1、输科目，单位通配符2、科目通配符，输单位 3、两都输 4、两都通配符


var current_date_ = "";

var flag = true;

var showupperItemName  = '';

$(function(){


    //
    // $('#level').val(level_);
    // $('#superior_account').val(upper_No_);
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


    $('#balanceSearch_Win').window({
        width: 390,
        height: 300,
        title: '往来单位余额查询（一维表，二维表）',
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

    // $('#catNo1_HelpWin').window({
    //     width: 480,
    //     height: 500,
    //     title: '专项核算类别',
    //     collapsible: false,
    //     minimizable: false,
    //     closed: true,
    //     resizable: false,
    //     maximizable: false,
    // });
    //
    // $('#catNo1Table').datagrid({
    //     width:'100%',
    //     height:'100%',
    //     fit: true,
    //     fitColumns: false,
    //     singleSelect : true,
    //     url: getRealPath() + '/SpAccountCategory/getSpAccountCategory',
    //     columns: [[
    //         {
    //             field: 'catNo',
    //             title: '编号',
    //             align:'center',
    //             width: 120
    //         },
    //         {
    //             field: 'catName',
    //             title: '名称',
    //             align:'center',
    //             width: 184
    //         },
    //         {
    //             field: 'finLevel',
    //             title: '明细否',
    //             align:'center',
    //             width: 80,
    //
    //         },
    //         {
    //             field: 'spLevel',
    //             title: '级数',
    //             align:'center',
    //             width: 80
    //         },
    //
    //     ]],
    //     toolbar: '#tbCatNo1_helpWin',
    // });

    //帮助窗口
    $('#companyNo_HelpWin').window({
        width: 480,
        height: 500,
        title: '专项核算编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    //帮助窗口数据表格
    $('#companyNoTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        url: getRealPath() + '/ContactsPageSearch/queryCompany',
        columns: [[
            {
                field: 'companyNo',
                title: '编号',
                width: 120,

            },
            {
                field: 'companyName',
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
        toolbar: '#tbCompanyNo_helpWin',
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

//初始化加载，解决页脚行不加载问题。
    var data ='{"total":0,"rows":[],"footer":[{"companyName":"合计","itemName":"合计","supMoney":0,"balance":0,"debitQtySup":0,"debitMoneySup":0,"creditQtySup":0,"creditMoneySup":0,"debitQty":0,"debitMoney":0,"creditQty":0,"creditMoney":0,"debitQtyAcm":0,"debitMoneyAcm":0,"creditQtyAcm":0,"creditMoneyAcm":0}]}';
    var jsonData  = JSON.parse(data);
    //查询结果显示表
    $('#main_table').datagrid({
        width: fixWidth(0.99),
        multiSort: false,
        toolbar: '#button_group',
        singleSelect: true,
        showFooter: true,
       // data : jsonData,
        columns: [
            [{
                field: 'companyNo',
                title: '单位编号',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lswldw['companyNo'];
                }
            }, {
                field: 'companyName',
                title: '单位名称',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    return row.lswldw['companyName'];
                }
            }, {
                field: 'itemNo',
                title: '科目编号',
                width: '12%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {

                    return row.lskmzd['itemNo'];
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
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;
                    // if (m == 1){
                    //     if(row.lswlje['debitMoneySup'] > row.lswlje['creditMoneySup']){
                    //         return '借';
                    //     }
                    //     else if(row.lswlje['debitMoneySup'] < row.lswlje['creditMoneySup']){
                    //         return '贷';
                    //     }
                    //     else{
                    //         return '平';
                    //     }
                    // }
                    // else{
                    //     if(row.lswlje['debitMoney' + getFullMonth(m)] > row.lswlje['creditMoney' + getFullMonth(m)]){
                    //         return '借';
                    //     }
                    //     else if(row.lswlje['debitMoney' + getFullMonth(m)] < row.lswlje['creditMoney' + getFullMonth(m)]){
                    //         return '贷';
                    //     }
                    //     else{
                    //         return '平';
                    //     }
                    // }

                    if(m == 1){
                        if(row.lswlje['supMoney']>0){
                                    return '借';
                        }else if(row.lswlje['supMoney']<0){
                            return '贷';
                        }else if(row.lswlje['supMoney']==0){
                            return '平';
                        }
                    }
                    else if(row.lswlje['balance'+getFullMonth(m-1)]>0){
                            return '借';
                        }else if(row.lswlje['balance'+getFullMonth(m-1)]<0){
                            return '贷';
                        }else if(row.lswlje['balance'+getFullMonth(m-1)]==0){
                            return '平';
                        }
                    }
            }, {
                field: 'titleSupMoney',
                title: '上月结转',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'titleSupCreditMoney',
                title: '上月贷方结转',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'supMoney',
                title: '上月结转',
                width: '12%',
                rowspan: 2,
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;
                    if(m == 1) return row.lswlje['supMoney'];
                    else return row.lswlje['balance'+getFullMonth(m-1)];
                }
            }, {
                field: 'thisMonth',
                title: '本月发生',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'thisMonthCredit',
                title: '本月贷方',
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'bkpDirectionAcm',
                title: '借或贷',
                width: '4%',
                rowspan: 2,
                align: 'center',
                formatter: function (value,row,index) {
                    if(row.lswlje['debitMoneyAcm' + getFullMonth(m)] > row.lswlje['creditMoneyAcm' + getFullMonth(m)]){
                        return '借';
                    }
                    else if(row.lswlje['debitMoneyAcm' + getFullMonth(m)] < row.lswlje['creditMoneyAcm' + getFullMonth(m)]){
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
                align: 'center'
            }, {
                field: 'titleCreditBalance',
                title: '当前贷方余额',
                colspan: 2,
                width: '12%',
                align: 'center'
            }, {
                field: 'balance',
                title: '当前余额',
                rowspan: 2,
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    return row.lswlje['balance'+getFullMonth(m)];
                }
            }], [{
                field: 'debitQtySup',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lswlsl == null) return null;
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;

                    if (m == 1) return row.lswlsl['debitQtySup'];
                    else return row.lswlsl['debitQty' + getFullMonth(m)];

                }
            }, {
                field: 'debitMoneySup',
                title: '借方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;
                    if (m == 1) return row.lswlje['debitMoneySup'];
                    else return row.lswlje['debitMoney' + getFullMonth(m)];

                }
            }, {
                field: 'creditQtySup',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lswlsl == null) return null;
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;
                    if(m == 1) return row.lswlsl['creditQtySup'];
                    else return row.lswlsl['creditQty'+getFullMonth(m)];
                }
            }, {
                field: 'creditMoneySup',
                title: '贷方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    //var m = parseInt(current_date_.substr(4, 2)) - 1;
                    //if(m == 1) return row.lswlje['creditMoneySup'];
                    //else
                        return row.lswlje['creditMoney'+getFullMonth(m)];
                }
            }, {
                field: 'debitQty',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lswlsl == null) return null;
                    //var mm = current_date_.substr(4, 2);
                    return row.lswlsl['debitQty' + mm];

                }
            }, {
                field: 'debitMoney',
                title: '借方',
                width: '12%',
                align: 'center'
                ,
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);
                    return row.lswlje['debitMoney' + getFullMonth(m)];

                }
            }, {
                field: 'creditQty',
                title: '数量',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lswlsl == null) return null;
                    //var mm = current_date_.substr(4, 2);
                    return row.lswlsl['creditQty'+getFullMonth(m)];
                }
            }, {
                field: 'creditMoney',
                title: '贷方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    //var mm = current_date_.substr(4, 2);
                    return row.lswlje['creditMoney'+getFullMonth(m)];
                }
            }, {
                field: 'debitQtyAcm',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lswlsl == null) return null;
                    return row.lswlsl['debitQtyAcm'];

                }
            }, {
                field: 'debitMoneyAcm',
                title: '借方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    return row.lswlje['debitMoneyAcm'];

                }
            }, {
                field: 'creditQtyAcm',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    if(row.lswlsl == null) return null;
                    return row.lswlsl['creditQtyAcm'];
                }
            }, {
                field: 'creditMoneyAcm',
                title: '贷方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    return row.lswlje['creditMoneyAcm'];
                }
            }]
        ]
    });

    $('#itemNo_Input').textbox('textbox').bind('dblclick', function() {
        $('#itemNo_HelpWin').window('open');
    })

    $('#companyNo_Input').textbox('textbox').bind('dblclick', function() {
        $('#companyNo_HelpWin').window('open');
    });

});
var data ='{"total":0,"rows":[],"footer":[{"companyName":"合计","itemName":"合计",supMoney":0,"balance":0,"debitQtySup":0,"debitMoneySup":0,"creditQtySup":0,"creditMoneySup":0,"debitQty":0,"debitMoney":0,"creditQty":0,"creditMoney":0,"debitQtyAcm":0,"debitMoneyAcm":0,"creditQtyAcm":0,"creditMoneyAcm":0}]}';

// function reloadFooter(){  //计算je表的页脚行总计
//     supMoney = compute("supMoney");
//     balance  = compute("supMoney");
//     debitQtySup = compute("supMoney");
//     debitMoneySup = compute("supMoney");
//     creditQtySup = compute("supMoney");
//     creditMoneySup = compute("supMoney");
//     debitQty = compute("supMoney");
//     debitMoney = compute("supMoney");
//     creditQty = compute("supMoney");
//     creditMoney = compute("supMoney");
//     debitQtyAcm = compute("supMoney");
//     debitMoneyAcm = compute("supMoney");
//     creditQtyAcm = compute("supMoney");
//     creditMoneyAcm = compute("supMoney");
//
//
//     $('#main_table').datagrid('reloadFooter',
//         [{"companyName":"合计","itemName":"合计","supMoney":supMoney,"balance":balance,"debitQtySup":debitQtySup,"debitMoneySup":debitMoneySup,"creditQtySup":creditQtySup,"creditMoneySup":creditMoneySup,"debitQty":debitQty,"debitMoney":debitMoney,"creditQty":creditQty,"creditMoney":creditMoney,"debitQtyAcm":debitQtyAcm,"debitMoneyAcm":debitMoneyAcm,"creditQtyAcm":creditQtyAcm,"creditMoneyAcm":creditMoneyAcm}]
// );
// }

//指定列求和
function compute(colName) {
    var rows = $('#main_table').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        total += parseFloat(rows[i][colName]);
    }
    return total;
}

//输入完查询条件，点击查询时事件
function searchClick(){
    year = $('#year_Input').val();
    m = $('#month_Input').val();
    itemNo = $('#itemNo_Input').val();
    companyNo = $('#companyNo_Input').val();
    searchAccType = $("input[name=searchFormat]:checked").val();//查询格式（数量账，金额账，S，J）
    searchOption = $("input[name=searchOption]:checked").val();//查询选项（是否包含着账前凭证）



    var isValidItemNo=$('#itemNo_Input').textbox("isValid");
    var isValidCompanyNo=$('#companyNo_Input').textbox("isValid");
    if(!isValidItemNo || !isValidCompanyNo){
        flag = false;
        return ;
    }

    if(companyNo == "..." && itemNo != "...."){ //查询1：输科目，单位通配符

//        judgeItemNoInput();
        //if(flag){
        searchKind = '1';
        setDatagridByformat(searchAccType,searchKind);
        search();
        $('#balanceSearch_Win').window('close');
        document.getElementById("mainDisplay").style.visibility = "visible";
        return;
        // }else{
        //    $.messager.alert('提示', '科目编号不存在或者不是往来科目！请输入一个已定义了往来单位的科目编号或通配符！', 'info');
        //    return;
        //}
        return;

    }else if(companyNo != "..." && itemNo == "...."){ //查询2：科目通配符，输单位
        //judgeCompanyNoInput();
        //if(!flag){
        //    $.messager.alert('提示', '该单位没有对应往来科目！请重新选择单位', 'info');
        //    return;
        //}else{
        searchKind = '2';
        setDatagridByformat(searchAccType,searchKind);
        search2();
        $('#balanceSearch_Win').window('close');
        document.getElementById("mainDisplay").style.visibility = "visible";
        //}

    }else if(companyNo != "..." && itemNo !="...."){  //查询3：两都输

        // judgeItemNoInput();
        // if(!flag){
        //     $.messager.alert('提示', '科目编号不存在或者不是往来科目！请输入一个已定义了往来单位的科目编号或通配符！', 'info');
        //     return;
        // }else{
        //     judgeCompanyNoInput();
        //     if(!flag){
        //         $.messager.alert('提示', '该单位没有对应往来科目！请重新选择单位', 'info');
        //         return;
        //     }else{
        //         judgeCompanyConnectItem();
        //         if(!flag){
        //             $.messager.alert('提示', '输入的科目非单位所对应的往来科目！请重新输入', 'info');
        //             return;
        //         }else{
        //             searchKind = '3';
        //             setDatagridByformat(searchAccType,searchKind);
        //             search();
        //             $('#balanceSearch_Win').window('close');
        //             document.getElementById("mainDisplay").style.visibility = "visible";
        //         }
        //     }
        // }
        judgeCompanyConnectItem();

    }else{  //查询4：科目通配符，单位通配符
        $('#queryDataType_Win').window('open');
    }

}

function searchClick4(){

    //前台获取
    var columns;

    $.ajax({
        type: "GET",
        url: getRealPath() + '/ContactsBalanceSearch/getAllColumn',
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
    params.companyNo = companyNo;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;
    params.queryDataType = queryDataType;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/ContactsBalanceSearch/getAllInfo',
        data: params,
        dataType:"json",
        success:function(result){
            $('#main_table').datagrid('loadData', result); //需要添加具体的data内容
            $('#balanceSearch_Win').window('close');
            $('#queryDataType_Win').window('close');
            document.getElementById("mainDisplay").style.visibility = "visible";

        }
    });
}

function search2(){
    var params = {};
    params.year = year;
    params.month = month;
    params.itemNo = itemNo;
    params.companyNo = companyNo;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        async:false,
        type:'post',
        url: getRealPath() + '/ContactsBalanceSearch/queryContactsBalance2',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var contactsBalanceQueryVoList = result.extend.contactsBalanceQueryVoList;
                $('#main_table').datagrid('loadData', contactsBalanceQueryVoList); //需要添加具体的data内容
                $('#searchTime_label').text(year+'年'+m+'月');
                $('#searchItem_label').text(result.extend.contactsBalanceQueryVoList[0].lskmzd.itemName);
            }else{
            }
        }
    });
}

//当所有的判断都正确后执行查询操作
function search(){
    var params = {};
    //params.XX必须与Spring Mvc controller中的参数名称一致
    //否则在controller中使用@RequestParam绑定
    params.year = year;
    params.month = month;
    params.itemNo = itemNo;
    params.companyNo = companyNo;
    params.searchAccType = searchAccType;
    params.searchOption = searchOption;

    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsBalanceSearch/queryContactsBalance',
        data: params,
        dataType:"json",
        success:function(result){
            if(result.code == 100){
                var contactsBalanceQueryVoList = result.extend.contactsBalanceQueryVoList;
                $('#main_table').datagrid('loadData', contactsBalanceQueryVoList); //需要添加具体的data内容
                $('#searchTime_label').text(year+'年'+m+'月');
                $('#searchItem_label').text(result.extend.contactsBalanceQueryVoList[0].lskmzd.itemName);
            }else{
            }
        }
    });
}

//当输入科目编号和单位编号都不是通配符时判断科目编号和单位编号是否对应关联了。
function judgeCompanyConnectItem(){
    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsPageSearch/judgeCompanyConnectItem/'+itemNo+'/'+companyNo,
        contentType:'application/json;charset=utf-8',
        success:function(result){
            if(result.code == 100){
                searchKind = '3';
                setDatagridByformat(searchAccType,searchKind);
                search();
                $('#balanceSearch_Win').window('close');
                document.getElementById("mainDisplay").style.visibility = "visible";
            }else{
                 $.messager.alert('提示', '输入的科目非单位所对应的往来科目！请重新输入！', 'info');
            }
        }
    });
}

//判断输入的单位编号是否正确
function judgeCompanyNoInput(){
    companyNo = $('#companyNo_Input').val();
    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsPageSearch/judgeContactsCompanyNo/'+companyNo,
        contentType:'application/json;charset=utf-8',
        success:function(result){
            var lswldw  = result.extend.lswldw;
            if(lswldw != null){
                flag = true;
            }else{
                //$.messager.alert('提示', '该单位没有对应往来科目！请重新选择单位', 'info');
                flag = false;
            }
        }
    });
}

//判断输入的科目编号是否正确
function judgeItemNoInput(){
    itemNo = $('#itemNo_Input').val();
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/ContactsPageSearch/judgeItemNo/' + itemNo ,
        contentType: 'application/json;charset=utf-8',
        success:function(result){
            var lskmzd  = result.extend.lskmzd;
            if(lskmzd != null){
                flag = true;
            }else{
                //$.messager.alert('提示', '该单位没有对应往来科目！请重新选择单位', 'info');
                flag =  false;
            }
        }
    });
}

//帮助窗口，选定科目时事件
function selectItemNoClick() {

    var row = $('#itemTable').datagrid("getSelected");
    if(row.exchang == 0){
        $.messager.alert('警告操作！', '该科目不是往来科目！', 'warning');
    }else{
        itemNo = row.itemNo;
        //itemName = row.itemName;
        $('#itemNo_HelpWin').window('close');
        $("#itemNo_Input").textbox('setValue',row.itemNo);
    }
}

//帮助窗口，选定科目时事件
function selectCompanyNoClick(){
    var row = $('#companyNoTable').datagrid("getSelected");
    companyNo = row.companyNo;
    $('#companyNo_HelpWin').window('close');
    $("#companyNo_Input").textbox('setValue',row.companyNo);
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

//获取金额和数量的小数精度
function dealPrecisions() {
    var money_prec = 2;
    var quan_prec = 2;
    precisions_.push(money_prec);
    precisions_.push(quan_prec);
}


function fixWidth(percent){
    return document.body.clientWidth * percent ; //这里你可以自己做调整
}

function fixHeight() {
    return document.body.clientHeight -70 ; //这里你可以自己做调整
}


function setDatagridByformat(searchAccType,searchKind){
    var table = $('#main_table');

    if(searchKind == '1' || searchKind == '3'){
        table.datagrid('hideColumn','itemNo');
        table.datagrid('hideColumn','itemName');
    }
    if(searchKind == '2'){
        table.datagrid('hideColumn','companyNo');
        table.datagrid('hideColumn','companyName');
    }

    if(searchAccType == 'J' ){
        //上月：仅显示上月结转直接标题，二级标题全部隐藏
        table.datagrid('hideColumn','titleSupMoney');
        table.datagrid('hideColumn','titleSupCreditMoney');
        table.datagrid('showColumn','supMoney');
        table.datagrid('getColumnOption','supMoney').title = '上月结转';
        table.datagrid('showColumn','bkpDirectionSup');

        table.datagrid('hideColumn','creditMoneySup');
        table.datagrid('hideColumn','debitMoneySup');
        table.datagrid('hideColumn','creditQtySup');
        table.datagrid('hideColumn','debitQtySup');

        //本月：不显示本月贷方，数量相关不显示
        table.datagrid('hideColumn','thisMonthCredit');
        table.datagrid('getColumnOption','thisMonth').title = '本月发生';

        table.datagrid('hideColumn','creditQty');
        table.datagrid('hideColumn','debitQty');
        table.datagrid('getColumnOption','debitMoney').title = '借方';
        table.datagrid('getColumnOption','creditMoney').title = '贷方';

        //当前：仅显示当前余额直接标题，二级标题全部隐藏
        table.datagrid('hideColumn','titleBalance');
        table.datagrid('hideColumn','titleCreditBalance');
        table.datagrid('showColumn','balance');
        table.datagrid('getColumnOption','balance').title = '当前余额';
        table.datagrid('showColumn','bkpDirectionAcm');

        table.datagrid('hideColumn','creditMoneyAcm');
        table.datagrid('hideColumn','debitMoneyAcm');
        table.datagrid('hideColumn','creditQtyAcm');
        table.datagrid('hideColumn','debitQtyAcm');
    }


    if(searchAccType == 'S' ){
        //上月：显示上月结转一级标题，二级标题改为数量金额
        table.datagrid('showColumn','titleSupMoney');
        table.datagrid('hideColumn','titleSupCreditMoney');
        table.datagrid('hideColumn','supMoney');
        table.datagrid('getColumnOption','titleSupMoney').title = '上月结转';
        table.datagrid('showColumn','bkpDirectionSup');
        //用原借方处debit显示总发生额，贷方不显示
        table.datagrid('hideColumn','creditMoneySup');
        table.datagrid('showColumn','debitMoneySup');
        table.datagrid('hideColumn','creditQtySup');
        table.datagrid('showColumn','debitQtySup');
        table.datagrid('getColumnOption','debitMoneySup').title = '金额';

        //本月：本月发生改名为本月借方，隐藏直接标题，显示数量相关
        table.datagrid('showColumn','thisMonthCredit');
        table.datagrid('getColumnOption','thisMonth').title = '本月借方';

        table.datagrid('showColumn','creditQty');
        table.datagrid('showColumn','debitQty');
        table.datagrid('getColumnOption','debitMoney').title = '金额';
        table.datagrid('getColumnOption','creditMoney').title = '金额';

        //当前：显示当前余额一级标题，二级标题改为数量金额
        table.datagrid('showColumn','titleBalance');
        table.datagrid('hideColumn','titleCreditBalance');
        table.datagrid('hideColumn','balance');
        table.datagrid('getColumnOption','titleBalance').title = '当前余额';
        table.datagrid('showColumn','bkpDirectionAcm');

        table.datagrid('hideColumn','creditMoneyAcm');
        table.datagrid('showColumn','debitMoneyAcm');
        table.datagrid('hideColumn','creditQtyAcm');
        table.datagrid('showColumn','debitQtyAcm');
        table.datagrid('getColumnOption','debitMoneyAcm').title = '金额';
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

function searchCompanyNoClick(){
    searchHelp1("#companyNoTable","#searchText_CompanyNo","companyNo","companyName");
}

function searchHelp1(eleTable,eleInput,no,name){
    var val = $(eleInput).val();
    var row = $(eleTable).datagrid("getSelected");
    var rowIndex ;
    if (row) {
        rowIndex = $(eleTable).datagrid('getRowIndex', row);
    }else{
        rowIndex = -1;
    }
    var gridData = $(eleTable).datagrid("getData");

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

//点击科目编号后面的通配符提示按钮
function wildcardForItemNoClick() {

    $("#itemNo_Input").textbox('setValue',"....");
}

//点击科目编号后面的通配符提示按钮
function wildcardForCompanyNoClick() {

    $("#companyNo_Input").textbox('setValue',"...");
}
