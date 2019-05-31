var datacom1 = [
    {
        "id" : "01",
        "text" : "一月",
        "selected" : true
    },
    {
        "id" : "02",
        "text" : "二月"
    },
    {
        "id" : "03",
        "text" : "三月"
    },
    {
        "id" : "04",
        "text" : "四月"
    },
    {
        "id" : "05",
        "text" : "五月"
    },
    {
        "id" : "06",
        "text" : "六月"
    },
    {
        "id" : "07",
        "text" : "七月"
    },
    {
        "id" : "08",
        "text" : "八月"
    },
    {
        "id" : "09",
        "text" : "九月"
    },
    {
        "id" : "10",
        "text" : "十月"
    },
    {
        "id" : "11",
        "text" : "十一月"
    },
    {
        "id" : "12",
        "text" : "十二月"
    }
];
var val = [];
var nam = [];
var val1 = [];
var val2 = [];
var val3 = [];
var nam2 = [];
var CompleteCost = [];
var BaseOperateExpense = [];
var DirCost = [];
var IndrtCost = [];
var Production = [];
var UnitCost = [];
var LaborCost = [];
var Depreciation = [];
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";
var selectTimeValue = "01";        //选择的时间
var selectTableValue = "ProductCost";   //选择的报表
var data1;  //接收后台传来的第一个图表的信息
var data2;  //接收后台传来的第二个图表的信息
var chartsTheme1 = 'wonderland';
var chartsTheme2 = "dark";
var myChart;
var selectItemValue = null;

$(document).ready(function () {
    getEchartsTheme();
    itemSelect();
});

$(function () {
    //科目项下拉菜单
    $('#DDL_SelectItem').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectItemValue = $('#DDL_SelectItem').combobox('getValue');
            echarts1DataSelect();
        }
    });

    //转到报表按钮
    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var FileName = "总体生产成本走势表";
        myDataGrids1(FileName);

    });

    //返回按钮
    $('#Back').click(function () {
        $('#Head').show();
        $('#Chart').show();
        $('#ReportForm').hide();
    });

});

//科目项下拉菜单的数据获取
function itemSelect() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/OverallProductionCostChart/getItemSelect/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.OverallProductionCostChart_getItemSelect;
                $("#DDL_SelectItem").combobox("loadData", data1);
                var data = $('#DDL_SelectItem').combobox('getData');
                $('#DDL_SelectItem').combobox('select',data[0][0]);
                selectItemValue = data[0][1];
                echarts1DataSelect();
            }
        }
    });
}

//图表主题获取
function getEchartsTheme() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/OverallProductionCostChart/getEchartsTheme/',
        contentType: 'application/json',
        async: true,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.OverallProductionCostChart_getEchartsTheme;
                chartsTheme1 = data1[0];
                myChart = echarts.init(document.getElementById('main'),chartsTheme1);
                chartsTheme2 = data1[1];
                myChart2 = echarts.init(document.getElementById('container'), chartsTheme2);
                echarts1DataSelect();
                echarts2DataSelect();
            }
        }
    });
}

//图表1数据获取
function echarts1DataSelect() {
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/OverallProductionCostChart/getValData1/' + selectItemValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                data1 = result.extend.OverallProductionCostChart_value1_List;
                val1 = data1[0];
                val2 = data1[1];
                myCharts1();
            }
        }
    });
}

//报表数据获取
function datagridDataSelect() {
    var timeValue;  //解决传给后台的时间月份问题
    if(selectTableValue === "ProductCost"){
        $.ajax({
            type: "POST",
            //async: false,
            url: getRealPath()+'/OverallProductionCostChart/getDatagridData1/',
            contentType: 'application/json',
            success: function(result) {
                if (result.code === 100) {
                    datagridData  = result.extend.OverallProductionCostChart_datagridValue_List1;
                    $('#DataGrid1').datagrid("loadData",datagridData);
                }
            }
        });
    }else {
        $.ajax({
            type: "POST",
            //async: false,
            url: getRealPath()+'/OverallProductionCostChart/getDatagridData2/' + selectTimeValue,
            contentType: 'application/json',
            success: function(result) {
                if (result.code == 100) {
                    datagridData  = result.extend.OverallProductionCostChart_datagridValue_List2;
                    $('#DataGrid1').datagrid("loadData",datagridData);
                }
            }
        });
    }

}

//总体生产成本走势图表
function myCharts1() {
    option = {
        title: {
            text: year + '年度总体生产成本走势图',
            subtext: '',
            x: 'center',
            textStyle: {
                //fontSize: 18,
                fontWeight: 'bolder',
                color: '#333'
            }
        },

        tooltip: {
            trigger: 'axis'
        },

        legend: {
            data: ['本期','上年同期'],
            y: 35
        },

        toolbox: {
            show: true,
            feature: {
                mark: { show: false },
                dataView: { show: true, readOnly: true },
                magicType: { show: true, type: ['line', 'bar', 'stack', 'tiled'] },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        calculable: false,
        xAxis: [{
            type: 'category',
            axisLabel: {
                show: true,
                interval: 0,
                //rotate: 40,
                formatter: function (val) {
                    return val.split("").join("\n");
                }
            },
            splitLine: {
                show: true,
            },
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        }],
        grid: { // 控制图的大小，调整下面这些值就可以，
            y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [{
            type: 'value',
            name: '商品量和成本金额',
            scale: true,
            splitArea: { show: true }
        }, {
            type: 'value',
            name: '单位成本',
            scale: true,
            splitArea: { show: true },
            axisLabel: {
                formatter: '{value} 元/方'
            }
        }],
        series:[
            {
                name: '本期',
                type: 'bar',
                data: val1
            },
            {
                name: '上年同期',
                type: 'bar',
                data: val2
            }
        ]
    };
    myChart.setOption(option);
}

//报表1
function myDataGrids1(FileName){
    $('#DataGrid1').datagrid({
        title: FileName,
        fit: true,//自动补全
        fitColumns: true,
        columns:[[
            { field:'text', title:'指标', width:100},
            { field: 'month1', title: '1月', width: 100},
            { field: 'month2', title: '2月', width: 100},
            { field: 'month3', title: '3月', width: 100},
            { field: 'month4', title: '4月', width: 100},
            { field: 'month5', title: '5月', width: 100},
            { field: 'month6', title: '6月', width: 100},
            { field: 'month7', title: '7月', width: 100},
            { field: 'month8', title: '8月', width: 100},
            { field: 'month9', title: '9月', width: 100},
            { field: 'month10', title: '10月', width: 100},
            { field: 'month11', title: '11月', width: 100},
            { field: 'month12', title: '12月', width: 100}
        ]]
    });
    datagridDataSelect();
}

function getRealPath(){
    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath ;
}