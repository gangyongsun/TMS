package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.List;
import java.util.Map;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysResourcesBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;

public interface ResourcesService {
	/**
	 * 查询所有资源
	 * 
	 * @return
	 */
	List<SysResources> selectAll();

	Pagination<SysResources> findPage(Map<String, Object>  modelMap, Integer pageNo, int pageSize);

	SysResources insertSelective(SysResources psermission);

	Map<String, Object> deleteResourceById(String ids);

	Map<String, Object> addResource2Role(Long roleId, String ids);

	List<SysResourcesBo> selectResourceById(Long id);
}
