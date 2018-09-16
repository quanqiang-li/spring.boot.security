package spring.boot.security.conf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // private RequestCache requestCache = new HttpSessionRequestCache();

    //private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //
     @Autowired
     private ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
         response.setContentType("application/json;charset=utf-8");
         response.getWriter().write(mapper.writeValueAsString(authentication));
        // SavedRequest savedRequest = requestCache.getRequest(request, response);
        // System.out.println(savedRequest.getRedirectUrl());
        // redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        //redirectStrategy.sendRedirect(request, response, "/index");
    }
}