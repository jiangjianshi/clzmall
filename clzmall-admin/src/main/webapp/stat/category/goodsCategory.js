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
	}, {
		field : 'catCode',
		title : '类别编号',
		align : 'left',
		width : '20%'
	}, {
		field : 'catName',
		title : '类别名称',
		align : 'left',
		width : '20%'
	} , {
        field : 'catDesc',
        title : '类别描述',
        align : 'left',
        width : '20%'
    } , {
		field : 'createTime',
		title : '新增时间',
		align : 'left',
		width : '20%'
	},  {
		field : 'updateTime',
		title : '更新时间',
		align : 'left',
		width : '20%'
	}] ]
};

$(document).ready(function() {
	grid = $("#dataGrid").datagrid({
		title : '',
		url : basePath + '/goods/listCategory',
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
	},
	add : function() {
		$('#addCategory').dialog('open').dialog('center').dialog('setTitle','添加分类');
        $('#addForm').form('clear');
	},
	saveCategory : function() {
		$('#addForm').form('submit',{
            url: '/goods/saveCategory',
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.code == 0){
                	showMessage("处理结果", result.msg, 1000);
                	 $('#addCategory').dialog('close');// close the dialog
                     grid.datagrid('reload');    // reload the user data
                } else {
                     alert(result.msg);
                }
            },
            error : function() {
                alert("操作失败，请联系管理员")
            }
        });
	}
}

