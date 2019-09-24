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
	 * 根据userId查询用户的角色名称列表
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findRoleNameByUserId(int userId);

	/**
	 * 分页查询角色列表
	 * 
	 * @param modelMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<SysRole> findPage(Map<String, Object> modelMap, int pageNo, int pageSize);

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	int insertSelective(SysRole role);
	
	/**
	 * 根据角色ID删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	int deleteRoleById(Integer roleId);

	/**
	 * 根据ID删除角色
	 * 
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteRoleByIds(String ids);

	/**
	 * 查询目前拥有的角色列表
	 * 
	 * @return
	 */
	List<SysRole> findNowAllPermission();

	/**
	 * 分页查询角色资源信息
	 * 
	 * @param modelMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<RoleResourceAllocationBo> findRoleAndResourcePage(ModelMap modelMap, Integer pageNo, int pageSize);

	/**
	 * 根据用户ID查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findRoleByUserId(Integer userId);
}
