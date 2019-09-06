package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.Map;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUserRole;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserRoleMapper extends Mapper<SysUserRole> {

	void deleteByUserId(int userId);

	void deleteRoleByUserIds(Map<String, Object> resultMap);
}