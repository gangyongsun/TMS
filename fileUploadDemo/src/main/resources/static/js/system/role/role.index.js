//server分页
$(function () {
    var t = $("#roleListTable").bootstrapTable({
        url: '/role/pageList',
        method: 'get',
        dataType: "json",
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        striped: true,//设置隔行变色效果
        clickToSelect: true,
        undefinedText: "空",//当数据为undefined时显示的字符
        showRefresh:true,//显示刷新按钮
        showToggle: true,//是否显示切换试图（table/card）按钮
        showColumns: true,//是否显示内容列下拉框
        pagination: true, //启用分页
        showPaginationSwitch:true,//是否显示数据条数选择框
        pageNumber: 1,//分页首页页码
        pageSize: 10,//分页页面数据条数
        pageList: [10, 20, 50, 100],//设置可供选择的页面数据条数；设置为All则显示所有记录
        minimumCountColumns: 2,//最少允许的列数
        paginationFirstText: "首页",//指定“首页”按钮的图标或文字
        paginationPreText: '上一页',//指定“上一页”按钮的图标或文字
        paginationNextText: '下一页',//指定“下一页”按钮的图标或文字
        paginationLastText: "末页",//指定“末页”按钮的图标或文字
        data_local: "zh-US",//表格汉化
        sidePagination: "server", //服务端处理分页
        queryParamsType:'',//默认值为'limit',在默认情况下传给服务端的参数为：offset,limit,sort;设置为'',在这种情况下传给服务器的参数为：pageSize,pageNumber
      	idField: "id",//指定主键列
        columns: [
            {
                checkbox:true,
            },
            {
                visible:false,
                field: 'id',//json数据中rows数组中的属性名
                align: 'center'//水平居中
            },
            {
                title: '角色名称',
                field: 'roleDesc',
                align: 'left'
            },
            {
                title: '角色类型',
                field: 'roleType',
                align: 'left'
            },
            {
                title: '操作',
                align: 'center',
                formatter: function (value, row, index) {//自定义显示，也可以写标签
                	operate=
                	'<!-- <shiro:hasPermission name="/role/delete"> -->'+
					'<a class="btn btn-danger btn-sm" onclick="javascript:_deleteone('+row.id+');"><span class="glyphicon glyphicon-trash"></span> 删除</a>'+
					'<!-- </shiro:hasPermission> -->'
                    return operate;
                }
            }
        ]
    });
 
    t.on('load-success.bs.table', function (data) {//table加载成功后的监听函数
        console.log("load success");
        $(".pull-right").css("display", "block");
    });
});

 /**
  * 删除选择角色
  * @returns
  */
function deleteSelected(){
	var idArray=getIdSelections();
	if (idArray.length == 0) {
		layer.msg("请先选择要删除的角色!");
	}else{
		console.log(idArray)
		var index = layer.confirm("确定删除这"+ idArray.length +"个角色？",function(){
			var load = layer.load();
			$.post('deleteRoleByIds',{
				ids:idArray.join(',')
			},function(result){
				layer.close(load);
				if (result) {
					layer.msg(result.message);
				}
				setTimeout(function() {
					$('#formId').submit();
				}, 1000);
			},'json');
		});
	}
}

/**
 * 获得选中的
 * 
 * @returns
 */
function getIdSelections() {
	return $.map($("#roleListTable").bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
}

/**
 * 根据ID数组，删除
 * 
 * @param ids
 * @returns
 */
function _deleteone(id) {
	var index = layer.confirm("确定删除该角色？", function() {
		var load = layer.load();
		$.post('deleteRoleByIds', {
			ids : id
		}, function(result) {
			layer.close(load);
			if (result) {
				layer.msg(result.message);
			}
			setTimeout(function() {
				$('#formId').submit();
			}, 1000);
		}, 'json');
	});
}

/**
 * 添加用户弹框
 * @returns
 */
function showAddRole(){
	$('#showAddRole').modal({backdrop: false,keyboard: true})
}

/**
 * 添加角色
 * 
 * @returns
 */
function addRole(){
	var roleName = $('#roleName').val();
	var	roleType = $('#roleType').val();
	if($.trim(roleName) == ''){
		return layer.msg('角色名称不能为空!');
	}
	if(!/^[a-z0-9A-Z]{6}$/.test(roleType)){
		return layer.msg('角色类型为6位置数字字母!');
	}
	var load = layer.load();
	$.post('addRole',{
		roleDesc:roleName,
		roleType:roleType
	},function(result){
		layer.close(load);
		if (result) {
			layer.msg(result.message);
		}
		setTimeout(function() {
			$('#formId').submit();
		}, 1000);
	},'json');
}