package spring.boot.security.conf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义过滤器
 * @author liqq
 *
 */
@Component
public class MyFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("myFilter init");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.info("myFilter doFilter begin");
		chain.doFilter(request, response);
		logger.info("myFilter doFilter end");

	}

	@Override
	public void destroy() {
		logger.info("myFilter destroy");

	}

}
