jQuery(document).ready(function() {
	/**
	 * 回车事件绑定
	 */
	document.onkeydown = function(event) {
		console.log(event);
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			searchKeyInfo();
		}
	}
});

/**
 * 搜索术语
 * 
 * @returns
 */
function searchKeyInfo() {
	var findContentVal = $("#findContent").val();
	$.ajax({
		type : "POST",
		url : 'searchAll',
		data : {
			findContent : findContentVal
		},
		success : function(data) {
			$("#keyInfoMain").html(data);
			$("#findContent").val(findContentVal);
			$("#findContent").focus();
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}

/**
 * 按类别查询术语
 * 
 * @param obj
 * @returns
 */
function searchByTermType(obj) {
	var termTypeValue = null;
	if (null != obj) {
		var termTypeValue = $(obj).attr("value");
	}
	$.ajax({
		type : "POST",
		url : 'index',
		data : {
			termType : termTypeValue
		},
		success : function(data) {
			$("#indexMain").html(data);
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}
