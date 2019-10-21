package cn.com.uploadAndDownload.fileUploadDemo.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * shiro Session操作
 * 
 * @author alvin
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Component 
public class CustomShiroSessionDAO extends AbstractSessionDAO {
	
	@Autowired
	private ShiroSessionRepository shiroSessionRepository;

	/**
	 * 更新session
	 */
	@Override
	public void update(Session session) throws UnknownSessionException {
		shiroSessionRepository.saveSession(session);
	}

	/**
	 * 删除session
	 */
	@Override
	public void delete(Session session) {
		if (session == null) {
			LoggerUtils.error(getClass(), "Session 不能为null");
			return;
		}
		Serializable id = session.getId();
		if (id != null) {
			shiroSessionRepository.deleteSession(id);
		}
	}

	/**
	 * 获取活跃状态的session
	 */
	@Override
	public Collection<Session> getActiveSessions() {
		return shiroSessionRepository.getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		shiroSessionRepository.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return shiroSessionRepository.getSession(sessionId);
	}
}
