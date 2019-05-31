var val1 = [];
var val2 = [];
var val3 = [];
var val4 = [];
var nam = [];
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";
var selectTimeValue = "AnnualCumulative";        //选择的时间
var chartsTheme1 = 'wonderland';
var myChart;
var legendDataMonth = ['年度预算金额', '年度完成金额', '完成年度预算百分比', '完成进度预算百分比'];//
var datacom1 = [
    {
        "id" : "AnnualCumulative",
        "text" : "本年",
        "selected" : true
    },
    {
        "id" : "ThisMoth",
        "text" : "本月"
    }
];

$(document).ready(function () {
    //echartsDataSelect();
    getEchartsTheme();
});

$(function () {
    //时间下拉菜单
    $('#DropDownList_SelectTime').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom1,
        onSelect:function () {
            selectTimeValue = $('#DropDownList_SelectTime').combobox('getValue');
            if (selectTimeValue === "AnnualCumulative") {
                month = "";
                legendDataMonth = ['年度预算金额', '完成金额', '完成年度预算百分比', '完成进度预算百分比'];
                myCharts1();

                //myCharts2();
            }
            else {
                var month1 = now.getMonth() + 1;
                month = month1 + "月";
                legendDataMonth = ['月度预算金额', '月度完成金额', '完成月度预算百分比', '完成进度预算百分比'];
                myCharts1();
                //myCharts2();
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
            FileName = year + "年成本预算执行情况分析";
        } else {
            month = now.getMonth() + 1;
            FileName = year + "年" + month + "月成本预算执行情况分析";
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
        url: getRealPath()+'/CostBudgetImplementationTable/getEchartsTheme/',
        contentType: 'application/json',
        async: true,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.CostBudgetImplementationTable_getEchartsTheme;
                chartsTheme1 = data1[0];
                myChart = echarts.init(document.getElementById('main'),chartsTheme1);
                echartsDataSelect();
            }
        }
    });
}

//成本预算执行情况分析图表数据获取
function echartsDataSelect() {
    var timeValue;  //解决传给后台的时间月份问题
    if(selectTimeValue === "AnnualCumulative"){
        timeValue = "AnnualCumulative";
    }else {
        timeValue = (now.getMonth() + 1).toString();
    }
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/CostBudgetImplementationTable/getValData/'+ timeValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                val1 = [];
                val2 = [];
                val3 = [];
                val4 = [];
                nam = [];
                var data = result.extend.CBIT_CostBudgetImplement_List;
                nam = data[0];
                val1 = data[1];
                val2 = data[2];
                val3 = data[3];
                val4 = data[4];
                console.log(val1);
                myCharts1();
            }
        }
    });
}

//报表获取
function datagridDataSelect() {
    var timeValue;  //解决传给后台的时间月份问题
    //alert(selectTimeValue);
    if(selectTimeValue === "AnnualCumulative"){
        timeValue = "AnnualCumulative";
    }else {
        timeValue = (now.getMonth() + 1).toString();
    }

    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/CostBudgetImplementationTable/getDatagridData/' + timeValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.CBIT_datagridValue_List;
                $('#DataGrid1').datagrid("loadData",datagridData);
            }
        }
    });
}

function myCharts1() {
    //var myChart = echarts.init(document.getElementById('main'), '');
    option = {
        tooltip: {
            trigger: 'axis'
        },
        title: {
            text: year + '年度' + month + '成本预算执行情况分析',
            subtext: '',
            x: 'center',
            textStyle: {
                //fontSize: 18,
                fontWeight: 'bolder',
                color: '#333'}
        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: false },
                dataView: { show: true, readOnly: false },
                magicType: { show: true, type: ['line', 'bar'] },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        calculable: true,
        legend: {
            data: legendDataMonth,
            y:35
        },
        xAxis: [
            {
                type: 'category',
                data: nam
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，

            y2: 30,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [
            {
                type: 'value',
                name: '成本金额（元）',
                //axisLabel: {
                //    formatter: '{value} '
                //},
                splitArea: { show: true }
            },
            {
                type: 'value',
                name: '完成预算百分比',
                axisLabel: {
                    formatter: '{value} %'
                },
                splitLine: { show: false }
            }
        ],
        series: [

            {
                name: legendDataMonth[0],
                type: 'bar',
                data: val2
            },
            {
                name: legendDataMonth[1],
                type: 'bar',
                data: val1
            },
            {
                name: legendDataMonth[2],
                type: 'line',
                itemStyle: {
                    normal: {
                        lineStyle: {
                            type: 'none',
                            shadowColor: 'rgba(0,0,0,0.4)'
                        }
                    }
                },
                //smooth: 'true',
                yAxisIndex: 1,
                data: val3
            },
            {
                name: legendDataMonth[3],
                type: 'line',
                itemStyle: {
                    normal: {
                        lineStyle: {
                            type: 'none',
                            shadowColor: 'rgba(0,0,0,0.4)'
                        }
                    }
                },
                //smooth: 'true',
                yAxisIndex: 1,
                data: val4
            }
        ]
    };
    myChart.setOption(option);
}

//本月报表绘制(test
function myDataGrids1(FileName){
    $('#DataGrid1').datagrid({
        title: FileName,
        fit: true,//自动补全
        fitColumns: true,
        //每个列具体内容
        columns:[[
            { field:'text', title:'指标', width:100},
            { field: 'budgetImplBudget', title: '年度预算金额', width: 100},
            { field: 'budgetImplCost', title: '完成金额', width:100},
            { field: 'budgetImplBudgetPercent', title: '完成年度预算百分比', width:100},
            { field: 'budgetImplCostPercent', title: '完成进度预算百分比', width:100},
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