$(document).ready(function() {

    var RptDate="";//报表日期

    //初始化报表日期
    $.ajax({
        type:'post',
        url:getRealPath()+"/ReportBase/getRptDateNow",
        traditional:true,
        async:false,
        success:function (result) {
            if(result.code==100){
                RptDate = result.msg;
            }else{
                window.parent.alertMessager(result.msg);
                $('#dlg').dialog('close');
                window.parent.tabsClose('#tab1');
            }
        }
    });

    var RptYear=RptDate.substring(0,4);//报表年份
    var RptMonth = RptDate.substring(5,7);//报表月份
    var RptData;//报表数据 Lcdyzd
    var RptInfo;//报表信息 Lcbzd
    var ii;//缓冲效果

    //新建报表窗口 报表日期预加载
    $('#importYear_label').html(RptYear+"年");
    $('#importMonth').numberspinner({
        min:1,
        max:12,
        value:RptMonth,
        suffix:"月",
        precision:0,
        onChange:function () {
            RptMonth = Number($('#importMonth').numberbox('getValue'))+"";
            if(Number(RptMonth)<10){
                RptMonth="0"+RptMonth;
            }
            RptDate = RptYear + "-" + RptMonth;
        }
    });

    //打开文件选择弹框
    $('#file_btn').click(function () {
        $('#turnintoDialog').dialog('open');
    });
    //加载.dat文件，显示列表
    $("#importDatForm").form({
        url:getRealPath()+'/ReportBase/ImportDatList',
        success: function (result) {
            layer.close(ii);
            var result = eval('(' + result + ')');
            if(result.code==100){
                RptInfo = result.extend.lcbzdList;
                RptData = result.extend.lcdyzdList;
                $('#fileName').html(result.extend.fileName);
                $('#importBox').datagrid('loadData', RptInfo);
            }else{
                $.messager.alert('提示', result.msg, 'info');
            }
        }
    });

    //全选
    $('#importSelectAll').click(function () {
        if($('#importSelectAll').is(':checked')){
            $('#importBox').datagrid('selectAll');
        }else{
            $('#importBox').datagrid('unselectAll');
        }
    });
    //基础显示框
    $('#dlg').dialog({
        title: '报表转入',
        buttons:'#dlg-buttons',
        closable:false
    });
    //文件选择框
    $('#turnintoDialog').dialog({
        width: 300,
        height: 200,
        title: '选择转入文件',
        closed: true,
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                ii = layer.load(2);
                $('#importDatForm').submit();
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


    //报表窗口 数据加载
    $('#importBox').datagrid({
        fitColumns:true,
        striped: true,
        loadMsg: '正在加载中，请稍后',
        columns: [[
            {
                field: 'rptNo',
                title: '编号',
                align:'center',
                width:60,
            },
            {
                field: 'rptName',
                title: '报表名称',
                align:'center',
                width:200,
            }
        ]]
    });

    //弹框 ok
    $('#dlg_ok').click(function () {
        var importRows = $('#importBox').datagrid('getSelections');
        var chk_value =[];
        $('input[name="RptImport_cb"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        if(importRows.length>0 && chk_value.length>0){
            var RptNos = [];
            for(var i=0; i<importRows.length; i++){
                RptNos.push(importRows[i].rptNo);
            }
            var RptInfoArr = Handsontable.helper.createEmptySpreadsheetData(RptInfo.length+1,9);
            for(var i=0;i<RptInfo.length;i++){
                //RptInfoArr[i][0] = RptInfo[i].bzdAutoId;
                RptInfoArr[i][1] = RptInfo[i].rptNo;
                RptInfoArr[i][2] = RptInfo[i].rptDate;
                RptInfoArr[i][3] = RptInfo[i].rptName;
                RptInfoArr[i][4] = RptInfo[i].titleRules;
                RptInfoArr[i][5] = RptInfo[i].headRules;
                RptInfoArr[i][6] = RptInfo[i].bodyRules;
                RptInfoArr[i][7] = RptInfo[i].totalRules;
                RptInfoArr[i][8] = RptInfo[i].rptCol;
            }
            var RptDataArr = Handsontable.helper.createEmptySpreadsheetData(RptData.length+1,15);
            for(var i=0;i<RptData.length;i++){
                //RptDataArr[i][0] = RptData[i].dyzdAutoId;
                RptDataArr[i][1] = RptData[i].rptNo;
                RptDataArr[i][2] = RptData[i].rptDate;
                RptDataArr[i][3] = RptData[i].rowInfoNo;
                RptDataArr[i][4] = RptData[i].colInfoNo;
                RptDataArr[i][5] = RptData[i].endRow;
                RptDataArr[i][6] = RptData[i].endCol;
                RptDataArr[i][7] = RptData[i].cellType;
                RptDataArr[i][8] = RptData[i].cellContent;
                RptDataArr[i][9] = RptData[i].formula;
                RptDataArr[i][10] = RptData[i].cellClassName;
                RptDataArr[i][11] = RptData[i].cellWidth;
                RptDataArr[i][12] = RptData[i].cellHeight;
                RptDataArr[i][13] = RptData[i].fontStyle;
                RptDataArr[i][14] = RptData[i].formulaRange;
            }
            $.ajax({
                type: 'POST',
                url: getRealPath() + '/ReportBase/checkRptByNosAndDate',
                traditional: true,//传递数组需要设置为true
                data: {"rptNos":RptNos,"rptDate":RptDate},
                success: function (result) {
                    if(result.code==100){
                        ii = layer.load(2);
                        $.ajax({
                            type: 'POST',
                            url: getRealPath() + '/ReportBase/ImportDatData',
                            traditional: true,//传递数组需要设置为true
                            data: {"isExist":"","rptInfo":RptInfoArr,"rptData":RptDataArr,"rptNos":RptNos,"rptDate":RptDate,"rptCont":chk_value},
                            success: function (res) {
                                layer.close(ii);
                                if(res.code==100){
                                    $.messager.alert('提示',res.msg,'info');
                                }else{
                                    $.messager.alert('提示',res.msg,'info');
                                }
                            }
                        });
                    }else{
                        $.messager.confirm('Confirm',"报表 "+result.msg+"已存在！继续转入将替换当前报表，是否继续？",function(r){
                            if (r){
                                ii = layer.load(2);
                                $.ajax({
                                    type: 'POST',
                                    url: getRealPath() + '/ReportBase/ImportDatData',
                                    traditional: true,//传递数组需要设置为true
                                    data: {"isExist":result.msg,"rptInfo":RptInfoArr,"rptData":RptDataArr,"rptNos":RptNos,"rptDate":RptDate,"rptCont":chk_value},
                                    success: function (res) {
                                        layer.close(ii);
                                        if(res.code==100){
                                            $.messager.alert('提示',res.msg,'info');
                                        }else{
                                            $.messager.alert('提示',res.msg,'info');
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }else{
            $.messager.alert('提示',"请选择导入内容！",'info');
        }

    });


});


//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}

