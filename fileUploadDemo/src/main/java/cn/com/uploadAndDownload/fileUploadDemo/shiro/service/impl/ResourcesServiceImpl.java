package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.BaseMybatisDao;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysResourcesBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysRoleResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysUserRoleMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysResources;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysRoleResources;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.ResourcesService;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.manager.TokenManager;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.StringUtils;

@Service
public class ResourcesServiceImpl extends BaseMybatisDao<SysResourcesMapper> implements ResourcesService {

	@Autowired
	private SysResourcesMapper resourcesMapper;

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	@Autowired
	private SysRoleResourcesMapper roleResourceMapper;

//	@Override
//	public List<SysResources> selectAll() {
//		return resourcesMapper.selectAll();
//	}

	@Override
	public int deleteResourceById(Integer id) {
		return resourcesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SysResources insert(SysResources resources) {
		resourcesMapper.insert(resources);
		return resources;
	}

	@Override
	public SysResources insertSelective(SysResources resource) {
		resourcesMapper.insertSelective(resource);
		// 每添加一个权限，都往【系统管理员 100001】里添加一次,保证系统管理员有最大的权限
		executePermission(new Integer(1), String.valueOf(resource.getId()));
		return resource;
	}

	@Override
	public Set<String> findResourceByUserId(Integer userId) {
		return resourcesMapper.findResourceByUserId(userId);
	}

	/**
	 * 先删除原有的，再重新添加
	 */
	@Override
	public Map<String, Object> addResource2Role(Integer roleId, String ids) {
		roleResourceMapper.deleteByRoleId(roleId);
		Map<String, Object> resultMap = executePermission(roleId, ids);
		return resultMap;
	}

	@Override
	public Pagination<SysResources> findPage(Map<String, Object> modelMap, Integer pageNo, int pageSize) {
		return super.findPage(modelMap, pageNo, pageSize);
	}

	@Override
	public int updateByPrimaryKeySelective(SysResources resources) {
		return resourcesMapper.updateByPrimaryKeySelective(resources);
	}

	@Override
	public SysResources selectByPrimaryKey(Integer id) {
		return resourcesMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> deleteResourceByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int successCount = 0, errorCount = 0;
			String resultMsg = "删除%s条，失败%s条";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String idx : idArray) {
				Integer id = new Integer(idx);

				List<SysRoleResources> roleResources = roleResourceMapper.findRoleResourceByResourceId(id);
				if (null != roleResources && roleResources.size() > 0) {
					errorCount += roleResources.size();
				} else {
					successCount += this.deleteResourceById(id);
				}
			}
			resultMap.put("status", 200);
			// 如果有成功的，也有失败的，提示清楚。
			if (errorCount > 0) {
				resultMsg = String.format(resultMsg, successCount, errorCount);
			} else {
				resultMsg = "操作成功";
			}
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	/**
	 * 处理权限
	 * 
	 * @param roleId
	 * @param ids
	 * @return
	 */
	private Map<String, Object> executePermission(Integer roleId, String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			/**
			 * 如果ids,permission 的id 有值，那么就添加；没值象征着：把这个角色（roleId）所有权限取消
			 */
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = null;
				if (StringUtils.contains(ids, ",")) {
					idArray = ids.split(",");
				} else {
					idArray = new String[] { ids };
				}
				/**
				 * 添加新的
				 */
				for (String pid : idArray) {
					/**
					 * 这里严谨点可以判断，也可以不判断；这个{@link StringUtils 我是重写了的}
					 */
					if (StringUtils.isNotBlank(pid)) {
						SysRoleResources roleResources = new SysRoleResources(roleId, new Integer(pid));
						count += roleResourceMapper.insert(roleResources);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		/**
		 * 清空拥有角色Id为：roleId 的用户权限已加载数据，让权限数据重新加载
		 */
		List<Integer> userIds = userRoleMapper.findUserIdListByRoleId(roleId);

		TokenManager.clearUserAuthByUserId(userIds);
//
//		List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);
//		for (SimplePrincipalCollection simplePrincipalCollection : result) {
//			sampleRealm.clearCachedAuthorizationInfo(simplePrincipalCollection);
//		}

		resultMap.put("count", count);
		return resultMap;
	}

	@Override
	public List<SysResourcesBo> selectResourceByRoleId(Integer id) {
		return resourcesMapper.selectResourceByRoleId(id);
	}

}
