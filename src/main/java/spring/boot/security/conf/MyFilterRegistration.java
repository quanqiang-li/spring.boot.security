package spring.boot.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 注册过滤器,
 * 
 * @author liqq
 *
 */
@Configuration
public class MyFilterRegistration {

	@Autowired
	private MyFilter myFilter;

	// 一次只注册一个
	@Bean
	public FilterRegistrationBean<MyFilter> register() {
		FilterRegistrationBean<MyFilter> register = new FilterRegistrationBean<>();
		register.setFilter(myFilter);
		// 不要写/**
		register.addUrlPatterns("/*");
		register.setOrder(Ordered.HIGHEST_PRECEDENCE + 200);
		return register;
	}
}
