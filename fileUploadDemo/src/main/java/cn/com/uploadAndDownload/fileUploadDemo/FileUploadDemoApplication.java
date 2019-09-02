package cn.com.uploadAndDownload.fileUploadDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.com.uploadAndDownload.fileUploadDemo.shiro.dao")
public class FileUploadDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadDemoApplication.class, args);
	}

}
