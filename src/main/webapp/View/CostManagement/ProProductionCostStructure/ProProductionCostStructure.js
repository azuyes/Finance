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

// 绘制图标所需当前时间
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";

// 绘制图标所需数据
var selectTimeField = 'AnnualCumulative';
var selectProductValue = "Null";
// var selectDepatmentValue = 'All';
// var selectSiteValue = 'Null';
var showNo = "All";     //图表显示项个数
var nam = [];   // 科目名称
var val = [];    // 科目核算金额
var FileName;   // 报表名称
var datagridData;   // 报表数据

$(document).ready(function () {
    productSelect();
    getShowNo();
    getChartsData();
})

$(function () {
    $('#DropDownList_SelectShowNo').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            showNo = $('#DropDownList_SelectShowNo').combobox('getValue');
            myCharts1();
            getChartsData();
            console.log(showNo);
        }
    });

    $('#DropDownList_SelectProduct').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectProductValue = $('#DropDownList_SelectProduct').combobox('getValue');
            myCharts1();
            getShowNo();
            showNo = $('#DropDownList_SelectProduct').combobox('getValue');
            getChartsData();
            console.log(selectProductValue);
        }
    });

    $('#DropDownList_SelectTime').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom1,
        onSelect:function () {
            selectTimeField = $('#DropDownList_SelectTime').combobox('getValue');
            if (selectTimeField === "AnnualCumulative") {
                month = "";
                myCharts1();
                getChartsData();
            }
            else {
                month = now.getMonth() + 1 + '月';
                myCharts1();
                getChartsData();
            }
        }
    });

    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var name = $('#DropDownList_SelectProduct').combobox('getText');
        FileName = year + "年" + name + "产品生产成本构成情况";
        myDataGrids(FileName);
        console.log(FileName);
    });

    $('#Back').click(function () {
        $('#Head').show();
        $('#Chart').show();
        $('#ReportForm').hide();
    });
});

//echarts报表绘制
function myDataGrids(FileName){
    $('#DataGrid').datagrid({
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

//报表获取
function datagridDataSelect() {
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/ProProductionCostStructure/getDatagridData/' + selectProductValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.PPCS_datagridValue_List;
                $('#DataGrid').datagrid("loadData",datagridData);
                console.log(datagridData);
            }
        }
    });
    // myDataGrids(FileName);
}

// 图表数据获取
function getChartsData() {
    var timeField; //传输给后台的时间选择
    if(selectTimeField === "AnnualCumulative"){
        timeField = "AnnualCumulative"
    }else {
        timeField = (now.getMonth() + 1).toString();
    }
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCostStructure/getValData/'+ showNo + '/' + timeField + '/' + selectProductValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data2 = result.extend.PPCS_value_List;
                nam = data2[0];
                val = data2[1];
            }
            myCharts1()
        }
    });
}

// 产品下拉菜单获取
function productSelect(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCostStructure/getProductSelect.action/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.productList;
                console.log(data1);
                $("#DropDownList_SelectProduct").combobox("loadData", data1);
                var data = $('#DropDownList_SelectProduct').combobox('getData');
                $('#DropDownList_SelectProduct').combobox('select',data[0][1]);
            }
        }
    });
}

// 图标显示个数
function getShowNo(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCostStructure/getShowNo.action/' + selectProductValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.getShowNo;
                $("#DropDownList_SelectShowNo").combobox("loadData", data1);
                var data = $('#DropDownList_SelectShowNo').combobox('getData');
                $('#DropDownList_SelectShowNo').combobox('select',data[0][1]);
            }
        }
    });
}

// echarts图表绘制
function myCharts1() {
    var myChart = echarts.init(document.getElementById('main'),'light');
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}"
        },
        title: {
            text: year + '年度'+ month +'产品生产成本结构分析',
            subtext: '',
            x: 'center',
            textStyle: {
                //fontSize: 18,
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: false },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true },
                magicType: {
                    show: false,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                }
            }
        },
        calculable: true,
        series: [
            {
                name: '成本项目',
                type: 'pie',
                radius: '40%',
                center: ['50%', '35%'],
                itemStyle: {
                    normal: {
                        label: {
                            position: 'outter',
                            formatter: '{b}({d}%)'
                            // textStyle: {
                            //     //fontSize: 18,
                            //     //color: '#333'
                            // }
                        },
                        labelLine: {
                            show: true,
                            length: 12
                        }
                    }
                },
                data: (function () {
                    var res = [];
                    var len = val.length;
                    while (len--) {
                        if (val[len]!=0)
                            res.push({
                                name: nam[len],
                                value: val[len]
                            });
                    }
                    return res;
                })()
            }
        ]
    };
    myChart.setOption(option);
}

function getRealPath() {

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;

    return basePath;
}