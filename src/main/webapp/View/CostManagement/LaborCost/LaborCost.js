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
var dataShowNo = [
    {
        "id" : "All",
        "text" : "全显示",
        "selected" : true
    },
    {
        "id" : "1",
        "text" : "1",
    },
    {
        "id" : "2",
        "text" : "2",
    },
    {
        "id" : "3",
        "text" : "3",
    },
    {
        "id" : "4",
        "text" : "4",
    },
    {
        "id" : "5",
        "text" : "5",
    },
    {
        "id" : "6",
        "text" : "6",
    },
    {
        "id" : "7",
        "text" : "7",
    },
    {
        "id" : "8",
        "text" : "8",
    }
];
var val = [];
var val1 = [];
var nam = [];
var nam1 = [];
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";
var selectTimeValue = "AnnualCumulative";        //选择的时间
var showNo = "All";     //图表显示项个数
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
        onSelect:function () {
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

    //转到报表按钮
    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var FileName;
        if (selectTimeValue === "AnnualCumulative") {
            FileName = year + "年薪酬信息及分析";
            myDataGrids1(FileName);
        } else {
            month = now.getMonth() + 1;
            FileName = year + "年" + month + "月薪酬信息及分析";
            myDataGrids1(FileName);
        }
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
        url: getRealPath()+'/LaborCost/getEchartsTheme/',
        contentType: 'application/json',
        async: true,
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.LaborCost_getEchartsTheme;
                chartsTheme1 = data1[0];
                myChart = echarts.init(document.getElementById('main'),chartsTheme1);
                echartsDataSelect();
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
        url: getRealPath()+'/LaborCost/getValData/'+ timeValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                val = [];
                val1 = [];
                nam = [];
                var data = result.extend.LC_LaborCost_List;
                // var data1 = data[0];//生产人员费用
                // console.log(data1);
                // for(var i=0;i<data1.length;i++) {
                //     $.each(data1[i], function (key, value) {
                //         val.push(value);
                //         nam.push(key);
                //     });
                // }
                //
                // var data2 = data[1];//管理人员费用
                // console.log(data2);
                // for(var i=0;i<data2.length;i++) {
                //     $.each(data2[i], function (key, value) {
                //         val.push(value);
                //         nam.push(key);
                //     });
                // }
                //
                // var data3 = data[2];//总费用
                // console.log(data3);
                // for(var i=0;i<data3.length;i++) {
                //     $.each(data3[i], function (key, value) {
                //         val1.push(value);
                //         nam1.push(key);
                //     });
                // }
                var data1 = data[0];//生产人员薪酬
                var data2 = data[1];//管理人员薪酬
                var showLength1 =0;
                var showLength2 = 0;
                var dataElse = 0;//将不显示的数据都算在其他里面
                var namElse = "其他（生产人员薪酬）";
                var dataElse2 = 0;//将不显示的数据都算在其他里面
                var namElse2 = "其他（管理人员薪酬）";
                console.log(data1);
                if(showNo === "All"){
                    showLength1 = data1.length;
                    showLength2 = data2.length;
                }else if(showNo>data1.length){
                    showLength1 = data1.length;
                    if(showNo>data2.length){
                        showLength2 = data2.length;
                    }else {
                        showLength2 = showNo;
                    }
                }else if(showNo>data2.length){
                    showLength2 = data2.length;
                    if(showNo>data1.length){
                        showLength1 = data1.length;
                    }else {
                        showLength1 = showNo;
                    }
                }else{
                    showLength1 = showNo;
                    showLength2 = showNo;
                }
                for(var i1=0; i1<showLength1; i1++) {
                    var max = 0;//记录最大值
                    var index = null;//记录最大值在第几个
                    var key1 =null;//记录最大值的名称
                    for (var i2 = 0; i2 < data1.length; i2++) {
                        $.each(data1[i2], function (key, value) {
                            if(value > max){
                                max = value;
                                index = i2;
                                key1 = key;
                            }
                        });
                    }
                    data1.splice(index,1);
                    val.push(max);
                    nam.push(key1);
                }
                //将剩余的数据相加算在“其他”的项里
                for(var i=0; i<data1.length; i++){
                    $.each(data1[i], function (key, value) {
                        dataElse = dataElse + value;
                    });
                }
                val.push(dataElse);
                nam.push(namElse);

                for(var i4=0; i4<showLength2; i4++) {
                    var max2 = 0;//记录最大值
                    var index2 = null;//记录最大值在第几个
                    var key2 = null;//记录最大值的名称
                    for (var i3 = 0; i3 < data2.length; i3++) {
                        $.each(data2[i3], function (key, value) {
                            if(value > max2){
                                max2 = value;
                                index2 = i3;
                                key2 = key;
                            }
                        });
                    }
                    data2.splice(index2,1);
                    val.push(max2);
                    nam.push(key2);
                }
                //将剩余的数据相加算在“其他”的项里
                for(var i=0; i<data2.length; i++){
                    $.each(data2[i], function (key, value) {
                        dataElse2 = dataElse2 + value;
                    });
                }
                val.push(dataElse2);
                nam.push(namElse2);

                var data3 = data[2];//总费用
                console.log(data3);
                for(var i=0;i<data3.length;i++) {
                    $.each(data3[i], function (key, value) {
                        val1.push(value);
                        nam1.push(key);
                    });
                }
                myCharts1();
                //myCharts2();
            };
        },
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
        url: getRealPath()+'/LaborCost/getDatagridData/' + timeValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.LC_datagridValue_List;
                $('#DataGrid1').datagrid("loadData",datagridData);
            }
        }
    });
}

//CB0501薪酬信息及分析图表
function myCharts1() {
    //var myChart = echarts.init(document.getElementById('main'), 'light');

    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        title: {
            text: year + '年度' + month + '薪酬信息及分析',
            subtext: '',
            x: 'center',
            y: 0,
            textStyle: {
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        //legend: {
        //    orient: 'vertical',
        //    //x: 'left',
        //    y: 35,
        //    data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
        //},
        toolbox: {
            show: true,
            feature: {
                mark: { show: false },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        calculable: false,
        series: [
            {
                name: '',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '25%'],
                center: ['50%', '45%'],
                itemStyle: {
                    normal: {
                        label: {
                            position: 'outter',
                            formatter: '{b}({d}%)',
                            textStyle: {
                                //fontSize: 18,
                                //color: '#333'
                            }
                        },
                        labelLine: {
                            show: true,
                            length: 12
                        }
                    }
                },
                data: [
                    { value: val1[1], name: nam1[1] },
                    { value: val1[0], name: nam1[2] }
                     // { value: 200, name: '管理人员薪酬' },
                     // { value: 400, name: '生产人员薪酬' }
                ]
            },
            {
                name: '',
                type: 'pie',
                radius: ['32%', '45%'],
                center: ['50%', '45%'],
                itemStyle: {
                    normal: {
                        label: {
                            position: 'outer',
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
                //     },
                //     {
                //         value: val[6], name: nam[6], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[6] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[6] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[7], name: nam[7], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[7] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[7] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[8], name: nam[8], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[8] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[8] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[9], name: nam[9], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[9] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[9] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                //     {
                //         value: val[10], name: nam[10], itemStyle: {
                //         normal: {
                //             label: {
                //                 //show: true,
                //                 show: function () {
                //                     if (val[10] == 0)
                //                     { return false; }
                //                 }(),
                //                 formatter: '{b}({d}%)'
                //             },
                //             labelLine: {
                //                 //show: true
                //                 show: function () {
                //                     if (val[10] == 0)
                //                     { return false; }
                //                 }(),
                //             }
                //         }
                //     }
                //     },
                // ]
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
    }
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
            { field: 'productionLaborCost', title: '生产人员费用', width: 100},
            { field: 'managementLaborCost', title: '管理人员费用', width:100},
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