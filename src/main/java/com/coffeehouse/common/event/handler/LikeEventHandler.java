package com.coffeehouse.common.event.handler;

import com.alibaba.fastjson.JSONObject;
import com.coffeehouse.common.entity.Coffeer;
import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.entity.Message;
import com.coffeehouse.common.event.Event;
import com.coffeehouse.common.event.EventType;
import com.coffeehouse.common.event.eventimpl.EventMoudle;
import com.coffeehouse.login.service.CoffeerService;
import com.coffeehouse.massage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "likeHandler")
public class LikeEventHandler implements EventHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CoffeerService coffeerService;


    private List<String> eventType = new ArrayList<String>(){
        {
            add(EventType.LIKEEVENT);
        }
    };


    public void doHandler(EventMoudle event) throws Exception {
        //test
        Message message = new Message();
        Coffeer formcoffeer = coffeerService.getCoffeerById(event.getActorId());
        Coffeer tocoffeer = coffeerService.getCoffeerById(event.getEntityOwnerId());
        message.setFromCoffeer(formcoffeer);
        message.setToCoffeer(tocoffeer);

        message.setContent("点赞了你的回答："+event.getExt("content"));

        messageService.sendMessage(message);

    }

    public List<String> getSupportEventType() {
        return eventType;
    }
}
