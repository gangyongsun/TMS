package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.UserRoleAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;

public interface UserService {

	/***
	 * 根据用户信息获取用户
	 *
	 * @param user
	 * @return
	 */
	public SysUser getUser(SysUser user);

	/**
	 * 根据userId获取用户权限
	 *
	 * @param userId userId
	 * @return 用户权限
	 */
	public Set<String> findResourcesByUserId(int userId);

	/**
	 * 用户角色权限分配分页查询
	 * 
	 * @param modelMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap, Integer pageNo, int pageSize);


	/**
	 * 用户登录，用于密码修改
	 * 
	 * @param username
	 * @param pswd
	 * @return
	 */
	public SysUser login(String username, String pswd);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public int updateUserOnSelective(SysUser user);

	/**
	 * 分页查询用户列表
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination<SysUser> findUserByPage(Map<String, Object> map, Integer pageNo, int pageSize);

	/**
	 * 根据用户id删除用户,ids 如果有多个则以“,”间隔。
	 * 
	 * @param ids
	 * @return
	 */
	public Map<String, Object> deleteUserByIds(String ids);

	/**
	 * 根据主键删除
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserById(Integer userId);

	/**
	 * 根据ID禁止登录
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public Map<String, Object> updateForbidUserById(Integer id, Integer userEnable);

	/**
	 * 根据ID查找用户
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser findUserById(Integer userId);

}
