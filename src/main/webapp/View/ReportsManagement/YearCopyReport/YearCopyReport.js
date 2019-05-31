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
                $('#dlg_1').dialog('close');
                window.parent.tabsClose('#tab1');
            }
        }
    });

    var RptYear=RptDate.substring(0,4);//报表年份
    var RptMonth = 12;//报表月份
    var RptNos = [];//选中需要复制的报表编号
    var isClearData = false;//是否清空数据

    //初始化年月label标签值
    $('#lb_form_date').html(RptYear+"年12月");
    $('#lb_to_data').html((Number(RptYear)+1)+"年01月");

    //清理报表 是否选中
    $('#isClearData').click(function () {
        if($('#isClearData').is(':checked')){
            isClearData = true;
        }else{
            isClearData = false;
        }
    });

    //选择报表窗口 年份加载
    $('#yearTime').numberbox({
        value:Number(RptYear),
        min:1900,
        precision:0,
        editable:false
    });

    //选择报表窗口 月加载
    $('#monthTime').numberbox({
        value:Number(RptMonth),
        min:1,
        max:12,
        precision:0,
        editable:false
    });

    //选择报表窗口--全选
    $('#rptSelectAll').click(function () {
        if($('#rptSelectAll').is(':checked')){
            $('#chooseRptBox').datagrid('selectAll');
        }else{
            $('#chooseRptBox').datagrid('unselectAll');
        }
    });

    //选择报表窗口--确定按钮
    $("#dlg_2_ok").click(function () {
        var selectRows = $('#chooseRptBox').datagrid('getSelections');
        RptNos = [];
        if(selectRows.length>0){
            for(var i=0; i<selectRows.length; i++){
                RptNos.push(selectRows[i].rptNo);
            }
        }
        $('#dlg_2').dialog('close');
    });

    //选择报表窗口--数据加载
    $('#chooseRptBox').datagrid({
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
                width:160,
            }
        ]]
    });

    //年度结转窗口--确认按钮
    $('#dlg_1_ok').click(function () {
        //结转缓冲条
        var ii = layer.msg('结转中，请耐心等待...',{
            icon: 16,
            shade: 0.01,
            time:false //取消自动关闭
        });
        if(RptNos.length == 0){
            RptNos.push("");
        }
        //结转--复制报表、修改报表系统日期
        $.ajax({
            type:'post',
            url:getRealPath()+"/ReportBase/yearCopyRpt",
            traditional:true,
            data:{"copyYear":RptYear,"fromRptNo":RptNos,"isClearData":isClearData},
            success:function (result) {
                layer.close(ii);
                if(result.code==100){
                    $.messager.alert("提示","结转完成！",'info');
                }else{
                    $.messager.alert("提示","结转错误！",'info');
                }
            }
        });
    });

});


//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}

