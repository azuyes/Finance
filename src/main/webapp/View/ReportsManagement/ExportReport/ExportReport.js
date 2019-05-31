$(document).ready(function() {

    var RptDate;//报表日期
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
    var RptMonth=RptDate.substring(5,7);//报表月份

    //报表窗口 年份加载
    $('#yearTime').numberbox({
        value:Number(RptDate.substring(0,4)),
        min:1900,
        precision:0,
        editable:false
    });
    //报表窗口 月加载
    $('#monthTime').numberspinner({
        value:Number(RptMonth),
        min:1,
        max:12,
        precision:0,
        onChange:function () {
            RptMonth = Number($('#monthTime').numberbox('getValue'))+"";
            if(Number(RptMonth)<10){
                RptMonth="0"+RptMonth;
            }
            var opt = $('#sourceBox').datagrid('options');
            opt.url = getRealPath() + '/ReportBase/findRptByYearAndMonth/' + RptYear + "/" + RptMonth;
            //修改url datagrid不会自动更新url,需要reload
            $('#sourceBox').datagrid('reload');
            $('#exportBox').datagrid('loadData', { total: 0, rows: [] });
        }
    });

    //报表窗口 数据加载
    $('#sourceBox').datagrid({
        url: getRealPath()+'/ReportBase/findRptByYearAndMonth/'+RptYear+"/"+RptMonth,
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
                width:150,
            }
        ]],
        onDblClickRow : function (rowIndex, rowData) {  //双击moveRight
            var sourceRows = $('#sourceBox').datagrid('getSelections');
            var exportRows = $('#exportBox').datagrid('getRows');
            for (var i = 0; i < sourceRows.length; i++) {
                var index = $('#sourceBox').datagrid('getRowIndex', sourceRows[i]);//获取某行的行号
                $('#sourceBox').datagrid('deleteRow', index);
                exportRows.push(sourceRows[i]);
            }
            if(exportRows.length>0)
                $('#exportBox').datagrid('loadData', exportRows);
        }

    });
    //文件名文本框设置
    $('#fileName').validatebox({
        required: true
    });
    $("#fileName").val(RptDate.substring(0,4)+RptDate.substring(5,7)+"_****");
    //文件目录文本框设置
    $('#filePath').validatebox({
        required: true
    });
    $("#filePath").val("D:\\DataDownloads");

    //导出报表列表
    $('#exportBox').datagrid({
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
                width:150,
            }
        ]],
        onDblClickRow : function (rowIndex, rowData) {  //双击moveLeft
            var sourceRows = $('#sourceBox').datagrid('getRows');
            var exportRows = $('#exportBox').datagrid('getSelections');
            for (var i = 0; i < exportRows.length; i++) {
                var index = $('#exportBox').datagrid('getRowIndex', exportRows[i]);//获取某行的行号
                $('#exportBox').datagrid('deleteRow', index);
                sourceRows.push(exportRows[i]);
            }
            if(sourceRows.length>0)
                $('#sourceBox').datagrid('loadData', sourceRows);
        }

    });
    //moveRight
    $('#moveRight').click(function () {
        var sourceRows = $('#sourceBox').datagrid('getSelections');
        var exportRows = $('#exportBox').datagrid('getRows');
        for (var i = 0; i < sourceRows.length; i++) {
            var index = $('#sourceBox').datagrid('getRowIndex', sourceRows[i]);//获取某行的行号
            $('#sourceBox').datagrid('deleteRow', index);
            exportRows.push(sourceRows[i]);
        }
        if(exportRows.length>0)
            $('#exportBox').datagrid('loadData', exportRows);
    });
    //allMoveRight
    $('#allMoveRight').click(function () {
        $('#sourceBox').datagrid('selectAll');
        var sourceRows = $('#sourceBox').datagrid('getSelections');
        var exportRows = $('#exportBox').datagrid('getRows');
        for (var i = 0; i < sourceRows.length; i++) {
            var index = $('#sourceBox').datagrid('getRowIndex', sourceRows[i]);//获取某行的行号
            $('#sourceBox').datagrid('deleteRow', index);
            exportRows.push(sourceRows[i]);
        }
        if(exportRows.length>0)
            $('#exportBox').datagrid('loadData', exportRows);
    });
    //moveLeft
    $('#moveLeft').click(function () {
        var sourceRows = $('#sourceBox').datagrid('getRows');
        var exportRows = $('#exportBox').datagrid('getSelections');
        for (var i = 0; i < exportRows.length; i++) {
            var index = $('#exportBox').datagrid('getRowIndex', exportRows[i]);//获取某行的行号
            $('#exportBox').datagrid('deleteRow', index);
            sourceRows.push(exportRows[i]);
        }
        if(sourceRows.length>0)
            $('#sourceBox').datagrid('loadData', sourceRows);
    });
    //allMoveLeft
    $('#allMoveLeft').click(function () {
        $('#exportBox').datagrid('selectAll');
        var sourceRows = $('#sourceBox').datagrid('getRows');
        var exportRows = $('#exportBox').datagrid('getSelections');
        for (var i = 0; i < exportRows.length; i++) {
            var index = $('#exportBox').datagrid('getRowIndex', exportRows[i]);//获取某行的行号
            $('#exportBox').datagrid('deleteRow', index);
            sourceRows.push(exportRows[i]);
        }
        if(sourceRows.length>0)
            $('#sourceBox').datagrid('loadData', sourceRows);
    });
    //弹框1 下一步
    $('#dlg_1_next').click(function () {
        $('#exportBox').datagrid('selectAll');
        var exportRows = $('#exportBox').datagrid('getSelections');
        if(exportRows.length>0){
            $('#dlg_1').dialog('close');
            $('#dlg_2').dialog('open');
        }else {
            $.messager.alert('提示', '请选择需要转出的报表!', 'info');
        }
    });
    //弹框2 上一步
    $('#dlg_2_back').click(function () {
        $('#dlg_2').dialog('close');
        $('#dlg_1').dialog('open');
    });
    //弹框2 下一步
    $('#dlg_2_next').click(function () {
        var chk_value =[];
        $('input[name="RptExport_cb"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        if(chk_value.length==0){
            $.messager.alert('提示','请选择转出报表的内容!','info');
        }else {
            $('#dlg_2').dialog('close');
            $('#dlg_3').dialog('open');
        }
    });
    //弹框3 上一步
    $('#dlg_3_back').click(function () {
        $('#dlg_3').dialog('close');
        $('#dlg_2').dialog('open');
    });
    //弹框3 ok
    $('#dlg_3_ok').click(function () {
        $('#exportBox').datagrid('selectAll');
        var exportRows = $('#exportBox').datagrid('getSelections');
        var chk_value =[];
        var fileNmae = $('#fileName').val();
        var filePath = $('#filePath').val();
        $('input[name="RptExport_cb"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        if(exportRows.length>0 && chk_value.length>0){
            var RptNos = [];
            for(var i=0; i<exportRows.length; i++){
                RptNos.push(exportRows[i].rptNo);
            }
            RptDate = RptDate.substring(0,5)+RptMonth;
            $.ajax({
                type: 'POST',
                url: getRealPath() + '/ReportBase/exportDat',
                traditional: true,//传递数组需要设置为true
                data: {"rptNos":RptNos,"rptDate":RptDate,"rptCont":chk_value,"fileName":fileNmae,"filePath":filePath},
                success: function (result) {
                    if(result.code==100){
                        $.messager.alert('提示',result.msg,'info');
                    }else{
                        $.messager.alert('提示',result.msg,'info');
                    }
                }
            });
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

