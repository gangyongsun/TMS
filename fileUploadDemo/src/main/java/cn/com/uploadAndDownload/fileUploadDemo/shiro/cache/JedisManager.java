package cn.com.uploadAndDownload.fileUploadDemo.shiro.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import cn.com.uploadAndDownload.fileUploadDemo.utils.LoggerUtils;
import cn.com.uploadAndDownload.fileUploadDemo.utils.SerializeUtil;
import cn.com.uploadAndDownload.fileUploadDemo.utils.StringUtils;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Redis Manager Utils
 * 
 * @author alvin
 *
 */
@Component
@Data
public class JedisManager {

	/**
	 * 这个地方尝试让spring注入，但是spring要求在配置里配置jedisPool Bean
	 */
	private JedisPool jedisPool;

	/**
	 * 从Redis池获取Redis对象
	 * @return
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			String message = StringUtils.trim(e.getMessage());
			if ("Could not get a resource from the pool".equalsIgnoreCase(message)) {
				System.out.println("++++++++++请检查是否安装并启动redis服务++++++++");
				System.out.println("|①.请检查redis启动是否带配置文件启动，也就是是否有密码，是否端口有变化（默认6379）|");
				System.out.println("项目退出中....生产环境中，请删除这些东西");
				System.exit(0);// 停止项目
			}
			throw new JedisConnectionException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return jedis;
	}

	/**
	 * 获取所有Session
	 * 
	 * @param dbIndex
	 * @param redisShiroSession
	 * @return
	 * @throws Exception
	 */
	public Collection<Session> AllSession(int dbIndex, String redisShiroSession) {
		Jedis jedis = null;
		boolean isBroken = false;
		Set<Session> sessions = new HashSet<Session>();
		try {
			jedis = getJedis();
			jedis.select(dbIndex);

			Set<byte[]> byteKeys = jedis.keys((JedisShiroSessionRepository.REDIS_SHIRO_ALL).getBytes());
			if (byteKeys != null && byteKeys.size() > 0) {
				for (byte[] byteKey : byteKeys) {
					Session obj = SerializeUtil.deserialize(jedis.get(byteKey), Session.class);
					if (obj instanceof Session) {
						sessions.add(obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jedis, isBroken);
		}
		return sessions;
	}

	public void returnResource(Jedis jedis, boolean isBroken) {
		if (jedis == null) {
			return;
		}
		jedis.close();
	}

	public byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
		Jedis jedis = null;
		byte[] result = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			result = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
		return result;
	}

	public void deleteByKey(int dbIndex, byte[] key) throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			Long result = jedis.del(key);
			LoggerUtils.fmtDebug(getClass(), "删除Session结果：%s", result);
		} catch (Exception e) {
			e.printStackTrace();
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
	}

	public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime) throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			jedis.set(key, value);
			if (expireTime > 0)
				jedis.expire(key, expireTime);
		} catch (Exception e) {
			e.printStackTrace();
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
	}

}
