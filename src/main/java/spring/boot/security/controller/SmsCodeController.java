package spring.boot.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import spring.boot.security.conf.SmsCode;

@RestController
public class SmsCodeController {

	public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 发送短信
	 * 
	 * @param request
	 * @param response
	 * @param mobile
	 */
	@GetMapping("/code/sms")
	public void createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) {
		SmsCode smsCode = createSMSCode();
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_SMS_CODE + mobile, smsCode);
		// 输出验证码到控制台代替短信发送服务
		System.out.println("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
	}

	private SmsCode createSMSCode() {
		String code = RandomStringUtils.randomNumeric(6);
		return new SmsCode(code, 60);
	}

}
