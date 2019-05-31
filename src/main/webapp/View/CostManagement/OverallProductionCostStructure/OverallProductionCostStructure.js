var dataShowNo = [
    {
        "id" : "All",
        "text" : "全显示",
        "selected" : true
    },
    {
        "id" : "1",
        "text" : "1"
    },
    {
        "id" : "2",
        "text" : "2"
    },
    {
        "id" : "3",
        "text" : "3"
    },
    {
        "id" : "4",
        "text" : "4"
    },
    {
        "id" : "5",
        "text" : "5"
    },
    {
        "id" : "6",
        "text" : "6"
    },
    {
        "id" : "7",
        "text" : "7"
    },
    {
        "id" : "8",
        "text" : "8"
    }
];

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

var val = [];
var nam = [];
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";
var selectTimeValue = "AnnualCumulative";        //选择的时间
var showNo = "All";     //图表显示项个数
var selectCompanyValue = "All";     //选择的公司（核算编号1）
var selectProductValue = null;     //选择的产品（核算编号2）
var chartsTheme1 = 'wonderland';
var myChart;

$(document).ready(function () {
    getEchartsTheme();
    //echartsDataSelect();
    departmentSelect();
    productSelect();
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
                echartsDataSelect();
            }
            else {
                var month1 = now.getMonth() + 1;
                month = month1 + "月";
                echartsDataSelect();
            }
        }
    });

    //显示个数选择下拉菜单
    $('#DropDownList_SelectShowNo').combobox({
        valueField: 'id',
        textField: 'text',
        data: dataShowNo,
        onSelect:function () {
            showNo = $('#DropDownList_SelectShowNo').combobox('getValue');
            echartsDataSelect();
        }
    });

    //公司下拉菜单
    $('#DDL_SelectCompany').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectCompanyValue = $('#DDL_SelectCompany').combobox('getValue');
            console.log(selectCompanyValue);
            if(selectCompanyValue === "全部" ){//这是因为第一次选择的时候默认项就是“全部”
                selectCompanyValue = "All";
            }
            echartsDataSelect();
        }
    });

    //产品下拉菜单
    $('#DDL_SelectProduct').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectProductValue = $('#DDL_SelectProduct').combobox('getValue');
            console.log(selectProductValue);
            if(selectProductValue === "不选择" ){//这是因为第一次选择的时候默认项就是“不选择”
                selectProductValue = "null";
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
            FileName = year + "年总体生产成本结构分析";
        } else {
            month = now.getMonth() + 1;
            FileName = year + "年" + month + "月总体生产成本结构分析";
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
        url: getRealPath()+'/OverallProductionCostStructure/getEchartsTheme/',
        contentType: 'application/json',
        async: true,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.OverallProductionCostStructure_getEchartsTheme;
                chartsTheme1 = data1[0];
                myChart = echarts.init(document.getElementById('main'),chartsTheme1);
                echartsDataSelect();
            }
        }
    });
}

//部门下拉菜单的数据获取
function departmentSelect() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/OverallProductionCostStructure/getDepartmentSelect.action/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.OverallProductionCostStructure_departmentSelect;
                $("#DDL_SelectCompany").combobox("loadData", data1);
                var data = $('#DDL_SelectCompany').combobox('getData');
                console.log(data);
                $('#DDL_SelectCompany').combobox('select',data[0][0]);
            }
        }
    });
}

//产品下拉菜单的数据获取
function productSelect() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/OverallProductionCostStructure/getProductSelect.action/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.OverallProductionCostStructure_productSelect;
                console.log(data1);
                $("#DDL_SelectProduct").combobox("loadData", data1);
                var data_product1 = $('#DDL_SelectProduct').combobox('getData');
                // console.log(data);
                $('#DDL_SelectProduct').combobox('select',data_product1[0][0]);
            }
        }
    });
}

//薪酬信息及分析图表数据获取
function echartsDataSelect() {
    var timeValue;  //解决传给后台的时间月份问题
    //alert(selectTimeValue);
    if(selectTimeValue === "AnnualCumulative"){
        timeValue = "AnnualCumulative";
        console.log(222);
    }else {
        timeValue = (now.getMonth() + 1).toString();
    }
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/OverallProductionCostStructure/getValData/' + selectCompanyValue + '/' + selectProductValue + '/' + timeValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                val = [];
                nam = [];
                var data = result.extend.OverallProductionCostStructure_value_List;
                var data1 = data[0];//科目名称
                var data2 = data[1];//科目金额
                var showLength =0;
                var dataElse = 0;//将不显示的数据都算在其他里面
                var namElse = "其他";
                if(showNo === "All"){
                    showLength = data2.length;
                } else if(showNo>data2.length){
                    showLength = data2.length;
                }else{
                    showLength = showNo;
                }
                for(var i1=0; i1<showLength; i1++) {
                    var max = 0;//记录最大值
                    var index = null;//记录最大值在第几个
                    var key1 =null;//记录最大值的名称
                    for (var i2 = 0; i2 < data2.length; i2++) {
                        if(data2[i2] > max){
                            max = data2[i2];
                            index = i2;
                            key1 = data1[i2];
                        }
                    }
                    data1.splice(index,1);
                    data2.splice(index,1);
                    val.push(max);
                    nam.push(key1);
                }
                //将剩余的数据相加算在“其他”的项里
                for(var i=0; i<data2.length; i++){
                    dataElse = dataElse + data2[i];
                }
                val.push(dataElse);
                nam.push(namElse);

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
    if(selectProductValue === "null"){
        selectProductValue = null;
    }

    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/OverallProductionCostStructure/getDatagridData/' + selectCompanyValue + '/' + selectProductValue + '/' + timeValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.OverallProductionCostStructure_datagridValue_List;
                $('#DataGrid1').datagrid("loadData",datagridData);
            }
        }
    });
}

//总体生产成本结构分析图表
function myCharts1() {
    //var myChart = echarts.init(document.getElementById('main'), '');
    option = {
        title: {
            text: year + '年度' + month + '月总体生产成本结构分析',
            subtext: '',
            x: 'center',
            textStyle: {
                //fontSize: 18,
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}"
        },
        //legend: {
        //    orient: 'vertical',
        //    x: 'left',
        //    data: [nam[0], nam[1], nam[2], nam[3], nam[4], nam[5]]
        //},
        toolbox: {
            show: true,
            feature: {
                mark: { show: false },
                dataView: { show: true, readOnly: false },
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
                },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        calculable: true,
        series: [
            {
                name: '',
                type: 'pie',
                radius: '40%',
                center: ['50%', '35%'],
                itemStyle: {
                    normal: {
                        label: {
                            formatter: '{b}({d}%)'
                        },
                        labelLine: {
                            show: true
                        }
                    }
                },
                // data: [
                //     {
                //         value: val[0], name: nam[0], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[0] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[0] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[1], name: nam[1], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[1] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[1] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[2], name: nam[2], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[2] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[2] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[3], name: nam[3], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[3] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[3] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[4], name: nam[4], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[4] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[4] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[5], name: nam[5], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[5] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[5] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     }
                // ]
                //itemStyle: {
                //    normal: {
                //        label: {
                //            show: true,
                //            formatter: '{b}({d}%)'
                //        },
                //        labelLine: { show: true }
                //    }
                //}
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

//报表绘制
function myDataGrids1(FileName){
    $('#DataGrid1').datagrid({
        title: FileName,
        fit: true,//自动补全
        fitColumns: true,
        //每个列具体内容
        columns:[[
            { field:'text', title:'指标', width:100},
            { field: 'productCost', title: '生产成本金额', width: 100},
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