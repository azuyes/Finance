function dataMove() {
    
    $.messager.progress({
        text: '正在上传中...',
    });
    var path = document.getElementById("txtFile").value;
    document.getElementById("path").value = path;
    $("#importForm").form('submit',{//AJAX方式的提交表单
        
        url:getRealPath()+'/DataManagement/moveData',
        onSubmit : function() {  //提交一个有效并且避免重复提交的表单
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
            }
            return isValid;	// 返回false终止表单提交
        },
        success : function(result) {
            $.messager.progress('close');
            if(result=='true'){
                $.messager.show({
                    title: '提示',
                    msg: "转入成功！",
                });
            }else {
                $.messager.alert({
                    title:'警告',
                    msg:"转入失败！",
                });
            }
        }
    });
    
}

function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/"+ contextPath;
    return basePath ;
}