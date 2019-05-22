package sdl.sdlHome.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 角色实体类
 * 
 * @author alvin
 *
 */
@Data
public class Role implements Serializable {
	private static final long serialVersionUID = -505512148969974749L;

	private long id;

	/**
	 * 角色标识
	 */
	private String name;

	/**
	 * 角色描述
	 */
	private String description;

	/**
	 * 角色类型
	 */
	private String type;

	/**
	 * 是否可用
	 */
	private boolean avaliable;

}
