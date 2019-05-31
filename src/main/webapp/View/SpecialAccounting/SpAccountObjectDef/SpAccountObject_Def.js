var catNo = "";
var catName = "";
var spLevel = 1;
var spNo = null;
var upperName;
var showupperName;

$(function () {

    obj = {
        editRow : undefined,
        // spNoUnique : undefined,
        //添加事件
        add : function () {

            if (this.editRow == undefined) {
                var table = $('#initTable');
                var rows = table.datagrid('getRows').length;
                //将新插入的那一行开启编辑状态
                ///table.datagrid('beginEdit', rows-1);
                //添加一行
                table.datagrid('insertRow', {
                    index : rows,
                    row : {
                    },
                });
                //$('#initTable').datagrid('addRow');
                //将第一行设置为可编辑状态
                $('#initTable').datagrid('beginEdit', rows);

                this.editRow = rows;
            }else{
                $.messager.alert('提示', '请先保存，再进行添加！', 'info');
            }
        },

        //保存事件
        save : function () {
            // if (this.spNoUnique != undefined){
                //将第一行设置为结束编辑状态
                $('#initTable').datagrid('endEdit', this.editRow);
                // spNoUnique = undefined;
            // }

        },

        cancel : function(){
            this.editRow = undefined;
            $('#initTable').datagrid('rejectChanges');
        },

        //删除事件
        remove : function () {
            var row = $('#initTable').datagrid('getSelected');
            if (row) {//判断是否选中行
                $.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
                    if (flag) {
                        if(row.finLevel.toString() == "1"){
                            var ids = row.spNo;
                            $.ajax({
                                type : 'POST',
                                url:getRealPath()+'/SpAccountObjectDef/delSpAccountObjectDef/' + ids + '/' + spLevel+ '/' + catNo,
                                success : function (result) {
                                    if (result.code == 100) {
                                        var data = result.extend.lshszds;
                                        $('#initTable').datagrid('loadData', data);
                                        $.messager.alert('提示', '删除成功！', 'info');
                                    }else if(result.code == 101){
                                        $.messager.alert('警告', result.extend.errorInfo, 'error');
                                    }
                                },

                            });
                        }else{
                            $.messager.alert('提示', '当前类别不是末级，不能删除！', 'info');
                        }
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'info');
            }
        },
    };

    $('#box').dialog({
        width: 380,
        height: 190,
        title: '专项核算类别',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                itemNoSelected();


            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#box').dialog('close');
            }
        }],
    });

    $('#helpWin').window({
        width: 480,
        height: 500,
        title: '专项核算类别',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    $('#initTable').datagrid({
        //width: 100,
        //height: 100,
        fit: true,
        //url: 'content.json',
        rownumbers: true,
        fitColumns:true,
        striped: true,
        singleSelect: true,
        columns: [[
			{
			    field: 'spNo',
			    title: '编号',
			    align:'center',
			    width: '35%',
                editor : {
                    type : 'textbox',
                    options : {
                        required : true,
                        validType:'length[4,4]',
                        invalidMessage : '请输入4位数字编号',
                        missingMessage : '请输入编号',
                    },

                },
			},
			{
			    field: 'spName',
			    title: '核算对象',
			    align:'center',
			    width: '35%',
                editor : {
                    type : 'textbox',
                    options : {
                        required : true,
                        validType:'length[0,20]',
                        invalidMessage : '请输入1-30个字符',
                        missingMessage : '请输入核算名称',
                    },
                },
			},
			{
			    field: 'finLevel',
			    title: '明细否',
			    align:'center',
			    width: '30%',
                formatter: function (value,row,index) {
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
			},

        ]],
        toolbar: '#tb',
        onAfterEdit : function (rowIndex, rowData, changes) {  //编辑结束之后事件
            var id = rowData.spNo;
            switch (spLevel){
                case 1:
                    id = id+"00000000";break;
                case 2:
                    num = spNo.substring(0,4);
                    id = num+id+"0000";break;
                case 3:
                    num = spNo.substring(0,8);
                    id = num+id;break;
            }
            var data = '{"catNo":"' + catNo + '","spNo":"' + id + '","spName":"' + rowData.spName + '","spLevel":"' + spLevel + '"}';

            $.ajax({
                type : 'POST',
                url : getRealPath()+'/SpAccountObjectDef/saveSpAccountObjectDef',
                contentType: 'application/json',
                data: data,
                beforeSend : function () {
                    $('#initTable').datagrid('loading');
                },
                success : function (result) {
                    if (result.code == 100) {

                        var data = result.extend.lshszdList;

                        $('#initTable').datagrid('loaded');
                        $('#initTable').datagrid('loadData', data);
                        $.messager.show({
                            title: '提示',
                            msg: "添加成功",
                        });
                        obj.editRow = undefined;
                    }else{
                        $.messager.alert('警告', '类别编号重复，请重新增加！', 'warning');
                        $('#initTable').datagrid('loaded');
                        $('#initTable').datagrid('rejectChanges');
                        obj.editRow = undefined;
                        // spNoUnique = false;
                    }
                },
            });
        },

        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件
            if(spLevel != 3){ //判断是否为末级
                spLevel++;
                spNo = rowData.spNo;
                upperName = rowData.spName;
                if(spLevel == 2){
                    no = spNo.substring(0,4);
                    showupperName = upperName+"\\";
                }
                if(spLevel ==3){
                    no = spNo.substring(0,8);
                    showupperName = showupperName+upperName;
                }

                showSpAccount(no,spLevel);

            }else{
                $.messager.alert('提示', '当前已是末级', 'info');
            }
        }
    });

    $('#itemTable').datagrid({
        width:'100%',
        height:'100%',
        fit: true,
        fitColumns: false,
        singleSelect : true,
        url: getRealPath() + '/SpAccountCategory/getSpAccountCategory',
        columns: [[
			{
			    field: 'catNo',
			    title: '编号',
			    align:'center',
			    width: 120
			},
			{
			    field: 'catName',
			    title: '名称',
			    align:'center',
			    width: 184
			},
            {
                field: 'finLevel',
                title: '明细否',
                align:'center',
                width: 80,

            },
            {
                field: 'spLevel',
                title: '级数',
                align:'center',
                width: 80
            },

        ]],
        toolbar: '#tb_helpWin',
    });
})

//帮助窗口，选定科目时事件
function selectClick() {

    var row = $('#itemTable').datagrid("getSelected");
    catName = row.catName;
    $('#helpWin').window('close');
    $("#catNo_input").val(row.catNo);
}



//初始对话框，填好科目编号后，点击确定时的处理事件
function itemNoSelected (){
    catNo = $('#catNo_input').val();
    if(catNo == ""){
        $.messager.alert('警告操作！', '请输入核算类别编号！', 'warning');
    }else{
        $.ajax({
            type: 'POST',
            url: getRealPath() + '/SpAccountObjectDef/getSpAccountCategoryById/' + catNo ,
            contentType: 'application/json',
            success: function (result) {
                if (result.code == 100) {

                    var data = result.extend.lshszdQueryVo.lshsfl;  //获取科目字典信息，加载在页面最上面进行显示
                    catNo = data.catNo;
                    catName = data.catName;
                    var loadData = result.extend.lshszdQueryVo.lshszds;  //获取金额或者数量信息，用于在je或sl表进行显示

                    $('#helpWin').window('close');
                    $('#box').dialog('close');
                    document.getElementById("specialDef").style.visibility = "visible";

                    $("#catName_label").text(catName);
                      //设置输入框为禁用
                    $('#initTable').datagrid('loadData', loadData);
                    //judgeSelected('#initTable');
                    document.getElementById("initTable").style.visibility = "visible";
                } else {
                    $.messager.alert('警告操作！', '核算类别不存在！', 'warning');
                }
            }

        });
    }

}

// function judgeSelected(ele){
//     if($(ele).datagrid("getData").total>0){
//         $(ele).datagrid("selectRow", 0);
//     }
// }

//计算上级科目编号
function getCatNo(num,level){
    var length;
    var no;
    if(level != 1){
        length  = 4;
        no = num.substring(0,length);
    }else{
        no = 0;
    }
    return no;
}


function uperLevel_click() {
    if(spLevel==1){
        $.messager.alert('提示', '当前已为第一级！', 'info');
    }else{
        spLevel--;
        if(spLevel == 2){
            no = spNo.substring(0,4);
            showupperName = showupperName.split("\\",1) + "\\";

        }
        if(spLevel ==1){
            no = 0;
            showupperName = "";
        }

        showSpAccount(no,spLevel);
    }

}
//下级点击事件
function nextLevel_click() {
    var row = $('#initTable').datagrid('getSelected');
    if(row==null){  //判断是否选中行
        $.messager.alert('提示', '请选择一条记录！', 'info');
    }else if(spLevel == 3){
        $.messager.alert('提示', '当前已为末页！', 'info');
    }else{
        spLevel++;
        spNo = row.spNo;
        upperName = row.spName;
        if(spLevel == 2){
            no = spNo.substring(0,4);
            showupperName = upperName+"\\";
        }
        if(spLevel ==3){
            no = spNo.substring(0,8);
            showupperName = showupperName+upperName;
        }

        showSpAccount(no,spLevel);
    }

}


//显示当前级的科目
function showSpAccount(no, spLevel){

    $("#upperSpName_label").text(showupperName);
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/SpAccountObjectDef/querySpAccountByLevel/' + no + '/' + spLevel + '/' + catNo,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lshszds;
                $('#initTable').datagrid('loadData', data);
               // judgeSelected('#initTable');
            }
        }
    });
}




function searchCatNo1Click(){
    searchHelp("#itemTable","#searchText_CatNo1","catNo","catName");
}

function searchHelp(eleTable,eleInput,no,name){
    var val = $(eleInput).val();
    var row = $(eleTable).datagrid("getSelected");
    var rowIndex ;
    if (row) {
        rowIndex = $(eleTable).datagrid('getRowIndex', row);
    }else{
        rowIndex = -1;
    }
    var gridData = $(eleTable).datagrid("getData");

    // $.each(gridData.rows, function () {
    //     console.log(this[no]);
    //     if(this[no].search(val) != -1 == this[name].search(val) != -1){
    //         $(eleTable).datagrid("selectRow", i);
    //     }else if(i == gridData.total -1){
    //         $.messager.alert('提示', '向下没有找到！', 'info');
    //     }
    // })
    var i = rowIndex +1;
    for (i; i < gridData.total; i++) {
        //console.log(gridData.rows[i][no]);
        if(gridData.rows[i][no].search(val) != -1 || gridData.rows[i][name].search(val) != -1){
            $(eleTable).datagrid("selectRow", i);
            return ;
        }
    }
    if(i == gridData.total ){
        $.messager.alert('提示', '向下没有找到！', 'info');
    }
}
