package sdl.sdlHome.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import sdl.sdlHome.service.TerminologyService;

/**
 * 
 * @author alvin
 *
 */
@Controller
public class MainController {
	public static final String ERROR="error";
	
	@Autowired
	@Qualifier("terminologyService")
	public TerminologyService termService;

	@GetMapping("/")
	public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String username, Model model) {
		model.addAttribute("username", username);
		return "login";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/loginPost")
	public @ResponseBody Map<String, Object> loginPost(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		String token = termService.getToken(username, password);
		System.out.println("token=" + token);
		if (ERROR != token) {
			session.setAttribute(WebSecurityConfig.SESSION_KEY, username);
			session.setAttribute(WebSecurityConfig.SESSION_TOKEN, token);
			System.out.println(session.getAttribute(WebSecurityConfig.SESSION_KEY) + " 登录成功：TOKEN is:\n" + session.getAttribute(WebSecurityConfig.SESSION_TOKEN));
			map.put("success", true);
			map.put("message", "登录成功");
		} else {
			map.put("success", false);
			map.put("message", "密码错误");
		}
		return map;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		return "redirect:/login";
	}

}
