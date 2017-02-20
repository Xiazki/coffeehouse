package com.coffeehouse.comment.service;

import com.coffeehouse.common.frame.RedisAdapter;
import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class OperService {

    @Autowired
    private RedisAdapter redisAdapter;

    public Long[] saveAtti(Long coffeerId,Long entityId,String type,boolean enAtti) throws Exception {

        String key1 = RedisAdapter.getLikeOrDisLikeKey(entityId,type,enAtti);
        //向like or dislike集合添加一个
        redisAdapter.sadd(key1,String.valueOf(coffeerId));
        //向like or dislike集合移除一个
        String key2 = RedisAdapter.getLikeOrDisLikeKey(entityId,type,!enAtti);
        redisAdapter.srem(key2,String.valueOf(coffeerId));
        Long[] retRs = new Long[2];
        retRs[0] = redisAdapter.scard(enAtti?key1:key2);
        retRs[1] = redisAdapter.scard(enAtti?key2:key1);
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
