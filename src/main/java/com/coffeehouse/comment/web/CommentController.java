package com.coffeehouse.comment.web;

import com.coffeehouse.comment.service.CommentService;
import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.entity.Question;
import com.coffeehouse.common.expection.NotFoundExpection;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.common.frame.CommentType;
import com.coffeehouse.common.web.BaseController;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CoffeerService coffeerService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    @RequestMapping(path = "/comm/add",method = RequestMethod.POST)
    @ResponseBody
    public String commentAdd(@RequestParam(name = "content") String content,
                             @RequestParam(name = "entityId") Long entityId,
                             @RequestParam(name = "type") String type
                            ) throws Exception {


            commentService.addComment(content, entityId, type);

        return getJSONString(CoffeerUtil.SUCCESS);
    }

    @RequestMapping(path = "/comm/list",method = RequestMethod.POST)
    @ResponseBody
    public String getCommentList(@RequestParam(name = "entityId") Long entityId,
                                 @RequestParam(name = "type") String type) throws Exception {
        List<Comment>comList = commentService.getCommentList(type,entityId);
        return getJSONString(CoffeerUtil.SUCCESS,comList);
    }
}
