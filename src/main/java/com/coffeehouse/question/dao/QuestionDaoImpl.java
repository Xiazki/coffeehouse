package com.coffeehouse.question.dao;

import com.coffeehouse.common.dao.BaseDao;
import com.coffeehouse.common.entity.Question;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class QuestionDaoImpl extends BaseDao<Question> implements IQuestionDao{


    public List<Question> getLastQuestion(int offset,int limit) throws Exception {
        return getScrollData(Question.class,offset,limit);
    };
}
