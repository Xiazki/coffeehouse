package com.coffeehouse.common.event;

/**
 * 事件接口
 */
public interface Event<T> {

    /**
     * 发起者
     * @return
     */
    Long getFormId();

    /**
     * 接受者
     * @return
     */
    Long getToId();

    /**
     * 事件的类型
     * @return
     */
   String getType();

    /**
     * 事件携带的数据
     * @return
     */
   T getData();
}
