$(document).ready(function () {
    //初始化表格
    InitGrid();
})

//初始化datagrid
function InitGrid(queryData) {
    //加载datagrid
    $('#grid').datagrid({   //定位到Table标签，Table标签的ID是grid
        url: getRealPath()+'/UserManagement/findAcc' , //指向后台的Action来获取当前菜单的信息的Json格式的数据
        height: function () { return document.body.clientHeight * 0.9 },
        width: function () { return document.body.clientWidth * 0.9 },
        fit: false,
        nowrap: true,
        autoRowHeight: false,
        striped: true,
        singleSelect: true,//只允许选中一行
        collapsible: true,
        rownumbers: true,
        sortName: 'id',    //根据某个字段给easyUI排序
        sortOrder: 'asc',
        remoteSort: false,
        idField: 'id',
        //data: [{id: "ts1", name: "测试套账", flag: 1}],
        columns: [[
             { title: '账套编号', field: 'id', width: '15%', align: 'center' },
             { title: '账套名称', field: 'name', width: '15%', align: 'center' },
             { title: '使用中', field: 'flag', width: '15%', align: 'center', formatter:
                function (value, row) {
                    switch(value){
                        case 1: return '是';
                        case 0: return '否';
                    }
                }
             },
             // {
             //     title: '操作', field: 'opt', width: '10%', align: 'center', formatter:
             //        function (value, row) {
             //            var id = row.id;
             //            var str = "";
             //            str += '<span><a href="#" class="easyui-linkbutton" style="text-decoration: none;color:blue;" onclick="SwitchDialog(\'' + id + '\')">切换套账</a></span>';
             //            return str;
             //            //return "<a onclick=\"show('"+row.userNo+"')\" > 查看 </a>";
             //        }
             // }
        ]],
        toolbar: '#tb'
    })
}

//添加
function ShowAddDialog() {
    $('#fm').form('clear');//清空内容
    $('#accNo').textbox('enable');//添加时设为可修改
    $('#dlg').dialog('open').dialog('setTitle', '添加账套');//打开对话框
    document.getElementById("test").value = "add";
}


//修改 
function ShowEditDialog() {

   var row = $('#grid').datagrid('getSelected');

   if (row == null) {
       $.messager.alert("提示", "请选择要修改的行！", "info");
   }
   else {

       $('#accNo').textbox('disable');
       
       //获取要修改的字段
       row = $('#grid').datagrid('getSelected');
       $('#accNo').textbox('setValue',row.id);
       $('#accName').textbox('setValue',row.name);

       $('#dlg').dialog('open').dialog('setTitle', '修改账套信息');
       document.getElementById("test").value = "modify";
       $('#fm').form('load', row);
   }
}


function SwitchDialog() {
    var id = $('#grid').datagrid('getSelected').id;
    $.ajax({
        type: "post",
        url: getRealPath()+'/UserManagement/switchAcc/'+id,
        async: false,//false为异步传输，异步传输才能传全局变量
        success: function (data) {
            $('#grid').datagrid('reload');
            $.messager.show({
                title: '提示',
                msg: "切换成功"
            });
        },
        error: function () {
            alert("error");
        }
    });
}


//删除信息
// function deleteData() {
//    //var adminID = document.getElementById("AdminID").value = "0";
//    var test = document.getElementById("test").value = "delete";
//    var row = $('#grid').datagrid('getSelected');
//    var num = 0;
//    if (row.length < 1) {
//        $.messager.alert("提示", "请选择要删除的用户！", "info");
//    } else {
//        $.messager.confirm("删除确认", "您确认删除选定的记录吗？", function (flag) {
//            if (flag) {
//                    var userNo = row.userNo;
//                    $.ajax({
//                        type: "post",
//                        url: getRealPath()+'/UserManagement/delInfo/'+userNo,
//                        async: false,//false为异步传输，异步传输才能传全局变量
//                        success: function (result) {
//                            if (result == true) {
//                                num++;
//                            }
//                        },
//                        error: function () {
//                            alert("error");
//                        }
//                    });
//                if (num == 1) {
//                    $('#grid').datagrid('clearSelections'); //清空选中的行
//                    InitGrid();
//                    $.messager.alert("提示", "恭喜您，成功删除" + num + "条信息！", "info");
//                } else {
//                    $.messager.alert("提示", "删除失败，请重新操作！", "info");
//                    InitGrid();
//                    return;
//                }
//            }
//        });
//    }
// }

//保存用户信息
function saveData() {
   //var adminID = document.getElementById("AdminID").value = "0";
    var accNo = document.getElementById("accNo").value;
    var accName = document.getElementById("accName").value;
    var test = document.getElementById("test").value;

   if (test == "add") {
       if($("#fm").form("validate")){
           $.ajax({
               type: 'post',
               url: getRealPath()+'/UserManagement/addAcc',
               dataType: "json",
               contentType: 'application/json; charset=utf-8',
               data: '{ "id":"'+accNo+'", "name": "'+accName+'", "flag": 0 "}',
               async: false,//false为异步传输，异步传输才能传全局变量
               success: function (result) {
                   if (result == true) {
                       $('#dlg').dialog('close');
                       $.messager.alert("提示", "恭喜您，信息添加成功!", "info");
                       $('#grid').datagrid('clearSelections'); //清空选中的行
                       //InitGrid();
                       $('#grid').datagrid('reload');
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
               url: getRealPath()+'/UserManagement/updateAcc',
               dataType: "json",
               contentType: 'application/json; charset=utf-8',
               data: '{ "id":"'+accNo+'", "name": "'+accName+'", "flag": 0 }',
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
// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//     return basePath ;
// }
