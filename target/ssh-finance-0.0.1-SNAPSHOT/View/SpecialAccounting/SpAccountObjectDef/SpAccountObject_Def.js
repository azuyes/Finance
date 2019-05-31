$(function () {
    $('#box').dialog({
        width: 350,
        height: 190,
        title: '专项核算类别',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#box').dialog('close');
                document.getElementById("specialDef").style.visibility = "visible";

            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#box').dialog('close');
            }
        }],
    });

    $('#helpWin').window({
        width: 480,
        height: 500,
        title: '专项核算类别',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#initTable').datagrid({
        //width: 100,
        //height: 100,
        fit: true,
        //url: 'content.json',
        rownumbers: true,
        fitColumns:true,
        striped: true,
        columns: [[
			{
			    field: 'CompCatNo14',
			    title: '编号',
			    align:'center',
			    width: '35%'
			},
			{
			    field: 'CatName15',
			    title: '核算对象',
			    align:'center',
			    width: '35%'
			},
			{
			    field: 'FinLevel6',
			    title: '明细否',
			    align:'center',
			    width: '30%'
			},

        ]],
        toolbar: '#tb',
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });

    $('#itemTable').datagrid({
//      width:'100%',
        height:'100%',
        fit: true,
        fitColumns: false,
        //url: 'content.json',
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '科目编号',
			    align:'center',
			    width: 100
			},
			{
			    field: 'CatName1',
			    title: '科目名称',
			    align:'center',
			    width: 120
			},
            {
                field: 'CatName1',
                title: '明细否',
                align:'center',
                width: 40
            },
            {
                field: 'CatName1',
                title: '级数',
                align:'center',
                width: 40
            },

        ]],
        toolbar: [{
            id: 'upperlevel_btn',
            text: '上级',
            iconCls: 'icon-back',
            handler: function () {
                ShowAddDialog();//实现添加记录的页面
            }
        }, '-', {
            id: 'nextlevel_btn',
            text: '下级',
            iconCls: 'icon-next',
            handler: function () {
                ShowEditDialog();//实现修改记录的方法
            }
        },'-', {
            id: 'details_btn',
            text: '选定',
            iconCls: 'icon-ok',
            handler: function () {
            	$('#helpWin').window('close');
            	$('#box').dialog('close');
                document.getElementById("specialDef").style.visibility = "visible";
//              document.getElementById("helpWin").window('close');
            }
        }],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });
})