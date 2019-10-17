package cn.com.uploadAndDownload.fileUploadDemo.shiro.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.bo.UserOnlineBo;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.ShiroSessionRepository;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.dao.shiroSession.CustomShiroSessionDAO;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.vo.SessionStatus;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.StringUtils;

/**
 * 用户Session 手动管理
 * 
 * @author alvin
 *
 */
@Service
public class CustomSessionManager {

	/**
	 * session status
	 */
	public static final String SESSION_STATUS = "tms-online-status";

	@Autowired
	ShiroSessionRepository shiroSessionRepository;

	@Autowired
	AbstractSessionDAO customShiroSessionDAO;

	/**
	 * 获取所有的有效Session用户
	 * 
	 * @return
	 */
	public List<UserOnlineBo> getAllUser() {
		// 获取所有session
		Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
		List<UserOnlineBo> userOnlineList = new ArrayList<UserOnlineBo>();

		for (Session session : sessions) {
			UserOnlineBo userOnline = getSessionBo(session);
			if (null != userOnline) {
				userOnlineList.add(userOnline);
			}
		}
		return userOnlineList;
	}

	/**
	 * 根据ID查询 SimplePrincipalCollection
	 * 
	 * @param userIds 用户ID
	 * @return
	 */
	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Integer... userIds) {
		// 把userIds 转成Set，好判断
		Set<Integer> idset = (Set<Integer>) StringUtils.array2Set(userIds);
		// 获取所有session
		Collection<Session> sessions = customShiroSessionDAO.getActiveSessions();
		// 定义返回
		List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();
		for (Session session : sessions) {
			// 获取SimplePrincipalCollection
			Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if (null != obj && obj instanceof SimplePrincipalCollection) {
				// 强转
				SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
				// 判断用户，匹配用户ID
				obj = spc.getPrimaryPrincipal();
				if (null != obj && obj instanceof SysUser) {
					SysUser user = (SysUser) obj;
					// 比较用户ID，符合即加入集合
					if (null != user && idset.contains(user.getId())) {
						list.add(spc);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取单个Session
	 * 
	 * @param sessionId
	 * @return
	 */
	public UserOnlineBo getSession(String sessionId) {
		Session session = shiroSessionRepository.getSession(sessionId);
		UserOnlineBo bo = getSessionBo(session);
		return bo;
	}

	private UserOnlineBo getSessionBo(Session session) {
		// 获取session登录信息
		Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		// 确保是 SimplePrincipalCollection对象
		if (null != obj && obj instanceof SimplePrincipalCollection) {
			SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
			/**
			 * 获取用户登录
			 * 
			 * @link SampleRealm.doGetAuthenticationInfo(...)方法中 return new
			 *       SimpleAuthenticationInfo(user,user.getPswd(), getName()); 的user 对象。
			 */
			obj = spc.getPrimaryPrincipal();

			if (null != obj && obj instanceof SysUser) {
				// 存储session + user 综合信息
				UserOnlineBo userOnlineBo = new UserOnlineBo((SysUser) obj);
				// 最后一次和系统交互的时间
				userOnlineBo.setLastAccess(session.getLastAccessTime());
				// 主机的ip地址
				userOnlineBo.setHost(session.getHost());
				// session ID
				userOnlineBo.setSessionId(session.getId().toString());
				// session最后一次与系统交互的时间
				userOnlineBo.setLastLoginTime(session.getLastAccessTime());
				// 会话到期 ttl(ms)
				userOnlineBo.setTimeout(session.getTimeout());
				// session创建时间
				userOnlineBo.setStartTime(session.getStartTimestamp());
				// 是否踢出
				SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
				boolean status = Boolean.TRUE;
				if (null != sessionStatus) {
					status = sessionStatus.getOnlineStatus();
				}
				userOnlineBo.setSessionStatus(status);
				return userOnlineBo;
			}
		}
		return null;
	}

	/**
	 * 改变Session状态
	 * 
	 * @param status    {true:踢出,false:激活}
	 * @param sessionId
	 * @return
	 */
	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String[] sessionIdArray = null;
			if (sessionIds.indexOf(",") == -1) {
				sessionIdArray = new String[] { sessionIds };
			} else {
				sessionIdArray = sessionIds.split(",");
			}
			for (String sessionId : sessionIdArray) {
				Session session = shiroSessionRepository.getSession(sessionId);
				//TODO getSession拿不到
				SessionStatus sessionStatus = new SessionStatus();
				sessionStatus.setOnlineStatus(status);

				session.setAttribute(SESSION_STATUS, sessionStatus);
				customShiroSessionDAO.update(session);
			}
			map.put("status", 200);
			map.put("message", "状态改变成功！");
//			map.put("sessionStatus", status ? 1 : 0);
//			map.put("sessionStatusText", status ? "踢出" : "激活");
//			map.put("sessionStatusTextTd", status ? "有效" : "已踢出");
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "改变Session状态错误，sessionId[%s]", sessionIds);
			map.put("status", 500);
			map.put("message", "状态改变失败，Session可能不存在，请刷新再试！");
		}
		return map;
	}

	/**
	 * 查询要禁用的用户是否在线。
	 * 
	 * @param id     用户ID
	 * @param status 用户状态
	 */
	public void forbidUserById(Integer id, Integer status) {
		// 获取所有在线用户
		List<UserOnlineBo> userOnlineList = getAllUser();
		if (null != userOnlineList) {
			for (UserOnlineBo userOnlineBo : userOnlineList) {
				Integer userId = userOnlineBo.getId();
				// 匹配用户ID
				if (null != userId && userId.equals(id)) {
					// 获取用户Session
					Session session = shiroSessionRepository.getSession(userOnlineBo.getSessionId());
					// 标记用户Session
					SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
					// 是否踢出 true:有效，false：踢出
					sessionStatus.setOnlineStatus(status.intValue() == 1);
					// 更新Session
					customShiroSessionDAO.update(session);
				}
			}
		}
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

	public void setCustomShiroSessionDAO(CustomShiroSessionDAO customShiroSessionDAO) {
		this.customShiroSessionDAO = customShiroSessionDAO;
	}
}
