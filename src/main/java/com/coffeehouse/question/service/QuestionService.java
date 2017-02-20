package com.coffeehouse.question.service;

import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Question;
import com.coffeehouse.question.dao.IQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class QuestionService{

    @Autowired
    private IQuestionDao iQuestionDao;

    public List<Question> getLastQuestion(int offset,int limit) throws Exception {

        return iQuestionDao.getLastQuestion(offset,limit);

    }

    public void addAQuestion(Question question, Coffeer coffeer) throws Exception{
        Question varQ = new Question();
        //HTML过滤
        String titleEscStr = HtmlUtils.htmlEscape(question.getTitle());
        String desExcStr = HtmlUtils.htmlEscape(question.getDescription());
        //敏感词过滤

        varQ.setTitle(titleEscStr);
        varQ.setDescription(desExcStr);
        varQ.setCoffeer(coffeer);
        varQ.setCreateDate(new Date());
        varQ.setCommentCount(0);
        iQuestionDao.save(varQ);
    }

    public Question getQuestionById(Long questionId) throws Exception {
        return iQuestionDao.get(Question.class,questionId);
    }

    public void updateQuestion(Question question) throws Exception {
        iQuestionDao.update(question);
    }
}
