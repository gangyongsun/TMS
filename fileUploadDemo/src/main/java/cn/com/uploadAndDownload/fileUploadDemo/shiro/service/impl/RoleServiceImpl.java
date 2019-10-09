package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.BaseMybatisDao;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.RoleResourceAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysRoleMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysUserRoleMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysRole;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUserRole;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.RoleService;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.SampleRealm;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.manager.TokenManager;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.StringUtils;

@Service
public class RoleServiceImpl extends BaseMybatisDao<SysRoleMapper> implements RoleService {

	/***
	 * 用户手动操作Session
	 */
	@Autowired
	private CustomSessionManager customSessionManager;

	@Autowired
	private static SampleRealm sampleRealm;

	@Autowired
	private SysRoleMapper roleMapper;

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	@Override
	public Pagination<SysRole> findPage(Map<String, Object> modelMap, int pageNo, int pageSize) {
		return super.findPage(modelMap, pageNo, pageSize);
	}

	@Override
	public int insertSelective(SysRole role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public Set<String> findRoleNameByUserId(int userId) {
		return roleMapper.findRoleByUserId(userId);
	}

	@Override
	public List<SysRoleBo> selectRoleByUserId(int id) {
		return roleMapper.selectRoleByUserId(id);
	}

	@Override
	public Set<String> findRoleByUserId(Integer userId) {
		return roleMapper.findRoleByUserId(userId);
	}

	@Override
	public Map<String, Object> addRole2User(int userId, String roleIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			// 先删除原有的
			userRoleMapper.deleteByUserId(userId);
			// 如果roleIds有值就添加，roleIds没值象征着把这个用户（userId）所有角色取消
			if (StringUtils.isNotBlank(roleIds)) {
				String[] roleIdArray = null;
				if (StringUtils.contains(roleIds, ",")) {
					roleIdArray = roleIds.split(",");
				} else {
					roleIdArray = new String[] { roleIds };
				}
				// 添加新的
				for (String roleId : roleIdArray) {
					if (StringUtils.isNotBlank(roleId)) {
						SysUserRole userRole = new SysUserRole(userId, new Integer(roleId));
						count += userRoleMapper.insertSelective(userRole);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
//		 清空用户的权限，迫使再次获取权限的时候，得重新加载
//		TokenManager.clearUserAuthByUserId(userId);

		List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userId);
		for (SimplePrincipalCollection simplePrincipalCollection : result) {
			sampleRealm.clearCachedAuthorizationInfo(simplePrincipalCollection);
		}
		resultMap.put("count", count);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByUserIds(String userIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("userIds", userIds);
			userRoleMapper.deleteRoleByUserIds(resultMap);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "删除成功！";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			c: for (String idx : idArray) {
				Integer id = new Integer(idx);
				if (new Integer(1).equals(id)) {
					resultMsg = "操作成功，但是<系统管理员>不能删除！";
					continue c;
				} else {
					count += this.deleteRoleById(id);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public List<SysRole> findNowAllPermission() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", TokenManager.getUserId());
		return roleMapper.findNowAllPermission(map);
	}

	@Override
	public Pagination<RoleResourceAllocationBo> findRoleAndResourcePage(ModelMap modelMap, Integer pageNo, int pageSize) {
		return super.findPage("findRoleAndResources", "findCount", modelMap, pageNo, pageSize);
	}

	@Override
	public int deleteRoleById(Integer roleId) {
		return roleMapper.deleteByPrimaryKey(roleId);
	}

}
