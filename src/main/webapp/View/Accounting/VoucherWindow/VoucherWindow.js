/**
 * Created by ChenZH on 2018/9/11.
 */

obj = {
    voucherSearch: function () {
        if($('#main_table').datagrid('getSelected') != null){
            var row = $('#main_table').datagrid('getSelected');
            var voucher_no = row.lspzk1.voucherNo;
            var input_date = row.lspzk1.inputDate;
            showVoucherTable(voucher_no, input_date);
            $('#voucher_window').window('open');
        }
    }

};

//显示当前凭证的分录
function showVoucherTable(voucher_no, input_date){
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountingVouchers/queryVouchersForDatagrid/' + voucher_no + '/' + input_date,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzk1QueryVoList;
                $('#voucher_table').datagrid('loadData', data);
                $('#atta').val($('#voucher_table').datagrid('getRows').length);
                if(data != null){
                    $('#year_month').val(parseInt(data[0]['inputDate'].substr(0,4)) + '年' + parseInt(data[0]['inputDate'].substr(4,6)) + '月');
                    $('#day').val(parseInt(data[0]['inputDate'].substr(6,2)));
                    $('#voucher_no').val(data[0]['voucherNo']);
                    $('#summary').val(data[0]['summary']);
                    $('#auditor').val(data[0]['auditor']);
                    $('#preact_doc').val(data[0]['preActDoc']);
                }
                $('#voucher_table').datagrid('sort', 'entryNo');
                //reloadFooterTotal();
            }
        }
    });
}