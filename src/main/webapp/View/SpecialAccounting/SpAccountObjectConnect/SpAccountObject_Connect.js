
//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度

var itemNo = "0";//当前的科目编号
var itemName = "";//当前科目名称

var supAcc1;
var supAcc2;

var spNos1 = [];//定义一个数组存储选中的编号，存储核算对象1的编号

var spNos2 = [];//定义一个数组存储选中的编号，存储核算对象2的编号
var i=0;
var j=0;//ij，作为spNos1，spNos2数组的下标，存储编号

$(function () {

    var convertTreeData = function (rows) {
        var nodes = [];
        var state;
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            if (row.finLevel == '0') {
                state = "closed";
            } else {
                state = "open";
            }
            nodes.push({
                id: row.spNo,
                text: row.spName,
                state: state,
                // checked:row.checked,
                // attributes:row.attributes
            });
        }
        return nodes;
    }
    $.fn.tree.defaults.loadFilter = convertTreeData;

    $('#level').val(level_);
    $('#superior_account').val(upper_No_);
    dealSubStru();//保存科目级数
    dealPrecisions();
    showCaption(upper_No_, level_.toString());

    obj = {
        //上级点击事件
        uperLevel_click: function () {
            if (level_ == 1) {
                return;
            }
            level_--;
            $('#level').val(level_);
            upper_No_ = getCatNo(upper_No_, level_ - 1);
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },
        //下级点击事件
        nextLevel_click: function () {
            var row = $('#itemTable').datagrid('getSelected');
            if (row == null) {
                return;
            }//判断是否选中行
            level_++;
            $('#level').val(level_);
            upper_No_ = getCatNo(row.itemNo, level_ - 1); //获取选中的分类编号
            $('#superior_account').val(upper_No_);
            showCaption(upper_No_, level_);
        },


    }

    $('#box').dialog({
        width: 345,
        height: 190,
        title: '往来单位科目选择',
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
        height: 530,
        title: '科目编码帮助',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: false,
        maximizable: false,
    });

    //帮助窗口数据表格
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect: true,
        columns: [[
            {
                field: 'itemNo',
                title: '编号',
                width: 120,
                formatter: function (value, row, index) {
                    var length = getNumLength(level_);
                    var no = value.substring(0, length);
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
                formatter: function (value, row, index) {
                    switch (value) {
                        case 1:
                            return "是";
                        case 0:
                            return "否";
                    }
                }
            },
            {
                field: 'item',
                title: '级数',
                width: 80
            },
        ]],
        toolbar: '#tb_helpWin',
    });

    // $('#catNo1').combobox({
    //     valueField : 'catNo',
    //     textField : 'catName',
    //     editable:false,
    //     url: getRealPath() + '/SpAccountObjectConnect/getSpAccountCategory',
    //     onSelect : function () {
    //         catNo1Combobox_Select();
    //     }
    //
    // });

    // $('#catNo2').combobox({
    //     valueField : 'catNo',
    //     textField : 'catName',
    //     editable:false,
    //     url: getRealPath() + '/SpAccountObjectConnect/getSpAccountCategory',
    //     onSelect : function () {
    //         catNo2Combobox_Select();
    //     }
    //
    // });

    $('#spTree1').panel({
        //id : 'pox',
        width: 400,
        height: '80%',
        left: 100,
        top: 100,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        closable: false,
        title: '专项核算类别1',
        //tools : '#tt',
        tools: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: function () {

            },
        }, {}],
    });
    $('#spTree2').panel({
        //id : 'pox',
        width: 400,
        height: '80%',
        left: 100,
        top: 100,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        closable: false,
        title: '专项核算类别2',
        //tools : '#tt',
        tools: [{
            text: '确定',
            iconCls: 'icon-ok',
        }, {}],
    });

    $('#specialObjectTree1').tree({
        url: getRealPath() + '/SpAccountObjectConnect/getSpAccountName',
        animate: true,
        checkbox: true,
        cascadeCheck: false,
        onlyLeafCheck: true,
        lines: true,
        dnd: false,
        queryParams: {},
        // loadFilter: function (data) {
        //     console.log(data);
        //     for (var i = 0; i < data.length; i++) {
        //         if(data[i].finLevel == '0'){
        //             data[i].finLevel = 'closed';
        //         }else{
        //             data[i].finLevel = 'open';
        //         }
        //         // for (var att in data.rows[i]) {
        //         //     if (typeof (data.rows[i][att]) == "string") {
        //         //         data.rows[i][att] = data.rows[i][att].replace(/</g, "&lt;").replace(/>/g, "&gt;");
        //         //     }
        //         // }
        //     }
        //     return data;
        // },

        onCheck:function(node,checked){                 //当点击 checkbox 时触发
            // if (checked) {
            //     var parentNode = $("#specialObjectTree1").tree('getParent', node.target);
            //     console.log(parentNode);
            //     if (parentNode != null) {
            //         $("#specialObjectTree1").tree('check', parentNode.target);
            //     }
            // }
            // } else {
            //     var childNode = $("#specialObjectTree1").tree('getChildren', node.target);
            //     if (childNode.length > 0) {
            //         for (var i = 0; i < childNode.length; i++) {
            //             $("#specialObjectTree1").tree('uncheck', childNode[i].target);
            //         }
            //     }
            // }
            if (!checked) {
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/SpAccountObjectConnect/judgeUnChecked/' + node.id + '/' + itemNo,
                    contentType: 'application/json',
                    success: function (result) {
                        if(result.code == 100) {
                            // $.messager.alert('提示！', '恭喜你，关联成功！', 'info');
                        }
                        else{
                            $.messager.alert('警告操作！', '该核算对象中还有账务，不能取消关联！', 'warning');
                            $('#specialObjectTree1').tree('check', node.target);

                        }
                    }
                });
            }

        },

        onBeforeLoad: function (node, param) {
            if(supAcc1 == null || supAcc1 == ""){
                param.type = 0;
            }else{
                param.type = supAcc1;
            }
        },

        onLoadSuccess:function (){   //在数据加载成功以后触发方法
            $("#specialObjectTree1").tree('expandAll');//展开所有节点

            var FunctionAuthority1 = '';
            $.ajax({
                type: "post",
                url: getRealPath() + '/SpAccountObjectConnect/getAuthority1/' + itemNo,
                async: false,//false为异步传输，异步传输才能传全局变量
                success: function (data) {
                    FunctionAuthority1 = data;
                },
                error: function () {
                    alert("error");
                }
            });
            //tree赋初值，即将数据库中已有的权限选中
            if (FunctionAuthority1 !== "") {
                //var TreeID = new Array();
                var TreeID = FunctionAuthority1.split(",");
                for (var i = 0; i < TreeID.length; i++) {
                    var node = $('#specialObjectTree1').tree('find', TreeID[i]);
                    console.log( $('#specialObjectTree1').tree('getRoots'));
                    console.log( $('#specialObjectTree1').tree('options'));

                    $('#specialObjectTree1').tree('check', node.target);
                }
            }


        },
        onExpand : function () {      //在节点展开的时候触发方法
            var FunctionAuthority1 = '';
            $.ajax({
                type: "post",
                url: getRealPath() + '/SpAccountObjectConnect/getAuthority1/' + itemNo,
                async: false,//false为异步传输，异步传输才能传全局变量
                success: function (data) {
                    FunctionAuthority1 = data;
                },
                error: function () {
                    alert("error");
                }
            });
            //tree赋初值，即将数据库中已有的权限选中
            if (FunctionAuthority1 !== "") {
                //var TreeID = new Array();
                var TreeID = FunctionAuthority1.split(",");
                for (var i = 0; i < TreeID.length; i++) {
                    var node = $('#specialObjectTree1').tree('find', TreeID[i]);
                    $('#specialObjectTree1').tree('check', node.target);
                }
            }
        },

        // onLoadSuccess: function (node, data) {
        //     if (data) {
        //         $(data).each(function (index, value) {
        //             if (this.state == 'closed') {
        //                 $('#specialObjectTree1').tree('expandAll');
        //             }
        //         });
        //     }
        //
        //     var FunctionAuthority1 = '';
        //     $.ajax({
        //         type: "post",
        //         url: getRealPath() + '/SpAccountObjectConnect/getAuthority1/' + itemNo,
        //         async: false,//false为异步传输，异步传输才能传全局变量
        //         success: function (data) {
        //             FunctionAuthority1 = data;
        //         },
        //         error: function () {
        //             alert("error");
        //         }
        //     });
        //     //tree赋初值，即将数据库中已有的权限选中
        //     if (FunctionAuthority1 !== "") {
        //         //var TreeID = new Array();
        //         var TreeID = FunctionAuthority1.split(",");
        //         for (var i = 0; i < TreeID.length; i++) {
        //             var node = $('#specialObjectTree1').tree('find', TreeID[i]);
        //             $('#specialObjectTree1').tree('check', node.target);
        //         }
        //     }
        // }

    });

    $('#specialObjectTree2').tree({
        url: getRealPath() + '/SpAccountObjectConnect/getSpAccountName',
        animate: true,
        checkbox: true,
        cascadeCheck: false,
        onlyLeafCheck: true,
        lines: true,
        dnd: false,
        queryParams: {},

        onCheck:function(node,checked){                 //当点击 checkbox 时触发
            var checknodes = $('#specialObjectTree2').tree('getParent', node.target);
            var temp;
            while(checknodes!= null){
                temp=checknodes;
                spNos2.push(temp.id);
                checknodes= $('#specialObjectTree2').tree('getParent', checknodes.target);//对该节点取父节点

            }

        },

        onBeforeLoad: function (node, param) {
            if(supAcc2 == null|| supAcc2 == ""){
                param.type = 0;
            }else{
                param.type = supAcc2;
            }
        },

        onLoadSuccess:function (){   //在数据加载成功以后触发方法
            $("#specialObjectTree2").tree('expandAll');//展开所有节点

            var FunctionAuthority2 = '';
            $.ajax({
                type: "post",
                url: getRealPath() + '/SpAccountObjectConnect/getAuthority2/' + itemNo,
                async: false,//false为异步传输，异步传输才能传全局变量
                success: function (data) {
                    FunctionAuthority2 = data;
                },
                error: function () {
                    alert("error");
                }
            });

            //tree赋初值，即将数据库中已有的权限选中
            if (FunctionAuthority2 !== "") {
                //var TreeID = new Array();
                var TreeID = FunctionAuthority2.split(",");
                for (var i = 0; i < TreeID.length; i++) {
                    var node = $('#specialObjectTree2').tree('find', TreeID[i]);
                    $('#specialObjectTree2').tree('check', node.target);
                }
            }

        },
        onExpand : function () {      //在节点展开的时候触发方法
            var FunctionAuthority2 = '';
            $.ajax({
                type: "post",
                url: getRealPath() + '/SpAccountObjectConnect/getAuthority2/' + itemNo,
                async: false,//false为异步传输，异步传输才能传全局变量
                success: function (data) {
                    FunctionAuthority2 = data;
                },
                error: function () {
                    alert("error");
                }
            });

            //tree赋初值，即将数据库中已有的权限选中
            if (FunctionAuthority2 !== "") {
                //var TreeID = new Array();
                var TreeID = FunctionAuthority2.split(",");
                for (var i = 0; i < TreeID.length; i++) {
                    var node = $('#specialObjectTree2').tree('find', TreeID[i]);
                    $('#specialObjectTree2').tree('check', node.target);
                }
            }
        },
    });

    $('#itemNo_input').textbox('textbox').bind('dblclick', function() {
        judgeSelected("#itemTable");
        $('#helpWin').window('open');
    });



})

// function catNo1Combobox_Select(){
//     supAcc1 = $('#catNo1').combobox('getValue');
//     if(supAcc1 == supAcc2){
//         $.messager.alert('警告操作！', '不能选择重复的分类编号，请重新选择！', 'warning');
//         $('#catNo1').combobox('unselect');
//     }else{
//         $('#specialObjectTree1').tree('reload');
//
//     }
//
// }
//
// function catNo2Combobox_Select(){
//     supAcc2 = $('#catNo2').combobox('getValue');
//     if(supAcc1 == supAcc2){
//         $.messager.alert('警告操作！', '不能选择重复的分类编号，请重新选择！', 'warning');
//         $('#catNo2').combobox('unselect');
//     }else{
//         $('#specialObjectTree2').tree('reload');
//
//     }
//
// }
//确认关联时触发的事件
function connectClick(){
    $.messager.confirm('确定操作', '您确定关联吗吗？', function (flag) {
        if (flag) {
            spNos1=[];
            spNos2=[];
            var nodes1 = $('#specialObjectTree1').tree('getChecked');
            if(nodes1.length == 0){
                $.messager.alert('警告操作！', '请选择要关联的核算对象1！', 'warning');
            }else{
                if(supAcc2 == null || supAcc2 == ""){

                    for (var i = 0; i < nodes1.length; i ++) {
                        spNos1.push(nodes1[i].id);

                        var checknodes = $('#specialObjectTree1').tree('getParent', nodes1[i].target);
                        var temp;
                        while(checknodes!= null){
                            temp=checknodes;
                            spNos1.push(temp.id);
                            checknodes= $('#specialObjectTree1').tree('getParent', checknodes.target);//对该节点取父节点

                        }
                    }

                    $.ajax({
                        type: 'POST',
                        url: getRealPath() + '/SpAccountObjectConnect/spAccountConnect1/' + spNos1 + '/' + itemNo,
                        contentType: 'application/json',
                        success: function (result) {
                            if(result.code == 100) {
                                // var data = result.extend.lskmzdList;
                                // $('#itemTable').datagrid('loadData', data);
                                $.messager.alert('提示！', '恭喜你，关联成功！', 'info');
                            }
                            else{
                                $.messager.alert('警告操作！', '关联失败，请重新操作！', 'warning');
                            }
                        }
                    });
                }else{
                    var nodes2 = $('#specialObjectTree2').tree('getChecked');

                    if(nodes2.length == 0){
                        $.messager.alert('警告操作！', '请选择要关联的核算对象2！', 'warning');
                    }else{

                        for (var i = 0; i < nodes1.length; i ++) {
                            spNos1.push(nodes1[i].id);

                            var checknodes = $('#specialObjectTree1').tree('getParent', nodes1[i].target);
                            var temp;
                            while(checknodes!= null){
                                temp=checknodes;
                                spNos1.push(temp.id);
                                checknodes= $('#specialObjectTree1').tree('getParent', checknodes.target);//对该节点取父节点

                            }
                        }
                        for (var i = 0; i < nodes2.length; i ++) {
                            spNos2.push(nodes2[i].id);

                            var checknodes = $('#specialObjectTree2').tree('getParent', nodes2[i].target);
                            var temp;
                            while(checknodes!= null){
                                temp=checknodes;
                                spNos2.push(temp.id);
                                checknodes= $('#specialObjectTree2').tree('getParent', checknodes.target);//对该节点取父节点

                            }
                        }
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/SpAccountObjectConnect/spAccountConnect2/' + spNos1 + '/' + spNos2 + '/' + itemNo,
                            contentType: 'application/json',
                            success: function (result) {
                                if(result.code == 100) {
                                    // var data = result.extend.lskmzdList;
                                    // $('#itemTable').datagrid('loadData', data);
                                    $.messager.alert('提示！', '恭喜你，关联成功！', 'info');
                                }else{
                                    $.messager.alert('警告操作！', '关联失败，请重新操作！', 'warning');
                                }

                            }
                        });
                    }

                }

            }

        }
    });

}

//帮助窗口，选定科目时事件
function selectClick() {

    var row = $('#itemTable').datagrid("getSelected");
    if(row.supAcc1 == null){
        $.messager.alert('警告操作！', '该科目不是核算科目！', 'warning');
    }else{
        $('#helpWin').window('close');
        $("#itemNo_input").textbox('setValue',row.itemNo);
    }

}

//初始对话框，填好科目编号后，点击确定时的处理事件
function itemNoSelected (){
    itemNo = $('#itemNo_input').val();
    if(itemNo == ""){
        $.messager.alert('警告操作！', '科目不存在！', 'warning');
    }else{
        $.ajax({
            type: 'POST',
            url: getRealPath() + '/SpAccountObjectConnect/getSpItemById/' + itemNo ,
            contentType: 'application/json',
            success: function (result) {
                if(result.code == 100) {
                    var data = result.extend.lskmzd;  //获取科目字典信息，加载在页面最上面进行显示
                    if(data.supAcc1 == null){
                        $.messager.alert('警告操作！', '该科目不是核算科目！', 'warning');
                    }else{  //当在初始化对话框输入或选择的编号为往来科目时，进行数据加载

                        $('#helpWin').window('close');
                        $('#box').dialog('close');
                        document.getElementById("mainDisplay").style.visibility = "visible";

                        supAcc1 = data.supAcc1;
                        supAcc2 = data.supAcc2;

                        var catName1 = result.extend.catName1;
                        var catName2 = result.extend.catName2;

                        itemNo = data.itemNo;
                        itemName = data.itemName;

                        $("#itemNo_label").text(itemNo);
                        $("#itemName_label").text(itemName);

                        $('#specialObjectTree1').tree('reload');



                        $('#spTree1').panel('setTitle','专项核算类别1:'+catName1);


                        if(data.supAcc2 != null && data.supAcc2 != ""){
                            document.getElementById("spTree2Display").style.visibility = "visible";
                            $('#specialObjectTree2').tree('reload');
                            $('#spTree2').panel('setTitle','专项核算类别2:'+catName2);
                        }
                    }

                }else{
                    $.messager.alert('警告操作！', '科目不存在！', 'warning');
                }
            }
        });

    }
}

//显示当前级的科目
function showCaption(num, level){
                    var new_num = "";
                    if(num==""){new_num="0";}
                    else{new_num=num;}
                    $.ajax({
                        type: 'POST',
                        url: getRealPath() + '/SpAccountObjectDef/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
                        contentType: 'application/json',
                        success: function (result) {
                            if(result.code == 100) {
                                var data = result.extend.lskmzdList;
                                $('#itemTable').datagrid('loadData', data);
            }
        }
    });
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
function dealSubStru() {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/getSubjectStructure/',
        contentType: 'application/json',
        success: function (result) {
            var stru = result.split("");
            for(var i=0;i<stru.length;i++){
                sub_stru_[i]=stru[i];
            }
            if(result=="0"){
                alert("请先设置科目结构");
                //TODO：关闭标签页
            }
        },
    });
}

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}

//获取金额账式的数据
function getMoneyData() {
    var acc_level = (level_).toString();
    var acc_num = getFullNo(upper_No_, $('#account_number_input').val(), level_);
    var acc_input = $('#account_name_input').val();
    var acc_attr = $('#account_attribute_select').combobox('getValue');
    var acc_form = $('#account_form_select').combobox('getValue');
    var acc = $('#account_select').combobox('getValue');
    var begin = "", this_debit = "", this_credit = "", current = "";
    if(acc_form=="J"){
        begin = $('#begin_quantity_input').val();
        this_debit = $('#this_debit_quantity_input').val();
        this_credit = $('#this_credit_quantity_input').val();
        current = $('#quantity_input').val();
    }
    else{
        begin = $('#begin_remain_input').val();
        this_debit = $('#this_debit_input').val();
        this_credit = $('#this_credit_input').val();
        current = $('#balance_input').val();
    }
    var data = '{"item":"' + acc_level + '",' +
        '"itemNo":"' + acc_num + '",' +
        '"itemName":"' + acc_input + '",' +
        '"ele":"' + acc_attr + '",' +
        '"accType":"' + acc_form + '",' +
        '"spType":"' + acc + '",' +
        '"SupMoney":"' + begin + '",' +
        '"debitMoneyAcm":"' + this_debit + '",' +
        '"creditMoneyAcm":"' + this_credit + '",' +
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
    var	begin = $('#begin_remain_input').val();
    var	this_debit = $('#this_debit_input').val();
    var	this_credit = $('#this_credit_input').val();
    var	current = $('#balance_input').val();
    var data = '{"itemNo":"' + acc_num + '",' +
        '"supQty":"' + begin + '",' +
        '"debitQtyAcm":"' + this_debit + '",' +
        '"creditQtyAcm":"' + this_credit + '",' +
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
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/getBeginMonth/',
        contentType: 'application/json',
        success: function (result) {
            if(result=="01"){
                if(type=="J") return "supMoney";
                else if(type=="Y") return "supQty";
            }
            else if(result=="0"){
                alert("请先设置科目结构");
                //TODO：关闭标签页
            }
            else{
                if(type=="J") return "LeftMoney"+result;
                else if(type=="Y") return "LeftQty"+result;
            }
        },
    });
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

//获取金额和数量的小数精度
function dealPrecisions() {
    var money_prec = 2;
    var quan_prec = 2;
    precisions_.push(money_prec);
    precisions_.push(quan_prec);
}


function  searchItemNoClick() {
    searchHelp("#itemTable","#searchText_ItemNo","itemNo","itemName");
}

function searchHelp(eleTable,eleInput,no,name){
    var val = $(eleInput).val();
    var row = $(eleTable).datagrid("getSelected");
    var rowIndex ;
    if (row) {
        rowIndex = $(eleTable).datagrid('getRowIndex', row);
    }else{
        rowIndex = 0;
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


function judgeSelected(ele){
    if($(ele).datagrid("getData").total>0){
        $(ele).datagrid("selectRow", 0);
    }
}
