/**
 * 清空多个用户的角色
 * 
 * @returns
 */
function deleteAll(){
	var checkeds = $('[id=subcheckbox]:checked');
	if(!checkeds.length){
		return layer.msg('请选择要清除角色的用户！',so.default),!0;
	}
	var array = [];
	checkeds.each(function(){
		array.push(this.value);
	});
	return deleteById(array);
};

/**
 * 根据ID数组清空用户的角色
 */
function deleteById(ids){
	var index = layer.confirm("确定清除这"+ ids.length +"个用户的角色？",function(){
		var load = layer.load();
		$.post('clearRoleByUserIds',{userIds:ids.join(',')},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!0;
			}else{
				layer.msg(result.message);
				setTimeout(function(){
					$('#formId').submit();
				},1000);
			}
		},'json');
		layer.close(index);
	});
}

/**
 * 选择角色后保存
 */
function selectRole(){
	var checked = $("#boxRoleForm  :checked");
	var ids=[],names=[];
	$.each(checked,function(){
		ids.push(this.id);
		names.push($.trim($(this).attr('name')));
	});
	userId=$('#selectedUserId').val();
	var index = layer.confirm("确定操作？",function(){
		var load = layer.load();
		$.post('addRole2User',{roleIds:ids.join(','),userId: userId},function(result){
			layer.close(load);
			if(result){
				layer.msg(result.message,so.default),!1;
			}
			setTimeout(function(){
				$('#formId').submit();
			},1000);
		},'json');
	});
};

/**
 * 根据角色ID选择权限，分配权限操作
 */
function selectRoleById(obj){
	var id = $(obj).attr("id");
	var load = layer.load();
	$.post("selectRoleByUserId",{id:id},function(result){
		layer.close(load);
		if(result && result.length){
			var html =[];
			$.each(result,function(){
				html.push("<div class='checkbox'><label>");
				html.push("<input type='checkbox' id='");
				html.push(this.id);
				html.push("'");
				if(this.check){
					html.push(" checked='checked'");
				}
				html.push("name='");
				html.push(this.roleDesc);
				html.push("'/>");
				html.push(this.roleDesc);
				html.push('</label></div>');
			});
			return so.id('boxRoleForm').html(html.join('')) & $('#selectRole').modal({backdrop: false,keyboard: true}),$('#selectedUserId').val(id),!1;
		}else{
			return layer.msg(result.message,so.default);
		}
	},'json');
}