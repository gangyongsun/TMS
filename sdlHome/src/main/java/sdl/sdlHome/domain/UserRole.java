package sdl.sdlHome.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRole implements Serializable {

	private static final long serialVersionUID = 6146076245639593828L;

	private long uid;

	private long rid;
}
