package com.coffeehouse.personal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PersonalController {

    @RequestMapping(path = "/coffeer/inbox")
    public String inbox(){
        return "content/personal/inbox";
    }

    @RequestMapping(path = "/coffeer/profile")
    public String profile(){
        return "content/personal/profile";
    }
}
