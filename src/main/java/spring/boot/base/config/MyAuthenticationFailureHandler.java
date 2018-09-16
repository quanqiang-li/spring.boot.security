package spring.boot.base.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());



	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// String targetUrl = savedRequest.getRedirectUrl();
		// logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		// getRedirectStrategy().sendRedirect(request, response, targetUrl);
		log.info("自定义登录失败后返回json");
		
		response.getWriter().write("{\"error\":\""+exception.getMessage()+"\"}");
	}
}
