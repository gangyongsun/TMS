package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.TableSplitResult;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.RoleResourceAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
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
	 * 根据用户ID查询权限
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRoleBo> selectRoleByUserId(int id);

	/**
	 * 根据用户ID查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findRoleByUserId(Integer userId);

	/**
	 * 为用户添加角色
	 * 
	 * @param userId
	 * @param ids
	 * @return
	 */
	public Map<String, Object> addRole2User(int userId, String ids);

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
	 * 根据ID删除角色信息
	 * 
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteRoleByIds(String ids);

	/**
	 * 根据用户id删除用户拥有的角色
	 * 
	 * @param userIds
	 * @return
	 */
	public Map<String, Object> deleteRoleByUserIds(String userIds);

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
	 * bootstrap table 分页查询
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	TableSplitResult<SysRole> findPage2(ModelMap modelMap, Integer pageNumber, Integer pageSize);

	TableSplitResult<RoleResourceAllocationBo> findRoleAndResourcePage2(ModelMap modelMap, Integer pageNumber, Integer pageSize);

}
