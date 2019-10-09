package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysResourcesBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;

public interface ResourcesService {

	/**
	 * 分页查询资源信息
	 * 
	 * @param modelMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<SysResources> findPage(Map<String, Object> modelMap, Integer pageNo, int pageSize);

	/**
	 * 添加资源
	 * 
	 * @param resources
	 * @return
	 */
	SysResources insert(SysResources resources);

	/**
	 * 添加资源
	 * 
	 * @param resources
	 * @return
	 */
	SysResources insertSelective(SysResources resources);

	/**
	 * 根据IDs删除资源
	 * 
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteResourceByIds(String ids);

	/**
	 * 根据ID删除资源
	 * 
	 * @param id
	 * @return
	 */
	int deleteResourceById(Integer id);

	/**
	 * 为角色添加资源
	 * 
	 * @param roleId
	 * @param ids
	 * @return
	 */
	Map<String, Object> addResource2Role(Integer roleId, String ids);

	/**
	 * 根据用户ID查询资源，放入到Authorization里
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findResourceByUserId(Integer userId);
	
	/**
	 * 更新资源信息
	 * 
	 * @param resources
	 * @return
	 */
	int updateByPrimaryKeySelective(SysResources resources);
	
	/**
	 * 根据主键查询资源
	 * 
	 * @param id
	 * @return
	 */
	SysResources selectByPrimaryKey(Integer id);

	/**
	 * 根据角色ID查询角色权限
	 * 
	 * @param id
	 * @return
	 */
	List<SysResourcesBo> selectResourceByRoleId(Integer id);
}
