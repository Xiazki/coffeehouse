package com.coffeehouse.common.frame;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class RedisAdapter {

    private static final String SPIT = ":";
    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";
    private static final String EVENT = "event";

    @Autowired
    private JedisPool jedisPool;

    /**
     *
     */
    public static String getLikeOrDisLikeKey(Long entityId,String type,boolean isLike){
        return (isLike?LIKE:DISLIKE)+SPIT+entityId+SPIT+type;
    }

    public static String getEventKey(){
        return EVENT;
    }

    /**
     *
     * @param key
     * @param value
     * @return 1添加成功，0已经存在
     * @throws Exception
     * 向集合添加一个id,
     */
    public long sadd(String key,String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //test
            return jedis.sadd(key,value);
        }catch (Exception e){
            throw e;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     *
     * @param key
     * @param value
     * @return 1移除成功，0不存在
     * @throws Exception
     * 移除一个id
     */
    public long srem(String key,String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            throw e;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     *
     * @param key
     * @return key集合的个数
     * @throws Exception
     */
    public long scard(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            throw e;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     *
     * @param key
     * @param value
     * @return
     * @throws Exception
     * 是否属于key集合
     */
    public boolean sismember(String key,String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            throw e;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public long lpush(String key,String value) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e){
            throw e;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public List<String> lpop(String key) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(0,key);
        }catch (Exception e){
            throw e;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
