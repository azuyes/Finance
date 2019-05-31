$(function (){
    $('#main_table').datagrid({
        width: '100%',
        columns: [[
			{
			    field: 'account_book_number',
			    title: '账册编号',
			    width: '10%',
			    align: 'center'
			},
			{
			    field: 'account_book_name',
			    title: '账册名称',
			    width: '10%',
			    align: 'center'
			},
			{
			    field: 'account_book_type',
			    title: '账册类型',
			    width: '10%',
			    align: 'center'
			},
            {
                field: 'account_form',
                title: '账式',
                width: '10%',
                align: 'center'
            },
            {
                field: 'row_page',
                title: '每页行数',
                width: '10%',
                align: 'center'
            },
            {
                field: 'is_fill',
                title: '补空行否',
                width: '10%',
                align: 'center'
            },
            {
                field: 'abstract_width',
                title: '摘要栏宽度（字符）',
                width: '10%',
                align: 'center'
            },
            {
                field: 'is_printing',
                title: '是否套打',
                width: '10%',
                align: 'center'
            },
            {
                field: 'is_skip',
                title: '科目间是否换页',
                width: '10%',
                align: 'center'
            },
            {
                field: 'print_format',
                title: '打印格式',
                width: '10%',
                align: 'center'
            },
        ]],
		toolbar:'#button_group'
    });
})