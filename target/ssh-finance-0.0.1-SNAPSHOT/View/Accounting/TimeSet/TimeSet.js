$(function(){
    $('#time_box').window({
        width: 320,
        height: 160,
        title: '财务日期维护',
        closable: false,
        minimizable: false,
        maximizable: false,
        resizable: false,
        collapsible: false,
    })
    $('#yes_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-ok',
    });
    $('#cancel_button').linkbutton({
        width: 100,
        height: 30,
        iconCls: 'icon-cancel',
    });
})