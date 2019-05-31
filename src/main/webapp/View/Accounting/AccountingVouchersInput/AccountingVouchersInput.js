var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = [];//存储科目结构
var current_date_ = "";
var unit_name_ = "";
var cashier_ = "";
var operator_ = "";
var level_names_stack_ = [];//存储各级科目名称
var atta_ = 0;//附件数
var entry_no_ = 0;
var user_name_ = "";
var user_no_ = "";
var first_no_ = "";
var voucher_no_ = "0000";
var lattest_voucher_no_ = "0000";
var add_voucher_ = false;
var unsaved_status_ = false;
var data_;

var sp_vouchers = [];

$(function () {
    $(document).ready(function () {
        $('#main_window').css("display", "none");
        $('#voucher_choose_box').window('close');

        //初始化时间
        var mydate = new Date();

        $('#year_input').attr("max", mydate.getFullYear());//设置最大年份为当前年份
        $('#year_input').val(mydate.getFullYear());
        $('#month_input').val(mydate.getMonth() + 1);
        $('#day').val(mydate.getDay());

        //选择时间
        $('#button_select_time').click(function () {
            $('#dlg_date').dialog('open');
        });

        $('#yes_button').click(function () {
            $('#dlg_date').dialog('close');
            $('#main_window').css("display", "block");
            $('#Hint').css("display", "none");
            var year_month = $('#year_input').val() + "年" + $('#month_input').val() + "月";
            $('#year_month').val(year_month);
            getLattestVoucherNo(add_voucher_);
            //showMainTable();
        });
    });

    $('#level').val(level_);
    $('#superior_account').val(upper_No_);
    $('#atta').val(atta_);

    dealConfigs();//保存科目级数
    showCaption(upper_No_,level_.toString());

    // //获取cookie字符串
    // var strCookie=document.cookie;
    // console.log(strCookie);
    // //将多cookie切割为多个名/值对
    // var arrCookie=strCookie.split(";");
    // //遍历cookie数组，处理每个cookie对，找出status对应的值
    // for(var i = 0; i < arrCookie.length; i++) {
    //     var arr = arrCookie[i].split('=');
    //     console.log(arr[0]);
    //     console.log(arr[1]);
    //     if(arr[0] === ' userName') {
    //         user_name_ = arr[1];
    //     }
    //     if(arr[0] === ' userNo') {
    //         user_no_ = arr[1];
    //     }
    // }

    user_name_ = window.localStorage.getItem('userName');
    user_no_ = window.localStorage.getItem('userNo');

    //只读模式，不可修改任何东西
    //初始默认进入只读
    var readonly_mode = true;
    $('#edit_mode').hide();
    $('#day').attr('readonly', true);
    $('#summary').textbox('readonly',true);

    var editRow = undefined;//定义全局变量，暂存当前编辑行
    var editMoneyRow = undefined;//非核算原始凭证编辑行
    var editAccRow = undefined;//核算凭证编辑行
    var edit_mode = false;//编辑模式否则为添加状态，是则为修改状态
    var edit_money_mode = false;
    var edit_acc_mode = false;
    var is_debit = true;//录入借方金额
    var sp_acc = [];//存储页面上每一条的特殊科目，普通为0，日记账为1，专项核算为2

    var is_review = true;//复核状态

    var ori_vouchers = [];

    var entries = [];

    obj = {
        switchMode: function(is_readonly){
            if(is_readonly){
                if(unsaved_status_){
                    $.messager.confirm('确认对话框', '您有未保存更新内容，是否保存？', function(r){
                        if (r){
                            obj.save();
                        }
                        else{
                            readonly_mode = true;
                            $('#readonly_mode').show();
                            $('#edit_mode').hide();
                            $('#day').attr('readonly', true);
                            $('#summary').textbox('readonly',true);
                            showMainTable();
                        }
                    });
                }
                else{
                    readonly_mode = true;
                    add_voucher_ = false;
                    $('#readonly_mode').show();
                    $('#edit_mode').hide();
                    $('#day').attr('readonly', true);
                    $('#summary').textbox('readonly',true);
                }
            }
            else{
                readonly_mode = false;
                $('#readonly_mode').hide();
                $('#edit_mode').show();
                $('#day').attr('readonly', false);
                $('#summary').textbox('readonly',false);
            }
        },

        //增加凭证
        addVoucher: function () {
            if(editRow != undefined){
                $.messager.alert('提示', '请先确认或删除上一条更新', 'info');
                return;
            }
            this.switchMode(false);
            add_voucher_ = true;
            atta_ = 0;
            getLattestVoucherNo(add_voucher_);
            $('#preact_doc').val(user_name_);
            $('#auditor').val(null);
            $('#summary').textbox('clear');
            $('#atta').val(atta_);
            // $('#day').attr('min', parseInt(current_date_.substr(6, 2)));
            // $('#day').attr('max', 31);
            // $('#day').val(parseInt(current_date_.substr(6, 2)));
            $('#main_table').datagrid('reloadFooter',
                [{"level_1":"合计","debitMoney":0,"creditMoney":0}]
            );
            dealConfigs();
        },

        //增加分录
        add: function () {
            var table = $('#main_table');
            //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
            if (editRow == undefined) {
                unsaved_status_ = true;//开启未保存状态
                edit_mode = false;
                table.datagrid('appendRow', {});
                var rows = table.datagrid('getRows').length;
                //将新插入的那一行开启编辑状态
                table.datagrid('beginEdit', rows - 1);
                //给当前编辑的行赋值
                editRow = rows - 1;
                //附件数增加
                atta_++;
                $('#atta').val(atta_);
                //设置该行分录编号
                entry_no_++;
                var item_no = $('#main_table').datagrid('getEditor', {index:editRow,field:'entryNo'});
                $(item_no.target).textbox('setValue', getFullNo(entry_no_, 4));
            }
            else {
                $.messager.alert('提示', '请先确认或删除上一条更新', 'info');
            }
        },

        next: function () {
            if(parseInt(voucher_no_) + 1 > parseInt(lattest_voucher_no_)){
                $.messager.alert('提示', '已经是最后一张凭证', 'info');
                return;
            }
            voucher_no_ = getFullNo(parseInt(voucher_no_) + 1, 4);
            $('#voucher_no').val(first_no_ + voucher_no_);
            showMainTable();
        },

        back: function () {
            if(parseInt(voucher_no_) - 1 < 1 ){
                $.messager.alert('提示', '已经是第一张凭证', 'info');
                return;
            }
            voucher_no_ = getFullNo(parseInt(voucher_no_) - 1, 4);
            $('#voucher_no').val(first_no_ + voucher_no_);
            showMainTable();
        },

        quit: function () {
            if(editRow != undefined){
                $.messager.alert('提示', '请先确认或删除上一条更新', 'info');
                return;
            }
            obj.switchMode(true);
        },

        print: function () {
            var bdhtml = window.document.body.innerHTML;
            var sprnstr = "<!--startprint-->";
            var eprnstr = "<!--endprint-->";
            var prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
            prnhtml = prnhtml.substring(0,prnhtml.indexOf(eprnstr));
            window.document.body.innerHTML = prnhtml;
            window.print();
        },

        //删除凭证
        removeVoucher: function () {
            if(add_voucher_){
                add_voucher_ = false;
                voucher_no_ = lattest_voucher_no_;
                $('#voucher_no').val(first_no_ + voucher_no_);
                showMainTable();
                $.messager.show({
                    title: '提示',
                    msg: "删除成功"
                });
                return;
            }
            $.ajax({
                type: 'POST',
                url: getRealPath() + '/AccountingVouchers/delVoucher/' + first_no_ + voucher_no_ + '/' + getInputDate(),
                contentType: 'application/json',
                success: function (result) {
                    if (result.code == 100) {
                        getLattestVoucherNo(false);
                        //showMainTable();
                        $.messager.show({
                            title: '提示',
                            msg: "删除成功"
                        });
                    } else {
                        var data = result.extend.errorInfo;
                        $.messager.alert('提示', data, 'info');
                    }
                }
            });
        },

        //删除分录
        remove: function () {
            var table = $('#main_table');
            var select = table.datagrid('getSelected');
            if(editRow != undefined) {
                table.datagrid('deleteRow', editRow);
                editRow = undefined;
                ori_vouchers.splice(0, ori_vouchers.length);//清空当前分录的原始凭证
            }
            else if(select){
                var row = table.datagrid('getSelected');
                var entry_no = row.entryNo;
                var data_index = getIndexOfData(data_, 'entryNo', entry_no);
                console.log('在data里的索引',data_index);
                var row_index = table.datagrid('getRowIndex', row);
                table.datagrid('deleteRow', row_index);
                //delete data_[data_index];
                //data_.splice(data_index, 1);
                console.log(data_);
                reloadFooterTotal();
                    // var entry_no = table.datagrid('getSelected').entryNo;
                    // var item_no = table.datagrid('getSelected').itemNo;
                    // $.ajax({
                    //     type: 'POST',
                    //     url: getRealPath() + '/AccountingVouchers/delEntry/'+getInputDate()+'/'+ first_no_ + voucher_no_+'/'+entry_no+'/'+item_no,
                    //     contentType: 'application/json',
                    //     success: function (result) {
                    //         if (result.code == 100) {
                    //             var index = table.datagrid('getRowIndex',select);
                    //             table.datagrid('deleteRow',index);
                    //             entries.splice(index, 1);//清除已保存在数组中的分录
                    //             reloadFooterTotal();
                    //             //showMainTable();
                    //             $.messager.show({
                    //                 title: '提示',
                    //                 msg: "删除成功"
                    //             });
                    //         } else {
                    //             var data = result.extend.errorInfo;
                    //             $.messager.alert('提示', data, 'info');
                    //         }
                    //     }
                    // });
            }
            else{
                $.messager.alert('提示', '请选择一行数据', 'info');
            }
        },

        save: function () {
            if(reloadFooterTotal()[0] != reloadFooterTotal()[1]){
                $.messager.alert('提示', '借方金额 ≠ 贷方金额', 'info');
                return;
            }
            if($('#day').val() == '' || $('#day').val() == null){
                $.messager.alert('提示', '日期不能为空', 'info');
                return;
            }
            if($('#atta').val() == '' || $('#atta').val() == null){
                $.messager.alert('提示', '附件不能为空', 'info');
                return;
            }
            if(!$('#summary').textbox('isValid')){
                $.messager.alert('提示', '摘要不能为空', 'info');
                return;
            }

                if(add_voucher_) {
                    updateLattestVoucherNo(true);
                }
                var data_str = JSON.stringify(data_);
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/AccountingVouchers/saveVoucher',
                    contentType: 'application/json',
                    data: data_str,
                    success: function (result) {
                        if (result.code == 100) {
                            unsaved_status_ = false;
                            showMainTable();
                            $.messager.show({
                                title: '提示',
                                msg: '保存成功'
                            });
                        } else {
                            var data = result.extend.errorInfo;
                            $.messager.alert('提示', data, 'info');
                        }
                    }
                });
        },

        saveEntry: function (row, is_debit) {
            var ori_vouchers_json = JSON.parse(JSON.stringify(ori_vouchers));
            var sp_vouchers_json = JSON.parse(JSON.stringify(sp_vouchers));
            if(row >= data_.length){
                var entry = getEntryData(row, is_debit);
                entry.lsyspzList = ori_vouchers_json;
                entry.lshspzQueryVoList = sp_vouchers_json;
                data_.push(entry);
            }
            else{
                data_[row] = getEntryData(row, is_debit);
                data_[row].lsyspzList = ori_vouchers_json;
                data_[row].lshspzQueryVoList = sp_vouchers_json;
            }
            $('#main_table').datagrid('endEdit',editRow);
            editRow = undefined;

            ori_vouchers.splice(0, ori_vouchers.length);
            sp_vouchers.splice(0, sp_vouchers.length);
            reloadFooterTotal();
            // var request = '';
            // if(edit_mode){
            //     request = '/AccountingVouchers/updateEntry';
            // }
            // else{
            //     request = '/AccountingVouchers/addEntry';
            // }
            // $.ajax({
            //     type: 'POST',
            //     url: getRealPath() + request,
            //     contentType: 'application/json',
            //     data: getEntryData(row, is_debit),
            //     success: function (result) {
            //         if (result.code == 100) {
            //             showMainTable();
            //             $.messager.show({
            //                 title: '提示',
            //                 msg: "添加成功"
            //             });
            //         } else {
            //             var data = result.extend.errorInfo;
            //             $.messager.alert('提示', data, 'info');
            //         }
            //     }
            // });
        },

        saveMoney: function (row, is_debit) {
            ori_vouchers.push(getMoneyData(row, editRow, is_debit));

            // var request = '';
            // if(edit_money_mode){
            //     request = '/AccountingVouchers/updateOriginalVoucher';
            // }
            // else{
            //     request = '/AccountingVouchers/addOriginalVoucher';
            // }
            // $.ajax({
            //     type: 'POST',
            //     url: getRealPath() + request,
            //     contentType: 'application/json',
            //     data: getMoneyData(row, editRow, is_debit),
            //     success: function (result) {
            //         if (result.code == 100) {
            //             showMoneyTable();
            //             $.messager.show({
            //                 title: '提示',
            //                 msg: "添加成功"
            //             });
            //         } else {
            //             $('#money_table').datagrid('deleteRow',row);
            //             editMoneyRow = undefined;
            //             var data = result.extend.errorInfo;
            //             $.messager.alert('提示', data, 'info');
            //         }
            //     }
            // });
        },

        saveAcc: function (row, is_debit, is_qty) {
            var acc_row = $('#acc_table').datagrid('getEditors',row);
            var money = $(acc_row[0].target).numberbox('getValue');
            var qty = $(acc_row[1].target).numberbox('getValue');
            if(money != null){
                if((is_qty && qty != null) || !is_qty){
                    var data = getAccData(row, editRow, is_debit);
                    for(var i = 0; i < sp_vouchers.length; i++){
                        if(data.spNo1 == sp_vouchers[i].spNo1 && data.spNo2 == sp_vouchers[i].spNo2) break;
                    }
                    if(sp_vouchers[i]){
                        var temp_name1 = sp_vouchers[i].upperName1;
                        var temp_name2 = sp_vouchers[i].upperName2;
                        sp_vouchers[i] = data;
                        sp_vouchers[i].upperName1 = temp_name1;
                        sp_vouchers[i].upperName2 = temp_name2;
                    }
                    else{
                        sp_vouchers[i] = data;
                    }
                    //sp_vouchers.push(getAccData(row, editRow, is_debit));
                }
            }

            // $.ajax({
            //     type: 'POST',
            //     url: getRealPath() + '/AccountingVouchers/saveSpVoucher',
            //     contentType: 'application/json',
            //     data: getAccData(row, editRow, is_debit),
            //     success: function (result) {
            //         if (result.code == 100) {
            //             showAccTable();
            //             $.messager.show({
            //                 title: '提示',
            //                 msg: "添加成功"
            //             });
            //         } else {
            //             $('#acc_table').datagrid('deleteRow',row);
            //             editAccRow = undefined;
            //             var data = result.extend.errorInfo;
            //             $.messager.alert('提示', data, 'info');
            //         }
            //     }
            // });
        },

        //复核
        review: function () {
            var year = parseInt(current_date_.substr(0, 4));
            var month = parseInt(current_date_.substr(4, 2));
            var days = getMonthDays(year,month);
            
            $('#voucher_choose_box').window('open');
            $('#from_number').textbox('setValue', first_no_ + voucher_no_);
            $('#to_number').textbox('setValue', first_no_ + voucher_no_);
            $('#from_date').numberbox('setValue', 1);
            $('#to_date').numberbox('setValue', days);
            is_review = true;
        },

        //取消复核
        cancelReview: function () {
            var year = parseInt(current_date_.substr(0, 4));
            var month = parseInt(current_date_.substr(4, 2));
            var days = getMonthDays(year,month);

            $('#voucher_choose_box').window('open');
            $('#from_number').textbox('setValue', first_no_ + voucher_no_);
            $('#to_number').textbox('setValue', first_no_ + voucher_no_);
            $('#from_date').numberbox('setValue', 1);
            $('#to_date').numberbox('setValue', days);
            is_review = false;
        },

        //复核操作
        operateReview: function () {
            var request = '';
            var start = '';
            var end = '';
            var operation = is_review ? 1 : 0;
            var msg = is_review ? '复核' : '取消复核';
            //TODO
            // user_name_ = '0';
            // user_no_ = '0';
            if($('input:radio[name="choose"]:checked').val() == "number"){
                start = $('#from_number').textbox('getValue');
                end = $('#to_number').textbox('getValue');
                request = '/AccountingVouchers/operateReviewByNum/';
            }
            else{
                start = $('#from_date').numberbox('getValue');
                end = $('#to_date').numberbox('getValue');
                request = '/AccountingVouchers/operateReviewByDate/';
            }
            $.ajax({
                type: 'POST',
                url: getRealPath() + request + operation + '/' + getInputDate() + '/' + start + '/' + end + '/' + user_name_ + '/' + user_no_,
                contentType: 'application/json',
                success: function (result) {
                    if (result.code == 100) {
                        $.messager.show({
                            title: '提示',
                            msg: msg + '成功'
                        });
                        $('#voucher_choose_box').window('close');
                        showMainTable();
                    } else {
                        var data = result.extend.errorInfo;
                        $.messager.alert('提示', data, 'info');
                    }
                }
            });
        },


        //上级点击事件
        uperLevel_click : function () {
            if(level_==1){return;}
            level_--;
            level_names_stack_.pop();
            $('#level').val(level_);
            upper_No_=getCatNo(upper_No_,level_-1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },
        //下级点击事件
        nextLevel_click : function () {
            var row = $('#itemTable').datagrid('getSelected');
            if(row==null){return;}//判断是否选中行
            level_++;
            level_names_stack_.push(row.itemName);
            $('#level').val(level_);
            upper_No_ = getCatNo(row.itemNo,level_-1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_,level_);
        },
        //选定科目字典
        selectClick : function () {
            var row = $('#itemTable').datagrid("getSelected");
            if(row.finLevel == 0){
                $.messager.alert('警告操作！', '科目非明细！', 'warning');
            }
            else{
                level_names_stack_.push(row.itemName);
                $('#helpWin').window('close');

                var item_no = $('#main_table').datagrid('getEditor', {index:editRow,field:'itemNo'});
                $(item_no.target).textbox('setValue', row.itemNo);
                var journal_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'journal'});
                $(journal_editor.target).textbox('setValue', row.journal);
                var accType_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'accType'});
                $(accType_editor.target).textbox('setValue', row.accType);
                var supAcc1_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'supAcc1'});
                $(supAcc1_editor.target).textbox('setValue', row.supAcc1);
                var supAcc2_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'supAcc2'});
                $(supAcc2_editor.target).textbox('setValue', row.supAcc2);

                var level_1 = $('#main_table').datagrid('getEditor', {index:editRow,field:'level_1'});
                var level_2 = $('#main_table').datagrid('getEditor', {index:editRow,field:'level_2'});
                var level_3 = $('#main_table').datagrid('getEditor', {index:editRow,field:'level_3'});
                var level_detail = $('#main_table').datagrid('getEditor', {index:editRow,field:'level_detail'});
                $(level_1.target).textbox('setValue', level_names_stack_.shift());
                if(level_names_stack_[0] != null){
                    $(level_2.target).textbox('setValue', level_names_stack_.shift());
                }
                else{
                    $(level_2.target).textbox('clear');
                }
                if(level_names_stack_[0] != null){
                    $(level_3.target).textbox('setValue', level_names_stack_.shift());
                }
                else{
                    $(level_3.target).textbox('clear');
                }
                var level_detail_name = '';
                while(level_names_stack_[0] != null){
                    level_detail_name = level_detail_name + '/' + level_names_stack_.shift();
                }

                $(level_detail.target).textbox('setValue', level_detail_name);

                level_ = 1;
                $('#level').val(level_);
                showCaption(null, level_);
            }
        },
        
        confirmAccTable: function () {
            var acc_table = $('#acc_table');
            var main_table = $('#main_table');
            var accType = main_table.datagrid('getEditor', {index: editRow, field: 'accType'});
            var accType_value = $(accType.target).textbox('getValue');
            if (editAccRow != undefined) {
                obj.saveAcc(editAccRow, is_debit, accType_value == 'Y');
                acc_table.datagrid('endEdit', editAccRow);
                editAccRow = undefined;
            }
            reloadFooterAcc();
        },

        confirmMoneyTable: function() {
            var money_table = $('#money_table');
            var main_table = $('#main_table');

            var originalNo = money_table.datagrid('getEditor', {index:editMoneyRow,field:'originalNo'});
            var originalNo_valid = $(originalNo.target).textbox('isValid');
            var originalNo_value = $(originalNo.target).textbox('getValue');
            var money = money_table.datagrid('getEditor', {index:editMoneyRow,field:'money'});
            var money_valid = $(money.target).textbox('isValid');

            if(!originalNo_valid || !money_valid){
                $.messager.alert('提示', '请先填写数据或删除该条目', 'info');
                return false;
            }

            var rows = money_table.datagrid('getRows');
            for(var index in rows){
                if(rows[index].originalNo == originalNo_value && index != editMoneyRow){
                    $.messager.alert('提示', '编号重复，请修改编号', 'info');
                    return false;
                }
            }

            if(editMoneyRow != undefined){
                if(index == editMoneyRow){
                    ori_vouchers[index] = getMoneyData(editMoneyRow, editRow, is_debit);
                }
                else {
                    ori_vouchers.push(getMoneyData(editMoneyRow, editRow, is_debit));
                }
                //obj.saveMoney(editMoneyRow, is_debit);
                money_table.datagrid('endEdit',editMoneyRow);
                editMoneyRow = undefined;
            }
            reloadFooterMoney();
            return true;
        }
    };

    // $('#yes_button').click(function () {
    //     $('#dlg_date').window('close');
    //     $('#main_window').css("display", "block");
    // });

    $('#main_table').datagrid({
        width: '100%',
        toolbar: '#button_group',
        showFooter: true,
        autoSave: true,
        singleSelect : true,
        loadMsg: '正在加载数据...',
        onDblClickRow: function (index, row) {
            if(readonly_mode){
                return;
            }
            var table = $('#main_table');
            //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
            if (editRow == undefined) {
                unsaved_status_ = true;
                edit_mode = true;
                //开启编辑状态
                table.datagrid('beginEdit', index);
                //给当前编辑的行赋值
                editRow = index;

                //获取当前行的原始凭证数据
                if(data_[index].lsyspzList){
                    for(var i = 0; i < data_[index].lsyspzList.length; i++){
                        ori_vouchers.push(data_[index].lsyspzList[i]);
                    }
                }
                if(data_[index].lshspzQueryVoList){
                    for(var i = 0; i < data_[index].lshspzQueryVoList.length; i++){
                        sp_vouchers.push(data_[index].lshspzQueryVoList[i]);
                    }
                }
            }
            else {
                $.messager.alert('提示', '请先保存上一条更新', 'info');
            }
        },
        onAfterEdit: function (index, row, changes) {
            reloadFooterTotal();
        },
        // onBeforeSave: function (index) {
        //     //保存当前分录
        //     obj.saveEntry(editRow, is_debit);
        //     $('#main_table').datagrid('endEdit',index);
        //     editRow = undefined;
        // },
        columns: [[
            {
                field: 'entryNo',
                title: '分录编号',
                rowspan: 2,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'textbox'
                }
            },
            {
                field: 'itemNo',
                title: '科目编号',
                rowspan: 2,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'textbox'
                }
            },
            {
                field: 'accType',
                title: '账式',
                rowspan: 2,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'textbox'
                }
            },
            {
                field: 'journal',
                title: '日记账',
                rowspan: 2,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'textbox'
                }
            },
            {
                field: 'supAcc1',
                title: '专项核算1',
                rowspan: 2,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'textbox'
                }
            },
            {
                field: 'supAcc2',
                title: '专项核算2',
                rowspan: 2,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'textbox'
                }
            },
            {
                field: 'level_1',
                title: '总账科目',
                width: '16%',
                rowspan: 2,
                align: 'center',
                editor: {
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: '必填',
                        editable: false,
                        buttonText: '选择',
                        buttonAlign: 'left',
                        onClickButton: function(index,field,value){
                            $('#helpWin').window('open');
                        }
                    }
                }
            },
            {
                field: 'level_2',
                title: '二级科目',
                width: '16%',
                rowspan: 2,
                align: 'center',
                editor: {
                    type: 'textbox',
                    options: {
                        readonly: true
                    }
                }
			},
			{
			    field: 'level_3',
			    title: '三级科目',
			    width: '16%',
			    rowspan: 2,
			    align: 'center',
                editor: {
                    type: 'textbox',
                    options: {
                        readonly: true
                    }
                }
			},
            {
                field: 'level_detail',
                title: '明细科目',
                width: '16%',
                rowspan: 2,
                align: 'center',
                editor: {
                    type: 'textbox',
                    options: {
                        readonly: true
                    }
                }
            },
            {
                field: 'money',
                title: '金额',
                width: '32%',
                colspan: 4,
                align: 'center'
            }],[
            {
                field: 'debitMoney',
                title: '借方金额',
                width: '16%',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        editable: false,
                        buttonText: '明细',
                        buttonAlign: 'left',
                        onClickButton: function(index,field,value){
                            var table = $('#main_table');
                            var level_1 = table.datagrid('getEditor', {index:editRow,field:'level_1'});
                            var level_1_value = $(level_1.target).textbox('getValue');

                            var credit = table.datagrid('getEditor', {index:editRow,field:'creditMoney'});
                            var credit_value = $(credit.target).numberbox('getValue');
                            if(level_1_value != null && level_1_value != ''){
                                if(credit_value != null && credit_value != ''){
                                    $.messager.confirm('确认对话框', '输入借方金额将会更改借贷方向为借方，是否继续？', function(r){
                                        if (r){
                                            //更换借贷方向,更换成功则返回true
                                            is_debit = true;
                                            changeBkpDirection(editRow, true);
                                        }
                                        else{
                                            return;
                                        }
                                    });
                                }
                                else{
                                    dealOriginalVoucherChoose(editRow);
                                    is_debit = true;
                                }
                            }
                            else{
                                $.messager.alert('提示', '请先设置科目', 'info');
                            }
                        }
                    }
                }
            },
            {
                field: 'debitQty',
                title: '借方数量',
                align: 'center',
                hidden: true,
                editor: {
                    type: 'numberbox',
                    options: {

                    }
                }
            },
            {
                field: 'creditMoney',
                title: '贷方金额',
                width: '16%',
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                            editable: false,
                            buttonText: '明细',
                            buttonAlign: 'left',
                            onClickButton: function(index,field,value){
                                var table = $('#main_table');
                                var level_1 = table.datagrid('getEditor', {index:editRow,field:'level_1'});
                                var level_1_value = $(level_1.target).textbox('getValue');

                                var debit = table.datagrid('getEditor', {index:editRow,field:'debitMoney'});
                                var debit_value = $(debit.target).numberbox('getValue');
                                if(level_1_value != null && level_1_value != ''){
                                    if(debit_value != null && debit_value != ''){
                                        $.messager.confirm('确认对话框', '输入借方金额将会更改借贷方向为贷方，是否继续？', function(r){
                                            if (r){
                                                //更换借贷方向,更换成功则返回true
                                                is_debit = false;
                                                changeBkpDirection(editRow, false);
                                            }
                                            else{
                                                return;
                                            }
                                        });
                                    }
                                    else{
                                        dealOriginalVoucherChoose(editRow);
                                        is_debit = false;
                                    }
                                }
                                else{
                                    $.messager.alert('提示', '请先设置科目', 'info');
                                }
                            }

                    }
                }
            },
            {
                field: 'creditQty',
                title: '贷方数量',
                align: 'center',
                hidden: true,
                editor: {
                    type: 'numberbox',
                    options: {

                    }
                }
            }
        ]]
    });

    //原始凭证表格
    $('#money_table').datagrid({
        width: '100%',
        showFooter: true,
        singleSelect : true,
        //url: getRealPath() + '/AccountingVouchers/queryOriginalVouchers/' + voucher_no_ + '/' + getInputDate() + '/' + getCurrentEntryNo(editRow),
        toolbar: [{
            iconCls: 'icon-add',
            text: '增加',
            handler: function(){
                var table = $('#money_table');
                if(editMoneyRow != undefined){
                    obj.confirmMoneyTable();
                    //$.messager.alert('提示', '请确认上一行操作', 'info');
                }
                else{
                    table.datagrid('appendRow', {});
                    var rows = table.datagrid('getRows').length;
                    //将新插入的那一行开启编辑状态
                    table.datagrid('beginEdit', rows - 1);
                    editMoneyRow = rows - 1;
                    edit_money_mode = false;
                }
            }
        },'-',{
            iconCls: 'icon-remove',
            text: '删除',
            handler: function(){
                var table = $('#money_table');

                if(editMoneyRow != undefined){
                    table.datagrid('deleteRow',editMoneyRow);
                    editMoneyRow = undefined;
                    ori_vouchers.splice(editMoneyRow, 1);
                    //showMainTable();
                }
                else{
                    var row = table.datagrid('getSelected');
                    var entry_no_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'entryNo'});
                    var entry_no = $(entry_no_editor.target).textbox('getValue');
                    var original_no = row.originalNo;
                    var data_index = getIndexOfData(data_, 'entryNo', entry_no);
                    var entry_index = data_[data_index] != null ? getIndexOfData(data_[data_index].lsyspzList, 'originalNo', original_no) : -1;
                    var row_index = table.datagrid('getRowIndex', row);
                    ori_vouchers.splice(row_index, 1);
                    table.datagrid('deleteRow', row_index);
                    // if(entry_index < 0){
                    //     delete data_[data_index][entry_index];
                    // }
                    reloadFooterMoney();
                    console.log(data_[data_index]);

                    // $.ajax({
                    //     type: 'POST',
                    //     url: getRealPath() + '/AccountingVouchers/delOriginalVoucher/'+getInputDate()+'/'+ first_no_ + voucher_no_+'/'+entry_no+'/'+original_no,
                    //     contentType: 'application/json',
                    //     success: function (result) {
                    //         if (result.code == 100) {
                    //             var select = table.datagrid('getSelected');
                    //             var index = table.datagrid('getRowIndex', select);
                    //             ori_vouchers.splice(index, 1);
                    //
                    //             showMoneyTable(editRow);
                    //             $.messager.show({
                    //                 title: '提示',
                    //                 msg: "删除成功"
                    //             });
                    //         } else {
                    //             var data = result.extend.errorInfo;
                    //             $.messager.alert('提示', data, 'info');
                    //         }
                    //     }
                    // });
                }
            }
        },'-',{
            iconCls: 'icon-ok',
            text: '确认',
            handler: function(){
            //     var money_table = $('#money_table');
            //     var main_table = $('#main_table');
            //     if(editMoneyRow != undefined){
            //         obj.saveMoney(editMoneyRow, is_debit);
            //         money_table.datagrid('endEdit',editMoneyRow);
            //         editMoneyRow = undefined;
            //     }
            //     reloadFooterMoney()
            //    obj.confirmMoneyTable();
                $('#win_money').window('close');
            }

        }],
        //双击开启编辑
        onDblClickRow : function (rowIndex, rowData) {
            if(readonly_mode){
                return;
            }
            var table = $('#money_table');
            if(editMoneyRow != undefined){
                //$.messager.alert('提示', '请确认上一行操作', 'info');
                var is_valid = obj.confirmMoneyTable();
                if(!is_valid) return;
            }

            table.datagrid('beginEdit', rowIndex);
            editMoneyRow = rowIndex;
            edit_money_mode = true;

        },
        onAfterEdit: function () {
            reloadFooterMoney();
        },
        // onBeforeSave: function (index) {
        //     obj.saveMoney(editMoneyRow, is_debit);
        //     $('#money_table').datagrid('endEdit',index);
        //     editMoneyRow = undefined;
        // },
        columns: [[
            {
                field: 'originalNo',
                title: '编号',
                width: '25%',
                align: 'center',
                editor:{
                    type: 'textbox',
                    options: {
                        required: true,
                        missingMessage: "不能为空"
                    }
                }
            },
            {
                field: 'summary',
                title: '用途',
                width: '25%',
                align: 'center',
                editor:{
                    type: 'textbox',
                    options: {}
                }
            },
            {
                field: 'money',
                title: '金额',
                width: '25%',
                align: 'center',
                editor:{
                    type: 'numberbox',
                    options:{
                        required: true,
                        missingMessage: "不能为空"
                    }
                }
            },
            {
                field: 'qty',
                title: '数量',
                width: '25%',
                align: 'center',
                editor:{
                    type: 'numberbox',
                    options:{}
                }
            }
        ]]
    });

    //核算凭证表格
    $('#acc_table').datagrid({
        width: '100%',
        showFooter: true,
        singleSelect : true,
        toolbar: '#tb_acc',
        //     [{
        //     iconCls: 'icon-ok',
        //     text: '确认',
        //     handler: function(){
        //         // var row = $('#itemTable').datagrid("getSelected");
        //         // $('#win_acc').window('close');
        //         // var ed = $('#acc_table').datagrid('getEditor', {index:editRow,field:'accBookName'});
        //         // $(ed.target).textbox('setValue', row.itemName);
        //         confirmAccTable();
        //     }
        // },{
        //     text: '核算对象1上级：<input type="text" style="width: 60px; border: 0" id="spNo1Upper" readonly/>核算对象2上级：<input type="text" style="width: 60px; border: 0" id="spNo2Upper" readonly/>'
        // }],
        //单击显示上级名称
        onClickRow: function (index, row) {
            $('#spNo1Upper').val(sp_vouchers[index].upperName1);
            $('#spNo2Upper').val(sp_vouchers[index].upperName2);
            console.log(sp_vouchers[index].upperName1, sp_vouchers[index].upperName2);
        },
        //双击开启编辑
        onDblClickRow : function (rowIndex, rowData) {
            if(readonly_mode){
                return;
            }
            var table = $('#acc_table');
            if(editAccRow != undefined){
                obj.confirmAccTable();
                //$.messager.alert('提示', '请确认上一行操作', 'info');
            }
            //else{
                table.datagrid('beginEdit', rowIndex);
                editAccRow = rowIndex;
                edit_acc_mode = true;
            //}
        },
        onAfterEdit: function (index, row, changes) {
            reloadFooterAcc();
        },
        columns: [[
            {
                field: 'spNo1',
                title: '核算编号1',
                width: '16%',
                align: 'center'
            },
            {
                field: 'spName1',
                title: '核算对象1',
                width: '16%',
                align: 'center'
            },
            {
                field: 'spNo2',
                title: '核算编号2',
                width: '16%',
                align: 'center'
            },
            {
                field: 'spName2',
                title: '核算对象2',
                width: '16%',
                align: 'center'
            },
            {
                field: 'money',
                title: '金额',
                width: '16%',
                align: 'center',
                editor:{
                    type: 'numberbox',
                    options:{}
                }
            },
            {
                field: 'qty',
                title: '数量',
                width: '16%',
                align: 'center',
                editor:{
                    type: 'numberbox',
                    options:{}
                }
            }
        ]]
    });


    //帮助窗口数据表格
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        //双击进入下一级事件
        onDblClickRow : function (rowIndex, rowData) {
            obj.nextLevel_click();
        },
        toolbar: '#tb_helpWin',
        columns: [[
            {
                field: 'itemNo',
                title: '编号',
                width: 120,
                formatter: function (value,row,index) {
                    var length = getNumLength(level_);
                    var no = value.substring(0,length);
                    return no;
                }
            },
            {
                field: 'itemName',
                title: '名称',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                width: 80,
                formatter: function (value,row,index) {
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'item',
                title: '级数',
                width: 80
            }
        ]]
    });

    //帮助窗口
    $('#helpWin').window({
        width: 480,
        height: 500,
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
        modal: true
    });

    //原始凭证窗口
    $('#win_money').window({
        width: 500,
        height: 300,
        title: '原始凭证',
        collapsible: false,
        minimizable: false,
        maximizable: false,
        closed: true,
        resizable: false,
        modal: true,
        onBeforeClose: function () {
            if(editMoneyRow != undefined){
                // $.messager.alert('提示', '请确认上一行操作', 'info');
                // return false;
                var is_valid = obj.confirmMoneyTable();
                if(!is_valid) return false;
            }
            var money_table = $('#money_table');
            var main_table = $('#main_table');
            var debitMoney = main_table.datagrid('getEditor', {index:editRow,field:'debitMoney'});
            var creditMoney = main_table.datagrid('getEditor', {index:editRow,field:'creditMoney'});
            var debitQty = main_table.datagrid('getEditor', {index:editRow,field:'debitQty'});
            var creditQty = main_table.datagrid('getEditor', {index:editRow,field:'creditQty'});
            var money = reloadFooterMoney()[0];
            var qty = reloadFooterMoney()[1];
            if(is_debit){
                $(debitMoney.target).textbox('setValue', money);
                $(debitQty.target).textbox('setValue', qty);
            }
            else{
                $(creditMoney.target).textbox('setValue', money);
                $(creditQty.target).textbox('setValue', qty);
            }

            //保存当前分录
            obj.saveEntry(editRow, is_debit);
        }
    });

    //核算凭证窗口
    $('#win_acc').window({
        width: 500,
        height: 300,
        title: '核算凭证',
        collapsible: false,
        minimizable: false,
        maximizable: false,
        closed: true,
        resizable: false,
        modal: true,
        onBeforeClose: function () {
            if(editAccRow != undefined){
                obj.confirmAccTable();
                //$.messager.alert('提示', '请确认上一行操作', 'info');
                //return false;
            }
            $('#spNo1Upper').val(null);
            $('#spNo2Upper').val(null);
            var acc_table = $('#acc_table');
            var main_table = $('#main_table');
            var debitMoney = main_table.datagrid('getEditor', {index:editRow,field:'debitMoney'});
            var creditMoney = main_table.datagrid('getEditor', {index:editRow,field:'creditMoney'});
            var debitQty = main_table.datagrid('getEditor', {index:editRow,field:'debitQty'});
            var creditQty = main_table.datagrid('getEditor', {index:editRow,field:'creditQty'});
            var money = reloadFooterAcc()[0];
            var qty = reloadFooterAcc()[1];
            if(is_debit){
                $(debitMoney.target).textbox('setValue', money);
                $(debitQty.target).textbox('setValue', qty);
            }
            else{
                $(creditMoney.target).textbox('setValue', money);
                $(creditQty.target).textbox('setValue', qty);
            }

            //保存当前分录
            obj.saveEntry(editRow, is_debit);
        }
    });

    $('#summary').textbox({
        onChange: function (newValue, oldValue) {
            for(var row = 0; row < data_.length; row++){
                data_[row].summary = newValue;
            }
        }
    });

    $('#day').change(function () {
        for(var row = 0; row < data_.length; row++){
            data_[row].inputDate = getInputDate();
            if(data_[row].lsyspzList != null){
                for(var i = 0; i < data_[row].lsyspzList.length; i++){
                    data_[row].lsyspzList[i].inputDate = getInputDate();
                }
            }
            if(data_[row].lshspzQueryVoList != null){
                for(var i = 0; i < data_[row].lshspzQueryVoList.length; i++){
                    data_[row].lshspzQueryVoList[i].inputDate = getInputDate();
                }
            }
        }
    });

    $('#by_date_select').on('click', function () {
        $('#by_date').css("display", "block");
        $('#by_number').css("display", "none");
    });

    $('#by_number_select').on('click', function () {
        $('#by_date').css("display", "none");
        $('#by_number').css("display", "block");
    });

    $('#voucher_choose_box').window({
        width: 260,
        height: 200,
        closed: true,
        resizable: false,
        minimizable: false,
        maximizable: false,
        collapsible: false
    });

    // function confirmAccTable() {
    //     var acc_table = $('#acc_table');
    //     var main_table = $('#main_table');
    //     var accType = main_table.datagrid('getEditor', {index:editRow,field:'accType'});
    //     var accType_value = $(accType.target).textbox('getValue');
    //     if(editAccRow != undefined){
    //         obj.saveAcc(editAccRow, is_debit, accType_value == 'Y');
    //         acc_table.datagrid('endEdit',editAccRow);
    //         editAccRow = undefined;
    //     }
    //     reloadFooterAcc();
    // }
    //
    // function confirmMoneyTable() {
    //     var money_table = $('#money_table');
    //     var main_table = $('#main_table');
    //     if(editMoneyRow != undefined){
    //         obj.saveMoney(editMoneyRow, is_debit);
    //         money_table.datagrid('endEdit',editMoneyRow);
    //         editMoneyRow = undefined;
    //     }
    //     reloadFooterMoney()
    // }


});

function getMonthDays(year,month){
    var stratDate = new Date(year,month-1,1),
        endData = new Date(year,month,1);
    var days = (endData -stratDate)/(1000*60*60*24);
    return days;
}

//获取路径
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
// }


//显示当前凭证的分录
function showMainTable(){
    $('#main_table').datagrid('loadData', []);
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountingVouchers/queryVouchersForDatagrid/' + first_no_ + voucher_no_ + '/' + getInputDate(),
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzk1QueryVoList;
                data_ = data;
                $('#main_table').datagrid('loadData', data);
                atta_ = $('#main_table').datagrid('getRows').length;
                $('#atta').val(atta_);
                entry_no_ = atta_;
                if(data != null){
                    $('#summary').textbox('setValue', data[0]['summary']);
                    $('#day').val(parseInt(data[0]['inputDate'].substr(6,2)));
                    $('#auditor').val(data[0]['auditor']);
                    $('#preact_doc').val(data[0]['preActDoc']);
                    $('#director').val(data[0]['directorNamee']);
                    if(data[0]['lastDate'] != null) $('#day').attr('min', data[0]['lastDate'].substr(6,2));
                    else $('#day').attr('min', 1);
                    if(data[0]['nextDate'] != null) $('#day').attr('max', data[0]['nextDate'].substr(6,2));
                    else $('#day').attr('max', 31);
                }
                $('#main_table').datagrid('sort', 'entryNo');
                reloadFooterTotal();
            }
        }
    });
}


//显示当前级的科目
function showCaption(num, level){
    var new_num = "";
    if(num==""){new_num="0";}
    else{new_num=num;}
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lskmzdList;
                $('#itemTable').datagrid('loadData', data);
            }
        }
    });
}


//显示当前分录的原始凭证
function showMoneyTable(editRow){
    if(data_[editRow].lsyspzList){
        var data = data_[editRow].lsyspzList;
        $('#money_table').datagrid('loadData', data);
        setInterval(reloadFooterMoney(),0);
    }
    else{
        $('#money_table').datagrid('loadData', []);
        setInterval(reloadFooterMoney(),0);
    }

    // $.ajax({
    //     type: 'POST',
    //     url: getRealPath() + '/AccountingVouchers/queryOriginalVouchers/' + voucher_no_ + '/' + getInputDate() + '/' + getCurrentEntryNo(editRow),
    //     contentType: 'application/json',
    //     success: function (result) {
    //         if(result.code == 100) {
    //             var data = result.extend.lsyspzList;
    //             $('#money_table').datagrid('loadData', data);
    //             reloadFooterMoney();
    //         }
    //     }
    // });
}

//显示当前分录的核算凭证
function showAccTable(editRow){
    if(data_[editRow].lshspzQueryVoList) {
        var data = data_[editRow].lshspzQueryVoList;
        $('#acc_table').datagrid('loadData', data);
        setInterval(reloadFooterAcc(),0);
    }
    else {
        var itemNo_editor = $('#main_table').datagrid('getEditor', {index: editRow, field: 'itemNo'});
        var itemNo = $(itemNo_editor.target).textbox('getValue');

        $.ajax({
            type: 'POST',
            url: getRealPath() + '/AccountingVouchers/querySpVouchers/' + voucher_no_ + '/' + getInputDate() + '/' + getCurrentEntryNo(editRow) + '/' + itemNo,
            contentType: 'application/json',
            success: function (result) {
                if (result.code == 100) {
                    var data = result.extend.lshspzQueryVoList;
                    if(data){
                        for(var i = 0; i < data.length; i++){
                            sp_vouchers.push(data[i]);
                        }
                    }
                    $('#acc_table').datagrid('loadData', data);
                    setInterval(reloadFooterAcc(),0);
                }
            }
        });
    }
}


//计算上级科目编号长度
function getNumLength(level) {
    if(level==0){
        return 0;
    }
    var length = 0;
    for(var i=0;i<level;i++){
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

//计算补零长度
function getZeroLength(level) {
    var length = 0;
    for(var i=level; i<sub_stru_.length; i++){
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

//获取科目级数
// function dealSubStru() {
//     $.ajax({
//         type: 'POST',
//         url: getRealPath() + '/CaptionOfAccount/getSubjectStructure/',
//         contentType: 'application/json',
//         success: function (result) {
//             var stru = result.split("");
//             for(var i=0;i<stru.length;i++){
//                 sub_stru_[i]=stru[i];
//             }
//             if(result=="0"){
//                 alert("请先设置科目结构");
//                 //TODO：关闭标签页
//             }
//         }
//     });
// }

function dealConfigs() {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/FinanceSearch/getConfigsForSearch/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                //处理科目结构
                if(!result.extend.sub_stru){
                    $.messager.alert('提示', '请先设置科目结构', 'info');
                    window.parent.tabsClose('#tab0');
                    return;
                }
                var stru = result.extend.sub_stru.split("");
                for (var i = 0; i < stru.length; i++) {
                    sub_stru_[i] = stru[i];
                }
                //处理财务日期
                current_date_ = result.extend.current_date;
                $('#year_input').attr("min", current_date_.substr(0, 4));//设置最大年份为当前年份
                $('#year_input').val(current_date_.substr(0, 4));
                $('#month_input').val(current_date_.substr(4, 2));
                $('#day').val(current_date_.substr(6, 2));
                //处理单位名称、经办人、出纳
                unit_name_ = result.extend.unit_name;
                $('#unit_name').val(unit_name_);
                operator_ = result.extend.operator;
                $('#operator').val(operator_);
                cashier_ = result.extend.cashier;
                $('#cashier').val(cashier_);
            }
        }
    });
}

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}

//指定列求和
function computeTotal(table, colName) {
    var rows = table.datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
        var add_item = parseFloat(rows[i][colName]);
        add_item = isNaN(add_item) ? 0 : add_item;
        total += add_item;
    }
    return total;
}

//重新加载金额页脚合计行
function reloadFooterMoney(){
    var table = $('#money_table');
    var money = computeTotal(table, "money");
    var qty = computeTotal(table, "qty");

    money = isNaN(money) ? 0 : money;
    qty = isNaN(qty) ? 0 : qty;

    table.datagrid('reloadFooter',
        [{"originalNo":"合计","money":money,"qty":qty}]
    );
    console.log(money, qty);
    return [money, qty];
}

//重新加载核算页脚合计行
function reloadFooterAcc(){
    var table = $('#acc_table');
    var money = computeTotal(table, "money");
    var qty = computeTotal(table, "qty");

    money = isNaN(money) ? 0 : money;
    qty = isNaN(qty) ? 0 : qty;

    table.datagrid('reloadFooter',
        [{"spNo1":"合计","money":money,"qty":qty}]
    );
    return [money, qty];
}

//重新加载主页面页脚合计行
function reloadFooterTotal(){
    var table = $('#main_table');
    var debit = computeTotal(table, "debitMoney");
    var credit = computeTotal(table, "creditMoney");

    debit = isNaN(debit) || debit == null ? 0 : debit;
    credit = isNaN(credit) || credit == null ? 0 : credit;

    table.datagrid('reloadFooter',
        [{"level_1":"合计","debitMoney":debit,"creditMoney":credit}]
    );
    return [debit, credit];
}

function getMoneyData(row, editRow, is_debit){

    var main_row = $('#main_table').datagrid('getEditors',editRow);
    var money_row = $('#money_table').datagrid('getEditors',row);

    var itemNo = $(main_row[1].target).textbox('getValue');
    var inputDate = getInputDate();
    var voucherNo = first_no_ + voucher_no_;
    var entryNo = $(main_row[0].target).textbox('getValue');
    var originalNo = $(money_row[0].target).textbox('getValue');
    var summary = $(money_row[1].target).numberbox('getValue');
    var bkpDirection = is_debit ? "J" : "D";
    var money = $(money_row[2].target).numberbox('getValue');
    var qty = $(money_row[3].target).numberbox('getValue');

    var data = {"itemNo": itemNo,
        "inputDate": inputDate,
        "voucherNo": voucherNo,
        "entryNo": entryNo,
        "originalNo": originalNo,
        "summary": summary,
        "bkpDirection": bkpDirection,
        "money": money,
        "qty": qty};

    return data;
}

function getAccData(row, editRow, is_debit){

    var main_row = $('#main_table').datagrid('getEditors',editRow);
    var acc_row = $('#acc_table').datagrid('getEditors',row);

    var itemNo = $(main_row[1].target).textbox('getValue');
    var inputDate = getInputDate();
    var voucherNo = first_no_ + voucher_no_;
    var entryNo = $(main_row[0].target).textbox('getValue');
    var spNo1 = $('#acc_table').datagrid('getRows')[row]['spNo1'];
    var spNo2 = $('#acc_table').datagrid('getRows')[row]['spNo2'];
    var spName1 = $('#acc_table').datagrid('getRows')[row]['spName1'];
    var spName2 = $('#acc_table').datagrid('getRows')[row]['spName2'];
    var bkpDirection = is_debit ? "J" : "D";
    var money = $(acc_row[0].target).numberbox('getValue');
    var qty = $(acc_row[1].target).numberbox('getValue');

    // var data = '{"itemNo":"' + itemNo + '",' +
    //     '"inputDate":"' + inputDate + '",' +
    //     '"voucherNo":"' + voucherNo + '",' +
    //     '"entryNo":"' + entryNo + '",' +
    //     '"spNo1":"' + spNo1 + '",' +
    //     '"spNo2":"' + spNo2 + '",' +
    //     '"bkpDirection":"' + bkpDirection + '",' +
    //     '"money":"' + money + '",' +
    //     '"qty":"' + qty + '"}';

    var data = {"itemNo": itemNo,
        "inputDate": inputDate,
        "voucherNo": voucherNo,
        "entryNo": entryNo,
        "spNo1": spNo1,
        "spNo2": spNo2,
        "spName1": spName1,
        "spName2": spName2,
        "bkpDirection": bkpDirection,
        "money": money,
        "qty": qty};

    return data;
}

function getEntryData(row, is_debit){
    var main_row = $('#main_table').datagrid('getEditors',row);

    var itemNo = $(main_row[1].target).textbox('getValue');
    var inputDate = getInputDate();
    var voucherNo = first_no_ + voucher_no_;
    var entryNo = $(main_row[0].target).textbox('getValue');
    var acsDocCnt = atta_;
    var voucherType = "01";
    var summary = $('#summary').textbox('getValue');
    var bkpDirection = is_debit ? "J" : "D";
    var money = 0;
    var qty = 0;
    if(is_debit){
        money = $(main_row[10].target).numberbox('getValue');
        qty = $(main_row[11].target).numberbox('getValue');
    }
    else{
        money = $(main_row[12].target).numberbox('getValue');
        qty = $(main_row[13].target).numberbox('getValue');
    }
    var preActDoc = user_name_;
    var preActNo = user_no_;
    //TODO:var directorName
    //TODO:var companyNo

    // var data = '{"itemNo":"' + itemNo + '",' +
    //     '"inputDate":"' + inputDate + '",' +
    //     '"voucherNo":"' + voucherNo + '",' +
    //     '"entryNo":"' + entryNo + '",' +
    //     '"acsDocCnt":"' + acsDocCnt + '",' +
    //     '"voucherType":"' + voucherType + '",' +
    //     '"summary":"' + summary + '",' +
    //     '"bkpDirection":"' + bkpDirection + '",' +
    //     '"money":"' + money + '",' +
    //     '"qty":"' + qty + '",' +
    //     '"preActDoc":"' + preActDoc + '",' +
    //     '"preActNo":"' + preActNo + '"}';

    var data = {"itemNo": itemNo,
        "inputDate": inputDate,
        "voucherNo": voucherNo,
        "entryNo": entryNo,
        "acsDocCnt": acsDocCnt,
        "voucherType": voucherType,
        "summary": summary,
        "bkpDirection": bkpDirection,
        "money": money,
        "qty": qty,
        "preActDoc": preActDoc,
        "preActNo": preActNo};

    return data;
}

function getInputDate(){
    var y = $('#year_input').val();
    var m = $('#month_input').val();
    var d = $('#day').val();
    return y+(m<10?('0'+m):m)+(d<10?('0'+d):d);
}

//TODO:财务录入日期不小于财务初始日期
function dealBeginDate() {

}

//获取指定位数的编号
function getFullNo(num, len){
    num = parseInt(num, 10);
    num = num.toString();
    while(num.length < len) {
        num = '0' + num;
    }
    return num;
}

function getCurrentEntryNo(editRow){
    var entryNo_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'entryNo'});
    var entryNo = entry_no_;
    if(editRow != undefined){
        entryNo = $(entryNo_editor.target).textbox('getValue');
    }
    return getFullNo(entryNo, 4);
}

// function getCurrentInputDate(editRow){
//     var inputDate_editor = $('#main_table').datagrid('getEditor', {index:editRow,field:'inputDate'});
//     var inputDate = getInputDate();
//     if(editRow != undefined){
//         inputDate = $(inputDate_editor.target).textbox('getValue');
//     }
//     return inputDate;
// }

function getVoucherNo(){
    return $('#voucher_no').val();
}

//获取url后的某一个query的值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}

//获取Url中中文参数的方法
function getQueryUrlString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if(r != null) {
        return decodeURI(r[2]);
        //alert(decodeURI(r[2]));
    }
    return '请登录';
}

//TODO:凭证编号目前有错，会显示nan或object
function getLattestVoucherNo(add_voucher){
    var year_month = getInputDate().substr(0, 6);
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountingVouchers/getVoucherData/'+ year_month,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzbh;
                voucher_no_ = getFullNo(parseInt(data['voucherNo'])-1, 4);
                first_no_ = data['voucherFirstChar'];
                lattest_voucher_no_ = voucher_no_;
                $('#voucher_no').val(first_no_ + voucher_no_);
                if(add_voucher){
                    voucher_no_ = getFullNo(parseInt(data['voucherNo']), 4);
                    $('#voucher_no').val(first_no_ + voucher_no_);
                }
                showMainTable();
            }
        }
    });
}

function getIndexOfData(data, key, id){
    for(var row in data){
        if(data[row][key] == id){
            return row;
        }
    }
    return -1;
}

//新增或删除凭证时更新凭证编号
function updateLattestVoucherNo(is_add){
    var year_month = getInputDate().substr(0, 6);
    var is_add_int = is_add ? 1 : 0;
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountingVouchers/updateVoucherData/' + is_add_int + '/' + year_month,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lspzbh;
                voucher_no_ = getFullNo(parseInt(data['voucherNo'])-1, 4);
                first_no_ = data['voucherFirstChar'];
                lattest_voucher_no_ = voucher_no_;
                add_voucher_ = false;
                $('#voucher_no').val(first_no_ + voucher_no_);
                showMainTable();
            }
        }
    });
}

//处理原始凭证借贷选择
function dealOriginalVoucherChoose(editRow){
    var table = $('#main_table');
    var money_table = $('#money_table');
    var acc_table = $('#acc_table');

    var accType = table.datagrid('getEditor', {index:editRow,field:'accType'});
    var accType_value = $(accType.target).textbox('getValue');

    var journal = table.datagrid('getEditor', {index:editRow,field:'journal'});
    var journal_value = $(journal.target).textbox('getValue');

    var supAcc1 = table.datagrid('getEditor', {index:editRow,field:'supAcc1'});
    var supAcc1_value = $(supAcc1.target).textbox('getValue');

    if (supAcc1_value == "" || supAcc1_value == null) {
        //判断是否为数量账
        if (accType_value == "Y") {
            money_table.datagrid('showColumn', 'qty');
        }
        else {
            money_table.datagrid('hideColumn', 'qty');
        }
        //判断是否为日记账
        if (journal_value == 1) {
            money_table.datagrid('showColumn', 'summary');
        }
        else {
            money_table.datagrid('hideColumn', 'summary');
        }
        showMoneyTable(editRow);
        $('#win_money').window('open');
    }
    else {
        //判断是否为数量账
        if (accType_value == "Y") {
            acc_table.datagrid('showColumn', 'qty');
        }
        else {
            acc_table.datagrid('hideColumn', 'qty');
        }
        showAccTable(editRow);
        $('#win_acc').window('open');
        reloadFooterAcc();
    }
}

//更换借贷方向
function changeBkpDirection(editRow, is_debit){
    //更换借贷方向
    var table = $('#main_table');
    var entry_no = table.datagrid('getEditor', {index:editRow,field:'entryNo'});
    var entry_no_value = $(entry_no.target).textbox('getValue');
    var item_no = table.datagrid('getEditor', {index:editRow,field:'itemNo'});
    var item_no_value = $(item_no.target).textbox('getValue');
    var credit = table.datagrid('getEditor', {index:editRow,field:'creditMoney'});
    var debit = table.datagrid('getEditor', {index:editRow,field:'debitMoney'});
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/AccountingVouchers/changeBkpDirection/'+getInputDate()+'/'+first_no_+voucher_no_+'/'+entry_no_value+'/'+item_no_value,
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                if(is_debit){
                    //如果要转为借方，则把贷方金额清零
                    $(credit.target).textbox('setValue',null);
                }
                else{
                    //否则把借方金额清零
                    $(debit.target).textbox('setValue',null);
                }

                dealOriginalVoucherChoose(editRow);

                $.messager.show({
                    title: '提示',
                    msg: "成功更改借贷方向"
                });
                return true;
            } else {
                var data = result.extend.errorInfo; 
                $.messager.alert('提示', data, 'info');
                return false;
            }
        }
    });
}