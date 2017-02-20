package com.coffeehouse.comment.dao;

import com.coffeehouse.common.dao.BaseDao;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.login.dao.ICoffeerDao;
import org.springframework.stereotype.Repository;


@Repository
public class CommentDaoImpl extends BaseDao<Comment> implements ICommentDao {

}
