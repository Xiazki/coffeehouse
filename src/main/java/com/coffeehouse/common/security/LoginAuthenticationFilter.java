package com.coffeehouse.common.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String USERNAMEFILED = "userName";
    private static final String PASSWORDFIELD = "password";
    private boolean postOnly = true;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(postOnly && !request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication Method must be POST");
        }

        String userName = obtainUsername(request);
        if(userName == null){
            userName = "";
        }
        String password = obtainPassword(request);
        if(password == null){
            password = "";
        }

       // String loginMode = obtainLoginMode(request);
//        if(loginMode == null){
//            throw new AuthenticationServiceException("can not found loginMode!");
//        }
        //request.getSession().setAttribute(HttpSessionUtil.SYSTEM_LOGIN_MODE,loginMode);
        UsernamePasswordAuthenticationToken taken = new UsernamePasswordAuthenticationToken(userName.trim(),password);

        this.setDetails(request,taken);
        return this.getAuthenticationManager().authenticate(taken);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String retStr1 = request.getParameter(USERNAMEFILED);
        return retStr1;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String retStr2 = request.getParameter(PASSWORDFIELD);
        return retStr2;
    }

//    private String obtainLoginMode(HttpServletRequest request){
//        String retStr3 = request.getParameter(HttpSessionUtil.SYSTEM_LOGIN_MODE);
//        return retStr3;
//    }
}

