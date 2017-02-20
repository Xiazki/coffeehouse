package com.coffeehouse.question.web;

import com.coffeehouse.comment.service.CommentService;
import com.coffeehouse.comment.service.OperService;
import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.entity.Question;
import com.coffeehouse.common.entity.ViewObject;
import com.coffeehouse.common.expection.NotFoundExpection;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.common.frame.CommentType;
import com.coffeehouse.common.web.BaseController;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController extends BaseController {

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    @Autowired
    private CoffeerService coffeerService;


    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OperService operService;

    @RequestMapping(path = "/que/add",method = RequestMethod.POST)
    @ResponseBody
    public String add(Question question) throws Exception {
        Coffeer local = coffeeHouseHolder.getLocalCoffeer();
        //如果未登陆就使用匿名用户身份
        if(local == null){
            local = coffeerService.getAnonymousUser();
        }
        questionService.addAQuestion(question,local);
        return getJSONString(CoffeerUtil.SUCCESS);
    }

    @RequestMapping(path = "/que/{questionId}")
    public String questionDetail(Model model,@PathVariable(value = "questionId")long questionId) throws Exception {
        Question question = questionService.getQuestionById(questionId);

        if(question == null){
            throw new NotFoundExpection("没有找到该问题，可能已经被删除！");
        }
        //问题
        model.addAttribute("que",question);
        List<Comment> comList = commentService.getCommentList(CommentType.QUESTIONANSWER,questionId);
        //回答
        model.addAttribute("ansLists",comList);
        List<ViewObject> comVos = new ArrayList<ViewObject>();
        Coffeer local = coffeeHouseHolder.getLocalCoffeer();
        for(Comment com:comList){
            ViewObject vo = new ViewObject();
            vo.set("ans",com);

            vo.set("like",operService.getAttiCount(com.getId(),CommentType.QUESTIONANSWER,true));
            vo.set("dislike",operService.getAttiCount(com.getId(),CommentType.QUESTIONANSWER,false));

            if(local!=null) {
                vo.set("atti",operService.getCoffeeAtti(local.getId(),com.getId(),CommentType.QUESTIONANSWER));
            }else {
                vo.set("atti",0);
            }
            comVos.add(vo);
        }
        model.addAttribute("comVos",comVos);
        return "content/question/detail";
    }
}
