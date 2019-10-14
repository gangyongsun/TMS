jQuery(document).ready(function() {
	/**
	 * 回车事件绑定
	 */
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			searchTerminology();
		}
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

function showDetail(obj){
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