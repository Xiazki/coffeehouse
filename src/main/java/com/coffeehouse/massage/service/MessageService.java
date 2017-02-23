package com.coffeehouse.massage.service;


import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Message;
import com.coffeehouse.common.expection.NotFoundExpection;
import com.coffeehouse.common.frame.MessageType;
import com.coffeehouse.massage.dao.IMessageDao;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private IMessageDao messageDao;

    //与某个用户的对话列表 默认是按照创建时间排序
    //需要近一步优化 先显示最近时间段的对话
    public List<Message> getmessageSessionList(Long formId,Long toId) throws Exception{

        String ql = "(o.fromCoffeer.id=? and o.toCoffeer.id=?) or (o.fromCoffeer.id=? and o.toCoffeer.id=?)";
        return messageDao.getQueryData(Message.class,ql,formId,toId,toId,formId);

    }

    //获取用户发送的私信
    public List<Message> getSendMessageList(Long coffeerId) throws Exception{
        String ql = " o.toCoffeer.id=? ";
        return messageDao.getQueryData(Message.class,ql,coffeerId);
    }

    //获取收到的私信列表
    public List<Message> getReceiveMessageList(Long toId,boolean status,String type) throws Exception{
        String ql = " o.toCoffeer.id=? and o.status = ? and o.type like ?";
        return messageDao.getQueryData(Message.class,ql,toId,status,"%_"+toId+"_"+type);
    }


    public void sendMessage(Message message) throws Exception{
        //获得中私信通知列表中的message
        String type = message.getFromCoffeer().getId()+"_"+message.getToCoffeer().getId()+"_"+MessageType.MESSAGE;
        Message old = getMessByType(type);
        if(old != null){
            //移除
            old.setType("");
            messageDao.update(old);
        }
        message.setStatus(true);
        message.setCreateDate(new Date());

        //将最新消息作为私信session入口
        message.setType(type);
        messageDao.save(message);
    }

    //标记已读
    public void updateMessageStatus(Long messageId) throws Exception {
       Message message = messageDao.get(Message.class,messageId);
        if(message == null){
            throw new NotFoundExpection("该私信已经被删除！");
        }
        message.setStatus(false);
        messageDao.update(message);
    }

    public Message getMessByType(String type) throws Exception {
        String ql = " o.type = ?";
        return messageDao.getSingleQueryData(Message.class,ql,type);
    }

    @Async
    public void test() throws InterruptedException {
        Thread.sleep(100000);
        System.out.println("complete");
    }
}
