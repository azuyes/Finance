$(function() {

	$('#add_account_box').window({
		width: 570,
		height: 400,
		padding: 10,
		title: '增加会计科目',
		iconCls: 'icon-add',
		closed: true,
		resizable: false,
		minimizable: false,
		maximizable: false,
		collapsible: false,
	})

	$('#main_table').datagrid({
		width: '100%',
		columns: [
			[{
					field: 'account_number',
					title: '科目编号',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'account_name',
					title: '科目名称',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'attribute_name',
					title: '属性名称',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'account_format',
					title: '账式',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'feature',
					title: '性质',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'foreign_name',
					title: '外币名称',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'is_detail_account',
					title: '明细否',
					width: '12.5%',
					align: 'center'
				},
				{
					field: 'balance',
					title: '金额余额',
					width: '12.5%',
					align: 'center'
				},
			]
		],
		toolbar: "#button_group"
		//      toolbar: [{
		//          text:'增加',
		//          iconCls: 'icon-add',
		//          handler: function(){
		//              $('#add_account_box').window('open');
		//              $('#add_account_box').style.display = 'block';
		//          }
		//      },'-',{
		//          text:'删除',
		//          iconCls: 'icon-cancel',
		//      },'-',{
		//          text:'修改',
		//          iconCls: 'icon-edit',
		//      },'-',{
		//          text:'上级',
		//          iconCls: 'icon-back',
		//      },'-',{
		//          text:'下级',
		//          iconCls: 'icon-next',
		//      },'-',{
		//          text:'转出',
		//          iconCls: 'icon-ok',
		//      },'-',{
		//          text:'退出',
		//          iconCls: 'icon-no',
		//      }]
	});

	$('#add_button').bind('click', function(){
		$('#add_account_box').window('open');
		$('#add_account_box').style.display = 'block';
   });

	//  $('#money').click(){
	//  	$('#quantity_information input').disabled="disabled";
	//  }

});