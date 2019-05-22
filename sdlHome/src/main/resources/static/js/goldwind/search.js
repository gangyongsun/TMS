$(document).ready(function() {
	$("#terminologyWord").focus();
	$('#searchTerminology').click(function() {
		//记录搜索框信息start
		var original_languageId = $("#languageId").val();
		var original_terminologyWord = $("#terminologyWord").val();
		//记录搜索框信息end
		
		//搜索术语start
		$.ajax({
			type : "POST",
			url : '/terminologyController/search',
			data : {
				terminologyWord : $("#terminologyWord").val(),
				languageId : $("#languageId").val()
			},
			beforeSend : function() {
				console.log(terminologyWord, languageId);
			},
			success : function(data) {
				console.log(data);
				$('#terminologies_main_div').html(data);
				$("#languageId").val(original_languageId);
				$("#terminologyWord").val(original_terminologyWord);
			},
			error : function(data) {
				console.log("搜索失败");
			}
		});
		//搜索术语end
	});
});