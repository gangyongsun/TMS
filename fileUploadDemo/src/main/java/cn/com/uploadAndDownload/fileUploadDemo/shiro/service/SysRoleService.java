package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.Set;

public interface SysRoleService {
	/**
	 * 根据userId查询用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findRoleNameByUserId(int userId);
}
