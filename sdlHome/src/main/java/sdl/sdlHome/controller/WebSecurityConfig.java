package sdl.sdlHome.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录配置
 * 
 * @author alvin
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
	/**
	 * 登录session key
	 */
	public final static String SESSION_KEY = "user";
	
	/**
	 * 登录session token
	 */
	public final static String SESSION_TOKEN = "token";

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		// 排除配置
//		addInterceptor.excludePathPatterns("/error");//调试error模板用
		addInterceptor.excludePathPatterns("/login**");
		addInterceptor.excludePathPatterns("/css/**");
		addInterceptor.excludePathPatterns("/js/**");
		addInterceptor.excludePathPatterns("/img/**");
		addInterceptor.excludePathPatterns("/images/**");
		addInterceptor.excludePathPatterns("/fonts/**");

		// 拦截配置
		addInterceptor.addPathPatterns("/**");
	}

	private class SecurityInterceptor extends HandlerInterceptorAdapter {
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			HttpSession session = request.getSession();
			System.out.println("seesion user:" + session.getAttribute(SESSION_KEY));
			if (session.getAttribute(SESSION_KEY) != null) {
				return true;
			} else {
				// 跳转登录
				response.sendRedirect("/login");
				return false;
			}
		}
	}
}
