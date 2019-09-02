package cn.com.uploadAndDownload.fileUploadDemo.controller;

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
import org.springframework.web.servlet.ModelAndView;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.utils.RequestUtils;

@Controller
@RequestMapping(value = "/auth")
public class LoginController {
	
	/**
	 * 登录跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("auth/login");
	}

	@RequestMapping(value = "submitLogin", method = RequestMethod.POST)
	public ModelAndView submitLogin(String username, String password, HttpServletRequest request) {
		System.out.println(username + "===" + password);
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			SysUser user = (SysUser) subject.getPrincipal();
		} catch (DisabledAccountException e) {
			request.setAttribute("msg", "账户已被禁用");
			return new ModelAndView("auth/login");
//			return "login";
		} catch (AuthenticationException e) {
			request.setAttribute("msg", "用户名或密码错误");
			return new ModelAndView("auth/login");
//			return "login";
		}
		// 执行到这里说明用户已登录成功
		return new ModelAndView("index");
//		return "redirect:/auth/index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String loginSuccessMessage(HttpServletRequest request) {
		String username = "未登录";
		SysUser currentLoginUser = RequestUtils.currentLoginUser();

		if (currentLoginUser != null && StringUtils.isNotEmpty(currentLoginUser.getUserName())) {
			username = currentLoginUser.getUserName();
		} else {
			return "redirect:/auth/login";
		}
		request.setAttribute("username", username);
		return "auth/index";
	}

	// 被踢出后跳转的页面
	@RequestMapping(value = "/kickout", method = RequestMethod.GET)
	public String kickOut() {
		return "auth/kickout";
	}
}