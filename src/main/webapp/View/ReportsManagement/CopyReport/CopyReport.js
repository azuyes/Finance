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
                $('#dlg').dialog('close');
                window.parent.tabsClose('#tab1');
            }
        }
    });
    var RptYear=RptDate.substring(0,4);//报表年份
    var RptMonth = RptDate.substring(5,7);//报表月份
    var RptNos = [];//选中需要复制的报表编号
    var isClearData = false;//是否清空数据
    var isExistArr = [];//需要结转的报表，存在标记数组
    var fromRptNo = [];//需要结转的报表，编号数组

    //初始化年月label标签值
    $('#lb_date').html(RptYear+"年");
    $('#rptMonth').numberspinner({
        min:1,
        max:12,
        value:RptMonth,
        suffix:"月",
        precision:0,
        onChange:function () {
            RptMonth = $('#rptMonth').numberspinner('getValue');
            if(RptMonth<10)
                RptMonth = "0"+RptMonth;
            RptDate = RptYear + "-" + RptMonth;
        }
    });

    //报表窗口 年份加载
    $('#yearTime').numberbox({
        value:Number(RptYear),
        min:1900,
        precision:0,
        editable:false
    });
    //报表窗口 月加载
    $('#monthTime').numberbox({
        value:Number(RptMonth),
        min:1,
        max:12,
        precision:0,
        editable:false
    });
    //选择报表--全选
    $('#rptSelectAll').click(function () {
        if($('#rptSelectAll').is(':checked')){
            $('#chooseRptBox').datagrid('selectAll');
        }else{
            $('#chooseRptBox').datagrid('unselectAll');
        }
    });
    //选择覆盖报表--全选
    $('#coverRptSelectAll').click(function () {
        if($('#coverRptSelectAll').is(':checked')){
            $('#chooseCoverRptBox').datagrid('selectAll');
        }else{
            $('#chooseCoverRptBox').datagrid('unselectAll');
        }
    });
    //清理报表 是否选中
    $('#isClearData').click(function () {
        if($('#isClearData').is(':checked')){
            isClearData = true;
        }else{
            isClearData = false;
        }
    });

    //月度结转窗口--指定文件
    $("#chooseRpt_btn").click(function () {
        $('#dlg_2').dialog('open');
        $('#yearTime').numberbox('setValue', Number(RptYear));
        $('#monthTime').numberbox('setValue', Number(RptMonth));
        var opt = $('#chooseRptBox').datagrid('options');
        opt.url = getRealPath() + '/ReportBase/findRptByYearAndMonth/' + RptYear + "/" + RptMonth;
        //修改url datagrid不会自动更新url,需要reload
        $('#chooseRptBox').datagrid('reload');
    });

    //选择报表弹出窗口_确定按钮
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

    //选择报表窗口 数据加载
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
    //选择覆盖报表窗口 数据加载
    $('#chooseCoverRptBox').datagrid({
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

    //月度结转窗口--确认按钮
    $('#dlg_1_ok').click(function () {
        var copyMonth = $('#copyMonth').numberspinner('getValue');
        var isExist = false;
        isExistArr = [];//清空存在标记数组
        fromRptNo = [];//清空报表编号数组
        if(Number(copyMonth)<10)
            copyMonth = "0"+copyMonth;
        if(Number(copyMonth) == Number(RptMonth)){
            $.messager.alert("提示","结转至的会计期间不允许与结转前会计期间相同！请选择其他会计期间。",'info');
        }else{
            if(RptNos.length == 0){//没有选择报表，只修改报表系统日期
                //结转缓冲条
                var ii = layer.msg('结转中，请耐心等待...', {
                    icon: 16,
                    shade: 0.01,
                    time:false //取消自动关闭
                });
                $.ajax({
                    type:'post',
                    url:getRealPath()+"/ReportBase/updateSystemDate",
                    traditional:true,
                    data:{"sysDate":RptDate.substring(0,5)+copyMonth},
                    success:function (result) {
                        layer.close(ii);
                        if(result.code==100){
                            $.messager.alert("提示","结转完成！",'info');
                        }else{
                            $.messager.alert("提示","结转错误！",'info');
                        }
                    }
                });
            }else{
                $.ajax({
                    type:'post',
                    async: false,
                    url:getRealPath()+"/ReportBase/checkRptByNosAndDate",
                    data:{"rptDate":RptDate.substring(0,5)+copyMonth,"rptNos":RptNos},
                    traditional:true,
                    success:function (result) {
                        if(result.code==101){//有已存在报表
                            //将不存在的报表加入存储数组
                            var unExistRptList = result.extend.unExistRptList;
                            for(var i=0;i<unExistRptList.length;i++){
                                isExistArr.push(isExist);
                                fromRptNo.push(unExistRptList[i]);
                            }
                            //将已存在的报表载入存在报表框
                            var RptInfo = result.extend.lcbzdList;
                            $('#chooseCoverRptBox').datagrid('loadData', RptInfo);
                            //关闭基础框，打开覆盖框
                            $('#dlg_1').dialog('close');
                            $('#dlg_3').dialog('open');
                        }else{//无已存在报表
                            for(var i=0;i<RptNos.length;i++){
                                isExistArr.push(isExist);
                                fromRptNo.push(RptNos[i]);
                            }
                            //结转缓冲条
                            var ii = layer.msg('结转中，请耐心等待...', {
                                icon: 16,
                                shade: 0.01,
                                time:false //取消自动关闭
                            });
                            //结转提交
                            $.ajax({
                                type:'post',
                                url:getRealPath()+"/ReportBase/copyRpt",
                                traditional:true,
                                data:{"isExistArr":isExistArr,"copyDate":RptDate.substring(0,5)+copyMonth,"isClearData":isClearData,"fromRptDate":RptDate,"fromRptNo":fromRptNo},
                                success:function (result) {
                                    layer.close(ii);
                                    if(result.code==100){
                                        $.messager.alert("提示","结转完成！",'info');
                                    }else{
                                        $.messager.alert("提示","结转错误！",'info');
                                    }
                                }
                            });
                        }
                    }
                });
            }


        }
    });

    //覆盖窗口确认按钮
    $('#dlg_3_ok').click(function () {
        var copyMonth = $('#copyMonth').numberspinner('getValue');
        var isExist = true;
        if(Number(copyMonth)<10)
            copyMonth = "0"+copyMonth;
        if(Number(copyMonth) == Number(RptMonth)){
            $.messager.alert("提示","结转的会计期间不允许与当前会计期间相同！请选择其他会计期间。",'info');
        }else{
            //将需要覆盖的报表加入存储数组
            var coverRows = $('#chooseCoverRptBox').datagrid('getSelections');
            for(var i=0;i<coverRows.length;i++){
                isExistArr.push(isExist);
                fromRptNo.push(coverRows[i].rptNo);
            }
            $('#dlg_3').dialog('close');
            //结转缓冲条
            var ii = layer.msg('结转中，请耐心等待...', {
                icon: 16
                ,shade: 0.01,
                time:false //取消自动关闭
            });
            //结转提交
            if(isExistArr.length!=0){
                $.ajax({
                    type:'post',
                    url:getRealPath()+"/ReportBase/copyRpt",
                    traditional:true,
                    data:{"isExistArr":isExistArr,"copyDate":RptDate.substring(0,5)+copyMonth,"isClearData":isClearData,"fromRptDate":RptDate,"fromRptNo":fromRptNo},
                    success:function (result) {
                        layer.close(ii);
                        if(result.code==100){
                            $.messager.alert("提示","结转完成！",'info');
                        }else{
                            $.messager.alert("提示","结转错误！",'info');
                        }
                    }
                });
            }else{//没有选择报表，只修改报表系统日期
                $.ajax({
                    type:'post',
                    url:getRealPath()+"/ReportBase/updateSystemDate",
                    traditional:true,
                    data:{"sysDate":RptDate.substring(0,5)+copyMonth},
                    success:function (result) {
                        layer.close(ii);
                        if(result.code==100){
                            $.messager.alert("提示","结转完成！",'info');
                        }else{
                            $.messager.alert("提示","结转错误！",'info');
                        }
                    }
                });
            }

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

