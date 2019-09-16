package cn.com.uploadAndDownload.fileUploadDemo.shiro.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.uploadAndDownload.fileUploadDemo.controller.BaseController;
import cn.com.uploadAndDownload.fileUploadDemo.mybatis.page.Pagination;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.UserOnlineBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.RoleService;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.UserService;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl.CustomSessionManager;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.utils.UserManager;
import cn.com.uploadAndDownload.fileUploadDemo.utils.PropertiesUtil;

/**
 * 用户会员管理
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("member")
public class MemberController extends BaseController {
	/***
	 * 用户手动操作Session
	 */
	@Autowired
	CustomSessionManager customSessionManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/**
	 * 用户列表管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(ModelMap map, Integer pageNo, String findContent) {
		map.put("findContent", findContent);
		Pagination<SysUser> page = userService.findByPage(map, pageNo, pageSize);
		map.put("page", page);
		return new ModelAndView("system/member/list");
	}

	/**
	 * 在线用户管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "online")
	public ModelAndView online(ModelMap map) {
		Pagination<UserOnlineBo> page = new Pagination<UserOnlineBo>();
		List<UserOnlineBo> list = customSessionManager.getAllUser();
		page.setList(list);
		map.put("page", page);
		return new ModelAndView("system/member/online");
	}

	/**
	 * 在线用户详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "onlineDetails/{sessionId}", method = RequestMethod.GET)
	public ModelAndView onlineDetails(@PathVariable("sessionId") String sessionId) {
		UserOnlineBo bo = customSessionManager.getSession(sessionId);
		return new ModelAndView("system/member/onlineDetails", "bo", bo);
	}

	/**
	 * 改变Session状态
	 * 
	 * @param status
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "changeSessionStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds) {
		return customSessionManager.changeSessionStatus(status, sessionIds);
	}

	/**
	 * 根据ID删除，
	 * 
	 * @param ids 如果有多个，以“,”间隔。
	 * @return
	 */
	@RequestMapping(value = "deleteUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserById(String ids) {
		return userService.deleteUserById(ids);
	}

	/**
	 * 禁止登录
	 * 
	 * @param id     用户ID
	 * @param status 1:有效，0:禁止登录
	 * @return
	 */
	@RequestMapping(value = "forbidUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> forbidUserById(Long id, Long status) {
		return userService.updateForbidUserById(id, status);
	}

	/**
	 * 密码重置
	 * 
	 * @return
	 */
	@RequestMapping(value = "resetPasswd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPasswd(String id, String newPswd) {
		Integer userId = null;
		if (null != id && !"".equals(id)) {
			userId = new Integer(id);
		}
		SysUser user = userService.findUserById(userId);

		Set<String> roleSet = roleService.findRoleByUserId(userId);
		String admin_role_type = PropertiesUtil.getValueByKey("ADMIN_ROLE_TYPE", "config.properties");
		if (null != roleSet && roleSet.contains(admin_role_type)) {
			resultMap.put("status", 300);
			resultMap.put("message", "管理员密码不允许重置！");
			return resultMap;
		}

		user.setPassWord(newPswd);
		user = UserManager.md5Pswd(user);
		userService.updateByPrimaryKeySelective(user);
		resultMap.put("status", 200);
		resultMap.put("message", "密码重置成功!");
		return resultMap;
	}

}
