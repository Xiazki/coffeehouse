package com.coffeehouse.massage.dao;

import com.coffeehouse.common.dao.BaseDao;
import com.coffeehouse.common.entity.Message;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDao extends BaseDao<Message> implements IMessageDao {
}
