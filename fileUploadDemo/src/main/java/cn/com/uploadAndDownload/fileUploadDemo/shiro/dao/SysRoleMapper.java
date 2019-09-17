package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysRole;
import tk.mybatis.mapper.common.Mapper;

public interface SysRoleMapper extends Mapper<SysRole> {
	@Select("SELECT sr.role_desc FROM sys_user_role sur LEFT JOIN sys_role sr ON sr.id = sur.role_id \n" + "LEFT JOIN sys_user su ON sur.user_id = su.id WHERE su.id = #{userId}")
	Set<String> findRoleNameByUserId(@Param("userId") int userId);
	
	/**
	 * 根据ID删除角色
	 * 
	 * @param id 角色ID
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 添加角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int insert(SysRole sysRole);

	/**
	 * 添加角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int insertSelective(SysRole sysRole);

	/**
	 * 查询角色
	 * 
	 * @param id 角色ID
	 * @return
	 */
	SysRole selectByPrimaryKey(Integer id);

	/**
	 * 更新角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int updateByPrimaryKeySelective(SysRole sysRole);

	/**
	 * 更新角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int updateByPrimaryKey(SysRole sysRole);

	/**
	 * 根据用户ID查询角色类型集合
	 * 
	 * @param id 用户ID
	 * @return
	 */
	Set<String> findRoleByUserId(Integer id);

	/**
	 * 根据用户ID，查询所有权限
	 * 
	 * @param map
	 * @return
	 */
	List<SysRole> findNowAllPermission(Map<String, Object> map);
}