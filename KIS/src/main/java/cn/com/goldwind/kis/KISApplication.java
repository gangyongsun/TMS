package cn.com.goldwind.kis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@ComponentScan("cn.com.goldwind.kis")
@MapperScan("cn.com.goldwind.kis.repository")
public class KISApplication extends WebMvcConfigurationSupport {

	public static void main(String[] args) {
		SpringApplication.run(KISApplication.class, args);
	}

	/**
	 * 这里配置静态资源文件的路径导包都是默认的直接导入就可以
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
		super.addResourceHandlers(registry);
	}

}
