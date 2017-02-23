package com.coffeehouse.comment.service;

import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.event.EventProducer;
import com.coffeehouse.common.event.EventType;
import com.coffeehouse.common.event.eventimpl.EventMoudle;
import com.coffeehouse.common.event.eventimpl.LikeEvent;
import com.coffeehouse.common.frame.CoffeeHouseHolder;
import com.coffeehouse.common.frame.RedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperService {

    @Autowired
    private RedisAdapter redisAdapter;


    @Autowired
    private EventProducer producer;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CoffeeHouseHolder coffeeHouseHolder;


    public Long[] saveAtti(Long coffeerId,Long entityId,String type,boolean enAtti) throws Exception {

        String key1 = RedisAdapter.getLikeOrDisLikeKey(entityId,type,enAtti);

        //发送likeevent到异步队列
        if(enAtti){
            //分离条件 确保取得的key是 like set
            if(!redisAdapter.sismember(key1,String.valueOf(coffeerId))) {
                Comment comment = commentService.getCommentById(entityId);
                EventMoudle likeEvent = new EventMoudle();
                likeEvent.setType(EventType.LIKEEVENT).setActorId(coffeerId)
                        .setEntityId(comment.getId())
                        .setEntityType(comment.getType())
                        .setEntityOwnerId(comment.getCoffeer().getId())
                        .setExt("content", comment.getContent());
                producer.sendEvent(likeEvent);
            }
        }

        //向like or dislike集合添加一个
        redisAdapter.sadd(key1,String.valueOf(coffeerId));
        //向like or dislike集合移除一个
        String key2 = RedisAdapter.getLikeOrDisLikeKey(entityId,type,!enAtti);
        redisAdapter.srem(key2,String.valueOf(coffeerId));
        Long[] retRs = new Long[2];
        retRs[0] = redisAdapter.scard(enAtti?key1:key2);
        retRs[1] = redisAdapter.scard(enAtti?key2:key1);

        //发布点赞事件到异步队列


        return retRs;
    }

    public int getCoffeeAtti(Long coffeerId,Long entityId,String type) throws Exception {
        String key1 = RedisAdapter.getLikeOrDisLikeKey(entityId,type,true);
        if(redisAdapter.sismember(key1,String.valueOf(coffeerId))){
            return 1;
        }
        String key2 = RedisAdapter.getLikeOrDisLikeKey(entityId,type,false);
        if(redisAdapter.sismember(key2,String.valueOf(coffeerId))){
            return -1;
        }
        return 0;
    }

    public Long getAttiCount(Long entityId,String type,boolean enAtti) throws Exception {
        String key = RedisAdapter.getLikeOrDisLikeKey(entityId,type,enAtti);
        return redisAdapter.scard(key);
    }
}
