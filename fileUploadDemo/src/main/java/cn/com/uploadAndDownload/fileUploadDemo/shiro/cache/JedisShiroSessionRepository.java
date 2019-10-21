package cn.com.uploadAndDownload.fileUploadDemo.shiro.cache;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.session.CustomSessionManager;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.session.SessionStatus;
import cn.com.uploadAndDownload.fileUploadDemo.shiro.session.ShiroSessionRepository;
import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.SerializeUtil;
import lombok.Data;

/**
 * Session 管理
 * 
 * @author alvin
 */
@Component
@Data
public class JedisShiroSessionRepository implements ShiroSessionRepository {
	public static final String REDIS_SHIRO_SESSION = "goldwind-tms-shiro-session:";
	/**
	 * 这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
	 */
	public static final String REDIS_SHIRO_ALL = "*goldwind-tms-shiro-session:*";
//	private static final int SESSION_VAL_TIME_SPAN = 18000;
	private static final int DB_INDEX = 1;

	@Autowired
	private JedisManager jedisManager;

	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			throw new NullPointerException("session is null, cannot save the session!");
		}
		try {
			byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));
			// 不存在才添加
			if (null == session.getAttribute(CustomSessionManager.SESSION_STATUS)) {
				//Session 踢出自存存储
				SessionStatus sessionStatus = new SessionStatus();
				session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
			}

			byte[] value = SerializeUtil.serialize(session);
			jedisManager.saveValueByKey(DB_INDEX, key, value, (int) (session.getTimeout() / 1000));
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]", session.getId());
		}
	}

	@Override
	public void deleteSession(Serializable id) {
		if (id == null) {
			throw new NullPointerException("session id is empty,cannot delete the session!");
		}
		try {
			jedisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(buildRedisSessionKey(id)));
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]", id);
		}
	}

	@Override
	public Session getSession(Serializable id) {
		if (id == null) {
			throw new NullPointerException("session id is empty,cannot get the session!");
		}
		Session session = null;
		try {
			byte[] value = jedisManager.getValueByKey(DB_INDEX, SerializeUtil.serialize(buildRedisSessionKey(id)));
			session = SerializeUtil.deserialize(value, Session.class);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]", id);
		}
		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		Collection<Session> sessions = null;
		try {
			sessions = jedisManager.AllSession(DB_INDEX, REDIS_SHIRO_SESSION);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
		}

		return sessions;
	}

	/**
	 * 组合session Key
	 * @param sessionId
	 * @return
	 */
	private String buildRedisSessionKey(Serializable sessionId) {
		return REDIS_SHIRO_SESSION + sessionId;
	}

}
