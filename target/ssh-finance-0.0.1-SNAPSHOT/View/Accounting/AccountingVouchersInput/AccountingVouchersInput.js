$(function () {
    $(document).ready(function () {
        $('#main_window').css("display", "none");
        $('#voucher_choose_box').window('close');

        //初始化时间
        var mydate = new Date();

        $('#year_input').attr("max", mydate.getFullYear());//设置最大年份为当前年份
        $('#year_input').val(mydate.getFullYear());
        $('#month_input').val(mydate.getMonth() + 1);

        //选择时间
        $('#button_select_time').click(function () {
            $('#dlg_date').dialog('open');
        })

        $('#button_time_sure').click(function () {
            $('#dlg_date').dialog('close');
            $('#Hint').css("display", "none");
            var year_month = $('#year_input').val + "年" + $('#month_input').val + "月";
            $('#year&month').innerHTML = year_month;
            $('#date_input').val(year_month);
        })

    });

    $('#yes_button').click(function () {
        $('#dlg_date').window('close');
        $('#main_window').css("display", "block");
    });
    $('#main_table').datagrid({
        width: '100%',
        columns: [[
			{
			    field: 'level_1',
			    title: '总账科目',
			    width: '16%',
			    rowspan: 2,
			    align: 'center'
			},
			{
			    field: 'level_2',
			    title: '二级科目',
			    width: '16%',
			    rowspan: 2,
			    align: 'center'
			},
			{
			    field: 'level_3',
			    title: '三级科目',
			    width: '16%',
			    rowspan: 2,
			    align: 'center'
			},
            {
                field: 'detail_level',
                title: '明细科目',
                width: '16%',
                rowspan: 2,
                align: 'center'
            },
            {
                field: 'money',
                title: '金额',
                width: '32%',
                colspan: 2,
                align: 'center'
            }],[
            {
                field: 'debit_money',
                title: '借方金额',
                width: '16%',
                align: 'center'
            },
            {
                field: 'credit_money',
                title: '贷方金额',
                width: '16%',
                align: 'center'
            }
        ]],
        toolbar: '#button_group'
    });

})