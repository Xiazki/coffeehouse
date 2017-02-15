package com.coffeehouse.common.web;

import com.coffeehouse.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends BaseController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})
    public String home(Model model) throws Exception {
        model.addAttribute("questions",questionService.getLastQuestion(0,3));

        return "home";
    }

}
