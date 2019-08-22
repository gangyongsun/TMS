package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.mapper.SysRoleMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> findRoleNameByUserId(int userId) {
        return sysRoleMapper.findRoleNameByUserId(userId);
    }
}
