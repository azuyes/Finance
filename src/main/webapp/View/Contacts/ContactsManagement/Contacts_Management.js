$(function () {



    var convertTreeData = function(rows) {
        var nodes = [];
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            nodes.push({
                id:row.catNo1,
                text:row.catName1,
                state: row.finLevel == '0' ? 'closed' : 'open',
                // checked:row.checked,
                // attributes:row.attributes
            });
        }
        return nodes;
    }
    $.fn.combotree.defaults.loadFilter = convertTreeData;


	function fixWidth(percent)
	{  
	    return document.body.clientWidth * percent ; //这里你可以自己做调整  
	}  
	
    $('#box').datagrid({
		height:'100%',
    	width: '100%',
        url: getRealPath()+'/ContactsManagement/getContactsCompany',
        // title: '往来单位类别',
        // iconCls: 'icon-chart',
        loadMsg: '正在加载中，请稍后',
        rownumbers: true,
        fitColumns: false,
        striped: true,
        fit:false,
        singleSelect:true,
        columns: [[
			{
			    field: 'companyNo',
			    title: '单位编号',
			    align:'center',
			    width:'18%',
                //easyUI中datagrid如何展示对象下属性的多个子属性
                formatter:function(value, row, index){
                    if(row.lswldw){
                        return  row.lswldw.companyNo;
                    }
                },
            },
			{
			    field: 'companyName',
			    title: '单位名称',
			    align:'center',
			    width:'20%',
                formatter:function (value,row,index) {
                    if(row.lswldw){
                        return  row.lswldw.companyName;
                    }
                }
			},
			{
			    field: 'catName1',
			    title: '单位类别',
			    align:'center',
			    width:'20%'
			},
            // {
            //     field: 'debitMoney',
            //     title: '借方余额',
            //     align:'center',
            //     width:'20%'
            // },
            // {
            //     field: 'creditMoney',
            //     title: '贷方余额',
            //     align:'center',
            //     width:'20%'
            // },
        ]],
        toolbar: '#tb',
    });

    $('#catName1').combotree({
        width:173,
        url: getRealPath()+'/ContactsManagement/getContactsCategory',
        required:true,
        missingMessage:'请选择单位的分类!',
        animate : true,
        checkbox : true,
        cascadeCheck : false,
        onlyLeafCheck : true,
        lines : true,
        dnd : false,

        // onBeforeSelect:function(node){
        //     if(node.state){
        //         $("#catName1").combotree("unselect");
        //     }
        // },
        onBeforeSelect: function(node) {
            if (!$(this).tree('isLeaf', node.target)) {
                return false;
            }
        },
        onClick: function(node) {
            if (!$(this).tree('isLeaf', node.target)) {
                $('#catName1').combotree('showPanel');
            }
        },

        onLoadSuccess:function (){   //在数据加载成功以后触发方法
            var t = $('#catName1').combotree('tree');
            t.tree('expandAll');//展开所有节点

        },


    });


    $('#catName_edit').combotree({
        width:173,
        url: getRealPath()+'/ContactsManagement/getContactsCategory',
        required:true,
        missingMessage:'请选择单位的分类!',
        animate : true,
        checkbox : true,
        cascadeCheck : false,
        onlyLeafCheck : true,
        lines : true,
        dnd : false,

        // onBeforeSelect:function(node){
        //     if(node.state){
        //         $("#catName1").combotree("unselect");
        //     }
        // },
        onBeforeSelect: function(node) {
            if (!$(this).tree('isLeaf', node.target)) {
                return false;
            }
        },
        onClick: function(node) {
            if (!$(this).tree('isLeaf', node.target)) {
                $('#catName_edits').combotree('showPanel');
            }
        },

        onLoadSuccess:function (){   //在数据加载成功以后触发方法
            var t = $('#catName_edit').combotree('tree');
            t.tree('expandAll');//展开所有节点

        },


    });

    $('#datailForm').dialog({
        width: 530,
        height: 330,
        title: '单位详细信息',
        modal: true,
        closed : true,
        buttons: [{
            text: '存盘',
            plain: true,
            iconCls: 'icon-save',
            handler: function () {
                if ($('#datailForm').form('validate')) {
                    var companyNo = $("#companyNo_input").val();
                    var companyName = $("#companyName_input").val();
                    var cont = $("#cont_input").val();
                    var tel = $("#tel_input").val();
                    var bank = $("#bank_input").val();
                    var account = $("#account_input").val();
                    var creditStanding = $("#creditStanding_input").val();
                    var taxIdNo = $("#taxIdNo_input").val();
                    var companyPost = $("input[name='companyPost_input']").val();
                    var companyAddr = $("#companyAddr_input").val();
                    var memo = $("#memo_input").val();

                    var t1 = $('#catName_edit').combotree('tree'); //获取修改后的单位分类编号
                    var node1 = t1.tree('getSelected').id;

                    var data = '{"companyNo":"' + companyNo + '","companyName":"' + companyName + '","cont":"' + cont + '","tel":"'+tel+'","bank":"'+bank+'","account":"'+account+'","creditStanding":"'+creditStanding+'","taxIdNo":"'+taxIdNo+'","companyPost":"'+companyPost+'","companyAddr":"'+companyAddr+'","memo":"'+memo+'","catNo1":"'+node1+'"}';

                    $.ajax({
                        url :  getRealPath() + '/ContactsManagement/updateContactsCompanyById',
                        contentType:'application/json;charset=utf-8',
                        data : data,
                        type : 'post',
                        beforeSend: function () {
                            $.messager.progress({
                                text: '正在修改中...',
                            });
                        },
                        success: function (result) {
                            $.messager.progress('close');

                            if (result.code == 100) {
                                $.messager.show({
                                    title: '提示',
                                    msg: '修改管理成功',
                                });
                                $('#datailForm').dialog('close').form('reset');
                                $('#box').datagrid('reload');
                            } else {
                                $.messager.alert('修改失败！', '未知错误或没有任何修改，请重试！', 'warning');
                            }
                        }
                    });
                }
            }
            }, {
            text: '打印',
            plain: true,
            iconCls: 'icon-print',
            handler: function () {

            }
            }, {
                text: '退出',
                plain: true,
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#datailForm').dialog('close');
                }
            }],
        });

    $('#batchDefinitionWin').window({
        width: 650,
        height: 500,
        title: '请选择往来单位编号和科目编号',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
    });

    $('#companyTable').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        url: getRealPath()+'/ContactsManagement/getContactsCompanyOfDefin',
        iconCls: 'icon-chart',
        idField:'companyNo',
        columns: [[
			{
			    field: 'companyNo',
			    title: '单位编号',
			    align:'center',
			    width: 140
			},
			{
			    field: 'companyName',
			    title: '单位名称',
			    align:'center',
			    width: 140
			},
			
        ]],
        toolbar: [{
            id: 'selectAll1_btn',
            text: '全选',
            iconCls: 'icon-ok',
            handler: function () {
                allselectRow("companyTable");
            }
        }, '-', {
            id: 'noSelect1_btn',
            text: '不选',
            iconCls: 'icon-remove',
            handler: function () {
                clearSelections("companyTable");
            }
        }],

        onUnselect:function(index,row){              //当点击 checkbox 时触发
            // if (checked) {
            //     var parentNode = $("#specialObjectTree1").tree('getParent', node.target);
            //     console.log(parentNode);
            //     if (parentNode != null) {
            //         $("#specialObjectTree1").tree('check', parentNode.target);
            //     }
            // }
            // } else {
            //     var childNode = $("#specialObjectTree1").tree('getChildren', node.target);
            //     if (childNode.length > 0) {
            //         for (var i = 0; i < childNode.length; i++) {
            //             $("#specialObjectTree1").tree('uncheck', childNode[i].target);
            //         }
            //     }
            // }
            $.ajax({
                type: 'POST',
                url: getRealPath() + '/ContactsManagement/judgeUnCheckedCompany/' + row.companyNo,
                contentType: 'application/json',
                success: function (result) {
                    if(result.code == 100) {
                        // $.messager.alert('提示！', '恭喜你，关联成功！', 'info');
                    }
                    else{
                        $.messager.alert('警告操作！', '该往来单位中还有账务，不能取消！', 'warning');
                        $('#companyTable').datagrid('selectRecord', row.companyNo);

                    }
                }
            });
        }
    });

    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        url: getRealPath()+'/ContactsManagement/getSpecialItemOfDefin',
        iconCls: 'icon-chart',
        idField:'itemNo',
        columns: [[
			{
			    field: 'itemNo',
			    title: '科目编号',
			    align:'center',
			    width: 200
			},
			{
			    field: 'itemName',
			    title: '科目名称',
			    align:'center',
			    width: 140
			},
			
        ]],
        toolbar: [{
            id: 'selectAll2_btn',
            text: '全选',
            iconCls: 'icon-ok',
            handler: function () {
                allselectRow("itemTable");
            }
        }, '-', {
            id: 'noSelect2_btn',
            text: '不选',
            iconCls: 'icon-remove',
            handler: function () {
                clearSelections("itemTable");
            }
        }],

        onUnselect:function(index,row){                 //当点击 checkbox 时触发

            $.ajax({
                type: 'POST',
                url: getRealPath() + '/ContactsManagement/judgeUnCheckedItem/' + row.itemNo,
                contentType: 'application/json',
                success: function (result) {
                    if(result.code == 100) {
                        // $.messager.alert('提示！', '恭喜你，关联成功！', 'info');
                    }
                    else{
                        $.messager.alert('警告操作！', '该科目关联的往来单位还有账务，不能取消！', 'warning');
                        $('#itemTable').datagrid('selectRecord', row.itemNo);

                    }
                }
            });
        }
    });

});


function searchTable() {
    searchHelp("#box","#search_input",'companyNo','companyName');
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

    var i = rowIndex +1;
    for (i; i < gridData.total; i++) {
        //console.log(gridData.rows[i][no]);
        var lswldw_row = gridData.rows[i];
        if(lswldw_row.lswldw.companyNo.search(val) != -1 || lswldw_row.lswldw.companyName.search(val) != -1){
            $(eleTable).datagrid("selectRow", i);
            return ;
        }
    }
    if(i == gridData.total ){
        $.messager.alert('提示', '向下没有找到！', 'info');
    }
}

function judgeSelected(ele){
    if($(ele).datagrid("getData").total>0){
        $(ele).datagrid("selectRow", 0);
    }
}

function add() {
    $('#add_dialog').dialog('open');
    $('input[name="companyNo"]').focus();
}

//存盘事件
function save() {
    var t = $('#catName1').combotree('tree');
    var node = t.tree('getSelected').id;
    var data = '{"companyNo":"' + $("#companyNo").val() + '","companyName":"' + $("#companyName").val() + '","catNo1":"' + node + '"}';

    if($('#add_form').form("validate")) {//判断校验是否通过
        $.ajax({
            type: 'POST',
            url: getRealPath() + '/ContactsManagement/saveContactsCompany',
            contentType:'application/json;charset=utf-8',
            data: data,
            beforeSend : function () {
                $.messager.progress({
                    text : '正在新增中...',
                });
            },
            success : function (result) {
                $.messager.progress('close');

                if (result.code == 100) {
                    $.messager.show({
                        title : '提示',
                        msg : '新增管理成功',
                    });
                    $('#box').datagrid('reload');
                    $('#add_dialog').dialog('close')
                    $('#add_form').form('clear');
                } else {
                    $.messager.alert('提示', '单位编号重复，请重试！', 'warning');
                    $('#add_form').form('clear');
                }
            },
        });
    }else{
        return $('#add_form').form('validate');
    }
}

function deleteClick(){
    var rows = $('#box').datagrid('getSelections');
    if (rows.length > 1) {
        $.messager.alert('警告操作！', '只能选定一条数据！', 'warning');
    } else if (rows.length == 1) {

        $.ajax({
            url :  getRealPath() + '/ContactsManagement/deleteContactsCompanyById/'+rows[0].lswldw.companyNo,
            type : 'post',

            success : function (result) {

                if (result.code ==100) {
                    $.messager.alert('成功！', '恭喜你，删除成功！', 'info');
                    $('#box').datagrid('reload');
                    judgeSelected("#box");
                } else if(result.code == 101){
                    $.messager.alert('失败！', '该往来单位还有数据，不能删除！', 'warning');
                }
            }
        });
    } else if (rows.length == 0) {
        $.messager.alert('警告操作！', '请选择一行要删除的数据！', 'warning');
    }
}

function edit(){
        var rows = $('#box').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert('警告操作！', '只能选定一条数据！', 'warning');
        } else if (rows.length == 1) {
            $.ajax({
                url :  getRealPath() + '/ContactsManagement/getContactsCompanyById/'+rows[0].lswldw.companyNo,
                type : 'post',
                beforeSend : function () {
                    $.messager.progress({
                        text : '正在获取中...',
                    });
                },
                success : function (result) {
                    $.messager.progress('close');

                    if (result.code ==100) {
                        var obj = result.extend.lswldw;
                        $('#catName_edit').combotree("setValue", rows[0].catName1);
                        $('#datailForm').form('load', {
                            companyNo_input : obj.companyNo,
                            companyName_input : obj.companyName,
                            cont_input : obj.cont,
                            tel_input : obj.tel,
                            bank_input : obj.bank,
                            account_input : obj.account,
                            creditStanding_input : obj.creditStanding,
                            taxIdNo_input : obj.taxIdNo,
                            companyPost_input : obj.companyPost,
                            companyAddr_input : obj.companyAddr,
                            memo_input : obj.memo,
                        }).dialog('open');
                    } else {
                        $.messager.alert('获取失败！', '未知错误导致失败，请重试！', 'warning');
                    }
                }
            });
        } else if (rows.length == 0) {
            $.messager.alert('警告操作！', '请至少选定一条数据！', 'warning');
        }


}


//全选
function allselectRow(tableName) {
    $('#' + tableName).datagrid('selectAll');
}

//全清
function clearSelections(tableName) {
    $('#' + tableName).datagrid('clearSelections');
}

function batchDefine(){

    var rowsOfCompany = $('#companyTable').datagrid('getSelections');
    var rowsOfItem = $('#itemTable').datagrid('getSelections');
    //var companyJson = JSON.parse(rowsOfCompany);
    // $.parseJSON(rowsOfCompany);


    if(rowsOfCompany.length == 0){
        $.messager.alert('警告操作！', '请至少选定一条数据！', 'warning');
    }else if(rowsOfItem.length == 0){
        $.messager.alert('警告操作！', '请至少选定一条数据！', 'warning');
    }else{
        // var data = '{"rowsOfCompany":"' + rowsOfCompany + '","rowsOfItem":"' + rowsOfItem + '"}';
        // for(var i=0;i<rowsOfCompany.length;i++){
        //     data.append("companyNo",rowsOfCompany[i].companyNo);
        // }
        // for(var i=0;i<rowsOfItem.length;i++){
        //     data.append("itemNo",rowsOfItem[i].itemNo);
        // }
        // console.log(data);
        var companyNos = [];
        var itemNos = [];
        for (var i = 0; i < rowsOfCompany.length; i ++) {
            companyNos.push(rowsOfCompany[i].companyNo);
        }
        for (var i = 0; i < rowsOfItem.length; i ++) {
            itemNos.push(rowsOfItem[i].itemNo);
        }
        $.ajax({
            url :  getRealPath() + '/ContactsManagement/defineOfCompanyAndItem/'+companyNos+'/'+itemNos,
            type : 'post',
            beforeSend : function () {
                $.messager.progress({
                    text : '正在关联中...',
                });
            },
            success : function (result) {
                $.messager.progress('close');

                if (result.code ==100) {
                    $.messager.alert('批量定义成功！', '恭喜你，批量定义车成功！', 'info');
                } else {
                    $.messager.alert('批量定义失败！', '未知错误导致失败，请重试！', 'warning');
                }
            }
        });
    }


}

function getDefinedOfCompanyAndItem(){
    $.ajax({
        url :  getRealPath() + '/ContactsManagement/getDefinedOfCompanyAndItem',
        type : 'post',
        success : function (result) {
            //var jsonObj =  JSON.parse(result);//转换为json对象
            for(var i=0;i<result.length;i++){
                $('#companyTable').datagrid('selectRecord',result[i].companyNo);
                $('#itemTable').datagrid('selectRecord',result[i].itemNo);
            }
            // companyTable
            // itemTable
        }
    });
};

function batchDefinitionClick(){
    $('#batchDefinitionWin').dialog('open');
    getDefinedOfCompanyAndItem();
}





// //显示当前级的单位
// function showCompany(){
//     $.ajax({
//         url: getRealPath()+'/ContactsManagement/getContactsCompany',
//         success: function (result) {
//                 $('#box').datagrid('loadData', result);
//         }
//     });
// }














