/**
 * Created by ChenZH on 2018/10/24.
 */
function getRealPath(){

    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + '/' + contextPath;
    return basePath ;
}

function thousandFormatter(value){
    if(value == null || value == 0) return '';
    return (parseFloat(value).toFixed(2) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
}

function  searchItemNoClick() {
    searchHelp1("#itemTable","#searchText_ItemNo","itemNo","itemName");
}

function searchHelp1(eleTable,eleInput,no,name){
    var val = $(eleInput).val();
    var row = $(eleTable).datagrid("getSelected");
    var rowIndex ;
    if (row) {
        rowIndex = $(eleTable).datagrid('getRowIndex', row);
    }else{
        rowIndex = -1;
    }
    var gridData = $(eleTable).datagrid("getData");

    var i = rowIndex +1;
    for (i; i < gridData.total; i++) {
        //console.log(gridData.rows[i][no]);
        if(gridData.rows[i][no].search(val) != -1 || gridData.rows[i][name].search(val) != -1){
            $(eleTable).datagrid("selectRow", i);
            return ;
        }
    }
    if(i == gridData.total ){
        $.messager.alert('提示', '向下没有找到！', 'info');
    }
}

function printPDF(){

    var counts = data_.length;
    var max_size = 4;
    var line_size = 10;//图片行宽
    var line_pix = 25;//canvas像素行宽
    var side_left = 15;
    var side_right = 570;
    console.log(counts);
    //初始化pdf，设置相应格式（单位为pt,导出a4纸的大小）
    var doc = new jsPDF("p", "pt", "a4");
    var imgHeader = null, imgBody = null;
    html2canvas(document.getElementsByClassName('datagrid-btable')[0]).then(function (canvas) {
        html2canvas(document.getElementsByClassName('datagrid-htable')[1]).then(function (canvas_header) {
            line_pix = Math.floor(canvas.height / counts);
            line_size = Math.floor(line_pix * ((side_right - side_left) / canvas.width));
            console.log('line_pix:' + line_pix);
            console.log('line_size:' + line_size);

            imgHeader = canvas_header.toDataURL('image/jpeg');
            console.log('success');
            var pages = Math.ceil(counts / max_size);
            var ctx = canvas.getContext("2d");

            var newCanvas = document.getElementById('save');
            newCanvas.width = canvas.width;
            newCanvas.height = canvas.height;
            var context = newCanvas.getContext("2d");

            var imgData = ctx.getImageData(0, 0, canvas.width, canvas.height);
            var height = counts * line_size;//图片高度（加上表头的2行）
            var height_pix = 0;//canvas遮盖绘制像素高度
            for (var i = 0; i < pages; i++) {
                var left = counts - i * max_size;

                height_pix += left < max_size ? line_pix * left : line_pix * max_size;
                var offset = i * max_size * line_size;//每页需偏上方放置来让当前应该置于头部的行显示在最上边
                var offset_pix = i * max_size * line_pix;//每页需要遮住上面不需要显示的部分
                if (i == 0) {
                    //height_pix += 2 * line_pix + 5;
                    //offset_pix = 0;
                    //offset = 0;
                }
                else {
                    doc.addPage('a4', 'pt');
                }
                // 从 canvas 提取图片数据

                context.putImageData(imgData, 0, 0);

                context.fillStyle = '#FFFFFF';
                //遮住每页的上方页边距部分
                context.fillRect(0, 0, 5000, offset_pix);
                //遮住每页的下方空余和页边距部分
                context.fillRect(0, height_pix + 3, 5000, 5000);

                imgBody = newCanvas.toDataURL('image/jpeg');
                imgBody = newCanvas.toDataURL('image/jpeg');

                console.log('宽度'+canvas.width);

                if (i >= 0) {
                    doc.addImage(imgBody, side_left, 20 - offset + 2 * line_size, side_right, height);
                    doc.addImage(imgHeader, side_left, 20, side_right, 2 * line_size);
                }
                else doc.addImage(imgBody, side_left, 20 - offset,  side_right, height);
                //doc.addImage(imgBody, 10, 20 - offset, 750, height);

            }
            doc.save('科目余额查询' + query_date_ + '.pdf');
        });
    });
}

// function set_unit(new_unit){
//     if(new_unit) $('#Unit').innerHTML = "单位：ceshi" + new_unit;
// }
