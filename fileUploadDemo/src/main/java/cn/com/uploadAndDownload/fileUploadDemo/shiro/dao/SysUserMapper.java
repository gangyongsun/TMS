package cn.com.uploadAndDownload.fileUploadDemo.shiro.dao;

import java.util.List;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper extends Mapper<SysUser> {

	List<SysRoleBo> selectRoleByUserId(int id);
}