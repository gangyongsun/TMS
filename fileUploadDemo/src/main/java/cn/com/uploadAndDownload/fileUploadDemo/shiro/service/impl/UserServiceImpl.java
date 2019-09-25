package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.BaseMybatisDao;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.UserRoleAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysUserMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysUserRoleMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUserRole;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.UserService;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.manager.TokenManager;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;

@Service
public class UserServiceImpl extends BaseMybatisDao<SysUserMapper> implements UserService {

	/***
	 * 用户手动操作Session
	 */
	@Autowired
	CustomSessionManager customSessionManager;

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private SysResourcesMapper resourcesMapper;

	@Override
	public SysUser getUser(SysUser user) {
		return userMapper.selectOne(user);
	}

	@Override
	public int deleteUserById(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public Set<String> findResourcesByUserId(int userId) {
		Set<String> resourceSet = resourcesMapper.findResourceByUserId(userId);
		Set<String> result = new HashSet<>();
		for (String permission : resourceSet) {
			if (StringUtils.isBlank(permission)) {
				continue;
			}
			permission = StringUtils.trim(permission);
			result.addAll(Arrays.asList(permission.split("\\s*;\\s*")));
		}
		return result;
	}

	@Override
	public Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap, Integer pageNo, int pageSize) {
		return super.findPage("findUserAndRole", "findCount", modelMap, pageNo, pageSize);
	}

	@Override
	public List<SysRoleBo> selectRoleByUserId(int id) {
		return userMapper.selectRoleByUserId(id);
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
		// 清空用户的权限，迫使再次获取权限的时候，得重新加载
		TokenManager.clearUserAuthByUserId(userId);
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
	public SysUser login(String userName, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", userName);
		map.put("password", password);
		SysUser user = userMapper.login(map);
		return user;
	}

	@Override
	public int updateUserOnSelective(SysUser sysUser) {
		return userMapper.updateByPrimaryKeySelective(sysUser);
	}

	@Override
	public Pagination<SysUser> findUserByPage(Map<String, Object> map, Integer pageNo, int pageSize) {
		return super.findPage(map, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> deleteUserByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String id : idArray) {
				count += this.deleteUserById(new Integer(id));
			}
			resultMap.put("status", 200);
			resultMap.put("message", "删除成功！");
			resultMap.put("count", count);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除失败！");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateForbidUserById(Integer id, Integer userEnable) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			SysUser user =findUserById(id);
			user.setUserEnable(userEnable);
			updateUserOnSelective(user);

			// 如果当前用户在线，需要标记并且踢出
			customSessionManager.forbidUserById(id, userEnable);

			resultMap.put("status", 200);
			resultMap.put("message", "操作成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "操作失败！");
			LoggerUtils.fmtError(getClass(), "禁止或者激活用户登录失败，id[%s],status[%s]", id, userEnable);
		}
		return resultMap;
	}

	@Override
	public SysUser findUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
