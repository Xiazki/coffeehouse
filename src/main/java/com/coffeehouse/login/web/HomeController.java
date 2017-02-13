package com.coffeehouse.login.web;

import com.coffeehouse.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页controller
 */
@Controller
public class HomeController extends BaseController {

    @RequestMapping(path = {"/","/home"})
    public String home(){
        return "home";
    }
}
