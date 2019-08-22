package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.mapper.SysResourcesMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.mapper.SysUserMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysResourcesMapper sysResourcesMapper;

    @Override
    public SysUser getUser(SysUser user) {
        SysUser info = userMapper.selectOne(user);
        return info;
    }

    @Override
    public Set<String> findPermissionsByUserId(int userId) {
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
}
