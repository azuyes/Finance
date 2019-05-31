$(function () {
	$('#box').dialog({
        width: 345,
        height: 190,
        title: '往来单位科目选择',
        modal: true,
        buttons: [{
            text: '确定',
            plain: true,
            iconCls: 'icon-ok',
            handler: function () {
                $('#box').dialog('close');
                document.getElementById("mainDisplay").style.visibility = "visible";
                
            }
        }, {
            text: '取消',
            plain: true,
            iconCls: 'icon-cancel',
            handler: function () {
                $('#box').dialog('close');
            }
        }],
    });
	
    $('#helpWin').window({
	    width: 480,
	    height: 530,
	    title: '科目编码帮助',
	    collapsible: false,
	    minimizable: false,
	    closed: true,
	    resizable: false,
	    maximizable: false,
    });
    
    
    $('#itemTable').datagrid({
        width: '100%',
        height:'100%',
        //url: 'content.json',
        columns: [[
			{
			    field: 'CompCatNo1',
			    title: '编号',
			    width: 120
			},
			{
			    field: 'CatName1',
			    title: '名称',
			    width: 130
			},
			{
			    field: 'FinLevel',
			    title: '明细否',
			    width: 100
			},
            {
                field: 'FinLevel',
                title: '级数',
                width:100

            },
        ]],
        toolbar: [{
            id: 'upperlevel_btn',
            text: '上级',
            iconCls: 'icon-back',
            handler: function () {
                ShowAddDialog();//实现添加记录的页面
            }
        }, '-', {
            id: 'nextlevel_btn',
            text: '下级',
            iconCls: 'icon-next',
            handler: function () {
                ShowEditDialog();//实现修改记录的方法
            }
        },'-', {
            id: 'details_btn',
            text: '选定',
            iconCls: 'icon-ok',
            handler: function () {
            	$('#helpWin').window('close');
            	$('#box').dialog('close');
                document.getElementById("mainDisplay").style.visibility = "visible";
//              document.getElementById("helpWin").window('close');
            }
        }],
        pagination: false,
        pageSize: 5,
        pageList: [5, 10, 15],
        pageNumber: 1,
        pagePosition: 'bottom',
    });
    
	$('#spTree1').panel({
		//id : 'pox',
		width : 400,
		height : '80%',
		left : 100,
		top : 100,	
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		title: '专项核算类别1',
		//tools : '#tt',
		tools : [{
			text:'确定',
			iconCls : 'icon-ok',
			handler : function () {

			},
		},{
		}],
	});
	$('#spTree2').panel({
		//id : 'pox',
		width : 400,
		height : '80%',
		left : 100,
		top : 100,	
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		title: '专项核算类别2',
		//tools : '#tt',
		tools : [{
			text:'确定',
			iconCls : 'icon-ok',
		},{
		}],
	});
	
    $('#specialObjectTree1').tree({
		//url : 'tree.json',
//		animate : true,
		checkbox : true,
		cascadeCheck : false,
//		onlyLeafCheck : true,
		lines : true,
		dnd : true,
		data : [{
			"text" : "本地节点",
			"children":[{
				"text":"本地节点1",
				"children":[{
					"text":"本地节点2",
				},{
					"text":"本地节点3",
				},{
					"text":"本地节点4",
				},],
			},{
				"text":"本地节点5",
			},{
				"text":"本地节点6",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			},{
				"text":"本地节点7",
			}],
			
		}],
		formatter : function (node) {
			return '[' + node.text + ']';
		}
	});
	
	$('#specialObjectTree2').tree({
		//url : 'tree.json',
		animate : true,
		checkbox : true,
		cascadeCheck : false,
		onlyLeafCheck : true,
		lines : true,
		dnd : true,
		data : [{
			"text" : "本地节点",
			"children":[{
				"text":"本地节点1",
				"children":[{
					"text":"本地节点2",
				},{
					"text":"本地节点3",
				},{
					"text":"本地节点4",
				},],
			},{
				"text":"本地节点5",
			},{
				"text":"本地节点6",
			},{
				"text":"本地节点7",
			}],
			
		}],
		formatter : function (node) {
			return '[' + node.text + ']';
		}
	});
});
