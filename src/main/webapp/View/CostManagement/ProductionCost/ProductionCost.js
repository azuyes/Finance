var datacom1 = [
    {
        "id": "AnnualCumulative",
        "text": "本年",
        "selected": true
    },
    {
        "id": "ThisMoth",
        "text": "本月"
    }
];
var val1 = [];
var nam = [];
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";
var selectCompanyValue = "All";     //选择的公司（核算编号1）
var selectProductValue = null;     //选择的产品（核算编号2）
var selectTimeValue = "AnnualCumulative";        //选择的时间
var spNam1 = [];  //核算编号1所有选项
var spNam2 = [];    //核算编号2所有选项
var X_spName = [];  //最终要在x轴显示的内容
var chartsData = [];//存储图表具体数据
var itemNumber = 0;
var itemNam = [];//图表显示的项名称
var itemNam2 = [];//方气的
var datagridData;
var myChart;
var myChart2;
var chartsTheme1 = 'wonderland';
var chartsTheme2 = "dark";

$(document).ready(function () {
    getEchartsTheme();
    departmentSelect();
    productSelect();
    Get_itemNam();
});

$(function () {
    //时间下拉菜单
    $('#DropDownList_SelectTime').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom1,
        onSelect: function () {
            selectTimeValue = $('#DropDownList_SelectTime').combobox('getValue');
            if (selectTimeValue === "AnnualCumulative") {
                month = "";
                echartsDataSelect();
            }
            else {
                var month1 = now.getMonth() + 1;
                month = month1 + "月";
                echartsDataSelect();
            }
        }
    });

    //公司下拉菜单
    $('#DDL_SelectCompany').combobox({
        valueField: '1',
        textField: '0',
        onSelect: function () {
            selectCompanyValue = $('#DDL_SelectCompany').combobox('getValue');
            if (selectCompanyValue === "全部") {//这是因为第一次选择的时候默认项就是“全部”
                selectCompanyValue = "All";
                X_spName = spNam1;
            }
            X_spName = [];//清空数组
            if(selectCompanyValue === "All" && selectProductValue !== "All"){
                X_spName = spNam1;
            }else if(selectCompanyValue === "All" && selectProductValue === "All"){
                alert("该选择无法显示数据！");
            }else if(selectCompanyValue !== "All" && selectProductValue === "All"){
                X_spName = spNam2;
            }else{
                X_spName.push($('#DDL_SelectCompany').combobox('getText'));
            }
            echartsDataSelect();
        }
    });

    //产品下拉菜单
    $('#DDL_SelectProduct').combobox({
        valueField: '1',
        textField: '0',
        onSelect: function () {
            selectProductValue = $('#DDL_SelectProduct').combobox('getValue');
            if (selectProductValue === "不选择") {//这是因为第一次选择的时候默认项就是“不选择”
                selectProductValue = "null";
            }
            X_spName = [];//清空数组
            if(selectCompanyValue === "All" && selectProductValue !== "All"){
                X_spName = spNam1;
            }else if(selectCompanyValue === "All" && selectProductValue === "All"){
                alert("该选择无法显示数据！");
                chartsData = [];
            }else if(selectCompanyValue !== "All" && selectProductValue === "All"){
                X_spName = spNam2;
            }else{
                X_spName.push($('#DDL_SelectCompany').combobox('getText'));
            }
            echartsDataSelect();
        }
    });

    //转到报表按钮
    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var FileName;
        if (selectTimeValue === "AnnualCumulative") {
            FileName = year + "年生产成本信息及分析";
        } else {
            month = now.getMonth() + 1;
            FileName = year + "年" + month + "月生产成本信息及分析";
        }
        myDataGrids1(FileName);
    });

    //返回按钮
    $('#Back').click(function () {
        $('#Head').show();
        $('#Chart').show();
        $('#ReportForm').hide();
    });
});

//图表主题获取
function getEchartsTheme() {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ProductionCost/getEchartsTheme/',
        contentType: 'application/json',
        async: true,
        success: function (result) {
            if (result.code == 100) {
                var data1 = result.extend.ProductionCost_getEchartsTheme;
                chartsTheme1 = data1[0];
                myChart = echarts.init(document.getElementById('main'), chartsTheme1);
                chartsTheme2 = data1[1];
                myChart2 = echarts.init(document.getElementById('container'), chartsTheme2);
                echartsDataSelect();
            }
        }
    });
}

//部门下拉菜单的数据获取
function departmentSelect() {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ProductionCost/getDepartmentSelect.action/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                var data1 = result.extend.ProductionCost_departmentSelect;
                $("#DDL_SelectCompany").combobox("loadData", data1);
                var data = $('#DDL_SelectCompany').combobox('getData');
                $('#DDL_SelectCompany').combobox('select', data[0][0]);
                for (var i = 1; i < data.length; i++) {
                    spNam1.push(data[i][0]);
                }
                X_spName = spNam1;
            }
        }
    });
}

//产品下拉菜单的数据获取
function productSelect() {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ProductionCost/getProductSelect.action/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                var data1 = result.extend.ProductionCost_productSelect;
                console.log(data1);
                $("#DDL_SelectProduct").combobox("loadData", data1);
                var data_product1 = $('#DDL_SelectProduct').combobox('getData');
                // console.log(data);
                $('#DDL_SelectProduct').combobox('select', data_product1[0][0]);
                for (var i = 2; i < data_product1.length; i++) {
                    spNam2.push(data_product1[i][0]);
                }
            }
        }
    });
}

//获取图表项目名称
function Get_itemNam() {
    $.ajax({
        type: "POST",
        url: getRealPath() + '/ProductionCost/getItemNam/',
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                itemNam = result.extend.ProductionCost_itemNam;
                for (var i = 0; i < itemNam.length; i++) {//方气图的项名称，注意最后的“商品量”方气里面没有
                    var item1 = "方气" + itemNam[i];
                    itemNam2.push(item1);
                }
            }
        }
    });
}

//图表数据获取
function echartsDataSelect() {
    var timeValue;  //解决传给后台的时间月份问题
    if (selectTimeValue === "AnnualCumulative") {
        timeValue = "AnnualCumulative";
    } else {
        timeValue = (now.getMonth() + 1).toString();
    }
    if (selectProductValue === "null") {
        selectProductValue = null;
    }
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath() + '/ProductionCost/getValData/' + selectCompanyValue + '/' + selectProductValue + '/' + timeValue,
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                chartsData = result.extend.ProductionCost_value_List;
                itemNumber = chartsData.length;
                myCharts1();
                myCharts2();
            }
        }
    });
}

//报表获取
function datagridDataSelect() {
    var timeValue;  //解决传给后台的时间月份问题
    //alert(selectTimeValue);
    if (selectTimeValue === "AnnualCumulative") {
        timeValue = "AnnualCumulative";
    } else {
        timeValue = (now.getMonth() + 1).toString();
    }
    if (selectProductValue === "null") {
        selectProductValue = null;
    }

    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath() + '/ProductionCost/getDatagridData/' + selectCompanyValue + '/' + selectProductValue + '/' + timeValue,
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                datagridData = result.extend.ProductionCost_datagridValue_List;
                $('#DataGrid1').datagrid("loadData", datagridData);
            }
        }
    });
}

//生产成本信息及分析图表
function myCharts1() {
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title: {
            text: year + '年度' + month + '月生产成本信息及分析',
            subtext: '',
            x: 'center',
            textStyle: {
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        legend: {
            data: itemNam,
            y: 35
        },
        toolbox: {
            show: true,
            orient: 'horizontal',
            feature: {
                mark: {show: false},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: X_spName
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，

            y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [
            {
                type: 'value',
                name: '成本金额(元)',
                splitArea: {show: true}
            }
        ],
        series: function () {
            var serie = [];
            var length1 = chartsData.length / 2;
            for (var i = 0; i < length1; i++) {
                var item = {
                    name: itemNam[i],
                    type: 'bar',
                    barWidth: 160,
                    stack: '广告',
                    data: chartsData[i],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'inside',
                                textStyle: {
                                    color: 'black'
                                }
                            }
                        }
                    }
                };
                serie.push(item);
            }
            return serie;
        }()
    };
    myChart.setOption(option);
}

//方气生产成本信息及分析图表
function myCharts2() {
    option2 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        title: {
            text: year + '年度' + month + '月方气生产成本信息及分析',
            subtext: '',
            x: 'center',
            textStyle: {
                //fontSize: 18,
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        legend: {
            data: itemNam2,
            y: 35
        },
        toolbox: {
            show: true,
            orient: 'horizontal',
            //x: 'right',
            //y: 'center',
            feature: {
                mark: {show: false},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: X_spName
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，

            y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [
            {
                type: 'value',
                name: '成本金额(元)',
                splitArea: {show: true}
            }
        ],
        series: function () {
            var serie = [];
            var length1 = chartsData.length / 2;
            for (var i = length1; i < chartsData.length; i++) {
                var item = {
                    name: itemNam2[i - length1],
                    type: 'bar',
                    barWidth: 160,
                    stack: '广告',
                    data: chartsData[i],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'inside',
                                textStyle: {
                                    color: 'black'
                                }
                            }
                        }
                    }
                };
                serie.push(item);
            }
            return serie;
        }(),

    };
    myChart2.setOption(option2);
}

//报表绘制
function myDataGrids1(FileName) {
    $('#DataGrid1').datagrid({
        title: FileName,
        fit: true,//自动补全
        fitColumns: true,
        //每个列具体内容
        columns: [[
            {field: 'text', title: '指标', width: 100},
            {field: 'itemNo', title: '编号', width: 100},
            {field: 'spNo1', title: '核算1', width: 100},
            {field: 'spNo2', title: '核算2', width: 100},
            {field: 'itemMoney', title: '金额', width: 100},
            {field: 'perItemMoney', title: '方气金额', width: 100},
        ]]
    });
    datagridDataSelect();
}

function getRealPath() {

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;

    return basePath;
}