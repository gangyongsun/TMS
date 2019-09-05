package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.Set;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;

public interface UserService {
    /***
     * 根据用户信息获取用户
     *
     * @param user
     * @return
     */
    SysUser getUser(SysUser user);

    /**
     * 根据userId获取用户权限
     *
     * @param userId userId
     * @return 用户权限
     */
    Set<String> findPermissionsByUserId(int userId);
}
