function searchKeyInfo() {
	var findContentVal = $("#findContent").val();
	$.ajax({
		type : "POST",
		url : 'searchAll',
		data : {
			findContent : findContentVal
		},
		success : function(data) {
			$('#keyInfoMain').html(data);
			$("#findContent").val(findContentVal);
			$("#findContent").focus();
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}