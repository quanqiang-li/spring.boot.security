package spring.boot.security.conf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * 第二步
 * 实现短信登录过滤器 {@link UsernamePasswordAuthenticationFilter}
 * @author liqq
 *
 */

public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private String usernameParameter = "username";
	private String smsCodeParameter = "smsCode";

	protected MyAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		logger.info("执行过滤器,组装Token");
		String username = obtainUsername(request);
		String smsCode = obtainSmsCode(request);

		if (username == null) {
			username = "";
		}

		if (smsCode == null) {
			smsCode = "";
		}

		username = username.trim();
		MyAuthenticationToken myAuthenticationToken = new MyAuthenticationToken(username, smsCode);
		setDetails(request, myAuthenticationToken);
		return this.getAuthenticationManager().authenticate(myAuthenticationToken);
	}

	protected String obtainSmsCode(HttpServletRequest request) {
		return request.getParameter(smsCodeParameter);
	}
	
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	protected void setDetails(HttpServletRequest request,
			MyAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
}
