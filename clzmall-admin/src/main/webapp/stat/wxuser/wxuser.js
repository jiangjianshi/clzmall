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
        field : 'nickName',
        title : '昵称',
        align : 'left',
        width : '20%'
    }, {
        field : 'avatarUrl',
        title : '头像',
        align : 'left',
        width : '15%',
        formatter : function(value, row) {
            return "<img src='"+value+"' style='width:24px; height: 24px;'/>"
        }
    },  {
		field : 'gender',
		title : '性别',
		align : 'left',
		width : '10%',
        formatter : function(value, row) {
            return get_js_codeText('genders_js', value);
        }
	} ,{
        field : 'mobile',
        title : '绑定手机号',
        align : 'left',
        width : '15%'
    },{
        field : 'score',
        title : '积分',
        align : 'left',
        width : '10%'
    } , {
        field : 'province',
        title : '省份',
        align : 'left',
        width : '15%'
    }, {
        field : 'city',
        title : '城市',
        align : 'left',
        width : '15%'
    } ,{
        field : 'inviterUid',
        title : '邀请人',
        align : 'left',
        width : '15%',

    },{
		field : 'loginTime',
		title : '登录时间',
		align : 'left',
		width : '15%'
	},  {
		field : 'createTime',
		title : '注册时间',
		align : 'left',
		width : '15%'
	}] ]
};

$(document).ready(function() {
	grid = $("#dataGrid").datagrid({
		title : '',
		url : basePath + '/wx/queryWxUsers',
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

