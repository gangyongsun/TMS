package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.HashMap;
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
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.manager.TokenManager;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.StringUtils;

@Service
public class RoleServiceImpl extends BaseMybatisDao<SysRoleMapper> implements RoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Override
	public Set<String> findRoleNameByUserId(int userId) {
		return sysRoleMapper.findRoleByUserId(userId);
	}

	@Override
	public Pagination<SysRole> findPage(Map<String, Object> modelMap, int pageNo, int pageSize) {
		return super.findPage(modelMap, pageNo, pageSize);
	}

	@Override
	public int insertSelective(SysRole role) {
		return sysRoleMapper.insertSelective(role);
	}

	@Override
	public Map<String, Object> deleteRoleByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "删除成功！";
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			c: for (String idx : idArray) {
				Integer id = new Integer(idx);
				if (new Integer(1).equals(id)) {
					resultMsg = "操作成功，但是<系统管理员>不能删除！";
					continue c;
				} else {
					count += this.deleteRoleById(id);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public List<SysRole> findNowAllPermission() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", TokenManager.getUserId());
		return sysRoleMapper.findNowAllPermission(map);
	}

	@Override
	public Pagination<RoleResourceAllocationBo> findRoleAndResourcePage(ModelMap modelMap, Integer pageNo, int pageSize) {
		return super.findPage("findRoleAndResources", "findCount", modelMap, pageNo, pageSize);
	}

	@Override
	public Set<String> findRoleByUserId(Integer userId) {
		return sysRoleMapper.findRoleByUserId(userId);
	}

	@Override
	public int deleteRoleById(Integer roleId) {
		return sysRoleMapper.deleteByPrimaryKey(roleId);
	}
}
