var level_ = 1;//级数
var upper_No_ = '' ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = [];//存储科目结构
var bill_row_ = '';
var abs_length_ = '';

$(function (){
    $('#level').val(level_);
    $('#superior_account').val(upper_No_);
    //dealSubStru();//保存科目级数
    //dealPrecisions();
    dealConfigs();
    showCaption(upper_No_,level_.toString());
    
    var editRow = undefined; //定义全局变量：当前编辑的行
    var editPrintRow = undefined;//打印格式当前编辑的行
    var edit_mode = false;//编辑模式否则为添加状态，是则为修改状态
    var edit_print_mode = false;//编辑模式否则为添加状态，是则为修改状态

    obj = {
        //增加账册字典
        add: function () {
            var table = $('#main_table');
            //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
            if (editRow == undefined) {
                edit_mode = false;
                table.datagrid('appendRow', {'pageRules':bill_row_, 'addEmptyRow': '0', 'absWidth': abs_length_, 'chaPage': '1'});
                var rows = table.datagrid('getRows').length;
                //将新插入的那一行开启编辑状态
                table.datagrid('beginEdit', rows-1);
                //给当前编辑的行赋值
                editRow = rows-1;
                table.datagrid('scrollTo', editRow);
            }
            else{
                $.messager.alert('提示', '请先保存上一条更新', 'info');
            }
        },

        //保存新增/修改账册字典
        save: function () {
            var table = $('#main_table');
            if(!table.datagrid('validateRow',editRow)){
                $.messager.alert('提示', '账册字典条目内容无效或未填写完整', 'info');
                return;
            }
            if (editRow != undefined) {
                var edit = table.datagrid('getEditors',editRow);
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/AccountBook' + getBookSavePath(edit_mode),
                    contentType: 'application/json',
                    data: getAccountBookData(edit),
                    success: function (result) {
                        if(result.code == 100){
                            table.datagrid('endEdit', editRow);
                            editRow = undefined;
                            table.datagrid('reload');//保存成功，刷新数据
                            $.messager.show({
                                title: '提示',
                                msg: "添加成功",
                            });
                        }
                        else {
                            var data = result.extend.errorInfo;
                            $.messager.alert('提示', data, 'info');
                            //table.datagrid('reload');
                            //editRow = undefined;
                        }
                    }
                });
            }
            else{
                $.messager.alert('提示', '未更新条目', 'info');
            }
        },

        //删除
        remove: function () {
            var table = $('#main_table');

            if(editRow != undefined){
                $.messager.confirm('确定操作', '您要删除这条未保存的记录吗？', function (flag) {
                    if(flag){
                        table.datagrid('deleteRow', editRow);
                        editRow = undefined;
                    }
                });
                return;
            }
            var row = table.datagrid('getSelected');
            if(row){				
                $.messager.confirm('确定操作', '您要删除所选的记录吗？', function (flag) {
                if (flag) {
                    var acontBookNo = row.acontBookNo.toString();
                    $.ajax({
                        type : 'POST',
                        url:getRealPath()+'/AccountBook/delAccountBook/' + acontBookNo,
                        success : function (result) {
                            if (result.code == 100) {
                                table.datagrid('reload');
                                $.messager.show({
                                    title : '提示',
                                    msg : '删除成功',
                                });
                            }else {
                                var data = result.extend.errorInfo;
                                $.messager.alert('提示', data, 'info');
                            }
                        }
                    });
                }
            });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'info');
            }
        },

        setPrint: function(){
            $('#printSetWin').window('open');
        },

        //保存新增/修改打印格式
        savePrintFor: function () {
            var table = $('#printTable');
            if(!table.datagrid('validateRow',editPrintRow)){
                $.messager.alert('提示', '打印格式条目内容无效或未填写完整', 'info');
                return;
            }
            if (editPrintRow != undefined) {
                var edit = table.datagrid('getEditors',editPrintRow);
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/AccountBook' + getPrintSavePath(edit_print_mode),
                    contentType: 'application/json',
                    data: getPrintForData(edit),
                    success: function (result) {
                        if(result.code == 100){
                            table.datagrid('endEdit', editPrintRow);
                            editPrintRow = undefined;
                            table.datagrid('reload');//保存成功，刷新数据
                            $.messager.show({
                                title: '提示',
                                msg: "添加成功"
                            });
                        }
                        else {
                            var data = result.extend.errorInfo;
                            $.messager.alert('提示', data, 'info');
                            table.datagrid('reload');
                            editPrintRow = undefined;
                        }
                    }
                });
            }
            else{
                $.messager.alert('提示', '未更新条目', 'info');
            }
        },

        //上级点击事件
        uperLevel_click : function () {
            if(level_==1){return;}
            level_--;
            $('#level').val(level_);
            upper_No_=getCatNo(upper_No_,level_-1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },
        //下级点击事件
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
            if(row.finLevel == 0){
                $.messager.alert('警告操作！', '科目非明细！', 'warning');
            }
            else{
                $('#helpWin').window('close');
                var ed = $('#main_table').datagrid('getEditor', {index:editRow,field:'accBookName'});
                $(ed.target).textbox('setValue', row.itemName);
            }

        }
    };

    $('#main_table').datagrid({
        width: '100%',
        toolbar:'#button_group',
        fit: true,
        singleSelect: true,
        url: getRealPath() + '/AccountBook/queryAccountBook',
        //双击开启编辑
        onDblClickRow: function (index, row) {
            if(editRow!=undefined){
                $.messager.alert('提示', '请先保存上一条更新', 'info');
                return;
            }
            //开启编辑状态
            edit_mode = true;
            $('#main_table').datagrid('beginEdit', index);
            //给当前编辑的行赋值
            editRow = index;
        },
        columns: [[
			{
			    field: 'acontBookNo',
			    title: '账册编号',
			    width: '10%',
			    align: 'center',
                sortable: true,
                editor: {
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: '必填'
                    }
                }
			},
			{
			    field: 'accBookName',
			    title: '账册名称',
			    width: '10%',
			    align: 'center',
                editor: {
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: '必填'
                    }
                }
			},
			{
			    field: 'accBookType',
			    title: '账册类型',
			    width: '10%',
			    align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        valueField: 'id',
                        textField: 'text',
                        data: [{
                            id: '1',
                            text: '总账'
                        },{
                            id: '2',
                            text: '日记账'
                        },{
                            id: '3',
                            text: '三栏式'
                        },{
                            id: '4',
                            text: '多栏式'
                        }]
                    },
                },
                formatter: function (value, row, index) {
                    switch(value) {
                        case "1":
                            return "总账";
                        case "2":
                            return "日记账";
                        case "3":
                            return "三栏式";
                        case "4":
                            return "多栏式";
                    }
                }
			},
            {
                field: 'accType',
                title: '账式',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        valueField: 'id',
                        textField: 'text',
                        data: [{
                            id: 'J',
                            text: '金额账'
                        },{
                            id: 'Y',
                            text: '数量金额账'
                        }]
                    }
                },
                formatter: function (value,row,index) {
                    switch(value){
                        case "J": return "金额账";
                        case "Y": return "数量金额账";
                    }
                }
            },
            {
                field: 'pageRules',
                title: '每页行数',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        value: bill_row_
                    }
                }
            },
            {
                field: 'addEmptyRow',
                title: '补空行否',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        value: '0',
                        valueField: 'id',
                        textField: 'text',
                        data: [{
                            id: '1',
                            text: '是'
                        },{
                            id: '0',
                            text: '否'
                        }]
                    }
                },
                formatter: function (value,row,index) {
                    switch(value){
                        case "1": return "是";
                        case "0": return "否";
                    }
                }
            },
            {
                field: 'absWidth',
                title: '摘要栏宽度（字符）',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        value: abs_length_,
                        min: 0,
                        max: 60
                    }
                }
            },
            {
                field: 'chaPage',
                title: '科目间是否换页',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        value: '1',
                        valueField: 'id',
                        textField: 'text',
                        data: [{
                            id: '1',
                            text: '是'
                        },{
                            id: '0',
                            text: '否'
                        }]
                    }
                },
                formatter: function (value,row,index) {
                    switch(value){
                        case "1": return "是";
                        case "0": return "否";
                    }
                }
            },
            {
                field: 'printFor',
                title: '打印格式',
                width: '10%',
                align: 'center',
                editor: {
                    type: 'combogrid',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        idField: 'setNo',
                        textField: 'setNote',
                        url: getRealPath() + '/AccountBook/queryPrintFor/',
                        columns:[[
                            {field:'setNo',title:'编号',width:40},
                            {field:'setNote',title:'设置说明',width:100}
                        ]]
                    }
                }
            }
        ]]
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

    $('#printTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        url: getRealPath() + '/AccountBook/queryPrintFor/',
        onClickRow: function(index, row) {
            if(editPrintRow != undefined){
                $.messager.alert('提示', '请确认上一行操作', 'info');
                $('#printTable').datagrid('unselectRow', index);
            }
            else{
                edit_print_mode = true;
                $('#title_font').combobox('select', row.titleFont);
                $('#title_size').numberbox('setValue', row.titleSize);
                $('#form_font').combobox('select', row.bodyFont);
                $('#form_size').numberbox('setValue', row.bodySize);
                $('#trans_dir').numberbox('setValue', row.horZoom);
                $('#mach_dir').numberbox('setValue', row.verZoom);
                $('#output_type').combobox('select', row.outputType);
                $('#left_blank').numberbox('setValue', row.leftMargin);
                $('#right_blank').numberbox('setValue', row.rightMargin);
                $('#top_blank').numberbox('setValue', row.topMargin);
                $('#bottom_blank').numberbox('setValue', row.bottomMargin);
                $('#paper_type').combobox('select', row.pageSize);
            }
        },
        toolbar: [{
            iconCls: 'icon-add',
            text: '增加',
            handler: function(){
                var table = $('#printTable');
                if(editPrintRow != undefined){
                    $.messager.alert('提示', '请确认上一行操作', 'info');
                }
                else{
                    table.datagrid('appendRow', {});
                    var rows = table.datagrid('getRows').length;
                    //将新插入的那一行开启编辑状态
                    edit_print_mode = false;
                    table.datagrid('beginEdit', rows - 1);
                    editPrintRow = rows - 1;
                }
            }
        },'-', {
            iconCls: 'icon-remove',
            text: '删除',
            handler: function () {
                var table = $('#printTable');

                if (editPrintRow != undefined) {
                    table.datagrid('deleteRow', editMoneyRow);
                    editPrintRow = undefined;
                }
                else {
                    var row = table.datagrid('getSelected');
                    var printNo = row['printNo'];
                    var setNo = row['setNo'];

                    $.ajax({
                        type: 'POST',
                        url: getRealPath() + '/AccountBook/delPrintFor/' + row.szzdAutoId,
                        contentType: 'application/json',
                        success: function (result) {
                            if (result.code == 100) {
                                //TODO:显示打印参数列表
                                table.datagrid('reload');
                                $.messager.show({
                                    title: '提示',
                                    msg: "删除成功"
                                });
                            } else {
                                var data = result.extend.errorInfo;
                                $.messager.alert('提示', data, 'info');
                            }
                        }
                    });
                }
            }
        }],
        columns: [[
            {
                field: 'setNo',
                title: '编号',
                align: 'center',
                width: '30%',
                editor: {
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: '必填'
                    }
                }
            },
            {
                field: 'setNote',
                title: '设置范围',
                align: 'center',
                width: '70%',
                editor: {
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: '必填'
                    }
                }
            }
        ]]
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
        modal: true
    });

    $('#printSetWin').window({
        title: "打印参数设置",
        width: 700,
        height: 420,
        padding: 20,
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
        modal: true
    });

});

//获取路径
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
// }

// //显示账册字典
// function showAccountBook() {
//     $.ajax({
//         typr: 'POST',
//         url: getRealPath() + 'AccountBook/queryAccountBook',
//         contentType: 'application/json',
//         success: function (result) {
//             if()
//         }
//     })
// }

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

// //获取科目级数
// function dealSubStru() {
//     $.ajax({
//         type: 'POST',
//         url: getRealPath() + '/CaptionOfAccount/getSubjectStructure/',
//         contentType: 'application/json',
//         success: function (result) {
//             var stru = result.split("");
//             for(var i=0;i<stru.length;i++){
//                 sub_stru_[i]=stru[i];
//             }
//             if(result=="0"){
//                 alert("请先设置科目结构");
//                 //TODO：关闭标签页
//             }
//         }
//     });
// }

function dealConfigs() {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountBook/getConfigsForBook/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                //处理科目结构
                var stru = result.extend.sub_stru.split("");
                for (var i = 0; i < stru.length; i++) {
                    sub_stru_[i] = stru[i];
                }
                //帐页行数
                bill_row_ = result.extend.bill_row;
                //摘要名称长度
                abs_length_ = result.extend.abs_length;
            }
        }
    });
}

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}

//获取更新的账册字典行数据
function getAccountBookData(row) {
    var acontBookNo = $(row[0].target).numberbox('getValue');
    var accBookName = $(row[1].target).textbox('getValue');
    var accBookType = $(row[2].target).combobox('getValue');
    var accType = $(row[3].target).combobox('getValue');
    var pageRules = $(row[4].target).numberbox('getValue');
    var addEmptyRow = $(row[5].target).combobox('getValue');
    var absWidth = $(row[6].target).numberbox('getValue');
    var chaPage = $(row[7].target).combobox('getValue');
    var printFor = $(row[8].target).combobox('getValue');

    var data = '{"acontBookNo":"' + acontBookNo + '",' +
        '"accBookName":"' + accBookName + '",' +
        '"accBookType":"' + accBookType + '",' +
        '"accType":"' + accType + '",' +
        '"pageRules":"' + pageRules + '",' +
        '"addEmptyRow":"' + addEmptyRow + '",' +
        '"absWidth":"' + absWidth + '",' +
        '"chaPage":"' + chaPage + '",' +
        '"printFor":"' + printFor + '"}';
    return data;
}

function getPrintForData(row) {
    var setNo = $(row[0].target).textbox('getValue');
    var setNote = $(row[1].target).textbox('getValue');
    var titleFont = $('#title_font').combobox('getValue');
    var titleSize = $('#title_size').numberbox('getValue');
    var bodyFont = $('#form_font').combobox('getValue');
    var bodySize = $('#form_size').numberbox('getValue');
    var horZoom = $('#trans_dir').numberbox('getValue');
    var verZoom = $('#mach_dir').numberbox('getValue');
    var outputType = $('#output_type').combobox('getValue');
    var leftMargin = $('#left_blank').numberbox('getValue');
    var rightMargin = $('#right_blank').numberbox('getValue');
    var topMargin = $('#top_blank').numberbox('getValue');
    var bottomMargin = $('#bottom_blank').numberbox('getValue');
    var pageSize = $('#paper_type').combobox('getValue');

    var data = '{"setNo":"' + setNo + '",' +
        '"setNote":"' + setNote + '",' +
        '"titleFont":"' + titleFont + '",' +
        '"titleSize":"' + titleSize + '",' +
        '"bodyFont":"' + bodyFont + '",' +
        '"bodySize":"' + bodySize + '",' +
        '"horZoom":"' + horZoom + '",' +
        '"verZoom":"' + verZoom + '",' +
        '"outputType":"' + outputType + '",' +
        '"leftMargin":"' + leftMargin + '",' +
        '"rightMargin":"' + rightMargin + '",' +
        '"topMargin":"' + topMargin + '",' +
        '"bottomMargin":"' + bottomMargin + '",' +
        '"pageSize":"' + pageSize + '"}';
    return data;
}

//增加和编辑使用不同的后台更新方式
function getBookSavePath(edit_mode){
    if(edit_mode){
        return '/editAccountBook';
    }
    else{
        return '/saveAccountBook';
    }
}

function getPrintSavePath(edit_mode){
    if(edit_mode){
        return '/editPrintFor';
    }
    else{
        return '/savePrintFor';
    }
}