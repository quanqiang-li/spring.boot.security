package spring.boot.security.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 第三步 参考{@link DaoAuthenticationProvider} 短信认证
 * 
 * @author liqq
 *
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 认证失败,如果返回null,则providerManager还配置了其他provider的话则继续验证
		// 如果抛出异常则,终止
		// 成功返还Authentication对象
		MyAuthenticationToken authenticationToken = (MyAuthenticationToken) authentication;
		String mobile = (String) authenticationToken.getPrincipal();
		String smsCode = (String) authenticationToken.getCredentials();

		//加载用户详细信息,角色信息
		UserDetails details = userDetailsService.loadUserByUsername(mobile);

		logger.info("手机{}和短信{}", mobile, smsCode);
		if (smsCode==null) {
			//验证短信验证码
			throw new BadCredentialsException("请填写正确的验证码");
		}
		if(details == null){
			//用户不存在,创建用户
		}
		//验证通过后,把用户
		authenticationToken.setDetails(details);
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// 支持所有认证
		return MyAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
