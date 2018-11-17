var grid;
// 数据加载参数
var dataGridParams = {
	queryParams : {
		pageSize : 20,// 配置每页显示条数，如果不配置，默认为10
	},
	pageList : [ 20, 30, 50, 100 ],
	columns : [ [ {
		field : 'id',
		title : 'id',
		hidden : true
	},  {
        field : 'name',
        title : '名称',
        align : 'left',
        width : '25%'
    }, {
        field : 'storeAmount',
        title : '库存',
        align : 'left',
        width : '10%'
    },  {
		field : 'minPrice',
		title : '最低价格(元)',
		align : 'left',
		width : '10%'
	} ,{
        field : 'originalPrice',
        title : '原始价格(元)',
        align : 'left',
        width : '15%'
    } , {
        field : 'buyCount',
        title : '卖出数量',
        align : 'left',
        width : '15%'
    }, {
        field : 'goodCommentCount',
        title : '好评数量',
        align : 'left',
        width : '15%'
    } ,{
        field : 'status',
        title : '状态',
        align : 'left',
        width : '15%',

    },  {
		field : 'createTime',
		title : '添加时间',
		align : 'left',
		width : '15%'
	}] ]
};

$(document).ready(function() {
	grid = $("#dataGrid").datagrid({
		title : '',
		url : basePath + '/goods/listGoods',
		queryParams : dataGridParams.queryParams,
		rownumbers : true,
		height : 'auto',
		width : 'auto',
		striped : true,
		fit : true,
		loadMsg : '正在加载数据，请稍后！',
		pagination : true,
		border : true,
		singleSelect : true,
		pageSize : dataGridParams.pageSize,
		pageList : dataGridParams.pageList,
		columns : dataGridParams.columns,
		toolbar : '#toolbar',
		onDblClickRow : function(rowIndex, rowData) {
//			 opt.edit(rowIndex, rowData);
		}
	});
	
	//分页插件
	var p = $("#dataGrid").datagrid("getPager"); 
	$(p).pagination({
		 onBeforeRefresh:function(pageNumber, pageSize){
			 targetPage = parseInt($('input.pagination-num').attr('value'));
			 $(p).pagination({
                 pageNumber: targetPage
             }); 
		 }
	});
	
}); // $(document).ready--end

opt = {
	
	// 信息查询
	search : function() {
		var form = serializeObject($('#searchForm'));
		grid.datagrid('reload', form);
	},
	// 重置查询条件
	resetSearch : function() {
		$('#searchForm').form('clear');
		grid.datagrid('reload', dataGridParams.queryParams);
	}

}

