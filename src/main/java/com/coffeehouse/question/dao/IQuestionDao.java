package com.coffeehouse.question.dao;

import com.coffeehouse.common.dao.BaseDao;
import com.coffeehouse.common.dao.idao.IBaseDao;
import com.coffeehouse.common.entity.Question;

import java.util.List;

/**
 * Created by xiang on 2017/2/14.
 */
public interface IQuestionDao extends IBaseDao<Question> {
    List<Question> getLastQuestion(int offset, int limit) throws Exception;
}
