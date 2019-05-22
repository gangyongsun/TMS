package sdl.sdlHome.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class RolePermission implements Serializable {

	private static final long serialVersionUID = -6778126365325083902L;

	private long rid;

	private long pid;
}
