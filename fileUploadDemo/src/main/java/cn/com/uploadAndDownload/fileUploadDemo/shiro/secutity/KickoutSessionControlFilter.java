package cn.com.uploadAndDownload.fileUploadDemo.shiro.secutity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class KickoutSessionControlFilter extends AccessControlFilter {

	//踢出后到的地址
	private String kickoutUrl;

	//踢出之前/之后登录的用户,默认踢出之前登录的用户
	private boolean kickoutAfter = false;

	//同一个帐号最大会话数,默认1
	private int maxSession = 1;

	private SessionManager sessionManager;

	private Cache<String, Deque<Serializable>> cache;

	/**
	 * 设置Cache的key的前缀
	 * 
	 * @param cacheManager
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("shiro_redis_cache");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}

		Session session = subject.getSession();
		Serializable sessionId = session.getId();

		SysUser user = (SysUser) subject.getPrincipal();
		String username = user.getUserName();

		// 读取缓存 没有就存入
		Deque<Serializable> deque = cache.get(username);

		// 如果此用户没有session队列，也就是还没有登录过，缓存中没有就new一个空队列，不然deque对象为空，会报空指针
		if (deque == null) {
			deque = new LinkedList<Serializable>();
		}

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
			// 将sessionId存入队列
			deque.push(sessionId);
			// 将用户的sessionId队列缓存
			cache.put(username, deque);
		}

		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if (kickoutAfter) { // 踢出后者
				kickoutSessionId = deque.removeFirst();
			} else { // 踢出前者
				kickoutSessionId = deque.removeLast();
			}
			// 踢出后再更新下缓存队列
			cache.put(username, deque);
			try {
				// 获取被踢出的sessionId的session对象
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e) {// ignore exception
				e.printStackTrace();
			}
		}

		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if (session.getAttribute("kickout") != null) {
			// 会话被踢出了
			try {
				// 退出登录
				subject.logout();
			} catch (Exception e) { // ignore
				e.printStackTrace();
			}
			saveRequest(request);

			Map<String, String> resultMap = new HashMap<String, String>();
			// 判断是不是Ajax请求
			if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
				resultMap.put("user_status", "300");
				resultMap.put("message", "您已经在其他地方登录，请重新登录！");
				// 输出json串
				out(response, resultMap);
			} else {
				// 重定向
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
			return false;
		}
		return true;
	}

	private void out(ServletResponse hresponse, Map<String, String> resultMap) throws IOException {
		PrintWriter printWriter = null;
		try {
			hresponse.setCharacterEncoding("UTF-8");
			printWriter = hresponse.getWriter();
			printWriter.println(JSON.toJSONString(resultMap));
			printWriter.flush();
		} catch (Exception e) {
			System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
		} finally {
			if (null != printWriter) {
				printWriter.close();
				printWriter = null;
			}
		}
	}
}