package com.coffeehouse.common.entity;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.util.Date;

/**
 * 站内信
 */
@Entity
@Table(name = "massage")
public class Message extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "fromid")
    private Coffeer fromCoffeer;

    @ManyToOne
    @JoinColumn(name = "toid")
    private Coffeer toCoffeer;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Coffeer getFromCoffeer() {
        return fromCoffeer;
    }

    public void setFromCoffeer(Coffeer fromCoffeer) {
        this.fromCoffeer = fromCoffeer;
    }

    public Coffeer getToCoffeer() {
        return toCoffeer;
    }

    public void setToCoffeer(Coffeer toCoffeer) {
        this.toCoffeer = toCoffeer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
