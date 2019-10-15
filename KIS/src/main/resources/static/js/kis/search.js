$(function() {
	/**
	 * 表格初始化
	 */
	function initTable() {
		$("#termTable").bootstrapTable({
			striped : true,// 设置为 true 会有隔行变色效果
			pagination : true,// 设置为 true 会在表格底部显示分页条
			cache : false,// 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性
			pageNumber : 1,// 初始化加载第一页，默认第一页
			pageSize : 20,// 每页的记录行数
			pageList : [ 10, 15, 20, 50, 100 ],// 可供选择的每页的行数
			sortable : true, // 设置为false 将禁止所有列的排序
			search : true,// 是否显示右上角的搜索框
			trimOnSearch: true,//搜索内容是否自动去除前后空格
			showToggle : true,// 是否显示切换视图（table/card）按钮
			showExport : true, // 是否显示导出按钮
			buttonsAlign : "right", // 按钮位置
			exportDataType : 'all', // 导出的方式 all全部 selected已选择的  basic', 'all', 'selected'.
			Icons : 'glyphicon glyphicon-export', // 导出图标
			exportTypes : [ 'txt' ],
			exportOptions: {
	            ignoreColumn: [0,1],  //忽略某一列的索引
	            fileName: 'terminology',  //文件名称设置
	            worksheetName: 'sheet1',  //表格工作区名称
	            tableName: 'termTable',
	            excelstyles: ['background-color', 'color', 'font-size', 'font-weight']
	        },
	        columns: [
	        	{
		        	title: '中文', // 表格表头显示文字
		        	align: 'left', // 左右居中
		        	valign: 'middle' // 上下居中
	        	}, {
		        	title: '英文',
		        	align: 'left',
		        	valign: 'middle'
	        	}
	        ]
		});
	}
	initTable();

	/**
	 * 回车事件绑定
	 */
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			searchTerminology();
		}
	}

	// 请求summary
	$.ajax({
		type : "POST",
		url : 'summary',
		success : function(data) {
			var keyArray = [];
			var keyValueMapJsonArray = [];
			for ( var key in data) {
				keyArray.push(key);
				keyValueMapJsonArray.push({
					value : data[key],
					name : key
				});
			}
			init_pieChart(keyArray, keyValueMapJsonArray);
		},
		error : function(data) {
			console.log("summary failure!");
		}
	});

	/**
	 * 初始化饼图
	 */
	function init_pieChart(keyData, displayData) {
		var pieChart = echarts.init(document
				.getElementById("echarts-pie-chart"));
		var pieoption = {
			title : {
				text : '术语构成比例',
				subtext : '数据统计',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : keyData
			},
			calculable : true,
			series : [ {
				name : '比例',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : displayData
			} ]
		};
		pieChart.setOption(pieoption);
		$(window).resize(pieChart.resize);
	}
});

/**
 * 查询术语
 * 
 * @param obj
 * @returns
 */
function searchTerminology(obj) {
	// 获取类型
	var termTypeValue = null;
	if (null != obj) {
		var termTypeValue = $(obj).attr("value");
	}
	// 获取搜索关键词
	var findContentVal = $("#findContent").val();

	$.ajax({
		type : "POST",
		url : 'index',
		data : {
			termType : termTypeValue,
			findContent : findContentVal
		},
		success : function(data) {
			$("#indexMain").html(data);
			$("#findContent").val(findContentVal);
			$("#findContent").focus();
			$("#termSummary").hide();
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}
/**
 * 显示详情
 * 
 * @param obj
 * @returns
 */
function showDetail(obj) {
	if (null != obj) {
		var id = $(obj).attr("id");
	}
	$.ajax({
		type : "POST",
		url : 'showDetail',
		data : {
			id : id
		},
		success : function(data) {
			$("#myModal").html(data);
			$('#myModal').modal('show');
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}