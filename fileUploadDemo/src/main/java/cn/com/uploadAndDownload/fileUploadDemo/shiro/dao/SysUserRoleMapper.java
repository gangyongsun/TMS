package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.List;
import java.util.Map;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUserRole;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserRoleMapper extends Mapper<SysUserRole> {

//	/**
//	 * 添加用户角色关系
//	 * 
//	 * @param sysUserRole
//	 * @return
//	 */
//	int insert(SysUserRole sysUserRole);

	/**
	 * 根据用户IDs删除
	 * 
	 * @param resultMap
	 * @return
	 */
	int deleteRoleByUserIds(Map<String, Object> resultMap);

	/**
	 * 根据用户主键ID删除用户角色关系
	 * 
	 * @param userId
	 */
	int deleteByUserId(Integer userId);

	/**
	 * 根据角色ID查询用户IDs
	 * 
	 * @param roleId
	 * @return
	 */
	List<Integer> findUserIdListByRoleId(Integer roleId);
}