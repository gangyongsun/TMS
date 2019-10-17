package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.List;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUserRole;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserRoleMapper extends Mapper<SysUserRole> {

	/**
	 * 根据用户IDs删除
	 * 
	 * @param resultMap
	 * @return
	 */
	int deleteRoleByUserIds(Integer[] userIds);

	/**
	 * 根据用户主键ID删除用户角色关系
	 * 
	 * @param userId
	 */
	int deleteRoleByUserId(Integer userId);

	/**
	 * 根据角色ID查询用户IDs
	 * 
	 * @param roleId
	 * @return
	 */
	List<Integer> findUserIdListByRoleId(Integer roleId);
}