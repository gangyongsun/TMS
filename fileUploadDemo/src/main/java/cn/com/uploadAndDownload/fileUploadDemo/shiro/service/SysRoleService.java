package cn.com.uploadAndDownload.fileUploadDemo.shiro.service;

import java.util.Set;

public interface SysRoleService {
    Set<String> findRoleNameByUserId(int userId);
}
