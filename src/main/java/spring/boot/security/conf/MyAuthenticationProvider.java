package spring.boot.security.conf;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 认证
 * @author liqq
 *
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//认证失败,如果返回null,则providerManager还配置了其他provider的话则继续验证
		//如果抛出异常则,终止
		//成功返还Authentication对象
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//支持所有认证
		return true;
	}

	
}
