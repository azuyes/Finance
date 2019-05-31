//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度

var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称

var year = "";      //查询年份
var monthFrom = "";  //起始月份
var monthTo = "";   //终止月份
var dataFrom = "";//其实查询日期
var dataTo = "";//终止查询日期
var catNo1 = "";    //科目编号
var companyNo = "";     //单位编号
var pageNum = "";
var searchOption1 = "";//查询选项（是否包含着账前凭证）
var searchOption2 = "";//查询选项（打印时不满页补空行）

var showupperItemName  = '';

$(function() {

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

    $('#accountPageSearch_Win').window({
        width: 430,
        height: 350,
        title: '单位往来明细账页查询',
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

    //帮助窗口数据表格companyNoTable
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
            },
            {
                field: 'item',
                title: '级数',
                width: 80
            },
        ]],
        toolbar: '#tbCompanyNo_helpWin',
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
                width: '12%',
                colspan: 2,
                align: 'center'
            }, {
                field: 'voucherNo',
                title: '凭证号',
                width: '12%',
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
                title: '借或贷',
                rowspan: 2,
                width: '6%',
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
                width: '6%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lspzk1['inputDate'] != null)
                        return row.lspzk1['inputDate'].substr(4, 2);
                }
            }, {
                field: 'day',
                title: '日',
                width: '6%',
                align: 'center',
                formatter: function (value, row, index) {
                    if(row.lspzk1['inputDate'] != null)
                        return row.lspzk1['inputDate'].substr(6, 2);
                }
            }, {
                field: 'debitQty',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    return;
                }
            }, {
                field: 'debitMoney',
                title: '金额',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    return;
                }
            }, {
                field: 'creditQty',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    return;
                }
            }, {
                field: 'creditMoney',
                title: '贷方',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    return;
                }
            }, {
                field: 'leftQty',
                title: '数量',
                width: '12%',
                align: 'center',
                hidden: true,
                formatter: function (value, row, index) {
                    return;
                }
            }, {
                field: 'leftPrice',
                title: '单价',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    return;
                }
            }, {
                field: 'leftMoney',
                title: '金额',
                width: '12%',
                align: 'center',
                formatter: function (value, row, index) {
                    return;
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

//帮助窗口，选定科目时事件
function selectItemNoClick() {

    var row = $('#itemTable').datagrid("getSelected");
    if(row.exchang == 0){
        $.messager.alert('警告操作！', '该科目不是往来科目！', 'warning');
    }else{
        itemNo = row.itemNo;
        $('#itemNo_HelpWin').window('close');
        $("#itemNo_Input").val(row.itemNo);
    }
}

//帮助窗口，选定科目时事件
function selectCompanyNoClick(){
    var row = $('#companyNoTable').datagrid("getSelected");
    companyNo = row.companyNo;
    $('#companyNo_HelpWin').window('close');
    $("#companyNo_Input").val(row.companyNo);
}

//输入完查询条件，点击查询时事件
function searchClick(){

    var flag = true;
    var isValidItemNo=$('#itemNo_Input').textbox("isValid");
    var isValidCompanyNo=$('#companyNo_Input').textbox("isValid");
    if(!isValidItemNo || !isValidCompanyNo){
        flag = false;
        return ;
    }

    year = $('#year_Input').val();
    monthFrom = $('#monthFrom_Input').val();
    monthTo = $('#monthTo_Input').val();
    dataFrom = year+getFullMonth(monthFrom);
    dataTo = year+getFullMonth(monthTo);
    itemNo = $('#itemNo_Input').val();
    companyNo = $('#companyNo_Input').val();
    pageNum = $('#pageNum_Input').val();
    searchOption1 = $("input[name=option1]:checked").val();//查询选项（是否包含着账前凭证）
    searchOption2 = $("input[name=option3]:checked").val();//查询选项（打印时不满页补空行）

    if(monthFrom > monthTo){
        $.messager.alert('提示', '终止月份应大于等于起始月份，请重新选择！', 'info');
        flag = false;
        return ;
    }

    //初始对话框，填好科目编号后，点击确定时的处理事件

    flag = judgeItemNoInput();
    if(!flag){
        $.messager.alert('提示', '科目编号不存在请重新输入！', 'info');
        return;
    }
    flag = judgeCompanyNoInput();
    if(!flag){
        $.messager.alert('提示', '往来单位编号不存在请重新输入！', 'info');
        return;
    }
    flag = judgeCompanyConnectItem();
    if(!flag){
        $.messager.alert('提示', '输入的科目非单位所对应的往来科目！请重新输入！', 'info');
        return;
    }

    //document.getElementById("mainDisplay").style.visibility = "visible";
    //输入的查询数据均正确，进行查询操作
    if(flag){
        $('#accountPageSearch_Win').window('close');
        setDatagridByformat('money');
        document.getElementById("mainDisplay").style.visibility = "visible";

        var params = {};
        //params.XX必须与Spring Mvc controller中的参数名称一致
        //否则在controller中使用@RequestParam绑定
        params.dataFrom = dataFrom;
        params.dataTo = dataTo;
        params.itemNo = itemNo;
        params.companyNo = companyNo;
        params.pageNum = pageNum;
        params.searchOption1 = searchOption1;
        params.searchOption2 = searchOption2;

        $.ajax({
            async:false,
            type:'post',
            url: getRealPath() + '/ContactsPageSearch/queryContactsAccountPage',
            //contentType:'application/json;charset=utf-8',
            data: params,
            dataType:"json",
            success:function(result){
                if(result.code == 100){

                    var lspzk1VoForSearches = result.extend.lspzk1VoForSearches;
                    $('#main_table').datagrid('loadData', lspzk1VoForSearches); //需要添加具体的data内容
                    headInfoShow(result.extend.headInfo);
                }else{

                }
            }
        });

        document.getElementById("mainDisplay").style.visibility = "visible";
    }

}

//当输入科目编号和单位编号都不是通配符时判断科目编号和单位编号是否对应关联了。
function judgeCompanyConnectItem(){
    var flag = true;
    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsPageSearch/judgeCompanyConnectItem/itemNo'+itemNo+'/companyNo'+companyNo,
        contentType:'application/json;charset=utf-8',
        success:function(result){
            if(result.code == 100){
                flag = true;
            }else{
                // $.messager.alert('提示', '输入的科目非单位所对应的往来科目！请重新输入！', 'info');
                flag = false;
            }
        }
    });
    return flag;
}

//判断输入的单位编号是否正确
function judgeCompanyNoInput(){
    companyNo = $('#companyNo_Input').val();
    var flag = true;
    $.ajax({
        type:'post',
        url: getRealPath() + '/ContactsPageSearch/judgeContactsCompanyNo/companyNo'+companyNo,
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
    return flag;
}

//判断输入的科目编号是否正确
function judgeItemNoInput(){
    itemNo = $('#itemNo_Input').val();
    var flag = true;
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/ContactsPageSearch/judgeItemNo/' + itemNo ,
        contentType: 'application/json',
        success:function(result){
            var lskmzd  = result.extend.lskmzd;
            if(lskmzd != null){
                flag = true;
            }else{
                //$.messager.alert('提示', '该单位没有对应往来科目！请重新选择单位', 'info');
                flag = false;
            }
        }
    });
    return flag;
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


function setDatagridByformat(actype){
    var table = $('#main_table');
    $('#main_table').datagrid('getColumnOption','year').title = year;
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

function getFullMonth(number){
    return number >= 10 ? number.toString() : '0' + number.toString();
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


function headInfoShow(headInfo){
    $("#head_info_area").append("科目：");
    if(headInfo.itemName != null){
        $("#head_info_area").append(headInfo.itemName);
    }
    if(headInfo.companyName != null){
        $("#head_info_area").append('/'+headInfo.companyName);
    }
    if(headInfo.head1Key != null && headInfo.head1Key != ""){
        $("#head_info_area").append('  '+headInfo.head1Key + ':' + headInfo.head1Value+'  ');
    }
    if(headInfo.head2Key != null &&  headInfo.head2Key != ""){
        $("#head_info_area").append('  '+headInfo.head2Key + ':' + headInfo.head2Value+'  ');
    }
    if(headInfo.head3Key != null &&  headInfo.head3Key != ""){
        $("#head_info_area").append('  '+headInfo.head3Key + ':' + headInfo.head3Value+'  ');
    }
    if(headInfo.head4Key != null &&  headInfo.head4Key != ""){
        $("#head_info_area").append('  '+headInfo.head4Key + ':' + headInfo.head4Value+'  ');
    }
    if(headInfo.head5Key != null &&  headInfo.head5Key != ""){
        $("#head_info_area").append('  '+headInfo.head5Key + ':' + headInfo.head5Value+'  ');
    }
    if(headInfo.head6Key != null &&  headInfo.head6Key != ""){
        $("#head_info_area").append('  '+headInfo.head6Key + ':' + headInfo.head6Value+'  ');
    }
}
