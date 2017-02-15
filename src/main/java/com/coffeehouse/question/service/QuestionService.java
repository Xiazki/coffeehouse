package com.coffeehouse.question.service;

import com.coffeehouse.common.entity.Question;
import com.coffeehouse.question.dao.IQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionService{

    @Autowired
    private IQuestionDao iQuestionDao;

    public List<Question> getLastQuestion(int offset,int limit) throws Exception {

        return iQuestionDao.getLastQuestion(offset,limit);

    }
}
