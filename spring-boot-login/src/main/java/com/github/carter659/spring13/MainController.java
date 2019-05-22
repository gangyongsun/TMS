package com.github.carter659.spring13;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {

	@GetMapping("/")
	public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String username, Model model) {
		model.addAttribute("username", username);
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/loginPost")
	public @ResponseBody Map<String, Object> loginPost(String username, String password, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		if (!"123456".equals(password)) {
			map.put("success", false);
			map.put("message", "密码错误");
			return map;
		}
		System.out.println("username=" + username);
		session.setAttribute(WebSecurityConfig.SESSION_KEY, username);// 设置session

		map.put("success", true);
		map.put("message", "登录成功");
		return map;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);// 移除session
		return "redirect:/login";
	}

}
