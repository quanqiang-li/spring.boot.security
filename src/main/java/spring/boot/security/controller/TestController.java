package spring.boot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("sayHello")
	@ResponseBody
	public String sayHello(String name) {
		return "hello:" + name;
	}
}
