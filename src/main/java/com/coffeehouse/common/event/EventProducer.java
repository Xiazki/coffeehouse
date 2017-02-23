package com.coffeehouse.common.event;

import com.coffeehouse.common.event.eventimpl.EventMoudle;

/**
 * 事件生产者
 */
public interface EventProducer {

    /**
     * 发布事件
     * @param event
     */
     void sendEvent(EventMoudle event) throws Exception;

}
