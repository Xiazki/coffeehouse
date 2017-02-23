package com.coffeehouse.common.event.eventimpl;

import com.coffeehouse.common.entity.Comment;
import com.coffeehouse.common.event.Event;
import com.coffeehouse.common.event.EventType;


public class LikeEvent implements Event<Comment> {

    private Long formId;

    private Long toId;

    private Comment comment;

    public LikeEvent setFormId(Long formId) {
        this.formId = formId;
        return this;
    }

    public LikeEvent setToId(Long toId) {
        this.toId = toId;
        return this;
    }

    public LikeEvent setComment(Comment comment){
        this.comment = comment;
        return this;
    }

    public Long getFormId() {
        return null;
    }

    public Long getToId() {
        return null;
    }

    public String getType() {
        return EventType.LIKEEVENT;
    }

    public Comment getData() {
        return comment;
    }
}
