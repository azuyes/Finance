$(function (){

    $('#edit_button').click(function () {
        $('#edit_account_box').window('open');
        $('#edit_account_box').style.display = 'block';
    })

    $('#edit_account_box').window({
        width: 300,
        height: 350,
        padding: 10,
        title: '定义特殊科目',
        iconCls: 'icon-edit_add',
        minimizable: false,
        maximizable: false,
        resizable: false,
        collapsible: false,
    })

    $('#special_accounting_1_select').panel({
        width: 125,
        height: 150,
        padding: 10,
    })

    $('#special_accounting_2_select').panel({
        width: 125,
        height: 150,
        padding: 10,
    })

    $('#main_table').datagrid({
        width: '100%',
        columns: [[
			{
			    field: 'account_number',
			    title: '科目编号',
			    width: '11%',
			    align: 'center'
			},
			{
			    field: 'account_name',
			    title: '科目名称',
			    width: '11%',
			    align: 'center'
			},
			{
			    field: 'is_detail',
			    title: '明细',
			    width: '11%',
			    align: 'center'
			},
            {
                field: 'is_daily_book',
                title: '日记账',
                width: '11%',
                align: 'center'
            },
            {
                field: 'is_balance',
                title: '对账',
                width: '11%',
                align: 'center'
            },
            {
                field: 'dealing',
                title: '往来',
                width: '11%',
                align: 'center'
            },
            {
                field: 'account_category_1',
                title: '核算类别1',
                width: '11%',
                align: 'center'
            },
            {
                field: 'account_category_2',
                title: '核算类别2',
                width: '11%',
                align: 'center'
            },
            {
                field: 'accounting_interval',
                title: '会计区间',
                width: '11%',
                align: 'center'
            }
        ]],
        toolbar: "#button_group"
//      toolbar: [{
//          text:'定义',
//          iconCls: 'icon-edit',
//      },'-',{
//          text:'上级',
//          iconCls: 'icon-back',
//      },'-',{
//          text:'下级',
//          iconCls: 'icon-next',
//      },'-',{
//          text:'前页',
//          iconCls: 'icon-undo',
//      },'-',{
//          text:'后页',
//          iconCls: 'icon-redo',
//      },'-',{
//          text:'确定',
//          iconCls: 'icon-ok',
//      }]
    });

    $('#special1_table').datagrid({
        height:100,
        width:100,
        columns: [[
			{
			    field: 'account_number',
			    title: '科目编号',
			    width: 100
			}],[
			{
			    field: 'account_name',
			    title: '科目名称',
			    width: 100
			}],
        ],
        //pagination: false,
        //pageSize: 5,
        //pageList: [5, 10, 15],
        //pageNumber: 1,
        //pagePosition: 'bottom',
    });

    // $('#edit_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-edit',
    // });
    // $('#previous_level_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-back',
    // });
    // $('#next_level_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-next',
    // });
    // $('#previous_page_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-undo',
    // });
    // $('#next_page_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-redo',
    // });
    // $('#yes_button').linkbutton({
    //     width: 100,
    //     height: 30,
    //     iconCls: 'icon-ok',
    // });
    $('#box_yes_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-ok',
    });
})