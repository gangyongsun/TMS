package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.List;
import java.util.Set;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysResourcesBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;
import tk.mybatis.mapper.common.Mapper;

public interface SysResourcesMapper extends Mapper<SysResources> {
//	@ResultMap(value = "BaseResultMap")
//	@Select("SELECT * FROM sys_resources ")
//	List<SysResources> selectAll();
//
//	@Select("SELECT sre.resource_url FROM sys_user_role sur LEFT JOIN sys_user su ON su.id = sur.user_id \n"
//			+ "LEFT JOIN sys_role sr ON sur.role_id=sr.id LEFT JOIN sys_role_resources srr ON sur.role_id = srr.role_id\n" + "LEFT JOIN sys_resources sre ON sre.id = srr.resources_id\n"
//			+ "WHERE su.id=#{userId}")
//	Set<String> findRoleNameByUserId(@Param("userId") int userId);

//	/**
//	 * 根据ID删除权限
//	 * 
//	 * @param id 权限ID
//	 * @return
//	 */
//	int deleteByPrimaryKey(Integer id);

//	/**
//	 * 添加权限
//	 * 
//	 * @param resources
//	 * @return
//	 */
//	int insert(SysResources resources);

//	/**
//	 * 添加权限
//	 * 
//	 * @param resources 权限对象
//	 * @return
//	 */
//	int insertSelective(SysResources resources);

//	/**
//	 * 查询权限
//	 * 
//	 * @param id 权限ID
//	 * @return
//	 */
//	SysResources selectByPrimaryKey(Integer id);

//	/**
//	 * 更新权限
//	 * 
//	 * @param resources 权限对象
//	 * @return
//	 */
//	int updateByPrimaryKeySelective(SysResources resources);

	/**
	 * 根据角色获取权限<br>
	 * marker:0,没有权限;<br>
	 * marker：非0(和up.id)一致表示有权限
	 * 
	 * @param id 角色ID
	 * @return 权限列表
	 */
	List<SysResourcesBo> selectResourceByRoleId(Integer id);

	/**
	 * 根据用户ID获取权限的Set集合
	 * 
	 * @param id 权限ID
	 * @return
	 */
	Set<String> findResourceByUserId(Integer id);

}