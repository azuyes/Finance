$(document).ready(function () {
    //初始化表格
    InitGrid();
})

//初始化datagrid
function InitGrid(queryData) {
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        url: getRealPath()+'/UserManagement/findUser' , //指向后台的Action来获取当前菜单的信息的Json格式的数据
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        fit: false,
        nowrap: true,
        autoRowHeight: false,
        striped: true,
        singleSelect: true,//只允许选中一行
        collapsible: true,
        pagination: false,
        pageSize: 100,
        pageList: [50, 100, 200],
        rownumbers: true,
        sortName: 'userNo',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'userNo',
        //queryParams: queryData,  //异步查询的参数
        columns: [[
            { field: 'ckeck', checkbox: true ,value:'userNo'},   //选择
             { title: '用户编号', field: 'userNo', width: 40 },
             { title: '用户名称', field: 'userName', width: 40 },
             { title: '所属部门', field: 'department', width: 40 },
             { title: '备注信息', field: 'userNote', width: 40 },

             {
                 title: '操作', field: 'opt', width: 40, align: 'center', formatter:
                    function (rowIndex) {
                        var str = "";
                        str += '<span><a href="#" class="easyui-linkbutton" style="text-decoration: none;color:blue;" onclick="ShowRoleDialog(' + rowIndex + ')">权限设置</a></span>';
                        return str;
                    }
             }
        ]],
        // data: [{ "UserID": '01', "UserName": '刘宇豪', "Department": '信息学部', "Note": '无' },
        //         { "UserID": '02', "UserName": '刘哲', "Department": '信息学部', "Note": '无'}],

        toolbar: '#tb',
        onDblClickRow: function (rowIndex, rowData) {//获取选中的行
            $('#grid').datagrid('uncheckAll');
            $('#grid').datagrid('checkRow', rowIndex);
        }
    })
}

//添加
function ShowAddDialog() {
    $('#fm').form('clear');//清空内容
    $('#userNo').attr("disabled", false);//添加时用户编号设为可修改
    //$('#department').combogrid('grid').datagrid('reload');//刷新所属部门下拉框
    $('#dlg').dialog('open').dialog('setTitle', '添加用户');//打开对话框
    document.getElementById("test").value = "add";
}
//关闭弹窗事件
$("#dlg").dialog({
	onClose:function(){
		$(this).dialog('destroy');
		window.parent.tabsClose();
	}	
});

//修改 
function ShowEditDialog() {

   var row = $('#grid').datagrid('getSelections');

   if (row.length < 1) {
       $.messager.alert("提示", "请选择要修改的行！", "info");
   }
   else if (row.length > 1) {
       $.messager.alert("提示", "请只选择一行来修改！", "info");
   }
   else if (row.length = 1) {

       //获取要修改的字段
       row = $('#grid').datagrid('getSelected');
       $('#userNo').val(row.userNo);
       $('#userName').val(row.userName);
       $('#department').val(row.department);
       $('#userNote').val(row.userNote);

       $('#userNo').attr("disabled", true);//添加时用户编号设为不可修改
       //$('#Note').val(row.Note);
       //$('#AffiliatedDepartment').combogrid('grid').datagrid('reload');//刷新所属部门下拉框
       //$('#AffiliatedDepartment').combogrid('setValue', row.AffiliatedDepartment);
       //$('#AffiliatedDepartment').val(row.AffiliatedDepartment);

       //$('#Note').combobox('setValue', row.Note);
       //$('#CreationDate').datebox('setValue', row.CreationDate);//初始化日期选择框

       //赋值后验证一遍
       // $('#userNo').textbox('validate');
       // $('#userName').textbox('validate');
       // $('#department').textbox('validate');
       // $('#userNote').textbox('validate');

       $('#dlg').dialog('open').dialog('setTitle', '修改用户信息');
       document.getElementById("test").value = "modify";
       //$('#fm').form('load', row);
   }
}
//打开权限设置对话框
function ShowRoleDialog(rowIndex) {
    var data = [{
        "id": "000000",
        "text": "财务功能管理",
        "children": [{
            "id": "010000",
            "text": "账务处理",
            "children": [{
                "id": "010100",
                "text": "凭证处理",
                "children": [{
                    "id": "010101",
                    "text": "记账凭证输入"
                }, {
                    "id": "010102",
                    "text": "记账凭证复核"
                }, {
                    "id": "010103",
                    "text": "记账凭证总汇"
                }]
            }, {
                "id": "010200",
                "text": "统计查询",
                "children": [{
                    "id": "010201",
                    "text": "科目余额查询"
                }, {
                    "id": "010202",
                    "text": "明细账查询"
                }, {
                    "id": "010203",
                    "text": "任意多栏式账查询"
                }, {
                    "id": "010204",
                    "text": "日记式账查询"
                }, {
                    "id": "010205",
                    "text": "凭证流水查询"
                }]
            }, {
                "id": "010300",
                "text": "月结处理",
                "children": [{
                    "id": "010301",
                    "text": "财务日期维护"
                }, {
                    "id": "010302",
                    "text": "月末结账"
                }]
            }, {
                "id": "010400",
                "text": "账务初始化",
                "children": [{
                    "id": "010401",
                    "text": "其他环境设置"
                }, {
                    "id": "010402",
                    "text": "会计科目设置"
                }, {
                    "id": "010403",
                    "text": "特殊科目设置"
                }, {
                    "id": "010404",
                    "text": "账册字典维护"
                }]
            }, {
                "id": "010500",
                "text": "往来功能",
                "children": [{
                    "id": "010501",
                    "text": "往来类别定义",
                }, {
                    "id": "010502",
                    "text": "往来单位管理"
                }, {
                    "id": "010503",
                    "text": "往来初始化"
                }, {
                    "id": "010504",
                    "text": "往来余额查询"
                }, {
                    "id": "010505",
                    "text": "往来账页查询"
                }]
            }, {
                "id": "010600",
                "text": "专项核算功能",
                "children": [{
                    "id": "010601",
                    "text": "专项核算类别定义"
                }, {
                    "id": "010602",
                    "text": "核算对象定义"
                }, {
                    "id": "010603",
                    "text": "核算对象关联科目"
                }, {
                    "id": "010604",
                    "text": "专项核算初始化"
                }, {
                    "id": "010605",
                    "text": "专项核算余额查询"
                }, {
                    "id": "010606",
                    "text": "专项账页查询"
                }]
            }],

        }, {
            "id": "020000",
            "text": "报表管理",
            "children": [{
                "id": "020100",
                "text": "报表文件管理",
                "children": [{
                    "id": "020101",
                    "text": "新建报表"
                }, {
                    "id": "020102",
                    "text": "打开报表"
                }, {
                    "id": "020103",
                    "text": "删除报表"
                }, {
                    "id": "020104",
                    "text": "转入报表"
                }, {
                    "id": "020105",
                    "text": "转出报表"
                }, {
                    "id": "020106",
                    "text": "导入EXCEL"
                }, {
                    "id": "020107",
                    "text": "导出EXCEL"
                }]
            }, {
                "id": "020200",
                "text": "报表状态切换",
                "children": [{
                    "id": "020201",
                    "text": "公式状态"
                }, {
                    "id": "020202",
                    "text": "数据状态"
                }]
            }, {
                "id": "020300",
                "text": "其他功能",
                "children": [{
                    "id": "020301",
                    "text": "计算报表"
                }, {
                    "id": "020302",
                    "text": "报表结转"
                }]
            }]
        }]
    }];
    $("#tt").tree({
        data: data,
        checkbox: true,
        cascadeCheck: false,
        //onCheck: function (node, checked) {
        //    if (checked) {
        //        var parentNode = $("#tt").tree('getParent', node.target);
        //        if (parentNode != null) {
        //            $("#tt").tree('check', parentNode.target);
        //        }
        //    } else {
        //        var childNode = $("#tt").tree('getChildren', node.target);
        //        if (childNode.length > 0) {
        //            for (var i = 0; i < childNode.length; i++) {
        //                $("#tt").tree('uncheck', childNode[i].target);
        //            }
        //        }
        //    }
        //}
    });

    //从后台获取已有的权限信息
    //var row = $('#grid').datagrid('getData').rows[rowIndex];
    //var RoleID = row.RoleID;
    //var FunctionAuthority = '';
    //$.ajax({
    //    type: "post",
    //    url: "UserRole_SelectAuthority.ashx",
    //    data: { "RoleID": RoleID, "FunctionAuthority": '0' },
    //    async: false,//false为异步传输，异步传输才能传全局变量
    //    success: function (data) {
    //        FunctionAuthority = data;
    //    },
    //    error: function () {
    //        alert("error");
    //    }
    //});

    //tree赋初值，即将数据库中已有的权限选中
    //if (FunctionAuthority !== "") {
    //    var TreeID = new Array();
    //    TreeID = FunctionAuthority.split(",");
    //    for (var i = 0; i < TreeID.length; i++) {
    //        var node = $('#tt').tree('find', TreeID[i]);
    //        $('#tt').tree('check', node.target);
    //    }
    //}

    $('#dlg-User').dialog('open').dialog('setTitle', '设置用户权限');
}

//权限设置对话框保存
//function saveRoleData() {
//    var row = $('#grid').datagrid('getSelected');
//    var RoleID = row.RoleID;
//    var adminID = document.getElementById("AdminID").value = "0";
//    var nodes = $('#tt').tree('getChecked');
//    var FunctionAuthority = '';
//    for (var i = 0; i < nodes.length; i++) {
//        if (FunctionAuthority != '') FunctionAuthority += ',';
//        FunctionAuthority += nodes[i].id;
//    }

//    $.ajax({
//        type: "post",
//        url: "UserRole_UpdateAuthority.ashx",
//        data: { "RoleID": RoleID, "AdminID": adminID, "FunctionAuthority": FunctionAuthority },
//        async: false,//false为异步传输，异步传输才能传全局变量
//        success: function (data) {
//            if (data == "1") {
//                $('#dlg-Role').dialog('close');
//                $.messager.alert("提示", "恭喜您，信息修改成功", "info");
//            }
//            else {
//                $.messager.alert("提示", "修改失败，请重新操作！", "info");
//                return;
//            }
//        },
//        error: function () {
//            alert("error");
//        }
//    });
//}


//删除信息
function deleteData() {
   //var adminID = document.getElementById("AdminID").value = "0";
   var test = document.getElementById("test").value = "delete";
   var row = $('#grid').datagrid('getSelected');
   var num = 0;
   if (row.length < 1) {
       $.messager.alert("提示", "请选择要删除的用户！", "info");
   } else {
       $.messager.confirm("删除确认", "您确认删除选定的记录吗？", function (flag) {
           if (flag) {
                   var userNo = row.userNo;
               alert(userNo);
                   $.ajax({
                       type: "post",
                       url: getRealPath()+'/UserManagement/delInfo/'+userNo,
                       // contentType: 'application/json; charset=utf-8',
                       // data: '{ "userNo":"'+userNo+'"}',
                       async: false,//false为异步传输，异步传输才能传全局变量
                       success: function (result) {
                           if (result == true) {
                               num++;
                           }
                       },
                       error: function () {
                           alert("error");
                       }
                   });
               if (num == 1) {
                   $('#grid').datagrid('clearSelections'); //清空选中的行
                   InitGrid();
                   $.messager.alert("提示", "恭喜您，成功删除" + num + "条信息！", "info");
               } else {
                   $.messager.alert("提示", "删除失败，请重新操作！", "info");
                   InitGrid();
                   return;
               }
           }
       });
   }
}

//添加用户信息
function saveData() {
   //var adminID = document.getElementById("AdminID").value = "0";
    var userNo = document.getElementById("userNo").value;
    var userName = document.getElementById("userName").value;
    var department = document.getElementById("department").value;
   //var department = $("#AffiliatedDepartment").combogrid("getValue");
    var userNote = document.getElementById("userNote").value;
    var test = document.getElementById("test").value;

   if (test == "add") {
       if($("#fm").form("validate")){
           $.ajax({
               type: 'post',
               url: getRealPath()+'/UserManagement/addInfo',
               dataType: "json",
               contentType: 'application/json; charset=utf-8',
               data: '{ "userNo":"'+userNo+'", "userName": "'+userName+'", "department": "'+department+'","userNote":"'+userNote+'"}',
               async: false,//false为异步传输，异步传输才能传全局变量
               success: function (result) {
                   if (result == true) {
                       $('#dlg').dialog('close');
                       $.messager.alert("提示", "恭喜您，信息添加成功!", "info");
                       //alert('恭喜您，信息添加成功！')
                       $('#grid').datagrid('clearSelections'); //清空选中的行
                       InitGrid();
                       //$('#grid').datagrid('reload');
                       //$('#fm').form('submit');
                   }
                   else {
                       $.messager.alert("提示", "添加失败，用户编号重复，请重新操作！", "info");
                       return;
                   }
               }
           });
       }else {
           return $('#fm').form('validate');
       }
   } else {
       if ($("#fm").form("validate")) {
           $.ajax({
               type: 'post',
               url: getRealPath()+'/UserManagement/updateInfo',
               dataType: "json",
               contentType: 'application/json; charset=utf-8',
               data: '{ "userNo":"'+userNo+'", "userName": "'+userName+'", "department": "'+department+'","userNote":"'+userNote+'"}',
               async: false,//false为异步传输，异步传输才能传全局变量
               success: function (result) {
                   if (result == "1") {
                       $('#dlg').dialog('close');
                       $('#grid').datagrid('clearSelections'); //清空选中的行  
                       $.messager.alert("提示", "恭喜您，信息修改成功", "info");
                       InitGrid();
                   }
                   else {
                       $.messager.alert("提示", "修改失败，请重新操作！", "info");
                       return;
                   }
               },
               error: function () {
                   alert("error!");
               }
           });
       } else {

           return $('#fm').form('validate');
       }
   }
}


//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath ;
}