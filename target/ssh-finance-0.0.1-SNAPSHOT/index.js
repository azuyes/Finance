$(function () {

//TODO:获取用户id和当前财务日期
//		var UserName;
//		var UserID;
//		//TODO:连接数据库获取用户名
//		/* UserName = '<%=Request.QueryString("UserName")%>';
//		UserID = '<%=Request.QueryString("UserID")%>'; */
		document.getElementById('User').innerHTML = "用户：" + '刘宇豪';
		document.getElementById('time1').innerHTML = "当前财务日期：" + '2018/3/29';//GetFinacialDate();
		document.getElementById('Unit').innerHTML = "单位：" + '北京工业大学';
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


//		//TODO:连接数据库获取用户ID
//		/* var userNo = <%=Request.QueryString("UserID")%>';  //分离key与Value */
//		var functionAuthority = {}; //记录用户的权限
//		//admin用户
//		if(userNo != "") {
//			if(userNo == "000000") { //admin用户禁用固定资产管理和成本管理                             
//				$('#auxiliary_module').css('display', 'block'); //去掉a标签中的onclick事件
//	
//			}
//			//普通用户
//			else {
//				$.ajax({
//						type: "post", //post提交方式默认是get
//						url: "index/Index_UserHandler.ashx",
//						data: {
//							"UserNo": userNo
//						},
//						async: false,
//						error: function() {
//							alert("error");
//						},
//						success: function(data) {
//							functionAuthority = data.split(',');
//							/* for (var i = 0; i < functionAuthority.length; i++) {
//	                                var functionNo = functionAuthority[i];
//	                            }
//	                        } */
//						}
//					);
//				}
//			}
//		}

})
	


function display(value) {
	//var wests = [west_accounting, west_auxiliary, west_report, west_other];
	var wests = new Array(4);
	wests[0] = document.getElementById('west_accounting');
	wests[1] = document.getElementById('west_auxiliary');
	wests[2] = document.getElementById('west_report');
	wests[3] = document.getElementById('west_other');
	if(value == 'Accounting') {
		displayNone(wests);
		west_accounting.style.display = 'block';
	} else if(value == 'Auxiliary') {
		displayNone(wests);
		west_auxiliary.style.display = 'block';
	} else if(value == 'ReportManagement') {
		displayNone(wests);
		west_report.style.display = 'block';
	} else if(value == 'OtherManagement') {
		displayNone(wests);
		west_other.style.display = 'block';
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

//$(function() {
//
//}); <
///script> <
//script >
//	$(function() {
//		$("#west_accounting li").click(
//			function() {
//				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
//				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
//				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
//			});
//	}); <
///script> <
//script >
//	$(function() {
//		$("#west_other li").click(
//			function() {
//				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
//				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
//				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
//			});
//	}); <
///script> <
//script >
//	$(function() {
//		$("#west_auxiliary li").click(
//			function() {
//				$(this).css("background", "#f2f2f2").siblings().css("background", "#ffffff");
//				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
//				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
//			});
//	}); <
///script>
//
//<
//script >
//	$(function() {
//		$('#north_menu a').click(
//			function() {
//				$(this).css("color", "#2b7dbc").siblings().css("color", "#585858");
//				$(this).css("font-weight", "600").siblings().css("font-weight", "500");
//			});
//	});

//<!-- 菜单项动态设置 -->
//
//	$(function() {
//			//TODO:连接数据库获取用户ID
//			/* var userNo = <%=Request.QueryString("UserID")%>';  //分离key与Value */
//			var functionAuthority = {}; //记录用户的权限
//			//admin用户
//			if(userNo != "") {
//				if(userNo == "000000") { //admin用户禁用固定资产管理和成本管理                             
//					$('#auxiliary_module').css('display', 'block'); //去掉a标签中的onclick事件
//
//				}
//				//普通用户
//				else {
//					$.ajax({
//							type: "post", //post提交方式默认是get
//							url: "index/Index_UserHandler.ashx",
//							data: {
//								"UserNo": userNo
//							},
//							async: false,
//							error: function() {
//								alert("error");
//							},
//							success: function(data) {
//								functionAuthority = data.split(',');
//								/* for (var i = 0; i < functionAuthority.length; i++) {
//                              var functionNo = functionAuthority[i];
//                          }
//                      } */
//							});
//					}
//				}
//			}) 

function Exit() {
	window.location.href = "/Login.html";
	window.open("/Login.html", '_self');
}

function GetFinacialDate() {
	var FinancialDate = "";
	$.ajax({
		type: "post", //post提交方式默认是get
		url: "Login/index.ashx",
		data: {
			"FinancialDate": ""
		},
		async: false,
		error: function() {
			alert("error");
		},
		success: function(data) {
			FinancialDate = data;
		}
	});
	return FinancialDate;
}

function InitGrid() {
	$('#dlg').dialog('open').dialog('setTitle', '个人设置');
	$('#SystemUserID').attr("disabled", "disabled"); //系统用户编号是主键，设为不可修改  

	//给输入框赋初值
	$('#SystemUserID').val(UserID);
	$('#SystemUserName').val(UserName);

	//赋值后验证一次
	$('#SystemUserID').validatebox('validate');
	$('#SystemUserName').validatebox('validate');

	//$('#Password').val(Password);
}

//保存信息  
function saveData() {
	var SystemUserID = document.getElementById("SystemUserID").value;
	var SystemUserName = document.getElementById("SystemUserName").value;
	var Password = document.getElementById("Password").value;
	if($("#fm").form("validate")) {
		$.ajax({
			type: "post",
			url: "SystemManagement/PersonalSet/PersonalSet_Update.ashx",
			data: {
				"SystemUserID": SystemUserID,
				"SystemUserName": SystemUserName,
				"Password": Password
			},
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
		return $(this).form('validate');
	}

}