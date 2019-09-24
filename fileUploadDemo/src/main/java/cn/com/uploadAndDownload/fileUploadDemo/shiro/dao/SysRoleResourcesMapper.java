package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.List;
import java.util.Map;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysResourcesBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysRoleResources;
import tk.mybatis.mapper.common.Mapper;

public interface SysRoleResourcesMapper extends Mapper<SysRoleResources>{

//	/**
//	 * 添加角色资源对应
//	 * 
//	 * @param roleResources
//	 * @return
//	 */
//	int insert(SysRoleResources roleResources);

	/**
	 * 根据资源ID查询角色资源对应
	 * 
	 * @param id 资源ID
	 * @return
	 */
	List<SysRoleResources> findRoleResourceByResourceId(Integer id);

	/**
	 * 根据角色ID查询角色资源对应
	 * 
	 * @param id 角色ID
	 * @return
	 */
	List<SysResourcesBo> findRoleResourceByRoleId(Integer id);

	/**
	 * 根据资源 && 角色ID查找
	 * 
	 * @param roleResources
	 * @return
	 */
	List<SysRoleResources> find(SysRoleResources roleResources);

	/**
	 * 根据资源ID删除
	 * 
	 * @param id 资源ID
	 * @return
	 */
	int deleteByResourceId(Integer id);

	/**
	 * 根据角色ID删除
	 * 
	 * @param id 角色ID
	 * @return
	 */
	int deleteByRoleId(Integer id);

//	/**
//	 * 根据角色ID && 资源ID删除
//	 * 
//	 * @param entity
//	 * @return
//	 */
//	int delete(SysRoleResources roleResources);

	/**
	 * 根据角色IDs删除
	 * 
	 * @param resultMap
	 * @return
	 */
	int deleteByRoleIds(Map<String, Object> resultMap);

//	/**
//	 * 更新资源信息
//	 * 
//	 * @param resources
//	 * @return
//	 */
//	int updateByPrimaryKeySelective(SysResources resources);
//
//	/**
//	 * 查询资源信息
//	 * 
//	 * @param id
//	 * @return
//	 */
//	SysResources selectByPrimaryKey(Integer id);
}