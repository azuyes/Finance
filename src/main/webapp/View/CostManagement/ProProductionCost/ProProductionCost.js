// 时间下拉菜单
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

// 绘制图表所需数据
// 销售方式
var selectSalesMethodValue = '001000000000';
var selectTimeField = 'AnnualCumulative';
// var selectDepatmentValue = 'All';
var nam = [];   // 产品名称
var itemsNam = [];  // 显示项目
var itemsNum;   // 显示项目数量
var itemsVal = [];  // 显示项目具体值
var FileName;   // 报表名称
var datagridData;   // 报表数据

// // 需要进一步修改
// // 固定报表绘制所用数据(test
// var val1 =[5445046.24, 4749451.09, 7354863.03];
// var val2 =[4124123.88, 4589920.67, 7370834.59];
// var val3 =[];
// var val4 =[7904447.00, 12094785.00, 25664652.00];
// var val5 =[	1.21, 0.77, 0.57];

$(document).ready(function () {
    salesMethodSelect();
    getItemsName();
    getChartsData();
    // departmentSelect();
});

$(function () {
    $('#DropDownList_SelectSalesMethod').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectSalesMethodValue = $('#DropDownList_SelectSalesMethod').combobox('getValue');
            myCharts1();
            getChartsData();
            console.log(selectSalesMethodValue);
        }
    });

    // $('#DropDownList_SelectDepartment').combobox({
    //     valueField:'1',
    //     textField:'0',
    //     onSelect:function () {
    //         selectDepatmentValue = $('#DropDownList_SelectDepartment').combobox('getValue');
    //         myCharts1();
    //         getChartsData();
    //         console.log(selectDepatmentValue);
    //     }
    // });

    $('#DropDownList_SelectTime').combobox({
        valueField: 'id',
        textField: 'text',
        data: datacom1,
        onSelect:function () {
            selectTimeField = $('#DropDownList_SelectTime').combobox('getValue');
            console.log(selectTimeField);
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

    // // 包含固定图标绘制(test
    // $('#DropDownList_SelectTable').combobox({
    //     onSelect:function () {
    //         var selectTabelValue = $('#DropDownList_SelectTable').combobox('getValue');
    //         var selectTimeField = $('#DropDownList_SelectTime').combobox('getValue');
    //         var FileName="";
    //         switch (selectTabelValue)
    //         {
    //             case "Production":
    //                 if (selectTimeField != "AnnualCumulative") {
    //                     month = now.getMonth() + 1;
    //                     FileName = year + "年" + month + "月生产成本构成情况";
    //                 }
    //                 else
    //                     FileName = year + "年生产成本构成情况";
    //                 myDataGrids(FileName);
    //                 break;
    //             case "CompleteCost":
    //                 if (selectTimeField != "AnnualCumulative") {
    //                     month = now.getMonth() + 1;
    //                     FileName = year + "年" + month + "月完全成本构成情况";
    //                 }
    //                 else
    //                     FileName = year + "年完全成本构成情况";
    //                 myDataGrids(FileName);
    //                 break;
    //             default:
    //                 break;
    //         }
    //     }
    // });

    // 图表绘制(
    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var selectTime = $('#DropDownList_SelectTime').combobox('getValue');
        if(selectTime === "AnnualCumulative"){
            FileName = year + "年产品生产成本构成情况";
        }else {
            month = now.getMonth() + 1;
            FileName = year + "年" + month + "月产品生产成本构成情况";
        }
        myDataGrids(FileName);
        console.log(FileName);
    });

    $('#Back').click(function () {
        $('#Head').show();
        $('#Chart').show();
        $('#ReportForm').hide();
    });
});

// 图表数据获取
function getChartsData() {
    console.log("aaa");
    var timeField; //传输给后台的时间选择
    if(selectTimeField === "AnnualCumulative"){
        timeField = "AnnualCumulative"
    }else {
        timeField = (now.getMonth() + 1).toString();
    }
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCost/getValData/'+ selectSalesMethodValue + '/' + timeField,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                itemsVal = result.extend.PPC_value_List;
                nam = itemsVal[0];
                itemsNum = itemsVal.length;
            }
            myCharts1()
        },
    });
}

// 销售方式下拉菜单的数据获取
function salesMethodSelect() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/SiteProductionCost/getSalesMethodSelect.action/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.salesMethodSelect;
                $("#DropDownList_SelectSalesMethod").combobox("loadData", data1);
                var data = $('#DropDownList_SelectSalesMethod').combobox('getData');
                $('#DropDownList_SelectSalesMethod').combobox('select',data[0][1]);
            }
        },
    });
}

// 获取显示项目
function getItemsName(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/ProProductionCost/getItemNam/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                itemsNam = result.extend.itemNam;
                console.log(itemsNam);
            }
        }
    });
}

// // 分公司下拉菜单获取
// function departmentSelect(){
//     $.ajax({
//         type: "POST",
//         url: getRealPath()+'/SiteProductionCost/getDepartmentSelect.action/',
//         contentType: 'application/json',
//         success: function(result) {
//             if (result.code == 100) {
//                 var data1 = result.extend.departmentSelect;
//                 $("#DropDownList_SelectDepartment").combobox("loadData", data1);
//                 var data = $('#DropDownList_SelectDepartment').combobox('getData');
//                 $('#DropDownList_SelectDepartment').combobox('select',data[0][1]);
//             }
//         },
//     });
// }

// echarts图表绘制
function myCharts1() {
    var myChart = echarts.init(document.getElementById('main'),'light');
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        title: {
            text: year + '年度'+ month +'产品生产成本分析',
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
                    interval: '0',//设为0显示所有标签
                    //rotate: -15,
                    formatter: function (val) {
                        return val.split("").join("\n");
                    }
                },
                splitLine: {
                    show: true
                },
                data: nam
            }
        ],
        grid: { // 控制图的大小，调整下面这些值就可以，
            x: 100,
            x2: 100,
            y2: 250// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        yAxis: [
            {
                type: 'value',
                name: '成本总额(元)',
                //axisLabel: {//加了这个千分位符就不显示了
                //    formatter: '{value}'
                //},
                splitArea: {
                    show: true
                }
            },
            {
                type: 'value',
                name: '平均成本(元/方)',
                axisLabel: {
                    formatter: '{value}'
                },
                splitLine: { show: false }
            }
        ],
        series:function(){
            var serie=[];
            for(var i = 1; i < itemsNum; i++){
                if(itemsNam[i-1].substring(0,2) === "方气"){
                    var item1={
                        name:itemsNam[i-1],
                        type: 'line',
                        itemStyle: {
                            normal: {
                                lineStyle: {
                                    type: 'solid'
                                }
                            }
                        },
                        yAxisIndex: 1,
                        data:itemsVal[i]
                    };
                    serie.push(item1);
                }
                else{
                    var item={
                        name:itemsNam[i-1],
                        type: 'bar',
                        data:itemsVal[i]
                    };
                    serie.push(item);
                }
            }
            return serie;
        }()
    };
    myChart.setOption(option);
}

//echarts固定报表绘制(test
function myDataGrids(FileName){
    var column = [];
    var array = [];
    array = getColumns();
    column.push(array);
    console.log(column);
    $('#DataGrid').datagrid({
        title: FileName,
        fit: true,//自动补全
        fitColumns: true,
        columns: column
        //     function(){
        //     var cols = [];
        //     for(var i = 0; i<=nam.size(); i++){
        //         if(i === 0){
        //             var col0 = {
        //                 field: 'text',
        //                 title: '指标',
        //                 width: 100
        //             };
        //             cols.push(col0);
        //         }
        //         else{
        //             var col = {
        //                 field: "department" + i.toString(),
        //                 title: nam[i],
        //                 width: 100
        //             };
        //             cols.push(col);
        //         }
        //     }
        //     console.log(cols);
        //     return cols;
        // }()
        //     [[
        //     { field:'text', title:'指标', width:100},
        //     { field: 'month1', title: '1月', width: 100},
        //     { field: 'month2', title: '2月', width: 100},
        //     { field: 'month3', title: '3月', width: 100},
        //     { field: 'month4', title: '4月', width: 100},
        //     { field: 'month5', title: '5月', width: 100},
        //     { field: 'month6', title: '6月', width: 100},
        //     { field: 'month7', title: '7月', width: 100},
        //     { field: 'month8', title: '8月', width: 100},
        //     { field: 'month9', title: '9月', width: 100},
        //     { field: 'month10', title: '10月', width: 100},
        //     { field: 'month11', title: '11月', width: 100},
        //     { field: 'month12', title: '12月', width: 100}
        // ]]
    });
    datagridDataSelect();
}

function getColumns(){
    var array = [];
    // array.push({field:'',title:'',width:''});
    // col.push(array);
    for(var i = 0; i<=nam.length; i++){
        if(i === 0){
            array.push({field:'text',title:'指标',width:100})
            // array[0][index]['field'] = 'text';
            // array[0][index]['title'] = '指标';
            // array[0][index]['width'] = 100;
            // var col0 = {
            //     field: 'text',
            //     title: '指标',
            //     width: 100
            // };
            // array.push(col0);
        }
        else{
            array.push({field:"department" + i.toString(),title:nam[i-1],width:100})
            // array[0][index]['field'] = "department" + i.toString();
            // array[0][index]['title'] = nam[i];
            // array[0][index]['width'] = 100;
            // var col = {
            //     field: "department" + i.toString(),
            //     title: nam[i],
            //     width: 100
            // };
            // array.push(col);
        }
    }
    console.log(array);
    return array;
}

//报表获取
function datagridDataSelect() {
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/ProProductionCost/getDatagridData/' + selectTimeField,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.PPC_datagridValue_List;
                $('#DataGrid').datagrid("loadData",datagridData);
                console.log(datagridData);
            }
        }
    });
    // myDataGrids(FileName);
}

// 获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}