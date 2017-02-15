package com.coffeehouse.login.interceptor;

import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.CoffeerTaken;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.login.service.CoffeerTakenService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 对客户端携带的taken进行验证
 */
public class CoffeerTakenInterceptor implements HandlerInterceptor {

    @Autowired
    private CoffeerTakenService coffeerTakenService;

    @Autowired
    private CoffeerService coffeerService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ctaken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("ct".equals(cookie.getName())) {
                    ctaken = cookie.getValue();
                    break;
                }
            }
        }
        if (ctaken != null) {
            CoffeerTaken ct = coffeerTakenService.getCtByTaken(ctaken);
            //凭证过期
            if (ct == null || ct.getExpriationDate().before(new Date()) || ct.getStatus() == 0) {
                return true;
            }
            coffeeHouseHolder.set("ct",ct);
            coffeeHouseHolder.setLocalCoffeer(ct.getCoffeer());
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && coffeeHouseHolder.getLocalCoffeer() != null) {
            modelAndView.addObject("coffeer",coffeeHouseHolder.getLocalCoffeer());
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        coffeeHouseHolder.clear();
    }
}
