/**
 * 删除所有
 * 
 * @returns
 */
function deleteAll(){
	var checkeds = $('[id=subcheckbox]:checked');
	console.log(checkeds);
	if(!checkeds.length){
		return layer.msg('请选择要删除的选项!'),!0;
	}
	var array = [];
	checkeds.each(function(){
		array.push(this.value);
	});
	return _delete(array);
}

/**
 * 根据ID数组，删除
 * 
 * @param ids
 * @returns
 */
function _delete(ids){
	var index = layer.confirm("确定删除这"+ ids.length +"个用户？",function(){
		var load = layer.load();
		$.post('deleteUserById',{ids:ids.join(',')},function(result){
			layer.close(load);
			if(result){
				layer.msg(result.message);
			}
			setTimeout(function(){
				$('#formId').submit();
			},1000);
		},'json');
	});
}

/**
 * 根据ID数组，删除
 * 
 * @param ids
 * @returns
 */
function _deleteone(obj){
	var id = $(obj).attr("id");
	var index = layer.confirm("确定删除该用户？",function(){
		var load = layer.load();
		$.post('deleteUserById',{ids:id},function(result){
			layer.close(load);
			if(result){
				layer.msg(result.message);
			}
			setTimeout(function(){
				$('#formId').submit();
			},1000);
		},'json');
	});
}

/**
 * 打开重置密码框
 * 
 * @param id
 * @returns
 */
function openResetPasswdWindow(obj){
	var id = $(obj).attr("id");
	$("#userId").val(id);
	$('#passwdReset').modal({backdrop: false,keyboard: true})
}

/**
 * 添加用户弹框
 * @returns
 */
function showAddUser(){
	$('#showAddUser').modal({backdrop: false,keyboard: true})
}

/**
 * 重置密码
 * 
 * @param id
 * @returns
 */
function resetPasswd(){
	var userId=$("#userId").val();
	var reNewPswd = $("#reNewPswd").val();
	var newPswd = $('#newPswd').val();
	if(newPswd != reNewPswd){
		return layer.msg('2次密码输入不一样！',function(){}),!1;
	}
	
	console.log(userId,newPswd,reNewPswd);
	var index = layer.confirm("确定为该用户重置密码？",function(){
		$.post('resetPasswd',{id:userId,newPswd:newPswd},function(result){
			if(result && result.status != 200){
				return layer.msg(result.message),!0;
			}else{
				layer.msg(result.message);
				$('#passwdReset').modal("hide");
			}
		},'json');
	});
}

/**
 * 激活 | 禁止用户登录
 * 
 * @param status
 * @param id
 * @returns
 */
function forbidUserById(userEnable,obj){
	var id = $(obj).attr("id");
	var text = userEnable?'激活':'禁止';
	var index = layer.confirm("确定"+text+"这个用户？",function(){
		var load = layer.load();
		$.post('forbidUserById',{userEnable:userEnable,id:id},function(result){
			layer.close(load);
			if(result){
				layer.msg(result.message);
			}
			setTimeout(function(){
				$('#formId').submit();
			},1000);
		},'json');
		layer.close(index);
	});
}