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
    private String context;

    @Column(name = "create_date")
    private Date createDate;

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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
