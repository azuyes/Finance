/**
 * Created by ChenZH on 2018/3/30.
 */
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;

    return basePath ;

}

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
                data += '{"confKey":"'+inputs[i].id+'","confValue":"'+inputs[i].value+'","confNote":"'+inputs[i].name+'"},';
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
            if (data) {
                $.messager.alert("提示", "恭喜您，信息添加成功", "info");
            }
            else {
                $.messager.alert("提示", "添加失败，请重新操作！", "info");
                return;
            }
        }
    });
}
