$(function () {
	
	function fixWidth(percent)  
	{  
	    return document.body.clientWidth * percent ; //这里你可以自己做调整  
	}  
	
    $('#box').datagrid({
		height:'100%',
    	width: '100%',
        //url: 'content.json',
        title: '往来单位类别',
        iconCls: 'icon-chart',
        rownumbers: true,
        fitColumns: false,
        striped: true,
        fit:false,
        columns: [[
			{
			    field: 'CompanyNo',
			    title: '单位编号',
			    align:'center',
			    width:'18%'
			},
			{
			    field: 'CompanyName',
			    title: '单位名称',
			    align:'center',
			    width:'20%'
			},
			{
			    field: 'FinLevel',
			    title: '单位类别',
			    align:'center',
			    width:'20%'
			},
            {
                field: 'DebitMoney',
                title: '借方余额',
                align:'center',
                width:'20%'
            },
            {
                field: 'CreditMoney',
                title: '贷方余额',
                align:'center',
                width:'20%'
            },
        ]],
        
        toolbar: '#tb',
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });

    $('#detailsDialog').dialog({
        width: 600,
        height: 330,
        title: '单位详细信息',
        modal: true,
        buttons: [{
            text: '存盘',
            plain: true,
            iconCls: 'icon-save',
            handler: function () {
                
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
                $('#detailsDialog').dialog('close');
            }
        }],
    });

    $('#batchDefinitionWin').window({
        width: 480,
        height: 500,
        title: '请选择往来单位编号和科目编号',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#companyTable').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        //url: 'content.json',
        iconCls: 'icon-chart',
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '单位编号',
			    align:'center',
			    width: 110
			},
			{
			    field: 'CatName1',
			    title: '单位名称',
			    align:'center',
			    width: 110
			},
			
        ]],
        toolbar: [{
            id: 'selectAll1_btn',
            text: '全选',
            iconCls: 'icon-ok',
            handler: function () {
                ShowAddDialog();//实现添加记录的页面
            }
        }, '-', {
            id: 'noSelect1_btn',
            text: '不选',
            iconCls: 'icon-remove',
            handler: function () {
                ShowEditDialog();//实现修改记录的方法
            }
        }],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });

    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        //url: 'content.json',
        iconCls: 'icon-chart',
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '科目编号',
			    align:'center',
			    width: 115
			},
			{
			    field: 'CatName1',
			    title: '科目名称',
			    align:'center',
			    width: 115
			},
			
        ]],
        toolbar: [{
            id: 'selectAll2_btn',
            text: '全选',
            iconCls: 'icon-ok',
            handler: function () {
                ShowAddDialog();//实现添加记录的页面
            }
        }, '-', {
            id: 'noSelect2_btn',
            text: '不选',
            iconCls: 'icon-remove',
            handler: function () {
                ShowEditDialog();//实现修改记录的方法
            }
        }],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });
});













