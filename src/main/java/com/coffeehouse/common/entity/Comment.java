package com.coffeehouse.common.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 评论实体
 */
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity<Long>{

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "coffeer_id")
    private Coffeer coffeer;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "type")
    private String type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Coffeer getCoffeer() {
        return coffeer;
    }

    public void setCoffeer(Coffeer coffeer) {
        this.coffeer = coffeer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
