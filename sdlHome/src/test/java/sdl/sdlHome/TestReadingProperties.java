package sdl.sdlHome;

import sdl.sdlHome.util.PropertiesUtil;

/**
 * 
 * @author alvin
 *
 */
public class TestReadingProperties {
	public static void main(String[] args) {
		String value=PropertiesUtil.getValueByKey("BASE_SERVER_URL","config.properties");
		System.out.println(value);
	}
}
