package spring.boot.security.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级别的权限认证
public class SmsSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

	@Autowired
	private SmsAuthenticationConfig smsAuthenticationConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin() // 表单登录
				.loginPage("/html/login.html") // 登录跳转 URL
				.loginProcessingUrl("/login") // 处理表单登录 URL,默认
				.successHandler(myAuthenticationSuccessHandler) // 处理登录成功
				.failureHandler(myAuthenticationFailureHandler) // 处理登录失败
				.and().authorizeRequests() // 授权配置
				.antMatchers("/html/login.html", "/code/sms").permitAll() // 无需认证的请求路径
				.anyRequest() // 所有请求
				.authenticated() // 都需要认证
				.and().csrf().disable().apply(smsAuthenticationConfig);
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}