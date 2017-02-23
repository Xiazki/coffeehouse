package com.coffeehouse.common.event.eventimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.coffeehouse.common.event.Event;
import com.coffeehouse.common.event.EventProducer;
import com.coffeehouse.common.frame.RedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventProducerImpl implements EventProducer{

    @Autowired
    private RedisAdapter redisAdapter;

    public void sendEvent(EventMoudle event) throws Exception {
        String eventJson = JSON.toJSONString(event, SerializerFeature.DisableCircularReferenceDetect);
        String key = RedisAdapter.getEventKey();
        redisAdapter.lpush(key,eventJson);
    }
}
