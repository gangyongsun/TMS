package cn.com.uploadAndDownload.fileUploadDemo.shiro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.uploadAndDownload.fileUploadDemo.controller.BaseController;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.SysRoleBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.UserRoleAllocationBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.ResourcesService;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.UserService;

/**
 * 用户角色分配
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("role")
public class RoleAllocationController extends BaseController {
	@Autowired
	UserService userService;

	@Autowired
	ResourcesService permissionService;

	/**
	 * 用户角色权限分配
	 * 
	 * @param modelMap
	 * @param pageNo
	 * @param findContent
	 * @return
	 */
	@RequestMapping(value = "allocation")
	public ModelAndView allocation(ModelMap modelMap, Integer pageNo, String findContent) {
		modelMap.put("findContent", findContent);
		Pagination<UserRoleAllocationBo> userRoleBoPage = userService.findUserAndRole(modelMap, pageNo, pageSize);
		modelMap.put("page", userRoleBoPage);
		return new ModelAndView("system/role/allocation");
	}

	/**
	 * 根据用户ID查询权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectRoleByUserId")
	@ResponseBody
	public List<SysRoleBo> selectRoleByUserId(Integer id) {
		List<SysRoleBo> sysRoleBoList = userService.selectRoleByUserId(id);
		return sysRoleBoList;
	}

	/**
	 * 操作用户的角色
	 * 
	 * @param userId  用户ID
	 * @param roleIds 角色ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "addRole2User")
	@ResponseBody
	public Map<String, Object> addRole2User(Integer userId, String roleIds) {
		return userService.addRole2User(userId, roleIds);
	}

	/**
	 * 根据用户id清空角色
	 * 
	 * @param userIds 用户ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "clearRoleByUserIds")
	@ResponseBody
	public Map<String, Object> clearRoleByUserIds(String userIds) {
		return userService.deleteRoleByUserIds(userIds);
	}
}
