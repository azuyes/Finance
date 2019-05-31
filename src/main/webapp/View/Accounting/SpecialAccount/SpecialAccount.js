var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var upper_name_ = [];
var sub_stru_ = [];//存储科目结构

$(function (){
    $('#level').val(level_);
    $('#superior_account').val(upper_No_);
    $('#superior_account_name').val(upper_name_[upper_name_.length-1]);
    dealSubStru();//保存科目级数
    showCaption(upper_No_,level_.toString());
    //getSpAccMap();

    obj = {
        //上级点击事件
        uperLevel_click: function () {
            if (level_ == 1) {
                $.messager.alert('提示', '1级科目没有上级', 'info');
                return;
            }
            level_--;
            $('#level').val(level_);
            upper_No_ = getCatNo(upper_No_, level_ - 1);
            $('#superior_account').val(upper_No_);
            upper_name_.pop();
            $('#superior_account_name').val(upper_name_[upper_name_.length-1]);
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
            $('#level').val(level_);
            upper_No_ = getCatNo(row.lskmzd.itemNo, level_ - 1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            upper_name_.push(row.lskmzd.itemName);
            $('#superior_account_name').val(upper_name_[upper_name_.length-1]);
            showCaption(upper_No_, level_);
        },
        edit: function () {
            var row = $('#main_table').datagrid('getSelected');
            if (row) {//判断是否选中行
                $('#edit_account_box').window('open');
                //判断是否为日记账
                if(row.lskmzd.journal==1){
                    $('#daily_account').prop("checked",true);
                }else{
                    $('#daily_account').prop("checked",false);
                }
                //判断是否为往来
                if(row.lskmzd.exchang==1){
                    $('#dealing').prop("checked",true);
                }else{
                    $('#dealing').prop("checked",false);
                }
                //判断是否为专项核算
                if(row.lskmzd.supAcc1!=null && row.lskmzd.supAcc1 != ''){
                    $('#special_accounting').prop("checked",true);
                    $('#special_accounting_1_input').val(row.lskmzd.supAcc1);
                    $('#special_accounting_2_input').val(row.lskmzd.supAcc2);
                    $('#special_accounting_1_input').attr('name',row.lskmzd.supAcc1);
                    $('#special_accounting_2_input').attr('name',row.lskmzd.supAcc2);
                    $('#special1_table').datagrid('selectRecord',row.lskmzd.supAcc1);
                    $('#special2_table').datagrid('selectRecord',row.lskmzd.supAcc2);
                }else{
                    $('#special_accounting').prop("checked",false);
                    $('#special_accounting_1_input').val(null);
                    $('#special_accounting_2_input').val(null);
                    $('#special_accounting_1_input').attr('name',null);
                    $('#special_accounting_2_input').attr('name',null);
                    $('#special1_table').datagrid('unselectAll');
                    $('#special2_table').datagrid('unselectAll');
                }
            }else {
                $.messager.alert('提示', '请选择要定义的记录！', 'info');
            }
        },
        save: function(){
            var row = $('#main_table').datagrid('getSelected');
            var id = row.lskmzd.itemNo;
            //alert(id);
            $.ajax({
                type: 'POST',
                url: getRealPath() + '/CaptionOfAccount/editCaptionOfAccount',
                contentType: 'application/json',
                data: getData(id),
                success: function (result) {
                    if (result.code == 100) {
                        showCaption(upper_No_,level_);
                        //保存成功，关闭对话框，刷新数据最后一页
                        $("#edit_account_box").window('close');
                        $.messager.show({
                            title: '提示',
                            msg: "添加成功"
                        });
                    } else {
                        var data = result.extend.errorInfo;
                        $.messager.alert('提示', data, 'info');
                        $('#edit_form').form('clear');
                    }
                }
            });
        }
    };

    $("#special_accounting").change(function() {
        if(this.checked!=true){
            $('#special_accounting_1_input').val(null);
            $('#special_accounting_2_input').val(null);
            $('#special_accounting_1_input').attr('name',null);
            $('#special_accounting_2_input').attr('name',null);
            $('#special1_table').datagrid('clearSelections');
            $('#special2_table').datagrid('clearSelections');
        }
    });

    $('#edit_account_box').window({
        width: 300,
        height: 350,
        padding: 10,
        title: '定义特殊科目',
        iconCls: 'icon-edit_add',
        minimizable: false,
        maximizable: false,
        resizable: false,
        collapsible: false,
        closed: true,
        modal: true
    });

    // $('#special_accounting_1_select').panel({
    //     width: 125,
    //     height: 150,
    //
    // })

    $('#main_table').datagrid({
        singleSelect: true,
        width: '100%',
        multiSort: false,
        toolbar: "#button_group",
        fit: true,
        //双击进入下一级事件
        onDblClickRow : function (rowIndex, rowData) {
            obj.nextLevel_click();
        },
        columns: [[
			{
			    field: 'itemNo',
			    title: '科目编号',
			    width: '12.5%',
			    align: 'center',
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
			    width: '12.5%',
			    align: 'center',
                formatter: function (value,row,index) {
                    value = row.lskmzd.itemName;
                    return value;
                }
			},
			{
			    field: 'finLevel',
			    title: '明细',
			    width: '12.5%',
			    align: 'center',
                formatter: function (value,row,index) {
                    value = row.lskmzd.finLevel;
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
			},
            {
                field: 'journal',
                title: '日记账',
                width: '12.5%',
                align: 'center',
                formatter: function (value,row,index) {
                    value = row.lskmzd.journal;
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'exchange',
                title: '往来',
                width: '12.5%',
                align: 'center',
                formatter: function (value,row,index) {
                    value = row.lskmzd.exchang;
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'spAccName1',
                title: '核算类别1',
                width: '12.5%',
                align: 'center'
            },
            {
                field: 'spAccName2',
                title: '核算类别2',
                width: '12.5%',
                align: 'center'
            }
        ]]
    });

    $('#special1_table').datagrid({
        height: '100%',
        width: '100%',
        singleSelect: true,
        idField: 'catNo',
        url: getRealPath() + '/CaptionOfAccount/querySpecialAcc',
        columns: [[
			{
			    field: 'catNo',
			    title: '科目编号',
			    width: '42%'
			},
			{
			    field: 'catName',
			    title: '科目名称',
			    width: '42%'
			}
        ]],
        onSelect: function (rowIndex, rowData) {
            if(!$('#special_accounting').prop("checked")){
                $('#special1_table').datagrid('unselectRow',rowIndex);
                return;
            }
            $('#special_accounting_1_input').val(rowData.catName);
            $('#special_accounting_1_input').attr('name',rowData.catNo);
            $('#special2_table').datagrid('reload');
            setTimeout(function () {
                $('#special2_table').datagrid('deleteRow',rowIndex);
                $('#special_accounting_1_select').show();
            },250);
        },
        onUnselect: function (rowIndex, rowData) {
            $('#special_accounting_1_input').val(null);
            $('#special_accounting_1_input').attr(null);
            $('#special_accounting_2_input').val(null);
            $('#special_accounting_2_input').attr(null);
            $('#special1_table').datagrid('clearSelections');
            $('#special2_table').datagrid('clearSelections');
        }
    });

    $('#special2_table').datagrid({
        height: '100%',
        width: '100%',
        singleSelect: true,
        idField: 'catNo',
        url: getRealPath() + '/CaptionOfAccount/querySpecialAcc',
        columns: [[
            {
                field: 'catNo',
                title: '科目编号',
                width: '42%'
            },
            {
                field: 'catName',
                title: '科目名称',
                width: '42%'
            }
        ]],
        onSelect: function (rowIndex, rowData) {
            if(!$('#special_accounting').prop("checked")||!$('#special_accounting_1_input').val()){
                $('#special2_table').datagrid('unselectRow',rowIndex);
                return;
            }
            if(rowData.catName==$('#special_accounting_1_input').val()){
                $('#special2_table').datagrid('unselectRow',rowIndex);
                $.messager.alert('提示', '不能选择相同的专项核算', 'info');
            }
            else{
                $('#special_accounting_2_input').val(rowData.catName);
                $('#special_accounting_2_input').attr('name',rowData.catNo);
            }
        },
        onUnselect: function (rowIndex, rowData) {
            $('#special_accounting_2_input').val(null);
            $('#special_accounting_2_input').attr('name',null);
            $('#special2_table').datagrid('clearSelections');
        }
    });

    $('#box_yes_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-ok'
    });

});

//显示当前级的科目
function showCaption(num, level){
    var new_num = "";
    if(num==""){new_num="0";}
    else{new_num=num;}
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/getCaptionWithSpAcc/' + new_num + '/' + level,
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

//获取路径
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
// }

//获取特殊科目数据
function getData(id) {
    var sup_acc1 = $('#special_accounting_1_input').prop("name");
    var sup_acc2 = $('#special_accounting_2_input').prop("name");
    var journal = null;
    var exchang = null;
    $('#edit_account_box').window('open');
    //判断是否为日记账
    if($('#daily_account').prop("checked")){
        journal = 1;
    }else{
        journal = 0;
    }
    //判断是否为往来
    if($('#dealing').prop("checked")){
        exchang = 1;
    }else{
        exchang = 0;
    }
    var data = '{"itemNo":"' + id + '",' +
        '"supAcc1":"' + sup_acc1 + '",' +
        '"supAcc2":"' + sup_acc2 + '",' +
        '"journal":"' + journal + '",' +
        '"exchang":"' + exchang + '"}';
    return data;

}

//获取特殊科目的Map表
function getSpAccMap(){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/querySpecialAcc',
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result;
                alert(result);
                var sp_acc_map = new Array();
            }
        }
    });
}