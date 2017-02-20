package com.coffeehouse.comment.service;


import com.coffeehouse.comment.dao.ICommentDao;
import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.entity.Question;
import com.coffeehouse.common.expection.NotFoundExpection;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.CoffeerUtil;
import com.coffeehouse.common.frame.CommentType;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.question.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private ICommentDao commentDao;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CoffeerService coffeerService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;

    public List<Comment> getCommentList(String commentType,Long id) throws Exception {
        String hqlwhere = " o.type= ? and o.entityId = ? ";
        return commentDao.getQueryData(Comment.class,hqlwhere,commentType,id);
    }

    public void addComment(String content,Long entityId,String type) throws Exception {
        Comment comment = new Comment();

        //html 过滤
        comment.setContent(HtmlUtils.htmlEscape(content));
        //敏感词过滤

        Coffeer coffeer = coffeeHouseHolder.getLocalCoffeer();
        if(coffeer == null){
            coffeer = coffeerService.getAnonymousUser();
        }
        comment.setCoffeer(coffeer);

        if(CommentType.QUESTIONANSWER.equals(type)) {
            Question que = questionService.getQuestionById(entityId);
            if (que == null) {
                throw new NotFoundExpection("该问题可能已经被删除");
            }
            //将使用redis替换
            int comCut = que.getCommentCount();
            que.setCommentCount(++comCut);
            questionService.updateQuestion(que);
        }else {
            Comment com = commentDao.get(Comment.class,entityId);
            if(com == null){
                throw new NotFoundExpection("该评论可能已经被删除");
            }
        }
        //设置评论类型为回答类型
        comment.setEntityId(entityId);
        comment.setType(type);

        comment.setCreateDate(new Date());
        //更新问题评论数

        //保存回答
        commentDao.save(comment);
    }

//    public List<Comment> getCommentList(Long answerId) throws Exception {
//        String hqlwhere = " o.entityId = ? and o.type = ? ";
//        return commentDao.getQueryData(Comment.class,hqlwhere,answerId, CommentType.ANSWERCOMMENT);
//    }

}
