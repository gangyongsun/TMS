package sdl.sdlHome.domain;

public enum UserStatus {
	SUCCESS(1, "有效"), FAILED(0, "无效");

	private int value;
	private String desc;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private UserStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

}
