package spring.boot.security.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Order(1)
@Configuration
@EnableWebSecurity
public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	// https://stackoverflow.com/questions/22998731/httpsecurity-websecurity-and-authenticationmanagerbuilder

	// https://stackoverflow.com/questions/48628389/how-to-configure-httpsecurity-for-this-situation-spring-boot

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
		http.authenticationProvider(myAuthenticationProvider).addFilterAfter(new MyAuthenticationFilter("/*"), UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/html/**").permitAll().and().formLogin().loginPage("/html/login.html").and().authorizeRequests().anyRequest().authenticated();
	}

	// 处理认证
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		log.info("配置AuthenticationManagerBuilder");
		try {
			auth.authenticationProvider(myAuthenticationProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
