var unit;//当前单位
var container;//报表控件容器
var pasteData;//复制寄存器
var cell_height;//行高
var cell_width;//列宽
var cell_id;//单元格id
var cell_data;//数据状态数据
var cell_formula;//公式状态数据
var formula_range;//公式应用范围
var cell_border;//边框
var cell_className;//单元格对齐
var cell_merge;//合并单元格
var cell_type;//单元格类型
var cell_type_temp;
var font_style;//字体样式
var titleRows;//标题行数
var headRows;//表头行数
var contentRows;//表体行数
var endRows;//表尾行数
var rows;//总行数
var cols;//总列数
var BBId="";//报表id
var BBNo;//报表编号
var BBName;//报表标题
var BBDate;//报表日期
//帮助框查询
var searchArr = [];//查询结果数组
var searchIndex = 0;//查询结果数组定位
var searchStr = '';//查询字符串

//科目编码--帮助框
//获取项目的相对路径
var level_ = 1;//级数
var upper_No_ = "" ;//编号,在点击下级的时候获得，用于返回上一级
var sub_stru_ = new Array();//存储科目结构
var precisions_ = new Array();//存储金额和数量小数精度
var itemNo = "";//当前的科目编号
var itemName = "";//当前科目名称
var upper_name = "";//上级名称

$(document).ready(function() {
    //自定义渲染器
    //标题渲染器
    function titleRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);//可以选择包含`TextRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.fontWeight = 'bold';
        //cellProperties.readOnly = 'true';
        cellProperties.className = 'htCenter htMiddle';
        td.fontSize = "100px";
    }
    //数字渲染器
    function numberRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.NumericRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        //td.type = "numeric";
        cellProperties.numericFormat.pattern = "0,0.00";
    }
    //无格式渲染器
    function noneRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);//可以选择包含`TextRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.fontWeight = 'normal';
        td.style.fontStyle = 'normal';
        td.style.textDecoration = 'none';
        cellProperties.className = '';
        td.fontSize = "16x";
    }
    //加粗渲染器
    function textBoldRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.fontWeight = 'bold';
    }
    //取消加粗渲染器
    function textBoldCancelRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.fontWeight = 'normal';
    }
    //倾斜渲染器
    function textItalicRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.fontStyle = 'italic';
    }
    //取消倾斜渲染器
    function textItalicCancelRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.fontStyle = 'normal';
    }
    //下划线渲染器
    function textUnderlineRenderer(hotInstance, td, row, column, prop, ni, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.textDecoration = 'underline';
    }
    //取消下划线渲染器
    function textUnderlineCancelRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        Handsontable.renderers.TextRenderer.apply(this, arguments);
        Handsontable.renderers.BaseRenderer.apply(this, arguments);//可以选择包含`BaseRenderer`，它负责向表格单元添加/删除CSS类。
        td.style.textDecoration = 'none';
    }
    //增大字体渲染器
    function fontSizeUpRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        fontSize=window.getComputedStyle(td,null).fontSize;//同步字体大小
        var bigerFont = parseInt(fontSize.substr(0,fontSize.length-2))+1;
        td.style.fontSize = bigerFont+'px';
    }
    //缩小字体渲染器
    function fontSizeDownRenderer(hotInstance, td, row, column, prop, value, cellProperties) {
        fontSize=window.getComputedStyle(td,null).fontSize;//同步字体大小
        var smallerFont = parseInt(fontSize.substr(0,fontSize.length-2))-1;
        td.style.fontSize = smallerFont+'px';
    }

    //注册渲染器
    Handsontable.renderers.registerRenderer('my.title', titleRenderer);
    Handsontable.renderers.registerRenderer('my.number', numberRenderer);
    Handsontable.renderers.registerRenderer('my.none', noneRenderer);
    Handsontable.renderers.registerRenderer('my.textBold', textBoldRenderer);
    Handsontable.renderers.registerRenderer('my.textBoldCancel', textBoldCancelRenderer);
    Handsontable.renderers.registerRenderer('my.textItalic', textItalicRenderer);
    Handsontable.renderers.registerRenderer('my.textItalicCancel', textItalicCancelRenderer);
    Handsontable.renderers.registerRenderer('my.textUnderline', textUnderlineRenderer);
    Handsontable.renderers.registerRenderer('my.textUnderlineCancel', textUnderlineCancelRenderer);
    Handsontable.renderers.registerRenderer('my.fontSizeUp', fontSizeUpRenderer);
    Handsontable.renderers.registerRenderer('my.fontSizeDown', fontSizeDownRenderer);

    unit = window.parent.document.getElementById("Unit").innerText;//当前单位
    container = document.getElementById('data_box');//报表控件容器
    cell_height = [50];//行高
    cell_width = [];//列宽
    cell_border = [];//边框
    cell_merge = [];//合并单元格
    cell_type_temp = [];

    //初始化报表日期
    $.ajax({
        type:'post',
        url:getRealPath()+"/ReportBase/getRptDateNow",
        traditional:true,
        async:false,
        success:function (result) {
            if(result.code==100){
                BBDate = result.msg;
            }else{
                window.parent.alertMessager(result.msg);
                $('#dlg').dialog('close');
                window.parent.tabsClose('#tab1');
            }
        }
    });

    var RptYear = BBDate.substring(0,4);//报表年份
    var RptMonth = Number(BBDate.substring(5,7));//报表月份
    var BBYear_temp=BBDate.substring(0,4);//报表年份（Lsconf中查询的报表年份）

    //新建报表窗口 报表日期预加载
    $('#createYear_label').html(RptYear+"年：");
    $('#createMonth').numberspinner({
        min:1,
        max:12,
        value:RptMonth,
        suffix:"月",
        precision:0
    });

    //新建报表窗口 确定按钮 点击事件
    $('#newReportBt').click(function () {
        if($('#reportNo').validatebox('isValid')==false||$('#reportName').validatebox('isValid')==false)
            return;
        titleRows = Number($("#titleRow").val());//获取标题行数
        headRows = Number($("#headRow").val());//获取表头行数
        contentRows = Number($("#contentRow").val());//获取表体行数
        endRows = Number($("#endRow").val());//获取表尾行数
        rows = titleRows + headRows + contentRows + endRows;//获取总行数
        cols=Number($('#reportCol').val());//获取总列数
        BBNo=$('#reportNo').val();//获取报表编号
        BBName=$('#reportName').val();//获取报表标题
        RptMonth = $('#createMonth').numberspinner('getValue');
        if(Number(RptMonth)<10)//报表日期加工
            BBDate = RptYear + "-0" + RptMonth;
        else
            BBDate = RptYear + "-" + RptMonth;
        //判断当前报表是否存在
        $.ajax({
            type: 'POST',
            url: getRealPath() + '/ReportBase/checkRptByNoAndDate',
            traditional: true,//传递数组需要设置为true
            data: {"rptNo":BBNo,"rptDate":BBDate},
            success: function (result) {
                if(result.code==100){
                    //初始化行高
                    for(var i=1 ; i<rows ; i++)
                        cell_height[i]=20;
                    //初始化列宽
                    for(var i=0 ; i<cols ; i++)
                        cell_width[i]=150;
                    //初始化边框
                    for (var i=titleRows;i<rows-endRows;i++) {
                        for (var j=0;j<cols;j++) {
                            var cell_border_temp={
                                row:i,col:j,
                                left: {width: 1, color: 'black'},
                                right: {width: 1, color: 'black'},
                                top: {width: 1, color: 'black'},
                                bottom: {width: 1, color: 'black'}
                            };
                            cell_border.push(cell_border_temp);
                        }
                    }
                    cell_id = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化单元格id
                    cell_data = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化数据状态数据
                    cell_formula = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化公式状态数据
                    formula_range = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化公式应用范围
                    cell_className = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化单元格对齐
                    cell_type = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化单元格类型
                    font_style = Handsontable.helper.createEmptySpreadsheetData(Number(rows),Number(cols));//初始化字体样式
                    cell_data[0][0] = BBName;//设置标题名
                    cell_data[titleRows-1][0] = unit;//设置单位名
                    cell_data[titleRows-1][Math.floor(cols/2)] = BBDate//设置日期
                    cell_data[titleRows-1][cols-1] = "金额单位：元";//设置金额单位
                    cell_formula[0][0] = BBName;//设置标题名
                    cell_formula[titleRows-1][0] = unit;//设置标题名
                    cell_formula[titleRows-1][cols-1] = "金额单位：元";//设置金额单位
                    //报表控件初始化
                    rptInit();
                    $('#rptDateNow').html("当前会计日期：" + RptYear + "-" + RptMonth);//函数窗口--显示会计日期
                    $('#dlg').dialog('close');
                }else{
                    $.messager.alert('提示',result.msg,'info');
                }
            }
        });
    });


    //单击事件的回调函数
    function clickCellCallBack(event, coords, td) {
        var row = coords.row;
        var col = coords.col;
        if(row >= 0 && col >= 0) {
            var currVal;//取出点击Cell的内容
            if($('#BBState').val()=="公式")
                currVal = cell_formula[row][col];
            else
                currVal = cell_data[row][col];
            var num = 65 + col; //getSelected()方法获取单元格的范围
            var s = String.fromCharCode(num) + (row + 1);
            var cell_val = String(currVal).replace(/&nbsp;/ig, " ");
            cell_val=cell_val.replace(/&amp;/g,"&");
            cell_val=cell_val.replace(/&lt;/g,"<");
            cell_val=cell_val.replace(/&gt;/g,">");
            cell_val=cell_val.replace(/#1/g,",");
            $('#formulabox').val(cellTrim(cell_val)); //同步公式编辑框内容,同时将 &nbsp;转换成空格
            $('#cellPos').get(0).options[0].text = s; //同步单元格位置
            if($('#BBState').val()!="公式"){
                $('#cellPos_input').val(s);//同步单元格位置
            }else{
                if(formula_range[row][col]==null||formula_range[row][col]==""){
                    $('#cellPos_input').val(s);
                    formula_range[row][col]=s;
                }else
                    $('#cellPos_input').val(formula_range[row][col]);//同步单元格位置
            }
            if(String(hot.getCellMeta(row, col).type)=='numeric'){
                set_select_checked('CellType', "数字");
            }else
                set_select_checked('CellType', "常规");
        }
    }

    //行高修改回调函数
    function rowResizeCallBack(currentRow,newSize,isDoubleClick) {
        cell_height[currentRow]=newSize;
    }

    //列宽修改回调函数
    function columnResizeCallBack(currentColumn,newSize,isDoubleClick) {
        cell_width[currentColumn]=newSize;
    }

    //合并单元格回调函数
    function mergeCellsCallBack(cellRange,mergeParent) {
        for(var i=0;i<cell_merge.length;i++){
            if(Number(cell_merge[i][0])>=Number(cellRange.from.row)&&Number(cell_merge[i][0])<=Number(cellRange.to.row)
                &&Number(cell_merge[i][1])>=Number(cellRange.from.col)&&Number(cell_merge[i][1])<=Number(cellRange.to.col)){
                cell_merge.splice(i,1);
            }
        }
        var mergeRange = [cellRange.from.row,cellRange.from.col,cellRange.to.row,cellRange.to.col];
        cell_merge.push(mergeRange);
    }

    //取消合并回调函数
    function beforeUnmergeCellsCallBack(cellRange) {
        for(var i=0;i<cell_merge.length;i++){
            if(Number(cell_merge[i][0])>=Number(cellRange.from.row)&&Number(cell_merge[i][0])<=Number(cellRange.to.row)
                &&Number(cell_merge[i][1])>=Number(cellRange.from.col)&&Number(cell_merge[i][1])<=Number(cellRange.to.col)){
                cell_merge.splice(i,1);
            }
        }
    }

    //创建行回调函数
    function afterCreateRowCallBack(index,amount) {
        createRow(index, amount ,false);
        setTimeout(function () {
            hot.destroy();
            rptInit();
        }, 50);
    }

    //创建列回调函数
    function afterCreateColCallBack(index,amount) {
        createCol(index, amount, false);
        setTimeout(function () {
            hot.destroy();
            rptInit();
        }, 50);
    }

    //删除行回调函数
    function afterRemoveRowCallBack(index,amount) {
        deleteRow(index,amount, false);
        setTimeout(function () {
            hot.destroy();
            rptInit();
        }, 50);
    }

    //删除列回调函数
    function afterRemoveColCallBack(index,amount) {
        deleteCol(index, amount,false);
        setTimeout(function () {
            hot.destroy();
            rptInit();
        }, 50);
    }

    //删除行前回调函数
    function beforeRemoveRowCallBack(index,amount) {
        var flag=true;
        var rows_temp=[];
        for(var i=0;i<rows;i++) {
            if(i<Number(titleRows)){
                rows_temp.push(1);
            }else if(i<Number((titleRows+headRows))){
                rows_temp.push(2);
            }else if(i<Number((titleRows+headRows+contentRows))){
                rows_temp.push(3);
            }else{
                rows_temp.push(4);
            }
        }
        rows_temp.splice(index,amount);
        var titleRows_temp=0,headRows_temp=0,contentRows_temp=0,endRows_temp=0;
        for(var i=0;i<rows_temp.length;i++) {
            switch(rows_temp[i]){
                case 1:
                    titleRows_temp++;
                    break;
                case 2:
                    headRows_temp++;
                    break;
                case 3:
                    contentRows_temp++;
                    break;
                case 4:
                    endRows_temp++;
                    break;
            }
        }
        if(titleRows_temp<2||headRows_temp<1||contentRows_temp<1||endRows_temp<1){
            flag = false;
        }
        if(flag==false)
            $.messager.alert("提示", "行数不满足最低行数限制！", "info");
        return flag;
    }

    //删除列前回调函数
    function beforeRemoveColCallBack(index,amount){
        var flag = true;
        if((cols-amount)<3){
            $.messager.alert('提示', "列数不满足最低列数限制！", "info");
            flag = false;
        }
        return flag;
    }

    //单元格范围文本框保存方法
    function formulaRangeSave(){
        var range = hot.getSelectedLast();
        var formula_val;
        if(range!=null&&($('#cellPos_input').val()!=null||$('#cellPos_input').val()!="")){
            if(hot.getCellMeta(range[0],range[1]).readOnly==true){
                $.messager.alert('提示',"当前单元格不可编辑！",'info');
            }else{
                formula_val = $('#cellPos_input').val();
                if(isValidRange(formula_val)) {
                    formula_range[range[0]][range[1]]=formula_val;

                }else
                    $.messager.alert('提示',"输入格式错误！(提示：A1或A1:B5)",'info');
            }
        }
    }

    //单元格范围编辑框焦点事件
    $('#cellPos_input').blur(function(){
        if($('#BBState').val()=="公式"){
            formulaRangeSave();
        }
    });

    //单元格范围编辑框回车事件
    $('#cellPos_input').bind('keypress',function(event){
        if(event.keyCode == 13){
            $('#cellPos_input').blur();
        }
    });

    //文本框保存方法
    function formulaBoxSave(){
        var range = hot.getSelectedLast();
        var formula_val;
        if(range!=null&&($('#formulabox').val()!=null||$('#formulabox').val()!="")){
            if(hot.getCellMeta(range[0],range[1]).readOnly==true){
                $.messager.alert('提示',"当前单元格不可编辑！",'info');
            }else{
                formula_val = $('#formulabox').val();
                formula_val = formula_val.replace(/&amp;/g,"&");
                formula_val = formula_val.replace(/&lt;/g,"<");
                formula_val = formula_val.replace(/&gt;/g,">");
                formula_val = formula_val.replace(/#1/g,",");
                hot.setDataAtCell(range[0],range[1],formula_val);
            }
        }
    }

    //文本框失去焦点事件
    $('#formulabox').blur(function(){
        if($('#BBState').val()!="公式"){
            formulaBoxSave();
        }
    });

    //文本框回车事件
    $('#formulabox').bind('keypress',function(event){
        if(event.keyCode == 13){
            $('#formulabox').blur();
        }
    });

    //新建报表按钮点击事件
    $('#newReport').click(function () {
        window.parent.addTab('#tab1','新建报表','../ReportsManagement/CreateReport/CreateReport.html');
    });

    //报表属性点击事件
    $('#reportProp').click(function () {
        //报表基本信息
        $('#property_reportNo').textbox('setValue',BBNo);
        $("#property_reportNo").textbox('textbox').css("text-align", "center");
        $('#property_reportName').val(BBName);
        $('#property_titleRow').numberspinner('setValue',titleRows);
        $('#property_headRow').numberspinner('setValue',headRows);
        $('#property_contentRow').numberspinner('setValue',contentRows);
        $('#property_endRow').numberspinner('setValue',endRows);
        $('#property_reportRow').numberspinner('setValue',rows);
        $('#property_reportCol').numberspinner('setValue',cols);
        //单元格类型赋值
        cell_type_temp=[];
        for(var i=0;i<cols;i++){
            if(cell_type[titleRows + headRows][i]!=null)
                cell_type_temp.push(cell_type[titleRows + headRows][i]);
            else
                cell_type_temp.push("");
        }
        //列集合
        var colHeaders = hot.getColHeader();
        var colData = [];
        for(var i = 0;i < colHeaders.length ; i++){
            var colName_temp = "";
            for(var j = titleRows; j < headRows+titleRows ; j++){
                if(cell_data[j][i]!=null&&cell_data[j][i]!="")
                    colName_temp = cell_data[j][i];
            }
            var colData_temp = {
                coord:colHeaders[i],
                colName:colName_temp
            };
            colData.push(colData_temp);
        }
        $('#col_list').datagrid("loadData",colData);
        $("#col_prop").parent().find("div .datagrid-header-check").children("input[type=\"checkbox\"]").eq(0).attr("style", "display:none;");//隐藏表头checkbox
        $('#dlg_property').dialog('open');
        $('#property_reportName').focus();
    });

    //报表属性数据设置
    //报表属性--报表编号
    $('#property_reportNo').textbox({
        readonly:true
    });

    //报表属性--报表名称
    $('#property_reportName').validatebox({
        required: true,
        validType:"length[1,10]",
        invalidMessage:"有效长度为1-10"
    });

    //报表属性--标题行数
    $('#property_titleRow').numberspinner({
        min:2,
        precision:0,
        onChange:function () {
            var v = Number($('#property_titleRow').numberbox('getValue'))+
                Number($('#property_headRow').numberbox('getValue'))+
                Number($('#property_contentRow').numberbox('getValue'))+
                Number($('#property_endRow').numberbox('getValue'));
            $('#property_reportRow').numberbox('setValue',v);
        }
    });

    //报表属性--表头行数
    $('#property_headRow').numberspinner({
        min:1,
        precision:0,
        onChange:function () {
            var v = Number($('#property_titleRow').numberbox('getValue'))+
                Number($('#property_headRow').numberbox('getValue'))+
                Number($('#property_contentRow').numberbox('getValue'))+
                Number($('#property_endRow').numberbox('getValue'));
            $('#property_reportRow').numberbox('setValue',v);
        }
    });

    //报表属性--表体行数
    $('#property_contentRow').numberspinner({
        min:1,
        precision:0,
        onChange:function () {
            var v = Number($('#property_titleRow').numberbox('getValue'))+
                Number($('#property_headRow').numberbox('getValue'))+
                Number($('#property_contentRow').numberbox('getValue'))+
                Number($('#property_endRow').numberbox('getValue'));
            $('#property_reportRow').numberbox('setValue',v);
        }
    });

    //报表属性--表尾行数
    $('#property_endRow').numberspinner({
        min:1,
        precision:0,
        onChange:function () {
            var v = Number($('#property_titleRow').numberbox('getValue'))+
                Number($('#property_headRow').numberbox('getValue'))+
                Number($('#property_contentRow').numberbox('getValue'))+
                Number($('#property_endRow').numberbox('getValue'));
            $('#property_reportRow').numberbox('setValue',v);
        }
    });

    //报表属性--总行数
    $('#property_reportRow').numberbox({
        min:5,
        precision:0,
        editable:false
    });

    //报表属性--表列数
    $('#property_reportCol').numberspinner({
        min:3,
        precision:0,
    });

    //报表属性--列集合
    $('#col_list').datagrid({
        fitColumns:true,
        loadMsg: '正在加载中，请稍后',
        singleSelect: true,
        columns: [[
            {
                field: 'coord',
                title: '坐标',
                align:'center',
                width:40,
            },
            {
                field: 'colName',
                title: '列名称',
                align:'center',
                width:160,
            }
        ]],
        onClickRow:function(index,row){
            $('#col_prop').datagrid("unselectAll");
            var col_type_str = cell_type_temp[index];
            if(col_type_str!=null&&col_type_str!=''){
                var col_type_temp = col_type_str.split(" ");
                for(var i=0;i<col_type_temp.length;i++){
                    switch (col_type_temp[i]){
                        case 'number':
                            $('#col_prop').datagrid('selectRow', 0);
                            break;
                        case 'summary':
                            $('#col_prop').datagrid('selectRow', 1);
                            break;
                        case 'project':
                            $('#col_prop').datagrid('selectRow', 2);
                            break;
                    }
                }
            }
        }
    });

    //报表属性--列属性
    $('#col_prop').datagrid({
        data:[
            {col_prop:"数值列"},
            {col_prop:"汇总列"},
            {col_prop:"项目列"}
        ],
        fitColumns:true,
        checkOnSelect:true,
        selectOnCheck:true,
        loadMsg: '正在加载中，请稍后',
        columns: [[
            {
                field: 'ck',
                checkbox:true,
            },
            {
                field: 'col_prop',
                title: '列属性',
                align:'center',
                width:90,
            }
        ]],
        onSelect: function (index, row) {
            var cell_types = ['number','summary','project'];
            var row_temp = $('#col_list').datagrid('getSelected');
            var rowIndex = $('#col_list').datagrid('getRowIndex',row_temp);
            var col_type_str = cell_type_temp[Number(rowIndex)];
            if(col_type_str.indexOf(cell_types[index])==-1){
                if(cell_type_temp[Number(rowIndex)].length==0||cell_type_temp[Number(rowIndex)].charAt(cell_type_temp[Number(rowIndex)].length-1)==" ")
                    cell_type_temp[Number(rowIndex)] += cell_types[index];//去除重复空格
                else
                    cell_type_temp[Number(rowIndex)] += " " + cell_types[index];
            }
        },
        onUnselect:function (index,row) {
            var cell_types = ['number','summary','project'];
            var row_temp = $('#col_list').datagrid('getSelected');
            var rowIndex = $('#col_list').datagrid('getRowIndex',row_temp);
            var col_type_str = cell_type_temp[Number(rowIndex)];
            var type_index = col_type_str.indexOf(cell_types[index]);
            if(type_index!=-1){
                col_type_str = col_type_str.replace(cell_types[index],"");
                cell_type_temp[Number(rowIndex)] = col_type_str;
            }
        }
    });

    //报表属性--确认按钮
    $("#propertyBt").click(function () {
        if($('#property_reportName').validatebox('isValid')==false)
            return;
        BBName = $('#property_reportName').val();
        //报表行列修改
        var titleRows_temp=Number($('#property_titleRow').numberbox('getValue'));
        var headRows_temp=Number($('#property_headRow').numberbox('getValue'));
        var contentRows_temp=Number($('#property_contentRow').numberbox('getValue'));
        var endRows_temp=Number($('#property_endRow').numberbox('getValue'));
        var rows_temp=titleRows_temp+headRows_temp+contentRows_temp+endRows_temp;
        var cols_temp=Number($('#property_reportCol').numberbox('getValue'));
        if(titleRows_temp<titleRows){
            deleteRow(titleRows_temp, (titleRows - titleRows_temp),true);
        }else if(titleRows_temp>titleRows){
            createRow(titleRows,(titleRows_temp-titleRows),true);
        }
        if(headRows_temp<headRows){
            deleteRow(titleRows_temp+headRows_temp, (headRows - headRows_temp),true);
        }else if(headRows_temp>headRows) {
            createRow(titleRows_temp + headRows, (headRows_temp - headRows),true);
        }
        if(contentRows_temp<contentRows){
            deleteRow((titleRows_temp+headRows_temp+contentRows_temp), (contentRows - contentRows_temp),true);
        }else if(contentRows_temp>contentRows) {
            createRow((titleRows_temp + headRows_temp + contentRows), (contentRows_temp - contentRows),true);
        }
        if(endRows_temp<endRows){
            deleteRow((titleRows_temp+headRows_temp+contentRows_temp+endRows_temp), (endRows - endRows_temp),true);
        }else if(endRows_temp>endRows) {
            createRow((titleRows_temp + headRows_temp + contentRows_temp + endRows), (endRows_temp - endRows),true);
        }
        if(cols_temp<cols){
            deleteCol(cols_temp, (cols - cols_temp),true);
        }else if(cols_temp>cols) {
            createCol(cols, (cols_temp - cols),true);
        }
        setTimeout(function () {
            hot.destroy();
            rptInit();
        }, 50);
        //报表列集合属性修改
        for(var i = (titleRows+headRows);i<rows-endRows;i++) {
            for(var j=0;j<cols;j++){
                cell_type[i][j]=cell_type_temp[j];
            }
        }
        $('#dlg_property').dialog('close');
    });

    //打开报表按钮点击事件
    $('#openReport').click(function () {

    });

    //保存报表按钮点击事件
    $('#saveReport').click(function () {
        $.messager.confirm('提示', '确定保存吗?', function(r){
            if (r){
                var subStr=new RegExp('[,，]','g');//创建正则表达式对象,全局
                //字符串前带有空格加上 "`" 字符
                var cell_data_temp=[].concat(cell_data);//深拷贝 不拷贝地址
                for (var i=0;i<cell_data_temp.length;i++) {//保存空格前缀
                    for(var j=0;j<cell_data_temp[i].length;j++){
                        if(cell_data_temp[i][j]!=null&&cell_data_temp[i][j]!=""){
                            if(cell_data_temp[i][j].charAt(0)==' ')
                                cell_data_temp[i][j]="`"+cell_data_temp[i][j];
                            cell_data_temp[i][j]=cell_data_temp[i][j].replace(subStr,"#1");//把'，,'替换为#1
                        }
                    }
                }
                //把公式数组的'，,'替换为#1
                var cell_formula_temp=[].concat(cell_formula);//深拷贝 不拷贝地址
                for (var i=0;i<cell_formula_temp.length;i++) {
                    for(var j=0;j<cell_formula_temp[i].length;j++){
                        if(cell_formula_temp[i][j]!=null&&cell_formula_temp[i][j]!="")
                            cell_formula_temp[i][j]=cell_formula_temp[i][j].replace(subStr,"#1");//把'，,'替换为#1
                    }
                }
                //修改cell_id数组
                if(cell_id.length>rows){
                    cell_id.splice(rows,(cell_id.length-rows));
                }else{
                    var row_temp=[];
                    for (var j = 0; j < Number(cols); j++) {
                        row_temp.push('');
                    }
                    for(var i=0;i<(rows-cell_id.length);i++){
                        cell_id.push(row_temp);
                    }
                }
                for(var i=0;i<cell_id.length;i++){
                    if(cell_id[i].length>cols){
                        cell_id[i].splice(cols, (cell_id[i].length - cols));
                    }else{
                        for (var j = 0; j < (cols-cell_id[i].length); j++) {
                            cell_id[i].push('');
                        }
                    }
                }
                cell_merge.push([0,0,0,0]);
                var ii = layer.load(2);
                var data = {
                    "cell_id":cell_id,
                    "cell_data":cell_data_temp,
                    "cell_formula":cell_formula_temp,
                    "formula_range":formula_range,
                    "cell_merge":cell_merge,
                    "font_style":font_style,
                    "cell_className":cell_className,
                    "cell_height":cell_height,
                    "cell_width":cell_width,
                    "cell_type":cell_type,
                    "rptId":BBId,
                    "rptNo":BBNo,
                    "rptDate":BBDate,
                    "rptName":BBName,
                    "titleRules":titleRows,
                    "headRules":headRows,
                    "bodyRules":contentRows,
                    "totalRules":rows,
                    "rptCol":cols
                };
                $.ajax({
                    type: 'POST',
                    url: getRealPath() + '/ReportBase/saveOrUpdateRpt',
                    traditional: true,//传递数组需要设置为true
                    data: data,
                    success: function (result) {
                        var lcbzd = result.extend.lcbzd;
                        var lcdyzdList = result.extend.lcdyzdList;
                        if(result.code==100){
                            if(lcbzd.bzdAutoId!=null)
                                BBId=lcbzd.bzdAutoId;//获取报表id
                            //初始化报表内容id
                            for (var i=0;i<lcdyzdList.length;i++) {
                                var rowTemp = Number(lcdyzdList[i].rowInfoNo);
                                var colTemp = Number(lcdyzdList[i].colInfoNo);
                                if(lcdyzdList[i].dyzdAutoId!=null)
                                    cell_id[rowTemp][colTemp] = lcdyzdList[i].dyzdAutoId;
                            }
                        }
                        layer.close(ii);
                        cell_merge.pop();
                        $.messager.alert('提示',result.msg,'info');
                    }
                });
            }
        });
    });

    //复制
    $('#copyCont').click(function() {
        var plugin = hot.getPlugin('copyPaste');
        var range = hot.getSelectedLast();//返回选择得单元格范围。return 左上行号、左上列号、右下行号、右下列号
        if(range!=null){
            pasteData = hot.getDataAtCell(range[0], range[1]);
            if(plugin.isEnabled()) {
                plugin.copy();
            }
        }
    });

    //剪切
    $('#cutCont').click(function() {
        var plugin = hot.getPlugin('copyPaste');
        var range = hot.getSelectedLast();
        if(range!=null){
            pasteData = hot.getDataAtCell(range[0], range[1]);
            if(plugin.isEnabled()) {
                plugin.cut();
            }
        }
    });

    //粘贴
    $('#pasteCont').click(function() {
        var plugin = hot.getPlugin('copyPaste');
        if(plugin.isEnabled()) {
            plugin.paste(pasteData);
        }
    });

    //加粗
    $('#fontB').click(function() {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    var fontW=hot.getCell(i,j,true).style.fontWeight.toString();
                    if(fontW!=null&&fontW=="bold"){
                        font_style[i][j] = "";
                        hot.setCellMeta(i, j, "renderer", "my.textBoldCancel");
                    }else {
                        font_style[i][j] = "bold";
                        hot.setCellMeta(i, j, "renderer", "my.textBold");
                    }
                }
            }
            hot.render();
        }
    });

    //倾斜
    $('#fontLean').click(function() {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    var fontW=hot.getCell(i,j,true).style.fontStyle.toString();
                    if(fontW!=null&&fontW=="italic") {
                        font_style[i][j] = "";
                        hot.setCellMeta(i, j, "renderer", "my.textItalicCancel");
                    }else {
                        font_style[i][j] = "italic";
                        hot.setCellMeta(i, j, "renderer", "my.textItalic");
                    }
                }
            }
            hot.render();
        }
    });

    //下划线
    $('#fontUnderline').click(function() {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    var fontW=hot.getCell(i,j,true).style.textDecoration.toString();
                    if(fontW!=null&&fontW=="underline"){
                        font_style[i][j] = "";
                        hot.setCellMeta(i, j, "renderer", "my.textUnderlineCancel");
                    }else{
                        font_style[i][j] = "underline";
                        hot.setCellMeta(i, j, "renderer", "my.textUnderline");
                    }
                }
            }
            hot.render();
        }
    });

    //增大字号
    // $('#fontSizeUp').click(function() {
    //     var selectArea = hot.getSelectedLast();
    //     if(selectArea!=null){
    //         for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
    //             for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
    //                 hot.setCellMeta(i, j, "renderer", "my.fontSizeUp");
    //             }
    //         }
    //         hot.render();
    //     }
    // });
    //缩小字号
    // $('#fontSizeDown').click(function() {
    //     var selectArea = hot.getSelectedLast();
    //     if(selectArea!=null){
    //         for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
    //             for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
    //                 hot.setCellMeta(i, j, "renderer", "my.fontSizeDown");
    //             }
    //         }
    //         hot.render();
    //     }
    // });

    //向上对齐
    $('#fontUp').click(function() {
        var selectArea = hot.getSelectedLast();
        var className_temp = "";
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(cell_className[i][j]!=null && cell_className[i][j]!="" && (cell_className[i][j]=="htLeft"||cell_className[i][j]=="htCenter"||cell_className[i][j]=="htRight"))
                        className_temp = cell_className[i][j]+" htTop";
                    else
                        className_temp = "htTop";
                    hot.setCellMeta(i, j, "className", className_temp);
                    cell_className[i][j]=className_temp;
                }
            }
            hot.render();
        }
    });

    //垂直居中
    $('#fontMid').click(function() {
        var selectArea = hot.getSelectedLast();
        var className_temp = "";
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(cell_className[i][j]!=null && cell_className[i][j]!="" && (cell_className[i][j]=="htLeft"||cell_className[i][j]=="htCenter"||cell_className[i][j]=="htRight"))
                        className_temp = cell_className[i][j]+" htMiddle";
                    else
                        className_temp = "htMiddle";
                    hot.setCellMeta(i, j, "className", className_temp);
                    cell_className[i][j]=className_temp;
                }
            }
            hot.render();
        }
    });

    //向下对齐
    $('#fontDown').click(function() {
        var selectArea = hot.getSelectedLast();
        var className_temp = "";
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(cell_className[i][j]!=null && cell_className[i][j]!="" && (cell_className[i][j]=="htLeft"||cell_className[i][j]=="htCenter"||cell_className[i][j]=="htRight"))
                        className_temp = cell_className[i][j]+" htBottom";
                    else
                        className_temp = "htBottom";
                    hot.setCellMeta(i, j, "className", className_temp);
                    cell_className[i][j]=className_temp;
                }
            }
            hot.render();
        }
    });

    //向左对齐
    $('#fontLeft').click(function() {
        var selectArea = hot.getSelectedLast();
        var className_temp = "";
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(cell_className[i][j]!=null && cell_className[i][j]!="" && (cell_className[i][j]=="htTop"||cell_className[i][j]=="htMiddle"||cell_className[i][j]=="htBottom"))
                        className_temp = cell_className[i][j]+" htLeft";
                    else
                        className_temp = "htLeft";
                    hot.setCellMeta(i, j, "className", className_temp);
                    cell_className[i][j]=className_temp;
                }
            }
            hot.render();
        }
    });

    //水平居中
    $('#fontCenter').click(function() {
        var selectArea = hot.getSelectedLast();
        var className_temp = "";
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(cell_className[i][j]!=null && cell_className[i][j]!="" && (cell_className[i][j]=="htTop"||cell_className[i][j]=="htMiddle"||cell_className[i][j]=="htBottom"))
                        className_temp = cell_className[i][j]+" htCenter";
                    else
                        className_temp = "htCenter";
                    hot.setCellMeta(i, j, "className", className_temp);
                    cell_className[i][j]=className_temp;
                }
            }
            hot.render();
        }
    });

    //向右对齐
    $('#fontRight').click(function() {
        var selectArea = hot.getSelectedLast();
        var className_temp = "";
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(cell_className[i][j]!=null && cell_className[i][j]!="" && (cell_className[i][j]=="htTop"||cell_className[i][j]=="htMiddle"||cell_className[i][j]=="htBottom"))
                        className_temp = cell_className[i][j]+" htRight";
                    else
                        className_temp = "htRight";
                    hot.setCellMeta(i, j, "className", className_temp);
                    cell_className[i][j]=className_temp;
                }
            }
            hot.render();
        }
    });

    //向右缩进
    $('#rightMove').click(function() {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            var selectData = hot.getSourceData(selectArea[0],selectArea[1],selectArea[2],selectArea[3]);
            var cellData=" ";
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    cellData=" "+selectData[i-parseInt(selectArea[0])][j-parseInt(selectArea[1])];
                    hot.setDataAtCell(i, j, cellData);
                    cellData=" ";
                }
            }
        }
    });

    //向左缩进
    $('#leftMove').click(function() {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            var selectData = hot.getSourceData(selectArea[0],selectArea[1],selectArea[2],selectArea[3]);
            var cellData="";
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    cellData=""+selectData[i-parseInt(selectArea[0])][j-parseInt(selectArea[1])];
                    if(cellData.charAt(0)==' ')
                        hot.setDataAtCell(i, j, cellData.substring(1));
                    cellData="";
                }
            }
        }
    });

    //合并后居中
    $('#mergeAndCenter').click(function() {
        var plugin = hot.getPlugin('mergeCells');
        var range = hot.getSelectedLast();
        if(range!=null){
            if(plugin.isEnabled()) {
                plugin.merge(range[0],range[1],range[2],range[3]);
            }
            for(var i = parseInt(range[0]); i <= parseInt(range[2]); i++) {
                for(var j = parseInt(range[1]); j <= parseInt(range[3]); j++) {
                    cell_className[i][j]="htCenter htMiddle";
                    hot.setCellMeta(i, j, "className", "htCenter htMiddle");
                }
            }
            hot.render();
        }
    });

    //取消合并
    $('#mergeCancel').click(function() {
        var plugin = hot.getPlugin('mergeCells');
        var range = hot.getSelectedLast();
        if(range!=null && plugin.isEnabled()) {
            plugin.unmerge(range[0],range[1],range[2],range[3]);
            for(var i = parseInt(range[0]); i <= parseInt(range[2]); i++) {
                for(var j = parseInt(range[1]); j <= parseInt(range[3]); j++) {
                    font_style[i][j] = "";
                    cell_className[i][j] = "";
                    hot.setCellMeta(i, j, "renderer", "my.none");
                }
            }
            hot.render();
        }
    });

    //单元格类型 常规/数字
    $("#CellType").change(function(){
        var opt=$("#CellType").val();
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(hot.getCellMeta(i,j).readOnly==true){
                        $.messager.alert('提示',"第"+(i+1)+"行，第"+(j+1)+"列的单元格不可编辑！",'info');
                    }else{
                        if(opt=="数字"){
                            var currVal = String(cell_data[i][j]); //取出点击Cell的内容
                            if(/^(\-|\+)?\d+(\.\d+)?$/.test(currVal)){
                                hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: '0.00',culture: 'en-US'}});
                            }
                        }else{
                            hot.setCellMetaObject(i, j, {type:'text'});
                        }
                    }
                }
            }
            hot.render();
        }
    });

    //百分数
    $('#percentBt').click(function () {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(hot.getCellMeta(i,j).readOnly==true){
                        $.messager.alert('提示',"第"+(i+1)+"行，第"+(j+1)+"列的单元格不可编辑！",'info');
                    }else{
                        var currVal = String(cell_data[i][j]); //取出点击Cell的内容
                        if(/^(\-|\+)?\d+(\.\d+)?$/.test(currVal)){
                            hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: '0.[00]%',culture: 'en-US'}});
                        }
                    }
                }
            }
            hot.render();
        }
    });

    //逗号
    $('#commaBt').click(function () {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(hot.getCellMeta(i,j).readOnly==true){
                        $.messager.alert('提示',"第"+(i+1)+"行，第"+(j+1)+"列的单元格不可编辑！",'info');
                    }else{
                        var currVal = String(cell_data[i][j]); //取出点击Cell的内容
                        if(/^(\-|\+)?\d+(\.\d+)?$/.test(currVal)){
                            hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: '0,0.00',culture: 'en-US'}});
                        }
                    }
                }
            }
            hot.render();
        }
    });

    //增加小数位数
    $('#increaseDec').click(function () {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(hot.getCellMeta(i,j).readOnly==true){
                        $.messager.alert('提示',"第"+(i+1)+"行，第"+(j+1)+"列的单元格不可编辑！",'info');
                    }else{
                        var ss = hot.getCell(i, j, true); //取出点击Cell
                        var currVal = $(ss).html(); //取出点击Cell的内容
                        if(/^(\-|\+)?\d+$/.test(currVal)||/^(\-|\+)?[0-9]{1,3}(,[0-9]{3})*$/.test(currVal)){
                            hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: '0,0.0',culture: 'en-US'}});
                        }else if(/^(\-|\+)?\d+\.\d+$/.test(currVal)||/^(\-|\+)?[0-9]{1,3}(,[0-9]{3})*\.\d+$/.test(currVal)){
                            var tempStr = "0,0.";
                            for(var k=currVal.indexOf('.');k<currVal.length;k++){
                                tempStr += "0";
                            }
                            hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: tempStr,culture: 'en-US'}});
                        }
                    }
                }
            }
            hot.render();
        }
    });

    //减小小数位数
    $('#reduceDec').click(function () {
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(hot.getCellMeta(i,j).readOnly==true){
                        $.messager.alert('提示',"第"+(i+1)+"行，第"+(j+1)+"列的单元格不可编辑！",'info');
                    }else{
                        var ss = hot.getCell(i, j, true); //取出点击Cell
                        var currVal = $(ss).html(); //取出点击Cell的内容
                        if(/^(\-|\+)?\d+\.\d{1}$/.test(currVal)||/^(\-|\+)?[0-9]{1,3}(,[0-9]{3})*\.\d{1}$/.test(currVal)){
                            hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: '0,0',culture: 'en-US'}});
                        }else if(/^(\-|\+)?\d+\.\d{2,}$/.test(currVal)||/^(\-|\+)?[0-9]{1,3}(,[0-9]{3})*\.\d{2,}$/.test(currVal)){
                            currVal = currVal.substring(0,currVal.length-1);
                            var tempStr = "0,0.";
                            for(var k=currVal.indexOf('.');k<currVal.length-1;k++){
                                tempStr += "0";
                            }
                            hot.setCellMetaObject(i, j, {type:'numeric',numericFormat:{pattern: tempStr,culture: 'en-US'}});
                        }
                    }
                }
            }
            hot.render();
        }
    });

    //内容确定
    $('#cellContConfm').click(function(){
        formulaBoxSave();
    });

    //内容清空
    $('#cellContdel').click(function(){
        var selectArea = hot.getSelectedLast();
        if(selectArea!=null){
            for(var i = parseInt(selectArea[0]); i <= parseInt(selectArea[2]); i++) {
                for(var j = parseInt(selectArea[1]); j <= parseInt(selectArea[3]); j++) {
                    if(hot.getCellMeta(i,j).readOnly==true){
                        $.messager.alert('提示',"第"+(i+1)+"行，第"+(j+1)+"列的单元格不可编辑！",'info');
                    }else{
                        hot.setDataAtCell(i, j, "");
                    }
                }
            }
        }
        $('#formulabox').val("");
    });

    //报表公式
    $('#formulaTip').click(function(){
        if($('#BBState').val()=="公式"){
            $('#cellPos').hide();
            $('#cellPos_input').hide();
            $('#functionItems').show();
            $('#functionBox').show();
        }
    });

    //公式--详细说明
    $('#FunctionDetail').click(function(){
        var FDvalue=$('#FunctionDetail').val();
        if(FDvalue=="详细说明>>"){
            $('#FunctionDetail').val("简单说明<<");
            $('#functionDescription').show();
            $('#parameterDescription').show();
        }else{
            $('#FunctionDetail').val("详细说明>>");
            $('#functionDescription').hide();
            $('#parameterDescription').hide();
        }
    });

    //公式--退出
    $('#exitFunction').click(function(){
        $('#functionBox').hide();
        $('#functionItems').hide();
        $('#cellPos').show();
        $('#cellPos_input').show();
    });

    //公式--追加函数
    $('#addFunction').click(function(){
        var year_temp = Number($('#BBFunctionYear').textbox('getValue'))+"";
        var month_temp = Number($('#BBFunctionMonth').textbox('getValue'))+"";
        var formulabox=$('#formulabox').val();
        var dateStr = "";
        var str = "";
        var func = $('#functionItems  option:selected').val();
        if(year_temp=="")
            year_temp = '0';
        if(month_temp=="")
            month_temp = '0';
        if(year_temp!='0'||month_temp!='0')
            dateStr = year_temp + "," + month_temp + ",";
        switch(func){
            case 'BBfunction':
                var rptNo_temp = $('#BBNum').textbox('getValue');
                var cellRange_temp = $('#BBCellRange').textbox('getValue');
                if(cellRange_temp==null||cellRange_temp==""){
                    $.messager.alert('提示',"单元格范围为必填项！",'info');
                }else{
                    if(isValidRange(cellRange_temp)){
                        str = "BB("+dateStr;
                        if(rptNo_temp!=null&&rptNo_temp!=''){
                            str = str + rptNo_temp + ",";
                        }
                        str = str + cellRange_temp +")"
                    }else{
                        $.messager.alert('提示',"单元格范围不符合规范！",'info');
                    }
                }
                break;
            case 'KMJEfunction':
                var KMJEClassNum = $('#KMJEClassNum').textbox('getValue');
                var KMJENumObj = $('#KMJENumObj').textbox('getValue');
                var KMJENumFactor = $('#KMJENumFactor').textbox('getValue');
                if(KMJEClassNum==null||KMJEClassNum==""||KMJENumObj==null||KMJENumObj==""){
                    $.messager.alert('提示',"科目编号和取数对象为必填项！",'info');
                }else{
                    str = "KMJE(" + dateStr + KMJEClassNum + "," + KMJENumObj;
                    if(KMJENumFactor!=null&&KMJENumFactor!=''){
                        str = str + "," + KMJENumFactor;
                    }
                    str = str + ")";
                }
                break;
            case 'XMJEfunction':
                var XMJEClassNum = $('#XMJEClassNum').textbox('getValue');
                var XMJESubNum = $('#XMJESubNum').textbox('getValue');
                var XMJENumObj = $('#XMJENumObj').textbox('getValue');
                var XMJENumFactor = $('#XMJENumFactor').textbox('getValue');
                if(XMJEClassNum==null||XMJEClassNum==""||XMJESubNum==null||XMJESubNum==""||XMJENumObj==null||XMJENumObj==""){
                    $.messager.alert('提示',"科目编号、项目编号和取数对象为必填项！",'info');
                }else{
                    str = "XMJE(" + dateStr + XMJEClassNum + "," + XMJESubNum + "," + XMJENumObj;
                    if(XMJENumFactor!=null&&XMJENumFactor!=''){
                        str = str + "," + XMJENumFactor;
                    }
                    str = str + ")";
                }
                break;
            case 'WLJEfunction':
                var WLJEClassNum = $('#WLJEClassNum').textbox('getValue');
                var WLJEUnitNum = $('#WLJEUnitNum').textbox('getValue');
                var WLJENumObj = $('#WLJENumObj').textbox('getValue');
                var WLJENumFactor = $('#WLJENumFactor').textbox('getValue');
                if(WLJEClassNum==null||WLJEClassNum==""||WLJEUnitNum==null||WLJEUnitNum==""||WLJENumObj==null||WLJENumObj==""){
                    $.messager.alert('提示',"科目编号、单位编号和取数对象为必填项！",'info');
                }else{
                    str = "WLJE(" + dateStr + WLJEClassNum + "," + WLJEUnitNum + "," + WLJENumObj;
                    if(WLJENumFactor!=null&&WLJENumFactor!=''){
                        str = str + "," + WLJENumFactor;
                    }
                    str = str + ")";
                }
                break;
        }
        if(str!=""){
            if(formulabox==null||formulabox==""){
                $('#formulabox').val("="+str);
            }else{
                $('#formulabox').val(formulabox + "+" + str);
            }
        }
    });

    //公式--保存函数
    $('#saveFunction').click(function(){
        var str = $('#formulabox').val();
        if($('#formulabox').val() != null || $('#formulabox').val() != ""){
            if(isValidFormula(str)){
                formulaBoxSave();
            }else{
                $.messager.alert('提示',"公式表达定义错误！",'info');
            }
        }

    });

    //公式--输入框加载
    //会计年度输入框
    $('#BBFunctionYear').textbox({
        buttonText:'？',
        value:'0',
        onClickButton:function () {
            $('#dlg_BBFunctionYear').dialog('open');
        }
    });

    //会计月份输入框
    $('#BBFunctionMonth').textbox({
        buttonText:'？',
        value:'0',
        onClickButton:function () {
            $('#dlg_BBFunctionMonth').dialog('open');
        }
    });

    //会计年份输入框--ridio点击事件
    $("input[name='BBYear']").click(function () {
        var yearType = $("input[name='BBYear']:checked").val();
        if(yearType=='relativeType'){
            $('#BBYear').numberspinner('setValue', 0);
        }else if(yearType=='absoluteType'){
            $('#BBYear').numberspinner('setValue', Number(BBYear));
        }
    });

    //报表编号输入框
    $('#BBNum').textbox({
        buttonText:'？',
        onClickButton:function () {
            var year_temp = Number($('#BBFunctionYear').textbox('getValue'))+"";
            var month_temp = Number($('#BBFunctionMonth').textbox('getValue'))+"";
            if(Number(year_temp)>(-10)&&Number(year_temp)<10){
                year_temp = Number(BBYear) + Number(year_temp);
            }
            if(Number(month_temp)<0)
                month_temp=(Number(BBMonth)+12+month_temp)%12;
            if(Number(month_temp)==0)
                month_temp=Number(BBMonth);
            if(Number(month_temp)<10)
                month_temp="0"+month_temp;
            if(Number(year_temp)==Number(BBYear_temp)){//查询当前年份数据
                var opt = $('#RptNosBox_help').datagrid('options');
                opt.url = getRealPath() + '/ReportBase/findRptByYearAndMonth/' + year_temp + "/" + month_temp;
                $('#RptNosBox_help').datagrid('reload');
            }
            $('#dlg_RptNos_help').dialog('open');
        }
    });

    //报表范围输入框
    $('#BBCellRange').textbox({
    });

    //科目金额函数--科目编号输入框
    $('#KMJEClassNum').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#helpWin').dialog('open');
        }
    });

    //科目金额函数--取数对象输入框
    $('#KMJENumObj').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_NumObj_help').dialog('open');
        }
    });

    //科目金额函数--取数条件输入框
    $('#KMJENumFactor').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_NumFactor_help').dialog('open');
        }
    });

    //项目金额函数--科目编号输入框
    $('#XMJEClassNum').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#helpWin').dialog('open');
        }
    });

    //项目金额函数--专项项目编号输入框
    $('#XMJESubNum').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_SubNum_help').dialog('open');
        }
    });

    //项目金额函数--取数对象输入框
    $('#XMJENumObj').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_NumObj_help').dialog('open');
        }
    });

    //项目金额函数--取数条件输入框
    $('#XMJENumFactor').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_NumFactor_help').dialog('open');
        }
    });

    //往来金额函数--科目编号输入框
    $('#WLJEClassNum').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#helpWin').dialog('open');
        }
    });

    //往来金额函数--往来单位编号输入框
    $('#WLJEUnitNum').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_UnitNum_help').dialog('open');
        }
    });

    //往来金额函数--取数对象输入框
    $('#WLJENumObj').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_NumObj_help').dialog('open');
        }
    });

    //往来金额函数--取数条件输入框
    $('#WLJENumFactor').textbox({
        buttonText:'？',
        onClickButton:function () {
            $('#dlg_NumFactor_help').dialog('open');
        }
    });

    //报表编号--帮助窗口数据表格
    $('#RptNosBox_help').datagrid({
        url: getRealPath()+'/ReportBase/findRptByYearAndMonth/'+Number(RptYear)+"/"+Number(RptMonth),
        fitColumns:true,
        striped: true,
        loadMsg: '正在加载中，请稍后',
        singleSelect: true,
        columns: [[
            {
                field: 'rptNo',
                title: '报表编号',
                align:'center',
                width:60,
            },
            {
                field: 'rptName',
                title: '报表名称',
                align:'center',
                width:200,
            }
        ]],
        onDblClickRow : function (rowIndex, rowData) {
            dlg_RptNos_help_ok();
        }

    });

    //专项项目编号--帮助窗口数据表格
    $('#SubNumBox_help').datagrid({
        url: getRealPath()+'/ReportBase/findAllHszd',
        fitColumns:true,
        striped: true,
        loadMsg: '正在加载中，请稍后',
        singleSelect: true,
        columns: [[
            {
                field: 'spNo',
                title: '项目编号',
                align:'center',
                width:60,
            },
            {
                field: 'spName',
                title: '项目名称',
                align:'center',
                width:200,
            }
        ]],
        onDblClickRow : function (rowIndex, rowData) {
            dlg_SubNum_help_ok();
        }
    });

    //往来单位编号--帮助窗口数据表格
    $('#UnitNumBox_help').datagrid({
        url: getRealPath()+'/ReportBase/findAllWldw',
        fitColumns:true,
        striped: true,
        loadMsg: '正在加载中，请稍后',
        singleSelect: true,
        columns: [[
            {
                field: 'companyNo',
                title: '单位编号',
                align:'center',
                width:60,
            },
            {
                field: 'companyName',
                title: '单位名称',
                align:'center',
                width:200,
            }
        ]],
        onDblClickRow : function (rowIndex, rowData) {
            dlg_UnitNum_help_ok();
        }
    });

    //取数对象--帮助窗口数据表格
    $('#NumObjBox_help').datagrid({
        singleSelect : true,
        data: [
            {numObj:'NCJF 年初借方 去年年末的的借方余额'},
            {numObj:'NCDF 年初贷方 去年年末的的贷方余额'},
            {numObj:'JFLJ 借方累计 本年从第一个会计月份开始到本月份(包括本月份)所有借方发生之和'},
            {numObj:'DFLJ 贷方累计 本年从第一个会计月份开始到本月份(包括本月份)所有贷方发生之和'},
            {numObj:'YCJF 月初借方 上一个会计月份的月末借方余额'},
            {numObj:'YCDF 月初贷方 上一个会计月份的月末贷方余额'},
            {numObj:'JFFS 借方发生 本会计月份的借方发生'},
            {numObj:'DFFS 贷方发生 本会计月份的贷方发生'},
            {numObj:'JFYE 借方余额 本会计月份的借方余额'},
            {numObj:'DFYE 贷方余额 本会计月份的贷方余额'},
            {numObj:'KMMC 科目名称'},//科目金额函数 项目金额函数 往来金额函数
            {numObj:'XMMC 项目名称'},//项目金额函数
            {numObj:'WLMC 往来名称'},//往来金额函数
            {numObj:'BNJD 本年借贷差额'},
            {numObj:'BNDJ 本年贷借差额'},
            {numObj:'BYJD 本月借贷差额'},
            {numObj:'BYDJ 本月贷借差额'},
            {numObj:'NJFX 年初借方分析余额'},
            {numObj:'NDFX 年初贷方分析余额'},
            {numObj:'YJFX 月初借方分析余额'},
            {numObj:'YDFX 月初贷方分析余额'}
        ],
        columns: [[
            {
                field: 'numObj',
                title: '取数对象'
            }
        ]],
        onDblClickRow:function (index, row) {
            dlg_NumObj_help_ok();
        }
    });

    //取数条件--帮助窗口数据表格
    $('#NumFactorBox_help').datagrid({
        singleSelect : true,
        onClickCell: onClickCell,
        data: [
            {left:'',compareObj:'',compareChar:'',compareNum:'',right:'',connectChar:''}
        ],
        columns: [[
            {field: 'left',title: '左(',width:40,
                editor:{
                    type:'combobox',
                    options:{
                        editable:false,
                        panelMaxHeight:150,
                        valueField: 'value',
                        textField: 'label',
                        data: [{label: '　',value: ''},{label: '(',value: '('}]
                    }
            }},
            {field: 'compareObj',title: '比较项目',width:130,
                editor:{
                    type:'combobox',
                    options:{
                        editable:false,
                        panelMaxHeight:150,
                        valueField: 'value',
                        textField: 'label',
                        data: [
                            {label: '科目编号',value: '科目编号(KMBH)'},
                            {label: '科目级数',value: '科目级数(KMJS)'},
                            {label: '科目明细',value: '科目明细(KMMX)'},//科目金额函数
                            {label: '项目编号',value: '项目编号(XMBH)'},//项目金额函数
                            {label: '项目级数',value: '项目级数(XMJS)'},//项目金额函数
                            {label: '往来类别',value: '往来类别(WLLB)'},//往来金额函数
                            {label: '往来编号',value: '往来编号(WLBH)'},//往来金额函数
                            {label: '借方余额',value: '借方余额(JFYE)'},
                            {label: '贷方余额',value: '贷方余额(DFYE)'},
                            {label: '借方发生',value: '借方发生(JFFS)'},
                            {label: '贷方发生',value: '贷方发生(DFFS)'},
                            {label: '年初借方',value: '年初借方(NCJF)'},
                            {label: '年初贷方',value: '年初贷方(NCDF)'},
                            {label: '借方累计',value: '借方累计(JFLJ)'},
                            {label: '贷方累计',value: '贷方累计(DFLJ)'},
                            {label: '月初借方',value: '月初借方(YCJF)'},
                            {label: '月初贷方',value: '月初贷方(YCDF)'}
                        ]
                    }
                }},
            {field: 'compareChar',title: '比较符',width:45,
                editor:{
                    type:'combobox',
                    options:{
                        editable:false,
                        panelMaxHeight:150,
                        valueField: 'value',
                        textField: 'label',
                        data: [
                            {label: '　',value: ''},
                            {label: '=',value: '='},
                            {label: '>',value: '>'},
                            {label: '>=',value: '>='},
                            {label: '<',value: '<'},
                            {label: '<=',value: '<='},
                            {label: '<>',value: '<>'},
                            {label: 'LIKE',value: 'like'},
                            {label: '包含',value: 'contain'}
                        ]
                    }
                }},
            {field: 'compareNum',title: '比较值',width:130,editor:'text'},
            {field: 'right',title: '右)',width:40,
                editor:{
                    type:'combobox',
                    options:{
                        editable:false,
                        panelMaxHeight:150,
                        valueField: 'value',
                        textField: 'label',
                        data: [
                            {label: '　',value: ''},
                            {label: ')',value: ')'}
                        ]
                    }
                }},
            {field: 'connectChar',title: '连接符',width:50,
                editor:{
                    type:'combobox',
                    options:{
                        editable:false,
                        panelMaxHeight:150,
                        valueField: 'value',
                        textField: 'label',
                        data: [
                            {label: '　',value: ''},
                            {label: '并且',value: '并且'},
                            {label: '或者',value: '或者'}
                        ]
                    }
                }
            }
        ]]
    });

    //科目编号-帮助窗口
    dealSubStru();//保存科目级数
    dealPrecisions();
    showCaption(upper_No_,level_.toString());

    obj = {
        //上级点击事件
        uperLevel_click : function () {
            if(level_==1){
                $('#Text2').textbox('setValue','');
                return;
            }
            level_--;
            upper_No_=getCatNo(upper_No_,level_-1);
            upper_name = upper_name.substring(0,upper_name.lastIndexOf('/'));
            $('#Text2').textbox('setValue',upper_name);
            searchArr = [];
            searchIndex = 0;
            searchStr = "";
            showCaption(upper_No_,level_);
        },
        //下级点击事件
        nextLevel_click : function () {
            var row = $('#itemTable').datagrid('getSelected');
            if(row==null){return;}//判断是否选中行
            level_++;
            upper_No_ = getCatNo(row.itemNo,level_-1); //获取选中的分类编号
            upper_name = upper_name + "/" + row.itemName;
            $('#Text2').textbox('setValue',upper_name);
            searchArr = [];
            searchIndex = 0;
            searchStr = "";
            showCaption(upper_No_,level_);
        },


    }

    //科目编号--帮助窗口
    $('#helpWin').window({
        width: 480,
        height: 500,
        title: '帮助-科目编号',
        collapsible: false,
        minimizable: false,
        closed: true,
        resizable: true,
        maximizable: false,
        iconCls:'icon-help'
    });

    //科目编号--帮助窗口数据表格
    $('#itemTable').datagrid({
        width: '100%',
        height: '100%',
        singleSelect : true,
        columns: [[
            {
                field: 'itemNo',
                title: '编号',
                width: 120,
                formatter: function (value,row,index) {
                    var length = getNumLength(level_);
                    var no = value.substring(0,length);
                    return no;
                }
            },
            {
                field: 'itemName',
                title: '名称',
                width: 184
            },
            {
                field: 'finLevel',
                title: '明细否',
                width: 80,
                formatter: function (value,row,index) {
                    switch(value){
                        case 1: return "是";
                        case 0: return "否";
                    }
                }
            },
            {
                field: 'item',
                title: '级数',
                width: 80
            },
        ]],
        toolbar: '#tb_helpWin',
    });

    //公式--函数选择事件
    $('#functionItems').change(function(){
        var func = $('#functionItems  option:selected').val();
        switch(func){
            case 'BBfunction':
                $('.BBview').show();
                $('.KMJEview').hide();
                $('.XMJEview').hide();
                $('.WLJEview').hide();
                break;
            case 'KMJEfunction':
                $('.KMJEview').show();
                $('.BBview').hide();
                $('.XMJEview').hide();
                $('.WLJEview').hide();
                break;
            case 'XMJEfunction':
                $('.XMJEview').show();
                $('.KMJEview').hide();
                $('.BBview').hide();
                $('.WLJEview').hide();
                break;
            case 'WLJEfunction':
                $('.WLJEview').show();
                $('.KMJEview').hide();
                $('.XMJEview').hide();
                $('.BBview').hide();
                break;
        }
        $('#dlg_BBFunctionYear').dialog('close');
        $('#dlg_BBFunctionMonth').dialog('close');
        $('#dlg_RptNos_help').dialog('close');
        $('#dlg_SubNum_help').dialog('close');
        $('#dlg_UnitNum_help').dialog('close');
        $('#helpWin').window('close');
        searchArr = [];
        searchIndex = 0;
        searchStr = "";
    });

    //公式--运算符选择事件
    $('#operatorItems').change(function(){
        var operatorName = $('#operatorItems option:selected').val();
        var operator = "";
        var formulabox=$('#formulabox').val();
        switch(operatorName){
            case 'BBsum':
                operator = 'SUM(';
                break;
            case 'BBadd':
                operator = '+';
                break;
            case 'BBminus':
                operator = '-';
                break;
            case 'BBmultiply':
                operator = '*';
                break;
            case 'BBdivide':
                operator = '/';
                break;
            case 'BBleft':
                operator = '(';
                break;
            case 'BBright':
                operator = ')';
                break;
        }
        if(operatorName=="BBsum"){
            if(formulabox==null||formulabox==""){
                $('#formulabox').val("="+operator);
            }else{
                $('#formulabox').val(formulabox+"+"+operator);
            }
        }else{
            $('#formulabox').val(formulabox+operator);
        }
        $('#operatorItems').val('err');
    });

    //状态切换 数据/公式
    $("#BBState").change(function(){
        var opt=$("#BBState").val();
        if(opt=="公式"){
            for (var i=titleRows;i<cell_type.length;i++) {
                for(var j=0;j<cell_type[i].length;j++){
                    if(i<(titleRows+headRows)){
                        cell_formula[i][j]=cell_data[i][j];
                        continue;
                    }
                    if(cell_data[i][j]!=null&&cell_data[i][j]!=""&&cell_type[i][j]=="project")
                        cell_formula[i][j]=cell_data[i][j];
                }
            }
            hot.loadData(cell_formula);
        }else{
            for (var i=titleRows;i<cell_type.length;i++) {
                for(var j=0;j<cell_type[i].length;j++){
                    if(i<(titleRows+headRows)){
                        cell_formula[i][j]="";
                        continue;
                    }
                    if(cell_type[i][j]=="project")
                        cell_formula[i][j]="";
                }
            }
            hot.loadData(cell_data);
            $('#functionBox').hide();
            $('#functionItems').hide();
            $('#cellPos').show();
            $('#cellPos_input').show();
        }
        hot.setCellMeta(0,0,'renderer','my.title');
        //单元格字体样式初始化
        if(font_style!=null){
            for (var i=0;i<font_style.length;i++) {
                for(var j=0;j<font_style[i].length;j++){
                    if(font_style[i][j]!=null&&font_style[i][j]!=""){
                        switch(font_style[i][j]){
                            case "bold":
                                hot.setCellMeta(i, j, "renderer", "my.textBold");
                                break;
                            case "italic":
                                hot.setCellMeta(i, j, "renderer", "my.textItalic");
                                break;
                            case "underline":
                                hot.setCellMeta(i, j, "renderer", "my.textUnderline");
                                break;
                        }
                    }
                    if(cell_className[i][j]!=null&&cell_className[i][j]!=""){
                        hot.setCellMeta(i, j, "className", cell_className[i][j]);
                    }
                }
            }
        }
        hot.render();
    });

    //转出事件
    $('#excelOutput').click(function () {
        if(BBNo==null||BBNo=='')
            $.messager.alert('警告操作！', '请先保存报表！', 'warning');
        else{
            var url = getRealPath()+ "/ReportBase/ExportRptExcel/"+BBDate+"/"+BBNo;
            window.open(url,"_blank");
        }
    });

    //计算报表按钮点击事件
    $('#calculateRp').click(function () {
        var subStr=new RegExp('[,，]','g');//创建正则表达式对象,全局
        //字符串前带有空格加上 "`" 字符
        var cell_data_temp=[].concat(cell_data);//深拷贝 不拷贝地址
        for (var i=0;i<cell_data_temp.length;i++) {
            for(var j=0;j<cell_data_temp[i].length;j++){
                if(cell_data_temp[i][j]!=null&&cell_data_temp[i][j]!=""){
                    if(cell_data_temp[i][j].charAt(0)==' ')
                        cell_data_temp[i][j]="`"+cell_data_temp[i][j];
                    cell_data_temp[i][j]=cell_data_temp[i][j].replace(subStr,"#1");//把'，,'替换为#1
                }
            }
        }
        //把公式数组的'，,'替换为#1
        var cell_formula_temp=[].concat(cell_formula);//深拷贝 不拷贝地址
        for (var i=0;i<cell_formula_temp.length;i++) {
            for(var j=0;j<cell_formula_temp[i].length;j++){
                if(cell_formula_temp[i][j]!=null&&cell_formula_temp[i][j]!="")
                    cell_formula_temp[i][j]=cell_formula_temp[i][j].replace(subStr,"#1");//把'，,'替换为#1
            }
        }
        var ii = layer.load(2);
        var data = {
            "cell_data":cell_data_temp,
            "cell_formula":cell_formula_temp,
            "cell_type":cell_type,
            "formula_range":formula_range,
            "rptNo":BBNo,
            "rptDate":BBDate,
            "titleRules":titleRows,
            "headRules":headRows
        };
        $.ajax({
            type: 'POST',
            url: getRealPath() + '/ReportBase/calculateFormula',
            traditional: true,//传递数组需要设置为true
            data: data,
            success: function (result) {
                for (var i=titleRows;i<cell_type.length;i++) {
                    for(var j=0;j<cell_type[i].length;j++){
                        if(i<(titleRows+headRows)){
                            cell_formula[i][j]="";
                            continue;
                        }
                        if(cell_type[i][j]=="project")
                            cell_formula[i][j]="";
                    }
                }
                cell_data=result.extend.resultData;
                hot.loadData(cell_data);
                hot.setCellMeta(0,0,'renderer','my.title');
                //单元格字体样式初始化
                if(font_style!=null){
                    for (var i=0;i<font_style.length;i++) {
                        for(var j=0;j<font_style[i].length;j++){
                            if(font_style[i][j]!=null&&font_style[i][j]!=""){
                                switch(font_style[i][j]){
                                    case "bold":
                                        hot.setCellMeta(i, j, "renderer", "my.textBold");
                                        break;
                                    case "italic":
                                        hot.setCellMeta(i, j, "renderer", "my.textItalic");
                                        break;
                                    case "underline":
                                        hot.setCellMeta(i, j, "renderer", "my.textUnderline");
                                        break;
                                }
                            }
                            if(cell_className[i][j]!=null&&cell_className[i][j]!=""){
                                hot.setCellMeta(i, j, "className", cell_className[i][j]);
                            }
                        }
                    }
                }
                hot.render();
                set_select_checked('BBState', "数据");
                $('#functionBox').hide();
                $('#functionItems').hide();
                $('#cellPos').show();
                $('#cellPos_input').show();
                layer.close(ii);
            }
        });
    });

    //报表初始化
    function rptInit() {
        var mergeCells = [{row: 0, col: 0, rowspan: 1, colspan: Number(cols)}];
        for (var i=0;i<cell_merge.length;i++) {
            var mergeCell_temp={
                row: cell_merge[i][0],
                col: cell_merge[i][1],
                rowspan: (cell_merge[i][2]-cell_merge[i][0]+1),
                colspan: (cell_merge[i][3]-cell_merge[i][1]+1)
            };
            mergeCells.push(mergeCell_temp);
        }
        //初始化边框
        cell_border = [];
        for (var i=titleRows;i<rows-endRows;i++) {
            for (var j=0;j<cols;j++) {
                var cell_border_temp={
                    row:i,col:j,
                    left: {width: 1, color: 'black'},
                    right: {width: 1, color: 'black'},
                    top: {width: 1, color: 'black'},
                    bottom: {width: 1, color: 'black'}
                };
                cell_border.push(cell_border_temp);
            }
        }
        hot = new Handsontable(container, {
            data: cell_data, //导入数据
            rowHeaders: true, //行头123
            colHeaders: true, //列头ABC
            autoColumnSize: false,//自适应列大小
            autoRowSize: true, //自动行高
            copyPaste: true,//允许复制粘贴
            wordWrap:false,//单元格内容自动换行
            minCols:3,//最小列数
            minRows:5,//最小行数
            trimWhitespace:false,//设置不默认去掉空格
            outsideClickDeselects: false, //设置外部点击是否影响内容焦点
            manualColumnResize: true, //行列可拉缩
            manualRowResize: true,//行列可拉缩
            mergeCells: true, //合并单元格
            contextMenu: true, //使用菜单
            colWidths: cell_width, //定义列宽度
            rowHeights: cell_height, //定义行高度
            formulas: true,//允许公式（需要提前导入js）
            customBorders: cell_border,//定义边框
            //设置只读
            cells: function (row, col, prop) {
                var cellProperties = {};
                var visualRowIndex = this.instance.toVisualRow(row);
                var visualColIndex = this.instance.toVisualColumn(col);
                if (visualRowIndex >= 0 && visualRowIndex < titleRows) {
                    //cellProperties.readOnly = true;
                }
                return cellProperties;
            },
            mergeCells: mergeCells,
        });
        Handsontable.hooks.add('afterOnCellMouseDown', clickCellCallBack, hot);//单击单元格事件
        Handsontable.hooks.add('afterRowResize', rowResizeCallBack, hot);//行高修改事件
        Handsontable.hooks.add('afterColumnResize', columnResizeCallBack, hot);//列宽修改事件
        Handsontable.hooks.add('afterMergeCells', mergeCellsCallBack, hot);//合并单元格事件
        Handsontable.hooks.add('beforeUnmergeCells', beforeUnmergeCellsCallBack, hot);//取消合并单元格事件
        Handsontable.hooks.add('afterCreateRow', afterCreateRowCallBack, hot);//创建行事件
        Handsontable.hooks.add('afterCreateCol', afterCreateColCallBack, hot);//创建列事件
        Handsontable.hooks.add('afterRemoveRow', afterRemoveRowCallBack, hot);//删除行事件
        Handsontable.hooks.add('afterRemoveCol', afterRemoveColCallBack, hot);//删除列事件
        Handsontable.hooks.add('beforeRemoveRow', beforeRemoveRowCallBack, hot);//删除行前事件
        Handsontable.hooks.add('beforeRemoveCol', beforeRemoveColCallBack, hot);//删除列前事件
        hot.setCellMeta(0,0,'renderer','my.title');//标题渲染
        //单元格字体样式初始化
        if(font_style!=null){
            for (var i=0;i<font_style.length;i++) {
                for(var j=0;j<font_style[i].length;j++){
                    if(font_style[i][j]!=null&&font_style[i][j]!=""){
                        switch(font_style[i][j]){
                            case "bold":
                                hot.setCellMeta(i, j, "renderer", "my.textBold");
                                break;
                            case "italic":
                                hot.setCellMeta(i, j, "renderer", "my.textItalic");
                                break;
                            case "underline":
                                hot.setCellMeta(i, j, "renderer", "my.textUnderline");
                                break;
                        }
                    }
                    if(cell_className[i][j]!=null&&cell_className[i][j]!=""){
                        hot.setCellMeta(i, j, "className", cell_className[i][j]);
                    }
                }
            }
        }
        hot.render();
    }

    //创建行
    function createRow(index,amount,isProp) {
        if(index<=Number(titleRows)){
            titleRows+=amount;
        }else if(index<=Number((titleRows+headRows))){
            headRows+=amount;
        }else if(index<=Number((titleRows+headRows+contentRows))){
            contentRows+=amount;
        }else{
            endRows+=amount;
        }
        rows+=amount;
        var row_temp=[];
        for (var j = 0; j < Number(cols); j++) {
            row_temp.push('');
        }
        for(var i=index;i<(index+amount);i++){
            if(isProp==true){
                cell_data.splice(i,0,row_temp);
                cell_formula.splice(i,0,row_temp);
            }else{
                if($('#BBState').val()=="公式"){
                    cell_data.splice(i,0,row_temp);
                }else {
                    cell_formula.splice(i,0,row_temp);
                }
            }
            font_style.splice(i,0,row_temp);
            cell_className.splice(i, 0, row_temp);
            cell_type.splice(i, 0, row_temp);
            formula_range.splice(i, 0, row_temp);
            cell_height.splice(i, 0, 20);
        }
        //修改合并单元格
        for (var i=0;i<cell_merge.length;i++) {
            if (index >= Number(cell_merge[i][0]) && index <= Number(cell_merge[i][2])) {
                cell_merge[i][2]=Number(cell_merge[i][2])+amount;
            }
        }
    }

    //创建列
    function createCol(index,amount,isProp) {
        //alert("CreateCol__"+index+"_"+amount);
        cols+=amount;
        for(var i=index;i<(index+amount);i++){
            for (var j = 0; j < Number(rows); j++) {
                if(isProp==true){
                    cell_data[j].splice(i,0,'');
                    cell_formula[j].splice(i,0,'');
                }else{
                    if($('#BBState').val()=="公式"){
                        cell_data[j].splice(i,0,'');
                    }else {
                        cell_formula[j].splice(i,0,'');
                    }
                }
                font_style[j].splice(i,0,'');
                cell_className[j].splice(i, 0, '');
                cell_type[j].splice(i, 0, '');
                formula_range[j].splice(i, 0, '');
            }
            cell_width.splice(i, 0, 150);
        }
        //alert(cell_data[0].length + "_" + cell_formula[0].length + "_" + font_style[0].length + "_" + cell_className[0].length + "_" + cell_width.length);
        for (var i=0;i<cell_merge.length;i++) {
            if (index >= Number(cell_merge[i][1]) && index <= Number(cell_merge[i][3])) {
                cell_merge[i][3]=Number(cell_merge[i][3])+amount;
            }
        }
    }

    //删除行
    function deleteRow(index,amount,isProp) {
        //alert("RemoveRow__"+index+"_"+amount);
        var rows_temp=[];
        for(var i=0;i<rows;i++) {
            if(i<Number(titleRows)){
                rows_temp.push(1);
            }else if(i<Number((titleRows+headRows))){
                rows_temp.push(2);
            }else if(i<Number((titleRows+headRows+contentRows))){
                rows_temp.push(3);
            }else{
                rows_temp.push(4);
            }
        }
        rows_temp.splice(index,amount);
        var titleRows_temp=0,headRows_temp=0,contentRows_temp=0,endRows_temp=0;
        for(var i=0;i<rows_temp.length;i++) {
            switch(rows_temp[i]){
                case 1:
                    titleRows_temp++;
                    break;
                case 2:
                    headRows_temp++;
                    break;
                case 3:
                    contentRows_temp++;
                    break;
                case 4:
                    endRows_temp++;
                    break;
            }
        }
        titleRows = titleRows_temp;
        headRows = headRows_temp;
        contentRows = contentRows_temp;
        endRows = endRows_temp;
        rows-=amount;
        if(isProp==true){
            cell_data.splice(index,amount);
            cell_formula.splice(index,amount);
        }else{
            if($('#BBState').val()=="公式"){
                cell_data.splice(index,amount);
            }else {
                cell_formula.splice(index,amount);
            }
        }
        font_style.splice(index,amount);
        cell_className.splice(index,amount);
        cell_type.splice(index,amount);
        formula_range.splice(index,amount);
        cell_height.splice(index, amount);
        for (var i=0;i<cell_merge.length;i++) {
            if (index >= Number(cell_merge[i][0]) && index <= Number(cell_merge[i][2])) {
                cell_merge[i][2]=Number(cell_merge[i][2])-amount;
            }
        }
    }

    //删除列
    function deleteCol(index,amount,isProp) {
        //alert("RemoveCol__"+index+"_"+amount);
        cols-=amount;
        for (var j = 0; j < Number(rows); j++) {
            if(isProp==true){
                cell_data[j].splice(index,amount);
                cell_formula[j].splice(index,amount);
            }else{
                if($('#BBState').val()=="公式"){
                    cell_data[j].splice(index,amount);
                }else {
                    cell_formula[j].splice(index,amount);
                }
            }
            font_style[j].splice(index,amount);
            cell_className[j].splice(index,amount);
            cell_type[j].splice(index,amount);
            formula_range[j].splice(index,amount);
        }
        cell_width.splice(index,amount);
        for (var i=0;i<cell_merge.length;i++) {
            if (index >= Number(cell_merge[i][1]) && index <= Number(cell_merge[i][3])) {
                cell_merge[i][3]=Number(cell_merge[i][3])-amount;
            }
        }
    }
});

//判断字符串函数
function isString(s) {
    var flag = true;
    if(s == null || s == "")
        flag = false;
    for(var i = 0; i < s.length; i++) {
        if(!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
            flag = false;
            break;
        }
    }
    return flag;
}

//缩进空格字符转换函数
function cellTrim(s) {
    var spaceNum = 0;
    var resultS = "";
    if(s == null || s == "")
        return s;
    for(var i = 0; i < s.length;) {
        if(s.substring(i,i+6)=="&nbsp;") {
            spaceNum++;
            i+=6;
        }else{
            resultS = s.substring(i,s.length);
            break;
        }
    }
    for(var i = 0; i < spaceNum;i++) {
        resultS = " "+resultS;
    }
    return resultS;
}

//会计年份帮助窗口--确认按钮
function BBFunctionYear_ok(){
    var year_temp=$('#BBYear').spinner('getValue');
    $('#BBFunctionYear').textbox('setValue',year_temp);
    $('#dlg_BBFunctionYear').dialog('close');

}
//会计月份帮助窗口--确认按钮
function BBFunctionMonth_ok(){
    var month_temp=$('#BBMonth').spinner('getValue');
    $('#BBFunctionMonth').textbox('setValue',month_temp);
    $('#dlg_BBFunctionMonth').dialog('close');
}

//帮助框--查询事件
function dg_search(dgId,boxId) {
    var str = $(boxId).textbox('getValue');
    if(str==null||str==''){
        return;
    }else{
        if(str==searchStr){
            if(searchArr.length>0){
                if(searchIndex+1<searchArr.length){
                    searchIndex++;
                }else{
                    searchIndex = 0;
                }
                $(dgId).datagrid('selectRow', searchArr[searchIndex]);
            }
        }else{
            searchArr = [];
            searchIndex = 0;
            searchStr = str.toString();
            var rows = $(dgId).datagrid('getRows');
            for(var i = 0;i<rows.length;i++){
                var noStr='';
                var nameStr='';
                switch(dgId){
                    case '#RptNosBox_help':
                        noStr = rows[i].rptNo;
                        nameStr = rows[i].rptName;
                        break;
                    case '#SubNumBox_help':
                        noStr = rows[i].spNo;
                        nameStr = rows[i].spName;
                        break;
                    case '#UnitNumBox_help':
                        noStr = rows[i].companyNo;
                        nameStr = rows[i].companyName;
                        break;
                    case '#itemTable':
                        var end = getNumLength(level_);
                        noStr = rows[i].itemNo.substring(0,end);
                        nameStr = rows[i].itemName;
                        break;
                }
                if(noStr.indexOf(str)!=-1||nameStr.indexOf(str)!=-1){
                    searchArr.push(i);
                }
            }
            if(searchArr.length>0){
                $(dgId).datagrid('selectRow', searchArr[0]);
            }
        }
    }
}

//报表编号帮助窗口--确认事件
function dlg_RptNos_help_ok(){
    var row = $('#RptNosBox_help').datagrid("getSelected");
    if(row==null){
        $.messager.alert('警告操作！', '请选择报表！', 'warning');
    }else{
        $("#BBNum").textbox('setValue',row.rptNo);
        $('#dlg_RptNos_help').dialog('close');
    }
}

//专项项目编号帮助窗口--确认事件
function dlg_SubNum_help_ok(){
    var row = $('#SubNumBox_help').datagrid("getSelected");
    if(row==null){
        $.messager.alert('警告操作！', '请选择项目！', 'warning');
    }else{
        $("#XMJESubNum").textbox('setValue',row.spNo);
        $('#dlg_SubNum_help').dialog('close');
    }
}

//往来编号帮助窗口--确认事件
function dlg_UnitNum_help_ok(){
    var row = $('#UnitNumBox_help').datagrid("getSelected");
    if(row==null){
        $.messager.alert('警告操作！', '请选择单位！', 'warning');
    }else{
        $("#WLJEUnitNum").textbox('setValue',row.companyNo);
        $('#dlg_UnitNum_help').dialog('close');
    }
}


//科目编号帮助窗口--选定科目时事件
function selectClick() {
    var row = $('#itemTable').datagrid("getSelected");
    if(row==null){
        $.messager.alert('警告操作！', '请选择科目！', 'warning');
    }else{
        itemName = row.itemName;
        $("#itemNo_input").val(row.itemNo);
        var func = $('#functionItems  option:selected').val();
        var length = getNumLength(level_);
        var no = row.itemNo.substring(0,length);
        switch(func){
            case 'KMJEfunction':
                $('#KMJEClassNum').textbox('setValue',no);
                break;
            case 'XMJEfunction':
                $('#XMJEClassNum').textbox('setValue',no);
                break;
            case 'WLJEfunction':
                $('#WLJEClassNum').textbox('setValue',no);
                break;
        }
        $('#helpWin').window('close');
    }

}
//获取科目级数
function dealSubStru() {
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/getSubjectStructure/',
        contentType: 'application/json',
        success: function (result) {
            var stru = result.split("");
            for(var i=0;i<stru.length;i++){
                sub_stru_[i]=stru[i];
            }
            if(result=="0"){
                alert("请先设置科目结构");
                //TODO：关闭标签页
            }
        },
    });
}

//获取金额和数量的小数精度
function dealPrecisions() {
    var money_prec = 2;
    var quan_prec = 2;
    precisions_.push(money_prec);
    precisions_.push(quan_prec);
}

//显示当前级的科目
function showCaption(num, level){
    var new_num = "";
    if(num==""){new_num="0";}
    else{new_num=num;}
    $.ajax({
        type: 'POST',
        url: getRealPath() + '/CaptionOfAccount/queryCaptionOfAccountByLevel/' + new_num + '/' + level,
        contentType: 'application/json',
        success: function (result) {
            if(result.code == 100) {
                var data = result.extend.lskmzdList;
                $('#itemTable').datagrid('loadData', data);
            }
        }
    });
}

//计算当前级别科目编号长度
function getNumLength(level) {
    if(level==0){
        return 0;
    }
    var length = 0;
    for(var i=0;i<level;i++){
        length += parseInt(sub_stru_[i]);
    }
    return length;
}

//计算上级科目编号
function getCatNo(num,level){
    var length = getNumLength(level);
    var no = num.substring(0,length);
    return no;
}

//取数对象确认按钮
function dlg_NumObj_help_ok(){
    var func = $('#functionItems  option:selected').val();
    var row_temp = $('#NumObjBox_help').datagrid('getSelected');
    if(row_temp!=null){
        switch(func){
            case 'KMJEfunction':
                $('#KMJENumObj').textbox('setValue',row_temp.numObj.substring(0,4));
                break;
            case 'XMJEfunction':
                $('#XMJENumObj').textbox('setValue',row_temp.numObj.substring(0,4));
                break;
            case 'WLJEfunction':
                $('#WLJENumObj').textbox('setValue',row_temp.numObj.substring(0,4));
                break;
        }
    }
    $('#dlg_NumObj_help').dialog('close');
}

//取数条件帮助框--easyUi单元格编辑--开始
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#NumFactorBox_help').datagrid('validateRow', editIndex)){
        $('#NumFactorBox_help').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
function onClickCell(index, field){
    if (endEditing()){
        $('#NumFactorBox_help').datagrid('selectRow', index)
            .datagrid('editCell', {index:index,field:field});
        editIndex = index;
    }
}
//easyUi单元格编辑--结束

//取数条件帮助框--插入行方法
function insertRow(){
    endEditing();
    var row = $('#NumFactorBox_help').datagrid('getSelected');
    if (row){
        var index = $('#NumFactorBox_help').datagrid('getRowIndex', row);
    } else {
        index = 0;
    }
    $('#NumFactorBox_help').datagrid('insertRow', {
        index: index,
        row:{}
    });
}

//取数条件帮助框--追加行方法
function appendRow(){
    endEditing();
    $('#NumFactorBox_help').datagrid('appendRow',{});
}

//取数条件帮助框--删除行方法
function deleteRow(){
    endEditing();
    var row = $('#NumFactorBox_help').datagrid('getSelected');
    var index;
    if (row){
        index = $('#NumFactorBox_help').datagrid('getRowIndex', row);
    } else {
        index = 0;
    }
    $('#NumFactorBox_help').datagrid('deleteRow',index);
}

//取数条件帮助框--确认按钮
function NumFactorBox_help_ok() {
    endEditing();
    var rows = $('#NumFactorBox_help').datagrid('getRows');
    if(rows==null||rows.length==0){
        $.messager.alert('警告操作！', '条件为空！', 'warning');
        return;
    }
    var top = 0;//括号配对验证符
    var result = '';
    for(var i=0;i<rows.length;i++){
        var row = rows[i];
        if(row.left!=null&&row.left!=''){
            ++top;
            result += '(';
        }
        if(row.compareObj==null||row.compareObj==''){
            $.messager.alert('警告操作！', '缺少必要的比较项目，请修改！', 'warning');
            return;
        }
        result += row.compareObj.substring(5,9);
        if(row.compareObj!='科目明细(KMMX)'){
            if(row.compareChar==null||row.compareChar==''){
                $.messager.alert('警告操作！', '缺少必要的比较符，请修改！', 'warning');
                return;
            }else{
                if(row.compareChar=="<>"){
                    result +=  "!=";
                }else if(row.compareChar=="like"||row.compareChar=="contain"){
                    result +=  " like ";
                }else{
                    result +=  row.compareChar;
                }
            }
        }
        if(row.compareObj!='科目明细(KMMX)'){
            if(row.compareNum==null||row.compareNum==''){
                $.messager.alert('警告操作！', '缺少必要的比较值，请修改！', 'warning');
                return;
            }else{
                if(row.compareChar=="like"){
                    result = result + "'" + row.compareNum + "%'";
                }else if(row.compareChar=="contain"){
                    result = result + "'%" + row.compareNum + "%'";
                }else{
                    result += row.compareNum;
                }
            }
        }
        if(row.right!=null&&row.right!=''){
            if(top>0){
                --top;
            }else{
                $.messager.alert('警告操作！', '左右括号不匹配，请修改！', 'warning');
                return;
            }
            result += ')';
        }
        if((rows.length-1)>i){
            if(row.connectChar==null||row.connectChar==''){
                $.messager.alert('警告操作！', '缺少必要的连接符，请修改！', 'warning');
                return;
            }else{
                if(row.connectChar=='并且')
                    result += ' and ';
                else if(row.connectChar=='或者')
                    result += ' or ';
            }
        }
    }
    if(top!=0){
        $.messager.alert('警告操作！', '左右括号不匹配，请修改！', 'warning');
        return;
    }
    var func = $('#functionItems  option:selected').val();
    switch(func){
        case 'KMJEfunction':
            $('#KMJENumFactor').textbox('setValue',result);
            break;
        case 'XMJEfunction':
            $('#XMJENumFactor').textbox('setValue',result);
            break;
        case 'WLJEfunction':
            $('#WLJENumFactor').textbox('setValue',result);
            break;
    }
    $('#dlg_NumFactor_help').dialog('close');
}

//判断字符串str是否为 A1、A1:B1型
function isValidRange(str) {
    return /^[A-Z]\d{1,3}(:[A-Z]\d{1,3})?$/.test(str);
}

//判断公式是否合法
function isValidFormula(str) {
    var result = true;
    var top = 0;
    var part1 = /[=+\/*-]{2,}/;//判断是否有连续的运算符
    var part2 = /BB+.*(KMJE+|XMJE+|WLJE+|DATE+)/;//判断报表函数是否与科目金额、往来金额或项目金额函数同时使用
    var part3 = /\)\(/;//判断是否函数中间没有连接符
    if(str.charAt(0)!='=')
        result = false;
    //判断括号是否匹配
    for(var i=0;i<str.length;i++){
        if(str.charAt(i)=='(')
            top++;
        if(str.charAt(i)==')')
            top--;
    }
    if(top!=0)
        result = false;
    if(part1.test(str)||part2.test(str)||part3.test(str))
        result = false;
    return result;
}

function set_select_checked(selectId, checkValue){
    var select = document.getElementById(selectId);

    for (var i = 0; i < select.options.length; i++){
        if (select.options[i].value == checkValue){
            select.options[i].selected = true;
            break;
        }
    }
}

function doPrint() {
    var bdhtml=window.document.body.innerHTML;
    var sprnstr="<!--startprint-->";
    var eprnstr="<!--endprint-->";
    var prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
    var prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
    window.document.body.innerHTML=prnhtml;
    window.print();
    window.document.body.innerHTML=bdhtml;
}

//获取路径
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;
}

