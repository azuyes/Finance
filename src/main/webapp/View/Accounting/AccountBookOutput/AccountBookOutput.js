var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构

$(function () {
    $('#level').val(level_);
    $('#superior_account').val(upper_No_);
    dealSubStru();//保存科目级数
    //dealPrecisions();
    showCaption(upper_No_,level_.toString());

    // $('#left_panel').panel({
    //     height: 400,
    //     padding: 10,
    //     margin: 10,
    // })
    //
    // $('#right_panel').panel({
    //     height: 400,
    //     padding: 10,
    //     margin: 10,
    // })

    var editRow = undefined;//定义全局变量，暂存当前编辑行
    var edit_mode = false;//编辑模式否则为添加状态，是则为修改状态
    var seleAcont = undefined;//暂存左表格，即账册字典中的选择编号
    obj = {
        //增加账册字典
        add: function () {
            var table = $('#right_table');
            if(!$('#left_table').datagrid('getSelected')){
                $.messager.alert('提示', '请选择账册字典条目', 'info');
                return;
            }
            //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
            if (editRow == undefined) {
                edit_mode = false;
                table.datagrid('appendRow', {});
                var rows = table.datagrid('getRows').length;
                //将新插入的那一行开启编辑状态
                table.datagrid('beginEdit', rows - 1);
                //给当前编辑的行赋值
                editRow = rows - 1;
                table.datagrid('scrollTo', editRow);
            }
            else {
                $.messager.alert('提示', '请先保存上一条更新', 'info');
            }
        },

        //保存新增/修改
        save: function () {
            var table = $('#right_table');
            // if (!table.datagrid('validateRow', editRow)) {
            //     $.messager.alert('提示', '账册定义条目内容无效或未填写完整', 'info');
            //     return;
            // }
            if (editRow != undefined) {
                var edit = table.datagrid('getEditors', editRow);
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/AccountBook' + getSavePath(edit_mode),
                    contentType: 'application/json',
                    data: getAccountBookDefData(edit,seleAcont),
                    success: function (result) {
                        if (result.code == 100) {
                            table.datagrid('endEdit', editRow);
                            editRow = undefined;
                            showAccountBookDef(seleAcont);//保存成功，刷新数据
                            $.messager.show({
                                title: '提示',
                                msg: "添加成功"
                            });
                        }
                        else {
                            var data = result.extend.errorInfo;
                            $.messager.alert('提示', data, 'info');
                            showAccountBookDef(seleAcont);
                            editRow = undefined;
                        }
                    }
                });
            }
            else {
                $.messager.alert('提示', '未更新条目', 'info');
            }
        },

        //删除
        remove: function () {
            var table = $('#right_table');
            var row = table.datagrid('getSelected');
            if (row) {
                $.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
                    if (flag) {
                        var auto_id = row.zcdyAutoId;
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/AccountBook/delAccountBookDef/' + auto_id,
                            success: function (result) {
                                if (result.code == 100) {
                                    showAccountBookDef(seleAcont);
                                    $.messager.show({
                                        title: '提示',
                                        msg: '删除成功',
                                    });
                                } else {
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
            $('#helpWin').window('close');
            var ed = $('#right_table').datagrid('getEditor', {index:editRow,field:'itemNo'});
            $(ed.target).textbox('setValue', row.itemNo);
        }
    };

    $('#left_table').datagrid({
        width:'100%',
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
        singleSelect: true,
        url: getRealPath() + '/AccountBook/queryAccountBook',
        onClickRow: function (index, row) {
            if (!editRow == undefined){
                $.messager.alert('提示', '请先保存上一条更新', 'info');
                return;
            }
            var right_table = $('#right_table');
            seleAcont = row.acontBookNo.toString();
            showAccountBookDef(seleAcont);
            switch (row.accBookType){
                case "1":
                case "2":
                case "3":
                    right_table.datagrid('hideColumn','listItemLevel');
                    right_table.datagrid('hideColumn','itemDir');
                    right_table.datagrid('hideColumn','fixCol');
                    right_table.datagrid('hideColumn','pageCol');
                    break;
                case "4":
                    right_table.datagrid('showColumn','listItemLevel');
                    right_table.datagrid('showColumn','itemDir');
                    right_table.datagrid('showColumn','fixCol');
                    right_table.datagrid('showColumn','pageCol');
                    break;
            }
        },
        columns: [[
			{
			    field: 'acontBookNo',
			    title: '账册编号',
			    width: '33%',
			    align: 'center'
			},
			{
			    field: 'accBookName',
			    title: '账册名称',
			    width: '33%',
			    align: 'center'
			},
			{
			    field: 'accBookType',
			    title: '账册类型',
			    width: '33%',
			    align: 'center',
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
			}
        ]]
    });

    $('#right_table').datagrid({
        width: '100%',
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
        singleSelect: true,
        // url: getRealPath() + '/AccountBook/queryAccountBookDef/' + select_acont,
        //双击开启编辑
        onDblClickRow: function (index, row) {
            if(editRow!=undefined){
                $.messager.alert('提示', '请先保存上一条更新', 'info');
                return;
            }
            //开启编辑状态
            edit_mode = true;
            $('#right_table').datagrid('beginEdit', index);
            //给当前编辑的行赋值
            editRow = index;
        },
        columns: [[
			{
			    field: 'itemNo',
			    title: '科目编号',
			    width: 150,
			    align: 'center',
                editor: {
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        buttonText: '选择',
                        iconAlign: 'right',
                        onClickButton: function(index,field,value){
                            $('#helpWin').window('open');
                        }
                    }
                }
			},
			{
			    field: 'accLevel',
			    title: '账页级数',
			    //width: '25%',
			    align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        min: 0,
                        max: 9
                    }
                }
			},
			{
			    field: 'printUse',
			    title: '无余额发生额是否打印',
			    //width: '25%',
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
                field: 'listItemLevel',
                title: '分析栏目级数',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '必填'
                    }
                }
            },
            {
                field: 'itemDir',
                title: '栏目方向',
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
                            text: '借方'
                        },{
                            id: 'D',
                            text: '贷方'
                        }]
                    }
                },
                formatter: function (value, row, index) {
                    switch(value){
                        case "J": return "借方";
                        case "D": return "贷方";
                    }
                }
            },
            {
                field: 'fixCol',
                title: '固定列',
                //width: '25%',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        min: 0,
                        max: 9
                    }
                }
            },
            {
                field: 'pageCol',
                title: '每页列数',
                //width: '25%',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        min: 0,
                        max: 9
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
        toolbar: '#tb_helpWin',
        //双击进入下一级事件
        onDblClickRow : function (rowIndex, rowData) {
            obj.nextLevel_click();
        },
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


    // $('#show_button').linkbutton({
    //     width: 120,
    //     height: 30,
    //     iconCls: 'icon-wage'
    // });
    // $('#add_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-add',
    //     onClick: obj.add()
    // });
    // $('#delete_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-remove',
    //     onClick: obj.remove()
    // });
    // $('#save_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-save',
    //     onClick: obj.save()
    // });

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

//显示账册定义表格
function showAccountBookDef(select_acont) {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountBook/queryAccountBookDef/' + select_acont,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lszcdyList;
                $('#right_table').datagrid('loadData', data);
            }
        }
    });
}

//获取更新的账册定义行数据
function getAccountBookDefData(row, seleAcont) {
    var acontBookNo = seleAcont;
    var itemNo = $(row[0].target).textbox('getValue');
    var accLevel = $(row[1].target).numberbox('getValue');
    var printUse = $(row[2].target).combobox('getValue');
    var listItemLevel = $(row[3].target).numberbox('getValue');
    var itemDir = $(row[4].target).combobox('getValue');
    var fixCol = $(row[5].target).numberbox('getValue');
    var pageCol = $(row[6].target).numberbox('getValue');

    var data = '{"acontBookNo":"' + seleAcont + '",' +
        '"itemNo":"' + itemNo + '",' +
        '"accLevel":"' + accLevel + '",' +
        '"printUse":"' + printUse + '",' +
        '"listItemLevel":"' + listItemLevel + '",' +
        '"itemDir":"' + itemDir + '",' +
        '"fixCol":"' + fixCol + '",' +
        '"pageCol":"' + pageCol + '"}';
    return data;
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

//增加和编辑使用不同的后台更新方式
function getSavePath(edit_mode){
    if(edit_mode){
        return '/editAccountBookDef';
    }
    else{
        return '/saveAccountBookDef';
    }
}