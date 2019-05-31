/**
 * Created by ChenZH on 2018/4/8.
 */
$(function () {
    var level_inputs = new Array($("#level1"),$("#level2"),$("#level3"),$("#level4"),$("#level5"),$("#level6"),$("#level7"));
    setInputs(level_inputs,6);
    getConfigs(level_inputs);

    $('#sub_level').numberbox({
        onChange: function (newValue, oldValue) {
            setInputs(level_inputs,newValue);
        }
    });

});

function dateformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y + '-' + (m<10?('0'+m):m) + '-' +(d<10?('0'+d):d);
}

function dateparser(s){
    if (!s) return new Date();
    //var ss = (s.split('-'));
    var y = parseInt(s.substr(0,4),10);
    var m = parseInt(s.substr(5,2),10);
    var d = parseInt(s.substr(8,2),10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

function setInputs(inputs,num) {
    var disable_inputs = inputs.slice(num);
    var enable_inputs = inputs.slice(0,num);
    for(var i=0;i<disable_inputs.length;i++){
        //alert(disable_inputs[i].tagName);
        disable_inputs[i].numberbox('disable');
        disable_inputs[i].numberbox('clear');
    }
    for(var i=0;i<enable_inputs.length;i++){
        //alert(enable_inputs[i].id);
        if(enable_inputs[i].prop('disabled')){
            enable_inputs[i].numberbox('reset');
        }
        enable_inputs[i].numberbox('enable');
    }
}

function getStructureLevel(){
    var level_inputs = new Array($("#level1"),$("#level2"),$("#level3"),$("#level4"),$("#level5"),$("#level6"),$("#level7"));
    var data = level_inputs[0].prop('value');
    for(var i=1; i<level_inputs.length; i++){
        if(level_inputs[i].prop('value')!=null){
            data += level_inputs[i].prop('value');
        }
    }
    var json = '{"confKey":"sub_stru","confValue":"'+data+'","confNote":"当前科目结构"}';
    return json;
}

function getDate() {
    var input = $("#begin_date");
    var date = input.datebox('getValue').split('-').join('');
    var json = '{"confKey":"begin_date","confValue":"'+date+'","confNote":"财务初始日期"}';
    console.log(json);
    return json;
}

function getData() {
    var data = '[{"confKey":"stru_def","confValue":"true","confNote":"科目结构已定义"},';
    var inputs = new Array($("#quantity_dec"),$("#price_dec"),$("#abs_length"),$("#sub_length"),$("#first_num"));
    var name = new Array("数量小数位数","单价小数位数","摘要名称长度","科目名称长度","凭证首位编号");
    for(var i=0; i<inputs.length; i++){
        data += '{"confKey":"'+inputs[i].prop('id')+'","confValue":"'+inputs[i].prop('value')+'","confNote":"'+name[i]+'"},';
    }
    data += getStructureLevel()+','+getDate()+']';
    return data;
}

function request() {
    if(!$("#form1").form('validate')){
        return $("#form1").form('validate');
    }
    $.ajax({
        type: "post",
        url: getRealPath()+'/OtherEnvironment/setOtherEnvironment',
        contentType: "application/json;charset:utf-8", //必须有
        data: getData(),
        success: function (data) {
            if (data.code == 100) {
                if(data.extend.using_flag) $.messager.alert("提示", "该账套正在使用，仅更新数据长度", "info");
                else $.messager.alert("提示", "恭喜您，信息添加成功", "info");
            }
            else {
                $.messager.alert("提示", "服务器正忙，添加失败", "info");
                return;
            }
        }
    });
}

function getConfigs(level_inputs) {
    $.ajax({
        type: "post",
        url: getRealPath()+'/OtherEnvironment/getConfigs',
        contentType: "application/json;charset:utf-8", //必须有
        data: getData(),
        success: function (data) {
            if (data.code == 100) {
                var result = data.extend;

                if(result.sub_stru) {
                    var length = result.sub_stru.length;
                    $('#sub_level').numberbox('setValue', length);
                    setInputs(level_inputs, length);
                    for (var i = 0; i < length; i++) {
                        level_inputs[i].numberbox('setValue', result.sub_stru[i]);
                    }
                }

                if(result.quantity_dec) $('#quantity_dec').numberbox('setValue', result.quantity_dec);
                if(result.price_dec) $('#price_dec').numberbox('setValue', result.price_dec);
                if(result.abs_length) $('#abs_length').numberbox('setValue', result.abs_length);
                if(result.sub_length) $('#sub_length').numberbox('setValue', result.sub_length);
                if(result.first_num) $('#first_num').textbox('setValue', result.first_num);
                
                if(result.begin_date) {
                    var x = result.begin_date;
                    var date = x.slice(0, 4) + '-' + x.slice(4, 6) + '-' + x.slice(6, 8);
                    $('#begin_date').datebox('setValue', date);
                }
            }
        }
    });
}

// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
//
// }