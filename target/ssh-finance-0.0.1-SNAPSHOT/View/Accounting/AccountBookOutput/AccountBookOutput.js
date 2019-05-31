$(function () {

    $('#left_panel').panel({
        height: 400,
        padding: 10,
        margin: 10,
    })

    $('#right_panel').panel({
        height: 400,
        padding: 10,
        margin: 10,
    })

    $('#left_table').datagrid({
        width:'100%',
        columns: [[
			{
			    field: 'account_book_number',
			    title: '账册编号',
			    width: '33%',
			    align: 'center'
			},
			{
			    field: 'account_book_name',
			    title: '账册名称',
			    width: '33%',
			    align: 'center'
			},
			{
			    field: 'account_book_type',
			    title: '账册类型',
			    width: '33%',
			    align: 'center'
			}
        ]],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });

    $('#right_table').datagrid({
        width: '100%',
        columns: [[
			{
			    field: 'account_number',
			    title: '科目编号',
			    width: '25%',
			    align: 'center'
			},
			{
			    field: 'page_level',
			    title: '账页级数',
			    width: '25%',
			    align: 'center'
			},
			{
			    field: 'is_print',
			    title: '无余额发生额是否打印',
			    width: '25%',
			    align: 'center'
			},
            {
                field: 'is_merge',
                title: '合并同类分类',
                width: '25%',
                align: 'center'
            }
        ]],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });

    $('#show_button').linkbutton({
        width: 120,
        height: 30,
        iconCls: 'icon-wage',
    });
    $('#add_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-add',
    });
    $('#delete_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-remove',
    });
    $('#save_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-save',
    });

})