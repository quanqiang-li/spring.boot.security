package spring.boot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 短信登录
 * {@link https://mrbird.cc/Spring-Security-SmsCode.html }
 * @author carl
 *
 */
@SpringBootApplication
public class Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
