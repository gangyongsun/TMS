package cn.com.uploadAndDownload.fileUploadDemo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.token.manager.TokenManager;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.utils.RequestUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;

@Controller
@RequestMapping(value = "/auth")
public class LoginController extends BaseController {
	/**
	 * 登录跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "auth/login";
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitLogin", method = RequestMethod.POST)
	public String submitLogin(String username, String password, HttpServletRequest request) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			// SysUser user = (SysUser) subject.getPrincipal();
		} catch (DisabledAccountException e) {
			request.setAttribute("msg", "账户已被禁用");
			return "auth/login";
		} catch (AuthenticationException e) {
			request.setAttribute("msg", "用户名或密码错误");
			return "auth/login";
		}
		// 执行到这里说明用户已登录成功
		return "redirect:index";
	}

	/**
	 * 登录成功后执行的方法，由submitLogin中返回redirect:index重定向到此方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String loginSuccessMessage(HttpServletRequest request) {
		String username = "未登录";
		SysUser currentLoginUser = RequestUtils.currentLoginUser();

		if (currentLoginUser != null && StringUtils.isNotEmpty(currentLoginUser.getUserName())) {
			username = currentLoginUser.getUserName();
		} else {
			return "auth/login";
		}
		request.setAttribute("username", username);
		return "index";
	}

	
	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		try {
			TokenManager.logout();
		} catch (Exception e) {
			logger.error("errorMessage:" + e.getMessage());
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
		}
		System.out.println("kickout");
		return "auth/kickout";
	}
	
	/**
	 * 被踢出后跳转的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kickout", method = RequestMethod.GET)
	public String kickOut() {
		return "auth/kickout";
	}

}