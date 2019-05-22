$(document).ready(function() {
	$('#login_btn').click(function() {
		var username = $("#username").val();
		var password = $("#password").val();
		console.log("username=", username,"password=", password);
		$.ajax({
			type : "POST",
			url : '/loginPost',
			data : {
				username : username,
				password : password
			},
			success : function(data) {
				console.log("data:" + data.success);
				 if (data.success) {
					window.location.href = '/terminologyController/index';
				} else {
					console.log(data.message);
					return;
				}
			},
			error : function(data) {
				console.log("登录失败");
			}
		});
	});

	// Toggle Function
	$('.toggle').click(function() {
		// Switches the Icon
		$(this).children('i').toggleClass('fa-pencil');
		// Switches the forms
		$('.form').animate({
			height : "toggle",
			'padding-top' : 'toggle',
			'padding-bottom' : 'toggle',
			opacity : "toggle"
		}, "slow");
	});

});