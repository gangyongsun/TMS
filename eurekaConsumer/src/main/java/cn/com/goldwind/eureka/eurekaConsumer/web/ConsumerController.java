package cn.com.goldwind.eureka.eurekaConsumer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.goldwind.eureka.eurekaConsumer.client.HelloRemote;

@RestController
public class ConsumerController {

	@Autowired
	HelloRemote helloRemote;

	@RequestMapping("/hello/{name}")
	public String index(@PathVariable("name") String name) {
		return helloRemote.hello(name);
	}
}
