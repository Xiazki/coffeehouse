package com.coffeehouse.common.event;

import com.alibaba.fastjson.JSON;
import com.coffeehouse.common.event.eventimpl.EventMoudle;
import com.coffeehouse.common.event.handler.EventHandler;
import com.coffeehouse.common.frame.RedisAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 事件的消费者
 */
@Component
public class EventCustomer implements InitializingBean,ApplicationContextAware{

    @Autowired
    private RedisAdapter redisAdapter;

    private Map<String,List<EventHandler>> handlers = new HashMap<String, List<EventHandler>>();

    private ApplicationContext applicationContext;

    public void afterPropertiesSet() throws Exception {
        //得到容器注册的所有handlers
       Map<String,EventHandler> handlerBeans = applicationContext.getBeansOfType(EventHandler.class);
        if(handlerBeans!=null) {
            for (Map.Entry<String, EventHandler> entry : handlerBeans.entrySet()) {
                //得到支持的类型
                List<String> supportTypes = entry.getValue().getSupportEventType();
                for(String type : supportTypes){
                    //存入 处理者map
                    if(!handlers.containsKey(type)){
                        handlers.put(type,new ArrayList<EventHandler>());
                    }
                    handlers.get(type).add(entry.getValue());
                }
            }
        }

        new Thread(new Runnable() {
            public void run() {
                while(true){
                    String key = RedisAdapter.getEventKey();
                    try {
                        List<String> eventJsons = redisAdapter.lpop(key);

                        for (String json : eventJsons){
                            if(json.equals(key)){
                                continue;
                            }

                            //反序列化
                            EventMoudle event = JSON.parseObject(json,EventMoudle.class);
                            //处理
                            for(EventHandler handler : handlers.get(event.getType())){
                                handler.doHandler(event);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
