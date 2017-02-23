package com.coffeehouse.common.event.eventimpl;


import java.util.HashMap;
import java.util.Map;

/**
 * 事件
 */
public class EventMoudle {
    /**
     * 事件类型
     */
    private String type;
    /**
     * 事件源Id
     */
    private Long actorId;
    /**
     * 实体类型
     */
    private String entityType;
    /**
     * 实体ID
     */
    private Long entityId;

    /**
     * 实体拥有者
     */
    private Long entityOwnerId;

    private Map<String, String> exts = new HashMap<String, String>();



    public EventMoudle setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }


    public String getExt(String key) {
        return exts.get(key);
    }


    public String getType() {
        return type;
    }

    public EventMoudle setType(String type) {
        this.type = type;
        return this;
    }

    public Long getActorId() {
        return actorId;
    }

    public EventMoudle setActorId(Long actorId) {
        this.actorId = actorId;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public EventMoudle setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public Long getEntityId() {
        return entityId;
    }

    public EventMoudle setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public Long getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventMoudle setEntityOwnerId(Long entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventMoudle setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
