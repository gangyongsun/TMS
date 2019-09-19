package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.uploadAndDownload.fileUploadDemo.mybatis.BaseMybatisDao;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.RoleResourceAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.SysRoleMapper;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysRole;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.RoleService;

@Service
public class RoleServiceImpl extends BaseMybatisDao<SysRoleMapper> implements RoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public Set<String> findRoleNameByUserId(int userId) {
		return sysRoleMapper.findRoleNameByUserId(userId);
	}

	@Override
	public Pagination<SysRole> findPage(Map<String, Object> modelMap, int pageNo, int pageSize) {
		return super.findPage(modelMap, pageNo, pageSize);
	}

	@Override
	public int insertSelective(SysRole role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> deleteRoleById(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysRole> findNowAllPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pagination<RoleResourceAllocationBo> findRoleAndResourcePage(ModelMap modelMap, Integer pageNo, int pageSize) {
		return super.findPage("findRoleAndResources", "findCount", modelMap, pageNo, pageSize);
	}

	@Override
	public Set<String> findRoleByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
