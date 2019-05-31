$(function () {
    //获取项目的相对路径
    var catLevel = 1;//级数
    var catNo = null ;//编号,在点击下级的时候获得，用于返回上一级

    obj = {
        editRow : undefined,
        //上一级事件
        uperLevel_click : function( ){
            obj.editRow = undefined;
            if(catLevel != 1) { //判断是否为第一级
                var id, levelFLag;//当前为 一级是id是前两位，二级时id为前四位
                switch (catLevel) {
                    case 2:
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ContactsCategory/getContactsCategory/1',
                            contentType: 'application/json',
                            success: function (result) {
                                $('#box').datagrid('loadData', result);
                                catLevel = 1;
                            }
                        });
                        break;
                    case 3:
                        id = catNo.toString().substring(0, 2);
                        levelFLag = 2;
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ContactsCategory/queryContactsCategoryByLevel/' + id + '/' + levelFLag,
                            contentType: 'application/json',
                            success: function (result) {
                                if (result.code == 100) {
                                    var data = result.extend.lswlflList;
                                    $('#box').datagrid('loadData', data);
                                    catLevel = levelFLag;
                                }
                            },
                        });
                        break;
                }
            }else{
                $.messager.alert('提示', '当前已为第一级！', 'info');
            }
        },

        //下级事件
        nextLevel_click:function () {
            obj.editRow = undefined;
            var row = $('#box').datagrid('getSelected');
            if (row) {//判断是否选中行
                if(catLevel != 3){//判断是否为末级
                        catNo = row.catNo1; //获取选中的分类编号
                        var id, levelFLag;//当前为 一级是id是前两位，二级时id为前四位
                        switch (catLevel) {
                            case 1:
                                id = catNo.toString().substring(0, 2);
                                levelFLag = 2;
                                break;
                            case 2:
                                id = catNo.toString().substring(0, 4);
                                levelFLag = 3;
                                break;
                        }
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ContactsCategory/queryContactsCategoryByLevel/' + id + '/' + levelFLag,
                            contentType: 'application/json',
                            success: function (result) {
                                if (result.code == 100) {
                                    var data = result.extend.lswlflList;
                                    $('#box').datagrid('loadData', data);
                                }
                            },
                        });
                        catLevel = levelFLag;
                }else{
                    $.messager.alert('提示', '当前已为末页！', 'info');
                }
            }else{
                $.messager.alert('提示', '请选择一条记录！', 'info');
            }
        },

        //增加事件
        add : function () {

            if (this.editRow == undefined) {
                var table = $('#box');
                var rows = table.datagrid('getRows').length;
                //将新插入的那一行开启编辑状态
                ///table.datagrid('beginEdit', rows-1);
                //添加一行
                table.datagrid('insertRow', {
                    index : rows,
                    row : {
                    },
                });
                //$('#initTable').datagrid('addRow');
                //将第一行设置为可编辑状态
                $('#box').datagrid('beginEdit', rows);

                this.editRow = rows;
            }else{
                $.messager.alert('提示', '请先保存，再进行添加！', 'info');
            }
        },

        //删除事件
        remove : function () {
            var row = $('#box').datagrid('getSelected');
            if (row) {//判断是否选中行
                $.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
                    if (flag) {
                        if(row.finLevel.toString() == "是"){
                            var ids = row.catNo1;
                            $.ajax({
                                type : 'POST',
                                url:getRealPath()+'/ContactsCategory/delContactsCategory/' + ids + '/' + catLevel,
                                success : function (result) {
                                    if (result.code == 100) {
                                        var data = result.extend.lswlflList;
                                        $('#box').datagrid('loadData', data);
                                        $.messager.show({
                                            title : '提示',
                                            msg : '删除成功',
                                        });
                                    }else{
                                        $.messager.alert('提示', result.extend.errorInfo, 'warning');

                                    }
                                },

                            });
                        }else{
                            $.messager.alert('提示', '当前类别不是末级，不能删除！', 'info');
                        }
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'info');
            }
        },

        cancel : function(){
            this.editRow = undefined;
            $('#box').datagrid('rejectChanges');
        },

        //保存事件
        save : function () {
            //将第一行设置为结束编辑状态
            $('#box').datagrid('endEdit', this.editRow);

        },

        //存盘事件
        // save:function () {
        //     var num ,id;
        //     var catNoSubString = null;
        //     var catNo_Input = rowData.catNo1;
        //     switch (catLevel){
        //         case 1 :
        //             num = catNo_Input+"0000";
        //             break;
        //         case 2 :
        //             id = catNoSubString = catNo.substring(0,2);
        //             num = catNoSubString+catNo_Input+"00";
        //             break;
        //         case 3 :
        //             id = catNoSubString = catNo.substring(0,4);
        //             num = catNoSubString+catNo_Input;
        //             break;
        //     }
        //     var data = '{"catNo1":"' + num + '","catName1":"' + $("input[name='catName1']").val() + '","catLevel":"' + catLevel + '","finLevel":"1"}';
        //
        //     if($('#ff').form("validate")) {//判断校验是否通过
        //         $.ajax({
        //             type: 'POST',
        //             url: getRealPath() + '/ContactsCategory/saveContactsCategory',
        //             contentType: 'application/json',
        //             data: data,
        //             success: function (result) {
        //                 if (result.code == 100) {
        //                     var data = result.extend.lswlflList;
        //                     $('#box').datagrid('loadData', data);
        //                     //保存成功，关闭对话框，刷新数据最后一页
        //                     $("#add_or_update").dialog('close');
        //                     $.messager.show({
        //                         title: '提示',
        //                         msg: "添加成功",
        //                     });
        //                 } else {
        //                     var data = result.extend.errorInfo;
        //                     $.messager.alert('提示', data, 'info');
        //                     $('#ff').form('clear');
        //                 }
        //             },
        //         });
        //     }
        //     else{
        //         return $('#ff').form('validate');
        //     }
        // },


    };

    $('#box').datagrid({
        url: getRealPath()+'/ContactsCategory/getContactsCategory/1',
        title: '往来单位类别',
        iconCls: 'icon-chart',
        fitColumns:true,
        striped: true,
        loadMsg: '正在加载中，请稍后',
        singleSelect: true,
        columns: [[
			{
			    field: 'catNo1',
			    title: '类别编号',
			    align:'center',
                width:100,
                editor : {
                    type : 'textbox',
                    options : {
                        required : true,
                        validType:'length[2,2]',
                        invalidMessage : '请输入2位数字编号',
                        missingMessage : '请输入编号',
                    },
                },

			},
			{
			    field: 'catName1',
			    title: '类别名称',
			    align:'center',
                width:200,
                editor : {
                    type : 'textbox',
                    options : {
                        required : true,
                        validType:'length[0,20]',
                        invalidMessage : '请输入1-20个字符！',
                        missingMessage : '请输入往来类别名称！',
                    },
                },

			},
			{
			    field: 'finLevel',
			    title: '明细否',
			    align:'center',
                width:50,
			},
        ]],
        toolbar:'#tb',

        onAfterEdit : function (rowIndex, rowData, changes) {  //编辑结束之后事件

            var num ,id;
            var catNoSubString = null;
            var catNo_Input = rowData.catNo1;
            switch (catLevel){
                case 1 :
                    num = catNo_Input+"0000";
                    break;
                case 2 :
                    id = catNoSubString = catNo.substring(0,2);
                    num = catNoSubString+catNo_Input+"00";
                    break;
                case 3 :
                    id = catNoSubString = catNo.substring(0,4);
                    num = catNoSubString+catNo_Input;
                    break;
            }
            var data = '{"catNo1":"' + num + '","catName1":"' + rowData.catName1 + '","catLevel":"' + catLevel + '"}';

            $.ajax({
                type: 'POST',
                url: getRealPath() + '/ContactsCategory/saveContactsCategory',
                contentType: 'application/json',
                data: data,
                success: function (result) {
                    if (result.code == 100) {
                        var data = result.extend.lswlflList;
                        $('#box').datagrid('loadData', data);
                        //保存成功，关闭对话框，刷新数据最后一页
                        $.messager.show({
                            title: '提示',
                            msg: "添加成功",
                        });
                        obj.editRow = undefined;
                    } else {
                        var data = result.extend.errorInfo;
                        $.messager.alert('提示', data, 'info');
                        obj.editRow = undefined;
                    }
                },
            });
        },

        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件
            obj.editRow = undefined;
            if(catLevel != 3){ //判断是否为末级
                catNo = rowData.catNo1;
                var id,levelFLag;//当前为 一级是id是前两位，二级时id为前四位
                switch (catLevel){
                    case 1:
                        id=catNo.toString().substring(0,2);
                        levelFLag = 2;
                        break;
                    case 2: id=catNo.toString().substring(0,4);
                        levelFLag = 3;
                        break;
                }
                $.ajax({
                    type : 'POST',
                    url : getRealPath()+'/ContactsCategory/queryContactsCategoryByLevel/'+id+'/'+levelFLag,
                    contentType : 'application/json',
                    success : function(result) {
                        if(result.code == 100) {
                            var data = result.extend.lswlflList;
                            $('#box').datagrid('loadData', data);
                        }

                    },

                });
                catLevel = catLevel+1;
            }else{
                $.messager.alert('提示', '当前已是末级', 'info');
            }
        }

    });

    $('#turnintoDialog').dialog({
        width: 500,
        height: 400,
        title: '往来分类转入',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#importExcelForm').attr('action',getRealPath()+'/ContactsCategory/ImportExcelContactsCategoryByLevel');
                $('#importExcelForm').submit();
                $('#turnintoDialog').dialog('close');
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#turnintoDialog').dialog('close');
            }
        }],
    });

    $('#turnoutDialog').dialog({
        width: 345,
        height: 190,
        title: '往来分类转出',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#turnoutDialog').dialog('close');
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#turnoutDialog').dialog('close');
            }
        }],
    });

    $('#catNo1Id').numberbox({
        min : 1,
        max : 99,
        height:20,
        parser : function (s) {
            var p = s.substring(0,1);
            if(p!=0 && s<10){
                return "0"+s;
            }else{
                return s;
            }
        },

        // formatter : function (value) {
        //     switch (level_){
        //         case 1:value = value+"0000";break;
        //         case 2:value = upper_No_.substring(0,2)+value+"00";break;
        //         case 3:value  = upper_No_.substring(0,4)+value;break;
        //     }
        //     return  value;
        // }
    });

    $('#catNo1Id').numberbox('textbox').attr('maxlength', 2);



});
//转出事件
function exportExcel_click() {
    //window.open('http://localhost:8080/ssh-finance/ContactsCategory/ExportExcelContactsCategoryByLevel/1','','height=500,width=611,scrollbars=yes,status=yes');
    var url = getRealPath()+ "/ContactsCategory/ExportExcelContactsCategoryByLevel/1";
    window.open(url,"_blank");
}

//转入事件
function importExcel_click() {
    // var url = getRealPath()+ "/ContactsCategory/ImportExcelContactsCategoryByLevel";
    // window.open(url,"_blank");
alert("111");
    $.ajax({
        type : 'POST',
        url : getRealPath()+'/ContactsCategory/ImportExcelContactsCategoryByLevel',
        success : function(result) {
            alert(result.msg);
        },

    });
}










