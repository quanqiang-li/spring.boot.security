package spring.boot.base.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// String targetUrl = savedRequest.getRedirectUrl();
		// logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		// getRedirectStrategy().sendRedirect(request, response, targetUrl);
		log.info("自定义登录成功后返回json");
		response.getWriter().write("{\"success\":\"" + authentication.getPrincipal() + "\"}");
	}
}
