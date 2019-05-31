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
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";
var val1 = [];//今年数据
var val2 = [];//去年数据
var nam = [];
var selectTimeValue = "AnnualCumulative";        //选择的时间
var chartsTheme1 = 'wonderland';
var myChart;

$(document).ready(function () {
    getEchartsTheme();
    //echartsDataSelect();
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
                myCharts1();
            }
            else {
                var month1 = now.getMonth() + 1;
                month = month1 + "月";
                myCharts1();
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
            FileName = year + "年管理费用";
        } else {
            month = now.getMonth() + 1;
            FileName = year + "年" + month + "月管理费用";
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
        url: getRealPath() + '/ManagementCost/getEchartsTheme/',
        contentType: 'application/json',
        async: true,
        success: function (result) {
            if (result.code == 100) {
                var data1 = result.extend.ManagementCost_getEchartsTheme;
                chartsTheme1 = data1[0];
                myChart = echarts.init(document.getElementById('main'), chartsTheme1);
                echartsDataSelect();
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
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath() + '/ManagementCost/getValData/' + timeValue,
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                val1 = [];
                nam = [];
                var data = result.extend.MC_ManagementCost_List;
                val1 = data[0];
                val2 = data[2];
                nam = data[1];

                myCharts1();
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
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath() + '/ManagementCost/getDatagridData/' + timeValue,
        contentType: 'application/json',
        success: function (result) {
            if (result.code == 100) {
                datagridData = result.extend.MC_datagridValue_List;
                $('#DataGrid1').datagrid("loadData", datagridData);
            }
        }
    });
}

function myCharts1() {
    //var myChart = echarts.init(document.getElementById('main'), 'light');

    option = {
        tooltip: {
            trigger: 'axis'
        },
        title: {
            text: year + '年度' + month + '管理费用分析',
            subtext: '',
            x: 'center',
            textStyle: {
                //fontSize: 18,
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        legend: {
            //显示策略，可选为：true（显示） | false（隐藏），默认值为true
            show: true,
            y: 35,
            //legend的data: 用于设置图例，data内的字符串数组需要与sereis数组内每一个series的name值对应
            data: ['本期', '上年同期']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: false},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
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
                data: nam
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，
            x: 100,
            x2: 100,
            y2: 150,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [
            {
                type: 'value',
                name: '成本金额(元)',
                splitArea: {
                    show: true
                }
            }
        ],
        series: [
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

//本月报表绘制(test
function myDataGrids1(FileName) {
    $('#DataGrid1').datagrid({
        title: FileName,
        fit: true,//自动补全
        fitColumns: true,
        //每个列具体内容
        columns: [[
            {field: 'text', title: '指标', width: 100},
            {field: 'managementCost', title: '管理费用', width: 100},
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