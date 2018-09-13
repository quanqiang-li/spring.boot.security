package spring.boot.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

	
	@GetMapping("doLogin")
	public String sayHello(String name){
		return "doLogin:"+name;
	}
}
