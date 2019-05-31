$(function () {
    $('#box').datagrid({
//  	height:'100%',
        fit:false,
        //url: 'Contacts.json',
        title: '专项核算类别定义',
        iconCls: 'icon-chart',
        rownumbers: true,
        fitColumns:true,
        striped: true,
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '类别编号',
			    align:'center',
                width:100
			},
			{
			    field: 'CatName1',
			    title: '类别名称',
			    align:'center',
                width:100
			},
        ]],
        toolbar: '#tb',
        data: [
			{
			    CompCatNo1: '001000000',
			    CatName1: '北京地区',
			    FinLevel: '是',
			}
        ],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
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













