package com.coffeehouse.login.web;


import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.common.web.BaseController;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.login.service.CoffeerTakenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginRegController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LoginRegController.class);

    @Autowired
    private CoffeerService coffeerService;

    @Autowired
    private CoffeerTakenService coffeerTakenService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    @RequestMapping(path = "/reg/", method = RequestMethod.POST)
    @ResponseBody
    public String register(Coffeer coffeer,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(name = "next",required = false) String next) {
        try {
            Map<String, Object> res = coffeerService.register(coffeer);
            if (res.get("errMsg") != null) {
                return getJSONString(CoffeerUtil.FAIL, res.get("errMsg").toString());
            }
            if (res.get("ct") != null) {
                //用户登陆凭证 ct
                //注册并完成登陆
                Cookie ct = new Cookie("ct", res.get("ct").toString());
                ct.setPath("/");
                response.addCookie(ct);
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            //throw new Exception("注册失败");
            return getJSONString(CoffeerUtil.FAIL, "注册失败");
        }
        return getJSONString(CoffeerUtil.REDIRECT, next);
    }

    @RequestMapping(path = "/relogin")
    public String reLogin(Model model, @RequestParam(name = "next") String next) {
        model.addAttribute("next",next);
        return "redirect:login";
    }

    @RequestMapping(path = "/lo/", method = RequestMethod.POST)
    @ResponseBody
    public String login(Coffeer coffeer,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(name = "next",required = false) String next,
                        @RequestParam(name = "rememberMe",defaultValue = "false") boolean rememberMe) throws Exception {

        Map<String,Object> res = coffeerService.login(coffeer);

        if(res.get("errMsg")!=null){
            return getJSONString(CoffeerUtil.FAIL,res.get("errMsg").toString());
        }
        if(res.get("ct")!=null){
            Cookie ct = new Cookie("ct",res.get("ct").toString());
            if(rememberMe){
                //设置cookie有效期
                ct.setMaxAge(3600*24*5);
            }
            ct.setPath("/");
            response.addCookie(ct);
        }
        //next 未未登录之前url
        return getJSONString(CoffeerUtil.REDIRECT,next);
    }

    @RequestMapping("/logout")
    public String logout(){
        coffeerService.logout(coffeeHouseHolder.getLocalCoffeer());
        return "redirect:/";
    }
}
