package spring.boot.security.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//https://www.jianshu.com/p/18875c2995f1

	@Autowired
	private MyAuthenticationProvider myAuthenticationProvider;

	// 处理环境
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("配置webSecurity");
		// 开放资源
		web.ignoring().antMatchers("/resources/**", "/js/**", "/css/**");
	}

	// 处理授权
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("配置HttpSecurity");
		log.info("myAuthenticationProvider:{}", myAuthenticationProvider);
		MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter();
		myAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		log.info("myAuthenticationProvider.userDetailsService:{}",myAuthenticationProvider.userDetailsService);
		http.authenticationProvider(myAuthenticationProvider);
		http.addFilterAfter(myAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.formLogin().loginPage("/html/login.html");
		http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests().antMatchers("/html/**").permitAll();
	}

}
