package cn.com.uploadAndDownload.fileUploadDemo.shiro.bo;

import java.io.Serializable;
import java.util.Date;

import cn.com.uploadAndDownload.fileUploadDemo.shiro.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Session + User Bo
 * 
 * @author alvin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserOnlineBo extends SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Session Id
	 */
	private String sessionId;

	/**
	 * Session Host
	 */
	private String host;

	/**
	 * Session创建时间
	 */
	private Date startTime;

	/**
	 * Session最后交互时间
	 */
	private Date lastAccess;

	/**
	 * Session timeout
	 */
	private long timeout;

	/**
	 * session 是否踢出
	 */
	private boolean sessionStatus = Boolean.TRUE;

	public UserOnlineBo(SysUser user) {
		super(user);
	}

}
