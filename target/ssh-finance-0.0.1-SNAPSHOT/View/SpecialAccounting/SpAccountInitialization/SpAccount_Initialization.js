$(function () {
    $('#initTable').datagrid({
        width: '100%',
        height: '100%',
        fit: false,
        //url: 'content.json',
        rownumbers: true,
//      fitColumns:true,
        striped: true,
        columns: [[
			{
			    field: 'CompCatNo14',
			    title: '科目',
			    align: 'center',
			    width: '12%',
			},
			{
			    field: 'CatName15',
			    title: '科目名称',
			    align: 'center',
			    width: '14%',
			},
			{
			    field: 'FinLevel6',
			    title: '明细否',
			    align: 'center',
			    width: '12.5%',
			},
            {
                field: 'FinLevel7',
                title: '核算类别',
                align: 'center',
                width: '12.5%',
            },
            {
                field: 'CompCatNo14',
                title: '年初余额',
                align: 'center',
                width: '12.5%',
            },
			{
			    field: 'CatName15',
			    title: '本年借方',
			    align: 'center',
			    width: '12.5%',
			},
			{
			    field: 'FinLevel6',
			    title: '本年贷方',
			    align: 'center',
			    width: '12.5%',
			},
            {
                field: 'FinLevel7',
                title: '当前余额',
                align: 'center',
                width: '12.5%'
            },

        ]],
        toolbar: '#tb',
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });

    $('#specialWin1').window({
        width: 1000,
        height: 600,
        title: '专项核算初始',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable:false,
    });

    $('#specialWin2').window({
        width: 1000,
        height: 600,
        title: '专项核算初始',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#specialTable').datagrid({
        width: '100%',
        height: '100%',
        iconCls: 'icon-chart',
        rownumbers: true,
//      fitColumns:true,
        striped: true,
        columns: [[
			{
			    field: 'CompCatNo14',
			    title: '编号',
			    align: 'center',
			    width: '12%'                                                 
			},
			{
			    field: 'CatName15',
			    title: '核算对象',
			    align: 'center',
			    width: '12.5%'
			},
			{
			    field: 'FinLevel6',
			    title: '明细否',
			    align: 'center',
			    width: '8%'
			},
            {
                field: 'FinLevel7',
                title: '年初金额',
                align: 'center',
                width: '12%'
            },
            {
                field: 'CompCatNo14',
                title: '本年借方金额',
                align: 'center',
                width: '12%'
            },
			{
			    field: 'CatName15',
			    title: '本年贷方金额',
			    align: 'center',
			    width: '12%'
			},
			{
			    field: 'FinLevel6',
			    title: '本年贷方',
			    align: 'center',
			    width: '12%'
			},
            {
                field: 'FinLevel7',
                title: '当前金额',
                align: 'center',
                width: '12%'
            },

        ]],
        toolbar: [{
            id: 'upperlevel_btn',
            text: '上级',
            iconCls: 'icon-back',
            handler: function () {
                ShowEditDialog();//实现修改记录的方法
            }
            
        }, '-', {
            id: 'nextlevel_btn',
            text: '下级',
            iconCls: 'icon-next',
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

    $('#specialTable2').datagrid({
        width: '100%',
        height: '100%',
        iconCls: 'icon-chart',
        rownumbers: true,
//      fitColumns:true,
        striped: true,
        columns: [[
			{
			    field: 'CompCatNo14',
			    title: '编号',
			    align: 'center',
			    width: '8%'
			},
			{
			    field: 'CatName15',
			    title: '核算对象',
			    align: 'center',
			    width: '10%'
			},
			{
			    field: 'FinLevel6',
			    title: '明细否',
			    align: 'center',
			    width: '10%'
			},
            {
                field: 'FinLevel7',
                title: '年初金额',
                align: 'center',
                width: '10%'
            },
            {
                field: 'CompCatNo14',
                title: '本年借方金额',
                align: 'center',
                width: '10%'
            },
			{
			    field: 'CatName15',
			    title: '本年贷方金额',
			    align: 'center',
			    width: '10%'
			},
            {
                field: 'FinLevel7',
                title: '当前金额',
                align: 'center',
                width: '10%'
            },
            {
                field: 'FinLevel7',
                title: '年初数量',
                align: 'center',
                width: '10%'
            },
            {
                field: 'CompCatNo14',
                title: '本年借方数量',
                align: 'center',
                width: '10%'
            },
			{
			    field: 'CatName15',
			    title: '本年贷方数量',
                align: 'center',			    
			    width: '10%'
			},
            {
                field: 'FinLevel7',
                title: '当前数量',
                align: 'center',
                width: '10%'
            },
        ]],
        toolbar: [{
            id: 'upperlevel_btn',
            text: '上级',
            iconCls: 'icon-back',
            handler: function () {
                ShowEditDialog();//实现修改记录的方法
            }
            
        }, '-', {
            id: 'nextlevel_btn',
            text: '下级',
            iconCls: 'icon-next',
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
})