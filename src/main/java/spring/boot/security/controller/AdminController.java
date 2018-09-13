package spring.boot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

	
	@GetMapping("sayHello")
	@ResponseBody
	public String sayHello(String name){
		System.out.println(name);
		return "hello:"+name;
	}
}
