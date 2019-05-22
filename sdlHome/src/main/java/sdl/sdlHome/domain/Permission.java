package sdl.sdlHome.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 权限实体类
 * 
 * @author alvin
 *
 */
@Data
public class Permission implements Serializable {

	private static final long serialVersionUID = 6023130417403648636L;

	private long id;

	/**
	 * 权限描述
	 */
	private String description;

	/**
	 * 操作url
	 */
	private String url;

	/**
	 * 操作名称
	 */
	private String name;

	/**
	 * 是否可用
	 */
	private boolean available;
}
