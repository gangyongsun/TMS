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
	SysUser getUser(SysUser user);

	/**
	 * 根据userId获取用户权限
	 *
	 * @param userId userId
	 * @return 用户权限
	 */
	Set<String> findResourcesByUserId(int userId);

	Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap, Integer pageNo, int pageSize);

	List<SysRoleBo> selectRoleByUserId(int id);

	Map<String, Object> addRole2User(int userId, String ids);

	Map<String, Object> deleteRoleByUserIds(String userIds);

	SysUser login(String username, String pswd);

	void updateByPrimaryKeySelective(SysUser user);

	Pagination<SysUser> findByPage(Map<String, Object> map, Integer pageNo, int pageSize);

	Map<String, Object> deleteUserById(String ids);

	Map<String, Object> updateForbidUserById(Long id, Long status);

	SysUser findUserById(Integer userId);

}
