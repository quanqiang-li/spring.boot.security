package spring.boot.security.conf;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 提供认证
 * 
 * @author carl
 *
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

	private UserDetailService userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
		UserDetails userDetails = userDetailService.loadUserByUsername((String) authenticationToken.getPrincipal());
		// 验证短信正确性,此处无法获取session的值,所以在过滤器的时候验证,或者把短信验证码存在redis,跟手机号绑定,这里就可以
		
		
		if (userDetails == null) {
			throw new InternalAuthenticationServiceException("未找到与该手机号对应的用户");
		}

		SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(authenticationToken.getPrincipal(),
				authenticationToken.getCredentials(), userDetails.getAuthorities());

		authenticationResult.setDetails(authenticationToken.getDetails());

		return authenticationResult;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return SmsAuthenticationToken.class.isAssignableFrom(aClass);
	}

	public UserDetailService getUserDetailService() {
		return userDetailService;
	}

	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
}
