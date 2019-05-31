// 绘制图标所需当前时间
var now = new Date();   //获取当前时间
var year = now.getFullYear();
var month = "";

// 绘制图标所需数据
var selectTimeField = 'AnnualCumulative';
var selectDepatmentValue = 'All';
var selectSiteValue = 'Null';
var itemsNam = [];  // 显示项目
var itemsNum;   // 显示项目数量
var itemsVal = [];  // 显示项目具体值
var datagridData;   // 报表数据
var FileName;   // 报表名称

$(document).ready(function () {
    departmentSelect();
    getItemsName();
    siteSelect();
    geteChartsData();
});
$(function () {
    $('#DropDownList_SelectSite').combobox({
        valueField: '1',
        textField: '0',
        onSelect:function () {
            selectSiteValue = $('#DropDownList_SelectSite').combobox('getValue');
            myCharts();
            geteChartsData();
            console.log(selectSiteValue);
        }
    });

    $('#DropDownList_SelectDepartment').combobox({
        valueField:'1',
        textField:'0',
        onSelect:function () {
            selectDepatmentValue = $('#DropDownList_SelectDepartment').combobox('getValue');
            myCharts();
            siteSelect();
            selectSiteValue = $('#DropDownList_SelectSite').combobox('getValue');
            geteChartsData();
            console.log(selectDepatmentValue);
        }
    });

    $('#Button_ToReport').click(function () {
        $('#Head').hide();
        $('#Chart').hide();
        $('#ReportForm').show();
        var name = $('#DropDownList_SelectSite').combobox('getText');
        FileName = year + "年" + name + "部门生产成本报表";
        myDataGrids(FileName);
        console.log(FileName);
    });

    $('#Back').click(function () {
        $('#Head').show();
        $('#Chart').show();
        $('#ReportForm').hide();
    });
});

// 分公司下拉菜单获取
function departmentSelect(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/SiteProductionCost/getDepartmentSelect.action/',
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.departmentSelect;
                $("#DropDownList_SelectDepartment").combobox("loadData", data1);
                var data = $('#DropDownList_SelectDepartment').combobox('getData');
                $('#DropDownList_SelectDepartment').combobox('select',data[0][1]);
            }
        },
    });
}

// 站点部门获取
function siteSelect(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/SiteProductionCostStructure/getSiteSelect.action/' + selectDepatmentValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                var data1 = result.extend.siteSelect;
                $("#DropDownList_SelectSite").combobox("loadData", data1);
                var data = $('#DropDownList_SelectSite').combobox('getData');
                $('#DropDownList_SelectSite').combobox('select',data[0][1]);
            }
        },
    });
}

//报表获取
function datagridDataSelect() {
    $.ajax({
        type: "POST",
        //async: false,
        url: getRealPath()+'/SiteProductionCostChart/getDatagridData/' + selectSiteValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                datagridData  = result.extend.SPCC_datagridValue_List;
                $('#DataGrid').datagrid("loadData",datagridData);
                // console.log(datagridData);
            }
        }
    });
    // myDataGrids(FileName);
}

// 图表数据获取
function geteChartsData() {
    $.ajax({
        type: "POST",
        url: getRealPath()+'/SiteProductionCostChart/getValData/' + selectSiteValue,
        contentType: 'application/json',
        success: function(result) {
            if (result.code == 100) {
                itemsVal = result.extend.SPCC_value_List;
                console.log(itemsVal.length);
                console.log(itemsVal);
            }
            myCharts()
        }
    });
}

// 获取显示项目
function getItemsName(){
    $.ajax({
        type: "POST",
        url: getRealPath()+'/SiteProductionCostChart/getItemNam/',
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

//echarts图表绘制
function myCharts() {
    var myChart = echarts.init(document.getElementById('main'),'light');
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        title: {
            text: year + '年度'+ month +'部门生产成本走势图',
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