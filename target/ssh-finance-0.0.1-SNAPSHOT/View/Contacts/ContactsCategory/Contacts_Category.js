$(function () {
    //获取项目的相对路径
    var catLevel = 1;//级数
    var catNo = null ;//编号,在点击下级的时候获得，用于返回上一级

    obj = {
        //上一级事件
        uperLevel_click : function( ){
            if(catLevel != 1) { //判断是否为第一级
                var id, levelFLag;//当前为 一级是id是前两位，二级时id为前四位
                switch (catLevel) {
                    case 2:
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ContactsCategory/getContactsCategory/1',
                            contentType: 'application/json',
                            success: function (result) {
                                $('#box').datagrid('loadData', result);
                                catLevel = 1;
                            }
                        });
                        break;
                    case 3:
                        id = catNo.toString().substring(0, 2);
                        levelFLag = 2;
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ContactsCategory/queryContactsCategoryByLevel/' + id + '/' + levelFLag,
                            contentType: 'application/json',
                            success: function (result) {
                                if (result.code == 100) {
                                    var data = result.extend.lswlflList;
                                    $('#box').datagrid('loadData', data);
                                    catLevel = levelFLag;
                                }
                            },
                        });
                        break;
                }
            }else{
                $.messager.alert('提示', '当前已为第一级！', 'info');
            }
        },

        //下级事件
        nextLevel_click:function () {
            var row = $('#box').datagrid('getSelected');
            if (row) {//判断是否选中行
                if(catLevel != 3){//判断是否为末级
                        catNo = row.catNo1; //获取选中的分类编号
                        var id, levelFLag;//当前为 一级是id是前两位，二级时id为前四位
                        switch (catLevel) {
                            case 1:
                                id = catNo.toString().substring(0, 2);
                                levelFLag = 2;
                                break;
                            case 2:
                                id = catNo.toString().substring(0, 4);
                                levelFLag = 3;
                                break;
                        }
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ContactsCategory/queryContactsCategoryByLevel/' + id + '/' + levelFLag,
                            contentType: 'application/json',
                            success: function (result) {
                                if (result.code == 100) {
                                    var data = result.extend.lswlflList;
                                    $('#box').datagrid('loadData', data);
                                }
                            },
                        });
                        catLevel = levelFLag;
                }else{
                    $.messager.alert('提示', '当前已为末页！', 'info');
                }
            }else{
                $.messager.alert('提示', '请选择一条记录！', 'info');
            }
        },

        //增加事件
        add : function () {
            $('#ff').form('clear');
            $('#add_or_update').dialog('open');
        },

        //删除事件
        remove : function () {
            var row = $('#box').datagrid('getSelected');
            if (row) {//判断是否选中行
                $.messager.confirm('确定操作', '您正在要删除所选的记录吗？', function (flag) {
                    if (flag) {
                        if(row.finLevel.toString() == "是"){
                            var ids = row.catNo1;
                            $.ajax({
                                type : 'POST',
                                url:getRealPath()+'/ContactsCategory/delContactsCategory/' + ids + '/' + catLevel,
                                success : function (result) {
                                    if (result.code == 100) {
                                        var data = result.extend.lswlflList;
                                        $('#box').datagrid('loadData', data);
                                        $.messager.show({
                                            title : '提示',
                                            msg : '删除成功',
                                        });
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

        //存盘事件
        save:function () {
            var num ,id;
            var catNoSubString = null;
            var catNo_Input = $("input[name='catNo1']").val();
            switch (catLevel){
                case 1 :num = catNo_Input+"0000";break;
                case 2 :id = catNoSubString = catNo.substring(0,2);
                        num = catNoSubString+catNo_Input+"00";break;
                case 3 :id = catNoSubString = catNo.substring(0,4);
                     num = catNoSubString+catNo_Input;break;
            }
            var data = '{"catNo1":"' + num + '","catName1":"' + $("input[name='catName1']").val() + '","catLevel":"' + catLevel + '"}';

            // if(!$("#catNo1Id").validatebox('isValid')||!$("#catName1Id").validatebox('isValid')) {
            //     return $('#ff').form('validate');
            // }
            if($('#ff').form("validate")) {//判断校验是否通过
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/ContactsCategory/saveContactsCategory',
                    contentType: 'application/json',
                    data: data,
                    success: function (result) {
                        if (result.code == 100) {
                            var data = result.extend.lswlflList;
                            $('#box').datagrid('loadData', data);
                            //保存成功，关闭对话框，刷新数据最后一页
                            $("#add_or_update").dialog('close');
                            $.messager.show({
                                title: '提示',
                                msg: "添加成功",
                            });
                        } else {
                            var data = result.extend.errorInfo;
                            $.messager.alert('提示', data, 'info');
                            $('#ff').form('clear');
                        }
                    },
                });
            }
            else{
                return $('#ff').form('validate');
            }
        },

    };

    $('#box').datagrid({
        url: getRealPath()+'/ContactsCategory/getContactsCategory/1',
        title: '往来单位类别',
        iconCls: 'icon-chart',
        fitColumns:true,
        striped: true,
        loadMsg: '正在加载中，请稍后',
        singleSelect:'true',
        columns: [[
			{
			    field: 'catNo1',
			    title: '类别编号',
			    align:'center',
                width:100,
			},
			{
			    field: 'catName1',
			    title: '类别名称',
			    align:'center',
                width:200,
			},
			{
			    field: 'finLevel',
			    title: '明细否',
			    align:'center',
                width:50,
			},
        ]],
        toolbar:'#tb',
        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件
            if(catLevel != 3){ //判断是否为末级
                catNo = rowData.catNo1;
                var id,levelFLag;//当前为 一级是id是前两位，二级时id为前四位
                switch (catLevel){
                    case 1:
                        id=catNo.toString().substring(0,2);
                        levelFLag = 2;
                        break;
                    case 2: id=catNo.toString().substring(0,4);
                        levelFLag = 3;
                        break;
                }
                $.ajax({
                    type : 'POST',
                    url : getRealPath()+'/ContactsCategory/queryContactsCategoryByLevel/'+id+'/'+levelFLag,
                    contentType : 'application/json',
                    success : function(result) {
                        if(result.code == 100) {
                            var data = result.extend.lswlflList;
                            $('#box').datagrid('loadData', data);
                        }

                    },

                });
                catLevel = catLevel+1;
            }else{
                $.messager.alert('提示', '当前已是末级', 'info');
            }
        }

    });

    $('#turnintoDialog').dialog({
        width: 405,
        height: 200,
        title: '往来分类转入',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#turnintoDialog').dialog('close');
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#turnintoDialog').dialog('close');
            }
        }],
    });

    $('#turnoutDialog').dialog({
        width: 345,
        height: 190,
        title: '往来分类转出',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#turnoutDialog').dialog('close');
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#turnoutDialog').dialog('close');
            }
        }],
    });

    $('#catNo1Id').numberbox({
        min : 1,
        max : 99,
        parser : function (s) {
            var p = s.substring(0,1);
            if(p!=0 && s<10){
                return "0"+s;
            }else{
                return s;
            }
        },

        // formatter : function (value) {
        //     switch (catLevel){
        //         case 1:value = value+"0000";break;
        //         case 2:value = catNo.substring(0,2)+value+"00";break;
        //         case 3:value  = catNo.substring(0,4)+value;break;
        //     }
        //     return  value;
        // }
    });

    $('#catNo1Id').numberbox('textbox').attr('maxlength', 2);

});


//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}










