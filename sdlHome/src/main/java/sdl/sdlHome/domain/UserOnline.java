package sdl.sdlHome.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserOnline extends User implements Serializable {

	private static final long serialVersionUID = -7762854258616806800L;

	/**
	 * Session Id
	 */
	private String sessionID;

	/**
	 * Session Host
	 */
	private String host;

	/**
	 * Session 创建时间
	 */
	private Date startTime;

	/**
	 * Session 最后交互时间
	 */
	private Date lastAccess;

	/**
	 * Session timeout
	 */
	private long timeout;

	/**
	 * Session 是否踢出
	 */
	private boolean sessionStatus = Boolean.TRUE;

}
