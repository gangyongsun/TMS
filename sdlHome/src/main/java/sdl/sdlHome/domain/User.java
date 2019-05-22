package sdl.sdlHome.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户实体类
 * 
 * @author alvin
 *
 */
@Data
public class User implements Serializable {
	private static final long serialVersionUID = 4842107090290092404L;

	private long id;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 邮箱 | 登录帐号
	 */
	private String email;

	/**
	 * 密码
	 */
	private transient String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 1:有效，0:禁止登录
	 */
	private int status;
}
