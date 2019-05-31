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

    //打开报表窗口 年份加载
    $('#yearTime').numberbox({
        value:Number(RptDate.substring(0,4)),
        min:1900,
        precision:0,
        editable:false
    });
    //打开报表窗口 月加载
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
            var opt = $('#delBox').datagrid('options');
            opt.url = getRealPath() + '/ReportBase/findRptByYearAndMonth/' + RptYear + "/" + RptMonth;
            //下面的写法会重复渲染，导致发送两次请求
            // $('#delBox').datagrid({url: getRealPath()+'/ReportBase/findRptByYearAndMonth/'+RptYear+"/"+RptMonth});
            $('#delBox').datagrid('reload');
        }
    });

    //打开报表窗口 数据加载
    $('#delBox').datagrid({
        url: getRealPath()+'/ReportBase/findRptByYearAndMonth/'+RptYear+"/"+RptMonth,
        fitColumns:true,
        striped: true,
        rownumbers: true,
        loadMsg: '正在加载中，请稍后',
        columns: [[
            {
                field: 'rptNo',
                title: '报表编号',
                align:'center',
                width:60,
            },
            {
                field: 'rptName',
                title: '报表名称',
                align:'center',
                width:200,
            }
        ]],
        onDblClickRow : function (rowIndex, rowData) {  //双击进入下一级事件

        }

    });
    //全选
    $('#delSelectAll').click(function () {
        if($('#delSelectAll').is(':checked')){
            $('#delBox').datagrid('selectAll');
        }else{
            $('#delBox').datagrid('unselectAll');
        }
    });

    //打开报表窗口 确定按钮 点击事件
    $('#delReportBt').click(function () {
        var rows = $('#delBox').datagrid('getSelections');
        if(rows.length==0)
            $.messager.alert('提示','请选择需要删除的报表!','info');
        if(rows.length>0){
            $.messager.confirm('报表管理子系统', '确定要删除所选定的报表吗？', function (r) {
                if(r){
                    var RptNos = [];
                    for(var i=0; i<rows.length; i++){
                        RptNos.push(rows[i].rptNo);
                    }
                    RptDate = RptDate.substring(0,5)+RptMonth;
                    $.ajax({
                        type: 'POST',
                        url: getRealPath() + '/ReportBase/delRptByNoAndDate',
                        traditional: true,//传递数组需要设置为true
                        data: {"rptNos":RptNos,"rptDate":RptDate},
                        success: function (result) {
                            if(result.code==100){
                                $.messager.alert('提示',result.msg,'info');
                                $('#delBox').datagrid({url: getRealPath()+'/ReportBase/findRptByYearAndMonth/'+RptYear+"/"+RptMonth});
                                $('#delBox').datagrid('reload');
                            }else{
                                $.messager.alert('提示',result.msg,'info');
                            }
                        }
                    });
                }
            });
        }

    });

    //关闭弹框事件
    // $("#dlg").dialog({
    //     onClose: function () {
    //         $(this).dialog('destroy');//销毁
    //         window.parent.tabsClose("#tab1");
    //     }
    // });


});

//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}

