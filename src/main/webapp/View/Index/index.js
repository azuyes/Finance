var userName;
var userNo;
var department = "";
$(function () {
	//自定义validatebox规则
	$.extend($.fn.validatebox.defaults.rules,{
		equalTo: {
			validator: function (value, param) {
				return value == $(param[0]).val();
			},
			message: '两次输入的字符不一至'
		},
		idCode:{
			validator:function(value,param){
				return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
			},
			message: '请输入正确的身份证号'
		},
		loginName: {
			validator: function (value, param) {
				return /^[\u0391-\uFFE5\w]+$/.test(value);
			},
			message: '登录名称只允许汉字、英文字母、数字及下划线。'
		}
	});

	//获取cookie字符串
	var strCookie=document.cookie;
	//将多cookie切割为多个名/值对
	var arrCookie=strCookie.split(";");
	var status;
	//遍历cookie数组，处理每个cookie对，找出status对应的值
	for(var i=0;i<arrCookie.length;i++){
		var arr=arrCookie[i].split("=");
		//找到名称为userId的cookie，并返回它的值
		if("status"==arr[0]){
			status=arr[1];
			break;
		}
	}
	//先判断cookie的值是否为success
	if(status=="success"){
		document.cookie="status=failse;path=/";

		//获取用户id

		//userName = Request.QueryString("userName");
		//userNo = Request.QueryString("userNo");
		userNo = getQueryString('userNo');

		getUserInfo(userNo);

		userName = getQueryUrlString('userName');
		document.getElementById('User').innerHTML = "用户：" + userName;
		document.getElementById('time1').innerHTML = "当前财务日期：" + getNowFormatDate();
		document.getElementById('Unit').innerHTML = "单位：" + department;
//		var financeDate = GetFinacialDate();
//		document.cookie = financeDate; //建立cooki  财务日期供其他页面使用

//	function westClick(id) {
//		$(id).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
//		$(id).css("color", "#2b7dbc").siblings().css("color", "#585858");
//		$(id).css("font-weight", "600").siblings().css("font-weight", "500");
//	}

		$("#west_report li").click(
//		westClick("#west_report li");
			function () {
				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
			}
		);
		$("#west_accounting li").click(
			function () {
				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
			}
		);
		$("#west_auxiliary li").click(
			function () {
				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
			}
		);
		$("#west_other li").click(
			function () {
				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
			}
		);
		$("#west_costmanagement li").click(
			function () {
				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
			}
		);

//	function westClick() {
//		$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
//		$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
//		$(this).css("font-weight", "600").siblings().css("font-weight", "500");
//	}

		$('#north_menu a').click(
			function () {
				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
			}
		);

		<!--初始化设置全部菜单不可用-->

		$('#accounting_module').css("display", 'none');//
		$('#report_module').css("display", 'none');//
		$('#auxiliary_module').css("display", 'none');//
		$('#costmanagement_module').css("display", 'none');//
		//$('#other_module').css("display", 'none');//

		//隐藏中间层
		document.getElementById("voucher_deal").parentNode.style.display = "none";
		document.getElementById("statistical_query").parentNode.style.display = "none";
		document.getElementById("monthly_carrydown").parentNode.style.display = "none";
		document.getElementById("accounting_initiate").parentNode.style.display = "none";
		document.getElementById("form_management").parentNode.style.display = "none";
		document.getElementById("form_state").parentNode.style.display = "none";
		document.getElementById("form_calculate").parentNode.style.display = "none";
		document.getElementById("contacts_setting").parentNode.style.display = "none";
		document.getElementById("sp_setting").parentNode.style.display = "none";
		//document.getElementById("data_management").parentNode.style.display = "none";
		document.getElementById("user_management").parentNode.style.display = "none";


		$('#AccountingVouchersInput').css("display", 'none');
		$('#AccountingVouchersReview').css("display", 'none');
		$('#Assets_Statistic').css("display", 'none');
		$('#Assets_DetailSearch').css("display", 'none');
		$('#Assets_SummarizeSearch').css('display', 'none');
		$('#Assets_ChangeSearch').css('display', 'none');
		$('#Assets_DepartmentDpreSearch').css('display', 'none');
		$('#TimeSet').css('display', 'none');
		$('#MonthlyStatement').css('display', 'none');
		$('#StructureDefinition').css('display', 'none');
		$('#OtherEnvironment').css('display', 'none');
		$('#CaptionOfAccount').css('display', 'none');
		$('#SpecialAccount').css('display', 'none');
		$('#AccountBookSet').css('display', 'none');
		$('#AccountBookOutput').css('display', 'none');
		$('#CreateReport').css('display', 'none');
		$('#OpenReport').css('display', 'none');
		$('#CopyReport').css("display", 'none');
		$('#DeleteReport').css("display", 'none');
		$('#ImportReport').css("display", 'none');
		$('#ExportReport').css("display", 'none');
		//$('#SiteProductionCost').css('display', 'none');
		//$('#SiteBasicOperatingExpenses').css('display', 'none');
		$('#Calculate').css('display', 'none');
		$('#Change').css('display', 'none');
		$('#Contacts_Category').css('display', 'none');
		$('#Contacts_Management').css('display', 'none');
		$('#Contacts_Initialization').css('display', 'none');
		$('#ContactsBalance_Search').css('display', 'none');
		$('#ContactsAccountPage_Search').css('display', 'none');
		$('#SpAccount_Category').css('display', 'none');
		$('#SpAccountObject_Def').css('display', 'none');
		$('#SpAccountObject_Connect').css('display', 'none');
		$('#SpAccount_Initialization').css('display', 'none');
		$('#SpAccountBalance_Search').css('display', 'none');
		$('#SpAccountBalance_Search2').css('display', 'none');
		$('#SpAccountPage_Search').css('display', 'none');
		$('#SpAccountPage_Search2').css('display', 'none');

		$('#BasicOperatingExpenses').css('display', 'none');
		$('#DepreciationAndDepletion').css('display', 'none');
		$('#LaborCost').css('display', 'none');
		$('#ManagementCost').css('display', 'none');
		$('#CostBudgetImplementationTable').css('display', 'none');
		$('#CompleteCost').css('display', 'none');
		$('#ProductionCost').css('display', 'none');
		$('#OverallProductionCostStructure').css('display', 'none');
		$('#OverallProductionCostChart').css('display', 'none');
		$('#ContactsAccountPage_Search').css('display', 'none');
		$('#PerOverallProductionCostChart').css('display', 'none');

		$('#SiteProductionCost').css('display', 'none');
		$('#SiteBasicOperatingExpenses').css('display', 'none');
		$('#SiteProductionCostStructure').css('display', 'none');
		$('#SiteProductionCostChart').css('display', 'none');

		$('#ProProductionCost').css('display', 'none');
		$('#ProBasicOperatingExpenses').css('display', 'none');
		$('#ProProductionCostStructure').css('display', 'none');
		$('#ProProductionCostChart').css('display', 'none');

		$('#QuotaSet').css('display', 'none');
		$('#ThemeSet').css('display', 'none');
		$('#ElseSet').css('display', 'none');
		$('#AnnualBudget').css('display', 'none');
		$('#DepartmentBudget').css('display', 'none');

		$('#west_costmanagement').css('display', 'none');

		//其他设置
		//$('#DataBak').css('display', 'none');
		//$('#HistoryDataBak').css('display', 'none');
		$('#UserManagement').css('display', 'none');





		var functionAuthority = '';
		if(userNo !=null){
			//admin用户只有权限分配的功能
			if(userNo=="0000"){

				$('#DataBak').css('display', 'block');
				$('#HistoryDataBak').css('display', 'block');
				$('#UserManagement').css('display', 'block');
				document.getElementById("data_management").parentNode.style.display = "block";
				document.getElementById("user_management").parentNode.style.display = "block";

			}else {
				$.ajax({
					type: "post",
					url:getRealPath()+'/UserManagement/getAuthority/'+userNo,
					async: false,//false为异步传输，异步传输才能传全局变量
					// error: function () {
					// 	alert("权限获取错误");
					// },
					success:function (data) {

						// $('#DataBak').css('display', 'none');
						// $('#HistoryDataBak').css('display', 'none');
						// $('#UserManagement').css('display', 'none');
						// document.getElementById("data_management").parentNode.style.display = "none";
						// document.getElementById("user_management").parentNode.style.display = "none";

						//$('#UserManagement').css('display', 'none');

						document.getElementById('data_management').parentNode.style.display="none";
						$('#other_module').css("display",'none');

						functionAuthority=data.split(',');
						for (var i=0;i<functionAuthority.length;i++){
							var functionNo= functionAuthority[i];
							switch(functionNo){

								case "010101":
									document.getElementById('voucher_deal').parentNode.style.display="block";
									$('#AccountingVouchersInput').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010102":
									document.getElementById('voucher_deal').parentNode.style.display="block";
									$('#AccountingVouchersReview').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010201":
									document.getElementById('statistical_query').parentNode.style.display="block";
									$('#Assets_Statistic').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010202":
									document.getElementById('statistical_query').parentNode.style.display="block";
									$('#Assets_DetailSearch').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010203":
									document.getElementById('statistical_query').parentNode.style.display="block";
									$('#Assets_SummarizeSearch').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010204":
									document.getElementById('statistical_query').parentNode.style.display="block";
									$('#Assets_ChangeSearch').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010205":
									document.getElementById('statistical_query').parentNode.style.display="block";
									$('#Assets_DepartmentDpreSearch').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010301":
									document.getElementById('monthly_carrydown').parentNode.style.display="block";
									$('#TimeSet').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010302":
									document.getElementById('monthly_carrydown').parentNode.style.display="block";
									$('#MonthlyStatement').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010405":
									document.getElementById('accounting_initiate').parentNode.style.display="block";
									$('#StructureDefinition').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010401":
									document.getElementById('accounting_initiate').parentNode.style.display="block";
									$('#OtherEnvironment').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010402":
									document.getElementById('accounting_initiate').parentNode.style.display="block";
									$('#CaptionOfAccount').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010403":
									document.getElementById('accounting_initiate').parentNode.style.display="block";
									$('#SpecialAccount').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									//1
									break;
								case "010404":
									document.getElementById('accounting_initiate').parentNode.style.display="block";
									$('#AccountBookSet').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "010406":
									document.getElementById('accounting_initiate').parentNode.style.display="block";
									$('#AccountBookOutput').css("display",'block');
									$('#accounting_module').css("display",'block');
									display("FinanceProcess");
									break;
								case "020101":
									document.getElementById('form_management').parentNode.style.display="block";
									$('#CreateReport').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020102":
									document.getElementById('form_management').parentNode.style.display="block";
									$('#OpenReport').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020103":
									document.getElementById('form_management').parentNode.style.display="block";
									$('#CopyReport').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020104":
									document.getElementById('form_management').parentNode.style.display="block";
									$('#DeleteReport').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020105":
									document.getElementById('form_management').parentNode.style.display="block";
									$('#ImportReport').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020106":
									document.getElementById('form_management').parentNode.style.display="block";
									$('#ExportReport').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020201":
									document.getElementById('form_state').parentNode.style.display="block";
									$('#SiteProductionCost').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020202":
									document.getElementById('form_state').parentNode.style.display="block";
									$('#SiteBasicOperatingExpenses').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020301":
									document.getElementById('form_calculate').parentNode.style.display="block";
									$('#Calculate').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "020302":
									document.getElementById('form_calculate').parentNode.style.display="block";
									$('#Change').css("display",'block');
									$('#report_module').css("display",'block');
									display("ReportManagement");
									break;
								case "010501":
									document.getElementById('contacts_setting').parentNode.style.display="block";
									$('#Contacts_Category').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010502":
									document.getElementById('contacts_setting').parentNode.style.display="block";
									$('#Contacts_Management').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010503":
									document.getElementById('contacts_setting').parentNode.style.display="block";
									$('#Contacts_Initialization').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010504":
									document.getElementById('contacts_setting').parentNode.style.display="block";
									$('#ContactsBalance_Search').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010505":
									document.getElementById('contacts_setting').parentNode.style.display="block";
									$('#ContactsAccountPage_Search').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010601":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccount_Category').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010602":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccountObject_Def').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010603":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccountObject_Connect').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010604":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccount_Initialization').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010605":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccountBalance_Search').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010607":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccountBalance_Search2').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010606":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccountPage_Search').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								case "010608":
									document.getElementById('sp_setting').parentNode.style.display="block";
									$('#SpAccountPage_Search2').css("display",'block');
									$('#auxiliary_module').css("display",'block');
									display("AssistedManagement");
									break;
								default:



									break;

							}
						}
					}
				});



			}
		}
	}else {
		window.location.href = "../../Login.html";
		window.open("../../Login.html", '_self');
	}

})

function display(value) {
	//var wests = [west_accounting, west_auxiliary, west_report, west_other];
	var wests = new Array(5);
	wests[0] = document.getElementById('west_accounting');
	wests[1] = document.getElementById('west_auxiliary');
	wests[2] = document.getElementById('west_report');
	wests[3] = document.getElementById('west_other');
	wests[4] = document.getElementById('west_costmanagement');
	if(value == 'FinanceProcess') {
		displayNone(wests);
		west_accounting.style.display = 'block';
	} else if(value == 'AssistedManagement') {
		displayNone(wests);
		west_auxiliary.style.display = 'block';
	} else if(value == 'ReportManagement') {
		displayNone(wests);
		west_report.style.display = 'block';
	} else if(value == 'OtherSettings') {
		displayNone(wests);
		west_other.style.display = 'block';
	}else if(value == 'CostManagement') {
		displayNone(wests);
		west_costmanagement.style.display = 'block';
	}

}

function displayNone(values) {
	for(var i = 0; i < values.length; i++) {
		values[i].style.display = 'none';
	}
}

function addTab(id, title, url) {
	if($(id).tabs('exists', title)) {
		$(id).tabs('select', title);
	} else {
		var content = '<iframe  frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
		$(id).tabs('add', {
			title: title,
			content: content,
			closable: true
		});
	}
}

function Exit() {
	document.cookie="status=failse;path=/";
	window.location.href = "../../Login.html";
	window.open("../../Login.html", '_self');
}

//获取url后的某一个query的值
function getQueryString( name ) {
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
	return "请登录";
}

//获取当前时间
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}


function login(){
	$('#dlg').dialog('close'),
		$('#verify').dialog('open').dialog('setTitle','验证身份信息');
	$('#userNo_verify').attr("disabled", "disabled"); //系统用户编号是主键，设为不可修改
	$('#userNo_verify').val(userNo);
}

//验证身份
function verifyInfo(){
	$('#showInfo').css('display','none');
	var userPass = document.getElementById("userPass_verify").value;
	var userNo = document.getElementById("userNo_verify").value;
	var result = " ";
	if(userPass==''){
		document.getElementById("showInfo").style.display = "block";
		document.getElementById("showInfo").innerText = "密码不能为空!";
	}else {
		if($('fm_verify').form("validate")){
			$.ajax({
				type:"post",
				url:  getRealPath()+'/UserManagement/login/'+userNo+"/"+userPass,
				contentType: 'application/json; charset=utf-8',
				dataType: "json",
				async: false,
				error: function () {
					alert("验证身份出错!");
				},
				success: function (data) {
					if(data.code==100) {
						result = data.extend.userName;
					}else {
						result = " ";
					}
				}
			});
			if(result != " "){
				$('#userPass_verify').val('');//清除输入的正确密码
				$('#verify').dialog('close');
				$('#dlg').dialog('open').dialog('setTitle', '个人设置');
				$('#userNo').attr("disabled", "disabled"); //系统用户编号是主键，设为不可修改

				//给输入框赋初值
				$('#userNo').val(userNo);
				$('#userName').val(userName);

			}else if(result == " "){
				$('#showInfo').css('display','block');
				document.getElementById("showInfo").innerText = "密码错误!";
			}

		}else {
			return $('fm_verify').form("validate");
		}
	}

}
//保存信息
function saveData() {
	var userNo = document.getElementById("userNo").value;
	var userName = document.getElementById("userName").value;
	var userPass = document.getElementById("userPass").value;
	var userPass1 = document.getElementById("userPass1").value;
	if ($("#fm").form("validate")) {
		$.ajax({
			type: "post",
			url:  getRealPath()+'/UserManagement/updateMsg',
			contentType: 'application/json; charset=utf-8',
			dataType: "json",
			data: '{ "userNo":"'+userNo+'", "userName": "'+userName+'","userPass":"'+userPass+'"}',
			async: false, //false为异步传输，异步传输才能传全局变量
			success: function(data) {
				if(data == "1") {
					$('#dlg').dialog('close');
					$.messager.alert("提示", "恭喜您，信息修改成功", "info");
					//InitGrid();
				} else {
					$.messager.alert("提示", "修改失败，请重新操作！", "info");
					return;
				}
			},
			error: function() {
				alert("error");
			}
		});
	} else {
		return $('#fm').form('validate');
	}

}


//关闭tab
function tabsClose(id){
	var tab=$(id).tabs('getSelected');//获取当前选中tabs
	var index = $(id).tabs('getTabIndex',tab);//获取当前选中tabs的index
	$(id).tabs('close',index);//关闭对应index的tabs
}

// function getRealPath(){
//
// 	var localObj = window.location;
// 	var contextPath = localObj.pathname.split("/")[1];
// 	var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
// 	return basePath ;
// }

function getUserInfo(userNo){
	$.ajax({
		type: "post",   //post提交方式默认是get
		url: getRealPath()+'/UserManagement/getUserInfo/'+userNo,
		async: false,

		success: function (data) {
			userName = data.extend.user.userName;
			department = data.extend.user.department;
		}
	});
}