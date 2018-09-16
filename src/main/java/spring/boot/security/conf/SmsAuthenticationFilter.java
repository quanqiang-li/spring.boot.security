package spring.boot.security.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;

import spring.boot.security.controller.SmsCodeController;

/**
 * 过滤器,组建SmsAuthenticationToken,并委托给AuthenticationManager
 * 
 * @author carl
 *
 */
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String MOBILE_KEY = "mobile";
	public static final String SMS_CODE_KEY = "smsCode";

	private String mobileParameter = MOBILE_KEY;
	private String smsCodeParameter = SMS_CODE_KEY;
	private boolean postOnly = true;

	// 过滤的url,和请求方式
	public SmsAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login/mobile", "POST"));
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String mobile = obtainMobile(request);
		String smsCode = obtainSmsCode(request);

		if (mobile == null) {
			mobile = "";
		}

		mobile = mobile.trim();

		// 在这里先把短信验证码校验通过了
		try {
			validateSmsCode(mobile, smsCode, request);
		} catch (Exception e) {
			throw new AuthenticationServiceException("验证码不通过",e);
		}

		SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile, smsCode);

		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(mobileParameter);
	}

	protected String obtainSmsCode(HttpServletRequest request) {
		return request.getParameter(smsCodeParameter);
	}

	protected void setDetails(HttpServletRequest request, SmsAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setMobileParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
		this.mobileParameter = mobileParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobileParameter() {
		return mobileParameter;
	}

	private void validateSmsCode(String mobile,String smsCodeInRequest,HttpServletRequest request) throws Exception {
		SmsCode codeInSession = (SmsCode) request.getSession().getAttribute(SmsCodeController.SESSION_KEY_SMS_CODE + mobile);
		if (StringUtils.isBlank(smsCodeInRequest)) {
			throw new Exception("验证码不能为空！");
		}
		if (codeInSession == null) {
			throw new Exception("验证码不存在，请重新发送！");
		}
		if (codeInSession.isExpire()) {
			request.getSession().removeAttribute(SmsCodeController.SESSION_KEY_SMS_CODE + mobile);
			throw new Exception("验证码已过期，请重新发送！");
		}
		if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), smsCodeInRequest)) {
			throw new Exception("验证码不正确！");
		}
		request.getSession().removeAttribute(SmsCodeController.SESSION_KEY_SMS_CODE + mobile);
	}
}