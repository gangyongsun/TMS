package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.RoleResourceAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysRole;

public interface RoleService {
	/**
	 * 根据userId查询用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findRoleNameByUserId(int userId);

	Pagination<SysRole> findPage(Map<String, Object> modelMap, int pageNo, int pageSize);

	int insertSelective(SysRole role);

	Map<String, Object> deleteRoleById(String ids);

	List<SysRole> findNowAllPermission();

	Pagination<RoleResourceAllocationBo> findRoleAndResourcePage(ModelMap modelMap, Integer pageNo, int pageSize);
}
