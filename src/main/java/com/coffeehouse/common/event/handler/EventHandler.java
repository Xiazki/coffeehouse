package com.coffeehouse.common.event.handler;

import com.coffeehouse.common.event.Event;
import com.coffeehouse.common.event.eventimpl.EventMoudle;

import java.util.List;

/**
 * 事件处理接口
 */
public interface EventHandler {

    void doHandler(EventMoudle event) throws Exception;

    List<String> getSupportEventType();

}
