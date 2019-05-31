/**
 * Created by ChenZH on 2018/3/30.
 */

$(function() {
    getConfigs();
});

// function getRealPath(){
//
//     var localObj = window.location;
//     var contextPath = localObj.pathname.split("/")[1];
//     var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
//
//     return basePath ;
//
// }

//TODO：空项验证提示
function emptyAlert(){
    //alert(name+"内容不能为空，请填写");
    return $("#form1").form('validate');
}

//TODO：提交设置提示
function submitAlert() {

}

function getData(){
    var data = '[';
    var inputs = $("input").not(".textbox-text,.textbox-value");
    for(var i=0; i<inputs.length; i++){
        if(inputs[i].type=="checkbox"){
            if(inputs[i].checked){
                data += '{"confKey":"'+inputs[i].id+'","confValue":"1","confNote":"'+inputs[i].value+'"},';
            }
            else{
                data += '{"confKey":"'+inputs[i].id+'","confValue":"0","confNote":"'+inputs[i].value+'"},';
            }
        }
        else if(inputs[i].type=="radio"){
            if(inputs[i].checked){
                data += '{"confKey":"'+inputs[i].name+'","confValue":"'+inputs[i].id+'","confNote":"'+inputs[i].value+'"},';
            }
            else{
                continue;
            }
        }
        else if(inputs[i].type=="number"||inputs[i].type=="text"){
            data += '{"confKey":"'+inputs[i].id+'","confValue":"'+inputs[i].value+'","confNote":"'+inputs[i].getAttribute("textboxname")+'"},';
        }
        else{
            data += '{"confKey":"'+inputs[i].id+'","confValue":"'+inputs[i].value+'","confNote":"'+inputs[i].name+'"},';
        }
    }
    data = data.slice(0,-1);
    data += ']';
    return data;
}

function getConfigs() {
    $.ajax({
        type: "post",
        url: getRealPath()+'/OtherEnvironment/getConfigs',
        contentType: "application/json;charset:utf-8", //必须有
        data: getData(),
        success: function (data) {
            if (data.code == 100) {
                var result = data.extend;

                if(result.abs_type != null) {
                    if (result.abs_type == 'single_abs') {
                        $('#single_abs').attr('checked', true);
                    }
                    else {
                        $('#multi_abs').attr('checked', true);
                    }
                }

                if(result.paper_size != null) {
                    if (result.paper_size == 'a4') {
                        $('#a4').attr('checked', true);
                    }
                    else {
                        $('#thin').attr('checked', true);
                    }
                }

                if(result.acc_officer != null){
                    if(result.acc_officer == '1')
                        $('#acc_officer').attr('checked',true);
                    else
                        $('#acc_officer').attr('checked',false);
                }
                if(result.auditor != null){
                    if(result.auditor == '1')
                        $('#auditor').attr('checked',true);
                    else
                        $('#auditor').attr('checked',false);
                }
                if(result.touching != null){
                    if(result.touching == '1')
                        $('#touching').attr('checked',true);
                    else
                        $('#touching').attr('checked',false);
                }
                if(result.cashier != null){
                    if(result.cashier == '1')
                        $('#cashier').attr('checked',true);
                    else
                        $('#cashier').attr('checked',false);
                }
                if(result.operator != null){
                    if(result.operator == '1')
                        $('#operator').attr('checked',true);
                    else
                        $('#operator').attr('checked',false);
                }

                if(result.print_row != null) $('#print_row').numberbox('setValue', result.print_row);
                if(result.bill_row != null) $('#bill_row').numberbox('setValue', result.bill_row);
                if(result.table1 != null) $('#table1').textbox('setValue', result.table1);
                if(result.table2 != null) $('#table2').textbox('setValue', result.table2);
                if(result.table3 != null) $('#table3').textbox('setValue', result.table3);
                if(result.table4 != null) $('#table4').textbox('setValue', result.table4);
                if(result.table5 != null) $('#table5').textbox('setValue', result.table5);
                if(result.table6 != null) $('#table6').textbox('setValue', result.table6);
                if(result.acc_manager != null) $('#acc_manager').textbox('setValue', result.acc_manager);
                if(result.unit_name != null) $('#unit_name').textbox('setValue', result.unit_name);
                //set_unit(result.unit_name);
                if(result.unit_number != null) $('#unit_number').textbox('setValue', result.unit_number);
                if(result.superior_unit != null) $('#superior_unit').textbox('setValue', result.superior_unit);
            }
        }
    });
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
                $.messager.alert("提示", "恭喜您，信息添加成功", "info");
                getConfigs();
            }
            else {
                $.messager.alert("提示", "数据库正忙，添加失败", "info");
            }
        }
    });
}
