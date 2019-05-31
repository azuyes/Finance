$(function () {

    obj = {
        editRow : undefined,

        //添加事件
        add : function () {

            if (this.editRow == undefined) {

                var table = $('#box');
                var rows = table.datagrid('getRows').length;
                //添加一行
                $('#box').datagrid('insertRow', {
                    index : rows,
                    row : {
                    },
                });

                //将第一行设置为可编辑状态
                $('#box').datagrid('beginEdit', rows);

                this.editRow = rows;
            }else{
                $.messager.alert('提示', '请先保存，再进行添加！', 'info');
            }
        },
        //保存事件
        save : function () {

            //将第一行设置为结束编辑状态
            $('#box').datagrid('endEdit', this.editRow);
        },

        //删除事件
        remove : function () {
            var row = $('#box').datagrid('getSelected');
            if (row) {//判断是否选中行
                $.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
                    if (flag) {
                        var id = row.catNo;
                        $.ajax({
                            type : 'POST',
                            url:getRealPath()+'/SpAccountCategory/delSpAccountCategoryById/' + id,
                            success : function (result) {
                                if (result.code == 100) {
                                    $('#box').datagrid('load');
                                    $('#box').datagrid('unselectAll');
                                    $.messager.show({
                                        title : '提示',
                                        msg : '删除成功',
                                    });

                                }
                                else{
                                    $.messager.alert('提示', result.extend.errorInfo, 'warning');

                                }
                            },

                        });
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
    };

    $('#box').datagrid({
        fit:false,
        url: getRealPath()+'/SpAccountCategory/getSpAccountCategory',
        title: '专项核算类别定义',
        iconCls: 'icon-chart',
        rownumbers: true,
        fitColumns:true,
        striped: true,
        singleSelect: true,
        columns: [[
			{
			    field: 'catNo',
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
                //     type : 'numberbox',
                //     options : {
                //         required : true,
                //         min : 1,
                //         prefix:'0',
                //         // max : 99,
                //         // parser : function (s) {
                //         //     var p = s.substring(0,1);
                //         //     if(p!=0 && s<10){
                //         //         return "0"+s;
                //         //     }else{
                //         //         return s;
                //         //     }
                //         // },
                //         // max : 99,
                //         // length:[2,2],
                //         // maxlength:2,
                //         // minlength:2,
                //         validType:'length[2,2]',
                //         invalidMessage : '请输入2位数字编号',
                //         missingMessage : '请输入编号',
                //     },
                //
                //
                 },
			},
			{
			    field: 'catName',
			    title: '类别名称',
			    align:'center',
                width:100,
                editor : {
                    type : 'textbox',
                    options : {
                        required : true,
                        validType:'length[0,20]',
                        invalidMessage : '请输入1-20个字符',
                        missingMessage : '请输入类别名称',
                    },
                },
			},
        ]],
        toolbar: '#tb',

        onAfterEdit : function (rowIndex, rowData, changes) {  //编辑结束之后事件
            var data = '{"catNo":"' + rowData.catNo + '","catName":"' + rowData.catName + '"}';

            $.ajax({
                type : 'POST',
                url : getRealPath()+'/SpAccountCategory/saveSpAccountCategory',
                contentType: 'application/json',
                data: data,
                // data : JSON.stringify(rowData),
                beforeSend : function () {
                    $('#box').datagrid('loading');
                },
                success : function (result) {
                    if (result.code == 100) {

                        $('#box').datagrid('loaded');
                        $('#box').datagrid('load');
                        $('#box').datagrid('unselectAll');
                        $.messager.show({
                            title : '提示',
                            msg : '添加成功',
                        });
                        obj.editRow = undefined;
                    }else{
                        $.messager.alert('警告', '类别编号重复，请重新增加！', 'warning');
                        $('#box').datagrid('loaded');
                        $('#box').datagrid('load');
                        obj.editRow = undefined;
                    }
                },
            });
        },
    });

    $('#turnintoDialog').dialog({
        width: 405,
        height: 200,
        title: '路径选择',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
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

    $('#turnoutWin').window({
        width: 400,
        height: 500,
        title: '核算类别定义转出',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
        shadow:true,
    });
    
     $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        //url: 'content.json',
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '类别编号',
			    align:'center',
			    width: 180
			},
			{
			    field: 'CatName1',
			    title: '类别名称',
			    align:'center',
			    width: 200
			},
        ]],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });
});
















