// 绘制图标所需当前时间
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";

// 绘制图标所需数据
var selectTimeField = 'AnnualCumulative';
var selectProductValue = "Null";
var itemsNam = [];  // 显示项目
var itemsNum;   // 显示项目数量
var itemsVal = [];  // 显示项目具体值
var datagridData;   // 报表数据
var FileName;   // 报表名称

// // 科目核算金额，这部分之后要从后台获取
// var val1 = ["-",89082,1376692,1720526,1668610,1100903,1122512,826122];
// var val3 = [13.609963629016,0.579512316480375,0.833176976110794,0.765718801877011,0.677173057026823,1.25830285110538];
// var val7 = [1687303.31,1389269.55,899414.05,1559121.04,1432246.34,844473.15,1542807.65];
// var val2 = [1554999.45,1212402.78,797809.97,1433502.65,1277686.05,745501.85,1412460.05];
// var val4 = [12988,1186.5,98713.82,16763.8,46585.7,470989.5,-3432.86];
// var val5 = [1542011.45,1211216.28,699096.15,1416738.85,1231100.35,274512.35,1415892.91];
// var val6 = [12988,1186.5,98049.02,16763.8,46585.7,469056.7,-3432.86];

$(document).ready(function () {
    productSelect();
    getItemsName();
    geteChartsData();
})
$(function () {
    $('#DropDownList_SelectProduct').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectProductValue = $('#DropDownList_SelectProduct').combobox('getValue');
            myCharts();
            geteChartsData();
            console.log(selectProductValue);
        }
    });

    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var name = $('#DropDownList_SelectProduct').combobox('getText');
        FileName = year + "年" + name + "产品生产成本报表";
        myDataGrids(FileName);
    });

    $('#Back').click(function () {
        $('#Head').show();
        $('#Chart').show();
        $('#ReportForm').hide();
    });
});

//报表获取
function datagridDataSelect() {
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/ProProductionCostChart/getDatagridData/' + selectProductValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.PPCC_datagridValue_List;
                $('#DataGrid').datagrid("loadData",datagridData);
                console.log(datagridData);
            }
        }
    });
    // myDataGrids(FileName);
}

// 产品下拉菜单获取
function productSelect(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCostChart/getProductSelect.action/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.product_List;
                $("#DropDownList_SelectProduct").combobox("loadData", data1);
                var data = $('#DropDownList_SelectProduct').combobox('getData');
                $('#DropDownList_SelectProduct').combobox('select',data[0][1]);
                console.log(selectProductValue);
            }
        },
    });
}

// 获取显示项目
function getItemsName(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCostChart/getItemNam/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                itemsNam = result.extend.itemNam;
                itemsNum = itemsNam.length;
                console.log(itemsNam);
            }
        }
    });
}

// 图表数据获取
function geteChartsData() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCostChart/getValData/' + selectProductValue,
        contentType: 'application/json',
        success: function(result) {
            selectProductValue = $('#DropDownList_SelectProduct').combobox('getValue');
            if (result.code == 100) {
                itemsVal = result.extend.PPCC_value_List;
                console.log(itemsVal.length);
            }
            myCharts()
        }
    });
}

//echarts图表绘制
function myCharts() {
    var myChart = echarts.init(document.getElementById('main'),'light');
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        title: {
            text: year + '年度'+ month +'产品生产成本走势图',
            subtext: '',
            x: 'center',
            textStyle: {
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        legend: {
            data: itemsNam,
            y: 35
        },
        toolbox: {
            show: true,
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
                boundaryGap: false,
                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，
            y2: 30// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [
            {
                type: 'value',
                name: '商品量和成本金额',
                scale: true,
                splitArea: { show: true }
            },
            {
                type: 'value',
                name: '单位成本',
                scale: true,
                splitArea: { show: true },
                axisLabel: {
                    formatter: '{value} 元/方'
                }
            }
        ],
        series:function(){
            var serie=[];
            for(var i = 0; i < itemsNum; i++){
                if(itemsNam[i].substring(0,2) === "方气"){
                    var item1={
                        name:itemsNam[i],
                        type: 'line',
                        itemStyle: {
                            normal: {
                                lineStyle: {
                                    type: 'solid'
                                }
                            }
                        },
                        smooth: 'true',
                        stack: itemsNam[i],
                        yAxisIndex: 1,
                        data:itemsVal[i]
                    };
                    serie.push(item1);
                }
                else{
                    var item={
                        name:itemsNam[i],
                        type: 'line',
                        smooth: 'true',
                        stack: itemsNam[i],
                        data:itemsVal[i]
                    };
                    serie.push(item);
                }
            }
            console.log(serie);
            return serie;
        }()
    };
    myChart.setOption(option);
}

//echarts固定报表绘制(test
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

function getRealPath() {

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;

    return basePath;
}