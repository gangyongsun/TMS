package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.UserRoleAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysUserMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.UserService;

@Service
public class UserServiceImpl implements UserService {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysRoleBo> selectRoleByUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> addRole2User(Long userId, String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteRoleByUserIds(String userIds) {
		// TODO Auto-generated method stub
		return null;
	}
}
