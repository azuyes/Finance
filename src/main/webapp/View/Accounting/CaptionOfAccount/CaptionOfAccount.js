var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var upper_name_ = [];
var sub_stru_ = [];//存储科目结构
var precisions_ = [2,2];//存储金额和数量小数精度
var begin_month_ = "";

$(function() {
	$('#level').val(level_);
	$('#superior_account').val(upper_No_);
	//$('#superior_account_name').val(upper_name_[upper_name_.length-1]);
	showUpperAccName();
	// dealSubStru();//保存科目级数
	// dealPrecisions();
	// dealBeginMonth();
	dealConfigs();
	showCaption(upper_No_,level_.toString());

	var acc_form_stack = [];
	var acc_attr_stack = [];
	var acc_stack = [];
	obj = {
		//上级点击事件
		uperLevel_click : function () {
			if(level_==1){
				$.messager.alert('提示', '1级科目没有上级', 'info');
				return;
			}
			level_--;
			$('#level').val(level_);
			upper_No_=getCatNo(upper_No_,level_-1);
			$('#superior_account').val(upper_No_);
			upper_name_.pop();
			//$('#superior_account_name').val(upper_name_[upper_name_.length-1]);
			showUpperAccName();
			acc_attr_stack.pop();
			acc_form_stack.pop();
			acc_stack.pop();
			showCaption(upper_No_,level_);
		},
		//下级点击事件
		nextLevel_click : function () {
			var row = $('#main_table').datagrid('getSelected');
			//判断是否选中行
			if(row==null){
				$.messager.alert('提示', '请选择科目', 'info');
				return;
			}
			level_++;
			$('#level').val(level_);
			upper_No_ = getCatNo(row.itemNo,level_-1); //获取选中的分类编号
			$('#superior_account').val(upper_No_);
			upper_name_.push(row.lskmzd.itemName);
			//$('#superior_account_name').val(upper_name_[upper_name_.length-1]);
			showUpperAccName();
			acc_attr_stack.push(row.lskmzd.ele);
			acc_form_stack.push(row.lskmzd.accType);
			acc_stack.push(row.lskmzd.spType);
			showCaption(upper_No_,level_);
		},
		
		//增加事件
		add : function () {
			var len = sub_stru_[level_-1];
			$('#account_number_input').textbox({validType:['length[' + len + ',' + len + ']'], invalidMessage:'编号长度应为' + len});

			$('#add_account_box').window('open');
			$('#add_account_box').prop('title','增加会计科目');
			$('#add_form').form('clear');
			$('#account_number_input').prop('missingMessage',"请输入"+sub_stru_[level_-1]+"位编号");
			chooseMoneyOrQty($('#account_form_select').combobox('getValue'));
			readonlyTextBox([$('#account_number_input'),$('#account_attribute_select'),$('#account_form_select'),$('#begin_remain_input'),$('#this_debit_input'),$('#this_credit_input'),$('#balance_input'),$('#begin_quantity_input'),$('#this_debit_quantity_input'),$('#this_credit_quantity_input'),$('#quantity_input'),$('#form_head1_input'),$('#form_head2_input'),$('#form_head3_input'),$('#form_head4_input'),$('#form_head5_input'),$('#form_head6_input')],false)
			// $('#account_attribute_select').textbox('readonly',false);
			// $('#account_form_select').textbox('readonly',false);
			//下级科目科目属性和账式和上级一致不允许修改
			if(level_!=1){
				$('#account_attribute_select').combobox('setValue',acc_attr_stack[acc_attr_stack.length-1]);
				$('#account_attribute_select').combobox('readonly',true);
				$('#account_form_select').combobox('setValue',acc_form_stack[acc_form_stack.length-1]);
				$('#account_form_select').combobox('readonly',true);
				//增加非一级科目时，科（栏）目默认继承上级设置，
				// 如果上一级是借方栏目或贷方栏目，其下级也必须是和上级一致，不能修改为科目
				$('#account_select').combobox('setValue',acc_stack[acc_stack.length-1]);
				if(acc_stack[acc_stack.length-1] == ' ') {
					$('#account_select').combobox('readonly', false);
				}
				else{
					$('#account_select').combobox('readonly', true);
				}
			}
			else{
				//一级科目只能设置科（栏）目为科目
				$('#account_select').combobox('select',' ');
				$('#account_select').combobox('readonly',true);
				$('#account_attribute_select').combobox('readonly',false);
				$('#account_form_select').combobox('readonly',false);
			}
		},
		
		//修改事件
		edit : function () {
			var row = $('#main_table').datagrid('getSelected');
			//判断是否选中行
			if(row==null){
				$.messager.alert('提示', '请选择要编辑的科目', 'info');
				return;
			}
			var len = sub_stru_[level_-1];
			$('#account_number_input').textbox({validType:['length[' + len + ',' + len + ']'], invalidMessage:'编号长度应为' + len});
			
			$('#add_account_box').window('open');
			$('#add_account_box').prop('title','修改会计科目');
			fillEditForm(row);
			chooseMoneyOrQty($('#account_form_select').combobox('getValue'));
			//下级科目科目属性和账式和上级一致不允许修改
			if(level_!=1){
				$('#account_attribute_select').combobox('readonly',true);
				$('#account_form_select').combobox('readonly',true);
				$('#account_select').combobox('readonly',true);
			}
			else{
				$('#account_attribute_select').combobox('readonly',false);
				$('#account_form_select').combobox('readonly',false);
				$('#account_select').combobox('readonly',true);
			}
			//不允许修改编号
			readonlyTextBox([$('#account_number_input')], true);
			//非明细科目不允许修改金额或数量信息、科目编号
			if(row.lskmzd.finLevel.toString() != "1"){
				//$('#account_attribute_select').combobox('readonly',true);
				//$('#account_form_select').combobox('readonly',true);
				readonlyTextBox([$('#begin_remain_input'),$('#this_debit_input'),$('#this_credit_input'),$('#balance_input'),$('#begin_quantity_input'),$('#this_debit_quantity_input'),$('#this_credit_quantity_input'),$('#quantity_input'),$('#form_head1_input'),$('#form_head2_input'),$('#form_head3_input'),$('#form_head4_input'),$('#form_head5_input'),$('#form_head6_input')],true)
			}
			else{
				//$('#account_attribute_select').combobox('readonly',false);
				//$('#account_form_select').combobox('readonly',false);
				readonlyTextBox([$('#begin_remain_input'),$('#this_debit_input'),$('#this_credit_input'),$('#balance_input'),$('#begin_quantity_input'),$('#this_debit_quantity_input'),$('#this_credit_quantity_input'),$('#quantity_input'),$('#form_head1_input'),$('#form_head2_input'),$('#form_head3_input'),$('#form_head4_input'),$('#form_head5_input'),$('#form_head6_input')],false)
			}
		},

		//存盘事件
		save : function () {
			if(!$('#add_form').form("validate")) {//判断校验是否通过
				return $('#add_form').form("validate");
			}
			if($('#account_number_input').val().length!=sub_stru_[level_-1]){
				$.messager.alert('提示', '科目编号应输入'+sub_stru_[level_-1]+'位', 'info');
				return;
			}

			//输入金额/数量必须平才能保存
			var begin_qty = "", this_debit_qty = "", this_credit_qty = "", current_qty = "",begin = "", this_debit = "", this_credit = "", current = "";
			var qty_flag = $('#account_form_select').combobox('getValue')=="Y" ? true : false;
			if($('#account_form_select').combobox('getValue')=="Y"){
				begin_qty = $('#begin_quantity_input').val();
				this_debit_qty = $('#this_debit_quantity_input').val();
				this_credit_qty = $('#this_credit_quantity_input').val();
				current_qty = $('#quantity_input').val();

				begin = $('#begin_remain_input').val();
				this_debit = $('#this_debit_input').val();
				this_credit = $('#this_credit_input').val();
				current = $('#balance_input').val()
			}
			else{
				begin = $('#begin_remain_input').val();
				this_debit = $('#this_debit_input').val();
				this_credit = $('#this_credit_input').val();
				current = $('#balance_input').val();
			}
			var sum = parseInt(begin) + parseInt(this_debit) - parseInt(this_credit);
			var sum_qty = parseInt(begin_qty) + parseInt(this_debit_qty) - parseInt(this_credit_qty);
			if(sum != current){
				$.messager.alert('提示', '当前金额余额 = '+ current + '\r\n年初金额余额 + 本年借方金额 - 本年贷方金额 = ' + sum, 'info');
				return;
			}
			else if(qty_flag && sum_qty != current_qty){
				$.messager.alert('提示', '当前数量余额 = '+ current_qty + '\r\n年初数量余额 + 本年借方数量 - 本年贷方数量 = ' + sum_qty, 'info');
				return;
			}

			var money_data = getMoneyData();
			var quan_data = getQuantityData();
			if($('#add_account_box').prop('title')=='增加会计科目'){
				//存储金额数据到LSKMXD
				$.ajax({
					type: 'POST',
					url: getRealPath() + '/CaptionOfAccount/addAllCaptionOfAccount',
					contentType: 'application/json',
					data: '{"lskmzd":' + money_data + ', "lskmsl": ' + quan_data + '}',
					success: function (result) {
						if (result.code == 100) {
							showCaption(upper_No_,level_);
							//保存成功，关闭对话框，刷新数据最后一页
							$("#add_account_box").window('close');
							$('#add_form').form('clear');
							$.messager.show({
								title: '提示',
								msg: "添加成功"
							});
						} else {
							var data = result.extend.errorInfo;
							$.messager.alert('提示', data, 'info');
							//$('#add_form').form('clear');
						}
					}
				});
				//如果账式选择了数量则向LSKMSL存储数量相关的信息
				// if($('#account_form_select').combobox('getValue') == "Y"){
				// 	$.ajax({
				// 		type: 'POST',
				// 		url: getRealPath() + '/CaptionOfAccount/addCaptionOfAccountQuantity',
				// 		contentType: 'application/json',
				// 		data: quan_data,
				// 		success: function (result) {
				// 			if (result.code == 100) {
				// 				showCaption(upper_No_,level_);
				// 				//保存成功，关闭对话框，刷新数据最后一页
				// 				$("#add_account_box").window('close');
				// 				$('#add_form').form('clear');
				// 				$.messager.show({
				// 					title: '提示',
				// 					msg: "添加成功"
				// 				});
				// 			} else {
				// 				var data = result.extend.errorInfo;
				// 				$.messager.alert('提示', data, 'info');
				// 				//$('#add_form').form('clear');
				// 			}
				// 		}
				// 	});
				// }
			}
			else{
				//修改会计科目
				//存储金额数据到LSKMXD
				$.ajax({
					type: 'POST',
					url: getRealPath() + '/CaptionOfAccount/editAllCaptionOfAccount',
					contentType: 'application/json',
					data: '{"lskmzd":' + money_data + ', "lskmsl": ' + quan_data + '}',
					success: function (result) {
						if (result.code == 100) {
							showCaption(upper_No_,level_);
							//保存成功，关闭对话框，刷新数据最后一页
							$("#add_account_box").window('close');
							$('#add_form').form('clear');
							$.messager.show({
								title: '提示',
								msg: "修改成功",
							});
						} else {
							var data = result.extend.errorInfo;
							$.messager.alert('提示', data, 'info');
							//$('#add_form').form('clear');
						}
					}
				});
				//如果账式选择了数量则向LSKMSL存储数量相关的信息
				// if($('#account_form_select').combobox('getValue') == "Y"){
				// 	$.ajax({
				// 		type: 'POST',
				// 		url: getRealPath() + '/CaptionOfAccount/editCaptionOfAccountQty',
				// 		contentType: 'application/json',
				// 		data: quan_data,
				// 		success: function (result) {
				// 			if (result.code == 100) {
				// 				showCaption(upper_No_,level_);
				// 				//保存成功，关闭对话框，刷新数据最后一页
				// 				$("#add_account_box").window('close');
				// 				$('#add_form').form('clear');
				// 				$.messager.show({
				// 					title: '提示',
				// 					msg: "修改成功",
				// 				});
				// 			} else {
				// 				var data = result.extend.errorInfo;
				// 				$.messager.alert('提示', data, 'info');
				// 				//$('#add_form').form('clear');
				// 			}
				// 		}
				// 	});
				// }
			}
		},

		//删除事件
		remove : function () {
			var row = $('#main_table').datagrid('getSelected');
			if (row) {//判断是否选中行
				var text = '您要删除所选的记录吗';
				if(row.lskmzd.finLevel.toString() != "1"){
					text = '当前类别不是末级，' + text;
				}
				$.messager.confirm('确定操作', text, function (flag) {
					if (flag) {
						//TODO:凭证库中有记录的、输入金额的不能删
							var item_no = row.lskmzd.itemNo.toString();
							var is_quan = row.lskmzd.accType.toString();
							$.ajax({
								type : 'POST',
								url:getRealPath()+'/CaptionOfAccount/delCaptionOfAccount/' + item_no + '/' + level_ + '/'+ is_quan,
								success : function (result) {
									if (result.code == 100) {
										showCaption(upper_No_,level_);
										$.messager.show({
											title : '提示',
											msg : '删除成功'
										});
									}else {
										var data = result.extend.errorInfo;
										$.messager.alert('提示', data, 'info');
									}
								}
							});
					}
				});
			} else {
				$.messager.alert('提示', '请选择要删除的记录！', 'info');
			}
		},
		//转出事件
		output : function () {
			
		}
	}

	//账式选择金额或数量
	$('#account_form_select').combobox({
		onChange: function (newValue, oldValue) {
			chooseMoneyOrQty(newValue);
		}
	});
	
	$('#add_account_box').window({
		width: 570,
		height: 400,
		padding: 10,
		title: '增加会计科目',
		iconCls: 'icon-add',
		closed: true,
		resizable: false,
		minimizable: false,
		maximizable: false,
		collapsible: false,
		modal: true
	});

	$('#main_table').datagrid({
		width: '100%',
		multiSort: false,
		toolbar: "#button_group",
		fit: true,
		singleSelect: true,
		showFooter: true,
		//双击进入下一级事件
		onDblClickRow : function (rowIndex, rowData) {
			obj.nextLevel_click();
		},
		columns: [
			[{
					field: 'itemNo',
					title: '科目编号',
					width: '9%',
					align: 'center',
					formatter: function (value,row,index) {
						if(!row.lskmzd) return value;
						var length = getNumLength(level_-1);
						var no = value.substr(length,sub_stru_[level_-1]);
						return no;
					}
				},
				{
					field: 'itemName',
					title: '科目名称',
					width: '9%',
					align: 'left',
					formatter: function (value,row,index) {
						if(row.lskmzd) value = row.lskmzd.itemName;
						return value;
					}
				},
				{
					field: 'ele',
					title: '属性名称',
					width: '9%',
					align: 'center',
					formatter: function (value,row,index) {
						if(row.lskmzd) value = row.lskmzd.ele;
						switch(value){
							case "01": return "资产类";
							case "02": return "负债类";
							case "03": return "所有者权益类";
							case "04": return "共同类";
							case "05": return "成本类";
							case "06": return "损益类";
							default: return null;
						}
					}
				},
				{
					field: 'accType',
					title: '账式',
					width: '9%',
					align: 'center',
					formatter: function (value,row,index) {
						if(row.lskmzd) value = row.lskmzd.accType;
						switch(value){
							case "J": return "金额";
							case "Y": return "数量";
							default: return null;
						}
					}
				},
				{
					field: 'spType',
					title: '栏目',
					width: '9%',
					align: 'center',
					formatter: function (value,row,index) {
						if(row.lskmzd) value = row.lskmzd.spType;
						else return null;
						switch(value){
							case "J": return "借方栏目";
							case "D": return "贷方栏目";
							default: return "科目";
						}
					}
				},
				// {
				// 	field: 'exchange',
				// 	title: '往来',
				// 	width: '9%',
				// 	align: 'center',
				// 	formatter: function (value,row,index) {
				// 		switch(value){
				// 			case 1: return "单位往来";
				// 			case 0: return "非单位往来";
				// 		}
				// 	}
				// },
				{
					field: 'finLevel',
					title: '明细否',
					width: '9%',
					align: 'center',
					formatter: function (value,row,index) {
						if(row.lskmzd) value = row.lskmzd.finLevel;
						switch(value){
							case 1: return "是";
							case 0: return "否";
							default: return null;
						}
					}
				},
				{
					field: 'balance',
					title: '当前余额',
					width: '9%',
					halign: 'center',
					align: 'right',
					formatter:function(value,row,rowIndex){
						if(row.lskmzd) value = row['lskmzd'][getBeginMonth("J")];
						return thousandFormatter(value);
					}
				},
				{
					field: 'supMoney',
					title: '年初余额',
					width: '9%',
					halign: 'center',
					align: 'right',
					formatter:function(value,row,rowIndex){
						if(row.lskmzd) value = row.lskmzd.supMoney;
						return thousandFormatter(value);
					}
				},
				{
					field: 'debitMoneySup',
					title: '本年借方',
					width: '9%',
					halign: 'center',
					align: 'right',
					formatter:function(value,row,rowIndex){
						if(row.lskmzd) value = row.lskmzd.debitMoneySup;
						return thousandFormatter(value);
					}
				},
				{
					field: 'creditMoneySup',
					title: '本年贷方',
					width: '9%',
					halign: 'center',
					align: 'right',
					formatter:function(value,row,rowIndex){
						if(row.lskmzd) value = row.lskmzd.creditMoneySup;
						return thousandFormatter(value);
					}
				}
			]
		]
	});

	//  $('#money').click(){
	//  	$('#quantity_information input').disabled="disabled";
	//  }

});

function request(){

}

//账式选择金额或数量
function chooseMoneyOrQty(value){
	//var money_inputs = new Array($('#begin_remain_input'),$('#this_debit_input'),$('#this_credit_input'),$('#balance_input'));
	var quan_inputs = new Array($('#begin_quantity_input'),$('#this_debit_quantity_input'),$('#this_credit_quantity_input'),$('#quantity_input'),$('#form_head1_input'),$('#form_head2_input'),$('#form_head3_input'),$('#form_head4_input'),$('#form_head5_input'),$('#form_head6_input'));
	if(value=="J"){//选择金额则disable数量相关输入框
		//dealTextBox(money_inputs,true);
		dealTextBox(quan_inputs,false);
	}
	else if(value=="Y"){//选择数量则enable数量相关
		//dealTextBox(money_inputs,false);
		dealTextBox(quan_inputs,true);
	}
	else{
		//dealTextBox(money_inputs,true);
		dealTextBox(quan_inputs,true);
	}
}

//显示当前级的科目
function showCaption(num, level){
	var new_num = "";
	if(num==""){new_num="0";}
	else{new_num=num;}
	$.ajax({
		type: 'POST',
		url: getRealPath() + '/CaptionOfAccount/queryAllCaptionOfAccountByLevel/' + new_num + '/' + level,
		contentType: 'application/json',
		success: function (result) {
			if(result.code == 100) {
				var data = result.extend.lskmzdNLskmslQueryVos;
				$('#main_table').datagrid('loadData', data);
				setInterval(reloadFooterTotal(data),0);
			}
		}
	});
	//reloadFooterTotal();
}

//TODO:从后台获取数量表头
//查找数量账式的表头单位
function getQtyHead() {
	$.ajax({
		type: 'POST',
		url: getRealPath() + '/CaptionOfAccount/getQtyHead',
		contentType: 'application/json',
		success: function (result) {
			if(result.code == 100){
				var data = result.extend.headList;
				$('#form_head1_input').textbox('setValue',data[0]);
			}
		}
	});
}

//重新加载页脚合计行
function reloadFooterTotal(data){
	var balance = computeTotal(data, getBeginMonth("J"));
	var supMoney = computeTotal(data, "supMoney");
	var debitMoney = computeTotal(data, "debitMoneySup");
	var creditMoney = computeTotal(data, "creditMoneySup");

	balance = isNaN(balance) ? 0 : balance;
	supMoney = isNaN(supMoney) ? 0 : supMoney;
	debitMoney = isNaN(debitMoney) ? 0 : debitMoney;
	creditMoney = isNaN(creditMoney) ? 0 : creditMoney;

	console.log(balance, supMoney, debitMoney, creditMoney);

	$('#main_table').datagrid('reloadFooter',
		[{itemNo:"合计",itemName:null,ele:null,accType:null,spType:null,finLevel:null,balance:balance,supMoney:supMoney,debitMoneySup:debitMoney,creditMoneySup:creditMoney}]
	);
}

//指定列求和
function computeTotal(data, colName) {
	var rows = data;
	var total = 0;
	for (var i = 0; i < rows.length; i++) {
		var add_item = parseFloat(rows[i]['lskmzd'][colName]);
		add_item = isNaN(add_item) ? 0 : add_item;
		total += add_item;
	}
	total = isNaN(total) ? 0 : total;
	return total;
}

//计算当前级别科目编号长度
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

//计算上级科目编号
function getCatNo(num,level){
	var length = getNumLength(level);
	var no = num.substring(0,length);
	return no;
}

function fillEditForm(row) {
	var length = getNumLength(level_-1);
	var no = row.itemNo.substr(length,sub_stru_[level_-1]);
	$('#account_number_input').textbox('setValue',no);
	$('#account_name_input').textbox('setValue',row.lskmzd.itemName);
	$('#account_attribute_select').combobox('setValue',row.lskmzd.ele);
	$('#account_form_select').combobox('setValue',row.lskmzd.accType);
	$('#account_select').combobox('setValue',row.lskmzd.spType);
	if(row.lskmzd.spType == '' || row.lskmzd.spType == null){
		$('#account_select').combobox('setValue',' ');
	}
	if(row.lskmzd.accType=="Y"){
		$('#begin_remain_input').numberbox('setValue',row.lskmzd.supMoney);
		$('#this_debit_input').numberbox('setValue',row.lskmzd.debitMoneySup);
		$('#this_credit_input').numberbox('setValue',row.lskmzd.creditMoneySup);
		$('#balance_input').numberbox('setValue',row.lskmzd[getBeginMonth("J")]);
		$('#begin_quantity_input').numberbox('setValue',row.lskmsl.supQty);
		$('#this_debit_quantity_input').numberbox('setValue',row.lskmsl.debitQtySup);
		$('#this_credit_quantity_input').numberbox('setValue',row.lskmsl.creditQtySup);
		$('#quantity_input').numberbox('setValue',row.lskmsl[getBeginMonth("Y")]);
	}
	else{
		$('#begin_remain_input').numberbox('setValue',row.lskmzd.supMoney);
		$('#this_debit_input').numberbox('setValue',row.lskmzd.debitMoneySup);
		$('#this_credit_input').numberbox('setValue',row.lskmzd.creditMoneySup);
		$('#balance_input').numberbox('setValue',row.lskmzd[getBeginMonth("J")]);
	}
}

//获取金额账式的数据
function getMoneyData() {
	var acc_level = (level_).toString();
	var acc_num = getFullNo(upper_No_, $('#account_number_input').val(), level_);
	var acc_input = $('#account_name_input').val();
	var acc_attr = $('#account_attribute_select').combobox('getValue');
	var acc_form = $('#account_form_select').combobox('getValue');
	var acc = $('#account_select').combobox('getValue');

	var begin = $('#begin_remain_input').val();
	var this_debit = $('#this_debit_input').val();
	var this_credit = $('#this_credit_input').val();
	var current = $('#balance_input').val();



	var data = '{"item":"' + acc_level + '",' +
		'"itemNo":"' + acc_num + '",' +
		'"itemName":"' + acc_input + '",' +
		'"ele":"' + acc_attr + '",' +
		'"accType":"' + acc_form + '",' +
		'"spType":"' + acc + '",' +
		'"supMoney":"' + begin + '",' +
		'"debitMoneySup":"' + this_debit + '",' +
		'"creditMoneySup":"' + this_credit + '",' +
		'"balance":"' + current + '",' +
	 	'"' + getBeginMonth("J") + '":"' + current + '"}';

	return data;
}

//获取数量账式的数据
function getQuantityData(){
	var acc_num = getFullNo(upper_No_, $('#account_number_input').val(), level_);
	var form_head_1 = $('#form_head1_input').val();
	var form_head_2 = $('#form_head2_input').val();
	var form_head_3 = $('#form_head3_input').val();
	var form_head_4 = $('#form_head4_input').val();
	var form_head_5 = $('#form_head5_input').val();
	var form_head_6 = $('#form_head6_input').val();

	var begin = $('#begin_quantity_input').val();
	var this_debit = $('#this_debit_quantity_input').val();
	var this_credit = $('#this_credit_quantity_input').val();
	var current = $('#quantity_input').val();

	var data = '{"itemNo":"' + acc_num + '",' +
		'"supQty":"' + begin + '",' +
		'"debitQtySup":"' + this_debit + '",' +
		'"creditQtySup":"' + this_credit + '",' +
		'"leftQty":"' + current + '",' +
		'"' + getBeginMonth("Y") + '":"' + current + '",'+
		'"head1":"' + form_head_1 + '",' +
		'"head2":"' + form_head_2 + '",' +
		'"head3":"' + form_head_3 + '",' +
		'"head4":"' + form_head_4 + '",' +
		'"head5":"' + form_head_5 + '",' +
		'"head6":"' + form_head_6 + '"}';
	return data;
}

//获得财务初始日期前一个月的月份，如果初始日期是一月，则使用上年结转金额
//type为J则输出金额相关的结转字段名，为Y则输出数量相关
function getBeginMonth(type){
			if(begin_month_=="01"){
				if(type=="J") return "supMoney";
				else if(type=="Y") return "supQty";
			}
			else if(begin_month_=="0"){
				$.messager.alert('提示', "请先设置科目结构", 'info');
				//TODO：关闭标签页
			}
			else{
				var m = parseInt(begin_month_) - 1;
				var last_month = m < 10 ? ('0' + m.toString()) : m.toString();
				if(type=="J") return "balance"+last_month;
				else if(type=="Y") return "leftQty"+last_month;
			}
}

//获得完整的科目编号，即补0后的编号
function getFullNo(uperNo,thisNo,level) {
	var length = getZeroLength(level);
	var fullNo = uperNo.toString() + thisNo.toString();
	for(var i=0;i<length;i++){
		fullNo += "0";
	}
	return fullNo;
}

//批量enable或disable文本框
function dealTextBox(boxes, is_show){
	if(is_show==true){
		for(var i=0; i<boxes.length; i++){
			boxes[i].textbox('enable');
		}
	}
	else{
		for(var i=0;i<boxes.length;i++){
			boxes[i].textbox('disable');
			boxes[i].textbox('clear');
		}
	}
}

//批量将文本框设置为只读或不只读
function readonlyTextBox(boxes, is_readonly){
	if(is_readonly==true){
		for(var i=0; i<boxes.length; i++){
			boxes[i].textbox('readonly',true);
		}
	}
	else{
		for(var i=0;i<boxes.length;i++){
			boxes[i].textbox('readonly',false);
		}
	}
}

//显示上级科目名称
function showUpperAccName() {
	var name = '';
	for(var i = upper_name_.length - 1; i >= 0; i-- ){
		name = '/' + upper_name_[i] + name;
	}
	$('#superior_account_name').val(name);
}

function dealConfigs(){
	$.ajax({
		type: 'POST',
		async: false,
		url: getRealPath() + '/CaptionOfAccount/getConfigsForCap/',
		contentType: 'application/json',
		success: function (result) {
			if (result.code == 100) {
				//处理科目结构
				var stru = result.extend.sub_stru.split("");
				for(var i=0;i<stru.length;i++){
					sub_stru_[i]=stru[i];
				}
				//处理初始日期
				begin_month_ = result.extend.begin_date.substr(4, 2);
				//处理精度
				var money_prec = result.extend.price_dec;
				var quan_prec = result.extend.quantity_dec;

				var money_boxes = [$('#begin_remain_input'), $('#this_debit_input'), $('#this_credit_input'), $('#balance_input')];
				var qty_boxes = [$('#begin_quantity_input'), $('#this_debit_quantity_input'), $('#this_credit_quantity_input'), $('#quantity_input')];

				for (var i = 0; i < money_boxes.length; i++) {
					money_boxes[i].numberbox({precision: money_prec});
					console.log(money_boxes[i].numberbox('options'));
				}
				for (var i = 0; i < qty_boxes.length; i++) {
					qty_boxes[i].numberbox({precision: quan_prec});
					console.log(qty_boxes[i].numberbox('options'));
				}
				for (var i = 1; i <= 6; i++) {
					var table = result.extend['table' + i];
					if(table) document.getElementById('form_head' + i + '_label').innerHTML = table + ':';
					console.log('表头'+i+'：', table);
				}
			}
		}
	});
}

// function dealBeginMonth(){
// 	$.ajax({
// 		type: 'POST',
// 		url: getRealPath() + '/CaptionOfAccount/getBeginMonth/',
// 		contentType: 'application/json',
// 		success: function (result) {
// 			begin_month_ = result;
// 		}
// 	});
// }
//
// //获取科目级数
// function dealSubStru() {
// 	$.ajax({
// 		type: 'POST',
// 		url: getRealPath() + '/CaptionOfAccount/getSubjectStructure/',
// 		contentType: 'application/json',
// 		success: function (result) {
// 			var stru = result.split("");
// 			for(var i=0;i<stru.length;i++){
// 				sub_stru_[i]=stru[i];
// 			}
// 			if(result=="0"){
// 				$.messager.alert('提示', "请先设置科目结构", 'info');
// 				//TODO：关闭标签页
// 			}
// 		}
// 	});
// }
//
// //获取金额和数量的小数精度
// function dealPrecisions() {
// 	$.ajax({
// 		type: 'POST',
// 		url: getRealPath() + '/CaptionOfAccount/getPrecisions/',
// 		contentType: 'application/json',
// 		success: function (result) {
// 			if(result.code == 100) {
// 				var data = result.extend.prec_list;
//
// 				var money_prec = JSON.parse(JSON.stringify(data))[0];
// 				var quan_prec = JSON.parse(JSON.stringify(data))[1];
//
// 				var money_boxes = [$('#begin_remain_input'), $('#this_debit_input'), $('#this_credit_input'), $('#balance_input')];
// 				var qty_boxes = [$('#begin_quantity_input'), $('#this_debit_quantity_input'), $('#this_credit_quantity_input'), $('#quantity_input')];
//
// 				for (var i = 0; i < money_boxes.length; i++) {
// 					money_boxes[i].numberbox({precision: money_prec});
// 				}
// 				for (var i = 0; i < qty_boxes.length; i++) {
// 					qty_boxes[i].numberbox({precision: quan_prec});
// 				}
// 			}
// 			else{
// 				$.messager.alert('提示', "请先设置小数位数", 'info');
// 				//TODO：关闭标签页
// 			}
// 		}
// 	});
// }

//获取路径
// function getRealPath(){
//
// 	var localObj = window.location;
// 	var contextPath = localObj.pathname.split("/")[1];
// 	var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
// 	return basePath ;
// }