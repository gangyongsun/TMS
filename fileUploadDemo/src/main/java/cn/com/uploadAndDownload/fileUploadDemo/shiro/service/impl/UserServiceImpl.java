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
import cn.com.uploadAndDownload.fileUploadDemo.shiro.session.CustomSessionManager;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.manager.TokenManager;

@Service
public class UserServiceImpl extends BaseMybatisDao<SysUserMapper> implements UserService {

	/***
	 * 用户手动操作Session
	 */
	@Autowired
	CustomSessionManager customSessionManager;

	@Autowired
	SysUserRoleMapper userRoleMapper;

	@Autowired
	private SysUserMapper userMapper;

	@Autowired
	private SysResourcesMapper sysResourcesMapper;

	@Override
	public SysUser getUser(SysUser user) {
		return userMapper.selectOne(user);
	}

	@Override
	public Set<String> findResourcesByUserId(int userId) {
		Set<String> permissions = sysResourcesMapper.findRoleNameByUserId(userId);
		Set<String> result = new HashSet<>();
		for (String permission : permissions) {
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
		return super.findPage(modelMap, pageNo, pageSize);
	}

	@Override
	public List<SysRoleBo> selectRoleByUserId(int id) {
		return userMapper.selectRoleByUserId(id);
	}

	@Override
	public Map<String, Object> addRole2User(int userId, String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			// 先删除原有的。
			userRoleMapper.deleteByUserId(userId);
			// 如果ids,role 的id 有值，那么就添加。没值象征着：把这个用户（userId）所有角色取消。
			if (StringUtils.isNotBlank(ids)) {
				String[] idArray = null;

				// 这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if (StringUtils.contains(ids, ",")) {
					idArray = ids.split(",");
				} else {
					idArray = new String[] { ids };
				}
				// 添加新的。
				for (String rid : idArray) {
					// 这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的}
					if (StringUtils.isNotBlank(rid)) {
						SysUserRole entity = new SysUserRole(userId, new Integer(rid));
						count += userRoleMapper.insertSelective(entity);
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
	public SysUser login(String username, String pswd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateByPrimaryKeySelective(SysUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pagination<SysUser> findByPage(ModelMap map, Integer pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteUserById(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> updateForbidUserById(Long id, Long status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysUser findUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
