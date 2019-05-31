$(function () {
	
	function fixWidth(percent)  
	{  
	    return document.body.clientWidth * percent ; //这里你可以自己做调整  
	}  
	
    $('#box').dialog({
        width: 350,
        height: 190,
        title: '往来单位科目选择',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#box').dialog('close');
                document.getElementById("initializtion").style.visibility = "visible";
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
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#initTable').datagrid({
        fit: true,
        //url: 'content.json',,
        rownumbers: true,
        fitColumns:true,
        striped: true,
        rownumbers: true,
        striped: true,
        fit:false,
        columns: [[
			{
			    field: 'CompCatNo14',
			    title: '单位编号',
			    align:'center',
			    width:'10%'
			},
			{
			    field: 'CatName15',
			    title: '单位名称',
			    align:'center',
			    width:'10%'
			},
			{
			    field: 'FinLevel6',
			    title: '年初金额',
			    align:'center',
			    width:'10%'
			},
            {
                field: 'FinLevel7',
                title: '本年借方金额',
                align:'center',
                width:'10%'
            },
            {
                field: 'FinLevel8',
                title: '本年贷方金额',
                align:'center',
                width:'10%'
            },
            {
                field: 'FinLevel9',
                title: '当前金额',
                align:'center',
                width:'10%'
            },
            {
			    field: 'FinLevel6',
			    title: '年初数量',
			    align:'center',
			    width:'10%'
			},
            {
                field: 'FinLevel7',
                title: '本年借方数量',
                align:'center',
                width:'10%'
            },
            {
                field: 'FinLevel8',
                title: '本年贷方数量',
                align:'center',
                width:'10%'
            },
            {
                field: 'FinLevel9',
                title: '当前数量',
                align:'center',
                width:'10%'
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
        width: '100%',
        height:'100%',
        //url: 'content.json',
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '编号',
			    width: 120
			},
			{
			    field: 'CatName1',
			    title: '名称',
			    width: 184
			},
			{
			    field: 'FinLevel',
			    title: '明细否',
			    width: 80
			},
            {
                field: 'FinLevel',
                title: '级数',
                width:  80
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
            	document.getElementById("initializtion").style.visibility = "visible";
//              document.getElementById("helpWin").window('close');
            }
        }],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });


});


