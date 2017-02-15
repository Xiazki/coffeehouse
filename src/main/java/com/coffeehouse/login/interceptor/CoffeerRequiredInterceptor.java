package com.coffeehouse.login.interceptor;

import com.coffeehouse.common.frame.CoffeeHouseHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截未登陆用户
 */
public class CoffeerRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(coffeeHouseHolder.getLocalCoffeer()==null) {
            //由前端控制
            //response.sendRedirect("/relogin?next="+request.getRequestURI());

            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //do nothing
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //do nothing
    }
}
